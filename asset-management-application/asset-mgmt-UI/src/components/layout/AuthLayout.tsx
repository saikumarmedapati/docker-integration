import React from 'react';
import { Outlet } from 'react-router-dom';
import { Package } from 'lucide-react';

const AuthLayout: React.FC = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 via-white to-cyan-50 flex items-center justify-center p-4">
      <div className="w-full max-w-md">
        {/* Logo and Title */}
        <div className="text-center mb-8">
          <div className="flex justify-center mb-4">
            <div className="p-3 bg-indigo-600 rounded-full">
              <Package className="h-8 w-8 text-white" />
            </div>
          </div>
          <h1 className="text-2xl font-bold text-gray-900">Asset Manager</h1>
          <p className="text-gray-600 mt-2">Manage your assets with confidence</p>
        </div>

        {/* Auth Form Container */}
        <div className="bg-white shadow-xl rounded-lg p-8 border border-gray-100">
          <Outlet />
        </div>

        {/* Footer */}
        <div className="text-center mt-8 text-sm text-gray-500">
          <p>&copy; 2024 Asset Manager. All rights reserved.</p>
        </div>
      </div>
    </div>
  );
};

export default AuthLayout;