package org.aig.ras.integration.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;


import com.ibm.websphere.bo.BOFactory;
import com.ibm.websphere.bo.BOXMLSerializer;
import com.ibm.websphere.sca.Service;
import com.ibm.websphere.sca.ServiceBusinessException;
import com.ibm.websphere.sca.ServiceRuntimeException;
import com.ibm.websphere.sca.Ticket;
import commonj.sdo.DataObject;
import com.ibm.websphere.sca.ServiceManager;


public class RASIntegrationServiceCmptImpl {
	/**
	 * Default constructor.
	 */
	public RASIntegrationServiceCmptImpl() {
		super();
	}

	/**
	 * Return a reference to the component service instance for this implementation
	 * class.  This method should be used when passing this service to a partner reference
	 * or if you want to invoke this component service asynchronously.    
	 *
	 * @generated (com.ibm.wbit.java)
	 */
	@SuppressWarnings("unused")
	private Object getMyService() {
		return (Object) ServiceManager.INSTANCE.locateService("self");
	}

	/**
	 * This method is used to locate the service for the reference
	 * named "DBInterfacePartner".  This will return an instance of 
	 * {@link com.ibm.websphere.sca.Service}.  This is the dynamic
	 * interface which is used to invoke operations on the reference service
	 * either synchronously or asynchronously.  You will need to pass the operation
	 * name in order to invoke an operation on the service.
	 *
	 * @generated (com.ibm.wbit.java)
	 *
	 * @return Service
	 */
	private Service _DBInterfacePartner = null;

	public Service locateService_DBInterfacePartner() {
		if (_DBInterfacePartner == null) {
			_DBInterfacePartner = (Service) ServiceManager.INSTANCE
					.locateService("DBInterfacePartner");
		}
		return _DBInterfacePartner;
	}

	/**
	 * This method is used to locate the service for the reference
	 * named "RASExcessRefundTicketFromDraftPartner".  This will return an instance of 
	 * {@link com.ibm.websphere.sca.Service}.  This is the dynamic
	 * interface which is used to invoke operations on the reference service
	 * either synchronously or asynchronously.  You will need to pass the operation
	 * name in order to invoke an operation on the service.
	 *
	 * @generated (com.ibm.wbit.java)
	 *
	 * @return Service
	 */
	private Service _RASExcessRefundTicketFromDraftPartner = null;

	public Service locateService_RASExcessRefundTicketFromDraftPartner() {
		if (_RASExcessRefundTicketFromDraftPartner == null) {
			_RASExcessRefundTicketFromDraftPartner = (Service) ServiceManager.INSTANCE
					.locateService("RASExcessRefundTicketFromDraftPartner");
		}
		return _RASExcessRefundTicketFromDraftPartner;
	}

	public BOFactory getBOFactory() {
		return (BOFactory) ServiceManager.INSTANCE
				.locateService("com/ibm/websphere/bo/BOFactory");
	}
	public enum Constants {
		CancellationRefund("CANCELLATION REFUND"), ExcessRefund("EXCESS REFUND"),IndividualTicket("INDIVIDUAL TICKET"), DebitAuthorization(
				"DEBIT AUTHORIZATION"), Cheque("CHEQUE"), NEFT("NEFT"), CUSTOMER(
				"CUSTOMER"), INTERMEDIARY("INTERMEDIARY"), DEALER("DEALER"), FINANCIER(
				"FINANCIER"), OTHERS("OTHERS"),PANCARD("PAN CARD"),ADDRESSPROOF("ADDRESS PROOF"),CUSTOMERINITIATED("CUSTOMER INITIATED");

		String constant;

		Constants(String constant) {
			this.constant = constant;
		}

		public String getValue() {
			return this.constant;
		}
	}

