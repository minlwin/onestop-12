import type React from "react"

export default function Page({icon, title, actions, children} : PageProperties) {
    return (
        <>
            <section className="d-flex justify-content-between align-items-center">
                <h3>{icon} <span>{title}</span></h3>
                {actions}
            </section>

            <section>
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