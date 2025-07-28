export function DashboardStatusInfo({name, value, icon, bgColor, textColor} : {name:string, value?:number, icon:React.ReactNode, bgColor:string, textColor: string}) {
    return (
        <div className={`card w-100 ${textColor} ${bgColor} mb-4`}>
            <div className="card-header">
                <h5 className="card-title">{name}</h5>
            </div>
            <div className="card-body d-flex align-items-baseline justify-content-between">
                <div className="ps-2">
                    {icon}
                </div>
                <div style={{fontSize : 24}} className="pe-2">
                    {prettyNumber(value)}
                </div>
            </div>
        </div>
    )
}

function prettyNumber(n:number | undefined) {
    if(n == undefined)
        return 0
    if (n >= 1_000_000_000) return (n / 1_000_000_000).toFixed(1) + 'B';
    if (n >= 1_000_000) return (n / 1_000_000).toFixed(1) + 'M';
    if (n >= 1_000) return (n / 1_000).toFixed(1) + 'K';
    return n.toString();
}

