import React, { useEffect, useState } from "react";
import { MemberPlanContext } from "./member-plan-context";
import type { SubscriptionPlanListItem } from "../dto/member/subscription-plan";
import { searchPlan } from "../client/member/subscription-plan-client";
import { authStore } from "../store/auth-result.store";

export default function MemberPlanContextProvider({children} : {children : React.ReactNode}) {
    const [plans, setPlans] = useState<SubscriptionPlanListItem[]>([])
    const {auth} = authStore()

    useEffect(() => {
        async function load() {
            if(auth) {
                const response = await searchPlan()
                if(response) {
                    setPlans(response)
                }
            }
        }

        load()
    }, [auth])

    return (
        <MemberPlanContext.Provider value={{plans : plans, setPlans : setPlans}}>
            {children}
        </MemberPlanContext.Provider>
    )
}