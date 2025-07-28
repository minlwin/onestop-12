import type React from "react";
import Page from "../../../ui/page";
import YearMonthControls from "../../../ui/year-month-controls";
import { BusinessYearContext, useBusinessYearContext } from "../../../model/provider/business-years-context";
import { useState } from "react";
import type { YearMonthData } from "../../../model/dto";

export default function DashBoard() {

    const {years} = useBusinessYearContext()

    async function loadData(form:YearMonthData) {
        console.log(form)
    }

    return (
        <BusinessYearContextProvider>
            <Page title="Admin Dashboard" icon={<i className="bi-house"></i>} actions={
                <YearMonthControls years={years} onChange={loadData} />
            }>
                <h3>Some Contents</h3>
            </Page>
        </BusinessYearContextProvider>
    )
}

function BusinessYearContextProvider({children} : {children : React.ReactNode}) {

    const [years, setYears] = useState<number[]>([])

    return (
        <BusinessYearContext.Provider value={{years: years, setYears : setYears}}>
            {children}
        </BusinessYearContext.Provider>
    )
}