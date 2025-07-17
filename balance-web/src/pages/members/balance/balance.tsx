import { useEffect, useRef, useState } from "react";
import Page from "../../../ui/page";
import Pagination from "../../../ui/pagination";
import { useForm } from "react-hook-form";
import type { BalanceReportListItem, BalanceReportSearch } from "../../../model/dto/member/balance-report";
import type { PageResult } from "../../../model/client/_instance";
import NoData from "../../../ui/no-data";
import { searchBalance } from "../../../model/client/member/balance-report-client";
import FormGroup from "../../../ui/form-group";
import { Link } from "react-router";

export default function BalanceManagement() {

    const [page, setPage] = useState(0)
    const [size, setSize] = useState(10)
    const [result, setResult] = useState<PageResult<BalanceReportListItem>>({contents : []})
    const {contents, pager} = result

    useEffect(() => {
        setPage(0)
    }, [size, setPage] )

    async function search(form: BalanceReportSearch) {
        const response = await searchBalance(form)
        setResult(response)
    }

    return (
        <Page title="Balance Management" icon={<i className="bi-pie-chart"></i>}>
            <SearchForm onSearch={search} page={page} size={size} />

            <section className="my-3">
                <ListView list={contents} />
            </section>

            <Pagination pager={pager} pageChange={setPage} sizeChange={setSize} />
        </Page>
    )
}

function SearchForm({page, size, onSearch} : {page: number, size : number, onSearch:(form:BalanceReportSearch) => void}) {

    const formRef = useRef<HTMLFormElement | null>(null)
    const {handleSubmit, reset, register} = useForm<BalanceReportSearch>()

    useEffect(() => {
        if(formRef.current) {
            reset({page : page, size : size})
            formRef.current.requestSubmit()
        }
    }, [page, size, reset])

    return (
        <form ref={formRef} onSubmit={handleSubmit(onSearch)} className="row">
            <FormGroup className="col-auto" label="Date From">
                <input {...register('from')} type="date" className="form-control" />
            </FormGroup>
            <FormGroup className="col-auto" label="Date To">
                <input {...register('to')} type="date" className="form-control" />
            </FormGroup>
            <div className="col btn-wrapper">
                <button type="submit" className="btn btn-secondary">
                    <i className="bi-search"></i> Search
                </button>
            </div>
        </form>
    )
}

function ListView({list} : {list : BalanceReportListItem[]}) {

    if(!list.length) {
        return (
            <NoData name="Balance Report" />
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
                    <th>Debit</th>
                    <th>Credit</th>
                    <th>Balance</th>
                    <th></th>
                </tr>
            </thead>

            <tbody>
            {list.map(item => 
                <tr key={item.id.requestId}>
                    <td>{item.issueAt}</td>
                    <td>{item.id.code}</td>
                    <td>{item.ledger}</td>
                    <td>{item.particular}</td>
                    <td className="text-end">{item.debit}</td>
                    <td className="text-end">{item.credit}</td>
                    <td className="text-end">{item.balance}</td>
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