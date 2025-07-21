import { useRef, useEffect } from "react"
import { useForm } from "react-hook-form"
import { updateLedger, createLedger, searchLedger } from "../../../model/client/member/ledger-client"
import { LEDGER_TYPE_LIST } from "../../../model/constants"
import type { LedgerForm } from "../../../model/dto/member/ledger"
import { useModalDialogContext } from "../../../model/provider/modal-dialog-context"
import FormError from "../../../ui/form-error"
import FormGroup from "../../../ui/form-group"
import ModalDialog from "../../../ui/modal-dialog"
import { useMemberLedgerContext } from "../../../model/provider/member-ledger-context"

export default function LedgerEditDialog({editData, refreshList} : 
    {   
        editData: Readonly<LedgerForm>,
        refreshList : VoidFunction
    }) {
    
    const {handleSubmit, register, reset,formState: {errors}} = useForm<LedgerForm>()
    const formRef = useRef<HTMLFormElement | null>(null)
    const {setShow} = useModalDialogContext()
    const {setLedgers} = useMemberLedgerContext()

    useEffect(() => {
        reset({
            code : editData.code,
            type : editData.type,
            name: editData.name,
            description: editData.description
        })
    }, [editData, reset])

    async function saveLedger(form : LedgerForm) {
        // Create Ledger
        if(editData.code) {
            await updateLedger(form)
        } else {
            await createLedger(form)
        }

        // hide dialog
        setShow(false)

        // Load Context Data
        const ledgers = await searchLedger({})
        if(ledgers) {
            setLedgers(ledgers)

            // Refresh List
            refreshList()
        }
    }

    return (
        <ModalDialog title={editData.code ? 'Edit Ledger' : 'Create Ledger'} 
            action={() => formRef.current?.requestSubmit()}>
            <form ref={formRef} onSubmit={handleSubmit(saveLedger)}>
                <div className="row mb-3">
                    <FormGroup label="Type" className="col-auto">
                        <select disabled={editData.code !== ''} {...register('type', {required : true})} className="form-select">
                            <option value="">Select Type</option>
                            {LEDGER_TYPE_LIST.map(item => 
                                <option key={item} value={item}>{item}</option>
                            )}
                        </select>
                        {errors.type && <FormError message="Please select type." />}
                    </FormGroup>

                    <FormGroup label="Code" className="col">
                        <input readOnly={editData.code !== ''} {...register('code', {required : true})} type="text" placeholder="Ledger Code" className="form-control" />
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