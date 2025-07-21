import type { SubscriptionPlanListItem } from "../../dto/member/subscription-plan";
import { userName } from "../../store/auth-result.store";
import { handleError } from "../_error_handler";
import { securedClient } from "../_instance";

export async function searchPlan():Promise<SubscriptionPlanListItem[] | undefined> {
    const response = securedClient().get(`/member/${userName()}/plan`).catch(handleError)
    return (await response)?.data
}