import { saveAuthResult } from "../context/auth-result.context";
import type { AuthResult, SignInForm, SignUpForm } from "../dto";
import { anonymousClient } from "./_instance";

export async function signInRequest(form : SignInForm):Promise<AuthResult> {
    const {data} = await anonymousClient().post('/token/generate', form)
    saveAuthResult(data)
    return data
}

export async function signUpRequest(form: SignUpForm):Promise<AuthResult> {
    const {data} = await anonymousClient().post('/signup', form)
    saveAuthResult(data)
    return data
}

export async function refreshToken(token: string):Promise<AuthResult> {
    const {data} = await anonymousClient().post('/token/refresh', {token : token})
    saveAuthResult(data)
    return data
}