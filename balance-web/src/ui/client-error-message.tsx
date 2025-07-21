import { useEffect, useRef, useState } from "react"
import { clientErrorStore } from "../model/store/client-error.store"
import { useNavigate } from "react-router"
import type { ClientError } from "../model/dto"
import type { Bootstrap, Modal } from "./modal-dialog"

declare const bootstrap:Bootstrap

export default function ClientErrorMessage({anonymous} : {anonymous : boolean}) {

    const {error, setError} = clientErrorStore()
    const navigate = useNavigate()

    const [clientError, setClientError] = useState<ClientError>()

    const dialogRef = useRef<HTMLDivElement | null>(null)
    const modalRef = useRef<Modal | undefined>(undefined)
    const [show, setShow] = useState<boolean>(false)

    useEffect(() => {
        if(dialogRef.current) {
            modalRef.current = new bootstrap.Modal(dialogRef.current, {backdrop : 'static'})
        }
    }, [dialogRef, modalRef])

    useEffect(() => {
        if(modalRef.current) {
            if(show) {
                modalRef.current?.show()
            } else {
                modalRef.current?.hide()
            }
        }
    }, [show])

    useEffect(() => {
        if(error) {
            const status = error.status

            if(!anonymous && (status === 401 || status === 408)) {
                navigate('/')
            }

            const response = error.response

            if(response) {
                const messages = response.data as ClientError
                setClientError(messages)
                setShow(true)
            }
        } 
    }, [error, anonymous, navigate])

    function closeDialog() {
        setError(undefined)
        setShow(false)
    }

    return (
        <div ref={dialogRef} className="modal fade">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <div className="modal-title"><i className="bi-info-circle"></i> Error Messages</div>
                    </div>

                    <div className="modal-body">
                        <ul className="list-group list-group-flush">
                            {clientError && clientError.messages.map(error => 
                            <li className="list-group-item">{error.message}</li>
                            )}
                        </ul>    
                    </div>

                    <div className="modal-footer">
                        <button onClick={closeDialog} className="btn btn-secondary">
                            <i className="bi-check"></i> Close
                        </button>
                    </div>
                </div>
            </div>
        </div>
    )
}