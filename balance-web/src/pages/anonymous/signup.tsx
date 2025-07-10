import { Link, useNavigate } from "react-router";
import FormGroup from "../../ui/form-group";
import { useForm } from "react-hook-form";
import type { SignUpForm } from "../../model/dto";
import { signUpRequest } from "../../model/client/anonymous-client";
import { authStore } from "../../model/store/auth-result.store";

export default function SignUp() {

    const {register, handleSubmit, formState: {errors}} = useForm<SignUpForm>()
    const {setAuth} = authStore()
    const navigate = useNavigate()

    async function signUp(form:SignUpForm) {
        const response = await signUpRequest(form)
        setAuth(response)
        navigate(`/${response.role.toLocaleLowerCase()}`)
    }

    return (
        <div className="w-50">
            <h3>Sign Up</h3>

            <form onSubmit={handleSubmit(signUp)} className="mt-4">
                <FormGroup label="Name">
                    <input type="text" className="form-control" placeholder="Please enter name" {
                        ...register('name', {required : "Please enter your name."})
                    } />
                    {errors.name && <span className="text-danger">{errors.name.message}</span>}
                </FormGroup>

                <FormGroup label="Email" className="mt-3">
                    <input type="text" className="form-control" placeholder="Please enter email for login" {
                        ...register('email', {required : "Please enter email for login."})
                    } />
                    {errors.email && <span className="text-danger">{errors.email.message}</span>}
                </FormGroup>

                <FormGroup label="Password" className="mt-3">
                    <input type="password" className="form-control" placeholder="Please enter password" {
                        ...register('password', {required : "Please enter password."})
                    } />
                    {errors.password && <span className="text-danger">{errors.password.message}</span>}
                </FormGroup>

                <div className="mt-3">
                    <button type="submit" className="btn btn-secondary">
                        <i className="bi-person-plus"></i> Sign Up
                    </button>

                    <Link to="/signin" className="btn btn-outline-secondary ms-2">
                        <i className="bi-unlock"></i> Sign In
                    </Link>
                </div>
            </form>
        </div>
    )
}