import { Link, useNavigate } from "react-router";
import FormGroup from "../../ui/form-group";
import { useForm } from "react-hook-form";
import type { SignInForm } from "../../model/dto";
import { signInRequest } from "../../model/client/anonymous-client";

export default function SignIn() {

    const navigate = useNavigate()
    const {register, handleSubmit, formState : {errors}} = useForm<SignInForm>()

    async function signIn(form : SignInForm) {
        const result = await signInRequest(form)
        navigate(`/${result.role.toLocaleLowerCase()}`)
    }

    return (
        <div className="w-50">
            <h3><i className="bi-unlock"></i> Member Sign In</h3>

            <form onSubmit={handleSubmit(signIn)} className="mt-4">
                <FormGroup label="Login ID">
                    <input type="text" className="form-control" placeholder="Enter email address." 
                        {...register('email', {required : "Please enter email for login."})}/>
                    {errors.email && <span className="text-sm text-danger">{errors.email?.message}</span>}
                </FormGroup>

                <FormGroup label="Password" className="mt-3">
                    <input type="password" className="form-control" placeholder="Enter password."
                        {...register('password', {required : "Please enter password."})}/>
                    {errors.password && <span className="text-sm text-danger">{errors.password?.message}</span>}
                </FormGroup>

                <div className="mt-3">
                    <button type="submit" className="btn btn-secondary">
                        <i className="bi-unlock"></i> Sign In
                    </button>

                    <Link to="/signup" className="btn btn-outline-secondary ms-2">
                        <i className="bi-person-plus"></i> Sign Up
                    </Link>
                </div>
            </form>
        </div>
    )
}