import type { SubscriptionDetails, SubscriptionListItem, SubscriptionPk, SubscriptionSearch, SubscriptionStatusUpdateForm } from "../../dto/management/subscription";
import { handleError } from "../_error_handler";
import { securedClient, type ModificationResult, type PageResult } from "../_instance";

export async function searchSubscription(form:SubscriptionSearch):Promise<PageResult<SubscriptionListItem> | undefined> {
    const response = await securedClient().get('/management/subscription', {params: form}).catch(handleError)
    return response?.data
}

export async function findSubscriptionById(code: unknown):Promise<SubscriptionDetails | undefined> {
    const response = await securedClient().get(`/management/subscription/${code}`).catch(handleError)
    return response?.data
}

export async function updateSubscriptionStatus(code : unknown, form:SubscriptionStatusUpdateForm):Promise<ModificationResult<SubscriptionPk> | undefined> {
    const response = await securedClient().put(`/management/subscription/${code}`, form).catch(handleError)
    return response?.data
}