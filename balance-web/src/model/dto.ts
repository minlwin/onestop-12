export interface SignInForm {
    email : string
    password : string
}

export interface SignInResult {
    email : string
    name : string
    role : 'Admin' | 'Member'
}