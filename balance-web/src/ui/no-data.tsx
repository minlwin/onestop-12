export default function NoData({name} : {name?: string}) {
    return (
        <div className="alert alert-info">
            There is {name ?? 'data'}. Please search again.
        </div>
    )
}