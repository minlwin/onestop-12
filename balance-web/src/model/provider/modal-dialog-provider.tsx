import React, { useState } from "react";
import { ModalDialogContext } from "./modal-dialog-context";

export default function ModalDialogProvider({children} : {children : React.ReactNode}) {
    const [show, setShow] = useState(false)

    return (
        <ModalDialogContext.Provider value={{
            show : show, 
            setShow : setShow
        }}>
            {children}
        </ModalDialogContext.Provider>
    )
}