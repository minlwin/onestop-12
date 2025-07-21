import type { MemberListItem, MemberSearch } from "../../dto/management/member";
import { handleError } from "../_error_handler";
import { securedClient, type PageResult } from "../_instance";

export async function searchMember(form:MemberSearch):Promise<PageResult<MemberListItem> | undefined> {
    const response = await securedClient().get('/management/member', {params : form}).catch(handleError)
    return response?.data
}