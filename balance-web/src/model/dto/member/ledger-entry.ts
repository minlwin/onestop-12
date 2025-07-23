import type { PageSearch } from ".."
import type { LedgerType } from "../../constants"

export type LedgerEntryPk = {
    code : string
    memberId : number
    seqNumber : number
    requestId : string
}

export type LedgerEntrySearch = {
    type: LedgerType
    code?: string
    from? : string
    to? : string
    keyword? : string
} & PageSearch

export type LedgerEntryListItem = {
  id: LedgerEntryPk;
  ledgerName: string;
  type: LedgerType;
  issueAt: string; // LocalDate → ISO date string (e.g., '2025-07-13')
  particular: string;
  lastBalance: number; 
  amount: number; 
};

export type LedgerEntryItem = {
    item: string
    unitPrice: number
    quantity: number
    remark: string
}

export type LedgerEntryForm = {
    code : string
    particular : string
    items : LedgerEntryItem[]
}

export type LedgerEntryDetails = LedgerEntryListItem & {
    items: LedgerEntryItem [],
    canEdit: boolean
}

