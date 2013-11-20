package com.kt.naas.process.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {
	@Autowired
	RestTemplate template;

	public void test(String name) {
		template = new RestTemplate();
		
		System.out.println("Hello RestClient.");
		System.out.println("template: " + template);

//		JSONMsg msg = (JSONMsg) template.getForObject(
//				"http://echo.jsontest.com/key/value ", JSONMsg.class);
		
		JSONMsg msg = (JSONMsg) template.getForObject(
				"http://echo.jsontest.com/key/value/otherkey/othervalue", JSONMsg.class);
		
		System.out.println("message: " + msg.getOtherkey());
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"application-context.xml");

		RestClient client = context.getBean(RestClient.class);
		client.test("sejoon");
	}
}