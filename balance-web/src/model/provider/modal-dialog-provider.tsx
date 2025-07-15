import React, { useState } from "react";
import { ModalDialogContext } from "./modal-dialog-context";

export default function ModalDialogProvider({children} : {children : React.ReactNode}) {
    const [show, setShow] = useState(false)
    const [action, setAction] = useState<VoidFunction | undefined>()

    return (
        <ModalDialogContext.Provider value={{
            show : show, 
            setShow : setShow,
            action: action,
            setAction: setAction
        }}>
            {children}
        </ModalDialogContext.Provider>
    )
}