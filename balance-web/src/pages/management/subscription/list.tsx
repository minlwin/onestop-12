import { useEffect, useRef, useState } from "react";
import Page from "../../../ui/page";
import Pagination from "../../../ui/pagination";
import type { SubscriptionListItem, SubscriptionSearch } from "../../../model/dto/management/subscription";
import { searchSubscription } from "../../../model/client/management/subscription-client";
import { useForm } from "react-hook-form";
import NoData from "../../../ui/no-data";
import FormGroup from "../../../ui/form-group";
import { useManagementPlan } from "../../../model/provider/management-plan-context";
import { SUBSCRIPTION_STATUS_LIST, type SubscriptionStatus } from "../../../model/constants";
import { Link } from "react-router";
import type { PageResult } from "../../../model/dto";

export default function Subscriptions() {

    const [page, setPage] = useState(0)
    const [size, setSize] = useState(10)
    const [result, setResult] = useState<PageResult<SubscriptionListItem>>({contents : []})
    const {contents, pager} = result

    async function search(form:SubscriptionSearch) {
        const response = await searchSubscription(form)
        if(response) {
            setResult(response)
        }
    }

    return (
        <Page icon={<i className="bi-cart-plus"></i>} title="Subscription Management">
            <SearchForm page={page} size={size} onSearch={search} />

            <section className="mb-3">
                <ListView list={contents} />
            </section>

            <Pagination pageChange={setPage} sizeChange={setSize} pager={pager} />
        </Page>
    )
}

function SearchForm({page = 0, size = 10, onSearch} : {page: number, size: number, onSearch : (form:SubscriptionSearch) => void}) {
    
    const {handleSubmit, reset, register, getValues} = useForm<SubscriptionSearch>({
        defaultValues: {status : 'Pending'}
    })
    const searchForm = useRef<HTMLFormElement | null>(null)
    const {plans} = useManagementPlan()
    const [status, setStatus] = useState<SubscriptionStatus>('Pending')

    useEffect(() => {
        if(searchForm.current) {
            reset({... getValues(),page : page, size: size})
            searchForm.current.requestSubmit()
        }
    }, [page, size, reset,  getValues])

    useEffect(() => {
        reset({...getValues(), status : status})
        searchForm.current?.requestSubmit()
    }, [status, reset, getValues])


    return (
        <>
            <ul className="nav nav-tabs">
                {SUBSCRIPTION_STATUS_LIST.map(item => 
                    <li key={item} className="nav-item">
                        <a href="#" onClick={e => {
                            e.preventDefault()
                            setStatus(item)
                        }} className={`nav-link ${status == item && 'active'}`}>{item}</a>
                    </li>
                )}
            </ul>
            <form ref={searchForm} onSubmit={handleSubmit(onSearch)} className="row p-4">
                <FormGroup label="Plan" className="col-auto">
                    <select {...register('planId')} className="form-select">
                        <option value="">Search All</option>
                        {plans.map(item => 
                            <option key={item.id} value={item.id}>{item.name}</option>
                        )}
                    </select>
                </FormGroup>

                <FormGroup label="Applied From" className="col-auto">
                    <input {...register('appliedFrom')} type="date" className="form-control" />
                </FormGroup>

                <FormGroup label="Applied To" className="col-auto">
                    <input {...register('appliedTo')} type="date" className="form-control" />
                </FormGroup>

                <FormGroup label="Keyword" className="col-auto">
                    <input {...register('keyword')} placeholder="Search Keyword" className="form-control" />
                </FormGroup>

                <div className="col btn-wrapper">
                    <button type="submit" className="btn btn-dark">
                        <i className="bi-search"></i> Search
                    </button>
                </div>
            </form>
        </>
    )
}

function ListView({list} : {list: SubscriptionListItem[]}) {
    
    if(!list.length) {
        return (
            <NoData name="Subscription" />
        )
    }

    return (
        <table className="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Member</th>
                    <th>Plan</th>
                    <th>Extension</th>
                    <th>Status</th>
                    <th>Previous Plan</th>
                    <th>Expire At</th>
                    <th>Paymet Name</th>
                    <th className="text-end">Fees</th>
                    <th></th>
                </tr>
            </thead>

            <tbody>
            {list.map(item => 
                <tr key={item.id.code}>
                    <td>{item.memberName}</td>
                    <td>{item.planName}</td>
                    <td>{item.usage}</td>
                    <td>{item.status}</td>
                    <td>{item.previousPlan}</td>
                    <td>{item.expiredAt}</td>
                    <td>{item.paymentName}</td>
                    <td className="text-end">{item.paymentAmount}</td>
                    <td className="text-center">
                        <Link className="icon-link" to={`/admin/subscriptions/${item.id.code}`}>
                            <i className="bi-arrow-right"></i>
                        </Link>
                    </td>
                </tr>
            )}    
            </tbody>
        </table>
    )
}