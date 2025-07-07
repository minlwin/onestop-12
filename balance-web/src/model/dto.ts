export interface SignInForm {
    email : string
    password : string
}

export type SignUpForm = SignInForm & {
    name : string
}

export interface AuthResult {
    email : string
    name : string
    role : 'Admin' | 'Member'
    accessToken : string
    refreshToken : string
}

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

