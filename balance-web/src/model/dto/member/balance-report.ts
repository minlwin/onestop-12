import type { PageSearch } from "../../client/_instance";
import type { LedgerEntryPk } from "./ledger-entry";

export type BalanceReportSearch = {
    from?: string
    to?: string
} & PageSearch

export type BalanceReportListItem = {
  id: LedgerEntryPk;
  issueAt:string;
  ledger: string;
  particular: string;
  debit: number;   
  credit: number;  
  balance: number; 
};
