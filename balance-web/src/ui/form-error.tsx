export default function FormError({message} : {message? : string}) {
    return (
        <span className="text-danger">{message}</span>
    )
}