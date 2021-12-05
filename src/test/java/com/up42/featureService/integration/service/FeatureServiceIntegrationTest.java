package com.up42.featureService.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.up42.featureService.exception.ImageValidationException;
import com.up42.featureService.repository.model.FeatureModel;
import com.up42.featureService.service.FeatureService;
import com.up42.featureService.util.TestConstants;
import com.up42.featureService.util.TestUtils;

@SpringBootTest
@ActiveProfiles("test")
public class FeatureServiceIntegrationTest {
	@Autowired
	FeatureService featureService;

	@Test
	public void givenFlywayPopulatedDb_whenGetAllFeatures_thenCorrectResponse() {
		List<FeatureModel> featureModelList = featureService.getAllFeatures();
		assertTrue(TestUtils.validateFeatureModelLists(featureModelList, TestConstants.featureModelsProvidedByFlyway));
	}
	
	@Test
	public void givenFlywayPopulatedDb_whenGetFeatureByIdWithExistingFeatureIdWithQuicklook_thenCorrectResponse() {
		FeatureModel featureToTest = TestConstants.flywayPopulatedFeatureModelWithQuicklook;
		FeatureModel returnedModel = featureService.getFeatureById(featureToTest.getId().toString());
		assertEquals(0, CompareToBuilder.reflectionCompare(returnedModel, featureToTest));
	}
	
	@Test
	public void givenFlywayPopulatedDb_whenGetFeatureByIdWithExistingFeatureIdWithoutQuicklook_thenCorrectResponse() {
		FeatureModel featureToTest = TestConstants.flywayPopulatedFeatureModelWithoutQuicklook;
		FeatureModel returnedModel = featureService.getFeatureById(featureToTest.getId().toString());
		assertEquals(0, CompareToBuilder.reflectionCompare(returnedModel, featureToTest));
	}
	
	@Test()
	public void givenFlywayPopulatedDb_whenGetFeatureByIdWithNonExistingId_thenNoSuchElementException() {
		assertThrows(NoSuchElementException.class, () -> {
			  featureService.getFeatureById("b6ace629-38ee-4c45-bb99-552302e19545");
		});
	}
	
	@Test
	public void givenFlywayPopulatedDb_whenGetFeatureQuicklookByIdWithExistingFeatureIdWithQuicklook_thenCorrectResponse() throws ImageValidationException {
		FeatureModel featureToTest = TestConstants.flywayPopulatedFeatureModelWithQuicklook;
		byte[] responseBytes = featureService.getFeatureQuicklookImageById(featureToTest.getId().toString());
		String encodedResponseString = new String(Base64.getEncoder().encode(responseBytes));
		assertEquals(encodedResponseString, featureToTest.getQuicklook());
	}
	
	@Test
	public void givenFlywayPopulatedDb_whenGetFeatureQuicklookByIdWithExistingFeatureIdWithoutQuicklook_thenNullResponse() throws ImageValidationException {
		byte[] responseBytes = featureService.getFeatureQuicklookImageById(TestConstants.flywayPopulatedFeatureModelWithoutQuicklook.getId().toString());
		assertNull(responseBytes);
	}
	
	@Test()
	public void whenGetFeatureQuicklookByIdWithNonExistingId_thenNoSuchElementException() {
		assertThrows(NoSuchElementException.class, () -> {
			  featureService.getFeatureQuicklookImageById("b6ace629-38ee-4c45-bb99-552302e19545");
		});
	}
}
