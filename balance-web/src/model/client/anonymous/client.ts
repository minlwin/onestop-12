import type { AuthResult, SignInForm, SignUpForm } from "../../dto/anonymous/commons";
import { anonymousClient } from "../_instance";

export async function signInRequest(form : SignInForm):Promise<AuthResult> {
    const {data} = await anonymousClient().post('/token/generate', form)
    return data
}

export async function signUpRequest(form: SignUpForm):Promise<AuthResult> {
    const {data} = await anonymousClient().post('/signup', form)
    return data
}

export async function refreshToken(token: string):Promise<AuthResult> {
    const {data} = await anonymousClient().post('/token/refresh', {token : token})
    return data
}