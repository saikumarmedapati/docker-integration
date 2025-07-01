package com.asset.mgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.asset.mgmt.entity.AssetCategory;
import com.asset.mgmt.repo.AssetCategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class AssetCategoryServiceImpl implements AssetCategoryService{
	
	@Autowired
	private AssetCategoryRepository assetCategoryRepo;
	
	@Override
	public List<AssetCategory> getAllAssetCatgories() {
		return assetCategoryRepo.findAll();
	}

	@Override
	public AssetCategory getAssetCategoryById(Integer id) {
		return assetCategoryRepo.findById(id).get();
	}

	@Override
	@Transactional
	public AssetCategory addAssetCategory(AssetCategory category) {
		if (assetCategoryRepo.existsByName(category.getName())) {
            throw new DuplicateKeyException("Asset category with this name already exists");
        }
		return assetCategoryRepo.save(category);
	}

	@Override
	@Transactional
	public boolean updateAssetCategory(AssetCategory category) {
		if(assetCategoryRepo.existsById(category.getId())) {
			assetCategoryRepo.save(category);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean deleteAssetCategeory(Integer id) {
		if(assetCategoryRepo.existsById(id)) {
			assetCategoryRepo.deleteById(id);
			return true;
		}
		return false;
	}

}
