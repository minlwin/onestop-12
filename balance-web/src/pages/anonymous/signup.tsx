import { Link } from "react-router";

export default function SignUp() {
    return (
        <div>
            <h3>Sign Up</h3>

            <Link to="/signin" className="btn btn-outline-secondary">
                Sign In
            </Link>
        </div>
    )
}