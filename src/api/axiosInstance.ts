import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/VroomERPRest/rest',
});

// Aggiunge il token automaticamente a ogni richiesta
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
      console.log('✅ Token applicato:', token);
    }else{
        console.warn('⚠️ Nessun token trovato');
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default axiosInstance;
