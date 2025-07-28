import type { ApiResponse, PageResult } from "../../dto";
import type { BalanceReportListItem, BalanceReportSearch } from "../../dto/member/balance-report";
import { userName } from "../../store/auth-result.store";
import { handleError } from "../_error_handler";
import { securedClient } from "../_instance";

export async function searchBalance(form:BalanceReportSearch):ApiResponse<PageResult<BalanceReportListItem>> {
    const response = await securedClient().get(`member/${userName()}/balance`, {params : form}).catch(handleError)
    return response?.data
}