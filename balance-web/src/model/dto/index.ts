export type ErrorMessage = {
    field?: string
    message: string
}

export type ClientError = {
    messages : ErrorMessage[]
}

export type Optional<T> = T | undefined