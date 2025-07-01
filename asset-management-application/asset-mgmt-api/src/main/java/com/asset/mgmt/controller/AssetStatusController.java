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

import com.asset.mgmt.entity.AssetStatus;
import com.asset.mgmt.service.AssetStatusService;

@RestController
@RequestMapping("/asset-status")
public class AssetStatusController {
	
	@Autowired
	private AssetStatusService assetStatusService;
	
	@GetMapping
	public ResponseEntity<List<AssetStatus>> getAllAssetStatus() {
		try {
			List<AssetStatus> allAssetStatus = assetStatusService.getAllAssetStatus();
			
			if(allAssetStatus == null || allAssetStatus.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			return ResponseEntity.ok(allAssetStatus);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AssetStatus> getAssetStatusById(@PathVariable Integer id) {
		AssetStatus assetStatusById = assetStatusService.getAssetStatusById(id);
		if(assetStatusById.getId() != null) {
			return ResponseEntity.ok(assetStatusById);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping
	public ResponseEntity<AssetStatus> createAssetStatus(@RequestBody AssetStatus status) {
	    try {
	        AssetStatus saved = assetStatusService.addAssetStatus(status);
	        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	    } catch (DuplicateKeyException e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	
	@PutMapping
	public ResponseEntity<String> updateAssetStatus(@RequestBody AssetStatus status) {
		boolean updateAssetStatus = assetStatusService.updateAssetStatus(status);
		
		if(updateAssetStatus) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("status updated successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("status not updated");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAssetStatus(@PathVariable Integer id) {
		boolean deleteAssetStatus = assetStatusService.deleteAssetStatus(id);
		if(deleteAssetStatus) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Removed Successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Asset status not found");
	}
}
