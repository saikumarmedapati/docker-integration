package com.asset.mgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.mgmt.entity.AssetCategory;
import com.asset.mgmt.service.AssetCategoryService;

@RestController
@RequestMapping("/asset-category")
public class AssetCategoryController {
	
	@Autowired
	private AssetCategoryService assetCategoryService;
	
	@GetMapping
	public ResponseEntity<List<AssetCategory>> getAllAssetCategories() {
		try {
			List<AssetCategory> allAssetCatgories = assetCategoryService.getAllAssetCatgories();
			
			if(allAssetCatgories == null || allAssetCatgories.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			return ResponseEntity.ok(allAssetCatgories);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AssetCategory> getAssetCategoryById(@PathVariable Integer id) {
		AssetCategory assetCategoryById = assetCategoryService.getAssetCategoryById(id);
		if(assetCategoryById.getId() != null) {
			return ResponseEntity.ok(assetCategoryById);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping
	public ResponseEntity<AssetCategory> createAssetCategory(@RequestBody AssetCategory category) {
	    try {
	        AssetCategory saved = assetCategoryService.addAssetCategory(category);
	        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	    } catch (DuplicateKeyException e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	
	@PutMapping
	public ResponseEntity<String> updateAssetCategory(@RequestBody AssetCategory category) {
		boolean updateAssetCategory = assetCategoryService.updateAssetCategory(category);
		
		if(updateAssetCategory) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("category updated successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("category not updated");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAssetCategory(@PathVariable Integer id) {
		boolean deleteAssetCategeory = assetCategoryService.deleteAssetCategeory(id);
		if(deleteAssetCategeory) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Removed Successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("category not found");
	}
	
}
