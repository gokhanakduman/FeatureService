package com.up42.featureService.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.up42.featureService.repository.model.FeatureModel;

@Repository
public interface FeatureRepository extends JpaRepository<FeatureModel, UUID> {
	
}
