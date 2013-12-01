package com.kt.naas.util;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import com.kt.naas.GlobalConstants;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;

public class RequestClient implements MessageListener, ExceptionListener {
	private ConnectionFactory connectionFactory;
	private Connection connection  = null;
    private Session session = null;
    private MessageProducer requestProducer = null;
    
    private String requestTopicName = "TOPIC.REQUEST";
    private String responseTopicName = "TOPIC.RESPONSE";
    
    private String clientId = "";
    
    public RequestClient() {
    }
    
    public void init() {
    	try {
    		System.out.println("##########" + connectionFactory);
    		connection = connectionFactory.createConnection();
            connection.setExceptionListener(this);
            connection.start();
            
            clientId = connection.getClientID();
            
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            // Create topic to receive response messages
            Topic responseTopic = session.createTopic(responseTopicName);
            String selector = "TARGETUID = '" + clientId + "'";
            session.createConsumer(responseTopic, selector).setMessageListener(this);

            // Create topic to send request messages
            Topic requestTopic = session.createTopic(requestTopicName);
            requestProducer = session.createProducer(requestTopic);
            requestProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            
            System.out.println("request  topic name is [" + requestTopicName +"]");
            System.out.println("response topic name is [" + responseTopicName +"]");
            System.out.println("My ClientUID is [" + clientId + "]");
		} catch (Exception ex) {
			System.out.println("fail to initialize." + ex);
			ex.printStackTrace();
            System.exit(0);
		}
    }
	@Override
	public void onException(JMSException ex) {
		System.out.println("[ActiveMQ Exception]");
		ex.printStackTrace();
	}
	@Override
	public void onMessage(Message message) {
		System.out.println("received message from ems\n" + message);
		
		ObjectMessage objectMessage = (ObjectMessage)message;
		ResponseMessage response = null;
		try {
			response = (ResponseMessage)objectMessage.getObject();
			responseReceived(response);
		} catch (JMSException e) {
			onException(e);
		}
	}
	
	public void responseReceived(ResponseMessage response)
	{
		System.out.println("[Response Received]\n" + response);
	}
    
	public void requestToAp(RequestMessage request) {
		request.setSenderType(GlobalConstants.ACTOR_WEBUI);
		request.setSenderUID(clientId);
		System.out.println("send request to ap\n" + request);
		try {
			request.setSenderType(GlobalConstants.ACTOR_WEBUI);
			ObjectMessage objectMessage = session.createObjectMessage(request);
			
			requestProducer.send(objectMessage);
		} catch (JMSException e) {
			onException(e);
		}
	}
	
	public void requestToWeb(RequestMessage request) {
		request.setSenderType(GlobalConstants.ACTOR_AP);
		request.setSenderUID(clientId);
		System.out.println("send request to webui\n" + request);
		try {
			request.setSenderType(GlobalConstants.ACTOR_AP);
			ObjectMessage objectMessage = session.createObjectMessage(request);
			
			requestProducer.send(objectMessage);
		} catch (JMSException e) {
			onException(e);
		}
	}
	
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	
}
