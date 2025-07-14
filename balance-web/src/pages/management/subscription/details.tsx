import { useParams } from "react-router";
import Page from "../../../ui/page";
import { useEffect, useState } from "react";
import { findSubscriptionById } from "../../../model/client/management/subscription-client";
import type { SubscriptionDetails } from "../../../model/dto/management/subscription";
import Card from "../../../ui/card";
import Information from "../../../ui/information";
import NoSlip from "../../../ui/no-slip";

export default function SubscriptionDetails() {

    const params = useParams()
    const [details, setDetails] = useState<SubscriptionDetails>()

    useEffect(() => {
        async function load() {
            const response = await findSubscriptionById(params.code)
            setDetails(response)
        }

        if(params.code) {
            load()
        }
    }, [params, setDetails]) 

    return (
        <Page title="Subscription Details" icon={<i className="bi-cart-plus"></i>}>
            <div className="row">
                <div className="col-3">
                    {details?.paymentSlip ? 
                        <img src={details.paymentSlip} alt="Payment Slip" /> 
                        : <NoSlip />
                    } 
                </div>

                <div className="col-4">
                    <Card title="Member">
                        <Information label="Name" value={details?.memberName || ''} /> 
                    </Card>
                </div>

            </div>
        </Page>
    )
}