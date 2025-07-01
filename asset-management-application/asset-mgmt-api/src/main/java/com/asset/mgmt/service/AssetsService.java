package com.asset.mgmt.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.asset.mgmt.entity.Assets;

public interface AssetsService {
	
	List<Assets> getAllAssets();
	
	Assets getAssetById(Integer id);
	
	Page<Assets> getAllAssetsByUserId(Integer userId, int page, int size);
	
	Assets addAsset(Assets asset);
	
	boolean updateAsset(Assets asset);
	
	boolean deleteAsset(Integer id);
}
