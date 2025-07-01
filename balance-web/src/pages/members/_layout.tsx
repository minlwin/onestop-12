import { Link, Outlet, useNavigate } from "react-router"
import NavItem from "../../ui/nav-item"

export default function MembersLayout() {
    return (
        <>
            <Navigation />

            <main className="container-fluid mt-3">
                <Outlet />
            </main>
        </>
    )
}

function Navigation() {
    const navigate = useNavigate()

    const signOut = () => {
        navigate('/signin')
    }

    return (
        <nav className="navbar bg-secondary navbar-expand navbar-dark">
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
                        <a href="#" onClick={e => {
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