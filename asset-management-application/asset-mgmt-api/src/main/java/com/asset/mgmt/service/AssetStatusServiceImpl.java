package com.asset.mgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.asset.mgmt.entity.AssetStatus;
import com.asset.mgmt.repo.AssetStatusRepository;

import jakarta.transaction.Transactional;

@Service
public class AssetStatusServiceImpl implements AssetStatusService{
	
	@Autowired
	private AssetStatusRepository assetStatusRepo;
	
	@Override
	public List<AssetStatus> getAllAssetStatus() {
		return assetStatusRepo.findAll();
	}

	@Override
	public AssetStatus getAssetStatusById(Integer id) {
		return assetStatusRepo.findById(id).get();
	}

	@Override
	@Transactional
	public AssetStatus addAssetStatus(AssetStatus assetStatus) {
		if(assetStatusRepo.existsByName(assetStatus.getName())) {
			throw new DuplicateKeyException("Asset status with this name already exists");
		}
		return assetStatusRepo.save(assetStatus);
	}

	@Override
	@Transactional
	public boolean updateAssetStatus(AssetStatus assetStatus) {
		if(assetStatusRepo.existsById(assetStatus.getId())) {
			assetStatusRepo.save(assetStatus);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean deleteAssetStatus(Integer id) {
		if(assetStatusRepo.existsById(id)) {
			assetStatusRepo.deleteById(id);
			return true;
		}
		return false;
	}

}
