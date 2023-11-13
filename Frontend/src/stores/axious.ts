import axios from "axios";
import { useAuthService } from '@/stores/authService'

const instance = axios.create({
  headers: {
    "Content-Type": "application/json"
  },
});

instance.interceptors.request.use(
  (config) => {
      const authService = useAuthService()
      const token: string = authService.getAccessToken

      if (token) {
          config.headers["Authorization"] = 'Bearer ' + token
      }
      
      return config
  },
  (error) => {
      Promise.reject(error)
  }
)

instance.interceptors.response.use(response => {
  return response;
}, error => {
  const originalRequest = error.config;
  if (error.response.status === 401) {
    const authService = useAuthService()
    authService.renewToken()
    return instance(originalRequest);
  }

  return Promise.reject(error);
});

export default instance;