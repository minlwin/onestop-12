import { useForm } from "react-hook-form";
import Page from "../../../ui/page";
import type { LedgerForm, LedgerListItem, LedgerSearch } from "../../../model/dto/member/ledger";
import FormGroup from "../../../ui/form-group";
import { LEDGER_TYPE_LIST } from "../../../model/constants";
import { useEffect, useRef, useState } from "react";
import { searchLedger } from "../../../model/client/member/ledger-client";
import NoData from "../../../ui/no-data";
import ModalDialogProvider from "../../../model/provider/modal-dialog-provider";
import { useModalDialogContext } from "../../../model/provider/modal-dialog-context";
import LedgerEditDialog from "./ledger-edit-dialog";

const BLANK_FORM:LedgerForm = {
    code : '',
    type : '',
    name: '',
    description : ''
}

export default function LedgerManagement() {

    const [list, setList] = useState<LedgerListItem[]>([])
    const [editData, setEditData] = useState<LedgerForm>(BLANK_FORM)

    const searchParamRef = useRef<LedgerSearch>({})

    useEffect(() => {       
        async function load() {
            const response = await searchLedger(searchParamRef.current)
            if(response) {
                setList(response)
            }
        }

        load()
    }, [searchParamRef])

    async function search(form:LedgerSearch) {
        searchParamRef.current = form
        const response = await searchLedger(form)
        if(response) {
            setList(response)
        }
    }

    async function refreshList() {
        // Refresh List View
        // Fetch API
        const response = await searchLedger(searchParamRef.current)
        
        if(response) {
            // Set Result List
            setList(response)

            // Clear Edit Data
            setEditData({...BLANK_FORM})
        }
    }

    function setForEdit(item : LedgerListItem) {
        setEditData({
            code : item.id.code,
            name : item.name,
            type : item.type,
            description : item.description
        })
    }

    return (
        <Page title="Ledger Management" icon={<i className="bi-tags"></i>}>
            <ModalDialogProvider>
                <SearchForm setEditData={setEditData} onSearch={search} />

                <section className="my-3">
                    <ListView setEditData={setForEdit} list={list} />
                </section>

                <LedgerEditDialog editData={editData} refreshList={refreshList} />
            </ModalDialogProvider>
        </Page>
    )
}

function SearchForm({onSearch, setEditData} : {
    onSearch : (form : LedgerSearch) => void,
    setEditData : (form : LedgerForm) => void
}) {

    const {handleSubmit, register} = useForm<LedgerSearch>()
    const {setShow} = useModalDialogContext()

    function addNew() {
        setEditData({...BLANK_FORM})
        setShow(true)
    }

    return (
        <form onSubmit={handleSubmit(onSearch)} className="row">
            <FormGroup className="col-auto" label="Ledger Type">
                <select {...register('type')} className="form-select">
                    <option value="">Search All</option>
                    {LEDGER_TYPE_LIST.map(item => 
                        <option key={item} value={item}>{item}</option>
                    )}
                </select>
            </FormGroup>

            <FormGroup label="Keyword" className="col-auto">
                <input {...register('keyword')} placeholder="Search Keyword" type="text" className="form-control" />
            </FormGroup>

            <div className="col btn-wrapper">
                <button type="submit" className="btn btn-secondary">
                    <i className="bi-search"></i> Search
                </button>

                <button onClick={addNew} type="button" className="btn btn-outline-secondary ms-2">
                    <i className="bi-plus"></i> Add New
                </button>
            </div>
        </form>
    )  
}

function ListView({list, setEditData} : {list: LedgerListItem[], setEditData: (data: LedgerListItem) => void },) {

    const {setShow} = useModalDialogContext()

    if(!list.length) {
        return <NoData name="Ledger" />
    }

    return (
        <table className="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Code</th>
                    <th>Type</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Entries</th>
                    <th>Amount</th>
                    <th></th>
                </tr>
            </thead>

            <tbody>
            {list.map(item => 
                <tr key={item.id.code}>
                    <td>{item.id.code}</td>
                    <td>{item.type}</td>
                    <td>{item.name}</td>
                    <td>{item.description}</td>
                    <td className="text-end">{item.entries}</td>
                    <td className="text-end">{item.total}</td>
                    <td className="text-center">
                        <a href="#" onClick={(e) => {
                            e.preventDefault()
                            setEditData(item)
                            setShow(true)
                        }} className="icon-link">
                            <i className="bi-pencil"></i>
                        </a>
                    </td>
                </tr>
            )}
            </tbody>
        </table>
    )
}
