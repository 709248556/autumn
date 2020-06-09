import HttpRequest from '@/libs/axios'
import host from '@/config/host'

const axios = new HttpRequest(host())
export default axios
