import type React from "react";

export default function Card({title, className, children} : {title: string, className?: string, children?: React.ReactNode}) {
    return (
        <div className={`card ${className}`}>
            <div className="card-body">
                <h5 className="card-title">{title}</h5>

                {children}
            </div>
        </div>
    )
}