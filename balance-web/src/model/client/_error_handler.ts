import type { AxiosError } from "axios";
import { clientErrorStore } from "../store/client-error.store";

export function handleError(error:AxiosError) {
    clientErrorStore.getState().setError(error)
}