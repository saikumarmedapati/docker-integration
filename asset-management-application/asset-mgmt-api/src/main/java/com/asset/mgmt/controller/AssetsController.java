package com.asset.mgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asset.mgmt.entity.Assets;
import com.asset.mgmt.service.AssetsService;

@RestController
@RequestMapping("/assets")
public class AssetsController {
	
	@Autowired
	private AssetsService assetsService;
	
	@GetMapping
	public ResponseEntity<List<Assets>> getAllAssets() {
		try {
			List<Assets> allAssets = assetsService.getAllAssets();
			
			if(allAssets == null || allAssets.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			return ResponseEntity.ok(allAssets);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<Page<Assets>> getAssetsByUserIdWithPagination(
	        @PathVariable Integer userId,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    Page<Assets> pagedAssets = assetsService.getAllAssetsByUserId(userId, page, size);
	    
	    if (pagedAssets.hasContent()) {
	        return ResponseEntity.ok(pagedAssets);
	    } else {
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	    }
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<Assets> getAssetById(@PathVariable Integer id) {
		
		Assets assetById = assetsService.getAssetById(id);
		if(assetById != null) {
			return ResponseEntity.ok(assetById);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping
	public ResponseEntity<Assets> createAsset(@RequestBody Assets asset) {
		return ResponseEntity.ok(assetsService.addAsset(asset));
	}
	
	@PutMapping
	public ResponseEntity<String> updateAsset(@RequestBody Assets asset) {
		boolean updateAsset = assetsService.updateAsset(asset);
		
		if(updateAsset) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Asset updated successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Asset not updated");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAsset(@PathVariable Integer id) {
		boolean deleteAsset = assetsService.deleteAsset(id);
		if(deleteAsset) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Removed Successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset not found");
	}
	
}
