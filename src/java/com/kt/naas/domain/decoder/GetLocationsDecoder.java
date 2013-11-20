package com.kt.naas.domain.decoder;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.kt.naas.domain.SnEntry;
import com.kt.naas.domain.SnList;
import com.kt.naas.domain.SnLocation;
import com.kt.naas.error.ErrorCodes;
import com.kt.naas.error.ExceptionFactory;

public class GetLocationsDecoder {
	public List<SnLocation> parse(String str)
	{
		return parse(null, str);
	}
	
	public List<SnLocation> parse(String emsId, String str)
	{
		Digester digester = new Digester();
		digester.setUseContextClassLoader(true);
		digester.setValidating(false);
		
		List<SnLocation> locations = new ArrayList<SnLocation>();
		digester.push(locations);
		
		digester.addObjectCreate("listing/record", SnLocation.class);
		digester.addSetProperties("listing/record", "Id", 					"snLocationId");
		digester.addSetProperties("listing/record", "ParentLocation", 		"parentId");
		//digester.addSetProperties("listing/record", "LeaderId", 			"leaderId");
		digester.addSetProperties("listing/record", "Level",				"levelNum");
		digester.addSetProperties("listing/record", "Description", 			"description");
		digester.addSetProperties("listing/record", "Name", 				"locationName");
		digester.addSetNext("listing/record", "add");

//		digester.addObjectCreate("listing/record/list", SnList.class);
//		digester.addSetProperties("listing/record/list", "type", "type");
//		digester.addSetProperties("listing/record/list", "size", "size");
//		digester.addSetProperties("listing/record/list", "name", "name");
//		digester.addSetNext("listing/record/list", "addServiceEngineIds");
//	 	
//		digester.addObjectCreate("listing/record/list/entry", SnEntry.class);
//		digester.addSetProperties("listing/record/list/entry", "id", "id");
//		digester.addSetNext("listing/record/list/entry", "addEntry");
		
	 	try {
			digester.parse(new StringReader(str));
			return locations;
		} catch (IOException e) {
			throw ExceptionFactory.getSNPException(ErrorCodes.EMS_MSG_DECODING_FAIL, e.getMessage());
		} catch (SAXException e) {
			throw ExceptionFactory.getSNPException(ErrorCodes.EMS_MSG_DECODING_FAIL, e.getMessage());
		}
	}
}