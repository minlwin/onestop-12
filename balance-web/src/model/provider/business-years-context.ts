import { createContext, useContext } from "react"

type BusinessYearsType = {
    years: number[]
    setYears: (years: number[]) => void
}

const BusinessYearContext = createContext<BusinessYearsType | undefined>(undefined)

const useBusinessYearContext = () => {
    const context = useContext(BusinessYearContext)

    if(!context) {
        throw new Error("Invalid usage of Business Year Context")
    }

    return context
}

export {
    BusinessYearContext,
    useBusinessYearContext
}