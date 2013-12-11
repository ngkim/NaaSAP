package com.kt.naas.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;

import com.kt.naas.GlobalConstants;
import com.sun.xml.internal.messaging.saaj.packaging.mime.Header;

public class RestAPIUtils {

	public String callAPI(String url, int method, String requestXml)
			throws Exception {
		String responseXml = "";

		PrintUtils pu = new PrintUtils();
		if (GlobalConstants.OP_DEBUG) {
			pu.printKeyAndValue("callAPI URL", url);
		}
		HttpResponse httpRes = requestToAPIServer(url, method, requestXml);
		responseXml = getResponseXml(httpRes);

		return responseXml;
	}

	public HttpResponse requestToAPIServer(String url, int method,
			String requestXml) throws Exception {
		HttpResponse res = null;

		HttpClient client = new DefaultHttpClient();
		if (method == GlobalConstants.HTTP_GET) {
			HttpGet req = new HttpGet(url);

			res = client.execute(req);
		} else if (method == GlobalConstants.HTTP_POST) {
			HttpPost req = new HttpPost(url);
			StringEntity entity = new StringEntity(requestXml, "UTF-8");
			req.setEntity(entity);

			res = client.execute(req);
		}

		return res;
	}

	private class CustomizedHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
	
	private String callAPIServerHttps(String url, String requestXml) throws Exception {
		String response;

		SslConfigurator sslConfig = SslConfigurator.newInstance()
				.trustStoreFile("./trust/truststore_client")
				.trustStorePassword("asdfgh")
				.keyStoreFile("./trust/keystore_client").keyPassword("asdfgh");

		Client client = ClientBuilder.newBuilder()
				.hostnameVerifier(new CustomizedHostnameVerifier())
				.sslContext(sslConfig.createSSLContext()).build();

		client.register(new HttpBasicAuthFilter("admin", "admin"));

		Entity<String> entity = Entity.entity(requestXml,
				MediaType.APPLICATION_XML_TYPE);

		Response res = client.target(url)
				.request(MediaType.APPLICATION_XML_TYPE).post(entity);

		response = res.readEntity(String.class);
		client.close();

		return response;
	}

	public String requestToAPIServerHttps(String url, String requestXml) throws Exception {
		String response;

		try {
			response = callAPIServerHttps(url, requestXml);
		} catch (SSLHandshakeException sslex) {
			System.out.println("Error!!! " + sslex.getMessage() + " Retry...");
			Thread.sleep(1000); // wait for 1 second and retry
			response = callAPIServerHttps(url, requestXml);
		}
		return response;
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

		JAXBContext jaxbContext = JAXBContext.newInstance(req.getClass());
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
		@SuppressWarnings("unchecked")
		E unmarshal = (E) jaxbUnmarshaller.unmarshal(reader);
		res = unmarshal;

		return res;
	}
}
