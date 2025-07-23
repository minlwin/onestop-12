import type { ApiResponse, ModificationResult } from "../../dto";
import type { PaymentMethodDetails, PaymentMethodForm, PaymentMethodListItem, PaymentMethodSearch } from "../../dto/management/payment-method";
import { handleError } from "../_error_handler";
import { securedClient } from "../_instance";

export async function searchPaymentMethod(form: PaymentMethodSearch)
    :ApiResponse<PaymentMethodListItem[]> {
    const response = await securedClient().get('/management/payment', {params : form}).catch(handleError)
    return response?.data   
}

export async function createPaymentMethod(form: PaymentMethodForm)
    :ApiResponse<ModificationResult<number>> {
    const response = await securedClient().post('/management/payment', form).catch(handleError)
    return response?.data
}

export async function updatePaymentMethod(id:unknown, form: PaymentMethodForm)
    :ApiResponse<ModificationResult<number>> {
    const response = await securedClient().put(`/management/payment/${id}`, form).catch(handleError)
    return response?.data
}

export async function findPaymentMethod(id: unknown)
    :ApiResponse<PaymentMethodDetails> {
    const response = await securedClient().get(`/management/payment/${id}`).catch(handleError)
    return response?.data
}