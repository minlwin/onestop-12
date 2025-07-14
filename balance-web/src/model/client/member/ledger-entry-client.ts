import type { LedgerEntryDetails, LedgerEntryForm, LedgerEntryListItem, LedgerEntryPk, LedgerEntrySearch } from "../../dto/member/ledger-entry";
import { userName } from "../../store/auth-result.store";
import { securedClient, type ModificationResult, type PageResult } from "../_instance";

export async function searchEntry(form:LedgerEntrySearch):Promise<PageResult<LedgerEntryListItem>> {
    const response = await securedClient().get(`/member/${userName()}/entry`, {params : form})
    return response.data
}

export async function findEntryById(requestId:unknown):Promise<LedgerEntryDetails> {
    const response = await securedClient().get(`/member/${userName()}/entry/${requestId}`)
    return response.data
}

export async function createEntry(form:LedgerEntryForm):Promise<ModificationResult<LedgerEntryPk>> {
    const response = await securedClient().post(`/member/${userName()}/entry`, form)
    return response.data
}

export async function updateEntry(requestId:unknown, form:LedgerEntryForm):Promise<ModificationResult<LedgerEntryPk>> {
    const response = await securedClient().put(`/member/${userName()}/entry/${requestId}`, form)
    return response.data
}
