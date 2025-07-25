import type { PageSearch, PlanInfo } from "..";
import type { SubscriptionStatus, Usage } from "../../constants";

export type SubscriptionPk = {
    appliedAt: string; // ISO 8601 date string, e.g., '2025-07-13'
    planId: number;
    memberId: number;
    code : string
};

export type SubscriptionListItem = {
    id: SubscriptionPk;
    planName: string;
    previousPlan: string;
    expiredAt: string; // 'yyyy-MM-dd'
    paymentName: string;
    paymentAmount: number;
    memberId: number;
    memberName: string;
    usage: Usage;
    status: SubscriptionStatus;
    statusChangeAt: string; // ISO 8601 datetime string, e.g., '2025-07-13T15:30:00'
    reason: string;
};

export type SubscriptionDetails = {
    id: SubscriptionPk;
    plan: PlanInfo;
    previousPlan: PlanInfo;
    currentAppliedAt: string;
    currentStartAt?: string;
    currentExpiredAt?: string;
    prevAppliedAt?: string;
    prevStartAt?: string;
    prevEndAt?: string;
    paymentAmount: number;
    paymentName: string;
    paymentSlip: string;
    memberId: number;
    memberName: string;
    phone: string;
    email: string;
    usage: Usage;
    status: SubscriptionStatus;
    statusChangeAt: string; // LocalDateTime → ISO date-time string (e.g., "2025-07-14T12:30:00")
    reason: string;
    createdBy: string;
    createdAt: string; // LocalDateTime
    updatedBy: string;
    updatedAt: string; // LocalDateTime
};


export type SubscriptionSearch = {
    planId?: number;
    status?: SubscriptionStatus;
    appliedFrom?: string; // 'yyyy-MM-dd' format
    appliedTo?: string;
    keyword?: string;
} & PageSearch;

export type SubscriptionStatusUpdateForm = {
    status: SubscriptionStatus
    message?: string
}
