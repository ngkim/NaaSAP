package com.kt.naas.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

public class RequestUtils implements MessageListener, ExceptionListener {
	private ConnectionFactory connectionFactory;
	private Connection connection  = null;
    private Session session = null;
    private MessageProducer requestProducer = null;
    
    private String requestTopicName = "TOPIC.REQUEST";
    private String responseTopicName = "TOPIC.RESPONSE";
    private String clientId = "";

	private long requestTimeout = 30000L;
    
    private Map<Long, RequestMessage> requestMap = new ConcurrentHashMap<Long, RequestMessage>();
    private Map<Long, ResponseMessage> responseMap = new ConcurrentHashMap<Long, ResponseMessage>();
    
    public RequestUtils() {
    }
    
    public void init() {
    	try {
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
		}
    }
    
	@Override
	public void onException(JMSException ex) {
		System.out.println("[ActiveMQ Exception]");
		ex.printStackTrace();
	}
	
	@Override
	public void onMessage(Message message) {
		System.out.println("received message from ap\n" + message);
		
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
		responseMap.put(response.getMsgSeqNo(), response);
		
		RequestMessage request = requestMap.get(response.getMsgSeqNo());
		if (request != null)
			synchronized(request)
			{
				request.notify();
			}
	}
	
	public ResponseMessage sendRequest(RequestMessage request)
	{
		long seqId = SequenceId.nextValue();
		request.setMsgSeqNo(seqId);
		
		requestMap.put(seqId, request);
		requestToAp(request);
		
		try {
			synchronized(request) {
				request.wait(requestTimeout);	
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ResponseMessage response = responseMap.remove(seqId);
		requestMap.remove(seqId);
		
		return response;
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
	
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public long getRequestTimeout() {
		return requestTimeout;
	}

	public void setRequestTimeout(long requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	public String getRequestTopicName() {
		return requestTopicName;
	}

	public void setRequestTopicName(String requestTopicName) {
		this.requestTopicName = requestTopicName;
	}

	public String getResponseTopicName() {
		return responseTopicName;
	}

	public void setResponseTopicName(String responseTopicName) {
		this.responseTopicName = responseTopicName;
	}
}