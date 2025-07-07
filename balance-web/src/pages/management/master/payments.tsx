import { useForm } from "react-hook-form";
import type { PaymentMethodListItem, PaymentMethodSearch } from "../../../model/dto";
import Page from "../../../ui/page";
import { useState } from "react";
import { searchPaymentMethod } from "../../../model/client/management-client";
import FormGroup from "../../../ui/form-group";

export default function PaymentMethods() {

    const {handleSubmit, register} = useForm<PaymentMethodSearch>()
    const [list, setList] = useState<PaymentMethodListItem[]>([])

    async function search(form:PaymentMethodSearch) {
        const response = await searchPaymentMethod(form)
        setList(response)
    }

    return (
        <Page title="Payment Method Management" icon={
            <i className="bi-credit-card"></i>
        }>

            <form onSubmit={handleSubmit(search)} className="row">
                <FormGroup label="State" className="col-auto">
                    <select className="form-select" {...register('active')}>
                        <option value="">All State</option>
                        <option value="true">Active</option>
                        <option value="false">Pending</option>
                    </select>
                </FormGroup>

                <FormGroup label="Keyword" className="col-auto">
                    <input type="text" placeholder="Search Keyword" className="form-control" {...register('keyword')} />
                </FormGroup>

                <div className="col btn-wrapper">
                    <button type="submit" className="btn btn-outline-dark">
                        <i className="bi-search"></i> Search
                    </button>
                </div>
            </form>

            <table className="table table-bordered table-striped table-hover mt-3">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Account Number</th>
                        <th>Account Name</th>
                        <th>State</th>
                        <th>Payments</th>
                    </tr>
                </thead>
                <tbody>
                    {list.map(item => 
                        <tr key={item.id}>
                            <td>{item.id}</td>
                            <td>{item.name}</td>
                            <td>{item.accountNo}</td>
                            <td>{item.accountName}</td>
                            <td>
                                <i className={item.active ? 'bi-check' : 'bi-x'}></i>
                            </td>
                            <td>{item.payments}</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </Page>
    )
}