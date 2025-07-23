import type { ApiResponse } from "../../dto";
import type { CurrentPlan } from "../../dto/member/member-profile";
import { userName } from "../../store/auth-result.store";
import { handleError } from "../_error_handler";
import { securedClient } from "../_instance";

export async function getCurrentPlan():ApiResponse<CurrentPlan> {
    const response = await securedClient().get(`/member/${userName()}/dashboard/plan`).catch(handleError)
    return response?.data
}