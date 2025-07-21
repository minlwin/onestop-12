import { Link, useParams } from "react-router";
import Page from "../../../ui/page";
import { useEffect, useRef, useState } from "react";
import type { LedgerType } from "../../../model/constants";
import Loading from "../../../ui/loading";
import Pagination from "../../../ui/pagination";
import { useForm } from "react-hook-form";
import type { LedgerEntryListItem, LedgerEntrySearch } from "../../../model/dto/member/ledger-entry";
import type { PageResult } from "../../../model/client/_instance";
import { searchEntry } from "../../../model/client/member/ledger-entry-client";
import NoData from "../../../ui/no-data";
import FormGroup from "../../../ui/form-group";
import { useMemberLedgerContext } from "../../../model/provider/member-ledger-context";

export default function LedgerEntryManagement() {

    const params = useParams()
    const ledgerType = params.type == 'credit' ? "Credit" : "Debit"
    
    const [page, setPage] = useState(0)
    const [size, setSize] = useState(10)
    const [result, setResult] = useState<PageResult<LedgerEntryListItem>>({contents: []})
    const {contents, pager} = result

    useEffect(() => {
        setPage(0)
    }, [size, setPage])

    if(!ledgerType) {
        return <Loading />
    }

    const icon = ledgerType === 'Credit' ? <i className="bi-bag-plus"></i> : <i className="bi-bag-dash"></i>

    async function search(form:LedgerEntrySearch) {
        const response = await searchEntry(form)
        if(response) {
            setResult(response)
        }
    }

    return (
        <Page icon={icon} title={`${ledgerType} Management`}>
            <SearchForm page={page} size={size} type={ledgerType} onSearch={search} />

            <section className="my-3">
                <ListView list={contents} />
            </section>

            <Pagination pageChange={setPage} sizeChange={setSize} pager={pager} />
        </Page>
    )
}

function SearchForm({type, page, size, onSearch} : {type: LedgerType, page : number, size : number, onSearch : (form:LedgerEntrySearch) => void}) {

    const formRef = useRef<HTMLFormElement | null>(null)
    const {reset, handleSubmit, register} = useForm<LedgerEntrySearch>()
    const {ledgers} = useMemberLedgerContext()

    const ledgerOptions = ledgers.filter(a => a.type == type)

    useEffect(() => {
        if(formRef.current) {
            reset({type : type, page : page, size : size})
            formRef.current.requestSubmit()
        }
    }, [type, page, size,reset])

    return (
        <form className="row" ref={formRef} onSubmit={handleSubmit(onSearch)}>
            <FormGroup className="col-auto" label="Ledger">
                <select {...register('code')} className="form-select">
                    <option value="">Search All</option>
                    {ledgerOptions.map(item => 
                        <option key={item.id.code} value={item.id.code}>{item.name}</option>
                    )}
                </select>
            </FormGroup>

            <FormGroup className="col-auto" label="From Date">
                <input {...register('from')} type="date" className="form-control" />
            </FormGroup>

            <FormGroup className="col-auto" label="To Date">
                <input {...register('to')} type="date" className="form-control" />
            </FormGroup>

            <FormGroup className="col-auto" label="Keyword">
                <input {...register('keyword')} placeholder="Search Keyword" className="form-control" />
            </FormGroup>

            <div className="col btn-wrapper">
                <button className="btn btn-secondary" type="submit">
                    <i className="bi-search"></i> Search
                </button>

                <Link to={`/member/entry/${type.toLowerCase()}/edit`} className="btn btn-outline-secondary ms-2">
                    <i className="bi-plus"></i> Add New
                </Link>
            </div>
        </form>
    )
}

function ListView({list} : {list : LedgerEntryListItem[]}) {

    if(!list.length) {
        return (
            <NoData name="Ledger Entry" />
        )
    }

    return (
        <table className="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Issue At</th>
                    <th>Code</th>
                    <th>Ledger</th>
                    <th>Particular</th>
                    <th className="text-end">Amount</th>
                    <th></th>
                </tr>
            </thead>

            <tbody>
            {list.map(item => 
                <tr key={item.id.code}>
                    <td>{item.issueAt}</td>
                    <td>{item.id.code}</td>
                    <td>{item.ledgerName}</td>
                    <td>{item.particular}</td>
                    <td className="text-end">{item.amount.toLocaleString()}</td>
                    <td className="text-center">
                        <Link to={`/member/balance/${item.id.requestId}`} className="icon-link">
                            <i className="bi-arrow-right"></i>
                        </Link>
                    </td>
                </tr>
            )}
            </tbody>
        </table>
    )
}