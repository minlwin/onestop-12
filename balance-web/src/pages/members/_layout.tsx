import { Link, Outlet, useNavigate } from "react-router"
import NavItem from "../../ui/nav-item"
import { authStore } from "../../model/store/auth-result.store"
import MemberLedgerProvider from "../../model/provider/member-ledger-provider"
import ClientErrorMessage from "../../ui/client-error-message"
import MemberPlanContextProvider from "../../model/provider/member-plan-context-provider"
import MemberPaymentContextProvider from "../../model/provider/member-payment-context-provider"
import type React from "react"
import { useEffect, useState } from "react"
import { getYears } from "../../model/client/member/dashboard-client"
import { BusinessYearContext } from "../../model/provider/business-years-context"

export default function MembersLayout() {
    return (
        <>
            <Navigation />

            <MasterDataProvider>
                <BusinessYearContextProvider>
                    <main className="container-fluid mt-3 pb-3">
                        <Outlet />
                    </main>
                </BusinessYearContextProvider>
            </MasterDataProvider>

            <ClientErrorMessage anonymous={false} />
        </>
    )
}

function BusinessYearContextProvider({children} : {children : React.ReactNode}) {

    const [years, setYears] = useState<number[]>([])

    useEffect(() => {
        async function load() {
            const response = await getYears()
            setYears(response || [])
        }
        load()
    }, [])

    return (
        <BusinessYearContext.Provider value={{years: years, setYears : setYears}}>
            {children}
        </BusinessYearContext.Provider>
    )
}

function MasterDataProvider({children} : {children : React.ReactNode}) {
    return (
        <MemberLedgerProvider>
            <MemberPlanContextProvider>
                <MemberPaymentContextProvider>
                    {children}
                </MemberPaymentContextProvider>
            </MemberPlanContextProvider>
        </MemberLedgerProvider>
    )
}

function Navigation() {
    const navigate = useNavigate()
    const {auth ,setAuth} = authStore()

    const signOut = () => {
        setAuth(undefined)
        navigate('/signin')
    }

    return (
        <nav className="navbar bg-light navbar-expand shadow-sm">
            <div className="container-fluid">
                <Link to="/member" className="navbar-brand">
                    <i className="bi-bar-chart"></i> My Balance
                </Link>

                <ul className="navbar-nav">
                    <NavItem path="/member/balance" title="Balances" icon={<i className="bi-pie-chart"></i>} />
                    <NavItem path="/member/entry/debit" title="Debit" icon={<i className="bi-bag-dash"></i>} />
                    <NavItem path="/member/entry/credit" title="Credit" icon={<i className="bi-bag-plus"></i>} />
                    <NavItem path="/member/ledger" title="Ledgers" icon={<i className="bi-tags"></i>} />
                    <li className="nav-item">
                    </li>
                    <li className="nav-item dropdown">
                        <a href="#" className="nav-link dropdown-toggle" data-bs-toggle="dropdown" >
                            <i className="bi-person"></i> {auth?.name}
                        </a>
                        <ul className="dropdown-menu dropdown-menu-end">
                            <li>
                                <Link to="/member" className="dropdown-item">
                                    <i className="bi-house"></i> Dashboard
                                </Link>
                            </li>
                            <li>
                                <Link to="/member/subscription" className="dropdown-item">
                                    <i className="bi-shield"></i> Subscriptions
                                </Link>
                            </li>
                            <li>
                                <hr className="dropdown-divider" />
                            </li>
                            <li>
                                <a href="#" onClick={e => {
                                    e.preventDefault()
                                    signOut()
                                }} className="dropdown-item">
                                    <i className="bi-lock"></i> Sign Out
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
    )
}