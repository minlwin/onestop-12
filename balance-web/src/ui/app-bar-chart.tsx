import { Bar, BarChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis } from "recharts";
import { COLORS } from "../model/constants";

export default function AppBarChart({data, valueNames} : {data:BarData[], valueNames:string[]}) {
    return (
        <ResponsiveContainer>
            <BarChart data={data}>
                {valueNames.map((item, index) => 
                    <Bar key={index} dataKey={item} fill={COLORS[index % COLORS.length]} />
                )}
                <Tooltip />
                <YAxis />
                <XAxis dataKey={'name'} />
                <CartesianGrid strokeDasharray="3 3" />
            </BarChart>
        </ResponsiveContainer>
    )
}

export type BarData = {
    name: string
    [key:string]: number | string
}
