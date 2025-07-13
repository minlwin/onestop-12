export type PaymentMethodSearch = {
    active? : boolean
    keyword? : string
}

export type PaymentMethodForm = {
    name: string
    accountNo: string
    accountName: string
    active: boolean
}

export type PaymentMethodDetails = {
    id: number
} & PaymentMethodForm

export type PaymentMethodListItem = {
    payments: number
} & PaymentMethodDetails
