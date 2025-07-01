import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { assetsApi, categoriesApi, statusApi } from '../services/api';
import type { Asset, CreateAssetRequest, AssetCategory, AssetStatus } from '../types';
import { ArrowLeft } from 'lucide-react';

const AssetForm: React.FC<{ editMode?: boolean }> = ({ editMode }) => {
  const navigate = useNavigate();
  const { id } = useParams(); // Used for edit mode

  const [formData, setFormData] = useState<CreateAssetRequest>({
    userId: Number(localStorage.getItem('userId')) || 2,
    assetName: '',
    categoryId: 0,
    statusId: 0,
    purchaseDate: '',
    warrantyExpiryDate: '',
    assetImageUrl: '',
  });

  const [categories, setCategories] = useState<AssetCategory[]>([]);
  const [statuses, setStatuses] = useState<AssetStatus[]>([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [catData, statData] = await Promise.all([
          categoriesApi.getAll(),
          statusApi.getAll(),
        ]);
        setCategories(catData);
        setStatuses(statData);

        // Edit mode: pre-fill data
        if (editMode && id) {
          const asset: Asset = await assetsApi.getById(Number(id));
          setFormData({
            userId: asset.user.id,
            assetName: asset.assetName,
            categoryId: asset.category.id,
            statusId: asset.status.id,
            purchaseDate: asset.purchaseDate,
            warrantyExpiryDate: asset.warrantyExpiryDate || '',
            assetImageUrl: asset.assetImageUrl || '',
          });
        }
      } catch (err: any) {
        setError(err.message || 'Failed to load data');
      }
    };

    fetchData();
  }, [editMode, id]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const payload: any = {
      ...(editMode && id ? { id: Number(id) } : {}),
      user: { id: formData.userId },
      assetName: formData.assetName,
      category: { id: formData.categoryId },
      status: { id: formData.statusId },
      purchaseDate: formData.purchaseDate,
      warrantyExpiryDate: formData.warrantyExpiryDate || null,
      assetImageUrl: formData.assetImageUrl || null,
    };

    try {
      if (editMode && id) {
        await assetsApi.update(payload);
      } else {
        await assetsApi.create(payload);
      }
      navigate('/assets');
    } catch (err: any) {
      setError(err.message || 'Failed to save asset');
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'categoryId' || name === 'statusId' ? Number(value) : value,
    }));
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold text-gray-900">{editMode ? 'Edit Asset' : 'Add New Asset'}</h1>
        <Link to="/assets" className="px-4 py-2 bg-gray-600 text-white rounded-md flex items-center space-x-2">
          <ArrowLeft className="w-4 h-4" /> <span>Back</span>
        </Link>
      </div>

      {error && <div className="text-red-500">{error}</div>}

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">Asset Name</label>
          <input
            type="text"
            name="assetName"
            value={formData.assetName}
            onChange={handleChange}
            className="border border-gray-300 rounded-md px-3 py-1 w-full"
            required
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">Category</label>
          <select
            name="categoryId"
            value={formData.categoryId}
            onChange={handleChange}
            className="border border-gray-300 rounded-md px-3 py-1 w-full"
            required
          >
            <option value="0">Select Category</option>
            {categories.map(cat => (
              <option key={cat.id} value={cat.id}>{cat.name}</option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">Status</label>
          <select
            name="statusId"
            value={formData.statusId}
            onChange={handleChange}
            className="border border-gray-300 rounded-md px-3 py-1 w-full"
            required
          >
            <option value="0">Select Status</option>
            {statuses.map(stat => (
              <option key={stat.id} value={stat.id}>{stat.name}</option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">Purchase Date</label>
          <input
            type="date"
            name="purchaseDate"
            value={formData.purchaseDate}
            onChange={handleChange}
            className="border border-gray-300 rounded-md px-3 py-1 w-full"
            required
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">Warranty Expiry Date</label>
          <input
            type="date"
            name="warrantyExpiryDate"
            value={formData.warrantyExpiryDate}
            onChange={handleChange}
            className="border border-gray-300 rounded-md px-3 py-1 w-full"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">Asset Image URL</label>
          <input
            type="text"
            name="assetImageUrl"
            value={formData.assetImageUrl}
            onChange={handleChange}
            className="border border-gray-300 rounded-md px-3 py-1 w-full"
          />
        </div>

        <button
          type="submit"
          className="px-4 py-2 bg-indigo-600 text-white rounded-md"
        >
          {editMode ? 'Update Asset' : 'Create Asset'}
        </button>
      </form>
    </div>
  );
};

export default AssetForm;
