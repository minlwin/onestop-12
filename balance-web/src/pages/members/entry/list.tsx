import { useParams } from "react-router";
import Page from "../../../ui/page";
import { useEffect, useRef, useState } from "react";
import type { LedgerType } from "../../../model/constants";
import Loading from "../../../ui/loading";
import Pagination from "../../../ui/pagination";
import { useForm } from "react-hook-form";
import type { LedgerEntryListItem, LedgerEntrySearch } from "../../../model/dto/member/ledger-entry";
import type { PageResult } from "../../../model/client/_instance";
import { searchEntry } from "../../../model/client/member/ledger-entry-client";

export default function LedgerEntryManagement() {

    const params = useParams()
    const [ledgerType, setLedgerType] = useState<LedgerType | undefined>(undefined)
    const [page, setPage] = useState(0)
    const [size, setSize] = useState(10)
    const [result, setResult] = useState<PageResult<LedgerEntryListItem>>({contents: []})
    const {contents, pager} = result

    useEffect(() => {
        const type = params.type 
        setLedgerType(type == 'Credit'.toLocaleLowerCase() ? 'Credit' : 'Debit')
    }, [params])

    if(!ledgerType) {
        return <Loading />
    }

    const icon = ledgerType === 'Credit' ? <i className="bi-bag-plus"></i> : <i className="bi-bag-dash"></i>

    async function search(form:LedgerEntrySearch) {
        const response = await searchEntry(form)
        setResult(response)
    }

    return (
        <Page icon={icon} title={`${ledgerType} Management`}>
            <SearchForm page={page} size={size} type={ledgerType} onSearch={search} />

            <section className="my-3">
                <ListView list={contents} />
            </section>

            <Pagination pageChange={a => setPage(a)} sizeChange={a => setSize(a)} pager={pager} />
        </Page>
    )
}

function SearchForm({type, page, size, onSearch} : {type: LedgerType, page : number, size : number, onSearch : (form:LedgerEntrySearch) => void}) {

    const formRef = useRef<HTMLFormElement | null>(null)
    const {reset, handleSubmit} = useForm<LedgerEntrySearch>()

    useEffect(() => {
        reset({
            page: page,
            type: type
        })

        formRef.current?.requestSubmit()
    }, [page, type, reset])

    useEffect(() => {
        reset({
            size: size,
            page: 0,
            type: type
        })
        formRef.current?.requestSubmit()
    }, [size, type, reset])

    return (
        <form ref={formRef} onSubmit={handleSubmit(onSearch)}>
            
        </form>
    )
}

function ListView({list} : {list : LedgerEntryListItem[]}) {
    return (
        list.length &&
        <></>
    )
}