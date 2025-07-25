import { useNavigate, useParams } from "react-router";
import Page from "../../../ui/page";
import { useMemberPlanContext } from "../../../model/provider/member-plan-context";
import React, { useEffect, useRef, useState } from "react";
import type { CurrentPlan } from "../../../model/dto/member/member-profile";
import { getCurrentPlan } from "../../../model/client/member/member-profile-client";
import Card from "../../../ui/card";
import PlanInfo from "../../../ui/plan-info";
import { limitValue, usageValue } from "../../../model/utils";
import FormGroup from "../../../ui/form-group";
import { useMemberPaymentContext } from "../../../model/provider/member-payment-context";
import type { Usage } from "../../../model/constants";
import { useForm } from "react-hook-form";
import type { SubscriptionForm } from "../../../model/dto/member/subscription";
import SlipImage from "../../../ui/slip-image";
import { createSubscription } from "../../../model/client/member/subscription-client";
import type { PaymentMethodListItem } from "../../../model/dto/member/payment-method";

export default function SubscriptionApplication() {

    const params = useParams()
    const {plans} = useMemberPlanContext()
    const {payments} = useMemberPaymentContext()    
    const navigate = useNavigate()

    const newPlan = plans.filter(a => a.id.toString() == params.planId).pop()

    const [currentPlan, setCurrentPlan] = useState<CurrentPlan>()
    const [usages, setUsages] = useState<Usage[]>([])

    const {register, setValue, handleSubmit, formState : {isValid}} = useForm<SubscriptionForm>()
    const fileSelectRef = useRef<HTMLInputElement | null>(null)

    const [slip, setSlip] = useState<File>()
    const [payment, setPayment] = useState<PaymentMethodListItem>()

    useEffect(() => {
        async function load() {
            const response = await getCurrentPlan()
            if(response) {
                setCurrentPlan(response)
            }
        }
        load()
    }, [])

    useEffect(() => {
        if(newPlan && currentPlan) {

            setValue("planId", newPlan.id, {shouldValidate : true})

            if(newPlan.id != currentPlan.planId && !currentPlan.expired) {
                // Current
                setUsages(['Urgent', 'Extend'])
            } else {
                setUsages(['Extend'])
            }
        }
    }, [currentPlan, newPlan, setValue])

    useEffect(() => {
        setValue('usage', usages[0], {shouldValidate :true})
    }, [usages, setValue])

    if(!newPlan || (!usages || usages.length == 0)) {
        return (
            <></>
        )
    }

    function changePaymentMethod(e: React.ChangeEvent<HTMLSelectElement>) {
        setPayment(undefined)

        if(e.target.value) {
            const selectedPayment = payments.find(a => a.id.toString() == e.target.value)
            setPayment(selectedPayment)
        }
    }

    function uploadSlip() {
        setValue('slip', undefined, {shouldValidate :true})
        setSlip(undefined)
        fileSelectRef.current?.click()
    }

    function changeSelectedFile(e : React.ChangeEvent<HTMLInputElement>) {
        if(e.target.files?.length) {
            setValue('slip', e.target.files[0], {shouldValidate :true})
            setSlip(e.target.files[0])
        }
    }

    async function save(form:SubscriptionForm) {
        const response = await createSubscription(form)
        if(response) {
            navigate("/member/subscription")
        }
    }

    return (
        <Page icon={<i className="bi-cart"></i>} title="Subscribe Plan">

            <form onSubmit={handleSubmit(save)} className="row">
                <input type="hidden" {...register('planId', {required : true})} />
                <input type="hidden" {...register('slip', {required : true})} />
                <input ref={fileSelectRef} type="file" className="d-none" onChange={changeSelectedFile} />
                <div className="col">
                    <Card icon={<i className="bi-shield me-2"></i>} title={newPlan.name}>
                        <div className="list-group list-group-flush">
                            <PlanInfo name="Maximum Ledgers" value={limitValue(newPlan.maxLedgers || 0)} />
                            <PlanInfo name="Daily Entry" value={limitValue(newPlan.dailyEntry || 0)} />
                            <PlanInfo name="Monthly Entry" value={limitValue(newPlan.monthlyEntry || 0)} />
                            <PlanInfo name="Months" value={newPlan.months} />
                            <PlanInfo name="Fees" value={newPlan.fees.toLocaleString()} />
                        </div>
                    </Card>
                </div>
                <div className="col">
                    <Card icon={<i className="bi-cart-plus me-2"></i>} title="Subscription Info">
                        <FormGroup className="mb-3" label="Subscription Type">
                            {usages.length > 1 ? 
                                <select {...register('usage', {required : true})} className="form-select">
                                    <option value="">Select One</option>
                                    {usages.map(a => 
                                        <option key={a} value={a}>{usageValue(a)}</option>
                                    )}    
                                </select> : 
                                <>  
                                    <span className="form-control">{usageValue(usages[0])}</span>
                                    <input {...register('usage', {required : true})} type="hidden" value={usages[0]} className="form-control" />
                                </>
                            }
                        </FormGroup>

                        <FormGroup label="Payment Method" className="mb-3">
                            <select {...register('paymentId', {required : true})} onChange={changePaymentMethod} className="form-select">
                                <option value="">Select One</option>
                                {payments.map(a => 
                                    <option key={a.id} value={a.id}>{a.name}</option>
                                )}
                            </select>
                        </FormGroup>

                        <FormGroup label="Account No" className="mb-3">
                            <input type="text" readOnly={true} className="form-control" value={payment?.accountNo} />
                        </FormGroup>
                        
                        <FormGroup label="Account Name" className="mb-3">
                            <input type="text" readOnly={true} className="form-control" value={payment?.accountName} />
                        </FormGroup>

                        <div>
                            <button onClick={uploadSlip} type="button" className="btn btn-outline-secondary me-2">
                                <i className="bi-upload"></i> Upload Slip
                            </button>
                            <button type="submit" disabled={!isValid} className={`btn btn-secondary`}>
                                <i className="bi-save"></i> Subscribe Plan
                            </button>
                        </div>
                    </Card>
                </div>
                <div className="col-3">
                    <Card icon={<i className="bi-credit-card me-2"></i>} title="Payment Slip">
                        <SlipImage src={slip} />
                    </Card>
                </div>
            </form>
        </Page>
    )
}