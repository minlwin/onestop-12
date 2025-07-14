export type SubscriptionPlanListItem = {
  id: number;
  name: string;
  months: number;
  fees: number;
  maxLedgers?: number;
  dailyEntry?: number;
  monthlyEntry?: number;
  defaultPlan: boolean;
};
