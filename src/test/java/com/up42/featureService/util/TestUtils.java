package com.up42.featureService.util;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.CompareToBuilder;

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
	
	public static final boolean validateFeatureModelLists(List<FeatureModel> first, List<FeatureModel> second) {
		if (first.size() == second.size()) {
			List<FeatureModel> firstSorted = first.stream().sorted((lhs, rhs) -> lhs.getId().compareTo(rhs.getId())).collect(Collectors.toList());
			List<FeatureModel> secondSorted = second.stream().sorted((lhs, rhs) -> lhs.getId().compareTo(rhs.getId())).collect(Collectors.toList());
			for(int i = 0; i < firstSorted.size() ;i++) {
				boolean isValid = CompareToBuilder.reflectionCompare(firstSorted.get(i), secondSorted.get(i)) == 0;
				if (isValid == false) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public static final Timestamp getRandomTimestamp() {
		long offset = Timestamp.valueOf("2021-12-05 00:00:00").getTime();
		long end = Timestamp.valueOf("2021-01-01 00:00:00").getTime();
		long diff = end - offset + 1;
		return new Timestamp(offset + (long)(Math.random() * diff));
	}
}
