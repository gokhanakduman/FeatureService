package com.up42.featureService.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.junit.jupiter.api.Test;

import com.up42.featureService.exception.ImageValidationException;
import com.up42.featureService.repository.model.FeatureModel;
import com.up42.featureService.service.FeatureService;
import com.up42.featureService.util.MockBeanProvider;
import com.up42.featureService.util.TestConstants;

public class FeatureServiceUnitTest {
	private FeatureService featureService = new FeatureService(MockBeanProvider.mockFeatureRepository());
	
	@Test
	public void whenGetAllFeatures_thenCorrectResponse() {
		List<FeatureModel> featureModelList = featureService.getAllFeatures();
		assertEquals(0, CompareToBuilder.reflectionCompare(featureModelList, TestConstants.getFeatureModelList()));
	}
	
	@Test
	public void whenGetFeatureByIdWithExistingFeatureIdWithQuicklook_thenCorrectResponse() {
		FeatureModel featureToTest = TestConstants.getSampleFeatureModelWithQuicklook();
		FeatureModel returnedModel = featureService.getFeatureById(featureToTest.getId().toString());
		assertEquals(0, CompareToBuilder.reflectionCompare(returnedModel, featureToTest));
	}
	
	@Test
	public void whenGetFeatureByIdWithExistingFeatureIdWithoutQuicklook_thenCorrectResponse() {
		FeatureModel featureToTest = TestConstants.getSampleFeatureModelWithoutQuicklook();
		FeatureModel returnedModel = featureService.getFeatureById(featureToTest.getId().toString());
		assertEquals(0, CompareToBuilder.reflectionCompare(returnedModel, featureToTest));
	}
	
	@Test()
	public void whenGetFeatureByIdWithNonExistingId_thenNoSuchElementException() {
		assertThrows(NoSuchElementException.class, () -> {
			  featureService.getFeatureById("b6ace629-38ee-4c45-bb99-552302e19545");
		});
	}
	
	@Test
	public void whenGetFeatureQuicklookByIdWithExistingFeatureIdWithQuicklook_thenCorrectResponse() throws ImageValidationException {
		FeatureModel featureToTest = TestConstants.getSampleFeatureModelWithQuicklook();
		byte[] responseBytes = featureService.getFeatureQuicklookImageById(featureToTest.getId().toString());
		String encodedResponseString = new String(Base64.getEncoder().encode(responseBytes));
		assertEquals(encodedResponseString, featureToTest.getQuicklook());
	}
	
	@Test
	public void whenGetFeatureQuicklookByIdWithExistingFeatureIdWithoutQuicklook_thenNullResponse() throws ImageValidationException {
		byte[] responseBytes = featureService.getFeatureQuicklookImageById(TestConstants.getSampleFeatureModelWithoutQuicklook().getId().toString());
		assertNull(responseBytes);
	}
	
	@Test()
	public void whenGetFeatureQuicklookByIdWithNonExistingId_thenNoSuchElementException() {
		assertThrows(NoSuchElementException.class, () -> {
			  featureService.getFeatureQuicklookImageById("b6ace629-38ee-4c45-bb99-552302e19545");
		});
	}
}
