import type { ApiResponse, ProgressData, SummaryData, YearMonthData } from "../../dto";
import { handleError } from "../_error_handler";
import { securedClient } from "../_instance";

export async function getYears():ApiResponse<number[]> {
    const response = await securedClient().get('/management/dashboard/years').catch(handleError)
    return response?.data
}

export async function getSummary(data:YearMonthData):ApiResponse<SummaryData> {
    const response = await securedClient().get('/management/dashboard/summary', {params: data}).catch(handleError)
    return response?.data
}

export async function getProgress(data: YearMonthData):ApiResponse<ProgressData> {
    const response = await securedClient().get('/management/dashboard/progress', {params: data}).catch(handleError)
    return response?.data
}