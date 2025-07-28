import { Cell, Label, Pie, PieChart, ResponsiveContainer, Tooltip } from "recharts"
import { COLORS } from "../model/constants"

export default function AppPieChart({model} : {model: PieModel}) {
    return (
        <ResponsiveContainer>
            <PieChart >
                <Pie data={model.data} nameKey={'name'} dataKey={'value'} outerRadius={80} startAngle={180} endAngle={-180} paddingAngle={5} innerRadius={60}  >
                    {model.data.map((item, index) => 
                        <Cell key={`${item}-${index}`} fill={COLORS[index % COLORS.length]} ></Cell>
                    )}
                    <Label position="center" value={model.name} />
                </Pie>
                <Tooltip />
            </PieChart>
        </ResponsiveContainer>
    )
}

export type PieModel = {
    name: string
    data: PieData[]
}

export type PieData = {
    name: string
    value: number
}
