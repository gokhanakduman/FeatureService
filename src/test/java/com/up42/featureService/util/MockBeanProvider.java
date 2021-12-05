package com.up42.featureService.util;

import static org.mockito.Mockito.when;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.mockito.Mockito;

import com.up42.featureService.exception.ImageValidationException;
import com.up42.featureService.repository.FeatureRepository;
import com.up42.featureService.service.FeatureService;

public class MockBeanProvider {
	
	public static FeatureService mockFeatureService() throws ImageValidationException {
		FeatureService featureService = Mockito.mock(FeatureService.class);		
		when(featureService.getAllFeatures())
		.thenReturn(TestConstants.getFeatureModelList());
		
		when(featureService.getFeatureById(TestConstants.getSampleFeatureModelWithQuicklook().getId().toString()))
		.thenReturn(TestConstants.getSampleFeatureModelWithQuicklook());
		
		when(featureService.getFeatureById(TestConstants.getSampleFeatureModelWithoutQuicklook().getId().toString()))
		.thenReturn(TestConstants.getSampleFeatureModelWithoutQuicklook());
		
		when(featureService.getFeatureById(TestConstants.getSampleFeatureModelWithQuicklook().getId().toString()))
		.thenReturn(TestConstants.getSampleFeatureModelWithQuicklook());
		
		when(featureService.getFeatureQuicklookImageById(TestConstants.getSampleFeatureModelWithoutQuicklook().getId().toString()))
		.thenReturn(null);
		
		when(featureService.getFeatureQuicklookImageById(TestConstants.getSampleFeatureModelWithQuicklook().getId().toString()))
		.thenReturn(Base64.getDecoder().decode(TestConstants.getSampleFeatureModelWithQuicklook().getQuicklook()));
		
		return featureService;
	}
	
	public static FeatureRepository mockFeatureRepository() {
		FeatureRepository featureRepository = Mockito.mock(FeatureRepository.class);
		when(featureRepository.findAll())
		.thenReturn(TestConstants.getFeatureModelList());
		
		when(featureRepository.findById(TestConstants.getSampleFeatureModelWithQuicklook().getId()))
		.thenReturn(Optional.of(TestConstants.getSampleFeatureModelWithQuicklook()));
		
		when(featureRepository.findById(TestConstants.getSampleFeatureModelWithoutQuicklook().getId()))
		.thenReturn(Optional.of(TestConstants.getSampleFeatureModelWithoutQuicklook()));
		
		return featureRepository;
		
	}
	
}
