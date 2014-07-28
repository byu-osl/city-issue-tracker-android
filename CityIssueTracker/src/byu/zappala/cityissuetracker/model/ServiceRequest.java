package byu.zappala.cityissuetracker.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ServiceRequest implements Serializable  {
	
	String serviceRequestID;
	String status;
	String statusNotes;
	String serviceName;
	String serviceCode;
	String description;
	String agencyResponsible;
	String serviceNotice;
	String requestedDatetime;
	String updatedDatetime;
	String expectedDatetime;
	String address;
	String addressID;
	String zipcode;
	double latitude;
	double longitude;
	String mediaURL;
	
	public ServiceRequest(String serviceRequestID, String status,
			String statusNotes, String serviceName, String serviceCode,
			String description, String agencyResponsible,
			String serviceNotice, String requestedDatetime,
			String updatedDatetime, String expectedDatetime, String address,
			String addressID, String zipcode, double latitude,
			double longitude, String mediaURL) {
		
		this.serviceRequestID = serviceRequestID;
		this.status = status;
		this.statusNotes = statusNotes;
		this.serviceName = serviceName;
		this.serviceCode = serviceCode;
		this.description = description;
		this.agencyResponsible = agencyResponsible;
		this.serviceNotice = serviceNotice;
		this.requestedDatetime = requestedDatetime;
		this.updatedDatetime = updatedDatetime;
		this.expectedDatetime = expectedDatetime;
		this.address = address;
		this.addressID = addressID;
		this.zipcode = zipcode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.mediaURL = mediaURL;
		
	}
	
	@Override
	public String toString() {
		//return "Hello!";
		return String.format("Service Request ID: %s, Status: %s, Status Notes: %s, Service Name: %s, "
				           + "Service Code: %s, Description: %s, Agency Responsible: %s, Service Notice: %s, "
				           + "Requested Datetime: %s, Updated Datetime: %s, Expected Datetime: %s, Address: %s, "
				           + "Address ID: %s, Zipcode: %s, Latitude: %f, Longitude: %f, Media URL: %s", 
				           serviceRequestID, status, statusNotes, serviceName, serviceCode, description, 
				           agencyResponsible, serviceNotice, requestedDatetime, updatedDatetime,
				           expectedDatetime, address, addressID, zipcode, latitude, longitude, mediaURL);	
	}
	
	public String getServiceRequestID() {
		return serviceRequestID;
	}
	public void setServiceRequestID(String serviceRequestID) {
		this.serviceRequestID = serviceRequestID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusNotes() {
		return statusNotes;
	}
	public void setStatusNotes(String statusNotes) {
		this.statusNotes = statusNotes;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAgencyResponsible() {
		return agencyResponsible;
	}
	public void setAgencyResponsible(String agencyResponsible) {
		this.agencyResponsible = agencyResponsible;
	}
	public String getServiceNotice() {
		return serviceNotice;
	}
	public void setServiceNotice(String serviceNotice) {
		this.serviceNotice = serviceNotice;
	}
	public String getRequestedDatetime() {
		return requestedDatetime;
	}
	public void setRequestedDatetime(String requestedDatetime) {
		this.requestedDatetime = requestedDatetime;
	}
	public String getUpdatedDatetime() {
		return updatedDatetime;
	}
	public void setUpdatedDatetime(String updatedDatetime) {
		this.updatedDatetime = updatedDatetime;
	}
	public String getExpectedDatetime() {
		return expectedDatetime;
	}
	public void setExpectedDatetime(String expectedDatetime) {
		this.expectedDatetime = expectedDatetime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddressID() {
		return addressID;
	}
	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getMediaURL() {
		return mediaURL;
	}
	public void setMediaURL(String mediaURL) {
		this.mediaURL = mediaURL;
	}
	
}
