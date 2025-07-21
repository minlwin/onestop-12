import { useForm } from "react-hook-form";
import type { MemberListItem, MemberSearch } from "../../../model/dto/management/member";
import Page from "../../../ui/page";
import FormGroup from "../../../ui/form-group";
import NoData from "../../../ui/no-data";
import { useEffect, useRef, useState } from "react";
import type { PageResult } from "../../../model/client/_instance";
import { searchMember } from "../../../model/client/management/member-client";
import Pagination from "../../../ui/pagination";
import { useManagementPlan } from "../../../model/provider/management-plan-context";

export default function MemberManagement() {

    const [result, setResult] = useState<PageResult<MemberListItem>>({contents: []})
    const [selectedPage, setSelectedPage] = useState(0)
    const [selectedSize, setSelectedSize] = useState(10)

    const {contents, pager} = result

    useEffect(() => {
        setSelectedPage(0)
    }, [selectedSize, setSelectedPage])

    async function search(form:MemberSearch) {
        const response = await searchMember(form)
        if(response) {
            setResult(response)
        }
    }

    return (
        <Page title="Member Management" icon={<i className="bi-people"></i>}>
            <SearchForm selectedPage={selectedPage} selectedSize={selectedSize} search={search} />

            <section className="my-3">
                <ListView list={contents} />
            </section> 

            <Pagination pager={pager} pageChange={setSelectedPage} sizeChange={setSelectedSize} />
        </Page>
    )
}

function SearchForm({search, selectedPage, selectedSize} : {search:(form:MemberSearch) => void, selectedPage : number, selectedSize : number}) {

    const {handleSubmit, register, reset} = useForm<MemberSearch>()
    const searchForm = useRef<HTMLFormElement | null>(null)
    const {plans} = useManagementPlan()

    useEffect(() => {
        if(searchForm.current) {
            reset({page: selectedPage, size : selectedSize})
            searchForm.current.requestSubmit()
        }
    }, [selectedPage, selectedSize, searchForm, reset])

    return (
        <form ref={searchForm} onSubmit={handleSubmit(search)} className="row">
            <FormGroup label="Plan" className="col-auto">
                <select {...register('planId')} className="form-select">
                    <option value="">Search All</option>
                    {plans.map(item => 
                        <option key={item.id} value={item.id}>{item.name}</option>
                    )}
                </select>
            </FormGroup>

            <FormGroup label="Expire From" className="col-auto">
                <input {...register('expiredFrom')} type="date" className="form-control"  />
            </FormGroup>

            <FormGroup label="Expire To" className="col-auto">
                <input {...register('expiredTo')} type="date" className="form-control"  />
            </FormGroup>

            <FormGroup label="Keyword" className="col-auto">
                <input {...register('keyword')}  className="form-control" placeholder="Search Keyword"  />
            </FormGroup>

            <div className="col btn-wrapper">
                <button type="submit" className="btn btn-dark">
                    <i className="bi-search"></i> Search
                </button>
            </div>
        </form>
    )
}

function ListView({list} : {list : MemberListItem[]}) {

    if(!list.length) {
        return (
            <NoData name="Member" />
        )
    }
    return (
        <table className="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Plan</th>
                    <th>Expire At</th>
                    <th>Phone</th>
                    <th>Email</th>
                </tr>
            </thead>
            <tbody>
            {list.map(item => 
                <tr key={item.id}>
                    <td>{item.id}</td>
                    <td>{item.name}</td>
                    <td>{item.planName}</td>
                    <td>{item.expiredAt}</td>
                    <td>{item.phone}</td>
                    <td>{item.email}</td>
                </tr>
            )}
            </tbody>
        </table>
    )
}