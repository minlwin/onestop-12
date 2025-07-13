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
    member: number
}

export type SubscriptionPlanSearch = {
  active?: boolean;       // nullable Boolean in Java
  keyword?: string;       // nullable String
  monthFrom?: number;     // nullable Integer
  monthTo?: number;       // nullable Integer
};
