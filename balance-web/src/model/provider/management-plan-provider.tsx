import type React from "react";
import { useEffect, useState } from "react";
import type { SubscriptionPlanListItem } from "../dto/management/subscription-plan";
import { ManagementPlanContext } from "./management-plan-context";
import { searchPlan } from "../client/management/subscription-plan-client";

export default function ManagementPlanProvider({children} : {children: React.ReactNode}) {
    const [plans, setPlans] = useState<SubscriptionPlanListItem[]>([])

    useEffect(() => {
        async function load() {
            const result = await searchPlan({})
            setPlans(result)
        }

        load()
    }, [setPlans])

    return (
        <ManagementPlanContext.Provider value={{plans : plans, setPlans: setPlans}}>
            {children}
        </ManagementPlanContext.Provider>
    )
}