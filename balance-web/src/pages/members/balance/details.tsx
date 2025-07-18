import { Link, useParams } from "react-router";
import Page from "../../../ui/page";
import { useEffect, useState } from "react";
import { findEntryById } from "../../../model/client/member/ledger-entry-client";
import type { LedgerEntryDetails } from "../../../model/dto/member/ledger-entry";
import Loading from "../../../ui/loading";
import InputGroup from "../../../ui/input-group";

export default function LedgerEntryDetails() {

    const params = useParams()
    const [details, setDetails] = useState<LedgerEntryDetails>()

    useEffect(() => {
        async function load(id:unknown) {
            const response = await findEntryById(id)
            setDetails(response)
        }

        if(params.id) {
            load(params.id)
        }
    }, [params])

    if(!details) {
        return (
            <Loading />
        )
    }

    return (
        <Page icon={<i className={details.type == 'Credit' ? 'bi-bag-plus' : 'bi-bag-dash'}></i>} title={`${details.id.requestId} : ${details.ledgerName}`} actions={
            details.canEdit && 
            <Link to={`/member/entry/${details.type.toLocaleLowerCase()}/edit?id=${details.id.requestId}`} className="btn btn-secondary">
                <i className="bi-pencil"></i> Edit Entry
            </Link>
        }>
            <div className="mb-4 row">
                <div className="col-auto">
                    <InputGroup label="Isse At">
                        <span className="form-control">{details.issueAt}</span>
                    </InputGroup>
                </div>

                <div className="col-auto">
                    <InputGroup label="Particular">
                        <span className="form-control">{details.particular}</span>
                    </InputGroup>
                </div>
            </div>

            <table className="table table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Item</th>
                        <th>Remark</th>
                        <th className="text-end">Unit Price</th>
                        <th className="text-end">Quantity</th>
                        <th className="text-end">Total</th>
                    </tr>
                </thead>

                <tbody>
                {details.items.map((item, index) => 
                    <tr key={index}>
                        <td>{index + 1}</td>
                        <td>{item.item}</td>
                        <td>{item.remark}</td>
                        <td className="text-end">{item.unitPrice.toLocaleString()}</td>
                        <td className="text-end">{item.quantity.toLocaleString()}</td>
                        <td className="text-end">{(item.quantity * item.unitPrice).toLocaleString()}</td>
                    </tr>
                )}
                </tbody>
                <tfoot>
                    <tr>
                        <td colSpan={5} className="text-end">All Total</td>
                        <td className="text-end">{details.items.map(a => a.unitPrice * a.quantity).reduce((a, b) => a + b).toLocaleString()}</td>
                    </tr>
                </tfoot>
            </table>
        </Page>
    )
}