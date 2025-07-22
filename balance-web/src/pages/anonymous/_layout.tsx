import { Outlet } from "react-router";
import ClientErrorMessage from "../../ui/client-error-message";

export default function AnonymousLayout() {
    return (
        <div className="d-flex align-items-stretch vh-100">

            <section className="bg-secondary w-50 d-flex flex-column justify-content-center align-items-center">
                <i className="bi-bar-chart cover-icon"></i>

                <h1 className="text-white">My Balance</h1>
                <h5 className="text-white">Final project for One Stop Bath 12</h5>
            </section>

            <main className="w-50 d-flex flex-column justify-content-center align-items-center">
                <Outlet />
            </main>

            <ClientErrorMessage anonymous={true} />
        </div>
    )
}