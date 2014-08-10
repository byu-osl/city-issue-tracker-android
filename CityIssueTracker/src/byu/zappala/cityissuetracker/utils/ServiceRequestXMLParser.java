package byu.zappala.cityissuetracker.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.StatusLine;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import byu.zappala.cityissuetracker.model.ListServiceRequest;
import byu.zappala.cityissuetracker.model.ServiceRequest;
import android.util.Xml;

public class ServiceRequestXMLParser {
	public List<ServiceRequest> parseServiceRequestList(InputStream input) throws XmlPullParserException, IOException {
		try {
		XmlPullParser parser = Xml.newPullParser();
		
		parser.setInput(input, null);
		parser.nextTag();
		return readServiceRequestList(parser);
		} finally {
			input.close();
		}
		
	}

	private List<ServiceRequest> readServiceRequestList(XmlPullParser parser) throws XmlPullParserException, IOException {
		List<ServiceRequest> serviceRequests = new ArrayList<ServiceRequest>();
		parser.require(XmlPullParser.START_TAG, null, "service_requests");
		
		while (parser.next() != XmlPullParser.END_TAG) {

			if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
			
			String name = parser.getName();
		
			if (name.equals("request")) {
				serviceRequests.add(readRequest(parser));
			}
		
		}
		return serviceRequests;
	}

	private ServiceRequest readRequest(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "request");
		
		String serviceRequestID = "";
		String status = "";
		String statusNotes = "";
		String serviceName = "";
		String serviceCode = "";
		String description = "";
		String agencyResponsible = "";
		String serviceNotice = "";
		String requestedDatetime = "";
		String updatedDatetime = "";
		String expectedDatetime = "";
		String address = "";
		String addressID = "";
		String zipcode = "";
		double latitude = 0;
		double longitude = 0;
		String email = "";
		String mediaURL = "";
		
		while (parser.next() != XmlPullParser.END_TAG) {

			if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
			
			String name = parser.getName();
			
			if (name.equals("service_request_id")) {
				serviceRequestID = readServiceRequestID(parser);
			} else if(name.equals("status")) {
				status = readStatus(parser);
			} else if(name.equals("status_notes")) {
				statusNotes = readStatusNotes(parser);
			}  else if(name.equals("service_name")) {
				serviceName = readServiceName(parser);
			} else if(name.equals("service_code")) {
				serviceCode = readServiceCode(parser);
			} else if(name.equals("description")) {
				description = readDescription(parser);
			} else if(name.equals("agency_responsible")) {
				agencyResponsible = readAgencyResponsible(parser);
			} else if(name.equals("service_notice")) {
				serviceNotice = readServiceNotice(parser);
			} else if(name.equals("requested_datetime")) {
				requestedDatetime = readRequestedDatetime(parser);
			} else if(name.equals("updated_datetime")) {
				updatedDatetime = readUpdatedDatetime(parser);
			} else if(name.equals("expected_datetime")) {
				expectedDatetime = readExpectedDatetime(parser);
			} else if(name.equals("address")) {
				address = readAddress(parser);
			} else if(name.equals("address_id")) {
				addressID = readAddressID(parser);
			} else if(name.equals("zipcode")) {
				zipcode = readZipcode(parser);
			} else if(name.equals("lat")) {
				latitude = readLatitude(parser);
			} else if(name.equals("long")) {
				longitude = readLongitude(parser);
			} else if(name.equals("email")) {
				email = readEmail(parser);
			} else if(name.equals("media_url")) {
				mediaURL = readMediaURL(parser);
			}
		}
		return new ServiceRequest(serviceRequestID, status, statusNotes, serviceName, serviceCode, description, agencyResponsible, serviceNotice,
				requestedDatetime, updatedDatetime, expectedDatetime, address, addressID, zipcode, latitude, longitude, email, mediaURL);
	}


	private double readLatitude(XmlPullParser parser) throws XmlPullParserException, IOException {
		try {
			parser.require(XmlPullParser.START_TAG, null, "lat");
	    	double latitude = Double.parseDouble(readText(parser));
	    	parser.require(XmlPullParser.END_TAG, null, "lat");
	    	return latitude;
		}
	    	catch (NumberFormatException e) {
	    	return 0;
        }
	   
	}

	private double readLongitude(XmlPullParser parser) throws XmlPullParserException, IOException {
		try {
			parser.require(XmlPullParser.START_TAG, null, "long");
	    	double longitude = Double.parseDouble(readText(parser));
	    	parser.require(XmlPullParser.END_TAG, null, "long");
	    	return longitude;
		}
	    catch (NumberFormatException e) {
	    	return 0;
        }
	}
	
	private String readEmail(XmlPullParser parser) throws XmlPullParserException, IOException  {
		parser.require(XmlPullParser.START_TAG, null, "email");
	    String email = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "email");
	    return email;
	}
    
	private String readMediaURL(XmlPullParser parser) throws XmlPullParserException, IOException  {
		parser.require(XmlPullParser.START_TAG, null, "media_url");
	    String mediaURL = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "media_url");
	    return mediaURL;
	}

	private String readZipcode(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "zipcode");
	    String zipcode = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "zipcode");
	    return zipcode;
	}

	private String readAddressID(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "address_id");
	    String addressID = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "address_id");
	    return addressID;
	}

	private String readAddress(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "address");
	    String address = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "address");
	    return address;
	}

	private String readExpectedDatetime(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "expected_datetime");
	    String expectedDatetime = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "expected_datetime");
	    return expectedDatetime;
	}

	private String readUpdatedDatetime(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "updated_datetime");
	    String updatedDatetime = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "updated_datetime");
	    return updatedDatetime;
	}

	private String readRequestedDatetime(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "requested_datetime");
	    String requestedDatetime = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "requested_datetime");
	    return requestedDatetime;
	}

	private String readServiceNotice(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "service_notice");
	    String serviceNotice = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "service_notice");
	    return serviceNotice;
	}

	private String readAgencyResponsible(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "agency_responsible");
	    String agencyResponsible = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "agency_responsible");
	    return agencyResponsible;
	}

	private String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "description");
	    String description = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "description");
	    return description;
	}

	private String readServiceCode(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "service_code");
	    String serviceCode = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "service_code");
	    return serviceCode;
	}

	private String readServiceName(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "service_name");
	    String serviceName = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "service_name");
	    return serviceName;
	}

	private String readStatusNotes(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "status_notes");
	    String statusNotes = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "status_notes");
	    return statusNotes;
	}

	private String readStatus(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "status");
	    String status = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "status");
	    return status;
	}

	private String readServiceRequestID(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "service_request_id");
	    String serviceRequestID = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "service_request_id");
	    return serviceRequestID;
	}
	
	private String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		System.out.println(result);
		return result;
	}
}
