import { useParams } from "react-router";
import Page from "../../../ui/page";
import { useEffect, useState } from "react";
import { findSubscriptionById } from "../../../model/client/management/subscription-client";
import type { SubscriptionDetails } from "../../../model/dto/management/subscription";

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
            
        </Page>
    )
}