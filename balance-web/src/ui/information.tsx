export default function Information({label, value, className} : {label: string, value:string | number, className?: string}) {
    return (
        <div className={`d-flex justify-content-between ${className} border-bottom border-1 mb-2 pb-1`}>
            <span className="text-secondary">{label}</span>
            <span>{value}</span>
        </div>
    )
}