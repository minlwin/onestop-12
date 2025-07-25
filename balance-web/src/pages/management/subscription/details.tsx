import { useParams } from "react-router";
import Page from "../../../ui/page";
import { useEffect, useRef, useState } from "react";
import { findSubscriptionById, updateSubscriptionStatus } from "../../../model/client/management/subscription-client";
import type { SubscriptionDetails, SubscriptionStatusUpdateForm } from "../../../model/dto/management/subscription";
import Card from "../../../ui/card";
import Information from "../../../ui/information";
import type { SubscriptionStatus } from "../../../model/constants";
import SlipImage from "../../../ui/slip-image";
import ModalDialogProvider from "../../../model/provider/modal-dialog-provider";
import ModalDialog from "../../../ui/modal-dialog";
import { useModalDialogContext } from "../../../model/provider/modal-dialog-context";
import { useForm } from "react-hook-form";
import FormGroup from "../../../ui/form-group";
import FormError from "../../../ui/form-error";
import Loading from "../../../ui/loading";

export default function SubscriptionDetails() {

    const params = useParams()
    const [details, setDetails] = useState<SubscriptionDetails>()
    const [status, setStatus] = useState<SubscriptionStatus>('Approved')

    useEffect(() => {
        async function load() {
            const response = await findSubscriptionById(params.code)
            setDetails(response)
        }

        if(params.code) {
            load()
        }
    }, [params, setDetails]) 


    async function updateStatus(form:SubscriptionStatusUpdateForm) {
        const updateResult = await updateSubscriptionStatus(params.code, form)
        const code = updateResult?.id?.code
        if(code) {
            const response = await findSubscriptionById(code)
            setDetails(response)
        }
    }

    if(!details) {
        return (
            <Loading />
        )
    }

    return (
        <ModalDialogProvider>
            <SubscriptionDetailsView details={details} setStatus={setStatus}/>
            <UpdateDialog status={status} save={updateStatus} />
        </ModalDialogProvider>
    )
}

function UpdateDialog({status, save} : {status : SubscriptionStatus, save : (form:SubscriptionStatusUpdateForm) => void}) {
    
    const {register, handleSubmit, reset, formState : {errors}} = useForm<SubscriptionStatusUpdateForm>()
    const formRef = useRef<HTMLFormElement>(null)

    useEffect(() => {
        reset({status : status, message: ""})
    }, [status, reset])

    return (
        <ModalDialog title={`Confirmation`} action={() => {
                formRef.current?.requestSubmit()
        }}>
            <p>{`Do you want to ${status == 'Approved' ? 'approve' : 'deny'}?`}</p>
            <form ref={formRef} onSubmit={handleSubmit(save)}>
            {status == 'Denied' && 
                <FormGroup label="Deny Reason">
                    <textarea {...register('message', {required : true})} className="form-control" placeholder="Enter Deny Reason"></textarea>                   
                    {errors.message && <FormError message="Please eneter deny reason." />}
                </FormGroup>
            }
            </form>
        </ModalDialog>
    )
}

function SubscriptionDetailsView({details, setStatus} : {details: SubscriptionDetails, setStatus : (status : SubscriptionStatus) => void}) {

    const {setShow} = useModalDialogContext()

    async function updateStatus(status: SubscriptionStatus) {
        setStatus(status)
        setShow(true)
    }

    useEffect(() => {
        setShow(false)
    }, [details, setShow])

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
                    <Card title="Member" className="mb-3" >
                        <Information label="Name" value={details?.memberName || ''} /> 
                        <Information label="Phone" value={details?.phone || 'No Phone Number'} /> 
                        <Information label="Email" value={details?.email || ''} /> 
                    </Card>

                    <Card title="Subscription">
                        <Information label="Amount" value={details?.paymentAmount || '0'} /> 
                        <Information label="Payment" value={details?.paymentName || 'No Payment'} /> 
                        <Information label="Requested Type" value={details?.usage || ''} /> 
                        <Information label="Status" value={details?.status || ''} /> 
                        <Information label="Change Reason" value={details?.reason || ''} /> 
                        <Information label="Changed At" value={details?.statusChangeAt || ''} /> 
                        <Information label="Created At" value={details?.createdAt || ''} /> 
                        <Information label="Modified At" value={details?.updatedAt || ''} /> 
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