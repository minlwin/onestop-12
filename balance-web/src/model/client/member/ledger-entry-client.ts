import type { ApiResponse, ModificationResult, PageResult } from "../../dto";
import type { LedgerEntryDetails, LedgerEntryForm, LedgerEntryListItem, LedgerEntryPk, LedgerEntrySearch } from "../../dto/member/ledger-entry";
import { userName } from "../../store/auth-result.store";
import { handleError } from "../_error_handler";
import { securedClient} from "../_instance";

export async function searchEntry(form:LedgerEntrySearch):ApiResponse<PageResult<LedgerEntryListItem>> {
    const response = await securedClient().get(`/member/${userName()}/entry`, {params : form}).catch(handleError)
    return response?.data
}

export async function findEntryById(requestId:unknown):ApiResponse<LedgerEntryDetails> {
    const response = await securedClient().get(`/member/${userName()}/entry/${requestId}`).catch(handleError)
    return response?.data
}

export async function createEntry(form:LedgerEntryForm):ApiResponse<ModificationResult<LedgerEntryPk>> {
    const response = await securedClient().post(`/member/${userName()}/entry`, form).catch(handleError)
    return response?.data
}

export async function updateEntry(requestId:unknown, form:LedgerEntryForm):ApiResponse<ModificationResult<LedgerEntryPk>> {
    const response = await securedClient().put(`/member/${userName()}/entry/${requestId}`, form).catch(handleError)
    return response?.data
}
