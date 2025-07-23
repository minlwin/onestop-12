import React, { useEffect, useState } from "react";
import type { PaymentMethodListItem } from "../dto/member/payment-method";
import { MemberPaymentContext } from "./member-payment-context";
import { searchPaymentMethod } from "../client/member/payment-method-client";
import { authStore } from "../store/auth-result.store";

export default function MemberPaymentContextProvider({children} : {children : React.ReactNode}) {
    const [payments, setPayments] = useState<PaymentMethodListItem[]>([])
    const {auth} = authStore()
    useEffect(() => {
        async function load() {
            if(auth) {
                const response = await searchPaymentMethod()
                if(response) {
                    setPayments(response)
                }
            }
        }

        load()
    }, [auth])

    return (
        <MemberPaymentContext.Provider value={{payments : payments, setPayments: setPayments}}>
            {children}
        </MemberPaymentContext.Provider>
    )
}