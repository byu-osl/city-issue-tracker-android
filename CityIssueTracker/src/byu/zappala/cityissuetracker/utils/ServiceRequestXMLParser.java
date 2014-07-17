package byu.zappala.cityissuetracker.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import byu.zappala.cityissuetracker.model.ListServiceRequest;
import android.util.Xml;

public class ServiceRequestXMLParser {
	public List<ListServiceRequest> parseServiceRequestList(InputStream input) throws XmlPullParserException, IOException {
		try {
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(input, null);
		parser.nextTag();
		return readServiceRequestList(parser);
		} finally {
			input.close();
		}
		
	}

	private List<ListServiceRequest> readServiceRequestList(XmlPullParser parser) throws XmlPullParserException, IOException {
		List<ListServiceRequest> serviceRequests = new ArrayList<ListServiceRequest>();
		parser.require(XmlPullParser.START_TAG, null, "requests");
		
		while (parser.next() != XmlPullParser.END_TAG) {
			String name = parser.getName();
			
			if (name.equals("request")) {
				serviceRequests.add(readRequest(parser));
			}
		
		}
		return null;
	}

	private ListServiceRequest readRequest(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "request");
		
		String serviceRequestID = null;
		String status = null;
		String statusNotes = null;
		String serviceName = null;
		String serviceCode = null;
		String description = null;
		String agencyResponsible = null;
		String serviceNotice = null;
		String requestedDatetime = null;
		String updatedDatetime = null;
		String expectedDatetime = null;
		String address = null;
		String addressID = null;
		String zipcode = null;
		double latitude = 0;
		double longitude = 0;
		String mediaURL = null;
		
		while (parser.next() != XmlPullParser.END_TAG) {
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
			} else if(name.equals("media_url")) {
				
			}
		}
		return null;
	}

	private double readLatitude(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "lat");
	    double latitude = Double.parseDouble(readText(parser));
	    parser.require(XmlPullParser.END_TAG, null, "lat");
	    return latitude;
	}

	private double readLongitude(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "long");
	    double longitude = Double.parseDouble(readText(parser));
	    parser.require(XmlPullParser.END_TAG, null, "long");
	    return longitude;
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

	private String readAddress(XmlPullParser parser) {
		// TODO Auto-generated method stub
		return null;
	}

	private String readExpectedDatetime(XmlPullParser parser) {
		// TODO Auto-generated method stub
		return null;
	}

	private String readUpdatedDatetime(XmlPullParser parser) {
		// TODO Auto-generated method stub
		return null;
	}

	private String readRequestedDatetime(XmlPullParser parser) {
		// TODO Auto-generated method stub
		return null;
	}

	private String readServiceNotice(XmlPullParser parser) {
		// TODO Auto-generated method stub
		return null;
	}

	private String readAgencyResponsible(XmlPullParser parser) {
		// TODO Auto-generated method stub
		return null;
	}

	private String readDescription(XmlPullParser parser) {
		// TODO Auto-generated method stub
		return null;
	}

	private String readServiceCode(XmlPullParser parser) {
		// TODO Auto-generated method stub
		return null;
	}

	private String readServiceName(XmlPullParser parser) {
		// TODO Auto-generated method stub
		return null;
	}

	private String readStatusNotes(XmlPullParser parser) {
		// TODO Auto-generated method stub
		return null;
	}

	private String readStatus(XmlPullParser parser) {
		// TODO Auto-generated method stub
		return null;
	}

	private String readServiceRequestID(XmlPullParser parser) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}
}
