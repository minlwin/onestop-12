import type { Pager } from "../model/client/_instance";
import InputGroup from "./input-group";

export default function Pagination({pager, sizeChange, pageChange} : {pager?: Pager, sizeChange : (size:number) => void, pageChange : (size:number) => void}) {
    
    if(!pager || pager.totalPage <= 1) {
        return (
            <></>
        )
    }

    return (
        <nav className="d-flex justify-content-between">
            <div className="row">
                <div className="col-auto">
                    <InputGroup label="Size">
                        <select onChange={e => sizeChange(Number.parseInt(e.target.value))} className="form-select">
                            <option value="10">10</option>
                            <option value="25">25</option>
                            <option value="50">50</option>
                        </select>
                    </InputGroup>
                </div>
                <div className="col">
                    <button onClick={() => pageChange(0)} type="button" className="btn btn-outline-dark pager-link ">
                        <i className="bi-arrow-bar-left"></i>
                    </button>

                    {pager.links.map(page => 
                        <button key={page} onClick={() => pageChange(page)} type="button" className={`btn ${pager.page == page ? "btn-dark" : "btn-outline-dark"} pager-link ms-1`}>
                            {page + 1}
                        </button>
                    )}

                    <button type="button" onClick={() => pageChange(pager.totalPage - 1)} className="btn btn-outline-dark pager-link ms-1">
                        <i className="bi-arrow-bar-right"></i>
                    </button>
                </div>
            </div>

            <div className="row gx-2">
                <div className="col-auto">
                    <InputGroup label="Pages">
                        <span className="form-control">{pager.totalPage}</span>
                    </InputGroup>
                </div>
                <div className="col-auto">
                    <InputGroup label="Count">
                        <span className="form-control">{pager.totalCount}</span>
                    </InputGroup>
                </div>
            </div>
        </nav>
    )
}