import React, { useEffect, useState } from "react";
import { MemberLedgerContext, type LedgerItem } from "./member-ledger-context";
import { searchLedger } from "../client/member/ledger-management-client";
import { authStore } from "../store/auth-result.store";

export default function MemberLedgerProvider({children} : {children : React.ReactNode}) {
    
    const [ledgers, setLedgers] = useState<LedgerItem[]>([])
    const {auth} = authStore()

    useEffect(() => {
        async function load() {
            if(auth) {
                const response = await searchLedger({})
                if(response) {
                    setLedgers(response)
                }
            }
        }

        load()
    }, [setLedgers, auth])

    return (
        <MemberLedgerContext.Provider value={{ledgers : ledgers, setLedgers : setLedgers}}>
            {children}
        </MemberLedgerContext.Provider>
    )
}