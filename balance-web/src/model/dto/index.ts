export type ErrorMessage = {
    field?: string
    message: string
}

export type ClientError = {
    messages : ErrorMessage[]
}

export type Optional<T> = T | undefined

export type Pager = {
    page: number
    size: number
    totalCount : number
    totalPage : number
    links : number[]
}

export type PageResult<T> = {
    contents : T[]
    pager?: Pager
}

export type ModificationResult<T> = {
    success: boolean
    id?: T
    message? :string
}

export type PageSearch = {
    page: number
    size: number
}

export type ApiResponse<T> = Promise<Optional<T>>

export type PlanInfo = {
    id: number
    name: string;             // required, must not be blank
    months: number;
    fees: number;
    maxLedgers: number;      // optional (Integer in Java = nullable)
    dailyEntry: number;      // optional
    monthlyEntry: number;    // optional
}

export type YearMonthData = {
    type: 'Yearly' | 'Monthly';
    year: number;
    month? : number;
}