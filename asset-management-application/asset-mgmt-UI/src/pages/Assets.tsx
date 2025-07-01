import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Eye, Edit3, Trash2, Plus, Search, Filter, X } from 'lucide-react';
import { assetsApi } from '../services/api';
import type { Asset } from '../types';
import LoadingSpinner from '../components/common/LoadingSpinner';
import ErrorMessage from '../components/common/ErrorMessage';

const Assets: React.FC = () => {
  const [assets, setAssets] = useState<Asset[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [selectedAsset, setSelectedAsset] = useState<Asset | null>(null);
  const [showModal, setShowModal] = useState(false);

  const itemsPerPage = 10;
  const userId = Number(localStorage.getItem('userId')) || 2;
  const navigate = useNavigate();

  useEffect(() => {
    loadAssets();
  }, []);

  const loadAssets = async () => {
    try {
      setLoading(true);
      const data = await assetsApi.getByUserId(userId);
      setAssets(data);
    } catch (err: any) {
      setError(err.message || 'Failed to load assets');
    } finally {
      setLoading(false);
    }
  };

  const handleViewAsset = async (id: number) => {
    try {
      const asset = await assetsApi.getById(id);
      setSelectedAsset(asset);
      setShowModal(true);
    } catch {
      alert('Failed to load asset details');
    }
  };

  const handleDeleteAsset = async (id: number) => {
    if (!window.confirm('Are you sure you want to delete this asset?')) return;
    try {
      await assetsApi.delete(id);
      setAssets(prev => prev.filter(asset => asset.id !== id));
    } catch (err: any) {
      alert('Failed to delete asset: ' + (err.message || 'Unknown error'));
    }
  };

  const filteredAssets = assets.filter(asset =>
    (asset.assetName?.toLowerCase() || '').includes(searchTerm.toLowerCase()) ||
    (asset.category?.name?.toLowerCase() || '').includes(searchTerm.toLowerCase()) ||
    (asset.status?.name?.toLowerCase() || '').includes(searchTerm.toLowerCase())
  );

  const paginatedAssets = filteredAssets.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  const totalPages = Math.ceil(filteredAssets.length / itemsPerPage);

  if (loading) return <LoadingSpinner text="Loading assets..." />;
  if (error) return <ErrorMessage message={error} onRetry={loadAssets} />;

  return (
    <div className="p-6 min-h-screen space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-gray-800">Asset Management</h1>
        <Link
          to="/assets/new"
          className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-md flex items-center gap-2"
        >
          <Plus className="w-4 h-4" /> Add Asset
        </Link>
      </div>

      <div className="flex items-center gap-3">
        <div className="relative w-full max-w-sm">
          <input
            type="text"
            placeholder="Search assets..."
            className="w-full border border-gray-300 rounded-md px-4 py-2 pr-10"
            value={searchTerm}
            onChange={e => setSearchTerm(e.target.value)}
          />
          <Search className="absolute right-3 top-2.5 text-gray-400 w-5 h-5" />
        </div>
      </div>

      <div className="overflow-x-auto bg-white shadow rounded-lg">
        <table className="min-w-full table-auto divide-y divide-gray-200 text-sm">
          <thead className="bg-indigo-600 text-white uppercase text-xs">
            <tr>
              <th className="px-6 py-3 text-left">Asset Name</th>
              <th className="px-6 py-3 text-left">Category</th>
              <th className="px-6 py-3 text-left">Status</th>
              <th className="px-6 py-3 text-right">Actions</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-200">
            {paginatedAssets.map(asset => (
              <tr key={asset.id} className="hover:bg-gray-50">
                <td className="px-6 py-3">{asset.assetName}</td>
                <td className="px-6 py-3">{asset.category?.name}</td>
                <td className="px-6 py-3">{asset.status?.name}</td>
                <td className="px-6 py-3 text-right space-x-2">
                  <button onClick={() => handleViewAsset(asset.id)} className="text-gray-500 hover:text-black">
                    <Eye className="inline w-4 h-4" />
                  </button>
                  <Link to={`/assets/edit/${asset.id}`} className="text-indigo-500 hover:text-indigo-700">
                    <Edit3 className="inline w-4 h-4" />
                  </Link>
                  <button onClick={() => handleDeleteAsset(asset.id)} className="text-red-500 hover:text-red-700">
                    <Trash2 className="inline w-4 h-4" />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Pagination */}
      <div className="flex justify-between items-center pt-4 text-sm text-gray-700">
        <p>
          Page {currentPage} of {totalPages}
        </p>
        <div className="space-x-2">
          <button
            disabled={currentPage === 1}
            onClick={() => setCurrentPage(prev => prev - 1)}
            className="px-3 py-1 border rounded-md disabled:opacity-50"
          >
            Previous
          </button>
          <button
            disabled={currentPage === totalPages}
            onClick={() => setCurrentPage(prev => prev + 1)}
            className="px-3 py-1 border rounded-md disabled:opacity-50"
          >
            Next
          </button>
        </div>
      </div>

      {/* Modal */}
      {showModal && selectedAsset && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-40">
          <div className="bg-white rounded-lg shadow-xl p-6 w-full max-w-lg relative">
            <button
              className="absolute top-2 right-2 text-gray-500 hover:text-black"
              onClick={() => setShowModal(false)}
            >
              <X className="w-5 h-5" />
            </button>
            <h2 className="text-xl font-semibold mb-4">{selectedAsset.assetName}</h2>
            {selectedAsset.assetImageUrl && (
              <img
                src={selectedAsset.assetImageUrl}
                alt="Asset"
                className="w-full h-48 object-cover rounded mb-4"
              />
            )}
            <div className="space-y-2 text-sm">
              <p><strong>Category:</strong> {selectedAsset.category?.name}</p>
              <p><strong>Status:</strong> {selectedAsset.status?.name}</p>
              <p><strong>Purchase Date:</strong> {selectedAsset.purchaseDate}</p>
              <p><strong>Warranty Expiry:</strong> {selectedAsset.warrantyExpiryDate || 'N/A'}</p>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Assets;