import type { AuthResult, SignInForm, SignUpForm } from "../dto";

export async function signInRequest(form : SignInForm):Promise<AuthResult> {
    return {
        name: form.password === 'admin' ? "Admin User" : "Member",
        email: form.email,
        role: form.password === 'admin' ? 'Admin' : 'Member'
    }
}

export async function signUpRequest(form: SignUpForm):Promise<AuthResult> {
    return {
        name: form.password === 'admin' ? "Admin User" : "Member",
        email: form.email,
        role: form.password === 'admin' ? 'Admin' : 'Member'
    }
}