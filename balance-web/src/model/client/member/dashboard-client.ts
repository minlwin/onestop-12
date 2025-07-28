import type { BarData } from "../../../ui/app-bar-chart"
import type { ApiResponse, SummaryData, YearMonthData } from "../../dto"
import { userName } from "../../store/auth-result.store"
import { handleError } from "../_error_handler"
import { securedClient } from "../_instance"

export async function getYears():ApiResponse<number[]> {
    const response = await securedClient().get(`/member/${userName()}/dashboard/years`).catch(handleError)
    return response?.data
}

export async function getSummary(data:YearMonthData):ApiResponse<SummaryData> {
    const response = await securedClient().get(`/member/${userName()}/dashboard/summary`, {params: data}).catch(handleError)
    return response?.data
}

export async function getProgress(data:YearMonthData):ApiResponse<BarData[]> {
    const response = await securedClient().get(`/member/${userName()}/dashboard/progress`, {params: data}).catch(handleError)
    return response?.data
}
