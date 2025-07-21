import { useFieldArray, useForm, useWatch } from "react-hook-form";
import Page from "../../../ui/page";
import type { LedgerEntryForm, LedgerEntryItem } from "../../../model/dto/member/ledger-entry";
import FormGroup from "../../../ui/form-group";
import { useNavigate, useParams, useSearchParams } from "react-router";
import { useMemberLedgerContext } from "../../../model/provider/member-ledger-context";
import FormError from "../../../ui/form-error";
import { createEntry, findEntryById, updateEntry } from "../../../model/client/member/ledger-entry-client";
import { useEffect } from "react";

const BLANK_ITEM:Readonly<LedgerEntryItem> = {item: '', remark: '', quantity: 0, unitPrice: 0}

const getTotal = (item: LedgerEntryItem) => {
    const total = item.unitPrice * item.quantity
    return total ? total : 0
}

const getAllTotal = (items : LedgerEntryItem[]) => items.map(getTotal).reduce((a, b) => a + b)

export default function LedgerEntryEdit() {

    const params = useParams()
    const [query] = useSearchParams()
    const navigate = useNavigate()

    const ledgerType = params.type == 'credit' ? 'Credit' : 'Debit'
    const {ledgers} = useMemberLedgerContext()
    const ledgerOptions = ledgers.filter(a => a.type == ledgerType)

    const {handleSubmit, control, register, reset, formState : {errors}} = useForm<LedgerEntryForm>({
        defaultValues: {
            code: '',
            particular: '',
            items: [
                {...BLANK_ITEM}
            ]
        }
    })
    
    const {fields, append, remove} = useFieldArray({
        control: control,
        name: 'items'
    })

    useEffect(() => {
        async function load(requestId:unknown) {
            const entry = await findEntryById(requestId)

            if(entry) {
                const {id, particular, items} = entry

                reset({
                    code: id.code,
                    particular: particular,
                    items : items
                })
            }
        }

        const id = query.get("id")
        if(id) {
            load(id)
        }
    }, [query, reset])

    async function save(form: LedgerEntryForm) {
        const respose = query.get("id") ? await updateEntry(query.get("id"), form) : await createEntry(form)
        if(respose && respose.success && respose.id) {
            navigate(`/member/balance/${respose.id.requestId}`)
        }
    }

    function addItem() {
        append({...BLANK_ITEM})
    }

    function removeItem(index : number) {
        remove(index)

        if(fields.length == 1) {
            addItem()
        }
    }

    const itemArray = useWatch({control : control, name : 'items'})

    return (
        <Page title={`${query.get("id") ? "Edit" : "Create"} ${ledgerType} Entry`} icon={<i className="bi-pencil"></i>} actions={
            <div className="row" style={{width : 320}}>
                <div className="col">
                    <div className="input-group">
                        <span className="input-group-text">Total Amount</span>
                        <span className="form-control text-end">{getAllTotal(itemArray)}</span>
                    </div>
                </div>             
            </div>
        }>

            <form onSubmit={handleSubmit(save)}>
                <div className="row mb-3">
                    <FormGroup className="col-5" label="Ledger">
                        <select {...register('code', {required : true})} className="form-select">
                            <option value="">Select Ledger</option>
                            {ledgerOptions.map(item => 
                                <option key={item.id.code} value={item.id.code}>{item.name}</option>
                            )}
                        </select>
                        {errors.code && <FormError message="Please select ledger." />}
                    </FormGroup>

                    <FormGroup label="Particular" className="col">
                        <input {...register('particular', {required : true})} placeholder="Enter Praticular" className="form-control" />
                        {errors.particular && <FormError message="Please enter particular message." />}
                    </FormGroup>
                </div>

                <h5>Entry Items</h5>

                {fields.map((item, index) => 
                    <div className="row mb-2" key={item.id}>
                        <FormGroup label={`${index ? '' : 'Item'}`} className="col">
                            <input {...register(`items.${index}.item`, {required : true})} placeholder="Enter Item Name" className="form-control" />
                            {errors.items && errors.items[index]?.item && <FormError message="Item name is required." />}
                        </FormGroup>
                        <FormGroup label={`${index ? '' : 'Unit Price'}`} className="col-2">
                            <input {...register(`items.${index}.unitPrice`, {required: true, min: 0})} type="number" className="form-control text-end" />
                            {errors.items && errors.items[index]?.unitPrice && <FormError message="Invalid Unit Price." />}
                        </FormGroup>
                        <FormGroup label={`${index ? '' : 'Quantity'}`} className="col-2">
                            <input {...register(`items.${index}.quantity`, {required: true, min: 0})} type="number" className="form-control text-end" />
                            {errors.items && errors.items[index]?.quantity && <FormError message="Invalid Quantity." />}
                        </FormGroup>
                        <FormGroup label={`${index ? '' : 'Total'}`} className="col-2">
                            <span className="form-control text-end">{getTotal(itemArray[index])}</span>
                        </FormGroup>
                        <FormGroup label={`${index ? '' : 'Remark'}`} className="col">
                            <div className="input-group">
                                <input {...register(`items.${index}.remark`)} type="text" placeholder="Enter Remark" className="form-control" />
                                <button type="button" onClick={() => removeItem(index)} className="btn btn-outline-secondary">
                                    <i className="bi-trash"></i>
                                </button>
                            </div> 
                        </FormGroup>
                    </div>
                )}

                <div className="text-end pt-2">
                    <button type="button" onClick={addItem} className="btn btn-outline-secondary">
                        <i className="bi-plus"></i> Add Item
                    </button>

                    <button type="submit" className="btn btn-secondary ms-2">
                        <i className="bi-save"></i> {`Save ${ledgerType} Entry`}
                    </button>
                </div>
            </form>
            
        </Page>
    )
}