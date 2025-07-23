import type { PageSearch } from "..";
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
