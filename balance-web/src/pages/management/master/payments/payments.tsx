import { useForm } from "react-hook-form";
import type { PaymentMethodListItem, PaymentMethodSearch } from "../../../../model/dto";
import Page from "../../../../ui/page";
import { useEffect, useState } from "react";
import { searchPaymentMethod } from "../../../../model/client/management-paymentmethod-client";
import FormGroup from "../../../../ui/form-group";
import { Link } from "react-router";
import NoData from "../../../../ui/no-data";

export default function PaymentMethods() {

    const {handleSubmit, register} = useForm<PaymentMethodSearch>()
    const [list, setList] = useState<PaymentMethodListItem[]>([])

    async function search(form:PaymentMethodSearch) {
        const response = await searchPaymentMethod(form)
        setList(response)
    }

    useEffect(() => {
        search({})
    }, [])

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
                    <button type="submit" className="btn btn-dark">
                        <i className="bi-search"></i> Search
                    </button>

                    <Link to="edit" className="btn btn-outline-dark ms-2">
                        <i className="bi-plus"></i> Create Payment
                    </Link>
                </div>
            </form>

            <section className="mt-3">
            {list.length ? 
                <table className="table table-bordered table-striped table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Account Number</th>
                            <th>Account Name</th>
                            <th>State</th>
                            <th>Payments</th>
                            <th></th>
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
                                    {item.active ? "Active" : "Pending"}
                                </td>
                                <td>{item.payments}</td>
                                <td className="text-center">
                                    <Link to={`/admin/master/payment/${item.id}`} className="icon-link">
                                        <i className="bi-arrow-right"></i>
                                    </Link>
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>  : 
                <NoData name="Payment Method" />          
            }                
            </section>
        </Page>
    )
}