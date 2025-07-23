import { createContext, useContext } from "react"
import type { SubscriptionPlanListItem } from "../dto/member/subscription-plan"
import type { Optional } from "../dto"

type MemberPlanContextType = {
    plans : SubscriptionPlanListItem[]
    setPlans : (plans : SubscriptionPlanListItem[]) => void
}

const MemberPlanContext = createContext<Optional<MemberPlanContextType>>(undefined)

const useMemberPlanContext = () => {
    const context = useContext(MemberPlanContext)
    if(!context) {
        throw new Error("Invalid usage of Member Plans Context")
    }

    return context
} 

export {
    MemberPlanContext,
    useMemberPlanContext
}
