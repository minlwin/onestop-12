import type { SignInForm, SignInResult } from "../dto";

export async function signInRequest(form : SignInForm):Promise<SignInResult> {
    return {
        name: form.password === 'admin' ? "Admin User" : "Member",
        email: form.email,
        role: form.password === 'admin' ? 'Admin' : 'Member'
    }
}