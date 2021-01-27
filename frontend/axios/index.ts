import axios, {AxiosInstance} from "axios";
import { API_BASE_URL } from "../config";

export default class AxiosService{
  static readonly instance: AxiosInstance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-Type": "application/json"
    },
    timeout: 100000
  })
}