export interface SignInForm {
    email : string
    password : string
}

export type SignUpForm = SignInForm & {
    name : string
}

export interface AuthResult {
    email : string
    name : string
    role : 'Admin' | 'Member'
}