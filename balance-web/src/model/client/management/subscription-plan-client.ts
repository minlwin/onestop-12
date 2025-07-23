import type { ApiResponse, ModificationResult } from "../../dto";
import type { SubscriptionPlanDetails, SubscriptionPlanForm, SubscriptionPlanListItem, SubscriptionPlanSearch } from "../../dto/management/subscription-plan";
import { handleError } from "../_error_handler";
import { securedClient } from "../_instance";

export async function searchPlan(search:SubscriptionPlanSearch):ApiResponse<SubscriptionPlanListItem[]> {
    const response = await securedClient().get("/management/plan", {params: search}).catch(handleError)
    return response?.data
}

export async function findPlanById(id:unknown) : ApiResponse<SubscriptionPlanDetails> {
    const response = await securedClient().get(`/management/plan/${id}`).catch(handleError)
    return response?.data
}

export async function createPlan(form:SubscriptionPlanForm) : ApiResponse<ModificationResult<number>>  {
    const response = await securedClient().post('/management/plan', form).catch(handleError)
    return response?.data
}

export async function updatePlan(id: unknown,form:SubscriptionPlanForm) : ApiResponse<SubscriptionPlanDetails> {
    const response = await securedClient().put(`/management/plan/${id}`, form).catch(handleError)
    return response?.data
}