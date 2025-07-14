import type { PageSearch } from "../../client/_instance"
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
  reason: string;
  statusChangeAt: string; // LocalDateTime → ISO string (e.g., '2025-07-13T18:30:00')
};

export type SubscriptionDetails = SubscriptionListItem & {
    planStartAt: string
    fees: number
    accountNo : string
    accountName: string
    paymentSlip: string    
}

export type SubscriptionForm = {
    planId: number
    paymentId: number
    slip: File
    usage: Usage
}
