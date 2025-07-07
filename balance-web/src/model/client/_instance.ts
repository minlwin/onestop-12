import axios from "axios";
import { authStore } from "../store/auth-result.store";
import { refreshToken } from "./anonymous-client";

export function anonymousClient() {
    return axios.create({
        baseURL: 'http://localhost:8080/anonymous',
        timeout: 100000
    })
}

export function securedClient() {

    const {auth} = authStore.getState()
    
    const instance = axios.create({
        baseURL: 'http://localhost:8080',
        timeout: 100000
    })

    instance.interceptors.request.use(
        config => {
            if(auth) {
                config?.headers.set('Authorization', `Bearer ${auth.accessToken}`)
            }
            return config
        }, 
        error => {
            console.log(error)
            return Promise.reject(error)
        }
    )

    instance.interceptors.response.use(response => {
        return response
    }, async (error) => {

        const originalRequest = error.config
        const {auth, setAuth} = authStore.getState()

        if(error.status == 408 && !originalRequest._retry) {
            try {
                originalRequest._retry = true
                
                // Refresh token
                const refreshResult = await refreshToken(auth?.refreshToken || '')
                setAuth(refreshResult)

                // Retry last request
                instance.defaults.headers.common['Authorization'] = `Bearer ${refreshResult.refreshToken}`
                instance(originalRequest)
            } catch(e) {
                console.log(e)
                window.location.href = "/"
            }
        }

        if(error.status == 401) {
            window.location.href = "/"
        }

        return Promise.reject(error)
    })

    return instance
}
