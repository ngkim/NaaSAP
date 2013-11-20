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

		// step1. 서비스 생성
		// 생성용 데이터는 미리 확인....
		result = createService("ciscoems", "serviceName", "serviceType", "originId", "cpid", "priority", "productId", "description");
		if (result.getResultCode() != 0)
		{
			// 처리하다가 에러났음
			response.setResultCode(result.getResultCode());	//에러코드정의해야함
			response.setResultMessage("서비스 생성실패");
			return;
		}
		
		// step2. location 생성
		// 생성용 데이터 확인 ^^
		result = createLocation("emsId", "locationId", "locationName", "parentId", "regionId", "description");
		if (result.getResultCode() != 0)
		{
			// 처리하다가 에러났음
			response.setResultCode(-1);	//에러코드정의해야함
			response.setResultMessage("Location 생성실패");
		}
		
	}
	
	private ResponseMessage createService(String emsId, String serviceName, String serviceType, 
			String originId, String cpId, String priority, String productId, String description)
	{
		RequestProcessor processor = (RequestProcessor)processorFactory.getProcessor("CREATE_SERVICE");
		
		// create service 호출용 request 생성
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
		
		// create service 호출용 request 생성
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
