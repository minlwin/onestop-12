import { Link, Outlet, useNavigate } from "react-router"

export default function AdminLayout() {
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

    function signOut() {
        navigate('/signin')
    }

    return (
        <nav className="navbar navbar-expand navbar-dark bg-dark">
            <div className="container-fluid">
                <Link className="navbar-brand" to='/admin'>
                    <i className="bi-house"></i> Balance Admin
                </Link>

                <ul className="navbar-nav">
                    <li className="nav-item">
                        <Link to="/admin/members" className="nav-link">
                            <i className="bi-people"></i> Member Management
                        </Link>
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