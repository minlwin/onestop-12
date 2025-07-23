import { Link, useParams } from "react-router";
import Page from "../../../../ui/page";
import { useEffect, useState } from "react";
import { findPlanById } from "../../../../model/client/management/subscription-plan-client";
import type { SubscriptionPlanDetails } from "../../../../model/dto/management/subscription-plan";
import FormGroup from "../../../../ui/form-group";
import { limitValue } from "../../../../model/utils";

export default function SubscriptionPlanDetails() {

    const params = useParams()
    const planId = params['planId']
    const [plan, setPlan] = useState<SubscriptionPlanDetails>()

    useEffect(() => {
        async function load() {
            const result = await findPlanById(planId)
            setPlan(result)
        }

        if(planId) {
            load()
        }
    }, [planId])

    if(!plan) {
        return <></>
    }

    return (
        <Page icon={<i className="bi-bookmark-heart"></i>} title="Subscription Plan">
            <div className="row mb-3">
                <FormGroup label="Plan Type" className="col-3">
                    <span className="form-control">{plan.defaultPlan ? "Default Plan" : "Paid Plan"}</span>
                </FormGroup>
                <FormGroup label="Plan Type" className="col-3">
                    <span className="form-control">{plan.name}</span>
                </FormGroup>
            </div>
            <div className="row mb-3">
                <FormGroup label="Fees" className="col-3">
                    <span className="form-control">{plan.fees} MMK</span>
                </FormGroup>
                <FormGroup label="Months" className="col-3">
                    <span className="form-control">{plan.months} Months</span>
                </FormGroup>
            </div>
            <div className="row mb-3">
                <FormGroup label="Maximum Ledger" className="col-3">
                    <span className="form-control">{limitValue(plan.maxLedgers || 0)}</span>
                </FormGroup>
                <FormGroup label="Daily Entry Limit" className="col-3">
                    <span className="form-control">{limitValue(plan.dailyEntry || 0)}</span>
                </FormGroup>
                <FormGroup label="Monthly Entry Limit" className="col-3">
                    <span className="form-control">{limitValue(plan.monthlyEntry || 0)}</span>
                </FormGroup>
            </div>

            <div className="row mb-3">
                <FormGroup className="col-3" label="Status">
                    <span className="form-control">{plan.active ? "Active" : "Pending"}</span>
                </FormGroup>
            </div>

            <Link to={`/admin/master/plan/edit?planId=${plan.id}`} className="btn btn-dark">
                <i className="bi-pencil"></i> Edit Plan
            </Link>
        </Page>
    )
}