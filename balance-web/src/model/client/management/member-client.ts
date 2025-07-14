import type { MemberListItem, MemberSearch } from "../../dto/management/member";
import { securedClient, type PageResult } from "../_instance";

export async function searchMember(form:MemberSearch):Promise<PageResult<MemberListItem>> {
    const response = await securedClient().get('/management/member', {params : form})
    return response.data
}