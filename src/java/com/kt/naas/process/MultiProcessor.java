package com.kt.naas.process;

import com.kt.naas.GlobalConstants;
import com.kt.naas.domain.FieldBuffer;
import com.kt.naas.message.RequestMessage;
import com.kt.naas.message.ResponseMessage;

public class MultiProcessor extends RequestProcessor {
	private ProcessorFactory processorFactory = null;
	
	@Override
	public void processRequest() {
		ResponseMessage result = null; 

		// step1. ���� ����
		// ������ �����ʹ� �̸� Ȯ��....
		result = createService("ciscoems", "serviceName", "serviceType", "originId", "cpid", "priority", "productId", "description");
		if (result.getResultCode() != 0)
		{
			// ó���ϴٰ� ��������
			response.setResultCode(result.getResultCode());	//�����ڵ������ؾ���
			response.setResultMessage("���� ��������");
			return;
		}
		
		// step2. location ����
		// ������ ������ Ȯ�� ^^
		result = createLocation("emsId", "locationId", "locationName", "parentId", "regionId", "description");
		if (result.getResultCode() != 0)
		{
			// ó���ϴٰ� ��������
			response.setResultCode(-1);	//�����ڵ������ؾ���
			response.setResultMessage("Location ��������");
		}
		
	}
	
	private ResponseMessage createService(String emsId, String serviceName, String serviceType, 
			String originId, String cpId, String priority, String productId, String description)
	{
		RequestProcessor processor = (RequestProcessor)processorFactory.getProcessor("CREATE_SERVICE");
		
		// create service ȣ��� request ����
		RequestMessage request = new RequestMessage();
		request.setSenderType(GlobalConstants.ACTOR_AP);
		request.setSenderUID(getClass().getSimpleName());
		request.setServiceName("CREATE_SERVICE");
		
		FieldBuffer buf = request.getFieldBuffer();
		buf.putString("EMSID", emsId);
		buf.putString("SERVICENAME", serviceName);
		buf.putString("SERVICETYPE", serviceType);		
		buf.putString("ORIGINID", originId);
		buf.putString("CPID", cpId);
		buf.putString("PRIORITY", priority);
		buf.putString("PRODUCTID", productId);
		buf.putString("DESCRIPTION", description);

		processor.process(request);
		return processor.getResponse();
	}

	private ResponseMessage createLocation(String emsId, String locationId, String locationName, 
			String parentId, String regionId, String description)
	{
		RequestProcessor processor = (RequestProcessor)processorFactory.getProcessor("CREATE-LOCATION");
		
		// create service ȣ��� request ����
		RequestMessage request = new RequestMessage();
		request.setSenderType(GlobalConstants.ACTOR_AP);
		request.setSenderUID(getClass().getSimpleName());
		request.setServiceName("CREATE-LOCATION");

		
		FieldBuffer buf = request.getFieldBuffer();
		
		buf.putString("EMSID", emsId);
		buf.putString("ID", locationId);
		buf.putString("LOCATIONNAME", locationName);
		buf.putString("PARENTID", parentId);
		buf.putString("REGIONID", regionId);
		buf.putString("DESCRIPTION", description);

		processor.process(request);
		return processor.getResponse();
	}

	public ProcessorFactory getProcessorFactory() {
		return processorFactory;
	}

	public void setProcessorFactory(ProcessorFactory processorFactory) {
		this.processorFactory = processorFactory;
	}
}
