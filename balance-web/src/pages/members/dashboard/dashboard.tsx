import { useState } from "react";
import { getProgress, getSummary } from "../../../model/client/member/dashboard-client";
import type { Optional, SummaryData, YearMonthData } from "../../../model/dto";
import { useBusinessYearContext } from "../../../model/provider/business-years-context";
import Page from "../../../ui/page";
import YearMonthControls from "../../../ui/year-month-controls";
import { DashboardStatusInfo } from "../../../ui/dashboard-status-info";
import { DashboardSummaryCharts } from "../../../ui/dashboard-summary-charts";
import type { PieModel } from "../../../ui/app-pie-chart";
import { getPieData } from "../../../model/utils";
import AppBarChart, { type BarData } from "../../../ui/app-bar-chart";

export default function MemberHome() {

    const {years} = useBusinessYearContext()
    const [summary, setSummary] = useState<SummaryData>()
    const [progress, setProgress] = useState<BarData[]>([])

    async function loadData(form:YearMonthData) {
        const summary = await getSummary(form)
        setSummary(summary)

        const progress = await getProgress(form)
        setProgress(progress || [])
    }

    return (
        <Page title="" actions={
            <YearMonthControls onChange={loadData} years={years} />
        }>
            <div className="row">
                <div className="col-3">
                    {summary && 
                        <SummaryInfo summary={summary} />
                    }
                </div>

                <div className="col">
                    <section className="ms-5">
                        <DashboardSummaryCharts models={getSummaryChartData(summary)} />
                    </section>
                    <section className="mt-5" style={{height : "60%", width: "100%"}}>
                        <AppBarChart data={progress} valueNames={['debit', 'credit']} />
                    </section>
                </div>
            </div>
        </Page>
    )
}

function SummaryInfo({summary} : {summary : SummaryData}) {

    const getTotal = (key : string) => (summary[key] 
            && Object.values(summary[key] ).reduce((a, b) => a + b)) || 0

    const getSummary = () => summary['Summary'] && 
        ((summary['Summary']['Credit'] || 0) - (summary['Summary']['Debit'] || 0))

    return (
        <>
            <DashboardStatusInfo 
                name="Debit" 
                bgColor="bg-danger" 
                textColor="text-white"
                value={getTotal('Debit')}
                icon={<i className="bi-cart-dash" style={{fontSize : 60}}></i>} />
                
            <DashboardStatusInfo 
                name="Credit" 
                bgColor="bg-primary" 
                textColor="text-white"
                value={getTotal('Credit')}
                icon={<i className="bi-cart-plus" style={{fontSize : 60}}></i>} />

            <DashboardStatusInfo 
                name="Summary" 
                bgColor="bg-warning" 
                textColor="text-white"
                value={getSummary()}
                icon={<i className="bi-pie-chart" style={{fontSize : 60}}></i>} />
        </>
    )
}

function getSummaryChartData(data: Optional<SummaryData>):PieModel[] {
    return (data && [
        {name : "Debit", data : getPieData("Debit", data)},
        {name : "Credit", data : getPieData("Credit", data)},
        {name : "Summary", data : getPieData("Summary", data)},
    ]) || []
}
