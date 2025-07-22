import { useEffect, useState } from "react";
import Page from "../../../ui/page";
import type { SubscriptionListItem } from "../../../model/dto/member/subscription";
import type { SubscriptionPlanListItem } from "../../../model/dto/member/subscription-plan";
import { searchPlan } from "../../../model/client/member/subscription-plan-client";
import { searchSubscription } from "../../../model/client/member/subscription-client";
import Card from "../../../ui/card";
import { Link } from "react-router";

export default function MemberSubscriptions() {

    const [plans, setPlans] = useState<AvailablePlan[]>([])
    const [history, setHistory] = useState<SubscriptionListItem[]>([])

    useEffect(() => {

        async function load() {
            const planList = await searchPlan()
            const subscriptionResult = await searchSubscription({
                page: 0,
                size: 10
            })

            if(planList && subscriptionResult) {
                const {contents} = subscriptionResult
                setHistory(contents)

                const pendingSubscription = contents.filter(a => a.status === 'Pending').shift()

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
                        applied : pendingSubscription ? plan.id == pendingSubscription.id.planId : false
                    }
                }

                setPlans(planList.map(mapToAvailablePlan))
            }
        }

        load()
    }, [setPlans, setHistory])

    return (
        <Page title="Subscriptions" icon={<i className="bi-shield"></i>}>
            
            <section className="mb-5">
                <AvailablePlans plans={plans} />
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
    applied: boolean
}

function AvailablePlans({plans} : {plans : AvailablePlan[]}) {

    function limitValue(value: number) {
        return value < 0 ? "Unlimited" : value.toLocaleString()
    }

    return (
        <>
            <h5 className="mb-2"><i className="bi-cart"></i> Available Plans</h5>

            <div className="row row-cols-3">
            {plans.map(plan => 
                <div className="col">
                    <Card title={`${plan.planName} Plan`}>
                        <div className="list-group list-group-flush">
                            <PlanInfo name="Max Ledger" value={limitValue(plan.maxLedger)} />
                            <PlanInfo name="Daily Limit" value={limitValue(plan.dailyEntry)} />
                            <PlanInfo name="Monthly Limit" value={limitValue(plan.monthlyEntry)} />
                            <PlanInfo name="Months" value={plan.months} />
                            <PlanInfo name="Fees" value={plan.fees.toLocaleString()} />
                        </div>

                        <div className="text-end">
                            <Link to={`/member/subscription/${plan.planId}`} className={`btn btn-secondary ${plan.defaultPlan || plan.applied ? 'disabled' : ''}`} >
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

function PlanInfo({name, value} : {name: string, value: string | number}) {
    return (
        <div className={`d-flex justify-content-between list-group-item`}>
            <label>{name}</label>
            <span>{value}</span>
        </div>
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
                        <th>Reason</th>
                    </tr>
                </thead>

                <tbody>
                {history.map(item => 
                    <tr key={item.id.appliedAt}>
                        <td>{item.planName}</td>
                        <td>{item.id.appliedAt}</td>
                        <td>{item.expiredAt}</td>
                        <td>{item.status}</td>
                        <td>{item.statusChangeAt}</td>
                        <td>{item.reason}</td>
                    </tr>
                )}    
                </tbody>
            </table>
        </>
    )
}
