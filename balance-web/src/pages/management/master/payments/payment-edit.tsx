import { useNavigate, useSearchParams } from "react-router";
import Page from "../../../../ui/page";
import FormGroup from "../../../../ui/form-group";
import type { PaymentMethodForm } from "../../../../model/dto";
import { createPaymentMethod, findPaymentMethod, updatePaymentMethod } from "../../../../model/client/management-paymentmethod-client";
import { useForm } from "react-hook-form";
import FormError from "../../../../ui/form-error";
import { useEffect } from "react";

export default function EditPaymentMethod() {

    const [params] = useSearchParams()
    const paymentId = params.get("paymentId")
    const navigate = useNavigate()

    const {handleSubmit, register, watch, reset, formState : {errors}} = useForm<PaymentMethodForm>()

    useEffect(() => {

        async function load(id: unknown) {
            const response = await findPaymentMethod(id)
            reset({
                name: response.name,
                accountNo: response.accountNo,
                accountName: response.accountName,
                active: response.active
            })
        }

        if(paymentId) {
            load(paymentId)
        }

    }, [paymentId, reset])

    async function save(form:PaymentMethodForm) {
        const response = paymentId ? await updatePaymentMethod(paymentId, form) : await createPaymentMethod(form)
        navigate(`/admin/master/payment/${response.id}`)
    }

    return (
        <Page icon={<i className="bi-pencil"></i>} title={`${paymentId ? 'Edit' : 'Create'} Payment Method`}>

            <div className="row">
                <div className="col-4">
                    <form onSubmit={handleSubmit(save)}>
                        <FormGroup label="Payment Name" className="mb-3">
                            <input type="text" placeholder="Enter Payment Method Name" className="form-control" {
                                ...register('name', {required : true})
                            } />
                            {errors.name && <FormError message="Please enter Payment Method Name." />}
                        </FormGroup>

                        <FormGroup label="Account No" className="mb-3">
                            <input type="text" placeholder="Enter Account Number" className="form-control" {
                                ...register('accountNo', {required : true})
                            } />
                            {errors.accountNo && <FormError message="Please enter account no." />}
                        </FormGroup>

                        <FormGroup label="Account Name" className="mb-3">
                            <input type="text" placeholder="Enter Account Name" className="form-control" {
                                ...register('accountName', {required : true})
                            } />
                            {errors.accountName && <FormError message="Please enter account name." />}
                        </FormGroup>

                        <div className="form-check mb-3">
                            <input id="active" type="checkbox" className="form-check-input" {...register('active')} />
                            <label htmlFor="active" className="form-check-label">{watch('active') ? "Active" : "Pending"}</label>
                        </div>

                        <div>
                            <button type="submit" className="btn btn-dark">
                                <i className="bi-save"></i> Save Payment Method
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </Page>
    )
}