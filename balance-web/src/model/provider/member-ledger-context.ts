import { createContext, useContext } from "react"
import type { LedgerListItem } from "../dto/member/ledger"

type LedgerItem = Pick<LedgerListItem, "id" | "name" | "type">

type MemberLedgerContextType = {
    ledgers: LedgerItem[],
    setLedgers: (ledgers : LedgerItem[]) => void
}

const MemberLedgerContext = createContext<MemberLedgerContextType | undefined>(undefined)

const useMemberLedgerContext = () => {
    const context = useContext(MemberLedgerContext)

    if(!context) {
        throw Error("Invalid usage of Member Ledger Context")
    }

    return context
}

export {
    MemberLedgerContext,
    useMemberLedgerContext
}

export type {
    LedgerItem
}