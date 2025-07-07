import axios from "axios";

export function anonymousClient() {
    return axios.create({
        baseURL: 'http://localhost:8080/anonymous',
        timeout: 1000
    })
}