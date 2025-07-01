import axios from 'axios';
import type {
  User,
  Asset,
  AssetCategory,
  AssetStatus,
  AuthResponse,
  LoginRequest,
  RegisterRequest,
  CreateAssetRequest,
  UpdateAssetRequest,
} from '../types';

const API_BASE_URL = 'http://localhost:9090';

// Create axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor to add auth token
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Response interceptor to handle errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Auth API
export const authApi = {
  login: async (data: LoginRequest): Promise<AuthResponse> => {
    const response = await api.post('/auth/login', data);
    return response.data;
  },

  register: async (data: RegisterRequest): Promise<AuthResponse> => {
    const response = await api.post('/auth/register', data);
    return response.data;
  },
};

// Assets API
export const assetsApi = {
  getAll: async (): Promise<Asset[]> => {
    const response = await api.get('/assets');
    return response.data;
  },

  getByUserId: async (userId: number): Promise<Asset[]> => {
  const response = await api.get(`/assets/user/${userId}`);
  // If it's paginated:
  return Array.isArray(response.data.content) ? response.data.content : response.data;
},

getById: async (id: number): Promise<Asset> => {
  const response = await api.get(`/assets/${id}`);
  return response.data;
},

 create: async (data: any): Promise<Asset> => {
  const response = await api.post('/assets', data);
  return response.data;
},

  update: async (data: UpdateAssetRequest): Promise<Asset> => {
  const response = await api.put('/assets', data);
  return response.data;
},

  delete: async (id: number): Promise<void> => {
    await api.delete(`/assets/${id}`);
  },
};

// Asset Categories API
export const categoriesApi = {
  getAll: async (): Promise<AssetCategory[]> => {
    const response = await api.get('/asset-category');
    return response.data;
  },

  getById: async (id: number): Promise<AssetCategory> => {
    const response = await api.get(`/asset-category/${id}`);
    return response.data;
  },

  create: async (data: { name: string }): Promise<AssetCategory> => {
    const response = await api.post('/asset-category', data);
    return response.data;
  },

  update: async (data: { id: number; name: string }): Promise<AssetCategory> => {
    const response = await api.put('/asset-category', data);
    return response.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/asset-category/${id}`);
  },
};

// Asset Status API
export const statusApi = {
  getAll: async (): Promise<AssetStatus[]> => {
    const response = await api.get('/asset-status');
    return response.data;
  },

  getById: async (id: number): Promise<AssetStatus> => {
    const response = await api.get(`/asset-status/${id}`);
    return response.data;
  },

  create: async (data: { name: string }): Promise<AssetStatus> => {
    const response = await api.post('/asset-status', data);
    return response.data;
  },

  update: async (data: { id: number; name: string }): Promise<AssetStatus> => {
    const response = await api.put('/asset-status', data);
    return response.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/asset-status/${id}`);
  },
};

// User API
export const userApi = {
  update: async (id: number, data: { userName: string; email: string; password?: string }): Promise<User> => {
    const response = await api.put(`/edit-user/${id}`, data);
    return response.data;
  },
};