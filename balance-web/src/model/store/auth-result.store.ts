import { create } from "zustand"
import type { AuthResult } from "../dto/anonymous/commons"
import { createJSONStorage, persist } from "zustand/middleware"

type AuthStoreType = {
    auth? : AuthResult
    setAuth : (auth? : AuthResult) => void
}

export const authStore = create(persist<AuthStoreType>(
   (set) => ({
        auth: undefined,
        setAuth: (auth) => set({auth: auth})
   }),
   {
     name: 'com.jdc.balance.user',
     storage: createJSONStorage(() => localStorage)
   }
))

export function userName() {
  const auth = authStore.getState().auth as AuthResult | undefined

  if(!auth) {
    throw Error("You need to login")
  }

  return auth.email
}