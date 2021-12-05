package com.up42.featureService.controller.dto;

import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "Convert Link Response Object", description = "Model")
public class FeatureResponseDTO {
	@ApiModelProperty(value = "Feature id")
	UUID id;
	@ApiModelProperty(value = "Timestamp")
	Long timestamp;
	@ApiModelProperty(value = "Begin Viewing Date")
	Long beginViewingDate;
	@ApiModelProperty(value = "End Viewing Date")
	Long endViewingDate;
	@ApiModelProperty(value = "Mission name")
	String missionName;
}
