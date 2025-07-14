import type { PaymentMethodListItem } from "../../dto/member/payment-method";
import { userName } from "../../store/auth-result.store";
import { securedClient } from "../_instance";

export async function searchPaymentMethod():Promise<PaymentMethodListItem[]> {
    const response = await securedClient().get(`/member/${userName()}/payment`)
    return response.data
}