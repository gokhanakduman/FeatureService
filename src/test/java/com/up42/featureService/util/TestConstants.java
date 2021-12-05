package com.up42.featureService.util;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import com.up42.featureService.repository.model.FeatureModel;

public class TestConstants {
	public static final String QUICKLOOK_FILE_STRING = "iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABmJLR0QA/wD/AP+gvaeTAAABJElEQVRIieXUPUoDQRjG8R8qaKcgBsHKGPAAFoK2HkE9Qu5grXcQWysjaBsrrVIavYFFWkGNFpoiWuwGlt3ZuJtNIz7wws687/yf+dgZ/oNqOMEDPuLo4jjOVdIB+vjOiT72q8CHY+CjGE5iUvtl5ul4w0oINJtjcIS9VN8rznGPBhYSuXl84q7oCh5TM3zBeiJfjw2TNd2icHhPDT4N1JzJHnhGMzkGXwXq0n2DHFZQHdktqifyG7Jb1AmB5nIMbrGTaC+J9vgybh9iMTCmsBqK3YFkbJYxgHYJeLssHLZKrGJ7EgNoFYC3JoXDqugPGvdErFUxgOYYg2ZV+EgXAfjVtOBE9+ApAe9heZoGsCt6DgbxdyHlPdch9fCMG1yXmtqf1g/2CJPvQAzABQAAAABJRU5ErkJggg==";
	public static final String NOT_EXISTING_FEATURE_ID = "b623d629-38ee-4c45-bb99-552302e19545";
	
	public static final FeatureModel sampleFeatureModelWithQuicklook = FeatureModel.builder()
			.id(UUID.fromString("c3f58280-0fdb-4716-a8ca-fa8ad6609bee"))
			.timestamp(new Timestamp(1558155148786L))
			.beginViewingDate(new Timestamp(1558155148886L))
			.endViewingDate(new Timestamp(1558155148986L))
			.quicklook(QUICKLOOK_FILE_STRING)
			.missionName("mission name")
			.build();
	
	public static final FeatureModel sampleFeatureModelWithoutQuicklook = FeatureModel.builder()
			.id(UUID.fromString("b4cfdaef-1f0e-4f49-bd0b-68348ec8c966"))
			.timestamp(new Timestamp(1558155148754L))
			.beginViewingDate(new Timestamp(1558155149754L))
			.endViewingDate(new Timestamp(1558155150754L))
			.quicklook(null)
			.missionName("mission name 2")
			.build();
	
	public static final List<FeatureModel> sampleFeatureModelList = List.of(
			sampleFeatureModelWithQuicklook, 
			sampleFeatureModelWithoutQuicklook
			);
	
	/*
	 source-data.json only includes these features with id for testing:
	 	{
	 		id:				 	b0d3bf6a-ff54-49e0-a4cb-e57dcb68d3b5
	 		timestamp:			1558155148786
	 		endViewingDate:		1558155173785
           beginViewingDate:	1558155148786
           missionName:		Sentinel-1A
           quicklook:			null
       },
       {
	 		id:				 	08a190bf-8c7e-4e94-a22c-7f3be11f642c
	 		timestamp:			1555044772083
	 		endViewingDate:		1555044797082
           beginViewingDate:	1555044772083
           missionName:		Sentinel-1B
           quicklook:			TestConstants.QUICKLOOK_FILE_STRING
       },
		
	 */
	
	public static final FeatureModel flywayPopulatedFeatureModelWithoutQuicklook = FeatureModel.builder()
			.id(UUID.fromString("b0d3bf6a-ff54-49e0-a4cb-e57dcb68d3b5"))
			.timestamp(new Timestamp(1558155148786L))
			.endViewingDate(new Timestamp(1558155173785L))
			.beginViewingDate(new Timestamp(1558155148786L))
			.missionName("Sentinel-1A")
			.quicklook(null).build();
	
	public static final FeatureModel flywayPopulatedFeatureModelWithQuicklook = FeatureModel.builder()
			.id(UUID.fromString("08a190bf-8c7e-4e94-a22c-7f3be11f642c"))
			.timestamp(new Timestamp(1555044772083L))
			.endViewingDate(new Timestamp(1555044797082L))
			.beginViewingDate(new Timestamp(1555044772083L))
			.missionName("Sentinel-1B")
			.quicklook(QUICKLOOK_FILE_STRING).build();
	
	
	public static final List<FeatureModel> featureModelsProvidedByFlyway = List.of(
			flywayPopulatedFeatureModelWithoutQuicklook,
			flywayPopulatedFeatureModelWithQuicklook
			);
	
}
