import type { Usage } from "./constants";

export function limitValue(value: number) {
    return value < 0 ? "Unlimited" : value.toLocaleString()
}

export function usageValue(usage: Usage) {
    return usage == 'Extend' ? "After Expiration" : "Subscribe Now"
}