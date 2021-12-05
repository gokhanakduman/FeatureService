package com.up42.featureService.util;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import com.up42.featureService.repository.model.FeatureModel;

public class TestConstants {
	public final static String QUICKLOOK_FILE_STRING = "iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABmJLR0QA/wD/AP+gvaeTAAABJElEQVRIieXUPUoDQRjG8R8qaKcgBsHKGPAAFoK2HkE9Qu5grXcQWysjaBsrrVIavYFFWkGNFpoiWuwGlt3ZuJtNIz7wws687/yf+dgZ/oNqOMEDPuLo4jjOVdIB+vjOiT72q8CHY+CjGE5iUvtl5ul4w0oINJtjcIS9VN8rznGPBhYSuXl84q7oCh5TM3zBeiJfjw2TNd2icHhPDT4N1JzJHnhGMzkGXwXq0n2DHFZQHdktqifyG7Jb1AmB5nIMbrGTaC+J9vgybh9iMTCmsBqK3YFkbJYxgHYJeLssHLZKrGJ7EgNoFYC3JoXDqugPGvdErFUxgOYYg2ZV+EgXAfjVtOBE9+ApAe9heZoGsCt6DgbxdyHlPdch9fCMG1yXmtqf1g/2CJPvQAzABQAAAABJRU5ErkJggg==";
	private static FeatureModel featureModelWithQuicklook = null;
	private static FeatureModel featureModelWithhoutQuicklook = null;
	private static List<FeatureModel> featureModelList = null;
	
	public final static FeatureModel getSampleFeatureModelWithQuicklook() {
		if (featureModelWithQuicklook == null) {
			featureModelWithQuicklook = FeatureModel.builder()
					.id(UUID.fromString("c3f58280-0fdb-4716-a8ca-fa8ad6609bee"))
					.timestamp(new Timestamp(1558155148786L))
					.beginViewingDate(new Timestamp(1558155148886L))
					.endViewingDate(new Timestamp(1558155148986L))
					.quicklook(QUICKLOOK_FILE_STRING)
					.missionName("mission name")
					.build();
		}
		return featureModelWithQuicklook;
	}
	
	public final static FeatureModel getSampleFeatureModelWithoutQuicklook() {
		if (featureModelWithhoutQuicklook == null) {
			featureModelWithhoutQuicklook = FeatureModel.builder()
					.id(UUID.fromString("b4cfdaef-1f0e-4f49-bd0b-68348ec8c966"))
					.timestamp(new Timestamp(1558155148754L))
					.beginViewingDate(new Timestamp(1558155149754L))
					.endViewingDate(new Timestamp(1558155150754L))
					.quicklook(null)
					.missionName("mission name 2")
					.build();
		}
		return featureModelWithhoutQuicklook;
	}
	
	public static final List<FeatureModel> getFeatureModelList() {
		if (featureModelList == null) {
			featureModelList = List.of(getSampleFeatureModelWithQuicklook(), getSampleFeatureModelWithoutQuicklook());
		}
		return featureModelList;
	}
	
}
