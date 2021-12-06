package com.up42.featureService.controller.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(title = "Convert Link Response Object", description = "FeatureResponseDTO")
public class FeatureResponseDTO {
	@Schema(description = "Feature id")
	UUID id;
	@Schema(description = "Timestamp")
	Long timestamp;
	@Schema(description = "Begin Viewing Date")
	Long beginViewingDate;
	@Schema(description = "End Viewing Date")
	Long endViewingDate;
	@Schema(description = "Mission name")
	String missionName;
}
