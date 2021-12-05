package com.up42.featureService.service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.up42.featureService.exception.ImageValidationException;
import com.up42.featureService.repository.FeatureRepository;
import com.up42.featureService.repository.model.FeatureModel;

@Service
public class FeatureService {
	
	FeatureRepository featureRepository;
	
	@Autowired
	public FeatureService(FeatureRepository featureRepository) {
		this.featureRepository = featureRepository;
	}
	
	public List<FeatureModel> getAllFeatures() {
		return featureRepository.findAll();
	}
	
	public FeatureModel getFeatureById(String featureId) {
		return featureRepository.findById(UUID.fromString(featureId)).get();
	}
	
	public byte[] getFeatureQuicklookImageById(String featureId) throws ImageValidationException {
		FeatureModel featureModel = featureRepository.findById(UUID.fromString(featureId)).get();
		if (featureModel.getQuicklook() == null) {
			return null;
		}
		try {
			return getBase64EncodedStringAsByteArray(featureModel.getQuicklook());
		} catch (IllegalArgumentException e) {
			throw new ImageValidationException();
		}
	}
	
	private byte[] getBase64EncodedStringAsByteArray(String base64encodedString) {
		return Base64.getDecoder().decode(base64encodedString);
	}
}
