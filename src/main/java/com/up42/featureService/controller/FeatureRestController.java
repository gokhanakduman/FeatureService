package com.up42.featureService.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.up42.featureService.controller.dto.FeatureResponseDTO;
import com.up42.featureService.exception.ImageValidationException;
import com.up42.featureService.repository.model.FeatureModel;
import com.up42.featureService.service.FeatureService;
import com.up42.featureService.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Feature Api")
@RestController
@Validated
public class FeatureRestController {
	
	private FeatureService featureService;
	
	private ModelMapper modelMapper =  new ModelMapper();
	
	@Autowired
	public FeatureRestController(FeatureService featureService) {
		this.featureService = featureService;
	}

	@GetMapping(value= "/v1/features" , produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get All Features")
	public @ResponseBody List<FeatureResponseDTO> getAllFeatures() {
		List<FeatureModel> imageMetadataList = featureService.getAllFeatures();
		return imageMetadataList.stream().map(
				imageMetadata -> convertFeatureToResponseDto(imageMetadata))
				.collect(Collectors.toList());
    }
	
	@GetMapping(value= "/v1/features/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get Feature By Id")
	public @ResponseBody FeatureResponseDTO getFeatureById(
			@PathVariable
			@NotNull
			@Pattern(regexp=Constants.UUID_V4_VALIDATOR_REGEX_STRING)
			@NotBlank 
			@Parameter(description = "Feature id")
			String id) {
		return convertFeatureToResponseDto(featureService.getFeatureById(id));
    }
	
	@GetMapping(value= "/v1/features/{id}/quicklook" , produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	@Operation(summary = "Get Feature Quicklook Image By Id")
	public @ResponseBody byte[] getFeatureQuicklookImageById(
			@PathVariable
			@NotNull
			@Pattern(regexp=Constants.UUID_V4_VALIDATOR_REGEX_STRING)
			@NotBlank
			@Parameter(description = "Feature id")
			String id) throws ImageValidationException, NoSuchElementException {
		return featureService.getFeatureQuicklookImageById(id);
    }
	
	private FeatureResponseDTO convertFeatureToResponseDto(FeatureModel featureModel) {
		return modelMapper.map(featureModel, FeatureResponseDTO.class);
	}
	
}
