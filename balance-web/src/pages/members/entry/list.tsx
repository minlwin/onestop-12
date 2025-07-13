import { useParams } from "react-router";
import Page from "../../../ui/page";
import { useEffect, useState } from "react";
import type { LedgerType } from "../../../model/constants";
import Loading from "../../../ui/loading";

export default function LedgerEntryManagement() {

    const params = useParams()
    const [ledgerType, setLedgerType] = useState<LedgerType | undefined>(undefined)

    useEffect(() => {
        const type = params.type 
        setLedgerType(type == 'Credit'.toLocaleLowerCase() ? 'Credit' : 'Debit')
    }, [params])

    if(!ledgerType) {
        return <Loading />
    }

    const icon = ledgerType === 'Credit' ? <i className="bi-bag-plus"></i> : <i className="bi-bag-dash"></i>

    return (
        <Page icon={icon} title={`${ledgerType} Management`}>
            
        </Page>
    )
}