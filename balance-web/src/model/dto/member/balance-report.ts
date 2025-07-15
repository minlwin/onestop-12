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
  debit: string;   // BigDecimal represented as string
  credit: string;  // BigDecimal represented as string
  balance: string; // BigDecimal represented as string
};
