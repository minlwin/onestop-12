import type { SubscriptionDetails, SubscriptionListItem, SubscriptionPk, SubscriptionSearch, SubscriptionStatusUpdateForm } from "../../dto/management/subscription";
import { securedClient, type ModificationResult, type PageResult } from "../_instance";

export async function searchSubscription(form:SubscriptionSearch):Promise<PageResult<SubscriptionListItem>> {
    const response = await securedClient().get('/management/subscription', {params: form})
    return response.data
}

export async function findSubscriptionById(code: unknown):Promise<SubscriptionDetails> {
    const response = await securedClient().get(`/management/subscription/${code}`)
    return response.data
}

export async function updateSubscriptionStatus(code : unknown, form:SubscriptionStatusUpdateForm):Promise<ModificationResult<SubscriptionPk>> {
    const response = await securedClient().put(`/management/subscription/${code}`, form)
    return response.data
}