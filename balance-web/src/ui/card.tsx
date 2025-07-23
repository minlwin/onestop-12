import type React from "react";

export default function Card({title, icon, className, children} : {title: string, icon? : React.ReactNode, className?: string, children?: React.ReactNode}) {
    return (
        <div className={`card ${className}`}>
            <div className="card-body">
                <h5 className="card-title d-flex justify-content-between align-items-center">
                    <span>{title}</span>
                    <span>{icon}</span>
                </h5>

                {children}
            </div>
        </div>
    )
}