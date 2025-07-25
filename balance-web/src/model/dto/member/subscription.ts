import type { PageSearch, PlanInfo } from ".."
import type { SubscriptionStatus, Usage } from "../../constants"
import type { SubscriptionPk } from "../management/subscription"

export type SubscriptionSearch = {
    planId? : number
    appliedFrom? : string
    appliedTo? : string
} & PageSearch

export type SubscriptionListItem = {
    id: SubscriptionPk;
    previousPlan: string;
    expiredAt: string; // LocalDate → ISO string (e.g., '2025-07-13')
    planName: string;
    paymentName: string;
    usage: Usage;
    status: SubscriptionStatus;
    reason?: string;
    statusChangeAt?: string; // LocalDateTime → ISO string (e.g., '2025-07-13T18:30:00')
};

export type SubscriptionDetails = {
    id: SubscriptionPk;
    plan: PlanInfo;
    previousPlan?: PlanInfo;
    currentAppliedAt: string;
    currentStartAt?: string;
    currentExpiredAt?: string;
    prevAppliedAt?: string;
    prevStartAt?: string;
    prevEndAt?: string;
    fees: number;
    payment: string;
    accountNo : string;
    accountName: string;
    paymentSlip: string;
    usage: Usage;
    status: SubscriptionStatus;
    reason?: string;
    statusChangeAt?: string; // LocalDateTime → ISO string (e.g., '2025-07-13T18:30:00')
}

export type SubscriptionForm = {
    planId: number
    paymentId: number
    slip?: File
    usage: Usage
}
