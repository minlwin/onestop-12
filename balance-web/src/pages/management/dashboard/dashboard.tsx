import Page from "../../../ui/page";
import YearMonthControls from "../../../ui/year-month-controls";
import { useBusinessYearContext } from "../../../model/provider/business-years-context";
import type { Optional, SummaryData, YearMonthData } from "../../../model/dto";
import { getProgress, getSummary } from "../../../model/client/management/dashboard-client";
import type { PieModel } from "../../../ui/app-pie-chart";
import { useState } from "react";
import type { BarData } from "../../../ui/app-bar-chart";
import AppBarChart from "../../../ui/app-bar-chart";
import type { ProgressData } from "../../../model/dto/management/dashboard";
import { DashboardStatusInfo } from "../../../ui/dashboard-status-info";
import { DashboardSummaryCharts } from "../../../ui/dashboard-summary-charts";
import { getPieData } from "../../../model/utils";

export default function DashBoard() {

    const {years} = useBusinessYearContext()
    const [summary, setSummary] = useState<SummaryData>()
    const [progress, setProgress] = useState<ProgressData>()

    async function loadData(form:YearMonthData) {
        const summary = await getSummary(form)
        setSummary(summary)

        const progress = await getProgress(form);
        setProgress(progress)
    }

    return (
        <Page title="" actions={
            <YearMonthControls years={years} onChange={loadData} />
        }>
            <div className="row">
                <div className="col-3">
                    <DashboardStatusInfo name="Pending" 
                        icon={<i className="bi-cart" style={{fontSize: 60}}></i>} 
                        value={getStatusCount('Pending', summary)}
                        bgColor="bg-warning" textColor="text-white" />
                    <DashboardStatusInfo name="Approved" 
                        icon={<i className="bi-check-circle" style={{fontSize: 60}}></i>} 
                        value={getStatusCount('Approved', summary)}
                        bgColor="bg-primary" textColor="text-white" />
                    <DashboardStatusInfo name="Denied" 
                        icon={<i className="bi-exclamation-circle" style={{fontSize: 60}}></i>} 
                        value={getStatusCount('Denied', summary)}
                        bgColor="bg-danger" textColor="text-white" />
                </div>

                <div className="col">
                    <section className="ps-5">
                        <DashboardSummaryCharts models={getSummaryChartData(summary)} />
                    </section>
                    <section className="mt-5" style={{height : "60%", width: "100%"}}>
                        <AppBarChart data={getProgressChartData(progress)} valueNames={['value']} />
                    </section>
                </div>
            </div>
        </Page>
    )
}

function getStatusCount(key:string, summary: Optional<SummaryData>) {
    return summary && summary[key] 
        && Object.values(summary[key]).map(a => a).reduce((a, b) => a + b)
}

function getProgressChartData(data : Optional<ProgressData>):BarData[] {
    return (data && Object.keys(data).map(a => ({
        name: a,
        value: data[a]
    }))) || []
}

function getSummaryChartData(data: Optional<SummaryData>):PieModel[] {
    return (data && [
        {name : "Pending", data : getPieData("Pending", data)},
        {name : "Approved", data : getPieData("Approved", data)},
        {name : "Denied", data : getPieData("Denied", data)},
    ]) || []
}
