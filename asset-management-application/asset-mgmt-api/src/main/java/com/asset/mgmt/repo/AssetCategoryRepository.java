package com.asset.mgmt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asset.mgmt.entity.AssetCategory;

@Repository
public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Integer>{

	boolean existsByName(String name);

}
