package com.up42.featureService.repository.model;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.up42.featureService.util.TestConstants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "features")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureModel {
	@Id
	UUID id;
	
	@Column
	String quicklook;
	
	@Column(nullable = false)
	Timestamp timestamp;
	
	@Column(name="begin_viewing_date", nullable = false)
	Timestamp beginViewingDate;
	
	@Column(name="end_viewing_date", nullable = false)
	Timestamp endViewingDate;
	
	@Column(name="mission_name", nullable = false)
	String missionName;
	
	@JsonProperty("properties")
	@SuppressWarnings("unchecked")
	private void taxonomyDeserializer(Map<String, Object> properties) {
		this.id = UUID.fromString((String)properties.get("id"));
		this.quicklook = (String) properties.get("quicklook");
		this.timestamp = new Timestamp( (long) properties.get("timestamp"));
		
		Map<String, Object> acquisition = (Map<String, Object>) properties.get("acquisition");
		this.beginViewingDate = new Timestamp( (long) acquisition.get("beginViewingDate"));
		this.endViewingDate = new Timestamp( (long) acquisition.get("endViewingDate"));
		this.missionName = (String) acquisition.get("missionName");
	}
}
