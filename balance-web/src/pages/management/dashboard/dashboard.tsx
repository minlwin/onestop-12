import Page from "../../../ui/page";
import YearMonthControls from "../../../ui/year-month-controls";
import { useBusinessYearContext } from "../../../model/provider/business-years-context";
import type { Optional, ProgressData, SummaryData, YearMonthData } from "../../../model/dto";
import { getProgress, getSummary } from "../../../model/client/management/dashboard-client";
import type { PieData, PieModel } from "../../../ui/app-pie-chart";
import AppPieChart from "../../../ui/app-pie-chart";
import React, { useState } from "react";
import type { BarData } from "../../../ui/app-bar-chart";
import AppBarChart from "../../../ui/app-bar-chart";

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
                    <StatusSummary name="Pending" 
                        icon={<i className="bi-cart" style={{fontSize: 60}}></i>} 
                        count={getStatusCount('Pending', summary)}
                        bgColor="bg-warning" textColor="text-white" />
                    <StatusSummary name="Approved" 
                        icon={<i className="bi-check-circle" style={{fontSize: 60}}></i>} 
                        count={getStatusCount('Approved', summary)}
                        bgColor="bg-primary" textColor="text-white" />
                    <StatusSummary name="Denied" 
                        icon={<i className="bi-exclamation-circle" style={{fontSize: 60}}></i>} 
                        count={getStatusCount('Denied', summary)}
                        bgColor="bg-danger" textColor="text-white" />
                </div>

                <div className="col">
                    <section className="ps-5">
                        <SummaryInformation data={summary} />
                    </section>
                    <section className="mt-5" style={{height : "60%", width: "100%"}}>
                        <AppBarChart data={refineProgressData(progress)} valueNames={['value']} />
                    </section>
                </div>
            </div>
        </Page>
    )
}

function SummaryInformation({data} : {data: Optional<SummaryData>}) {
    const modelList:PieModel[] = refineSummaryData(data)
    return (
        <div className="row row-cols-5 " style={{height : 180}}>
            {modelList.map((item, index) => 
                <div key={`Pie-${index}`} className="col">
                    <AppPieChart model={item} />
                </div>
            )}
        </div>
    )
}

function StatusSummary({name, count, icon, bgColor, textColor} : {name:string, count?:number, icon:React.ReactNode, bgColor:string, textColor: string}) {
    return (
        <div className={`card w-100 ${textColor} ${bgColor} mb-4`}>
            <div className="card-header">
                <h5 className="card-title">{name}</h5>
            </div>
            <div className="card-body d-flex align-items-center justify-content-around">

                <div>
                    {icon}
                </div>

                <div style={{fontSize : 40}}>
                    {count || 0}
                </div>
            </div>
        </div>
    )
}

function getStatusCount(key:string, summary: Optional<SummaryData>) {

    if(summary) {
        const data = summary[key];
        if(data) {
           return Object.values(data).map(a => a).reduce((a, b) => a + b)
        }
    }
}

function refineProgressData(data : Optional<ProgressData>):BarData[] {

    if(!data) {
        return []
    }

    return Object.keys(data).map(a => ({
        name: a,
        value: data[a]
    }))
}

function refineSummaryData(data: Optional<SummaryData>):PieModel[] {
    if(data) {
        return Object.keys(data).map(item => ({
            name: item,
            data: getPieData(item, data)
        }))
    }
    return []
}

function getPieData(key:string, summary:SummaryData):PieData[] {
    const value = summary[key]
    if(value) {
        return Object.keys(value).map(item => ({
            name: item,
            value: value[item] || 0
        }))
    }
    return []
}
