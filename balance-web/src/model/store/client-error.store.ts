import type { AxiosError } from "axios"
import { create } from "zustand"

type ClientErrorStoreType = {
    error? : AxiosError
    setError : (error? : AxiosError) => void
}

export const clientErrorStore = create<ClientErrorStoreType>((set) => ({
    error : undefined,
    setError : (e) => set({error : e})
}))