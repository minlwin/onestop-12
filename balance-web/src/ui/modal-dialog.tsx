import type React from "react"
import { useEffect, useRef } from "react"
import { useModalDialogContext } from "../model/provider/modal-dialog-context"

export type Modal = {
    show : () => void
    hide : () => void
}

export type Bootstrap = {
    Modal : new (element: HTMLDivElement, options : {backdrop : boolean | 'static'}) => Modal
}

declare const bootstrap:Bootstrap

export default function ModalDialog({title, action, children} : {title : string, action?:VoidFunction, closeAction?:VoidFunction  ,children : React.ReactNode}) {
    
    const dialogRef = useRef<HTMLDivElement | null>(null)
    const modalRef = useRef<Modal | undefined>(undefined)
    const {show, setShow} = useModalDialogContext()

    useEffect(() => {
        if(dialogRef.current) {
            const modal = new bootstrap.Modal(dialogRef.current, {backdrop : 'static'})
            modalRef.current = modal
        }
    }, [dialogRef, modalRef])

    useEffect(() => {
        if(modalRef.current) {
            if(show) {
                modalRef.current.show()
            } else {
                modalRef.current.hide()
            }
        }
    }, [show, modalRef])
    
    return (
        <div ref={dialogRef} className="modal fade">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">{title}</h5>
                    </div>

                    <div className="modal-body">
                        {children}
                    </div>

                    <div className="modal-footer">
                        <button onClick={() => setShow(false)} type="button" className="btn btn-outline-dark">
                            <i className="bi-x"></i> Close
                        </button>

                        {action && 
                            <button onClick={action} className="btn btn-dark ms-2" type="button">
                                <i className="bi-save"></i> Save
                            </button>
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}