package com.asset.AssetManagement.service.Impl;

import com.asset.AssetManagement.entity.ArchivedAsset;
import com.asset.AssetManagement.entity.Asset;
import com.asset.AssetManagement.repository.ArchivedAssetRepository;
import com.asset.AssetManagement.repository.AssetRepository;
import com.asset.AssetManagement.scheduler.ExpiredAssetScheduler;
import com.asset.AssetManagement.service.ExpiredAssetService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ExpiredAssetServiceImpl implements ExpiredAssetService {
    private static final Logger logger = LoggerFactory.getLogger(ExpiredAssetScheduler.class);

    private final AssetRepository assetRepository;
    private final ArchivedAssetRepository archivedAssetRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ExpiredAssetServiceImpl(AssetRepository assetRepository, ArchivedAssetRepository archivedAssetRepository,ModelMapper modelMapper) {
        this.assetRepository = assetRepository;
        this.archivedAssetRepository = archivedAssetRepository;
        this.modelMapper= modelMapper;
    }

    @Transactional
    @Override
    public void moveExpiredAssets() {
        List<Asset> expiredAssets = assetRepository.findByExpireDateBefore(LocalDate.now());
        for (Asset asset : expiredAssets) {
            ArchivedAsset archived = modelMapper.map(asset, ArchivedAsset.class);
            archivedAssetRepository.save(archived);
            logger.info("Archived asset with ID: ");
        }
        assetRepository.deleteAll(expiredAssets);
    }
}
