package com.asset.AssetManagement.repository;

import com.asset.AssetManagement.entity.ArchivedAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivedAssetRepository extends JpaRepository<ArchivedAsset,Long> {
}
