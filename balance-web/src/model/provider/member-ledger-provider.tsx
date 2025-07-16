import React, { useEffect, useState } from "react";
import { MemberLedgerContext, type LedgerItem } from "./member-ledger-context";
import { searchLedger } from "../client/member/ledger-client";

export default function MemberLedgerProvider({children} : {children : React.ReactNode}) {
    
    const [ledgers, setLedgers] = useState<LedgerItem[]>([])

    useEffect(() => {
        async function load() {
            const response = await searchLedger({})
            setLedgers(response)
        }

        load()
    }, [setLedgers])

    return (
        <MemberLedgerContext.Provider value={{ledgers : ledgers, setLedgers : setLedgers}}>
            {children}
        </MemberLedgerContext.Provider>
    )
}