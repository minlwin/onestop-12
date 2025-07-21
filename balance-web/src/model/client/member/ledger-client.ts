import type { LedgerForm, LedgerListItem, LedgerPk, LedgerSearch } from "../../dto/member/ledger";
import { userName } from "../../store/auth-result.store";
import { handleError } from "../_error_handler";
import { securedClient, type ModificationResult } from "../_instance";

export async function searchLedger(form:LedgerSearch):Promise<LedgerListItem[] | undefined> {
    const response = await securedClient().get(`member/${userName()}/ledger`, {params: form}).catch(handleError)
    return response?.data
}

export async function createLedger(form:LedgerForm):Promise<ModificationResult<LedgerPk> | undefined> {
    const response = await securedClient().post(`member/${userName()}/ledger`, form).catch(handleError)
    return response?.data    
}

export async function updateLedger(form:LedgerForm):Promise<ModificationResult<LedgerPk> | undefined> {
    const response = await securedClient().put(`member/${userName()}/ledger`, form).catch(handleError)
    return response?.data    
}
