import type { PieData } from "../ui/app-pie-chart";
import type { Usage } from "./constants";
import type { SummaryData } from "./dto";

export function limitValue(value: number) {
    return value < 0 ? "Unlimited" : value.toLocaleString()
}

export function usageValue(usage: Usage) {
    return usage == 'Extend' ? "After Expiration" : "Subscribe Now"
}

export function getPieData(key:string, summary:SummaryData):PieData[] {
    const value = summary[key]
    return (value && Object.keys(value).map(item => ({
        name: item,
        value: value[item] || 0
    }))) || []
}
