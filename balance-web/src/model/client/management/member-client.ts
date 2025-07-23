import type { ApiResponse, PageResult } from "../../dto";
import type { MemberListItem, MemberSearch } from "../../dto/management/member";
import { handleError } from "../_error_handler";
import { securedClient } from "../_instance";

export async function searchMember(form:MemberSearch):ApiResponse<PageResult<MemberListItem>> {
    const response = await securedClient().get('/management/member', {params : form}).catch(handleError)
    return response?.data
}