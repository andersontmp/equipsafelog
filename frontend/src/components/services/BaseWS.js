import axios from 'axios'

const axiosConfig = {
  baseURL: '/api/equipsafelog'
}

export default axios.create(axiosConfig)
