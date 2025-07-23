import { createContext, useContext } from "react"
import type { PaymentMethodListItem } from "../dto/member/payment-method"
import type { Optional } from "../dto"

type MemberPaymentContextType = {
    payments : PaymentMethodListItem[]
    setPayments : (payments : PaymentMethodListItem[]) => void
}

const MemberPaymentContext = createContext<Optional<MemberPaymentContextType>>(undefined)

const useMemberPaymentContext = () => {
    const context = useContext(MemberPaymentContext)

    if(!context) {
        throw new Error("Invalid usage of Member Payment Method")
    }

    return context
}

export {
    MemberPaymentContext,
    useMemberPaymentContext
}