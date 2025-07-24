import { useEffect, useState } from "react";
import Page from "../../../ui/page";
import type { SubscriptionListItem } from "../../../model/dto/member/subscription";
import type { SubscriptionPlanListItem } from "../../../model/dto/member/subscription-plan";
import { searchSubscription } from "../../../model/client/member/subscription-client";
import Card from "../../../ui/card";
import { Link } from "react-router";
import { getCurrentPlan } from "../../../model/client/member/member-profile-client";
import { useMemberPlanContext } from "../../../model/provider/member-plan-context";
import PlanInfo from "../../../ui/plan-info";
import { limitValue } from "../../../model/utils";

export default function MemberSubscriptions() {

    const [plans, setPlans] = useState<AvailablePlan[]>([])
    const [history, setHistory] = useState<SubscriptionListItem[]>([])
    const planMaster = useMemberPlanContext()

    const applied = history.find(a => a.status == 'Pending')

    useEffect(() => {

        async function load() {
            const planList = planMaster.plans
            const subscriptionResult = await searchSubscription({
                page: 0,
                size: 10
            })

            if(planList && subscriptionResult) {
                const {contents} = subscriptionResult
                setHistory(contents)

                const currentPlan = await getCurrentPlan()

                if(currentPlan) {
                    function mapToAvailablePlan(plan: SubscriptionPlanListItem):AvailablePlan {
                        return {
                            planId : plan.id,
                            planName: plan.name,
                            months : plan.months,
                            fees: plan.fees,
                            defaultPlan: plan.defaultPlan,
                            maxLedger: plan.maxLedgers || 0,
                            dailyEntry: plan.dailyEntry || 0,
                            monthlyEntry: plan.monthlyEntry || 0,
                            current : currentPlan?.planId == plan.id
                        }
                    }
                    setPlans(planList.map(mapToAvailablePlan))
                }
            }
        }

        load()
    }, [setPlans, setHistory, planMaster])

    return (
        <Page title="Subscriptions" icon={<i className="bi-flag"></i>}>
            
            {applied && 
            <div className="alert alert-info">
                You had applied {applied.planName} plan. Please wait for approvment.
            </div>
            }

            <section className="mb-4">
                <AvailablePlans applied={applied != undefined} plans={plans} />
            </section>

            <section>
                <SubscriptionHistory history={history} />
            </section>
        </Page>
    )
}

type AvailablePlan = {
    planId: number
    planName : string
    months: number
    fees : number
    maxLedger : number
    dailyEntry : number
    monthlyEntry : number
    defaultPlan : boolean
    current: boolean
}

function AvailablePlans({applied, plans} : {applied : boolean, plans : AvailablePlan[]}) {

    return (
        <>
            <h5 className="mb-2"><i className="bi-cart"></i> Available Plans</h5>

            <div className="row row-cols-3">
            {plans.map(plan => 
                <div key={plan.planId} className="col">
                    <Card icon={<i className="bi-shield me-2"></i>} title={`${plan.planName} Plan`} >
                        <div className="list-group list-group-flush">
                            <PlanInfo name="Max Ledger" value={limitValue(plan.maxLedger)} />
                            <PlanInfo name="Daily Limit" value={limitValue(plan.dailyEntry)} />
                            <PlanInfo name="Monthly Limit" value={limitValue(plan.monthlyEntry)} />
                            <PlanInfo name="Months" value={plan.months} />
                            <PlanInfo name="Fees" value={plan.fees.toLocaleString()} />
                        </div>

                        <div className="d-flex justify-content-between align-items-center mt-3">
                            <div>
                            {plan.current && 
                                <span className="btn btn-outline-secondary"><i className="bi-check-circle"></i> Current Plan</span>
                            }
                            </div>
                            <Link to={`/member/subscription/${plan.planId}`} className={`btn btn-secondary ${plan.defaultPlan || applied ? 'disabled' : ''}`} >
                                <i className="bi-cart-plus"></i> Subscribe
                            </Link>
                        </div>
                    </Card>
                </div>
            )}    
            </div>
        </>
    )
}

function SubscriptionHistory({history} : {history: SubscriptionListItem[]}) {
    return (
        <>
            <h5 className="mb-2"><i className="bi-calendar"></i> Subscription History</h5>

            <table className="table table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th>Plan</th>
                        <th>Applied At</th>
                        <th>Expired At</th>
                        <th>Status</th>
                        <th>Change At</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody>
                {history.map(item => 
                    <tr key={item.id.code}>
                        <td>{item.planName}</td>
                        <td>{item.id.appliedAt}</td>
                        <td>{item.expiredAt}</td>
                        <td>{item.status}</td>
                        <td>{item.statusChangeAt}</td>
                        <td className="text-center">
                            <Link to={`/member/subscription/details/${item.id.code}`} className="icon-link">
                                <i className="bi-arrow-right"></i>
                            </Link>
                        </td>
                    </tr>
                )}    
                </tbody>
            </table>
        </>
    )
}
