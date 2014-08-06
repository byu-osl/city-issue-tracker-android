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
import byu.zappala.cityissuetracker.model.Service;
import byu.zappala.cityissuetracker.model.ServiceRequest;
import android.util.Xml;

public class ServiceXMLParser {
	public List<Service> parseServiceList(InputStream input) throws XmlPullParserException, IOException {
		try {
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(input, null);
		parser.nextTag();
		return readServiceList(parser);
		} finally {
			input.close();
		}
		
	}

	private List<Service> readServiceList(XmlPullParser parser) throws XmlPullParserException, IOException {
		List<Service> services = new ArrayList<Service>();
		parser.require(XmlPullParser.START_TAG, null, "services");
		
		while (parser.next() != XmlPullParser.END_TAG) {

			if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
			
			String name = parser.getName();
		
			if (name.equals("service")) {
				services.add(readService(parser));
			}
		
		}
		return services;
	}

	private Service readService(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "service");
		
		String serviceCode = "";
		boolean metadata = false;
		String type = "";
		List<String> keywords = new ArrayList<String>();
		String group = "";
		String serviceName = "";
		String description = "";
		
		while (parser.next() != XmlPullParser.END_TAG) {

			if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
			
			String name = parser.getName();
			
			if (name.equals("service_code")) {
				serviceCode = readServiceCode(parser);
			} else if(name.equals("metadata")) {
				metadata = readMetadata(parser);
			} else if(name.equals("type")) {
				type = readType(parser);
			}  else if(name.equals("keywords")) {
				keywords = readKeywords(parser);
			} else if(name.equals("group")) {
				group = readGroup(parser);
			} else if(name.equals("service_name")) {
				serviceName = readServiceName(parser);
			} else if(name.equals("description")) {
				System.out.println("Got to description");
				description = readDescription(parser);
			} 
		}
		return new Service(serviceCode, metadata, type, keywords, group, serviceName, description);
	}

	private String readServiceCode(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "service_code");
	    String serviceCode = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "service_code");
	    return serviceCode;
	}
	
	private boolean readMetadata(XmlPullParser parser) throws XmlPullParserException, IOException  {
		boolean metadata = false;
		parser.require(XmlPullParser.START_TAG, null, "metadata");
	    String metadataString = readText(parser);
	    if(metadataString.equals("true")) {
	    	metadata = true;
	    }
	    parser.require(XmlPullParser.END_TAG, null, "metadata");
	    return metadata;
	}

	private String readType(XmlPullParser parser) throws XmlPullParserException, IOException  {
		parser.require(XmlPullParser.START_TAG, null, "type");
	    String type = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "type");
	    return type;
	}
	
	private List<String> readKeywords(XmlPullParser parser)  throws XmlPullParserException, IOException {
		parser.nextTag();
		return null;
	}
	
	private String readGroup(XmlPullParser parser) throws XmlPullParserException, IOException  {
		parser.require(XmlPullParser.START_TAG, null, "group");
	    String group = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "group");
	    return group;
	}
	
	private String readServiceName(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "service_name");
	    String serviceName = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "service_name");
	    return serviceName;
	}
	
	private String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "description");
	    String description = readText(parser);
	    parser.require(XmlPullParser.END_TAG, null, "description");
	    return description;
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

