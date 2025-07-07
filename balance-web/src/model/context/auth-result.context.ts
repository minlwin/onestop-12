import { createContext, useContext } from "react"
import type { AuthResult } from "../dto"

type AuthResultContextType = {
    authResult?: AuthResult
    setAuthResult: (result?: AuthResult) => void
}

const AuthResultContext = createContext<AuthResultContextType | undefined>(undefined)

function useAuthResultContext() {
    const context = useContext(AuthResultContext)

    if(!context) {
        throw Error('Invalid usage of AuthResult Context')
    }

    return context
}

function useAuthResult() {
    return useAuthResultContext().authResult
}

function useAuthResultSetter() {
    return useAuthResultContext().setAuthResult
}

const STORAGE_KEY = 'com.jdc.balance.user'

function readAuthResult():AuthResult | undefined {
    const result = localStorage.getItem(STORAGE_KEY)

    if(result) {
        return JSON.parse(result)
    }
}

function saveAuthResult(result?: AuthResult) {
    if(result) {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(result))
    }
}

export {
    useAuthResult,
    useAuthResultSetter,
    AuthResultContext,
    readAuthResult,
    saveAuthResult
}