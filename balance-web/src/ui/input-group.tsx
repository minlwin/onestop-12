import type React from "react";

export default function InputGroup({label, className, children} : {label : string, className?: string, children : React.ReactNode}) {
    return (
        <div className={`input-group ${className}`}>
            <div className="input-group-text">{label}</div>
            {children}
        </div>
    )
}