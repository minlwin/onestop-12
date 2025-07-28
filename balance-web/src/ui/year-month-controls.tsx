import { useEffect, useRef } from "react"
import { useForm } from "react-hook-form"
import type { YearMonthData } from "../model/dto"

export default function YearMonthControls({years, onChange} : {years: number[], onChange : (data:YearMonthData) => void}) {

    const {register, handleSubmit, setValue, watch} = useForm<YearMonthData>({defaultValues : {type : 'Monthly'}})
    const selectedYear = watch('year')
    const selectedMonth = watch('month')
    const selectedType = watch('type')

    const formRef = useRef<HTMLFormElement | null>(null)

    useEffect(() => {
        const now = new Date()
        setValue('year', now.getFullYear())
        setValue('month', selectedType == "Monthly" ? now.getMonth() + 1 : undefined)
    }, [selectedType, setValue])

    useEffect(() => {
        if(selectedYear) {
            formRef.current?.requestSubmit()
        }
    }, [selectedYear, selectedMonth, selectedType])

    return (
        <form onSubmit={handleSubmit(onChange)} ref={formRef} className="row g-2">
            <div className="col-auto">
                <select {...register('year')} className="form-select">
                    {years.map(item => <option key={item} value={item}>{item}</option>)}
                </select>
            </div>
            {selectedType == 'Monthly' &&
                <div className="col-auto">
                    <select {...register('month')} className="form-select">
                        {MONTHS.map(item => <option key={item.value} value={item.value}>{item.label}</option>)}
                    </select>
                </div>
            }
            <div className="col-auto">
                <select {...register('type')} className="form-select">
                    <option value="Monthly">Monthly</option>
                    <option value="Yearly">Yearly</option>
                </select>
            </div>
        </form>
    )
}

type Month = {
    value: number
    label: string
}
const MONTHS : Month[] = [
    {value: 1, label : "January"},
    {value: 2, label : "February"},
    {value: 3, label : "March"},
    {value: 4, label : "April"},
    {value: 5, label : "May"},
    {value: 6, label : "June"},
    {value: 7, label : "July"},
    {value: 8, label : "August"},
    {value: 9, label : "September"},
    {value: 10, label : "October"},
    {value: 11, label : "November"},
    {value: 12, label : "December"},
]
