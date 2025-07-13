import type { SubscriptionPlanDetails, SubscriptionPlanForm, SubscriptionPlanListItem, SubscriptionPlanSearch } from "../../dto/management/subscription-plan";
import { securedClient } from "../_instance";

export async function searchPlan(search:SubscriptionPlanSearch):Promise<SubscriptionPlanListItem[]> {
    const {data} = await securedClient().get("/management/plan", {params: search})
    return data
}

export async function findPlanById(id:unknown) : Promise<SubscriptionPlanDetails> {
    const {data} = await securedClient().get(`/management/plan/${id}`)
    return data
}

export async function createPlan(form:SubscriptionPlanForm) : Promise<SubscriptionPlanDetails>  {
    const {data} = await securedClient().post('/management/plan', form)
    return data
}

export async function updatePlan(id: unknown,form:SubscriptionPlanForm) : Promise<SubscriptionPlanDetails> {
    const {data} = await securedClient().put(`/management/plan/${id}`, form)
    return data
}