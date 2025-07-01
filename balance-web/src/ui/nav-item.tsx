import type React from "react";
import { Link } from "react-router";

export default function NavItem({icon, title, path} : {icon?: React.ReactNode, title: string, path?: string}) {
    return (
        <li className="nav-item">
            <Link to={path ?? '/'} className="nav-link">
                {icon} <span>{title}</span>
            </Link>
        </li>
    )
}