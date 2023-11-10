import Axios from "axios";

export const baseURL = "http://localhost:8080";

export const api = Axios.create({
    baseURL: `${baseURL}`,
    timeout: 1000,
});