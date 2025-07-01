package com.asset.mgmt.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asset.mgmt.entity.Assets;

@Repository
public interface AssetsRepository extends JpaRepository<Assets, Integer>{
	
	Page<Assets> findByUserId(Integer userId, Pageable pageable);
}