	public int incrementSequence(String category)
	{
		int sequenceId=0;
		Integer nextValue=0;
		DataObject exceptionData = null;

		if (category == null || "".equalsIgnoreCase(category.trim())) {
			throw new RuntimeException("Category cannot be blank");
		}
		try
		{
		if(category == "EXCESS REFUND SERVICE SEQUENCE")
		{
		DataObject excessRefundServiceIdBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/serviceidbg",
							"ServiceIdBG");
		DataObject  excessRefundServiceId = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/serviceid",
							"ServiceId");
		
		excessRefundServiceIdBG.setDataObject("ServiceId", excessRefundServiceId);
		
		DataObject excessRefundServiceSeries = (DataObject) locateService_DBInterfacePartner().invoke(
				"retrieveallServiceIdBG", excessRefundServiceIdBG);
		nextValue = 
				excessRefundServiceSeries.getInt("retrieveallServiceIdBGOutput/ServiceId/next_value");
		
		}
		if(category == "EXCESS REFUND DRAFT SEQUENCE")
		{
		DataObject excessRefundDraftIdBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftidbg",
							"DraftIdBG");
		DataObject excessRefundDraftId = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftid",
							"DraftId");
		
		excessRefundDraftIdBG.setDataObject("DraftId", excessRefundDraftId);
		
		DataObject excessRefundServiceSeries = (DataObject) locateService_DBInterfacePartner().invoke(
				"retrieveallDraftIdBG", excessRefundDraftIdBG);
		nextValue = 
				excessRefundServiceSeries.getInt("retrieveallDraftIdBGOutput/DraftId/next_value");	
		}
		if(category == "EXCESS REFUND REVERSE FEED SEQUENCE")
		{
		DataObject excessReverseFeedIdBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/reversefeedidbg",
							"ReverseFeedIdBG");
		DataObject excessReverseFeedId = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/reversefeedid",
							"ReverseFeedId");
		
		excessReverseFeedIdBG.setDataObject("ReverseFeedId", excessReverseFeedId);
		
		DataObject excessRefundServiceSeries = (DataObject) locateService_DBInterfacePartner().invoke(
				"retrieveallReverseFeedIdBG", excessReverseFeedIdBG);
		nextValue = 
				excessRefundServiceSeries.getInt("retrieveallReverseFeedIdBGOutput/ReverseFeedId/next_value");	
		}
		DataObject updateAppConfigBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateappconfigbg",
						"UpdateAppConfigBG");

		DataObject updateAppConfigInput = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateappconfig",
						"UpdateAppConfig");

		updateAppConfigInput.setString("statement1parameter2", category);
		updateAppConfigInput.setString("statement1parameter1",String.valueOf(nextValue));
	
		updateAppConfigBG.setDataObject("UpdateAppConfig",
				updateAppConfigInput);

		this.locateService_DBInterfacePartner().invoke(
				"executeUpdateAppConfigBG", updateAppConfigBG);
		sequenceId = nextValue;
		return sequenceId;
		}
		catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();

			throw new ServiceBusinessException(exceptionData.get("message")
					.toString());

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage().toString(), sre);
		} catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException(e.getMessage().toString(), e);
		}
	}
	// Convert rasTicketInput into SOAP XML Request and save in the database
	public int convertRasTicketInputObjectToXML(DataObject rasTicketInput)
			throws IOException, ParseException {
		DataObject exceptionData = null;
		int serviceId=0;
		
		BOXMLSerializer mySerializer = (BOXMLSerializer) ServiceManager.INSTANCE
				.locateService("com/ibm/websphere/bo/BOXMLSerializer");
	    String rootElementName = rasTicketInput.getType().getName();
		String targetNamespace = rasTicketInput.getType().getURI();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mySerializer.writeDataObject(rasTicketInput, targetNamespace,
				"rasTicketInput", baos);
		String rasTicketInputXml = new String(baos.toString());
		System.out.println("RAS Ticket Input XML " + rasTicketInputXml);
		
		try {
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage soapMsg = factory.createMessage();
			SOAPPart part = soapMsg.getSOAPPart();
			SOAPEnvelope envelope = part.getEnvelope();
			SOAPHeader header = envelope.getHeader();
			SOAPBody body = envelope.getBody();
			SOAPBodyElement element = body.addBodyElement(envelope.createName(
					"createRASExcessRefundDraftTicket", "ras",
					rasTicketInputXml));
			ByteArrayOutputStream requestStream = new ByteArrayOutputStream();
			soapMsg.writeTo(requestStream);
			String soapXmlRequest = new String(requestStream.toByteArray(),
					"utf-8");
			System.out.println("SOAP Request created");
			serviceId = incrementSequence("EXCESS REFUND SERVICE SEQUENCE");
			DataObject serviceBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_excess_refund_servicebg",
							"DboRas_Excess_Refund_ServiceBG");
			DataObject serviceData = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_excess_refund_service",
							"DboRas_Excess_Refund_Service");
			if(rasTicketInput!=null)
			{
			if(rasTicketInput.getString("source").length()<=200)
			serviceData.setString("source", rasTicketInput.getString("source"));
			if(rasTicketInput.getString("sourceUniqueId").length()<=200)
			{
			serviceData.setString("source_unique_id",
					rasTicketInput.getString("sourceUniqueId"));
			serviceData.setString("created_by",
					rasTicketInput.getString("sourceUniqueId"));
			}
			if(rasTicketInput.getString("sourceUserName").length()<=200)
			serviceData.setString("source_username",
					rasTicketInput.getString("sourceUserName"));
			}
			serviceData.setInt("ras_excess_refund_service_id",serviceId );
			serviceData.setString("request_xml", soapXmlRequest);
			serviceData.setDate("created_on", formatDate(new Date()));
			serviceBG.set("DboRas_Excess_Refund_Service", serviceData);
			this
					.locateService_DBInterfacePartner().invoke(
							"createDboRas_Excess_Refund_ServiceBG", serviceBG);
			
			System.out.println("Service Id in request "+serviceId);
		}
		catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();

			throw new ServiceBusinessException(exceptionData.get("message")
					.toString());

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage().toString(), sre);
		}
		 catch (Exception e) {
				throw new RuntimeException(e.getMessage().toString(), e);
			}
		return serviceId;
	}

	// Convert rasTicketOutput into SOAP XML Response and save in the database
	public void convertRasTicketOutputObjectToXML(DataObject rasTicketOutput,
			int serviceId,String sourceUserName) throws IOException, ParseException {
		DataObject exceptionData = null;
        System.out.println("Service ID in response "+serviceId);
		BOXMLSerializer mySerializer = (BOXMLSerializer) ServiceManager.INSTANCE
				.locateService("com/ibm/websphere/bo/BOXMLSerializer");
		String rootElementName = rasTicketOutput.getType().getName();
		String targetNamespace = rasTicketOutput.getType().getURI();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mySerializer.writeDataObject(rasTicketOutput, targetNamespace,
				"rasTicketOutput", baos);
		String rasTicketOutputXml = new String(baos.toString());
		System.out.println("RAS Ticket Output XML " + rasTicketOutputXml);
		try {
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage soapMsg = factory.createMessage();
			SOAPPart part = soapMsg.getSOAPPart();
			SOAPEnvelope envelope = part.getEnvelope();
			SOAPHeader header = envelope.getHeader();
			SOAPBody body = envelope.getBody();
			SOAPBodyElement element = body.addBodyElement(envelope.createName(
					"createRASExcessRefundDraftTicketResponse", "ras",
					rasTicketOutputXml));
			ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
			soapMsg.writeTo(responseStream);
			String soapXmlResponse = new String(responseStream.toByteArray(),
					"utf-8");
			System.out.println("SOAP Response created");
			DataObject updateServiceBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateexcessrefundservicebg",
							"UpdateExcessRefundServiceBG");

			DataObject updateServiceInput = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateexcessrefundservice",
							"UpdateExcessRefundService");

			
			updateServiceInput.setInt("statement1parameter4", serviceId);
			updateServiceInput.setString("statement1parameter1", soapXmlResponse);
			updateServiceInput
					.setDate("statement1parameter2", formatDate(new Date()));
			if(sourceUserName.length()<=200)
				updateServiceInput
			.setString("statement1parameter3", sourceUserName);
			updateServiceBG.setDataObject(
					"UpdateExcessRefundService",
					updateServiceInput);
			this.locateService_DBInterfacePartner().invoke(
					"executeUpdateExcessRefundServiceBG", updateServiceBG);
		}catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();

			throw new ServiceBusinessException(exceptionData.get("message")
					.toString());

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage().toString(), sre);
		}
		 catch (Exception e) {
				throw new RuntimeException(e.getMessage().toString(), e);
			}

	}

	// formatDate Method to format date into DB Accepted mode
	public Timestamp formatDate(Date date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss a");
		String newDate = dateFormat.format(date);
		Date parsedDate = dateFormat.parse(newDate);
		Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		return timestamp;
	}

	// validation for ras ticket input object
	public List<String> validateRASTicketInput(DataObject rasTicketInput)
			throws ParseException {
		List<String> validationErrors = new ArrayList<String>();

		if (rasTicketInput.getString("source") == null
				|| "".equalsIgnoreCase(rasTicketInput.get("source").toString())) {
			validationErrors.add("Source cannot be blank");
			return validationErrors;
		}
	
		System.out.println("Source len"+rasTicketInput.getString(
				"source").length());
		int soucrceLength = rasTicketInput.getString(
				"source").length();
		if (soucrceLength > 200) {
			validationErrors.add("Invalid value input in Source, Value exceeding field length limit 200 characters. Please recheck the value");
			return validationErrors;
		}
		if (rasTicketInput.getString("sourceUniqueId") == null
				|| "".equalsIgnoreCase(rasTicketInput.get("sourceUniqueId")
						.toString())) {
			validationErrors.add("Source Unique ID cannot be blank");
			return validationErrors;
		}
		int soucrceUniqueIdLength = rasTicketInput.getString(
				"sourceUniqueId").length();
		if (soucrceUniqueIdLength > 200) {
			validationErrors
					.add("Invalid value input in Source Unique ID, Value exceeding field length limit 200 characters. Please recheck the value");
			return validationErrors;
		}
		if (rasTicketInput.getString("sourceUserName") == null
				|| "".equalsIgnoreCase(rasTicketInput.get("sourceUserName")
						.toString())) {
			validationErrors.add("Source Username cannot be blank");
			return validationErrors;
		}
		int soucrceUserNameLength = rasTicketInput.getString(
				"sourceUserName").length();
		if (soucrceUserNameLength > 200) {
			validationErrors
					.add("Invalid value input in Source Username, Value exceeding field length limit 200 characters. Please recheck the value");
			return validationErrors;
		}
		if (rasTicketInput.getString("bopsChannel") == null
				|| "".equalsIgnoreCase(rasTicketInput.get("bopsChannel")
						.toString())) {
			validationErrors.add("BOPS Channel cannot be blank");
			return validationErrors;
		}
		int bopsChannelLength = rasTicketInput.getString(
				"bopsChannel").length();
		if (bopsChannelLength > 200) {
			validationErrors
					.add("Invalid value input in BOPS Channel, Value exceeding field length limit 200 characters. Please recheck the value");
			return validationErrors;
		}
		
		
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (!"".equalsIgnoreCase(rasTicketInput.getString("emailId"))
				&& rasTicketInput.getString("emailId") != null) {
			if (!(rasTicketInput.getString("emailId").matches(emailPattern))) {
				validationErrors
						.add("Invalid value input in Email ID. Please enter Email ID value in correct format");
				return validationErrors;
			}
		}

		String contactNoPattern = "^[0-9]{10}$";
		if (rasTicketInput.getString("contactNo") != null
				&& !"".equalsIgnoreCase(rasTicketInput.getString("contactNo"))) {
			if (!(rasTicketInput.getString("contactNo")
					.matches(contactNoPattern))) {
				validationErrors
						.add("Invalid value input in Contact No, Only 10 digits numeric value should be input in Contact No. Please recheck the value");
				return validationErrors;
			}
		}

		if (rasTicketInput.getString("productCode") == null
				|| "".equalsIgnoreCase(rasTicketInput.getString("productCode"))) {
			validationErrors.add("Product Code cannot be blank");
			return validationErrors;
		}
		
		

		String ProductCodePattern = "^[0-9]{4}$";
		if (rasTicketInput.getString("productCode") != null
				&& !"".equalsIgnoreCase(rasTicketInput.getString("productCode"))) {
			if (!(rasTicketInput.getString("productCode")
					.matches(ProductCodePattern))) {
				validationErrors
						.add("Invalid value input in Product Code, Only 4 digits numeric value should be input in Product Code. Please recheck the value");
				return validationErrors;
			}
		}
		
		if (rasTicketInput.getString("lob") == null
				|| "".equalsIgnoreCase(rasTicketInput.getString("lob"))) {
			validationErrors.add("LOB cannot be blank");
			return validationErrors;
		}

		if (rasTicketInput.getString("ticketRemarks") == null
				|| "".equalsIgnoreCase(rasTicketInput
						.getString("ticketRemarks"))) {
			validationErrors.add("Ticket Remarks cannot be blank");
			return validationErrors;
		}

		String ticketRemarksPattern = "^[a-zA-Z0-9,. ]*$";
		if (!(rasTicketInput.getString("ticketRemarks")
				.matches(ticketRemarksPattern))) {
			validationErrors
					.add("Invalid value input in Ticket Remarks, Only alphanumeric characters value should be input in Ticket Remarks. Please recheck the value");
			return validationErrors;
		}

		int ticketRemarksLen = rasTicketInput.getString("ticketRemarks")
				.length();
		if (ticketRemarksLen > 400) {
			validationErrors
					.add("Invalid value input in Ticket Remarks, Value exceeding field length limit 400 characters. Please recheck the value");
			return validationErrors;
		}
		System.out.print("Document size"+rasTicketInput.getList("documentDetails/documents").size());
		List<String> isDocumentsAvailableList = new ArrayList();
		if(rasTicketInput.getList("documentDetails/documents").size()!=0)
		{
		DataObject docList = getBOFactory().create("http://RACASBO","DocumentList");
		boolean validDoctype=true;
		docList = findExcessRefundDraftDocumentDetails(rasTicketInput.getString("lob"),Constants.ExcessRefund.getValue(),"");
		List<String> docTypeMasterList = new ArrayList();
		for (int j = 1; j <= docList.getList("document").size(); j++) {
		docTypeMasterList.add(docList.getString("document["+ j + "]/docType"));
		}
		for (int j = 1; j <= rasTicketInput.getList("documentDetails/documents").size(); j++) {
		if(rasTicketInput.getString("documentDetails/documents["+ j + "]/documentType")!=null && !"".equalsIgnoreCase(rasTicketInput.getString("documentDetails/documents["+ j + "]/documentType")) && validDoctype==true && !docTypeMasterList.contains(rasTicketInput.getString("documentDetails/documents["+ j + "]/documentType")))
		validDoctype = false;
		}
		if(!validDoctype)
		{
			validationErrors.add("Invalid value input in Documents. Please recheck the value");
			return validationErrors;
		}
	
		for (int j = 1; j <= rasTicketInput.getList("documentDetails/documents").size(); j++) {
			isDocumentsAvailableList.add(rasTicketInput.getString("documentDetails/documents["+ j + "]/isAvailable"));
			if(rasTicketInput.getString("documentDetails/documents["+ j + "]/documentType")!=null && !"".equalsIgnoreCase(rasTicketInput.getString("documentDetails/documents["+ j + "]/documentType")))
			{
			if("".equalsIgnoreCase(rasTicketInput.getString("documentDetails/documents["+ j + "]/isAvailable")) || !("N".equals(rasTicketInput.getString("documentDetails/documents["+ j + "]/isAvailable")) || "Y".equals(rasTicketInput.getString("documentDetails/documents["+ j + "]/isAvailable"))))
			{
				validationErrors.add("Invalid value input in Is Available, Only Y or N value should be input in Is Available. Please recheck the value");
				return validationErrors;
			}
			}
		
		}
		}
		
		for (int j = 1; j <= rasTicketInput.getList("documentDetails/documents").size(); j++) {
			isDocumentsAvailableList.add(rasTicketInput.getString("documentDetails/documents["+ j + "]/isAvailable").trim());
		}
		if(!isDocumentsAvailableList.isEmpty() && isDocumentsAvailableList!=null)
		{
		
			if(isDocumentsAvailableList.contains("Y") && (rasTicketInput.getString("documentDetails/documentURL")==null || "".equalsIgnoreCase(rasTicketInput.getString("documentDetails/documentURL"))))
			{
				validationErrors.add("Omni Documents URL is not available");
				return validationErrors;
			}
		}
		
		if(rasTicketInput.getDouble("ticketDetails/receiptRefundAmount")>=100000)
		{
			if(rasTicketInput.getString("documentDetails/documentURL")==null || "".equalsIgnoreCase(rasTicketInput.getString("documentDetails/documentURL")))
			{
				validationErrors.add("Omni Documents URL is not available");
				return validationErrors;
			}
		List<String> docTypeList = new ArrayList();
		List<String> isAvailableList = new ArrayList();
		List<String> mandateDocumentsList = new ArrayList();
		boolean mandatoryDocumentsAvailable= true;
		boolean mandatoryDocumentsAvailability= true;
		mandateDocumentsList.add(Constants.PANCARD.getValue());
		mandateDocumentsList.add(Constants.ADDRESSPROOF.getValue());
		for (int j = 1; j <= rasTicketInput.getList(
				"documentDetails/documents").size(); j++) {
			docTypeList.add(rasTicketInput
					.getString("documentDetails/documents["
							+ j + "]/documentType"));
			isAvailableList.add(rasTicketInput
					.getString("documentDetails/documents["
							+ j + "]/isAvailable"));
			if(Constants.PANCARD.getValue().equalsIgnoreCase(rasTicketInput.getString("documentDetails/documents["+ j + "]/documentType")) || Constants.ADDRESSPROOF.getValue().equalsIgnoreCase(rasTicketInput.getString("documentDetails/documents["+ j + "]/documentType")))
			{
				
				if(!"Y".equalsIgnoreCase(rasTicketInput.getString("documentDetails/documents["+ j + "]/isAvailable")))
					
				{
					mandatoryDocumentsAvailability=false;
				}
			
				
			}
		
		}
		if(!(docTypeList.containsAll(mandateDocumentsList)))
	    mandatoryDocumentsAvailable= false;

		if (!(mandatoryDocumentsAvailable && mandatoryDocumentsAvailability)){
			validationErrors.add("Mandatory documents PAN CARD and ADDRESS PROOF are not available");
			return validationErrors;
		}
		}
		
		return validationErrors;
		
		
	}

	// Validation for ticket details object
	public List<String> validateTicketDetails(DataObject ticketDetails)
			throws ParseException {
		List<String> validationErrors = new ArrayList<String>();

		System.out.println("Ticket details" + ticketDetails);

		if (ticketDetails.getString("receiptNumber") == null
				|| "".equalsIgnoreCase(ticketDetails.getString("receiptNumber")
						.toString())) {
			validationErrors.add("Receipt Number cannot be blank");
			return validationErrors;
		}

		String receiptNoPattern = "^[A-Za-z0-9]{15}$";
		if (!"".equalsIgnoreCase(ticketDetails.getString("receiptNumber"))
				&& ticketDetails.getString("receiptNumber") != null) {
			if (!(ticketDetails.getString("receiptNumber")
					.matches(receiptNoPattern))) {
				validationErrors
						.add("Invalid value input in Receipt Number, Only 15 alphanumeric characters value should be input in Receipt Number. Please recheck the value");
				return validationErrors;
			}
		}
	
        if (ticketDetails.getString("isMainReceipt") == null
				|| "".equalsIgnoreCase(ticketDetails.getString("isMainReceipt")
						.toString())) {
			validationErrors.add("Is Main Receipt cannot be blank");
			return validationErrors;
		}
		if (!("Y".equalsIgnoreCase(ticketDetails.getString("isMainReceipt")) || "N".equalsIgnoreCase(ticketDetails.getString("isMainReceipt")))) {
			validationErrors.add("Invalid value input in Is Main Receipt, Only Y or N value should be input in Is Main Receipt. Please recheck the value");
			return validationErrors;
		}
		

		if (ticketDetails.getDouble("receiptRefundAmount") <= 0.0D) {
			validationErrors
					.add("Invalid value input in Receipt Refund Amount, Only value greater than 0 should be input in Receipt Refund Amount. Please recheck the value");
			return validationErrors;
		}

		if (ticketDetails.getString("payeeType") == null
				|| "".equalsIgnoreCase(ticketDetails.getString("payeeType")
						.toString())) {
			validationErrors.add("Payee Type cannot be blank");
			return validationErrors;
		}

		if (!(ticketDetails.getString("payeeType").equalsIgnoreCase(
				Constants.CUSTOMER.getValue())
				|| ticketDetails.getString("payeeType").equalsIgnoreCase(
						Constants.INTERMEDIARY.getValue())
				|| ticketDetails.getString("payeeType").equalsIgnoreCase(
						Constants.DEALER.getValue())
				|| ticketDetails.getString("payeeType").equalsIgnoreCase(
						Constants.FINANCIER.getValue()) || ticketDetails
				.getString("payeeType").equalsIgnoreCase(
						Constants.OTHERS.getValue()))) {
			validationErrors
					.add("Invalid value input in Payee Type. Please recheck the value");
			return validationErrors;
		}
      /*
		if (ticketDetails.getString("refundMode") == null
				|| "".equalsIgnoreCase(ticketDetails.getString("refundMode"))) {
			validationErrors.add("Refund Mode cannot be blank");
			return validationErrors;
		}*/
		if (!"".equalsIgnoreCase(ticketDetails.getString("refundMode"))
				&& ticketDetails.getString("refundMode") != null)
		{
		if (!(ticketDetails.getString("refundMode").equalsIgnoreCase(
				Constants.NEFT.getValue())
				|| ticketDetails.getString("refundMode").equalsIgnoreCase(
						Constants.Cheque.getValue()) || ticketDetails
				.getString("refundMode").equalsIgnoreCase(
						Constants.DebitAuthorization.getValue()))) {
			validationErrors
					.add("Invalid value input in Refund Mode. Please recheck the value");
			return validationErrors;
		}
		}
		// Validations for DEBIT AUTHORIZATION Refund Mode
		if (ticketDetails.getString("refundMode").equalsIgnoreCase(
				Constants.DebitAuthorization.getValue())) {
			if ("".equalsIgnoreCase(ticketDetails.getString("authCode"))
					|| ticketDetails.getString("authCode") == null) {
				validationErrors
						.add("AuthCode Transaction Number cannot be blank for Debit Authorization Refund Mode");
				return validationErrors;
			}
		}
		// Validations for NEFT Refund Mode
		if (ticketDetails.getString("refundMode").equalsIgnoreCase(
				Constants.NEFT.getValue())) {

			/*if ("".equalsIgnoreCase(ticketDetails
			.getString("accountDetails/beneficiaryBank"))
			|| ticketDetails
					.getString("accountDetails/beneficiaryBank") == null) {
		validationErrors
				.add("Beneficiary Bank cannot be blank for NEFT Refund Mode");
		return validationErrors;
	}*/
	if (!"".equalsIgnoreCase(ticketDetails
			.getString("accountDetails/beneficiaryBank"))
			&& ticketDetails
					.getString("accountDetails/beneficiaryBank") != null) {
		
		String bankPattern = "^[a-zA-Z0-9,. ]*$";
		if (!(ticketDetails
				.getString("accountDetails/beneficiaryBank").matches(bankPattern))) {
			validationErrors
					.add("Invalid value input in Beneficiary Bank, Beneficiary Bank should be alphanumerics only. Please recheck the value");
			return validationErrors;
		}
		
		int customerBankLen = ticketDetails.getString(
				"accountDetails/beneficiaryBank").length();
		if (customerBankLen > 60) {
			validationErrors
					.add("Invalid value input in Beneficiary Bank, Value exceeding field length limit 60 characters. Please recheck the value");
			return validationErrors;
		}
	}
	/*if ("".equalsIgnoreCase(ticketDetails
			.getString("accountDetails/beneficiaryIFSCCode"))
			|| ticketDetails
					.getString("accountDetails/beneficiaryIFSCCode") == null) {
		validationErrors
				.add("Beneficiary IFSC Code cannot be blank for NEFT Refund Mode");
		return validationErrors;
	}*/
	String bankAccountNoPattern = "^[a-zA-Z0-9]*$";
	
	String ifscValidation = "^[A-Za-z]{4}0[A-Za-z0-9]{6}$";
	if (!"".equalsIgnoreCase(ticketDetails
			.getString("accountDetails/beneficiaryIFSCCode"))
			&& ticketDetails
					.getString("accountDetails/beneficiaryIFSCCode") != null) {
		if (!(ticketDetails
				.getString("accountDetails/beneficiaryIFSCCode").matches(bankAccountNoPattern))) {
			validationErrors
					.add("Invalid value input in Beneficiary IFSC Code, Beneficiary Beneficiary IFSC Code should be alphanumerics only. Please recheck the value");
			return validationErrors;
		}
		if (!(ticketDetails
				.getString("accountDetails/beneficiaryIFSCCode")
				.matches(ifscValidation))) {
			validationErrors
					.add("Invalid value input in Beneficiary IFSC Code. Please enter Beneficiary IFSC Code value in correct format");
			return validationErrors;
		}
	}

	/*if ("".equalsIgnoreCase(ticketDetails
			.getString("accountDetails/beneficiaryAccountNumber"))
			|| ticketDetails
					.getString("accountDetails/beneficiaryAccountNumber") == null) {
		validationErrors
				.add("Beneficiary Account Number cannot be blank for NEFT Refund Mode");
		return validationErrors;
	}*/

	if (!"".equalsIgnoreCase(ticketDetails
			.getString("accountDetails/beneficiaryAccountNumber"))
			&& ticketDetails
					.getString("accountDetails/beneficiaryAccountNumber") != null) {
		
		if (!(ticketDetails
				.getString("accountDetails/beneficiaryAccountNumber").matches(bankAccountNoPattern))) {
			validationErrors
					.add("Invalid value input in Beneficiary Bank Account Number, Beneficiary Bank Account Number should be alphanumerics only. Please recheck the value");
			return validationErrors;
		}
		int bankAccountNoLen = ticketDetails.getString(
				"accountDetails/beneficiaryAccountNumber").length();
		if (bankAccountNoLen > 20) {
			validationErrors
					.add("Invalid value input in  Beneficiary Bank Account Number, Value exceeding field length limit 20 characters. Please recheck the value");
			return validationErrors;
		}
	    }

		}
		// Validations for CHEQUE Refund Mode
		if (ticketDetails.getString("refundMode").equalsIgnoreCase(
				Constants.Cheque.getValue())) {

			if ("".equalsIgnoreCase(ticketDetails
					.getString("accountDetails/addressLine1"))
					|| ticketDetails.getString("accountDetails/addressLine1") == null) {
				validationErrors
						.add("Address Line 1 cannot be blank for CHEQUE Refund Mode");
				return validationErrors;
			}

			if (!"".equalsIgnoreCase(ticketDetails
					.getString("accountDetails/addressLine1"))
					&& ticketDetails.getString("accountDetails/addressLine1") != null) {
				int addressLine1Len = ticketDetails.getString(
						"accountDetails/addressLine1").length();
				if (addressLine1Len > 35) {
					validationErrors
							.add("Invalid value input in Address Line 1, Value exceeding field length limit 35 characters. Please recheck the value");
					return validationErrors;
				}
			}

			if (!"".equalsIgnoreCase(ticketDetails
					.getString("accountDetails/addressLine2"))
					&& ticketDetails.getString("accountDetails/addressLine2") != null) {
				int addressLine2Len = ticketDetails.getString(
						"accountDetails/addressLine2").length();
				if (addressLine2Len > 35) {
					validationErrors
							.add("Invalid value input in Address Line 2, Value exceeding field length limit 35 characters. Please recheck the value");
					return validationErrors;
				}
			}

			if (!"".equalsIgnoreCase(ticketDetails
					.getString("accountDetails/addressLine3"))
					&& ticketDetails.getString("accountDetails/addressLine3") != null) {
				int addressLine3Len = ticketDetails.getString(
						"accountDetails/addressLine3").length();
				if (addressLine3Len > 35) {
					validationErrors
							.add("Invalid value input in Address Line 3, Value exceeding field length limit 35 characters. Please recheck the value");
					return validationErrors;
				}
			}

			if (!"".equalsIgnoreCase(ticketDetails
					.getString("accountDetails/area"))
					&& ticketDetails.getString("accountDetails/area") != null) {
				int areaLen = ticketDetails.getString("accountDetails/area")
						.length();
				if (areaLen > 30) {
					validationErrors
							.add("Invalid value input in Area, Value exceeding field length limit 30 characters. Please recheck the value");
					return validationErrors;
				}
			}
			/*
			if ("".equalsIgnoreCase(ticketDetails
					.getString("accountDetails/city"))
					|| ticketDetails.getString("accountDetails/city") == null) {
				validationErrors
						.add("City cannot be blank for CHEQUE Refund Mode");
				return validationErrors;
			}*/

			if (!"".equalsIgnoreCase(ticketDetails
					.getString("accountDetails/city"))
					&& ticketDetails.getString("accountDetails/city") != null) {
				int cityLen = ticketDetails.getString("accountDetails/city")
						.length();
				if (cityLen > 30) {
					validationErrors
							.add("Invalid value input in City, Value exceeding field length limit 30 characters. Please recheck the value");
					return validationErrors;
				}
				/*
				String cityPattern = "^[a-zA-z]+([\\s][a-zA-Z]+)*$";

				if (!(ticketDetails.getString("accountDetails/city")
						.matches(cityPattern))) {
					validationErrors
							.add("Invalid value input in City, Only alphabets should be input in State. Please recheck the value");
					return validationErrors;
				}*/
			}
			/*
			if ("".equalsIgnoreCase(ticketDetails
					.getString("accountDetails/state"))
					|| ticketDetails.getString("accountDetails/state") == null) {
				validationErrors
						.add("State cannot be blank for CHEQUE Refund Mode");
				return validationErrors;
			}
			 */
			if (!"".equalsIgnoreCase(ticketDetails
					.getString("accountDetails/state"))
					&& ticketDetails.getString("accountDetails/state") != null) {
				int stateLen = ticketDetails.getString("accountDetails/state")
						.length();
				if (stateLen > 30) {
					validationErrors
							.add("Invalid value input in State, Value exceeding field length limit 30 characters. Please recheck the value");
					return validationErrors;
				}
                /*
				String statePattern = "^[a-zA-z]+([\\s][a-zA-Z]+)*$";

				if (!(ticketDetails.getString("accountDetails/state")
						.matches(statePattern))) {
					validationErrors
							.add("Invalid value input in State, Only alphabets should be input in State. Please recheck the value");
					return validationErrors;
				}
				*/
			}

			if ("".equalsIgnoreCase(ticketDetails
					.getString("accountDetails/pincode"))
					|| ticketDetails.getString("accountDetails/pincode") == null) {
				validationErrors
						.add("Pincode cannot be blank for CHEQUE Refund Mode");
				return validationErrors;
			}

			if (!"".equalsIgnoreCase(ticketDetails
					.getString("accountDetails/pincode"))
					&& ticketDetails.getString("accountDetails/pincode") != null) {
				String pincodePattern = "^[0-9]{6}$";

				if (!(ticketDetails.getString("accountDetails/pincode")
						.matches(pincodePattern))) {
					validationErrors
							.add("Invalid value input in Pincode, Only 6 digits numeric value should be input in Pincode. Please recheck the value");
					return validationErrors;
				}
			}

		}
		return validationErrors;
	}

	// Generation of Draft Ticket Number
	public String getExcessRefundDraftTicketNumber(String category) {

		DataObject appConfigBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/appconfigbg",
						"AppConfigBG");

		DataObject appConfigData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/appconfig",
						"AppConfig");
		
		DataObject draftNoBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftnobg",
						"DraftNoBG");

		DataObject draftNo = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftno",
						"DraftNo");

		// Object to store Business Exception Data
		DataObject exceptionData = null;

		if (category == null || "".equalsIgnoreCase(category.trim())) {
			throw new RuntimeException("Category cannot be blank");
		}
		try {
			appConfigData.setString("parameter1", category);
			appConfigBG.setDataObject("AppConfig", appConfigData);
			DataObject response = (DataObject) this
					.locateService_DBInterfacePartner().invoke(
							"retrieveallAppConfigBG", appConfigBG);
			String draftFormat = response
					.getString("retrieveallAppConfigBGOutput/AppConfig[0]/name");
			draftNoBG.setDataObject("DraftNo", draftNo);
			DataObject draftTicketresponse = (DataObject) this
					.locateService_DBInterfacePartner().invoke(
							"retrieveallDraftNoBG", draftNoBG);
			Integer draftTicketSeries = draftTicketresponse
					.getInt("retrieveallDraftNoBGOutput/DraftNo/next_value");
			System.out.println("Format --> " + draftFormat
					+ " Updated Value --> " + draftTicketSeries);
			String draftTicketNo = "";

			draftTicketNo = draftFormat + String.valueOf(draftTicketSeries);

			DataObject updateAppConfigBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateappconfigbg",
							"UpdateAppConfigBG");

			DataObject updateAppConfigInput = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateappconfig",
							"UpdateAppConfig");

			updateAppConfigInput.setString("statement1parameter2",
					"EXCESS REFUND DRAFT SERIES");
			updateAppConfigInput.setString("statement1parameter1",
					String.valueOf(draftTicketSeries));
		
			updateAppConfigBG.setDataObject("UpdateAppConfig",
					updateAppConfigInput);

			this.locateService_DBInterfacePartner().invoke(
					"executeUpdateAppConfigBG", updateAppConfigBG);
			return draftTicketNo;
		} catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();

			throw new ServiceBusinessException(exceptionData.get("message")
					.toString());

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage().toString(), sre);
		} catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException(e.getMessage().toString(), e);
		}

	}
	/**
	 * Method generated to support implementation of operation "createRASExcessRefundDraftTicket" defined for WSDL port type 
	 * named "RASIntegrationService".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a parameter 
	 * type conveys that it is a complex type. Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public DataObject createRASExcessRefundDraftTicket(DataObject rasTicketInput) throws IOException, ParseException {
		// To create a DataObject, use the creation methods on the BOFactory:
		// com.ibm.websphere.bo.BOFactory boFactory = (com.ibm.websphere.bo.BOFactory) ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");
		//
		// To get or set attributes for a DataObject such as rasTicketInput, use the APIs as shown below:
		// To set a string attribute in rasTicketInput, use rasTicketInput.setString(stringAttributeName, stringValue)
		// To get a string attribute in rasTicketInput, use rasTicketInput.getString(stringAttributeName)
		// To set a dataObject attribute in rasTicketInput, use rasTicketInput.setDataObject(stringAttributeName, dataObjectValue)
		// To get a dataObject attribute in rasTicketInput, use rasTicketInput.getDataObject(stringAttributeName)
		DataObject draftTicketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_excess_refund_draftbg",
						"DboRas_Excess_Refund_DraftBG");
		DataObject draftTicketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_excess_refund_draft",
						"DboRas_Excess_Refund_Draft");
		DataObject ticketDetailsIn = getBOFactory().create(
				"http://RASAccess-ApplicationDB/RASHealthIntegrationData",
				"ExcessRefundDraftInputTicketDetails");

		DataObject draftTicketOut = getBOFactory().create(
				"http://RASAccess-ApplicationDB/RASHealthIntegrationData",
				"ExcessRefundDraftTicketOutput");
		DataObject exceptionData = null;
		
		
		int serviceId = convertRasTicketInputObjectToXML(rasTicketInput);
		int draftId =0;
		String ntid="";


		if (rasTicketInput == null) {
			draftTicketOut.setString("ticketDetailsOut[1]/status", "FAILED");
			draftTicketOut.setString("ticketDetailsOut[1]/message",
					"RAS Mandatory Validations");
			draftTicketOut.setString("ticketDetailsOut[1]/errorCode", "100001");
			draftTicketOut.setString("ticketDetailsOut[1]/errorDetail",
					"rasTicketInput object cannot be blank");
		} else {
			String errorString = "";
			String ProductCodePattern = "^[0-9]{4}$";
			if (rasTicketInput.getString("source") != null && !"".equalsIgnoreCase(rasTicketInput.getString("source")) && rasTicketInput.getString("bopsChannel") != null && !"".equalsIgnoreCase(rasTicketInput.getString("bopsChannel")) && rasTicketInput.getString("source").length()<=200 && rasTicketInput.getString("bopsChannel").length()<=200)
			{
			try{
				DataObject bopsChannelNtidConfigDataBG = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/bopschannelntidconfigbg",
								"BopsChannelNtidConfigBG");

				DataObject bopsChannelNtidConfigData = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/bopschannelntidconfig",
								"BopsChannelNtidConfig");
				
				DataObject bopsChannelNtidConfigResponseBO = null;
				bopsChannelNtidConfigData.setString("parameter1", rasTicketInput.getString("source").toUpperCase());
				bopsChannelNtidConfigData.setString("parameter2", rasTicketInput.getString("bopsChannel").toUpperCase());
				bopsChannelNtidConfigDataBG.setDataObject("BopsChannelNtidConfig", bopsChannelNtidConfigData);
				
				bopsChannelNtidConfigResponseBO = (DataObject) locateService_DBInterfacePartner()
						.invoke("retrieveallBopsChannelNtidConfigBG", bopsChannelNtidConfigDataBG);
				
				
				ntid = bopsChannelNtidConfigResponseBO.getString("retrieveallBopsChannelNtidConfigBGOutput/BopsChannelNtidConfig[0]/ntid");
				
			
				System.out.println("NTID " + ntid);
				draftTicketData.setString("created_by", ntid);
				}
				catch (ServiceBusinessException sbe) {
					exceptionData = (DataObject) sbe.getData();
                    errorString = exceptionData.get("message").toString()+" error while fetching the ntid from source and bops channel";
				} 
				catch (ServiceRuntimeException sre) {
					errorString = sre.getMessage().toString()+" error while fetching the ntid from source and bops channel";
				} 
				catch (Exception e) {
					errorString = e.getMessage()+" error while fetching the ntid from source and bops channel";
				}
			
			if (errorString != null && !"".equalsIgnoreCase(errorString))
			{
			draftTicketOut.setString("ticketDetailsOut[1]/status", "FAILED");
			draftTicketOut
					.setString("ticketDetailsOut[1]/message",
							"Database Retrieve Operation Failed");
			draftTicketOut.setString("ticketDetailsOut[1]/errorCode", "100002");
			draftTicketOut.setString("ticketDetailsOut[1]/errorDetail",errorString);
			draftTicketData.setString("status", "FAILED");
			draftTicketData.setString("error_code", "100002");
			draftTicketData.setString("error_details", errorString);
			draftTicketData.setString("gc_error", "");
			}
			}
			if (rasTicketInput.getString("productCode") != null && !"".equalsIgnoreCase(rasTicketInput.getString("productCode")) && rasTicketInput.getString("productCode").matches(ProductCodePattern) && (errorString == null || "".equalsIgnoreCase(errorString)))
			{
			
			try{
				DataObject productBG = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/productbg",
								"ProductBG");

				DataObject productData = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/product",
								"Product");
				productData.setString("parameter1",
						rasTicketInput.getString("productCode"));
				productBG.setDataObject("Product",
						productData);

				DataObject lobName = (DataObject) locateService_DBInterfacePartner()
						.invoke("retrieveallProductBG",
								productBG);

				String lob = lobName
						.getString("retrieveallProductBGOutput/Product[0]/lob");

				System.out.println("LOB " + lob);
				rasTicketInput.setString("lob", lob);
				draftTicketData.setString("lob", lob);
				}
				catch (ServiceBusinessException sbe) {
					exceptionData = (DataObject) sbe.getData();
                    errorString = exceptionData.get("message").toString()+" error while fetching the LOB from product code";
				} 
				catch (ServiceRuntimeException sre) {
					errorString = sre.getMessage().toString()+" error while fetching the LOB from product code";
				} 
				catch (Exception e) {
					errorString = e.getMessage()+" error while fetching the LOB from product code";
				}
			if (errorString != null && !"".equalsIgnoreCase(errorString))
			{
			draftTicketOut.setString("ticketDetailsOut[1]/status", "FAILED");
			draftTicketOut
					.setString("ticketDetailsOut[1]/message",
							"Database Retrieve Operation Failed");
			draftTicketOut.setString("ticketDetailsOut[1]/errorCode", "100002");
			draftTicketOut.setString("ticketDetailsOut[1]/errorDetail",errorString);
			draftTicketData.setString("status", "FAILED");
			draftTicketData.setString("error_code", "100002");
			draftTicketData.setString("error_details", errorString);
			draftTicketData.setString("gc_error", "");
			}
			}

			List<String> valErrors = new ArrayList();
			valErrors = validateRASTicketInput(rasTicketInput);
			if (errorString == null || "".equalsIgnoreCase(errorString))
			{
			if ((valErrors != null) && (!valErrors.isEmpty())) {
				for (String error : valErrors) {
					if (errorString == "") {
						errorString = errorString + error;
					} else
						errorString = errorString + " ," + error;
				}
				draftTicketData.setString("status", "FAILED");
				draftTicketData.setString("error_code", "100001");
				draftTicketData.setString("error_details", errorString);
				draftTicketData.setString("gc_error", "");

				draftTicketOut
						.setString("ticketDetailsOut[1]/status", "FAILED");
				draftTicketOut.setString("ticketDetailsOut[1]/message",
						"RAS Mandatory Validations");
				draftTicketOut.setString("ticketDetailsOut[1]/errorCode",
						"100001");
				draftTicketOut.setString("ticketDetailsOut[1]/errorDetail",
						errorString);

			}
			}
			if (errorString == null || "".equalsIgnoreCase(errorString)) {
				if (rasTicketInput.getList("ticketDetails") != null
						&& rasTicketInput.getList("ticketDetails").size() != 0) {

					// Accessing ticket Details one by one and Assigning them to
					// a List
					for (int i = 1; i <= rasTicketInput
							.getList("ticketDetails").size(); i++) {
						errorString = "";
						if (rasTicketInput.getDataObject("ticketDetails[" + i
								+ "]") == null) {
							errorString = "ticketDetails object cannot be blank";
							draftTicketOut.setString("ticketDetailsOut[" + i
									+ "]/status", "FAILED");
							draftTicketOut.setString("ticketDetailsOut[" + i
									+ "]/message", "RAS Mandatory Validations");
							draftTicketOut.setString("ticketDetailsOut[" + i
									+ "]/errorCode", "100001");
							draftTicketOut.setString("ticketDetailsOut[" + i
									+ "]/errorDetail", errorString);

						}
						if (errorString == null
								|| "".equalsIgnoreCase(errorString)) {
							draftId = incrementSequence("EXCESS REFUND DRAFT SEQUENCE");
							System.out.println("Draft ID "+draftId);
							ticketDetailsIn = rasTicketInput
									.getDataObject("ticketDetails[" + i + "]");
							valErrors = validateTicketDetails(ticketDetailsIn);
							errorString = "";
							if ((valErrors != null) && (!valErrors.isEmpty())) {
								for (String error : valErrors) {
									if (errorString == "") {
										errorString = errorString + error;
									} else
										errorString = errorString + " ,"
												+ error;
								}
								draftTicketData.setString("status", "FAILED");
								draftTicketData.setString("error_code", "100001");
								draftTicketData.setString("error_details",
										errorString);
								
								draftTicketOut.setString("ticketDetailsOut["
										+ i + "]/status", "FAILED");
								draftTicketOut.setString("ticketDetailsOut["
										+ i + "]/receiptNumber", rasTicketInput
										.getString("ticketDetails[" + i
												+ "]/receiptNumber"));
								draftTicketOut.setString("ticketDetailsOut["
										+ i + "]/message",
										"RAS Mandatory Validations");
								draftTicketOut.setString("ticketDetailsOut["
										+ i + "]/errorCode", "100001");
								draftTicketOut.setString("ticketDetailsOut["
										+ i + "]/errorDetail", errorString);
								

							}
							draftTicketData.setInt(
									"ras_excess_refund_draft_id", draftId);
							draftTicketData.setInt(
									"ras_excess_refund_service_id", serviceId);
							draftTicketData.setString("source",
									rasTicketInput.getString("source"));
							draftTicketData.setString("source_unique_id",
									rasTicketInput.getString("sourceUniqueId"));
							draftTicketData.setString("source_username",
									rasTicketInput.getString("sourceUserName"));
							draftTicketData.setString("bops_channel",
									rasTicketInput.getString("bopsChannel"));
							draftTicketData.setString("email_id",
									rasTicketInput.getString("emailId"));
							draftTicketData.setString("contact_no",
									rasTicketInput.getString("contactNo"));
							draftTicketData.setInt("gc_transaction_count",
									0);
							draftTicketData.setString(
									"draft_ticket_no", "");
							draftTicketData.setString("product_code",
									rasTicketInput.getString("productCode"));
							draftTicketData.setString("ticket_remarks",
									rasTicketInput.getString("ticketRemarks"));
							if(rasTicketInput.getString("documentDetails/documentURL")!=null && !"".equalsIgnoreCase(rasTicketInput.getString("documentDetails/documentURL")))
							draftTicketData
									.setString(
											"document_url",
											rasTicketInput
													.getString("documentDetails/documentURL"));
							String documentType = "";
							String isAvailable = "";
							List<String> docTypeList = new ArrayList();
							List<String> isAvailableList = new ArrayList();
							for (int j = 1; j <= rasTicketInput.getList(
									"documentDetails/documents").size(); j++) {
								docTypeList.add(rasTicketInput
										.getString("documentDetails/documents["
												+ j + "]/documentType"));
								isAvailableList.add(rasTicketInput
										.getString("documentDetails/documents["
												+ j + "]/isAvailable"));
							}
						
							if (docTypeList != null && (!docTypeList.isEmpty())) {

								for (String docType : docTypeList) {
									if (documentType == "") {
										documentType = documentType + docType;
									} else
										documentType = documentType + ","
												+ docType;
								}

							}
							if (isAvailableList != null
									&& (!isAvailableList.isEmpty())) {

								for (String available : isAvailableList) {
									if (isAvailable == "") {
										isAvailable = isAvailable + available;
									} else
										isAvailable = isAvailable + ","
												+ available;
								}

							}
							System.out.println("Documents" + documentType);
							System.out.println("Is Available" + isAvailable);

							draftTicketData.setString("document_type",
									documentType);
							draftTicketData.setString("is_available",
									isAvailable);

							draftTicketData.setString(
									"receipt_number",
									rasTicketInput.getString("ticketDetails["
											+ i + "]/receiptNumber"));
							draftTicketData.setString(
									"is_main_receipt",
									rasTicketInput.getString("ticketDetails["
											+ i + "]/isMainReceipt"));

							double receiptRefundAmount = rasTicketInput
									.getDouble("ticketDetails[" + i
											+ "]/receiptRefundAmount");
							BigDecimal receiptRefundAmountDecimal = BigDecimal
									.valueOf(receiptRefundAmount);
							draftTicketData.setBigDecimal(
									"receipt_refund_amount",
									receiptRefundAmountDecimal);

							double ppcAmount = rasTicketInput
									.getDouble("ticketDetails[" + i
											+ "]/ppcAmount");
							BigDecimal ppcAmountDecimal = BigDecimal
									.valueOf(ppcAmount);
							draftTicketData.setBigDecimal("ppc_amount",
									ppcAmountDecimal);

							draftTicketData.setString(
									"payee_type",
									rasTicketInput.getString("ticketDetails["
											+ i + "]/payeeType"));
							draftTicketData.setString(
									"refund_mode",
									rasTicketInput.getString("ticketDetails["
											+ i + "]/refundMode"));
							draftTicketData.setString(
									"auth_code",
									rasTicketInput.getString("ticketDetails["
											+ i + "]/authCode"));
							System.out.println("Account Details "+rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails"));
							System.out.println("Account Details IFSC "+rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/beneficiaryIFSCCode"));
							
							if(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/beneficiaryBank")!=null && !"".equalsIgnoreCase(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/beneficiaryBank")))
								draftTicketData
								.setString(
										"beneficiary_bank",
										rasTicketInput
												.getString("ticketDetails["
														+ i
														+ "]/accountDetails/beneficiaryBank"));
							else
									draftTicketData
									.setString(
											"beneficiary_bank",
											"");
							
							if(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/beneficiaryIFSCCode")!=null && !"".equalsIgnoreCase(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/beneficiaryIFSCCode")))
						        draftTicketData
								.setString(
										"beneficiary_ifsc_code",
										rasTicketInput
												.getString("ticketDetails["
														+ i
														+ "]/accountDetails/beneficiaryIFSCCode"));
							else 
							  draftTicketData
								.setString(
										"beneficiary_ifsc_code",
										"");
							  
							if(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/beneficiaryAccountNumber")!=null && !"".equalsIgnoreCase(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/beneficiaryAccountNumber")))
							
						        draftTicketData
								.setString(
										"beneficiary_account_number",
										rasTicketInput
												.getString("ticketDetails["
														+ i
														+ "]/accountDetails/beneficiaryAccountNumber"));
							else
								draftTicketData
								.setString(
										"beneficiary_account_number",
										"");
								
							  
							if(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/addressLine1")!=null && !"".equalsIgnoreCase(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/addressLine1")))
						        draftTicketData.setString(
								"address_line1",
								rasTicketInput.getString("ticketDetails["
										+ i
										+ "]/accountDetails/addressLine1"));
							else
								draftTicketData.setString(
										"address_line1",
										"");
							
							if(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/addressLine2")!=null && !"".equalsIgnoreCase(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/addressLine2")))
						        draftTicketData.setString(
								"address_line2",
								rasTicketInput.getString("ticketDetails["
										+ i
										+ "]/accountDetails/addressLine2"));
							else
								   draftTicketData.setString(
											"address_line2",
											"");
							
							if(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/addressLine3")!=null && !"".equalsIgnoreCase(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/addressLine3")))
						        draftTicketData.setString(
								"address_line3",
								rasTicketInput.getString("ticketDetails["
										+ i
										+ "]/accountDetails/addressLine3"));
							else
								 draftTicketData.setString(
											"address_line3",
											"");
							if(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/area")!=null && !"".equalsIgnoreCase(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/area")))
						        draftTicketData.setString(
								"area",
								rasTicketInput.getString("ticketDetails["
										+ i + "]/accountDetails/area"));
							else
								draftTicketData.setString(
										"area",
									     "");
							
							if(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/city")!=null && !"".equalsIgnoreCase(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/city")))	
						        draftTicketData.setString(
								"city",
								rasTicketInput.getString("ticketDetails["
										+ i + "]/accountDetails/city"));
							else
								 draftTicketData.setString(
											"city",
											"");
							
							if(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/state")!=null && !"".equalsIgnoreCase(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/state")))	
						        draftTicketData.setString(
								"state",
								rasTicketInput.getString("ticketDetails["
										+ i + "]/accountDetails/state"));
							else
								 draftTicketData.setString(
											"state",
										    "");
							
							if(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/pincode")!=null && !"".equalsIgnoreCase(rasTicketInput.getString("ticketDetails["+ i+ "]/accountDetails/pincode")))	
						        draftTicketData.setString(
								"pincode",
								rasTicketInput.getString("ticketDetails["
										+ i + "]/accountDetails/pincode"));
							else
						        draftTicketData.setString(
										"pincode",
										"");
							draftTicketData.setDate("created_on",
									formatDate(new Date()));
							/*draftTicketData.setString("created_by",
									rasTicketInput.getString("sourceUniqueId"));*/
							
							try {
								
								if (errorString == null
										|| "".equalsIgnoreCase(errorString)) {
									String draftTicketNo = getExcessRefundDraftTicketNumber("EXCESS REFUND DRAFT SERIES");
									draftTicketData.setString(
											"draft_ticket_no", draftTicketNo);
									draftTicketData.setString("status",
											"SUCCESS");

									draftTicketOut.setString(
											"ticketDetailsOut[" + i
													+ "]/status", "SUCCESS");
									draftTicketOut
											.setString(
													"ticketDetailsOut[" + i
															+ "]/receiptNumber",
													rasTicketInput
															.getString("ticketDetails["
																	+ i
																	+ "]/receiptNumber"));
									draftTicketOut.setString(
											"ticketDetailsOut[" + i
													+ "]/draftTicketNumber",
											draftTicketNo);
									
								}

								draftTicketDataBG.set(
										"DboRas_Excess_Refund_Draft",
										draftTicketData);
								this.locateService_DBInterfacePartner().invoke(
										"createDboRas_Excess_Refund_DraftBG",
										draftTicketDataBG);
								//CreateRASExcessRefundTicketFromDraft BPEL partner createRASExcessRefundTicketFromDraft operation invoke asynchronously by passing draftTicketNo as input
								if(draftTicketOut.getString("ticketDetailsOut["+ i + "]/draftTicketNumber")!=null && !"".equalsIgnoreCase(draftTicketOut.getString("ticketDetailsOut["+ i + "]/draftTicketNumber")))
								this.locateService_RASExcessRefundTicketFromDraftPartner().invokeAsync("createRASExcessRefundTicketFromDraft", draftTicketOut.getString("ticketDetailsOut["+ i + "]/draftTicketNumber"));


							} catch (ServiceBusinessException sbe) {
								exceptionData = (DataObject) sbe.getData();
								errorString = exceptionData.get("message").toString();

							} catch (ServiceRuntimeException sre) {
								errorString = sre.getMessage().toString();

							} catch (Exception e) {
								errorString =e.getMessage();
							}
							if (errorString != null && !"".equalsIgnoreCase(errorString))
							{
							draftTicketOut.setString("ticketDetailsOut["
									+ i + "]/status", "FAILED");
							draftTicketOut
									.setString("ticketDetailsOut[" + i
											+ "]/message",
											"Create RAS Excess Refund Draft Ticket Service Failed");
							draftTicketOut.setString("ticketDetailsOut["
									+ i + "]/errorCode", "100005");
							draftTicketOut.setString("ticketDetailsOut["
									+ i + "]/errorDetail", errorString);
							}
						}
					}
				} else {
					draftTicketOut.setString("ticketDetailsOut[1]/status",
							"FAILED");
					draftTicketOut.setString("ticketDetailsOut[1]/message",
							"RAS Mandatory Validations");
					draftTicketOut.setString("ticketDetailsOut[1]/errorCode",
							"100001");
					draftTicketOut.setString("ticketDetailsOut[1]/errorDetail",
							"List of ticketDetails object cannot be blank");
				}
			}
		}
		convertRasTicketOutputObjectToXML(draftTicketOut, serviceId,rasTicketInput.getString("sourceUniqueId"));
		return draftTicketOut;
	}

	/**
	 * Method generated to support implementation of operation "findExcessRefundDraftTicketDetails" defined for WSDL port type 
	 * named "RASIntegrationService".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a parameter 
	 * type conveys that it is a complex type. Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public DataObject findExcessRefundDraftTicketDetails(String draftTicketNo) {
		//TODO Needs to be implemented.
		DataObject draftTicketResponseBO = null;
		DataObject rasException = getBOFactory().create("http://RACASBO",
				"RASException");
		DataObject exceptionData = null;
		DataObject ticketBO = getBOFactory().create("http://RACASBO",
				"TicketBO");
		DataObject draftTicketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftticketbg",
						"DraftTicketBG");

		DataObject draftTicketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftticket",
						"DraftTicket");
		List draftTicketDataList =null;
		String channelMode = "";

		if (draftTicketNo != null && !("".equalsIgnoreCase(draftTicketNo.trim()))) {
			draftTicketData.setString("parameter1", draftTicketNo);

		} else {
			throw new RuntimeException("Draft Ticket Number cannot be blank or empty");
		}
		draftTicketDataBG.setDataObject("DraftTicket", draftTicketData);
		// invoke service

		try {
			
			draftTicketResponseBO = (DataObject) locateService_DBInterfacePartner()
					.invoke("retrieveallDraftTicketBG", draftTicketDataBG);
			System.out.println("EXECUTED");
			System.out.print("source"+draftTicketResponseBO
			.getString("retrieveallDraftTicketBGOutput/DraftTicket[0]/source"));
			
			draftTicketDataList =draftTicketResponseBO
			.getList("retrieveallDraftTicketBGOutput/DraftTicket");
			draftTicketData = (DataObject) draftTicketDataList.get(0);
	        try
	        {
			
			DataObject bopsChannelNtidConfigDataBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/bopschannelntidconfigbg",
							"BopsChannelNtidConfigBG");

			DataObject bopsChannelNtidConfigData = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/bopschannelntidconfig",
							"BopsChannelNtidConfig");
			DataObject bopsChannelNtidConfigResponseBO = null;
			bopsChannelNtidConfigData.setString("parameter1", draftTicketData.getString("source"));
			bopsChannelNtidConfigData.setString("parameter2", draftTicketData.getString("bops_channel"));
		
			bopsChannelNtidConfigDataBG.setDataObject("BopsChannelNtidConfig", bopsChannelNtidConfigData);
			
			bopsChannelNtidConfigResponseBO = (DataObject) locateService_DBInterfacePartner()
					.invoke("retrieveallBopsChannelNtidConfigBG", bopsChannelNtidConfigDataBG);
			
			
			channelMode = bopsChannelNtidConfigResponseBO.getString("retrieveallBopsChannelNtidConfigBGOutput/BopsChannelNtidConfig[0]/channel_mode");
			ticketBO.setString("channelMode", channelMode);
	        }
			catch (ServiceBusinessException sbe) {

				exceptionData = (DataObject) sbe.getData();
				rasException.setString("status", "FAILED");
				rasException.setString("error[0]/errorCode", "100002");
				rasException.setString("error[0]/message",
						exceptionData.get("message").toString()+" error while fetching channel mode from source and bops channel");
				throw new ServiceBusinessException(rasException);

			} catch (ServiceRuntimeException sre) {
				throw new ServiceRuntimeException(sre.getMessage()+" error while fetching channel mode from source and bops channel", sre);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage()+" error while fetching channel mode from source and bops channel", e);
			}
			ticketBO.setString("refundType",Constants.ExcessRefund.getValue());
			ticketBO.setString("lob", draftTicketData.getString("lob"));
			ticketBO.setString("receiptNo", draftTicketData.getString("receipt_number"));
			ticketBO.setString("requestType", Constants.CUSTOMERINITIATED.getValue());
			ticketBO.setString("ticketType", Constants.IndividualTicket.getValue());
			ticketBO.setString("stage", "BOPS");
			ticketBO.setString("status", "New Ticket Raised");
			ticketBO.setString("product", draftTicketData.getString("product_code"));
			ticketBO.setString("source", draftTicketData.getString("source").toUpperCase());
			ticketBO.setString("ticketRemarks", draftTicketData.getString("ticket_remarks"));
			ticketBO.setDouble("receiptRefundAmount", draftTicketData.getDouble("receipt_refund_amount"));
			ticketBO.setDouble("PPCAmount", draftTicketData.getDouble("ppc_amount"));
			ticketBO.setInt("gcTransactionCount", draftTicketData.getInt("gc_transaction_count"));
			//OTHERS payee type considered as CUSTOMER payee type
			if(draftTicketData.getString("payee_type").equalsIgnoreCase(Constants.OTHERS.getValue()))
			ticketBO.setString("payeeType", Constants.CUSTOMER.getValue());	
			else
			ticketBO.setString("payeeType", draftTicketData.getString("payee_type").toUpperCase());
			ticketBO.setString("refundMode", Constants.NEFT.getValue());
			//ticketBO.setString("refundMode", draftTicketData.getString("refund_mode").toUpperCase());
			if (ticketBO.getString("refundMode").equalsIgnoreCase(Constants.NEFT.getValue()) || ticketBO.getString("refundMode").equalsIgnoreCase(Constants.Cheque.getValue())) {
				try {
				DataObject bankDetailsBG = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/bankdetailsbg",
								"BankDetailsBG");

				DataObject bankDetailsData = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/bankdetails",
								"BankDetails");
				
				bankDetailsData.setString("parameter1", ticketBO.getString("refundMode"));
				bankDetailsBG.setDataObject("BankDetails", bankDetailsData);
				DataObject response = (DataObject) this
						.locateService_DBInterfacePartner().invoke(
								"retrieveallBankDetailsBG", bankDetailsBG);
				String bankName = response
						.getString("retrieveallBankDetailsBGOutput/BankDetails[0]/bank_name");
				
				ticketBO.setString("bank", bankName);
				}
				catch (ServiceBusinessException sbe) {

					exceptionData = (DataObject) sbe.getData();
					rasException.setString("status", "FAILED");
					rasException.setString("error[0]/errorCode", "100002");
					rasException.setString("error[0]/message",
							exceptionData.get("message").toString()+" error while fetching bank from refund mode");
					throw new ServiceBusinessException(rasException);

				} catch (ServiceRuntimeException sre) {
					throw new ServiceRuntimeException(sre.getMessage()+" error while fetching bank from refund mode", sre);
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage()+" error while fetching bank from refund mode", e);
				}
			}
			ticketBO.setString("applicationNo", draftTicketData.getString("receipt_number"));
			if("Y".equalsIgnoreCase(draftTicketData.getString("is_main_receipt")))
			ticketBO.setString("subReceipt", "N");
			ticketBO.setString("rasDraftTicketNo", draftTicketData.getString("draft_ticket_no"));
			ticketBO.setString("bopsChannel", draftTicketData.getString("bops_channel"));
			ticketBO.setString("sourceUniqueId", draftTicketData.getString("source_unique_id"));
			ticketBO.setString("sourceUserName", draftTicketData.getString("source_username"));
			ticketBO.setString("raisedBy",draftTicketData.getString("created_by"));
			ticketBO.setString("actionBy",draftTicketData.getString("created_by"));
			ticketBO.setString("source", draftTicketData.getString("source"));
			ticketBO.setString("omniDocumentsURL", draftTicketData.getString("document_url"));
			if(draftTicketData.getString("document_url")!=null && !"".equalsIgnoreCase(draftTicketData.getString("document_url")))
			ticketBO.setBoolean("uploadedTo",true);
			ticketBO.setString("receiptDetail[0]/authCode", draftTicketData.getString("auth_code"));
			ticketBO.setString("accountDetail/customerBank", draftTicketData.getString("beneficiary_bank"));
			ticketBO.setString("accountDetail/customerIfscCode", draftTicketData.getString("beneficiary_ifsc_code").toUpperCase());
			ticketBO.setString("accountDetail/bankAccountNo", draftTicketData.getString("beneficiary_account_number"));
			ticketBO.setString("accountDetail/addressLine1", draftTicketData.getString("address_line1"));
			ticketBO.setString("accountDetail/addressLine2", draftTicketData.getString("address_line2"));
			ticketBO.setString("accountDetail/addressLine3", draftTicketData.getString("address_line3"));
			ticketBO.setString("accountDetail/state", draftTicketData.getString("state"));
			ticketBO.setString("accountDetail/city", draftTicketData.getString("city"));
			ticketBO.setString("accountDetail/area", draftTicketData.getString("area"));
			ticketBO.setString("accountDetail/pincode", draftTicketData.getString("pincode"));
			ticketBO.setString("accountDetail/emailId",  draftTicketData.getString("email_id"));
			ticketBO.setString("accountDetail/contactNo",  draftTicketData.getString("contact_no"));
			ticketBO.setString("accountDetail/createdBy",  draftTicketData.getString("created_by"));
		}

		catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();
			rasException.setString("status", "FAILED");
			rasException.setString("error[0]/errorCode", "100002");
			rasException.setString("error[0]/message",
					exceptionData.get("message").toString()+" error while fetching excess refund draft ticket details from draft ticket no");
			throw new ServiceBusinessException(rasException);

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage()+" error while fetching excess refund draft ticket details from draft ticket no", sre);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage()+" error while fetching excess refund draft ticket details from draft ticket no", e);
		}
		return ticketBO;
	}

	/**
	 * Method generated to support implementation of operation "findExcessRefundDraftDocumentDetails" defined for WSDL port type 
	 * named "RASIntegrationService".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a parameter 
	 * type conveys that it is a complex type. Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public DataObject findExcessRefundDraftDocumentDetails(String lob,
			String refundType, String draftTicketNo) {
		//TODO Needs to be implemented.
		DataObject draftTicketResponseBO = null;
		List draftTicketDataList =null;
		DataObject findDocsBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/documentmatrixbg",
						"DocumentMatrixBG");
		DataObject findDocs = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/documentmatrix",
						"DocumentMatrix");
		DataObject docList = getBOFactory().create("http://RACASBO",
				"DocumentList");
		DataObject rasException =  getBOFactory().create("http://RACASBO", "RASException");
		DataObject exceptionData = null;
		
		DataObject draftTicketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftticketbg",
						"DraftTicketBG");

		DataObject draftTicketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftticket",
						"DraftTicket");
		List<String> mandateDocTypeList = new ArrayList();
		Double receiptRefundAmount=0.0;
		String omniDocURL="",ntid="";

	    if (lob == null || "".equalsIgnoreCase(lob)) {
			throw new RuntimeException("LOB cannot be blank or empty");
		}
		if (refundType == null || "".equalsIgnoreCase(refundType)) {
			throw new RuntimeException("refundType cannot be blank or empty");
		}
		if (draftTicketNo != null && !("".equalsIgnoreCase(draftTicketNo.trim()))) {
			draftTicketData.setString("parameter1", draftTicketNo);
			draftTicketDataBG.setDataObject("DraftTicket", draftTicketData);
			draftTicketResponseBO = (DataObject) locateService_DBInterfacePartner()
					.invoke("retrieveallDraftTicketBG", draftTicketDataBG);
			draftTicketDataList =draftTicketResponseBO
					.getList("retrieveallDraftTicketBGOutput/DraftTicket");
			draftTicketData = (DataObject) draftTicketDataList.get(0);
			String[] docTypeList= draftTicketData.getString("document_type").split(",");
			String[] isAvailableList = draftTicketData.getString("is_available").split(",");
			receiptRefundAmount = draftTicketData.getDouble("receipt_refund_amount");
			omniDocURL = draftTicketData.getString("document_url");
			ntid = draftTicketData.getString("created_by");
			for(int i=0;i<isAvailableList.length;i++)
			{
				if("Y".equalsIgnoreCase(isAvailableList[i]))
				mandateDocTypeList.add(docTypeList[i]);
					
			}
		}
		
		findDocs.setString("parameter1", lob);
		findDocs.setString("parameter2", refundType);
		findDocs.setDouble("parameter3", 0);
	//	findDocs.setString("parameter4", applicationNo);
		findDocsBG.setDataObject("DocumentMatrix", findDocs);
		DataObject responseBO = null;
		
		try {
			responseBO = (DataObject) locateService_DBInterfacePartner()
					.invoke("retrieveallDocumentMatrixBG", findDocsBG);
			int size = responseBO.getList(
					"retrieveallDocumentMatrixBGOutput/DocumentMatrix").size();
			List documentsList = new ArrayList();
			for (int i = 1; i <= size; i++) {
				DataObject docs = getBOFactory().create("http://RACASBO",
						"Document");
				String xpathString = "retrieveallDocumentMatrixBGOutput/DocumentMatrix["
						+ i + "]";

				docs.setString("docType",
						responseBO.getString(xpathString + "/document_type"));
				
				if(responseBO.getString(xpathString + "/document_type").equalsIgnoreCase(Constants.PANCARD.getValue()) || responseBO.getString(xpathString + "/document_type").equalsIgnoreCase(Constants.ADDRESSPROOF.getValue()))
				{
				if(receiptRefundAmount>=100000)
				docs.setString("isMandatory","Y");
				else
				docs.setString("isMandatory",responseBO.getString(xpathString + "/is_mandatory"));
				}
				else
				docs.setString("isMandatory",responseBO.getString(xpathString + "/is_mandatory"));
				
				if (responseBO.get(xpathString + "/docid") != null) {
					docs.setDouble("docId",
							responseBO.getDouble(xpathString + "/docid"));
				}
				if (responseBO.get(xpathString + "/ticket_id") != null) {
					docs.setDouble("ticketId",
							responseBO.getDouble(xpathString + "/ticket_id"));
				}
				
					
				if(mandateDocTypeList!=null && !mandateDocTypeList.isEmpty() && mandateDocTypeList.contains(responseBO.getString(xpathString + "/document_type")))
				{
						docs.setBoolean("isSubmitted",
								true);
						//Documents through OMNI URL
						docs.setInt("dmsFlag",
								2);
						if(omniDocURL!=null && !"".equalsIgnoreCase(omniDocURL))
						docs.setString("dmsRemarks",
								omniDocURL);
						docs.setString("dmsUploadedBy",
								ntid);
				}
				else
				{
					docs.setBoolean("isSubmitted",false);
					
					docs.setInt("dmsFlag", 0);
				
				}
				
				docs.setString("docName",
						responseBO.getString(xpathString + "/name"));
				docs.setString("docPath",
						responseBO.getString(xpathString + "/path"));
				
				documentsList.add(docs);

			}
			docList.setList("document", documentsList);
			return docList;

		}
		
		catch (ServiceBusinessException sbe) {
			
			exceptionData  = (DataObject)sbe.getData();
			rasException.setString("status", "FAILED");
			rasException.setString("error[0]/errorCode", "100002");
			rasException.setString("error[0]/message", exceptionData.get("message").toString()+" error while fetching document master from refund type and lob");
			throw new ServiceBusinessException(rasException);
			
			 }
		catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage()+" error while fetching document master from refund type and lob",sre);
			 }
		catch (Exception e) {
			throw new RuntimeException(e.getMessage()+" error while fetching document master from refund type and lob",e);
		     }
	}

	/**
	 * Method generated to support implementation of operation "saveExcessRefundReverseFeedDetails" defined for WSDL port type 
	 * named "RASIntegrationService".
	 * 
	 * Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public Integer saveExcessRefundReverseFeedDetails(String draftTicketNo,
			String rasTicketNo, String requestType, String source) {
		//TODO Needs to be implemented.
		System.out.println("Update PACE Status start access layer 10 --> "+rasTicketNo);
		int reverseFeedId = incrementSequence("EXCESS REFUND REVERSE FEED SEQUENCE");
		DataObject rasException =  getBOFactory().create("http://RACASBO", "RASException");
		DataObject exceptionData = null;
		      try{
				
				System.out.println("Reverse Feed Id "+reverseFeedId);
				DataObject draftTicketDataBG = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftticketbg",
								"DraftTicketBG");
				DataObject draftTicketData = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftticket",
								"DraftTicket");
			
				draftTicketData.setString("parameter1", draftTicketNo);
				draftTicketDataBG.setDataObject("DraftTicket", draftTicketData);
				DataObject response = (DataObject) this
						.locateService_DBInterfacePartner().invoke(
								"retrieveallDraftTicketBG", draftTicketDataBG);
				System.out.println("Update PACE Status start access layer 11 --> "+rasTicketNo);
				int draftId = response
						.getInt("retrieveallDraftTicketBGOutput/DraftTicket[0]/ras_excess_refund_draft_id");
				String ntid = response
						.getString("retrieveallDraftTicketBGOutput/DraftTicket[0]/created_by");
				
				DataObject reverseFeedDataBG = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_excess_refund_reverse_feedbg",
								"DboRas_Excess_Refund_Reverse_FeedBG");
				DataObject reverseFeedData = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_excess_refund_reverse_feed",
								"DboRas_Excess_Refund_Reverse_Feed");
				reverseFeedData.setInt("ras_excess_refund_reverse_feed_id", reverseFeedId);
				reverseFeedData.setInt("ras_excess_refund_draft_id", draftId);
				reverseFeedData.setString("draft_ticket_no", draftTicketNo);
				reverseFeedData.setString("ras_ticket_no", rasTicketNo);
				reverseFeedData.setString("request_type",requestType);
				reverseFeedData.setString("source",source);
				reverseFeedData.setDate("created_on", formatDate(new Date()));
				reverseFeedData.setString("created_by",ntid);
				reverseFeedData.setDate("requested_date_time",formatDate(new Date()));
				reverseFeedDataBG.set("DboRas_Excess_Refund_Reverse_Feed", reverseFeedData);
				System.out.println("Update PACE Status start access layer 12 --> "+rasTicketNo);
				this
						.locateService_DBInterfacePartner().invoke(
								"createDboRas_Excess_Refund_Reverse_FeedBG", reverseFeedDataBG);
				if(rasTicketNo!=null && !"".equalsIgnoreCase(rasTicketNo))
				{
				DataObject updateDraftBG = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateexcessrefunddraftbg",
								"UpdateExcessRefundDraftBG");
				DataObject updateDraftInput = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateexcessrefunddraft",
								"UpdateExcessRefundDraft");
				
				updateDraftInput.setString("statement1parameter1", rasTicketNo);
				
				updateDraftInput
						.setDate("statement1parameter2", formatDate(new Date()));
				
				updateDraftInput
				.setString("statement1parameter3",ntid);
			
				updateDraftInput
				.setString("statement1parameter4",draftTicketNo);

				updateDraftBG.setDataObject(
						"UpdateExcessRefundDraft",
						updateDraftInput);
				System.out.println("Update PACE Status start access layer 13 --> "+rasTicketNo);
				this.locateService_DBInterfacePartner().invoke(
						"executeUpdateExcessRefundDraftBG", updateDraftBG);
				System.out.println("Update PACE Status start access layer 14 --> "+rasTicketNo);
				}
				return reverseFeedId;
		      }
				catch (ServiceBusinessException sbe) {
					
					exceptionData  = (DataObject)sbe.getData();
					rasException.setString("status", "FAILED");
					rasException.setString("error[0]/errorCode", "100003");
					rasException.setString("error[0]/message", exceptionData.get("message").toString()+" error while saving excess refund reverse feed details and updating ras ticket no in draft ticket");
					throw new ServiceBusinessException(rasException);
					
					 }
				catch (ServiceRuntimeException sre) {
					throw new ServiceRuntimeException(sre.getMessage()+" error while saving excess refund reverse feed details and updating ras ticket no in draft ticket",sre);
					 }
				catch (Exception e) {
					throw new RuntimeException(e.getMessage()+" error while saving excess refund reverse feed details and updating ras ticket no in draft ticket",e);
				     }
	}

	/**
	 * Method generated to support implementation of operation "saveErrorDetails" defined for WSDL port type 
	 * named "RASIntegrationService".
	 * 
	 * Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public void saveErrorDetails(String errorCode, String errorDetail,
			String gcError, String draftTicketNo, String sourceUserName) {
		//TODO Needs to be implemented.
		DataObject exceptionData = null;
		DataObject draftTicketResponseBO = null;
		try
		{
		
		DataObject updateDraftBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updatedrafterrordetailsbg",
						"UpdateDraftErrorDetailsBG");

		DataObject updateDraftInput = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updatedrafterrordetails",
						"UpdateDraftErrorDetails");
		DataObject draftTicketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftticketbg",
						"DraftTicketBG");

		DataObject draftTicketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftticket",
						"DraftTicket");
		if (draftTicketNo != null && !("".equalsIgnoreCase(draftTicketNo.trim()))) {
			draftTicketData.setString("parameter1", draftTicketNo);

		} else {
			throw new RuntimeException("Draft Ticket Number cannot be blank or empty");
		}
		draftTicketDataBG.setDataObject("DraftTicket", draftTicketData);
		// invoke service

		
			
			draftTicketResponseBO= (DataObject) this.locateService_DBInterfacePartner()
					.invoke("retrieveallDraftTicketBG", draftTicketDataBG);
			
			sourceUserName = draftTicketResponseBO.getString("retrieveallDraftTicketBGOutput/DraftTicket[0]/created_by");

			updateDraftInput.setString("statement1parameter7", draftTicketNo);
			updateDraftInput.setString("statement1parameter1", errorCode);
			updateDraftInput.setString("statement1parameter2", errorDetail);
			updateDraftInput.setString("statement1parameter3", gcError);
			updateDraftInput.setString("statement1parameter4", "FAILED");
			updateDraftInput.setDate("statement1parameter5", formatDate(new Date()));
			updateDraftInput.setString("statement1parameter6", sourceUserName);
		
		updateDraftBG.setDataObject(
				"UpdateDraftErrorDetails",
				updateDraftInput);
		this.locateService_DBInterfacePartner().invoke(
				"executeUpdateDraftErrorDetailsBG", updateDraftBG);
	}
		catch (ServiceBusinessException sbe) {
			exceptionData  = (DataObject)sbe.getData();
			throw new ServiceBusinessException(exceptionData.get("message").toString());
			
			 }
		catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage(),sre);
			 }
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		     }
	}

	/**
	 * Method generated to support implementation of operation "updateExcessRefundReverseFeedDetails" defined for WSDL port type 
	 * named "RASIntegrationService".
	 * 
	 * Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public String updateExcessRefundReverseFeedDetails(Integer reverseFeedId,DataObject reverseFeedInput,DataObject reverseFeedOutput) {
		//TODO Needs to be implemented.
		System.out.println("Update PACE Status start access layer 15 --> "+reverseFeedId);
		System.out.println("Update PACE Status start access layer 15 --> Printing arguments  : "+reverseFeedInput);
		System.out.println(reverseFeedOutput==null);
		/*if(reverseFeedOutput!=null) {
			System.out.println(reverseFeedOutput.getString(0));
			System.out.println(reverseFeedOutput.getString(1));
			System.out.println(reverseFeedOutput.getString(2));
		}*/
		//System.out.println("Update PACE Status start access layer 15 --> "+reverseFeedOutput.toString());
		DataObject rasException =  getBOFactory().create("http://RACASBO", "RASException");
		DataObject exceptionData = null;
		try
		{
		DataObject reverseFeedDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/reversefeedbg",
						"ReverseFeedBG");
		DataObject reverseFeedData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/reversefeed",
						"ReverseFeed");
		reverseFeedData.setInt("parameter1", reverseFeedId);
		reverseFeedDataBG.setDataObject("ReverseFeed", reverseFeedData);
		DataObject reverseFeedResponse = (DataObject) this
				.locateService_DBInterfacePartner().invoke(
						"retrieveallReverseFeedBG", reverseFeedDataBG);
		int draftId = reverseFeedResponse
				.getInt("retrieveallReverseFeedBGOutput/ReverseFeed[0]/ras_excess_refund_draft_id");
		
		System.out.println("Update PACE Status start access layer 16 --> ");
		DataObject draftTicketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftticketidbg",
						"DraftTicketIdBG");
		DataObject draftTicketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/draftticketid",
						"DraftTicketId");
		DataObject updateDraftBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateexcessrefunddraftidbg",
						"UpdateExcessRefundDraftIdBG");
		DataObject updateDraftInput = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateexcessrefunddraftid",
						"UpdateExcessRefundDraftId");

		System.out.println("Update PACE Status start access layer 17 --> ");
		updateDraftInput.setInt("statement1parameter6", draftId);
		draftTicketData.setInt("parameter1", draftId);
		draftTicketDataBG.setDataObject("DraftTicketId", draftTicketData);
		DataObject draftTicketresponse = (DataObject) this
				.locateService_DBInterfacePartner().invoke(
						"retrieveallDraftTicketIdBG", draftTicketDataBG);
		String draftTicketNo = draftTicketresponse
				.getString("retrieveallDraftTicketIdBGOutput/DraftTicketId[0]/draft_ticket_no");
		String rasTicketNo = draftTicketresponse
				.getString("retrieveallDraftTicketIdBGOutput/DraftTicketId[0]/ras_ticket_no");
		String receiptNo = draftTicketresponse
				.getString("retrieveallDraftTicketIdBGOutput/DraftTicketId[0]/receipt_number");
		String errorCode = draftTicketresponse
				.getString("retrieveallDraftTicketIdBGOutput/DraftTicketId[0]/error_code");
		String errorDetail = draftTicketresponse
				.getString("retrieveallDraftTicketIdBGOutput/DraftTicketId[0]/error_details");
		String gcError = draftTicketresponse
				.getString("retrieveallDraftTicketIdBGOutput/DraftTicketId[0]/gc_error");
		String status = draftTicketresponse
				.getString("retrieveallDraftTicketIdBGOutput/DraftTicketId[0]/status");
		String ntid = draftTicketresponse
				.getString("retrieveallDraftTicketIdBGOutput/DraftTicketId[0]/created_by");
		String source = draftTicketresponse
				.getString("retrieveallDraftTicketIdBGOutput/DraftTicketId[0]/source");
		DataObject updateReverseFeedDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateexcessrefundreversefeedbg",
						"UpdateExcessRefundReverseFeedBG");

		DataObject updateReverseFeedInput = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateexcessrefundreversefeed",
						"UpdateExcessRefundReverseFeed");

		
		System.out.println("Update PACE Status start access layer 18 --> ");
		if(reverseFeedInput!=null)
		{
		BOXMLSerializer requestSerializer = (BOXMLSerializer) ServiceManager.INSTANCE
					.locateService("com/ibm/websphere/bo/BOXMLSerializer");
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage soapMsg = factory.createMessage();
		SOAPPart part = soapMsg.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		SOAPHeader header = envelope.getHeader();
		SOAPBody body = envelope.getBody();
		String rootElementName = reverseFeedInput.getType().getName();
		String targetNamespace = reverseFeedInput.getType().getURI();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		requestSerializer.writeDataObject(reverseFeedInput, targetNamespace,
				"UpdateReceiptDetails", baos);
		String reverseFeedInputXml = new String(baos.toString());
		System.out.println("reverseFeedInput XML " + reverseFeedInputXml);
	
			
			SOAPBodyElement element = body.addBodyElement(envelope.createName(
					"refundDetails", "nbhd",
					reverseFeedInputXml));
			ByteArrayOutputStream requestStream = new ByteArrayOutputStream();
			soapMsg.writeTo(requestStream);
			String soapreverseFeedInputXmlRequest = new String(requestStream.toByteArray(),
					"utf-8");
			System.out.println("reverseFeedInput SOAP Request created");
			//reverseFeedDataValue.setDate("requested_date_time", formatDate(new Date()));
			updateReverseFeedInput.setString("statement1parameter1", soapreverseFeedInputXmlRequest);
		}
		if(reverseFeedOutput!=null)
		{
			System.out.println("Update PACE Status start access layer 19 --> ");
		BOXMLSerializer responeSerializer = (BOXMLSerializer) ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOXMLSerializer");
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage soapMsg = factory.createMessage();
		SOAPPart part = soapMsg.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		System.out.println("Update PACE Status start access layer 20 --> ");
		SOAPHeader header = envelope.getHeader();
		SOAPBody body = envelope.getBody();
		String rootElementName = reverseFeedOutput.getType().getName();
		String targetNamespace = reverseFeedOutput.getType().getURI();
		ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
		System.out.println("Update PACE Status start access layer 21 --> ");
		responeSerializer.writeDataObject(reverseFeedOutput, targetNamespace,
				"UpdateReceiptDetailsResponse", baos1);
		System.out.println("Update PACE Status start access layer 22 --> ");
		String reverseFeedOutputXml = new String(baos1.toString());
		System.out.println("reverseFeedOutput XML " + reverseFeedOutputXml);
	
			
			SOAPBodyElement element = body.addBodyElement(envelope.createName(
					"response", "io5",
					reverseFeedOutputXml));
			System.out.println("Update PACE Status start access layer 23 --> ");
			ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
			soapMsg.writeTo(responseStream);
			String soapreverseFeedOutputXmlResponse = new String(responseStream.toByteArray(),
					"utf-8");
			System.out.println("Update PACE Status start access layer 24 --> ");
			System.out.println("reverseFeedOutput SOAP Response created");
			updateReverseFeedInput.setDate("statement1parameter4", formatDate(new Date()));
			System.out.println("Update PACE Status start access layer 25 --> ");
			updateReverseFeedInput.setString("statement1parameter2", soapreverseFeedOutputXmlResponse);
			System.out.println("Update PACE Status start access layer 26 --> ");
		}
		updateReverseFeedInput.setInt("statement1parameter7", reverseFeedId);
		System.out.println("Update PACE Status start access layer 27 --> ");
		//if("200".equalsIgnoreCase(reverseFeedOutput.getString("responseCode")))
		//{
			System.out.println("Update PACE Status start access layer 28 --> ");
			updateReverseFeedInput.setString("statement1parameter3", "YES");
			updateDraftInput
		.setString("statement1parameter3", "SUCCESS");
		//}
		/*else
		{
			System.out.println("Update PACE Status start access layer 29 --> ");
			updateReverseFeedInput.setString("statement1parameter3", "NO");	
			updateDraftInput
		.setString("statement1parameter2", reverseFeedOutput.getString("responseDescription")+" error from PACE Integration service");
			updateDraftInput
		.setString("statement1parameter1", "100009");
			updateDraftInput
		.setString("statement1parameter3", "FAILED");
		} */
		
		updateDraftInput
				.setDate("statement1parameter4", formatDate(new Date()));
		
		updateDraftInput
		.setString("statement1parameter5",ntid);

		updateDraftBG.setDataObject(
				"UpdateExcessRefundDraftId",
				updateDraftInput);
		this.locateService_DBInterfacePartner().invoke(
				"executeUpdateExcessRefundDraftIdBG", updateDraftBG);
		
		updateReverseFeedInput.setDate("statement1parameter5", formatDate(new Date()));
		updateReverseFeedInput.setString("statement1parameter6", ntid);
	
		
		updateReverseFeedDataBG.setDataObject(
				"UpdateExcessRefundReverseFeed",
				updateReverseFeedInput);
		this.locateService_DBInterfacePartner().invoke(
				"executeUpdateExcessRefundReverseFeedBG", updateReverseFeedDataBG);
		//return reverseFeedOutput.getString("responseDescription");
		System.out.println("SUCCESS in reverse feed");
		return "SUCCESS";
		}
		catch (ServiceBusinessException sbe) {
			
			exceptionData  = (DataObject)sbe.getData();
			rasException.setString("status", "FAILED");
			rasException.setString("error[0]/errorCode", "100004");
			rasException.setString("error[0]/message", exceptionData.get("message").toString()+" error while updating excess refund reverse feed details");
			throw new ServiceBusinessException(rasException);
			
			 }
		catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage()+" error while updating excess refund reverse feed details",sre);
			 }
		catch (Exception e) {
			throw new RuntimeException(e.getMessage()+" error while updating excess refund reverse feed details",e);
		     }
	
	
	}

	/**
	 * Method generated to support implementation of operation "updateRASTicketStatusToSource" defined for WSDL port type 
	 * named "RASIntegrationService".
	 * 
	 * Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public String updateRASTicketStatusToSource(String rasTicketNo,
			String draftTicketNo, String source, String oldStage,
			String oldStatus, String newStage, String newStatus) {
		//TODO Needs to be implemented.
		System.out.println("Update PACE Status start access layer 7 --> "+rasTicketNo);
		DataObject exceptionData = null;
		DataObject rasException = getBOFactory().create("http://RACASBO",
				"RASException");
		String updateStatus = "N";
		try
		{
		DataObject reverseFeedStatusDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/reversefeedstatusbg",
						"ReverseFeedStatusBG");

		DataObject reverseFeedStatusData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/reversefeedstatus",
						"ReverseFeedStatus");
		reverseFeedStatusData.setString("parameter1", source);
		reverseFeedStatusData.setString("parameter2", oldStatus);
		reverseFeedStatusDataBG.setDataObject("ReverseFeedStatus", reverseFeedStatusData);
		DataObject oldSourceStatusResponse = (DataObject) this
				.locateService_DBInterfacePartner().invoke(
						"retrieveallReverseFeedStatusBG", reverseFeedStatusDataBG);
		String oldSourceStatus = oldSourceStatusResponse
				.getString("retrieveallReverseFeedStatusBGOutput/ReverseFeedStatus[0]/source_status");
		System.out.println("Update PACE Status start access layer 8 --> "+rasTicketNo);
		reverseFeedStatusData.setString("parameter1", source);
		reverseFeedStatusData.setString("parameter2", newStatus);
		reverseFeedStatusDataBG.setDataObject("ReverseFeedStatus", reverseFeedStatusData);
		DataObject newSourceStatusResponse = (DataObject) this
				.locateService_DBInterfacePartner().invoke(
						"retrieveallReverseFeedStatusBG", reverseFeedStatusDataBG);
		String newSourceStatus = newSourceStatusResponse
				.getString("retrieveallReverseFeedStatusBGOutput/ReverseFeedStatus[0]/source_status");
		System.out.println("Update PACE Status start access layer 9 --> "+rasTicketNo);
		if(!oldSourceStatus.equalsIgnoreCase(newSourceStatus))
		{
			updateStatus = newSourceStatus;
		}
		else
		{
			updateStatus = "N";
		}
		return updateStatus;
		}
		catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();
			rasException.setString("status", "FAILED");
			rasException.setString("error[0]/errorCode", "100002");
			rasException.setString("error[0]/message",
					exceptionData.get("message").toString()+" error while fetching source status from source and ras status");
			throw new ServiceBusinessException(rasException);

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage()+" error while fetching source status from source and ras status", sre);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage()+" error while fetching source status from source and ras status", e);
		}
	}

	
	/**
	 * Method generated to support implementation of operation "updateExcessRefundDraftGCTransactionCount" defined for WSDL port type 
	 * named "RASIntegrationService".
	 * 
	 * Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public Boolean updateExcessRefundDraftGCTransactionCount(String draftTicketNo,
			Integer gcTransactionCount) {
		//TODO Needs to be implemented.
		
		DataObject exceptionData = null;
		DataObject draftTicketResponseBO = null;
		boolean updatePACE = false;
		try
		{
			
			DataObject appConfigBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/appconfigbg",
							"AppConfigBG");
			DataObject appConfig = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/appconfig",
							"AppConfig");
			
			appConfig.setString("parameter1", "FAILED GC TRANSACTION COUNT");
			appConfigBG.setDataObject("AppConfig", appConfig);
			
			DataObject series = (DataObject) locateService_DBInterfacePartner().invoke(
					"retrieveallAppConfigBG", appConfigBG);
			 
			Integer failedGCTransactionCount = Integer
					.parseInt(series
							.getString("retrieveallAppConfigBGOutput/AppConfig[0]/value"));
		
			
		DataObject updateDraftBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updatetransactioncountbg",
						"UpdateTransactionCountBG");

		DataObject updateDraftInput = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updatetransactioncount",
						"UpdateTransactionCount");

		
		if (draftTicketNo != null && !("".equalsIgnoreCase(draftTicketNo.trim()))) {
			updateDraftInput.setString("statement1parameter3", draftTicketNo);
			updateDraftInput.setInt("statement1parameter1", gcTransactionCount+1);
			
			updateDraftInput
					.setDate("statement1parameter2", formatDate(new Date()));
			
			
			updateDraftBG.setDataObject(
					"UpdateTransactionCount",
					updateDraftInput);
			this.locateService_DBInterfacePartner().invoke(
					"executeUpdateTransactionCountBG", updateDraftBG);
			
			if(gcTransactionCount+1 == failedGCTransactionCount)
			updatePACE = true;
		
		}
		else {
			throw new RuntimeException("Draft Ticket Number cannot be blank or empty");
		}
		
		}
		catch (ServiceBusinessException sbe) {
			exceptionData  = (DataObject)sbe.getData();
			throw new ServiceBusinessException(exceptionData.get("message").toString());
			
			 }
		catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage(),sre);
			 }
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		     }	
return updatePACE;
	}
	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_TicketBG(DataObject createDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_TicketBG(DataObject updateDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_TicketBG(DataObject updateallDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_TicketBG(DataObject retrieveDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_TicketBG(DataObject retrieveallDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_TicketBG(DataObject applychangesDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_TicketBG(DataObject existsDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_TicketBG(DataObject upsertDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_TicketBG(DataObject batchcreateDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_TicketBG(DataObject batchupdateDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Policy_DetailBG(DataObject createDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Policy_DetailBG(DataObject updateDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Policy_DetailBG(DataObject updateallDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Policy_DetailBG(DataObject retrieveDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Policy_DetailBG(DataObject retrieveallDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Policy_DetailBG(DataObject applychangesDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Policy_DetailBG(DataObject existsDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Policy_DetailBG(DataObject upsertDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Policy_DetailBG(DataObject batchcreateDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Policy_DetailBG(DataObject batchupdateDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_RefundBG(DataObject createDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_RefundBG(DataObject updateDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_RefundBG(DataObject updateallDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_RefundBG(DataObject retrieveDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_RefundBG(DataObject retrieveallDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_RefundBG(DataObject applychangesDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_RefundBG(DataObject existsDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_RefundBG(DataObject upsertDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_RefundBG(DataObject batchcreateDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_RefundBG(DataObject batchupdateDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Receipt_DetailBG(DataObject createDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Receipt_DetailBG(DataObject updateDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Receipt_DetailBG(DataObject updateallDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Receipt_DetailBG(DataObject retrieveDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Receipt_DetailBG(DataObject retrieveallDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Receipt_DetailBG(DataObject applychangesDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Receipt_DetailBG(DataObject existsDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Receipt_DetailBG(DataObject upsertDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Receipt_DetailBG(DataObject batchcreateDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Receipt_DetailBG(DataObject batchupdateDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_InsuredBG(DataObject createDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_InsuredBG(DataObject updateDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_InsuredBG(DataObject updateallDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_InsuredBG(DataObject retrieveDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_InsuredBG(DataObject retrieveallDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_InsuredBG(DataObject applychangesDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_InsuredBG(DataObject existsDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_InsuredBG(DataObject upsertDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_InsuredBG(DataObject batchcreateDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_InsuredBG(DataObject batchupdateDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Account_Detail_HistBG(DataObject createDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Account_Detail_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Account_Detail_HistBG(DataObject updateDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Account_Detail_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Account_Detail_HistBG(DataObject updateallDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Account_Detail_HistBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Account_Detail_HistBG(DataObject retrieveDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Account_Detail_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Account_Detail_HistBG(DataObject retrieveallDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Account_Detail_HistBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Account_Detail_HistBG(DataObject applychangesDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Account_Detail_HistBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Account_Detail_HistBG(DataObject existsDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Account_Detail_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Account_Detail_HistBG(DataObject upsertDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Account_Detail_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Account_Detail_HistBG(DataObject batchcreateDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Account_Detail_HistBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Account_Detail_HistBG(DataObject batchupdateDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Account_Detail_HistBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_App_ConfigBG(DataObject createDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_App_ConfigBG(DataObject updateDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_App_ConfigBG(DataObject updateallDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_App_ConfigBG(DataObject retrieveDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_App_ConfigBG(DataObject retrieveallDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_App_ConfigBG(DataObject applychangesDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_App_ConfigBG(DataObject existsDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_App_ConfigBG(DataObject upsertDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_App_ConfigBG(DataObject batchcreateDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_App_ConfigBG(DataObject batchupdateDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_ApproverBG(DataObject createDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_ApproverBG(DataObject updateDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_ApproverBG(DataObject updateallDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_ApproverBG(DataObject retrieveDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_ApproverBG(DataObject retrieveallDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_ApproverBG(DataObject applychangesDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_ApproverBG(DataObject existsDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_ApproverBG(DataObject upsertDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_ApproverBG(DataObject batchcreateDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_ApproverBG(DataObject batchupdateDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Cancellation_OptionBG(DataObject createDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Cancellation_OptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Cancellation_OptionBG(DataObject updateDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Cancellation_OptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Cancellation_OptionBG(DataObject updateallDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Cancellation_OptionBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Cancellation_OptionBG(DataObject retrieveDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Cancellation_OptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Cancellation_OptionBG(DataObject retrieveallDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Cancellation_OptionBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Cancellation_OptionBG(DataObject applychangesDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Cancellation_OptionBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Cancellation_OptionBG(DataObject existsDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Cancellation_OptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Cancellation_OptionBG(DataObject upsertDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Cancellation_OptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Cancellation_OptionBG(DataObject batchcreateDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Cancellation_OptionBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Cancellation_OptionBG(DataObject batchupdateDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Cancellation_OptionBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Cancellation_ReasonBG(DataObject createDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Cancellation_ReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Cancellation_ReasonBG(DataObject updateDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Cancellation_ReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Cancellation_ReasonBG(DataObject updateallDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Cancellation_ReasonBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Cancellation_ReasonBG(DataObject retrieveDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Cancellation_ReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Cancellation_ReasonBG(DataObject retrieveallDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Cancellation_ReasonBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Cancellation_ReasonBG(DataObject applychangesDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Cancellation_ReasonBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Cancellation_ReasonBG(DataObject existsDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Cancellation_ReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Cancellation_ReasonBG(DataObject upsertDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Cancellation_ReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Cancellation_ReasonBG(DataObject batchcreateDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Cancellation_ReasonBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Cancellation_ReasonBG(DataObject batchupdateDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Cancellation_ReasonBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Document_MasterBG(DataObject createDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Document_MasterBG(DataObject updateDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Document_MasterBG(DataObject updateallDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Document_MasterBG(DataObject retrieveDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Document_MasterBG(DataObject retrieveallDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Document_MasterBG(DataObject applychangesDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Document_MasterBG(DataObject existsDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Document_MasterBG(DataObject upsertDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Document_MasterBG(DataObject batchcreateDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Document_MasterBG(DataObject batchupdateDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Exception_HistBG(DataObject createDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Exception_HistBG(DataObject updateDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Exception_HistBG(DataObject updateallDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Exception_HistBG(DataObject retrieveDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Exception_HistBG(DataObject retrieveallDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Exception_HistBG(DataObject applychangesDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Exception_HistBG(DataObject existsDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Exception_HistBG(DataObject upsertDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Exception_HistBG(DataObject batchcreateDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Exception_HistBG(DataObject batchupdateDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_JobsBG(DataObject createDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_JobsBG(DataObject updateDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_JobsBG(DataObject updateallDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_JobsBG(DataObject retrieveDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_JobsBG(DataObject retrieveallDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_JobsBG(DataObject applychangesDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_JobsBG(DataObject existsDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_JobsBG(DataObject upsertDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_JobsBG(DataObject batchcreateDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_JobsBG(DataObject batchupdateDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_LobBG(DataObject createDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_LobBG(DataObject updateDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_LobBG(DataObject updateallDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_LobBG(DataObject retrieveDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_LobBG(DataObject retrieveallDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_LobBG(DataObject applychangesDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_LobBG(DataObject existsDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_LobBG(DataObject upsertDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_LobBG(DataObject batchcreateDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_LobBG(DataObject batchupdateDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_StatusBG(DataObject createDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_StatusBG(DataObject updateDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_StatusBG(DataObject updateallDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_StatusBG(DataObject retrieveDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_StatusBG(DataObject retrieveallDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_StatusBG(DataObject applychangesDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_StatusBG(DataObject existsDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_StatusBG(DataObject upsertDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_StatusBG(DataObject batchcreateDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_StatusBG(DataObject batchupdateDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Ticket_DocumentsBG(DataObject createDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Ticket_DocumentsBG(DataObject updateDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Ticket_DocumentsBG(DataObject updateallDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Ticket_DocumentsBG(DataObject retrieveDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Ticket_DocumentsBG(DataObject retrieveallDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Ticket_DocumentsBG(DataObject applychangesDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Ticket_DocumentsBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Ticket_DocumentsBG(DataObject existsDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Ticket_DocumentsBG(DataObject upsertDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Ticket_DocumentsBG(DataObject batchcreateDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Ticket_DocumentsBG(DataObject batchupdateDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Ticket_HistBG(DataObject createDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Ticket_HistBG(DataObject updateDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Ticket_HistBG(DataObject updateallDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Ticket_HistBG(DataObject retrieveDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Ticket_HistBG(DataObject retrieveallDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Ticket_HistBG(DataObject applychangesDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Ticket_HistBG(DataObject existsDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Ticket_HistBG(DataObject upsertDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Ticket_HistBG(DataObject batchcreateDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Ticket_HistBG(DataObject batchupdateDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDocumentMatrixBG(DataObject retrieveallDocumentMatrixBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDocumentMatrixBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_ProductBG(DataObject createDboRasProductBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_ProductBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_ProductBG(DataObject updateDboRasProductBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_ProductBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_ProductBG(DataObject updateallDboRasProductBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_ProductBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_ProductBG(DataObject retrieveDboRasProductBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_ProductBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_ProductBG(DataObject retrieveallDboRasProductBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_ProductBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_ProductBG(DataObject applychangesDboRasProductBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_ProductBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_ProductBG(DataObject existsDboRasProductBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_ProductBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_ProductBG(DataObject upsertDboRasProductBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_ProductBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_ProductBG(DataObject batchcreateDboRasProductBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_ProductBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_ProductBG(DataObject batchupdateDboRasProductBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_ProductBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Account_DetailBG(DataObject createDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Account_DetailBG(DataObject updateDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Account_DetailBG(DataObject updateallDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Account_DetailBG(DataObject retrieveDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Account_DetailBG(DataObject retrieveallDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Account_DetailBG(DataObject applychangesDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Account_DetailBG(DataObject existsDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Account_DetailBG(DataObject upsertDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Account_DetailBG(DataObject batchcreateDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Account_DetailBG(DataObject batchupdateDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Account_MasterBG(DataObject createDboRasAccountMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Account_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Account_MasterBG(DataObject updateDboRasAccountMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Account_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Account_MasterBG(DataObject updateallDboRasAccountMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Account_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Account_MasterBG(DataObject retrieveDboRasAccountMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Account_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Account_MasterBG(DataObject retrieveallDboRasAccountMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Account_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Account_MasterBG(DataObject applychangesDboRasAccountMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Account_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Account_MasterBG(DataObject existsDboRasAccountMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Account_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Account_MasterBG(DataObject upsertDboRasAccountMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Account_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Account_MasterBG(DataObject batchcreateDboRasAccountMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Account_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Account_MasterBG(DataObject batchupdateDboRasAccountMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Account_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Bulk_File_LogBG(DataObject createDboRasBulkFileLogBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Bulk_File_LogBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Bulk_File_LogBG(DataObject updateDboRasBulkFileLogBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Bulk_File_LogBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Bulk_File_LogBG(DataObject updateallDboRasBulkFileLogBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Bulk_File_LogBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Bulk_File_LogBG(DataObject retrieveDboRasBulkFileLogBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Bulk_File_LogBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Bulk_File_LogBG(DataObject retrieveallDboRasBulkFileLogBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Bulk_File_LogBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Bulk_File_LogBG(DataObject applychangesDboRasBulkFileLogBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Bulk_File_LogBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Bulk_File_LogBG(DataObject existsDboRasBulkFileLogBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Bulk_File_LogBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Bulk_File_LogBG(DataObject upsertDboRasBulkFileLogBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Bulk_File_LogBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Bulk_File_LogBG(DataObject batchcreateDboRasBulkFileLogBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Bulk_File_LogBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Bulk_File_LogBG(DataObject batchupdateDboRasBulkFileLogBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Bulk_File_LogBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Holiday_ScheduleBG(DataObject createDboRasHolidayScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Holiday_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Holiday_ScheduleBG(DataObject updateDboRasHolidayScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Holiday_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Holiday_ScheduleBG(DataObject updateallDboRasHolidayScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Holiday_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Holiday_ScheduleBG(DataObject retrieveDboRasHolidayScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Holiday_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Holiday_ScheduleBG(DataObject retrieveallDboRasHolidayScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Holiday_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Holiday_ScheduleBG(DataObject applychangesDboRasHolidayScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Holiday_ScheduleBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Holiday_ScheduleBG(DataObject existsDboRasHolidayScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Holiday_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Holiday_ScheduleBG(DataObject upsertDboRasHolidayScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Holiday_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Holiday_ScheduleBG(DataObject batchcreateDboRasHolidayScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Holiday_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Holiday_ScheduleBG(DataObject batchupdateDboRasHolidayScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Holiday_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Refund_HistBG(DataObject createDboRasRefundHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Refund_HistBG(DataObject updateDboRasRefundHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Refund_HistBG(DataObject updateallDboRasRefundHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Refund_HistBG(DataObject retrieveDboRasRefundHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Refund_HistBG(DataObject retrieveallDboRasRefundHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Refund_HistBG(DataObject applychangesDboRasRefundHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Refund_HistBG(DataObject existsDboRasRefundHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Refund_HistBG(DataObject upsertDboRasRefundHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Refund_HistBG(DataObject batchcreateDboRasRefundHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Refund_HistBG(DataObject batchupdateDboRasRefundHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Report_AccessBG(DataObject createDboRasReportAccessBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Report_AccessBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Report_AccessBG(DataObject updateDboRasReportAccessBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Report_AccessBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Report_AccessBG(DataObject updateallDboRasReportAccessBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Report_AccessBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Report_AccessBG(DataObject retrieveDboRasReportAccessBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Report_AccessBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Report_AccessBG(DataObject retrieveallDboRasReportAccessBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Report_AccessBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Report_AccessBG(DataObject applychangesDboRasReportAccessBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Report_AccessBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Report_AccessBG(DataObject existsDboRasReportAccessBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Report_AccessBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Report_AccessBG(DataObject upsertDboRasReportAccessBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Report_AccessBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Report_AccessBG(DataObject batchcreateDboRasReportAccessBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Report_AccessBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Report_AccessBG(DataObject batchupdateDboRasReportAccessBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Report_AccessBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Uploaded_FileBG(DataObject createDboRasUploadedFileBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Uploaded_FileBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Uploaded_FileBG(DataObject updateDboRasUploadedFileBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Uploaded_FileBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Uploaded_FileBG(DataObject updateallDboRasUploadedFileBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Uploaded_FileBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Uploaded_FileBG(DataObject retrieveDboRasUploadedFileBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Uploaded_FileBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Uploaded_FileBG(DataObject retrieveallDboRasUploadedFileBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Uploaded_FileBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Uploaded_FileBG(DataObject applychangesDboRasUploadedFileBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Uploaded_FileBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Uploaded_FileBG(DataObject existsDboRasUploadedFileBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Uploaded_FileBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Uploaded_FileBG(DataObject upsertDboRasUploadedFileBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Uploaded_FileBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Uploaded_FileBG(DataObject batchcreateDboRasUploadedFileBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Uploaded_FileBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Uploaded_FileBG(DataObject batchupdateDboRasUploadedFileBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Uploaded_FileBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_User_DetailsBG(DataObject createDboRasUserDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_User_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_User_DetailsBG(DataObject updateDboRasUserDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_User_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_User_DetailsBG(DataObject updateallDboRasUserDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_User_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_User_DetailsBG(DataObject retrieveDboRasUserDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_User_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_User_DetailsBG(DataObject retrieveallDboRasUserDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_User_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_User_DetailsBG(DataObject applychangesDboRasUserDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_User_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_User_DetailsBG(DataObject existsDboRasUserDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_User_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_User_DetailsBG(DataObject upsertDboRasUserDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_User_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_User_DetailsBG(DataObject batchcreateDboRasUserDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_User_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_User_DetailsBG(DataObject batchupdateDboRasUserDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_User_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Valid_Proposal_StatusBG(DataObject createDboRasValidProposalStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Valid_Proposal_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Valid_Proposal_StatusBG(DataObject updateDboRasValidProposalStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Valid_Proposal_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Valid_Proposal_StatusBG(DataObject updateallDboRasValidProposalStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Valid_Proposal_StatusBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Valid_Proposal_StatusBG(DataObject retrieveDboRasValidProposalStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Valid_Proposal_StatusBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Valid_Proposal_StatusBG(DataObject retrieveallDboRasValidProposalStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Valid_Proposal_StatusBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Valid_Proposal_StatusBG(DataObject applychangesDboRasValidProposalStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Valid_Proposal_StatusBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Valid_Proposal_StatusBG(DataObject existsDboRasValidProposalStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Valid_Proposal_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Valid_Proposal_StatusBG(DataObject upsertDboRasValidProposalStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Valid_Proposal_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Valid_Proposal_StatusBG(DataObject batchcreateDboRasValidProposalStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Valid_Proposal_StatusBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Valid_Proposal_StatusBG(DataObject batchupdateDboRasValidProposalStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Valid_Proposal_StatusBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Work_ScheduleBG(DataObject createDboRasWorkScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Work_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Work_ScheduleBG(DataObject updateDboRasWorkScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Work_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Work_ScheduleBG(DataObject updateallDboRasWorkScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Work_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Work_ScheduleBG(DataObject retrieveDboRasWorkScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Work_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Work_ScheduleBG(DataObject retrieveallDboRasWorkScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Work_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Work_ScheduleBG(DataObject applychangesDboRasWorkScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Work_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Work_ScheduleBG(DataObject existsDboRasWorkScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Work_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Work_ScheduleBG(DataObject upsertDboRasWorkScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Work_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Work_ScheduleBG(DataObject batchcreateDboRasWorkScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Work_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Work_ScheduleBG(DataObject batchupdateDboRasWorkScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Work_ScheduleBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Approval_GridBG(DataObject createDboRasApprovalGridBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Approval_GridBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Approval_GridBG(DataObject updateDboRasApprovalGridBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Approval_GridBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Approval_GridBG(DataObject updateallDboRasApprovalGridBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Approval_GridBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Approval_GridBG(DataObject retrieveDboRasApprovalGridBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Approval_GridBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Approval_GridBG(DataObject retrieveallDboRasApprovalGridBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Approval_GridBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Approval_GridBG(DataObject applychangesDboRasApprovalGridBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Approval_GridBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Approval_GridBG(DataObject existsDboRasApprovalGridBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Approval_GridBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Approval_GridBG(DataObject upsertDboRasApprovalGridBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Approval_GridBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Approval_GridBG(DataObject batchcreateDboRasApprovalGridBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Approval_GridBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Approval_GridBG(DataObject batchupdateDboRasApprovalGridBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Approval_GridBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Bank_DetailsBG(DataObject createDboRasBankDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Bank_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Bank_DetailsBG(DataObject updateDboRasBankDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Bank_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Bank_DetailsBG(DataObject updateallDboRasBankDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Bank_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Bank_DetailsBG(DataObject retrieveDboRasBankDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Bank_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Bank_DetailsBG(DataObject retrieveallDboRasBankDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Bank_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Bank_DetailsBG(DataObject applychangesDboRasBankDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Bank_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Bank_DetailsBG(DataObject existsDboRasBankDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Bank_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Bank_DetailsBG(DataObject upsertDboRasBankDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Bank_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Bank_DetailsBG(DataObject batchcreateDboRasBankDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Bank_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Bank_DetailsBG(DataObject batchupdateDboRasBankDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Bank_DetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Icrm_Service_ResponseBG(DataObject createDboRasIcrmServiceResponseBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Icrm_Service_ResponseBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Icrm_Service_ResponseBG(DataObject updateDboRasIcrmServiceResponseBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Icrm_Service_ResponseBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Icrm_Service_ResponseBG(DataObject updateallDboRasIcrmServiceResponseBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Icrm_Service_ResponseBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Icrm_Service_ResponseBG(DataObject retrieveDboRasIcrmServiceResponseBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Icrm_Service_ResponseBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Icrm_Service_ResponseBG(DataObject retrieveallDboRasIcrmServiceResponseBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Icrm_Service_ResponseBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Icrm_Service_ResponseBG(DataObject applychangesDboRasIcrmServiceResponseBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Icrm_Service_ResponseBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Icrm_Service_ResponseBG(DataObject existsDboRasIcrmServiceResponseBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Icrm_Service_ResponseBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Icrm_Service_ResponseBG(DataObject upsertDboRasIcrmServiceResponseBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Icrm_Service_ResponseBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Icrm_Service_ResponseBG(DataObject batchcreateDboRasIcrmServiceResponseBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Icrm_Service_ResponseBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Icrm_Service_ResponseBG(DataObject batchupdateDboRasIcrmServiceResponseBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Icrm_Service_ResponseBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Excess_Refund_ServiceBG(DataObject createDboRasExcessRefundServiceBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Excess_Refund_ServiceBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Excess_Refund_ServiceBG(DataObject updateDboRasExcessRefundServiceBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Excess_Refund_ServiceBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Excess_Refund_ServiceBG(DataObject updateallDboRasExcessRefundServiceBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Excess_Refund_ServiceBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Excess_Refund_ServiceBG(DataObject retrieveDboRasExcessRefundServiceBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Excess_Refund_ServiceBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Excess_Refund_ServiceBG(DataObject retrieveallDboRasExcessRefundServiceBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Excess_Refund_ServiceBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Excess_Refund_ServiceBG(DataObject applychangesDboRasExcessRefundServiceBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Excess_Refund_ServiceBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Excess_Refund_ServiceBG(DataObject existsDboRasExcessRefundServiceBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Excess_Refund_ServiceBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Excess_Refund_ServiceBG(DataObject upsertDboRasExcessRefundServiceBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Excess_Refund_ServiceBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Excess_Refund_ServiceBG(DataObject batchcreateDboRasExcessRefundServiceBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Excess_Refund_ServiceBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Excess_Refund_ServiceBG(DataObject batchupdateDboRasExcessRefundServiceBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Excess_Refund_ServiceBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Reverse_Feed_Status_MasterBG(DataObject createDboRasReverseFeedStatusMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Reverse_Feed_Status_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Reverse_Feed_Status_MasterBG(DataObject updateDboRasReverseFeedStatusMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Reverse_Feed_Status_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Reverse_Feed_Status_MasterBG(DataObject updateallDboRasReverseFeedStatusMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Reverse_Feed_Status_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Reverse_Feed_Status_MasterBG(DataObject retrieveDboRasReverseFeedStatusMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Reverse_Feed_Status_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Reverse_Feed_Status_MasterBG(DataObject retrieveallDboRasReverseFeedStatusMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Reverse_Feed_Status_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Reverse_Feed_Status_MasterBG(DataObject applychangesDboRasReverseFeedStatusMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Reverse_Feed_Status_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Reverse_Feed_Status_MasterBG(DataObject existsDboRasReverseFeedStatusMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Reverse_Feed_Status_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Reverse_Feed_Status_MasterBG(DataObject upsertDboRasReverseFeedStatusMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Reverse_Feed_Status_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Reverse_Feed_Status_MasterBG(DataObject batchcreateDboRasReverseFeedStatusMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Reverse_Feed_Status_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Reverse_Feed_Status_MasterBG(DataObject batchupdateDboRasReverseFeedStatusMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Reverse_Feed_Status_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Bops_Channel_Ntid_ConfigBG(DataObject createDboRasBopsChannelNtidConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Bops_Channel_Ntid_ConfigBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Bops_Channel_Ntid_ConfigBG(DataObject updateDboRasBopsChannelNtidConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Bops_Channel_Ntid_ConfigBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Bops_Channel_Ntid_ConfigBG(DataObject updateallDboRasBopsChannelNtidConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Bops_Channel_Ntid_ConfigBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Bops_Channel_Ntid_ConfigBG(DataObject retrieveDboRasBopsChannelNtidConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Bops_Channel_Ntid_ConfigBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Bops_Channel_Ntid_ConfigBG(DataObject retrieveallDboRasBopsChannelNtidConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Bops_Channel_Ntid_ConfigBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Bops_Channel_Ntid_ConfigBG(DataObject applychangesDboRasBopsChannelNtidConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Bops_Channel_Ntid_ConfigBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Bops_Channel_Ntid_ConfigBG(DataObject existsDboRasBopsChannelNtidConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Bops_Channel_Ntid_ConfigBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Bops_Channel_Ntid_ConfigBG(DataObject upsertDboRasBopsChannelNtidConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Bops_Channel_Ntid_ConfigBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Bops_Channel_Ntid_ConfigBG(DataObject batchcreateDboRasBopsChannelNtidConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Bops_Channel_Ntid_ConfigBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Bops_Channel_Ntid_ConfigBG(DataObject batchupdateDboRasBopsChannelNtidConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Bops_Channel_Ntid_ConfigBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Excess_Refund_DraftBG(DataObject createDboRasExcessRefundDraftBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Excess_Refund_DraftBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Excess_Refund_DraftBG(DataObject updateDboRasExcessRefundDraftBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Excess_Refund_DraftBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Excess_Refund_DraftBG(DataObject updateallDboRasExcessRefundDraftBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Excess_Refund_DraftBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Excess_Refund_DraftBG(DataObject retrieveDboRasExcessRefundDraftBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Excess_Refund_DraftBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Excess_Refund_DraftBG(DataObject retrieveallDboRasExcessRefundDraftBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Excess_Refund_DraftBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Excess_Refund_DraftBG(DataObject applychangesDboRasExcessRefundDraftBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Excess_Refund_DraftBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Excess_Refund_DraftBG(DataObject existsDboRasExcessRefundDraftBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Excess_Refund_DraftBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Excess_Refund_DraftBG(DataObject upsertDboRasExcessRefundDraftBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Excess_Refund_DraftBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Excess_Refund_DraftBG(DataObject batchcreateDboRasExcessRefundDraftBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Excess_Refund_DraftBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Excess_Refund_DraftBG(DataObject batchupdateDboRasExcessRefundDraftBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Excess_Refund_DraftBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Excess_Refund_Reverse_FeedBG(DataObject createDboRasExcessRefundReverseFeedBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Excess_Refund_Reverse_FeedBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Excess_Refund_Reverse_FeedBG(DataObject updateDboRasExcessRefundReverseFeedBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Excess_Refund_Reverse_FeedBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Excess_Refund_Reverse_FeedBG(DataObject updateallDboRasExcessRefundReverseFeedBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Excess_Refund_Reverse_FeedBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Excess_Refund_Reverse_FeedBG(DataObject retrieveDboRasExcessRefundReverseFeedBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Excess_Refund_Reverse_FeedBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Excess_Refund_Reverse_FeedBG(DataObject retrieveallDboRasExcessRefundReverseFeedBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Excess_Refund_Reverse_FeedBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Excess_Refund_Reverse_FeedBG(DataObject applychangesDboRasExcessRefundReverseFeedBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Excess_Refund_Reverse_FeedBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Excess_Refund_Reverse_FeedBG(DataObject existsDboRasExcessRefundReverseFeedBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Excess_Refund_Reverse_FeedBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Excess_Refund_Reverse_FeedBG(DataObject upsertDboRasExcessRefundReverseFeedBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Excess_Refund_Reverse_FeedBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Excess_Refund_Reverse_FeedBG(DataObject batchcreateDboRasExcessRefundReverseFeedBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Excess_Refund_Reverse_FeedBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Excess_Refund_Reverse_FeedBG(DataObject batchupdateDboRasExcessRefundReverseFeedBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Excess_Refund_Reverse_FeedBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "RASExcessRefundTicketFromDraft#createRASExcessRefundTicketFromDraft(String draftTicketNo)"
	 * of wsdl interface "RASExcessRefundTicketFromDraft"	
	 */
	public void onCreateRASExcessRefundTicketFromDraftResponse(Ticket __ticket,
			String returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

}