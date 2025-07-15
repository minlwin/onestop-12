import { createContext, useContext } from "react"

type ModalDialogContextType = {
    show: boolean
    setShow : (show: boolean) => void
    action?: VoidFunction
    setAction? : (action : VoidFunction) => void
}

const ModalDialogContext = createContext<ModalDialogContextType | undefined>(undefined)

const useModalDialogContext = () => {
    const context = useContext(ModalDialogContext)

    if(!context) {
        throw Error("Invalid usage of Modail Dialog Context.")
    }

    return context
}

export {
    ModalDialogContext,
    useModalDialogContext
}