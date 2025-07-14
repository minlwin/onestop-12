export default function Information({label, value, className} : {label: string, value:string, className?: string}) {
    return (
        <div className={`d-flex justify-content-between ${className}`}>
            <span>{label}</span>
            <span>{value}</span>
        </div>
    )
}