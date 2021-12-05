package com.up42.featureService.integration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.up42.featureService.controller.dto.FeatureResponseDTO;
import com.up42.featureService.repository.model.FeatureModel;
import com.up42.featureService.util.TestConstants;
import com.up42.featureService.util.TestUtils;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class FeatureRestControllerIntegrationTest {
	@Autowired
	MockMvc mvc;
	
	@Test
	public void givenFlywayPopulatedDb_whenGetAllFeatures_thenCorrectResponse() throws Exception {
		MvcResult mvcResult = mvc.perform(get("/v1/features"))
                .andExpect(status().isOk())
                // Instead of these expect statements, I preferred to thoroughly compare actual models and returned DTOs'
                // .andExpect(jsonPath("$[0].id", is(TestConstants.getFeatureModelList().get(0).getId().toString())))
				// .andExpect(jsonPath("$[1].id", is(TestConstants.getFeatureModelList().get(1).getId().toString())))
                .andReturn();
		
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<FeatureResponseDTO>> typeRef 
    		= new TypeReference<List< FeatureResponseDTO>>() {};
		
    	List<FeatureResponseDTO> responseDTOs = mapper.readValue(mvcResult.getResponse().getContentAsString(), typeRef);
		List<FeatureModel> featureModelList = TestConstants.featureModelsProvidedByFlyway;
		assertTrue(TestUtils.validateFeatureModelListAndFeatureResponseDTOList(featureModelList, responseDTOs));
	}
	
	@Test
	public void givenFlywayPopulatedDb_whenGetFeatureByIdWithExistingFeatureIdWithQuicklook_thenCorrectResponse() throws Exception {
		FeatureModel featureModelToTest = TestConstants.flywayPopulatedFeatureModelWithQuicklook;
		MvcResult mvcResult = mvc.perform(get("/v1/features/{id}", featureModelToTest.getId().toString()))
                .andExpect(status().isOk())
                .andReturn();
		
		ObjectMapper mapper = new ObjectMapper();
    	FeatureResponseDTO responseDTO = mapper.readValue(mvcResult.getResponse().getContentAsString(), FeatureResponseDTO.class);
		assertTrue(TestUtils.validateFeatureModelAndFeatureResponseDTO(featureModelToTest, responseDTO));
	}
	
	@Test
	public void givenFlywayPopulatedDb_whenGetFeatureByIdWithExistingFeatureIdWithoutQuicklook_thenCorrectResponse() throws Exception {
		FeatureModel featureModelToTest = TestConstants.flywayPopulatedFeatureModelWithoutQuicklook;
		MvcResult mvcResult = mvc.perform(get("/v1/features/{id}", featureModelToTest.getId().toString()))
                .andExpect(status().isOk())
                .andReturn();
		
		ObjectMapper mapper = new ObjectMapper();
    	FeatureResponseDTO responseDTO = mapper.readValue(mvcResult.getResponse().getContentAsString(), FeatureResponseDTO.class);
		assertTrue(TestUtils.validateFeatureModelAndFeatureResponseDTO(featureModelToTest, responseDTO));
	}
	
	@Test()
	public void givenFlywayPopulatedDb_whenGetFeatureByIdWithInvalidId_thenHttpStatusBadRequest() throws Exception {
		mvc.perform(get("/v1/features/{id}", "qwe123"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
	
	@Test()
	public void givenFlywayPopulatedDb_whenGetFeatureByIdWithNonExistingId_thenHttpStatusNotFound() throws Exception {
		mvc.perform(get("/v1/features/{id}", "b6ace629-38ee-4c45-bb99-552302e19545"))
        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	public void givenFlywayPopulatedDb_whenGetFeatureQuicklookByIdWithExistingFeatureIdWithQuicklook_thenCorrectResponse() throws Exception {
		FeatureModel featureModelToTest = TestConstants.flywayPopulatedFeatureModelWithQuicklook;
		MvcResult mvcResult = mvc.perform(get("/v1/features/{id}/quicklook", featureModelToTest.getId().toString()))
                .andExpect(status().isOk())
                .andReturn();
		
		byte[] responseBytes = mvcResult.getResponse().getContentAsByteArray();
		String encodedResponseString = new String(Base64.getEncoder().encode(responseBytes));
		assertEquals(encodedResponseString, featureModelToTest.getQuicklook());
	}
	
	@Test
	public void givenFlywayPopulatedDb_whenGetFeatureQuicklookByIdWithExistingFeatureIdWithoutQuicklook_thenNullResponse() throws Exception {
		FeatureModel featureModelToTest = TestConstants.flywayPopulatedFeatureModelWithoutQuicklook;
		mvc.perform(get("/v1/features/{id}/quicklook", featureModelToTest.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
	}
	
	@Test()
	public void givenFlywayPopulatedDb_whenGetFeatureQuicklookByIdWithInvalidId_thenHttpStatusNotFound() throws Exception {
		mvc.perform(get("/v1/features/{id}/quicklook", "b6ace629-38ee-4c45-bb99-552302e19545"))
        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test()
	public void givenFlywayPopulatedDb_whenGetFeatureQuicklookByIdWithNonExistingId_thenHttpStatusBadRequest() throws Exception {
		mvc.perform(get("/v1/features/{id}/quicklook", "qwe123"))
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
	
}
