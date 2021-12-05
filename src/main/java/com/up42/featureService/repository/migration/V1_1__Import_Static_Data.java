package com.up42.featureService.repository.migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.up42.featureService.repository.model.FeatureModel;

public class V1_1__Import_Static_Data extends BaseJavaMigration {

	private final static String resourceFilePath = "classpath:db/migration/source-data.json";
	
	public void migrate(Context context) throws Exception{
		JdbcTemplate template = new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true));
		List<FeatureModel> featureModels = readFeaturesFromSourceFile();
		for(FeatureModel featureModel: featureModels) { 
			template.update("INSERT INTO features VALUES(?,?,?,?,?,?)", 
					featureModel.getId(),
					featureModel.getTimestamp(),
					featureModel.getBeginViewingDate(),
					featureModel.getEndViewingDate(),
					featureModel.getMissionName(),
					featureModel.getQuicklook());
		}
	}
	
	private List<FeatureModel> readFeaturesFromSourceFile() throws StreamReadException, DatabindException, IOException{
		File file = ResourceUtils.getFile(resourceFilePath);
        InputStream in = new FileInputStream(file);
        ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(in);
		List<JsonNode> featureNodes = extractFeatureNodesFromRootNode(rootNode);
		List<FeatureModel> featureModelList = new ArrayList<>();
		for(JsonNode node : featureNodes) {
			featureModelList.add(objectMapper.treeToValue(node, FeatureModel.class));
		}
		return featureModelList;
	}
	
	private List<JsonNode> extractFeatureNodesFromRootNode(JsonNode rootNode) throws IOException {
		List<JsonNode> featureNodes = new ArrayList<>(); 
		rootNode.elements().forEachRemaining(featureCollectionNode -> {
			JsonNode featuresListNode = featureCollectionNode.get("features");
			featuresListNode.elements().forEachRemaining(featureNode -> {
				featureNodes.add(featureNode);
			});
		});
		return featureNodes;
	}
	
}
