import type { ApiResponse, ModificationResult, PageResult } from "../../dto";
import type { SubscriptionDetails, SubscriptionListItem, SubscriptionPk, SubscriptionSearch, SubscriptionStatusUpdateForm } from "../../dto/management/subscription";
import { handleError } from "../_error_handler";
import { securedClient} from "../_instance";

export async function searchSubscription(form:SubscriptionSearch):ApiResponse<PageResult<SubscriptionListItem>> {
    const response = await securedClient().get('/management/subscription', {params: form}).catch(handleError)
    return response?.data
}

export async function findSubscriptionById(code: unknown):ApiResponse<SubscriptionDetails> {
    const response = await securedClient().get(`/management/subscription/${code}`).catch(handleError)
    return response?.data
}

export async function updateSubscriptionStatus(code : unknown, form:SubscriptionStatusUpdateForm):ApiResponse<ModificationResult<SubscriptionPk>> {
    const response = await securedClient().put(`/management/subscription/${code}`, form).catch(handleError)
    return response?.data
}