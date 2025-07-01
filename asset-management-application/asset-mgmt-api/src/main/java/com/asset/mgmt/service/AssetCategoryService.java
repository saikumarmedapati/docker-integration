package com.asset.mgmt.service;

import java.util.List;

import com.asset.mgmt.entity.AssetCategory;

public interface AssetCategoryService {
	
	List<AssetCategory> getAllAssetCatgories();
	
	AssetCategory getAssetCategoryById(Integer id);
	
	AssetCategory addAssetCategory(AssetCategory category);
	
	boolean updateAssetCategory(AssetCategory category);
	
	boolean deleteAssetCategeory(Integer id);
}
