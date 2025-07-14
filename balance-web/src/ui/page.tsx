import type React from "react"

export default function Page({icon, title, actions, children} : PageProperties) {
    return (
        <>
            <section className="d-flex justify-content-between align-items-center">
                <h4>{icon} <span>{title}</span></h4>
                {actions}
            </section>

            <section className="mt-3">
                {children}
            </section>
        </>
    )
}

type PageProperties = {
    title : string
    icon? : React.ReactNode
    actions? : React.ReactNode
    children? : React.ReactNode
}