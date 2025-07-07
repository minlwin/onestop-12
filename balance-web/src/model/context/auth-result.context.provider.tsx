import React, { useEffect, useState } from "react";
import { AuthResultContext, readAuthResult } from "./auth-result.context";
import type { AuthResult } from "../dto";

export default function AuthResultContextProvider({children} : {children: React.ReactNode}) {
    const [authResult, setAuthResult] = useState<AuthResult>()

    useEffect(() => {
        const result = readAuthResult()
        if(result) {
            setAuthResult(result)
        }
    }, [])

    return (
        <AuthResultContext.Provider value={{authResult: authResult, setAuthResult: setAuthResult}}>
            {children}
        </AuthResultContext.Provider>
    )
}