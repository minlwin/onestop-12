export default function PlanInfo({name, value} : {name: string, value: string | number}) {
    return (
        <div className={`d-flex justify-content-between list-group-item`}>
            <label>{name}</label>
            <span>{value}</span>
        </div>
    )
}