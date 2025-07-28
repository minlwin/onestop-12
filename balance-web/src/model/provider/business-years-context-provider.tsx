import { useEffect, useState } from "react"
import { BusinessYearContext } from "./business-years-context"
import { getYears } from "../client/management/dashboard-client"

export default function BusinessYearContextProvider({children} : {children : React.ReactNode}) {

    const [years, setYears] = useState<number[]>([])

    useEffect(() => {
        async function load() {
            const response = await getYears()
            setYears(response || [])
        }
        load()
    }, [])

    return (
        <BusinessYearContext.Provider value={{years: years, setYears : setYears}}>
            {children}
        </BusinessYearContext.Provider>
    )
}