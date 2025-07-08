import { Link, useParams } from "react-router";
import Page from "../../../../ui/page";
import { useEffect, useState } from "react";
import { findPaymentMethod } from "../../../../model/client/management-client";
import type { PaymentMethodDetails } from "../../../../model/dto";
import FormGroup from "../../../../ui/form-group";

export default function PaymentMethodDetails() {

    const params = useParams()
    const paymentId = params['paymentId']
    const [details, setDetails] = useState<PaymentMethodDetails>()

    useEffect(() => {
        async function load() {
            const response = await findPaymentMethod(paymentId)
            setDetails(response)     
        }

        if(paymentId) {
            load()
        }
    }, [paymentId])

    return (
        <Page title="Payment Method" icon={
            <i className="bi-credit-card"></i>
        }>
            <div className="row">
                <div className="col-4">
                    <FormGroup label="Payment Method Name" className="mb-3">
                        <span className="form-control">{details?.name}</span>
                    </FormGroup>
                    <FormGroup label="Account No" className="mb-3">
                        <span className="form-control">{details?.accountNo}</span>
                    </FormGroup>
                    <FormGroup label="Account Name" className="mb-3">
                        <span className="form-control">{details?.accountName}</span>
                    </FormGroup>
                    <FormGroup label="Status" className="mb-3">
                        <span className="form-control">{details?.active ? "Active" : "Pending"}</span>
                    </FormGroup>

                    <div>
                        <Link to={`/admin/master/payment/edit?paymentId=${details?.id}`} className="btn btn-dark">
                            <i className="bi-pencil"></i> Edit Payment Method
                        </Link>
                    </div>
                </div>
            </div>

        </Page>
    )
}