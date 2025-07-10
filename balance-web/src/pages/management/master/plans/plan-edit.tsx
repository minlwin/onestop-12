import { useNavigate, useSearchParams } from "react-router";
import Page from "../../../../ui/page";
import FormGroup from "../../../../ui/form-group";
import { useForm } from "react-hook-form";
import type { SubscriptionPlanForm } from "../../../../model/dto";
import { createPlan, findPlanById, updatePlan } from "../../../../model/client/management-plan-client";
import { useEffect } from "react";
import FormError from "../../../../ui/form-error";

export default function EditSubscriptionPlan() {

    const [params] = useSearchParams()
    const planId = params.get("planId")
    const navigate = useNavigate()

    const {handleSubmit, reset, register, watch,formState : {errors}} = useForm<SubscriptionPlanForm>()

    useEffect(() => {
        async function load() {
            const response = await findPlanById(planId)
            reset({
                name: response.name,
                fees: response.fees,
                months: response.months,
                maxLedgers: response.maxLedgers,
                dailyEntry: response.dailyEntry,
                monthlyEntry: response.monthlyEntry,
                defaultPlan: response.defaultPlan,
                active: response.active
            })
        }

        if(planId) {
            load()
        }
    }, [planId, reset])

    async function save(form:SubscriptionPlanForm) {
        if(planId) {
            await updatePlan(planId, form)
        } else {
            await createPlan(form)
        } 
        navigate(`/admin/master/plan`)
    }

    return (
        <Page icon={<i className="bi-pencil"></i>} title={`${planId ? "Edit" : "Create"} Subscription Plan`}>

            <div className="row">
                <div className="col-9">
                    <form onSubmit={handleSubmit(save)}>
                        <div className="row mb-3">
                            <FormGroup label="Plan Type" className="col-4">
                                <select {...register('defaultPlan', {required: true})} className="form-select">
                                    <option value="">Select Plan Type</option>
                                    <option value="true">Default Plan</option>
                                    <option value="false">Paid Plan</option>
                                </select>
                                {errors.active && <FormError message="Please select plan type" />}
                            </FormGroup>
                            <FormGroup label="Plan Name" className="col-4">
                                <input {...register('name', {required: true})} type="text" placeholder="Enter Subscription Plan Name" className="form-control" />
                                {errors.name && <FormError message="Please enter plan name." />}
                            </FormGroup>
                        </div>
                        <div className="row mb-3">
                            <FormGroup className="col-4" label="Fees">
                                <input {...register('fees', {required: true})} type="number" placeholder="Enter fees" className="form-control" />
                                {errors.fees && <FormError message="Please enter fees." />}
                            </FormGroup>
                            <FormGroup className="col-4" label="Months">
                                <input {...register('months', {required: true})} type="number" placeholder="Enter Months" className="form-control" />
                                {errors.months && <FormError message="Please enter months." />}
                            </FormGroup>
                        </div>
                        <div className="row mb-3">
                            <FormGroup className="col-4" label="Maximum Ledger">
                                <input {...register('maxLedgers', {required : true})} type="number" placeholder="Enter Maximum Ledger" className="form-control" />
                                {errors.fees && <FormError message="Please enter fees." />}
                            </FormGroup>
                            <FormGroup className="col-4" label="Daily Entry Limit">
                                <input {...register('dailyEntry', {required :true})} type="number" placeholder="Enter Daily Entry Limit" className="form-control" />
                                {errors.fees && <FormError message="Please enter daily entry limit." />}
                            </FormGroup>
                            <FormGroup className="col-4" label="Monthly Entry Limit">
                                <input {...register('monthlyEntry', {required : true})} type="number" placeholder="Enter Monthly Entry Limit" className="form-control" />
                                {errors.monthlyEntry && <FormError message="Please enter monthly entry limit." />}
                            </FormGroup>
                        </div>
                        <div className="mb-3 form-check">
                            <input {...register('active')} type="checkbox" className="form-check-input" id="status" />
                            <label htmlFor="status" className="form-check-label">{watch('active') ? "Active" : "Pending"}</label>
                        </div>

                        <div>
                            <button type="submit" className="btn btn-dark">
                                <i className="bi-save"></i> Save Subscription Plan
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </Page>
    )
}