import { Link, Outlet, useNavigate } from "react-router"
import { authStore } from "../../model/store/auth-result.store"
import ManagementPlanProvider from "../../model/provider/management-plan-provider"
import ClientErrorMessage from "../../ui/client-error-message"
import { useEffect, useState } from "react"
import { getYears } from "../../model/client/management/dashboard-client"
import { BusinessYearContext } from "../../model/provider/business-years-context"

export default function AdminLayout() {
    return (
        <>
            <Navigation />

            <main className="container-fluid mt-3">
                <ManagementPlanProvider>
                    <BusinessYearContextProvider>
                        <Outlet />
                    </BusinessYearContextProvider>
                </ManagementPlanProvider>
            </main>

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

function Navigation() {

    const navigate = useNavigate()
    const {setAuth} = authStore()

    function signOut() {
        setAuth(undefined)
        navigate('/signin')
    }

    return (
        <nav className="navbar navbar-expand navbar-light bg-light shadow-sm">
            <div className="container-fluid">
                <Link className="navbar-brand" to='/admin'>
                    <i className="bi-house"></i> Balance Admin
                </Link>

                <ul className="navbar-nav">
                    <li className="nav-item">
                        <Link to="/admin/subscriptions" className="nav-link">
                            <i className="bi-cart-plus"></i> Subscriptions
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/admin/members" className="nav-link">
                            <i className="bi-people"></i> Members
                        </Link>
                    </li>
                    <li className="nav-item dropdown">
                        <a href="#" className="nav-link dropdown-toggle" data-bs-toggle="dropdown" >
                            <i className="bi-database"></i> Master Data
                        </a>
                        <ul className="dropdown-menu">
                            <li>
                                <Link to="/admin/master/plan" className="dropdown-item">
                                    <i className="bi-bookmark-heart"></i> Subscription Plan
                                </Link>
                            </li>
                            <li>
                                <Link to="/admin/master/payment" className="dropdown-item">
                                    <i className="bi-credit-card"></i> Payment Method
                                </Link>
                            </li>
                        </ul>
                    </li>
                    <li className="nav-item">
                        <a onClick={e => {
                            e.preventDefault()
                            signOut()
                        }} className="nav-link">
                            <i className="bi-lock"></i> Sign Out
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
    )
}