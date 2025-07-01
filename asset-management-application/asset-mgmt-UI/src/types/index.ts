export interface User {
  id: number;
  username: string;
  email: string;
}

export interface Asset {
  id: number;
  user: {
    id: number;
    userName: string;
    email: string;
  };
  assetName: string;
  category: {
    id: number;
    name: string;
  };
  status: {
    id: number;
    name: string;
  };
  purchaseDate: string;
  warrantyExpiryDate?: string;
  assetImageUrl?: string;
  createdAt: string;
  updatedAt: string;
}

export interface AssetCategory {
  id: number;
  name: string;
}

export interface AssetStatus {
  id: number;
  name: string;
}

export interface AuthResponse {
  token: string;
  email: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
}

export interface CreateAssetRequest {
  userId: number;
  assetName: string;
  categoryId: number;
  statusId: number;
  purchaseDate: string;
  warrantyExpiryDate?: string;
  assetImageUrl?: string;
}

export interface UpdateAssetRequest extends CreateAssetRequest {
  id: number;
}

export interface ApiError {
  message: string;
  status: number;
}