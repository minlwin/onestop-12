import { useParams } from "react-router";
import Page from "../../../ui/page";
import { useEffect, useState } from "react";
import { findSubscriptionById } from "../../../model/client/management/subscription-client";
import type { SubscriptionDetails } from "../../../model/dto/management/subscription";
import Card from "../../../ui/card";
import Information from "../../../ui/information";
import type { SubscriptionStatus } from "../../../model/constants";
import SlipImage from "../../../ui/slip-image";

export default function SubscriptionDetails() {

    const params = useParams()
    const [details, setDetails] = useState<SubscriptionDetails>()

    useEffect(() => {
        async function load() {
            const response = await findSubscriptionById(params.code)
            console.log(response)
            setDetails(response)
        }

        if(params.code) {
            load()
        }
    }, [params, setDetails]) 

    async function updateStatus(status: SubscriptionStatus) {
        console.log(status)
    }

    return (
        <Page title="Subscription Details" icon={<i className="bi-cart-plus"></i>} actions={
            details?.status == 'Pending' && 
            <div>
                <button onClick={() => updateStatus('Approved')} type="button" className="btn btn-primary">
                    <i className="bi-check"></i> Approve
                </button>

                <button onClick={() => updateStatus('Denied')} type="button" className="btn btn-danger ms-2">
                    <i className="bi-x"></i> Denied
                </button>
            </div>
        }>
            <div className="row">

                <div className="col-4">
                    <Card title="Member" className="mb-3" >
                        <Information label="Name" value={details?.memberName || ''} /> 
                        <Information label="Phone" value={details?.phone || 'No Phone Number'} /> 
                        <Information label="Email" value={details?.email || ''} /> 
                    </Card>

                    <Card title="Request Plan" className="mb-3">
                        <Information label="Plan Name" value={details?.plan.name || ''} /> 
                        <Information label="Fees" value={details?.plan.fees || '0'} /> 
                        <Information label="Months" value={details?.plan.months || ''} /> 
                        <Information label="Ledger Limit" value={details?.plan.maxLedgers || ''} /> 
                        <Information label="Daily Entry" value={details?.plan.dailyEntry || ''} /> 
                        <Information label="Monthly Entry" value={details?.plan.monthlyEntry || ''} /> 
                    </Card>

                    {details?.previousPlan &&
                        <Card title="Previous Plan" className="mb-3">
                            <Information label="Plan Name" value={details?.previousPlan.name || ''} /> 
                            <Information label="Expired At" value={details?.prevEndAt || ''} /> 
                        </Card>
                    }

                </div>

                <div className="col">
                    <Card title="Subscription">
                        <Information label="Amount" value={details?.paymentAmount || '0'} /> 
                        <Information label="Payment" value={details?.paymentName || 'No Payment'} /> 
                        <Information label="Requested Type" value={details?.usage || ''} /> 
                        <Information label="Status" value={details?.status || ''} /> 
                        <Information label="Change Reason" value={details?.reason || ''} /> 
                        <Information label="Changed At" value={details?.statusChangeAt || ''} /> 
                        <Information label="Created At" value={details?.createdAt || ''} /> 
                        <Information label="Created By" value={details?.createdBy || ''} /> 
                        <Information label="Modified At" value={details?.updatedAt || ''} /> 
                        <Information label="Modified By" value={details?.updatedBy || ''} /> 
                    </Card>
                </div>

                <div className="col-3">
                    <Card title="Payment Slip" icon={<i className="bi-filetype-png"></i>}>
                        <SlipImage src={details?.paymentSlip} />                     
                    </Card>
                </div>
            </div>
        </Page>
    )
}