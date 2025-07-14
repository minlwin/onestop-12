import { createContext, useContext } from "react"
import type { SubscriptionPlanListItem } from "../dto/management/subscription-plan"

type PlanContextType = {
    plans: SubscriptionPlanListItem[]
    setPlans: (plans: SubscriptionPlanListItem[]) => void
}

const ManagementPlanContext = createContext<PlanContextType | undefined>(undefined)

const useManagementPlan = () => {
    const context = useContext(ManagementPlanContext)

    if(!context) {
        throw Error("Invalid usage of Management Plan Context")
    }

    return context
}

export {
    ManagementPlanContext,
    useManagementPlan
}