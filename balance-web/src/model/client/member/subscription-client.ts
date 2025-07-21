import type { SubscriptionPk } from "../../dto/management/subscription";
import type { SubscriptionDetails, SubscriptionForm, SubscriptionListItem, SubscriptionSearch } from "../../dto/member/subscription";
import { userName } from "../../store/auth-result.store";
import { handleError } from "../_error_handler";
import { securedClient, type ModificationResult, type PageResult } from "../_instance";

export async function searchSubscription(form:SubscriptionSearch):Promise<PageResult<SubscriptionListItem> | undefined> {
    const response = await securedClient().get(`member/${userName()}/subscription`, {params: form}).catch(handleError)
    return response?.data
}

export async function findSubscriptionById(code : unknown):Promise<SubscriptionDetails | undefined> {
    const response = await securedClient().get(`member/${userName()}/subscription/${code}`).catch(handleError)
    return response?.data
}

export async function createSubscription(form:SubscriptionForm):Promise<ModificationResult<SubscriptionPk> | undefined> {
    const response = await securedClient().post(`member/${userName()}/subscription`, form).catch(handleError)
    return response?.data
}