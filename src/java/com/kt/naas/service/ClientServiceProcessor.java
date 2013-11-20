package com.kt.naas.service;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kt.naas.message.EventMessage;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;
import com.kt.naas.process.Processor;
import com.kt.naas.process.ProcessorFactory;

public class ClientServiceProcessor implements ClientService, MessageListener, ExceptionListener {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private ConnectionFactory connectionFactory = null;
	private Connection connection  = null;
    private Session session = null;
    private MessageProducer responseProducer = null;
    private MessageProducer eventProducer = null;
    
    private ProcessorFactory processorFactory = null;
    private ExecutionService executionService = null;
    
    private String serviceName = null;
    private String username = null;
    private String password = null;
    
    private String requestTopicName = "TOPIC.REQUEST";
    private String responseTopicName = "TOPIC.RESPONSE";
    private String eventTopicName = "TOPIC.EVENT";
	
	@Override
	public void start() {
		logger.info("start " + serviceName + "...");
		try {
			logger.debug("try to connect message-queue....");
			connection = connectionFactory.createConnection(username, password);
			
            connection.setExceptionListener(this);
            connection.start();
			logger.debug("connect ok.");

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            logger.debug("session created. [" + session + "]");
            
            // Create topic to receive request messages
            Topic requestTopic = session.createTopic(requestTopicName);
            session.createConsumer(requestTopic, null).setMessageListener(this);

            // Create topic to send response messages
            Topic responseTopic = session.createTopic(responseTopicName);
            responseProducer = session.createProducer(responseTopic);
            responseProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create topic to send event messages
            Topic eventTopic = session.createTopic(eventTopicName);
            eventProducer = session.createProducer(eventTopic);
            eventProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            
            logger.info("request  topic = [" + requestTopicName +"]");
            logger.info("response topic = [" + responseTopicName +"]");
            logger.info("event    topic = [" + eventTopicName +"]");
            
            logger.info(serviceName + " initialized.");
		} catch (Exception ex) {
			logger.error("fail to initialize " + serviceName, ex);
            System.exit(-1);
		}
	}

	@Override
	public void stop() {
		logger.info("stop " + serviceName + "...");
		try {
			session.close();
		} catch (Exception e) { }
		try {
			connection.close();
		} catch (Exception e) { }
	}

	@Override
	public void responseToClient(ResponseMessage response) {
		try {
			ObjectMessage objectMessage = session.createObjectMessage(response);
			objectMessage.setStringProperty("TARGETUID", response.getTargetUID());
			responseProducer.send(objectMessage);
		} catch (JMSException e) {
			onException(e);
		}
	}

	@Override
	public void notifyEvent(EventMessage event) {
		try {
			ObjectMessage objectMessage = session.createObjectMessage(event);
			eventProducer.send(objectMessage);
		} catch (JMSException e) {
			onException(e);
		}
	}

	@Override
	public void onException(JMSException ex) {
		logger.error("[ActiveMQ Exception]", ex);
	}

	@Override
	public void onMessage(Message message) {
        ObjectMessage objectMessage = (ObjectMessage)message;
        RequestMessage request = null;
		try {
			request = (RequestMessage)objectMessage.getObject();
		} catch (JMSException e) {
			onException(e);
		}
        String serviceName = request.getServiceName();
        String processorName = serviceName;
        
        if (processorFactory.containsBeanDefinition(processorName))
        {
        	Processor processor = processorFactory.getProcessor(processorName);
        	
        	executionService.executeProcessor(processor, request);
        }
        else
        {
        	ResponseMessage response = new ResponseMessage(request);
        	response.setResultCode((short) -1);
        	response.setResultMessage("no service in ems");
        	responseToClient(response);
        }
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
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

	public String getEventTopicName() {
		return eventTopicName;
	}

	public void setEventTopicName(String eventTopicName) {
		this.eventTopicName = eventTopicName;
	}

	public ProcessorFactory getProcessorFactory() {
		return processorFactory;
	}

	public void setProcessorFactory(ProcessorFactory processorFactory) {
		this.processorFactory = processorFactory;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public ExecutionService getExecutionService() {
		return executionService;
	}

	public void setExecutionService(ExecutionService executionService) {
		this.executionService = executionService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
