import type { BalanceReportListItem, BalanceReportSearch } from "../../dto/member/balance-report";
import { userName } from "../../store/auth-result.store";
import { securedClient, type PageResult } from "../_instance";

export async function searchBalance(form:BalanceReportSearch):Promise<PageResult<BalanceReportListItem>> {
    const response = await securedClient().get(`member/${userName()}/balance`, {params : form})
    return response.data
}