import type { ApiResponse, ModificationResult, Optional, PageResult } from "../../dto";
import type { SubscriptionPk } from "../../dto/management/subscription";
import type { SubscriptionDetails, SubscriptionForm, SubscriptionListItem, SubscriptionSearch } from "../../dto/member/subscription";
import { userName } from "../../store/auth-result.store";
import { handleError } from "../_error_handler";
import { securedClient} from "../_instance";

export async function searchSubscription(form:SubscriptionSearch):Promise<Optional<PageResult<SubscriptionListItem>>> {
    const response = await securedClient().get(`member/${userName()}/subscription`, {params: form}).catch(handleError)
    return response?.data
}

export async function findSubscriptionById(code : unknown):ApiResponse<SubscriptionDetails> {
    const response = await securedClient().get(`member/${userName()}/subscription/${code}`).catch(handleError)
    return response?.data
}

export async function createSubscription(form:SubscriptionForm):ApiResponse<ModificationResult<SubscriptionPk>> {
    const response = await securedClient().post(`member/${userName()}/subscription`, form, {
        headers: {'Content-Type' : 'multipart/form-data'}
    }).catch(handleError)
    return response?.data
}