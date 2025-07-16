import type { LedgerType } from "../../constants"

export type LedgerSearch = {
    type? : LedgerType
    keyword? : string
}

export type LedgerPk = {
    code : string
    memberId : number
}

export type LedgerListItem = {
  id: LedgerPk;
  type: LedgerType; 
  name: string;
  description: string;
  entries: number;
  total: string; // BigDecimal as string
};

export type LedgerForm = {
    type: LedgerType | ""
    code: string
    name: string
    description?: string
}