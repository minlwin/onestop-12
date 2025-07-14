import type { SubscriptionPlanListItem } from "../../dto/member/subscription-plan";
import { userName } from "../../store/auth-result.store";
import { securedClient } from "../_instance";

export async function searchPlan():Promise<SubscriptionPlanListItem[]> {
    const response = securedClient().get(`/member/${userName()}/plan`)
    return (await response).data
}