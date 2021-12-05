package com.up42.featureService.util;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.up42.featureService.exception.ImageValidationException;
import com.up42.featureService.repository.FeatureRepository;
import com.up42.featureService.service.FeatureService;

public class MockBeanProvider {
	
	public static FeatureService mockFeatureService() throws ImageValidationException {
		FeatureService featureService = Mockito.mock(FeatureService.class);		
		when(featureService.getAllFeatures())
		.thenReturn(TestConstants.sampleFeatureModelList);
		
		when(featureService.getFeatureById(TestConstants.sampleFeatureModelWithQuicklook.getId().toString()))
		.thenReturn(TestConstants.sampleFeatureModelWithQuicklook);
		
		when(featureService.getFeatureById(TestConstants.sampleFeatureModelWithoutQuicklook.getId().toString()))
		.thenReturn(TestConstants.sampleFeatureModelWithoutQuicklook);
		
		when(featureService.getFeatureById(TestConstants.sampleFeatureModelWithQuicklook.getId().toString()))
		.thenReturn(TestConstants.sampleFeatureModelWithQuicklook);
		
		when(featureService.getFeatureQuicklookImageById(TestConstants.sampleFeatureModelWithoutQuicklook.getId().toString()))
		.thenReturn(null);
		
		when(featureService.getFeatureQuicklookImageById(TestConstants.sampleFeatureModelWithQuicklook.getId().toString()))
		.thenReturn(Base64.getDecoder().decode(TestConstants.sampleFeatureModelWithQuicklook.getQuicklook()));
		
		when(featureService.getFeatureById( argThat(i -> !Arrays.asList(
				TestConstants.sampleFeatureModelWithQuicklook.getId().toString(),
				TestConstants.sampleFeatureModelWithoutQuicklook.getId().toString()
				).contains(i)))).thenThrow(NoSuchElementException.class);
		
		when(featureService.getFeatureQuicklookImageById( argThat(i -> !Arrays.asList(
				TestConstants.sampleFeatureModelWithQuicklook.getId().toString(),
				TestConstants.sampleFeatureModelWithoutQuicklook.getId().toString()
				).contains(i)))).thenThrow(NoSuchElementException.class);
		
		return featureService;
	}
	
	public static FeatureRepository mockFeatureRepository() {
		FeatureRepository featureRepository = Mockito.mock(FeatureRepository.class);
		when(featureRepository.findAll())
		.thenReturn(TestConstants.sampleFeatureModelList);
		
		when(featureRepository.findById(TestConstants.sampleFeatureModelWithQuicklook.getId()))
		.thenReturn(Optional.of(TestConstants.sampleFeatureModelWithQuicklook));
		
		when(featureRepository.findById(TestConstants.sampleFeatureModelWithoutQuicklook.getId()))
		.thenReturn(Optional.of(TestConstants.sampleFeatureModelWithoutQuicklook));
		
		return featureRepository;
		
	}
	
}
