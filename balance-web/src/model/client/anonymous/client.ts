import type { AuthResult, SignInForm, SignUpForm } from "../../dto/anonymous/commons";
import { handleError } from "../_error_handler";
import { anonymousClient } from "../_instance";

export async function signInRequest(form : SignInForm):Promise<AuthResult | undefined> {
    const response = await anonymousClient().post('/token/generate', form).catch(handleError)
    return response?.data
}

export async function signUpRequest(form: SignUpForm):Promise<AuthResult | undefined> {
    const response = await anonymousClient().post('/signup', form).catch(handleError)
    return response?.data
}

export async function refreshToken(token: string):Promise<AuthResult | undefined> {
    const response = await anonymousClient().post('/token/refresh', {token : token}).catch(handleError)
    return response?.data
}