package com.up42.featureService.integration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ActiveProfiles;

import com.up42.featureService.repository.FeatureRepository;
import com.up42.featureService.repository.model.FeatureModel;
import com.up42.featureService.util.TestConstants;
import com.up42.featureService.util.TestUtils;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FeatureRepositoryIntegrationTest {
	@Resource
	private FeatureRepository featureRepository;
	
	@Test
	public void whenSaveValidFeatureWithQuicklook_thenCorrectResponse() {
		FeatureModel featureModelToSave = FeatureModel.builder()
				.id(UUID.randomUUID())
				.beginViewingDate(TestUtils.getRandomTimestamp())
				.endViewingDate(TestUtils.getRandomTimestamp())
				.timestamp(TestUtils.getRandomTimestamp())
				.missionName("mission name 1")
				.quicklook(TestConstants.QUICKLOOK_FILE_STRING)
				.build();
		FeatureModel savedFeatureModel = featureRepository.save(featureModelToSave);
		assertEquals(0, CompareToBuilder.reflectionCompare(featureModelToSave, savedFeatureModel));
	}
	
	@Test
	public void whenSaveValidFeatureWithoutQuicklook_thenCorrectResponse() {
		FeatureModel featureModelToSave = FeatureModel.builder()
				.id(UUID.randomUUID())
				.beginViewingDate(TestUtils.getRandomTimestamp())
				.endViewingDate(TestUtils.getRandomTimestamp())
				.timestamp(TestUtils.getRandomTimestamp())
				.missionName("mission name 2")
				.quicklook(null)
				.build();
		FeatureModel savedFeatureModel = featureRepository.save(featureModelToSave);
		assertEquals(0, CompareToBuilder.reflectionCompare(featureModelToSave, savedFeatureModel));
	}
	
	@Test
	public void whenSaveInvalidFeatureWithoutId_thenJpaSystemException() {
		FeatureModel featureModelToSave = FeatureModel.builder()
				.id(null)
				.beginViewingDate(TestUtils.getRandomTimestamp())
				.endViewingDate(TestUtils.getRandomTimestamp())
				.timestamp(TestUtils.getRandomTimestamp())
				.missionName("mission name 1")
				.quicklook(TestConstants.QUICKLOOK_FILE_STRING)
				.build();
		assertThrows(JpaSystemException.class, () -> {
			featureRepository.save(featureModelToSave);
		});
	}
	
	@Test
	public void givenFlywayPopulatedDb_whenFindAll_ThenCorrectResponse() {
		List<FeatureModel> fetchedList = featureRepository.findAll();
		assertTrue(TestUtils.validateFeatureModelLists(fetchedList, TestConstants.featureModelsProvidedByFlyway));
	}
	
	@Test
	public void givenFlywayPopulatedDb_whenFindById_ThenCorrectResponse() {
		FeatureModel featureModel = featureRepository.findById(TestConstants.flywayPopulatedFeatureModelWithQuicklook.getId()).get();
		assertEquals(0, CompareToBuilder.reflectionCompare(featureModel, TestConstants.flywayPopulatedFeatureModelWithQuicklook));
	}
}
