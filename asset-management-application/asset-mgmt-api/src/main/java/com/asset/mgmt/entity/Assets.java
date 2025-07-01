package com.asset.mgmt.entity;

import java.time.Instant;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assets", schema = "assetmgmt")
@EntityListeners(AuditingEntityListener.class)
public class Assets {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;
	
	@Column(name = "asset_name", nullable = false)
	private String assetName;
	
	@ManyToOne
	@JoinColumn(name = "category", nullable = false)
	private AssetCategory category;
	
	@ManyToOne
	@JoinColumn(name = "status", nullable = false)
	private AssetStatus status;
	
	@Column(name = "purchase_date", nullable = false)
	private LocalDate purchaseDate;
	
	@Column(name = "warranty_expiry_date", nullable = true)
	private LocalDate warrantyExpiryDate;
	
	@Column(name = "asset_image_url", nullable = true)
	private String assetImageUrl;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	@CreatedDate
	private Instant createdAt;
	
	@Column(name = "updated_at", nullable = false)
	@LastModifiedDate
	private Instant updatedAt;
	
	// setters and getters
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public AssetCategory getCategory() {
		return category;
	}

	public void setCategory(AssetCategory category) {
		this.category = category;
	}

	public AssetStatus getStatus() {
		return status;
	}

	public void setStatus(AssetStatus status) {
		this.status = status;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public LocalDate getWarrantyExpiryDate() {
		return warrantyExpiryDate;
	}

	public void setWarrantyExpiryDate(LocalDate warrantyExpiryDate) {
		this.warrantyExpiryDate = warrantyExpiryDate;
	}

	public String getAssetImageUrl() {
		return assetImageUrl;
	}

	public void setAssetImageUrl(String assetImageUrl) {
		this.assetImageUrl = assetImageUrl;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
