import { useFieldArray, useForm } from "react-hook-form";
import Page from "../../../ui/page";
import type { LedgerEntryForm, LedgerEntryItem } from "../../../model/dto/member/ledger-entry";
import FormGroup from "../../../ui/form-group";
import { useParams } from "react-router";
import { useMemberLedgerContext } from "../../../model/provider/member-ledger-context";
import FormError from "../../../ui/form-error";

const BLANK_ITEM:Readonly<LedgerEntryItem> = {item: '', remark: '', quantity: 0, unitPrice: 0}

export default function LedgerEntryEdit() {

    const params = useParams()
    const ledgerType = params.type == 'credit' ? 'Credit' : 'Debit'
    const {ledgers} = useMemberLedgerContext()
    const ledgerOptions = ledgers.filter(a => a.type == ledgerType)

    const {handleSubmit, control, register, formState : {errors}} = useForm<LedgerEntryForm>({
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

    async function save(form: LedgerEntryForm) {
        console.log(form)
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

    return (
        <Page title={`Create ${ledgerType} Entry`} icon={<i className="bi-pencil"></i>} actions={
            <div className="row" style={{width : 320}}>
                <div className="col">
                    <div className="input-group">
                        <span className="input-group-text">Total Amount</span>
                        <span className="form-control text-end">0</span>
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
                            <input {...register(`items.${index}.item`)} placeholder="Enter Item Name" className="form-control" />
                        </FormGroup>
                        <FormGroup label={`${index ? '' : 'Unit Price'}`} className="col-2">
                            <input {...register(`items.${index}.unitPrice`)} type="number" className="form-control text-end" />
                        </FormGroup>
                        <FormGroup label={`${index ? '' : 'Quantity'}`} className="col-2">
                            <input {...register(`items.${index}.quantity`)} type="number" className="form-control text-end" />
                        </FormGroup>
                        <FormGroup label={`${index ? '' : 'Total'}`} className="col-2">
                            <span className="form-control text-end">0</span>
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