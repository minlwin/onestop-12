import type { SubscriptionPlanDetails, SubscriptionPlanForm, SubscriptionPlanListItem, SubscriptionPlanSearch } from "../../dto/management/subscription-plan";
import { handleError } from "../_error_handler";
import { securedClient, type ModificationResult } from "../_instance";

export async function searchPlan(search:SubscriptionPlanSearch):Promise<SubscriptionPlanListItem[] | undefined> {
    const response = await securedClient().get("/management/plan", {params: search}).catch(handleError)
    return response?.data
}

export async function findPlanById(id:unknown) : Promise<SubscriptionPlanDetails | undefined> {
    const response = await securedClient().get(`/management/plan/${id}`).catch(handleError)
    return response?.data
}

export async function createPlan(form:SubscriptionPlanForm) : Promise<ModificationResult<number> | undefined>  {
    const response = await securedClient().post('/management/plan', form).catch(handleError)
    return response?.data
}

export async function updatePlan(id: unknown,form:SubscriptionPlanForm) : Promise<SubscriptionPlanDetails | undefined> {
    const response = await securedClient().put(`/management/plan/${id}`, form).catch(handleError)
    return response?.data
}