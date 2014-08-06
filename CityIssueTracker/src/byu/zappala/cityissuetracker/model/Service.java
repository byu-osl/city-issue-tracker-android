package byu.zappala.cityissuetracker.model;

import java.util.List;

public class Service {
	String serviceCode;
	boolean metadata;
	String type;
	List<String> keywords;
	String group;
	String serviceName;
	String description;
	
	
	public Service(String serviceCode, boolean metadata, String type,
			List<String> keywords, String group, String serviceName,
			String description) {
		this.serviceCode = serviceCode;
		this.metadata = metadata;
		this.type = type;
		this.keywords = keywords;
		this.group = group;
		this.serviceName = serviceName;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return serviceName;
		/*return String.format("Service Code: %s, Metadata: %s, Type: %s, Keywords: %s, "
				           + "Group: %s, Service Name: %s, Description: %s", 
				           serviceCode, metadata, type, keywords.toString(), group, serviceName, description);*/	
	}
	
	
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public boolean isMetadata() {
		return metadata;
	}
	public void setMetadata(boolean metadata) {
		this.metadata = metadata;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
