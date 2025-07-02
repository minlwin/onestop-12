import type React from "react";

export default function FormGroup({label, className, children}:{label:string, className?: string, children?: React.ReactNode}) {
    return (
        <div className={className}>
            <label className="form-label">{label}</label>
            {children}
        </div>
    )
}