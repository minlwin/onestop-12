import { useRef, type ChangeEvent } from "react"
import { useForm } from "react-hook-form"
import type { YearMonthData } from "../model/dto"

export default function YearMonthControls({years, onChange} : {years: number[], onChange : (data:YearMonthData) => void}) {

    const {register, handleSubmit, setValue} = useForm<YearMonthData>({defaultValues : {type : 'Monthly'}})
    
    const formRef = useRef<HTMLFormElement | null>(null)

    function changeType(e : ChangeEvent<HTMLSelectElement>) {
        const now = new Date()
        setValue("year", now.getFullYear())
        
        if(e.target.value == 'Monthly') {
            setValue("month", now.getMonth() + 1)
        } else {
            setValue("month", undefined)
        }

        formRef.current?.requestSubmit()
    }

    return (
        <form onSubmit={handleSubmit(onChange)} ref={formRef} className="row g-2">
            <div className="col-auto">
                <select {...register('year')} onChange={() => formRef.current?.requestSubmit()} className="form-select">
                    {years.map(item => <option key={item} value={item}>{item}</option>)}
                </select>
            </div>
            <div className="col-auto">
                <select {...register('month')} onChange={() => formRef.current?.requestSubmit()} className="form-select">
                    {MONTHS.map(item => <option key={item.value} value={item.value}>{item.label}</option>)}
                </select>
            </div>
            <div className="col-auto">
                <select {...register('type')} onChange={changeType} className="form-select">
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
