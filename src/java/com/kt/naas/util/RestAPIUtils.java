package com.kt.naas.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.kt.naas.GlobalConstants;
import com.kt.naas.xml.RequestInfoCloudSDN;

public class RestAPIUtils {

	public HttpResponse requestToAPIServer(String url, int method,
			String requestXml) throws Exception {
		HttpResponse res = null;

		HttpClient client = new DefaultHttpClient();
		if (method == GlobalConstants.HTTP_GET) {
			HttpGet req = new HttpGet(url);

			res = client.execute(req);
		} else if (method == GlobalConstants.HTTP_POST) {
			HttpPost req = new HttpPost(url);

			StringEntity entity = new StringEntity(requestXml);
			req.setEntity(entity);

			res = client.execute(req);
		}

		return res;
	}

	public String getResponseXml(HttpResponse res) {
		String responseXml = "";

		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(res
					.getEntity().getContent()));

			String line = "";
			StringBuffer jb = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				jb.append(line);
			}
			responseXml = jb.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseXml;
	}

	public <E> String getRequestXML(E req) throws Exception {
		String requestXml = "";

		JAXBContext jaxbContext = JAXBContext
				.newInstance(req.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter writer = new StringWriter();
		jaxbMarshaller.marshal(req, writer);

		requestXml = writer.toString();

		return requestXml;
	}

	public <E> E getResponseObject(String responseXml, E item) throws Exception {
		E res = null;

		JAXBContext jaxbContext = JAXBContext.newInstance(item.getClass());
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(responseXml);
		E unmarshal = (E) jaxbUnmarshaller.unmarshal(reader);
		res = unmarshal;

		return res;
	}
}
