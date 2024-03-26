import axios from 'axios';
import store from '../../store';

// Configuração base do Axios
const axiosConfig = { 
  baseURL: '/api/equipsafelog'
};

// Crie uma nova instância do Axios com as configurações
const instance = axios.create(axiosConfig);

// Adicione um interceptor para todas as solicitações
instance.interceptors.request.use(
  config => {
    // Obtenha o token do Vuex Store
    const token = store?.getters?.getData?.token

    // Se houver um token, adicione-o ao cabeçalho de autorização
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

export default instance;