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

export type SubscriptionPlanForm = {
    name: string;             // required, must not be blank
    months: number;
    fees: number;
    maxLedgers?: number;      // optional (Integer in Java = nullable)
    dailyEntry?: number;      // optional
    monthlyEntry?: number;    // optional
    defaultPlan: boolean;
    active: boolean;
};

export type SubscriptionPlanDetails = SubscriptionPlanForm & {
    id: number
}

export type SubscriptionPlanListItem = SubscriptionPlanDetails & {
    subscription : number
}

export type SubscriptionPlanSearch = {
  active?: boolean;       // nullable Boolean in Java
  keyword?: string;       // nullable String
  monthFrom?: number;     // nullable Integer
  monthTo?: number;       // nullable Integer
};



