import { useForm } from "react-hook-form";
import Page from "../../../ui/page";
import type { LedgerForm, LedgerListItem, LedgerSearch } from "../../../model/dto/member/ledger";
import FormGroup from "../../../ui/form-group";
import { LEDGER_TYPE_LIST } from "../../../model/constants";
import { useEffect, useRef, useState } from "react";
import { createLedger, searchLedger, updateLedger } from "../../../model/client/member/ledger-client";
import NoData from "../../../ui/no-data";
import ModalDialog from "../../../ui/modal-dialog";
import ModalDialogProvider from "../../../model/provider/modal-dialog-provider";
import { useModalDialogContext } from "../../../model/provider/modal-dialog-context";
import FormError from "../../../ui/form-error";

export default function LedgerManagement() {

    const [list, setList] = useState<LedgerListItem[]>([])
    const [editData, setEditData] = useState<LedgerListItem>()

    const searchParamRef = useRef<LedgerSearch>({})

    useEffect(() => {       
        async function load() {
            const response = await searchLedger(searchParamRef.current)
            setList(response)
        }

        load()
    }, [searchParamRef])

    async function search(form:LedgerSearch) {
        searchParamRef.current = form
        const response = await searchLedger(form)
        setList(response)
    }

    async function refreshList() {
        // Refresh List View
        const response = await searchLedger(searchParamRef.current)
        setList(response)

        // Clear Edit Data
        setEditData(undefined)
    }

    return (
        <Page title="Ledger Management" icon={<i className="bi-tags"></i>}>
            <ModalDialogProvider>
                <SearchForm onSearch={search} />

                <section className="my-3">
                    <ListView setEditData={setEditData} list={list} />
                </section>

                <EditDialog editData={editData} refreshList={refreshList} />
            </ModalDialogProvider>
        </Page>
    )
}

function SearchForm({onSearch} : {onSearch : (form : LedgerSearch) => void}) {

    const {handleSubmit, register} = useForm<LedgerSearch>()
    const {setShow} = useModalDialogContext()

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

                <button onClick={() => setShow(true)} type="button" className="btn btn-outline-secondary ms-2">
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

function EditDialog({editData, refreshList} : {editData?: LedgerListItem,refreshList : VoidFunction}) {
    const {handleSubmit, register, reset,formState: {errors}} = useForm<LedgerForm>()
    const formRef = useRef<HTMLFormElement | null>(null)
    const {setShow} = useModalDialogContext()

    useEffect(() => {
        reset({
            code : editData?.id.code,
            type : editData?.type,
            name: editData?.name,
            description: editData?.description
        })
    }, [editData, reset])

    async function saveLedger(form : LedgerForm) {
        // Create Ledger
        if(editData) {
            await updateLedger(form)
        } else {
            await createLedger(form)
        }

        // hide dialog
        setShow(false)

        // Clear Form
        reset(undefined)

        // Refresh List
        refreshList()
    }

    return (
        <ModalDialog title={editData ? 'Edit Ledger' : 'Create Ledger'} 
            action={() => formRef.current?.requestSubmit()}>
            <form ref={formRef} onSubmit={handleSubmit(saveLedger)}>
                <div className="row mb-3">
                    <FormGroup label="Type" className="col-auto">
                        <select {...register('type', {required : true})} className="form-select">
                            <option value="">Select Type</option>
                            {LEDGER_TYPE_LIST.map(item => 
                                <option key={item} value={item}>{item}</option>
                            )}
                        </select>
                        {errors.type && <FormError message="Please select type." />}
                    </FormGroup>

                    <FormGroup label="Code" className="col">
                        <input {...register('code', {required : true})} type="text" placeholder="Ledger Code" className="form-control" />
                        {errors.code && <FormError message="Please enter code." />}
                    </FormGroup>
                </div>
                
                <FormGroup label="Name" className="mb-3">
                    <input {...register('name', {required : true})} type="text" className="form-control" placeholder="Ledger Name" />
                    {errors.name && <FormError message="Please enter ledger name." />}
                </FormGroup>

                <FormGroup label="Description">
                    <textarea {...register('description')} className="form-control"></textarea>
                </FormGroup>
            </form>
        </ModalDialog>
    )
}