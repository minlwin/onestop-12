import type { ApiResponse } from "../../dto";
import type { PaymentMethodListItem } from "../../dto/member/payment-method";
import { userName } from "../../store/auth-result.store";
import { handleError } from "../_error_handler";
import { securedClient } from "../_instance";

export async function searchPaymentMethod():ApiResponse<PaymentMethodListItem[]> {
    const response = await securedClient().get(`/member/${userName()}/payment`).catch(handleError)
    return response?.data
}