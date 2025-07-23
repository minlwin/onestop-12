import { useEffect, useState } from 'react'
import noslipSrc from '../assets/no-slip.png'
import { authStore } from '../model/store/auth-result.store'

export default function SlipImage({src} : {src? : string | File}) {
    
    if(!src) {
        return  (
            <img src={noslipSrc} className='img-fluid' alt="There is no Slip" />
        )
    }

    if(typeof src === 'string') {
        return (
            <SecuredImage src={src} />
        )
    }
    
    return (
        <img src={URL.createObjectURL(src)} className='img-fluid' alt="Slip File" />
    )
}

function SecuredImage({src} : {src : string}) {

    const [url, setUrl] = useState('')
    const {auth} = authStore()

    useEffect(() => {
        if(auth) {
            fetch(`http://localhost:8080/slips/${src}`, {
                headers: {
                    Authorization : `Bearer ${auth.accessToken}`
                }
            })
            .then(res => res.blob())
            .then(blob => URL.createObjectURL(blob))
            .then(slipUrl => setUrl(slipUrl))
        }
    }, [src, auth])

    if(!url) {
        return (
            <></>
        )
    }

    return (
        <img src={url} className='img-fluid' alt={src} />
    )
}