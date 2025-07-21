export type ErrorMessage = {
    field?: string
    message: string
}

export type ClientError = {
    messages : ErrorMessage[]
}