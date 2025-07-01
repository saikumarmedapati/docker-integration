package com.asset.mgmt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asset.mgmt.entity.AssetStatus;

@Repository
public interface AssetStatusRepository extends JpaRepository<AssetStatus, Integer>{

	boolean existsByName(String name);

}
