import type { SubscriptionPk } from "../../dto/management/subscription";
import type { SubscriptionDetails, SubscriptionForm, SubscriptionListItem, SubscriptionSearch } from "../../dto/member/subscription";
import { userName } from "../../store/auth-result.store";
import { securedClient, type ModificationResult, type PageResult } from "../_instance";

export async function searchSubscription(form:SubscriptionSearch):Promise<PageResult<SubscriptionListItem>> {
    const response = await securedClient().get(`member/${userName()}/subscription`, {params: form})
    return response.data
}

export async function findSubscriptionById(code : unknown):Promise<SubscriptionDetails> {
    const response = await securedClient().get(`member/${userName()}/subscription/${code}`)
    return response.data
}

export async function createSubscription(form:SubscriptionForm):Promise<ModificationResult<SubscriptionPk>> {
    const response = await securedClient().post(`member/${userName()}/subscription`, form)
    return response.data
}