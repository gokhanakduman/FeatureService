package com.up42.featureService.util;

import java.util.List;
import java.util.stream.Collectors;

import com.up42.featureService.controller.dto.FeatureResponseDTO;
import com.up42.featureService.repository.model.FeatureModel;

public class TestUtils {
	public static final boolean validateFeatureModelAndFeatureResponseDTO(FeatureModel model, FeatureResponseDTO dto) {
		return
				model.getId().equals(dto.getId()) &&
				model.getBeginViewingDate().getTime() == dto.getBeginViewingDate() &&
				model.getTimestamp().getTime() == dto.getTimestamp() &&
				model.getEndViewingDate().getTime() == dto.getEndViewingDate() &&
				model.getMissionName().equals(dto.getMissionName());
	}
	
	public static final boolean validateFeatureModelListAndFeatureResponseDTOList(List<FeatureModel> modelList, List<FeatureResponseDTO> dtoList) {
		if (modelList.size() == dtoList.size()) {
			List<FeatureModel> sortedModelList = modelList.stream().sorted((lhs, rhs) -> lhs.getId().compareTo(rhs.getId())).collect(Collectors.toList());
			List<FeatureResponseDTO> sortedDtoList = dtoList.stream().sorted((lhs, rhs) -> lhs.getId().compareTo(rhs.getId())).collect(Collectors.toList());
			
			for(int i = 0; i < sortedModelList.size() ;i++) {
				boolean isValid = validateFeatureModelAndFeatureResponseDTO(sortedModelList.get(i), sortedDtoList.get(i));
				if (isValid == false) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
