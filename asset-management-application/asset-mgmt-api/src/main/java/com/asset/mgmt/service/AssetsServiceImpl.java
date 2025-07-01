package com.asset.mgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.asset.mgmt.entity.Assets;
import com.asset.mgmt.repo.AssetsRepository;

import jakarta.transaction.Transactional;

@Service
public class AssetsServiceImpl implements AssetsService{
	
	@Autowired
	private AssetsRepository assetsRepo;

	@Override
	public List<Assets> getAllAssets() {
		return assetsRepo.findAll();
	}

	@Override
	public Assets getAssetById(Integer id) {
		return assetsRepo.findById(id).get();
	}
	
	@Override
	public Page<Assets> getAllAssetsByUserId(Integer userId, int page, int size) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
	    return assetsRepo.findByUserId(userId, pageable);
	}

	@Override
	@Transactional
	public Assets addAsset(Assets asset) {
		return assetsRepo.save(asset);
	}

	@Override
	@Transactional
	public boolean updateAsset(Assets asset) {
		if(assetsRepo.existsById(asset.getId())) {
			assetsRepo.save(asset);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean deleteAsset(Integer id) {
		if(assetsRepo.existsById(id)) {
			assetsRepo.deleteById(id);
			return true;
		}
		return false;
	}
	
}
