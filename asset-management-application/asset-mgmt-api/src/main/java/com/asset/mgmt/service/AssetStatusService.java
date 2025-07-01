package com.asset.mgmt.service;

import java.util.List;

import com.asset.mgmt.entity.AssetStatus;

public interface AssetStatusService {
	
	List<AssetStatus> getAllAssetStatus();
	
	AssetStatus getAssetStatusById(Integer id);
	
	AssetStatus addAssetStatus(AssetStatus assetStatus);
	
	boolean updateAssetStatus(AssetStatus assetStatus);
	
	boolean deleteAssetStatus(Integer id);
	
}
