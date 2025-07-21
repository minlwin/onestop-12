import { useEffect, useRef, useState } from "react";
import Page from "../../../ui/page";
import Pagination from "../../../ui/pagination";
import type { PageResult } from "../../../model/client/_instance";
import type { SubscriptionListItem, SubscriptionSearch } from "../../../model/dto/management/subscription";
import { searchSubscription } from "../../../model/client/management/subscription-client";
import { useForm } from "react-hook-form";
import NoData from "../../../ui/no-data";
import FormGroup from "../../../ui/form-group";
import { useManagementPlan } from "../../../model/provider/management-plan-context";
import { SUBSCRIPTION_STATUS_LIST } from "../../../model/constants";
import { Link } from "react-router";

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

    useEffect(() => {
        setPage(0)
    }, [size, setPage])

    return (
        <Page icon={<i className="bi-cart-plus"></i>} title="Subscription Management">
            <SearchForm page={page} size={size} onSearch={search} />

            <section className="my-3">
                <ListView list={contents} />
            </section>

            <Pagination pageChange={setPage} sizeChange={setSize} pager={pager} />
        </Page>
    )
}

function SearchForm({page, size, onSearch} : {page: number, size: number, onSearch : (form:SubscriptionSearch) => void}) {
    
    const {handleSubmit, reset, register} = useForm<SubscriptionSearch>()
    const searchForm = useRef<HTMLFormElement | null>(null)
    const {plans} = useManagementPlan()

    useEffect(() => {
        if(searchForm.current) {
            reset({page : page, size: size})
            searchForm.current.requestSubmit()
        }
    }, [page, size, searchForm, reset])

    return (
        <form ref={searchForm} onSubmit={handleSubmit(onSearch)} className="row">
            <FormGroup label="Plan" className="col-auto">
                <select {...register('planId')} className="form-select">
                    <option value="">Search All</option>
                    {plans.map(item => 
                        <option key={item.id} value={item.id}>{item.name}</option>
                    )}
                </select>
            </FormGroup>

            <FormGroup label="Status" className="col-auto">
                <select {...register('status')} className="form-select">
                    <option value="">Search All</option>
                    {SUBSCRIPTION_STATUS_LIST.map(item => 
                        <option key={item} value={item}>{item}</option>
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