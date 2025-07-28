import type { PieModel } from "./app-pie-chart";
import AppPieChart from "./app-pie-chart";

export function DashboardSummaryCharts({models} : {models: PieModel[]}) {

    function hasValue(model: PieModel) {
        return model.data.length > 0 && model.data.filter(data => data.value > 0).length > 0
    }

    return (
        <div className="row row-cols-5 " style={{height : 180}}>
            {models.filter(item => hasValue(item)).map((item, index) => 
                <div key={`Pie-${index}`} className="col">
                    <AppPieChart model={item} />
                </div>
            )}
        </div>
    )
}
