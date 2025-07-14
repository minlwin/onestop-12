import axios from "axios";
import { authStore } from "../store/auth-result.store";
import { refreshToken } from "./anonymous/client";

export function anonymousClient() {
    return axios.create({
        baseURL: 'http://localhost:8080/anonymous',
        timeout: 100000
    })
}

export function securedClient() {

    const instance = axios.create({
        baseURL: 'http://localhost:8080',
        timeout: 100000
    })

    instance.interceptors.request.use(config => {
        const {auth} = authStore.getState()
    
        if(auth) {
                config?.headers.set('Authorization', `Bearer ${auth.accessToken}`)
            }
            return config
        }, error => {
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
                instance(originalRequest)
            } catch(e) {
                console.log(e)
                window.location.href = "/"
            }
        }

        return Promise.reject(error)
    })

    return instance
}

export type Pager = {
    page: number
    size: number
    totalCount : number
    totalPage : number
    links : number[]
}

export type PageResult<T> = {
    contents : T[]
    pager?: Pager
}

export type ModificationResult<T> = {
    success: boolean
    id?: T
    message? :string
}

export type PageSearch = {
    page: number
    size: number
}