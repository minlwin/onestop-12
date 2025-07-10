import { Link } from "react-router";
import FormGroup from "../../../../ui/form-group";
import Page from "../../../../ui/page";
import type { SubscriptionPlanListItem, SubscriptionPlanSearch } from "../../../../model/dto";
import { useForm } from "react-hook-form";
import { useEffect, useState } from "react";
import { searchPlan } from "../../../../model/client/management-plan-client";
import NoData from "../../../../ui/no-data";

export default function SubscriptionPlanManagement() {

    const {handleSubmit, register} = useForm<SubscriptionPlanSearch>()
    const [list, setList] = useState<SubscriptionPlanListItem[]>([])

    async function search(form:SubscriptionPlanSearch) {
        const result = await searchPlan(form)
        setList(result)
    }

    useEffect(() => {
        search({})
    }, [])

    return (
        <Page icon={<i className="bi-bookmark-heart"></i>} title="Subscription Plan Management">
            <form onSubmit={handleSubmit(search)} className="row">
                <FormGroup label="Status" className="col-auto">
                    <select className="form-select" {...register('active')}>
                        <option value="">All Status</option>
                        <option value="true">Active</option>
                        <option value="false">Pending</option>
                    </select>
                </FormGroup>

                <FormGroup label="Month From" className="col-auto">
                    <input type="number" {...register('monthFrom')} style={{width : 120}} placeholder="Month From" className="form-control" />
                </FormGroup>

                <FormGroup label="Month To" className="col-auto">
                    <input type="number" {...register('monthTo')} style={{width : 120}} placeholder="Month To" className="form-control" />
                </FormGroup>

                <FormGroup label="Keyword" className="col-auto">
                    <input {...register('keyword')}  placeholder="Search Keyword" className="form-control" />
                </FormGroup>

                <div className="col btn-wrapper">
                    <button type="submit" className="btn btn-dark">
                        <i className="bi-search"></i> Search
                    </button>

                    <Link to="/admin/master/plan/edit" className="btn btn-outline-dark ms-2">
                        <i className="bi-plus"></i> Create Plan
                    </Link>
                </div>
            </form>

            <section className="mt-3">
                {list.length ? 
                    <table className="table table-bordered table-striped table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Fees</th>
                                <th>Months</th>
                                <th>Status</th>
                                <th>Default</th>
                                <th>Subscriptions</th>
                                <th>Members</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                        {list.map(item => 
                            <tr key={item.id}>
                                <td>{item.id}</td>
                                <td>{item.name}</td>
                                <td>{item.fees}</td>
                                <td>{item.months}</td>
                                <td>{item.active ? "Active" : "Pending"}</td>
                                <td>{item.defaultPlan && "Default"}</td>
                                <td>{item.subscription}</td>
                                <td>{item.member}</td>
                                <td className="text-center">
                                    <Link to={`/admin/master/plan/${item.id}`} className="icon-link">
                                        <i className="bi-arrow-right"></i>
                                    </Link>
                                </td>
                            </tr>
                        )}    
                        </tbody>
                    </table> : 
                    <NoData name="Subscription Plan" />
                }
            </section>
        </Page>
    )
}