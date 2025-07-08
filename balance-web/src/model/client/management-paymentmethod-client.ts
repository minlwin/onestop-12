import type { PaymentMethodDetails, PaymentMethodForm, PaymentMethodListItem, PaymentMethodSearch } from "../dto";
import { securedClient } from "./_instance";

export async function searchPaymentMethod(form: PaymentMethodSearch)
    :Promise<PaymentMethodListItem[]> {
    const {data} = await securedClient().get('/management/payment', {params : form})
    return data   
}

export async function createPaymentMethod(form: PaymentMethodForm)
    :Promise<PaymentMethodDetails> {
    const {data} = await securedClient().post('/management/payment', form)
    return data
}

export async function updatePaymentMethod(id:unknown, form: PaymentMethodForm)
    :Promise<PaymentMethodDetails> {
    const {data} = await securedClient().put(`/management/payment/${id}`, form)
    return data
}

export async function findPaymentMethod(id: unknown)
    :Promise<PaymentMethodDetails> {
    const {data} = await securedClient().get(`/management/payment/${id}`)
    return data
}