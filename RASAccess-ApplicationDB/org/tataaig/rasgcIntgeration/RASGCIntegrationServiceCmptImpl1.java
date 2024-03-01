package org.tataaig.rasgcIntgeration;

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

import commonj.sdo.DataObject;
import com.ibm.websphere.bo.BOFactory;
import com.ibm.websphere.bo.BOXMLSerializer;
import com.ibm.websphere.sca.ServiceManager;
import com.ibm.websphere.sca.Service;
import com.ibm.websphere.sca.ServiceBusinessException;
import com.ibm.websphere.sca.ServiceRuntimeException;
import com.ibm.websphere.sca.Ticket;


public class RASGCIntegrationServiceCmptImpl1 {
	/**
	 * Default constructor.
	 */
	public RASGCIntegrationServiceCmptImpl1() {
		super();
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
	 * named "NegativeEndorsementDraftTicketPartner".  This will return an instance of 
	 * {@link com.ibm.websphere.sca.Service}.  This is the dynamic
	 * interface which is used to invoke operations on the reference service
	 * either synchronously or asynchronously.  You will need to pass the operation
	 * name in order to invoke an operation on the service.
	 *
	 * @generated (com.ibm.wbit.java)
	 *
	 * @return Service
	 */
	private Service _NegativeEndorsementDraftTicketPartner = null;

	public Service locateService_NegativeEndorsementDraftTicketPartner() {
		if (_NegativeEndorsementDraftTicketPartner == null) {
			_NegativeEndorsementDraftTicketPartner = (Service) ServiceManager.INSTANCE
					.locateService("NegativeEndorsementDraftTicketPartner");
		}
		return _NegativeEndorsementDraftTicketPartner;
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
	public BOFactory getBOFactory() {
		return (BOFactory) ServiceManager.INSTANCE
				.locateService("com/ibm/websphere/bo/BOFactory");
	}
	
	public enum Constants {
		CancellationRefund("CANCELLATION REFUND"), NegativeEndorsementRefund("NEGATIVE ENDORSEMENT REFUND"), ExcessRefund("EXCESS REFUND"),IndividualTicket("INDIVIDUAL TICKET"), DebitAuthorization(
				"DEBIT AUTHORIZATION"), Cheque("CHEQUE"), NEFT("NEFT"), CUSTOMER(
				"CUSTOMER"), INTERMEDIARY("INTERMEDIARY"), DEALER("DEALER"), FINANCIER(
				"FINANCIER"), OTHERS("OTHERS"),PANCARD("PAN CARD"),ADDRESSPROOF("ADDRESS PROOF"),CUSTOMERINITIATED("CUSTOMER INITIATED"),GC("GC");

		String constant;

		Constants(String constant) {
			this.constant = constant;
		}

		public String getValue() {
			return this.constant;
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
		
		// formatStringDate Method to convert string to date format into DB Accepted mode
		public Timestamp formatStringDate(String date) throws ParseException {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    Date parsedDate = dateFormat.parse(date);
		    Timestamp timestamp = new Timestamp(parsedDate.getTime());
		    System.out.println("Database Coverted date and time "+timestamp);
			return timestamp;
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
		if(category == "NEGATIVE ENDORSEMENT SERVICE LOGGING ID SEQUENCE")
		{
		DataObject negativeEndorsementServiceLoggingIdBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementloggingidbg",
							"NegativeEndorsementLoggingIDBG");
		DataObject negativeEndorsementServiceLoggingId = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementloggingid",
						"NegativeEndorsementLoggingID");
		
		negativeEndorsementServiceLoggingIdBG.setDataObject("NegativeEndorsementLoggingID", negativeEndorsementServiceLoggingId);
		
		DataObject negativeEndorsementServiceSeries = (DataObject) locateService_DBInterfacePartner().invoke(
				"retrieveallNegativeEndorsementLoggingIDBG", negativeEndorsementServiceLoggingIdBG);
		nextValue = 
				negativeEndorsementServiceSeries.getInt("retrieveallNegativeEndorsementLoggingIDBGOutput/NegativeEndorsementLoggingID/next_value");
		
		}
		if(category == "NEGATIVE ENDORSEMENT DRAFT SERIES")
		{
		DataObject negativeEndorsementDraftNoBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementdraftnobg",
						"NegativeEndorsementDraftNoBG");
        DataObject negativeEndorsementDraftNo = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementdraftno",
						"NegativeEndorsementDraftNo");
		
        negativeEndorsementDraftNoBG.setDataObject("NegativeEndorsementDraftNo", negativeEndorsementDraftNo);
		
		DataObject negativeEndorsementDraftNoSeries = (DataObject) locateService_DBInterfacePartner().invoke(
				"retrieveallNegativeEndorsementDraftNoBG", negativeEndorsementDraftNoBG);
		nextValue = 
				negativeEndorsementDraftNoSeries.getInt("retrieveallNegativeEndorsementDraftNoBGOutput/NegativeEndorsementDraftNo/next_value");	
		}
		if(category == "NEGATIVE ENDORSEMENT DRAFT ID SEQUENCE")
		{
		DataObject negativeEndorsementDraftIdBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementdraftidbg",
						"NegativeEndorsementDraftIDBG");
		DataObject negativeEndorsementDraftId = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementdraftid",
							"NegativeEndorsementDraftID");
		
		negativeEndorsementDraftIdBG.setDataObject("NegativeEndorsementDraftID", negativeEndorsementDraftId);
		
		DataObject negativeEndorsementDraftIdSeries = (DataObject) locateService_DBInterfacePartner().invoke(
				"retrieveallNegativeEndorsementDraftIDBG", negativeEndorsementDraftIdBG);
		nextValue = 
				negativeEndorsementDraftIdSeries.getInt("retrieveallNegativeEndorsementDraftIDBGOutput/NegativeEndorsementDraftID/next_value");	
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
	
	// Generation of Negative Endorsement Draft Ticket Number
		public String getNegativeEndorsementDraftTicketNumber(String category) {

			DataObject appConfigBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/appconfigbg",
							"AppConfigBG");

			DataObject appConfigData = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/appconfig",
							"AppConfig");

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
				Integer draftTicketSeries = incrementSequence(category);
				System.out.println("Negative Endorsement Draft Ticket Format --> " + draftFormat
						+ " Negative Endorsement Draft Ticket Updated Value --> " + draftTicketSeries);
				String draftTicketNo = "";

				draftTicketNo = draftFormat + String.valueOf(draftTicketSeries);

				DataObject updateAppConfigBG = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateappconfigbg",
								"UpdateAppConfigBG");

				DataObject updateAppConfigInput = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateappconfig",
								"UpdateAppConfig");

				updateAppConfigInput.setString("statement1parameter2",
						category);
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
	
	// Convert draftTicketRequest into SOAP XML Request and save in the database
	public int convertDraftTicketRequestObjectToXML(DataObject draftTicketRequest)
			throws IOException, ParseException {
		DataObject exceptionData = null;
		int serviceId=0;
		
		BOXMLSerializer mySerializer = (BOXMLSerializer) ServiceManager.INSTANCE
				.locateService("com/ibm/websphere/bo/BOXMLSerializer");
	    String rootElementName = draftTicketRequest.getType().getName();
		String targetNamespace = draftTicketRequest.getType().getURI();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mySerializer.writeDataObject(draftTicketRequest, targetNamespace,
				"draftTicketRequest", baos);
		String draftTicketRequestXml = new String(baos.toString());
		System.out.println("Draft Ticket Request XML " + draftTicketRequestXml);
		
		try {
			MessageFactory factory = MessageFactory.newInstance(); //A factory for creating SOAPMessage objects
			SOAPMessage soapMsg = factory.createMessage();// SOAP message is an XML document or a MIME message whose first body part is an XML/SOAP document.
			SOAPPart part = soapMsg.getSOAPPart();//The container for the SOAP-specific portion of a SOAPMessage object
			SOAPEnvelope envelope = part.getEnvelope();//The container for the SOAPHeader and SOAPBody portions of a SOAPPart object
			SOAPHeader header = envelope.getHeader(); //A representation of the SOAP header element
			SOAPBody body = envelope.getBody();//An object that represents the contents of the SOAP body element in a SOAP message
			SOAPBodyElement element = body.addBodyElement(envelope.createName(
					"createNegativeEndorsementDraftTicket", "ras",
					draftTicketRequestXml));//A SOAPBodyElement object represents the contents in a SOAPBody object
					
			ByteArrayOutputStream requestStream = new ByteArrayOutputStream();
			soapMsg.writeTo(requestStream); //This class implements an output stream in which the data is written into a byte array. The buffer automatically grows as data is written to it
			String soapXmlRequest = new String(requestStream.toByteArray(),
					"utf-8");
			System.out.println("Negative Endorsement Service SOAP Request created");
			serviceId = incrementSequence("NEGATIVE ENDORSEMENT SERVICE LOGGING ID SEQUENCE");
			DataObject serviceBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_gc_negative_endorsement_service_loggingbg",
							"DboRas_Gc_Negative_Endorsement_Service_LoggingBG");
			DataObject serviceData = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_gc_negative_endorsement_service_logging",
							"DboRas_Gc_Negative_Endorsement_Service_Logging");
			if(draftTicketRequest!=null)
			{
			if(draftTicketRequest.getString("userId").length()<=200)
			{
			serviceData.setString("user_id", draftTicketRequest.getString("userId"));
			serviceData.setString("created_by",
					draftTicketRequest.getString("userId"));
			}
			
			if(draftTicketRequest.getString("lob").length()<=200)
			serviceData.setString("lob", draftTicketRequest.getString("lob"));
			
			if(draftTicketRequest.getString("productCode").length()<=200)
			serviceData.setString("product_code", draftTicketRequest.getString("productCode"));
				
			if(draftTicketRequest.getString("productName").length()<=200)
			serviceData.setString("product_name", draftTicketRequest.getString("productName"));
			
			if(draftTicketRequest.getString("policyNumber").length()<=200)
			serviceData.setString("policy_number", draftTicketRequest.getString("policyNumber"));
					
			if(draftTicketRequest.getString("endorsementProposalNumber").length()<=200)
			serviceData.setString("endorsement_proposal_number", draftTicketRequest.getString("endorsementProposalNumber"));
			}
			serviceData.setInt("ras_gc_negative_endorsement_service_logging_id",serviceId );
			serviceData.setString("request_xml", soapXmlRequest);
			serviceData.setDate("created_on", formatDate(new Date()));
			serviceBG.set("DboRas_Gc_Negative_Endorsement_Service_Logging", serviceData);
			this
					.locateService_DBInterfacePartner().invoke(
							"createDboRas_Gc_Negative_Endorsement_Service_LoggingBG", serviceBG);
			
			System.out.println("Negative Endorsement Service Id in request "+serviceId);
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

	// Convert draftTicketResponse into SOAP XML Response and save in the database
		public void convertdraftTicketResponseObjectToXML(DataObject draftTicketResponse,
				int serviceId,String userId) throws IOException, ParseException {
			DataObject exceptionData = null;
	        System.out.println("Negative Endorsement Service ID in response "+serviceId);
			BOXMLSerializer mySerializer = (BOXMLSerializer) ServiceManager.INSTANCE
					.locateService("com/ibm/websphere/bo/BOXMLSerializer");
			String rootElementName = draftTicketResponse.getType().getName();
			String targetNamespace = draftTicketResponse.getType().getURI();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			mySerializer.writeDataObject(draftTicketResponse, targetNamespace,
					"draftTicketResponse", baos);
			String draftTicketResponseXml = new String(baos.toString());
			System.out.println("Draft Ticket Response XML " + draftTicketResponseXml);
			try {
				MessageFactory factory = MessageFactory.newInstance();
				SOAPMessage soapMsg = factory.createMessage();
				SOAPPart part = soapMsg.getSOAPPart();
				SOAPEnvelope envelope = part.getEnvelope();
				SOAPHeader header = envelope.getHeader();
				SOAPBody body = envelope.getBody();
				SOAPBodyElement element = body.addBodyElement(envelope.createName(
						"createNegativeEndorsementDraftTicketResponse", "ras",
						draftTicketResponseXml));
				ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
				soapMsg.writeTo(responseStream);
				String soapXmlResponse = new String(responseStream.toByteArray(),
						"utf-8");
				System.out.println("Negative Endorsement Service SOAP Response created");
				DataObject updateServiceBG = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updatenegativeendorsementserviceloggingbg",
								"UpdateNegativeEndorsementServiceLoggingBG");

				DataObject updateServiceInput = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updatenegativeendorsementservicelogging",
								"UpdateNegativeEndorsementServiceLogging");

				
				updateServiceInput.setInt("statement1parameter4", serviceId);
				updateServiceInput.setString("statement1parameter1", soapXmlResponse);
				updateServiceInput
						.setDate("statement1parameter2", formatDate(new Date()));
				if(userId.length()<=200)
					updateServiceInput
				.setString("statement1parameter3", userId);
				updateServiceBG.setDataObject(
						"UpdateNegativeEndorsementServiceLogging",
						updateServiceInput);
				this.locateService_DBInterfacePartner().invoke(
						"executeUpdateNegativeEndorsementServiceLoggingBG", updateServiceBG);
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

		
	// validation for negative endorsement draft ticket object
		public List<String> validateNegativeEndorsementDraftTicketInput(DataObject draftTicketInput)
				throws ParseException {
			List<String> validationErrors = new ArrayList<String>();

			if (draftTicketInput.getString("userId") == null
					|| "".equalsIgnoreCase(draftTicketInput.getString("userId"))) {
				validationErrors.add("User ID cannot be blank");
				return validationErrors;
			}
			
			if (draftTicketInput.getString("userId") != null && !"".equalsIgnoreCase(draftTicketInput.getString("userId"))) {
			System.out.println("User ID len"+draftTicketInput.getString("userId").length());
			int userIdLength = draftTicketInput.getString("userId").length();
			if (userIdLength > 200) {
			validationErrors.add("Invalid value input in User ID, Value exceeding field length limit 200 characters. Please recheck the value");
			return validationErrors;
			}
			try{
			DataObject userData = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/userdetails",
								"UserDetails");
		    DataObject userDataBG = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/userdetailsbg",
								"UserDetailsBG");
		    userData.setString("parameter1", draftTicketInput.getString("userId"));	
		    userDataBG.set("UserDetails", userData);
		    DataObject userDataResponseBO = (DataObject) this.locateService_DBInterfacePartner().invoke("retrieveallUserDetailsBG",userDataBG);
		    String ntid = userDataResponseBO.getString("retrieveallUserDetailsBGOutput/UserDetails[0]/ntid");
		    Boolean userStatus = userDataResponseBO.getBoolean("retrieveallUserDetailsBGOutput/UserDetails[0]/status");
			System.out.println("User Ntid : "+ntid+", User Status : " + userStatus);	
			if(ntid == null || "".equalsIgnoreCase(ntid))
			{
				   validationErrors.add("Negative Endorsement Draft Ticket cannot be created as the user id does not exist in RAS");
				   return validationErrors;
			}
			else 
			{
			if(userStatus == false)
			{
				   validationErrors.add("Negative Endorsement Draft Ticket cannot be created as the user id is inactive in RAS");
	               return validationErrors;
			}
			}
			}
			catch (ServiceBusinessException sbe) {
				
				DataObject exceptionData = (DataObject) sbe.getData();
				validationErrors.add(exceptionData.get("message").toString()+" error while fetching the user data");
			} 
			catch (ServiceRuntimeException sre) {
				validationErrors.add(sre.getMessage().toString()+" error while fetching the user data");
			} 
			catch (Exception e) {
				validationErrors.add(e.getMessage()+" error while fetching the user data");
			}
			}
			if (draftTicketInput.getString("lob") == null
					|| "".equalsIgnoreCase(draftTicketInput.getString("lob"))) {
				validationErrors.add("LOB cannot be blank");
				return validationErrors;
			}
			
			
			if (draftTicketInput.getString("lob") != null && !"".equalsIgnoreCase(draftTicketInput.getString("lob"))) {
				int lobLength = draftTicketInput.getString("lob").length();
				if (lobLength > 200) {
					validationErrors.add("Invalid value input in LOB, Value exceeding field length limit 200 characters. Please recheck the value");
					return validationErrors;
				}
				try{
				DataObject lobData = getBOFactory()
							.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementlob",
									"NegativeEndorsementLob");
			    DataObject lobDataBG = getBOFactory()
							.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementlobbg",
									"NegativeEndorsementLobBG");
			    lobData.setString("parameter1", draftTicketInput.getString("lob"));	
			    lobData.setString("parameter2", draftTicketInput.getString("lob"));	
			    lobDataBG.set("NegativeEndorsementLob", lobData);
			    DataObject lobDataResponseBO = (DataObject) this.locateService_DBInterfacePartner().invoke("retrieveallNegativeEndorsementLobBG",lobDataBG);
			    String lob = lobDataResponseBO.getString("retrieveallNegativeEndorsementLobBGOutput/NegativeEndorsementLob[0]/code");
			    System.out.println("LOB from master : "+lob);
				if("Invalid Lob".equalsIgnoreCase(lob))
				{
					   validationErrors.add("Invalid value input in LOB, LOB unavailable in negative endorsement master. Please recheck the value");
					   return validationErrors;
				}
				else
			    draftTicketInput.setString("lob",lob);	
				}
				catch (ServiceBusinessException sbe) {
					
					DataObject exceptionData = (DataObject) sbe.getData();
					validationErrors.add(exceptionData.get("message").toString()+" error while validating the negative endorsement lob from master");
				} 
				catch (ServiceRuntimeException sre) {
					validationErrors.add(sre.getMessage().toString()+" error while validating the negative endorsement lob from master");
				} 
				catch (Exception e) {
					validationErrors.add(e.getMessage()+" error while validating the negative endorsement lob from master");
				}
				}
			
			if (draftTicketInput.getString("policyNumber") == null
					|| "".equalsIgnoreCase(draftTicketInput.getString("policyNumber"))) {
				validationErrors.add("Policy Number cannot be blank");
				return validationErrors;
			}
			
			String policyNoPattern = "^[A-Za-z0-9]{10}$";
			if (!"".equalsIgnoreCase(draftTicketInput.getString("policyNumber"))
					&& draftTicketInput.getString("policyNumber") != null) {
				if (!(draftTicketInput.getString("policyNumber")
						.matches(policyNoPattern))) {
					validationErrors
							.add("Invalid value input in Policy Number, Only 10 alphanumeric characters value should be input in Policy Number. Please recheck the value");
					return validationErrors;
				}
			}
			
			if (draftTicketInput.getString("productCode") == null
					|| "".equalsIgnoreCase(draftTicketInput.getString("productCode"))) {
				validationErrors.add("Product Code cannot be blank");
				return validationErrors;
			}
			

			if (draftTicketInput.getString("productCode") != null && !"".equalsIgnoreCase(draftTicketInput.getString("productCode"))) {
				String ProductCodePattern = "^[0-9]{4}$";
				if (!(draftTicketInput.getString("productCode").matches(ProductCodePattern))) {
					validationErrors.add("Invalid value input in Product Code, Only 4 digits numeric value should be input in Product Code. Please recheck the value");
					return validationErrors;
				}
				
				try{
					DataObject productCodeData = getBOFactory()
								.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementproductcode",
										"NegativeEndorsementProductCode");
				    DataObject productCodeDataBG = getBOFactory()
								.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementproductcodebg",
										"NegativeEndorsementProductCodeBG");
				    productCodeData.setString("parameter1", draftTicketInput.getString("productCode"));	
				    productCodeData.setString("parameter2", draftTicketInput.getString("productCode"));	
				    productCodeDataBG.set("NegativeEndorsementProductCode", productCodeData);
				    DataObject productCodeDataResponseBO = (DataObject) this.locateService_DBInterfacePartner().invoke("retrieveallNegativeEndorsementProductCodeBG",productCodeDataBG);
				    String productCode = productCodeDataResponseBO.getString("retrieveallNegativeEndorsementProductCodeBGOutput/NegativeEndorsementProductCode[0]/product_code");
					System.out.println("Product Code from master : "+productCode);	
				    if("Invalid Product Code".equalsIgnoreCase(productCode))
					{
						   validationErrors.add("Invalid value input in Product Code, Product Code unavailable in negative endorsement master. Please recheck the value");
						   return validationErrors;
					}
				    else
				    draftTicketInput.setString("productCode",productCode);
					}
					catch (ServiceBusinessException sbe) {
						
						DataObject exceptionData = (DataObject) sbe.getData();
						validationErrors.add(exceptionData.get("message").toString()+" error while validating the negative endorsement product code from master");
					} 
					catch (ServiceRuntimeException sre) {
						validationErrors.add(sre.getMessage().toString()+" error while validating the negative endorsement product code from master");
					} 
					catch (Exception e) {
						validationErrors.add(e.getMessage()+" error while validating the negative endorsement product code from master");
					}
			}
			
			if (draftTicketInput.getString("productName") == null
					|| "".equalsIgnoreCase(draftTicketInput.getString("productName"))) {
				validationErrors.add("Product Name cannot be blank");
				return validationErrors;
			}
			if (draftTicketInput.getString("productName") != null && !"".equalsIgnoreCase(draftTicketInput.getString("productName")))
			{
			int productNameLength = draftTicketInput.getString("productName").length();
			if (productNameLength > 200) {
				validationErrors.add("Invalid value input in Product Name, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			try{
				DataObject productNameData = getBOFactory()
							.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementproductname",
									"NegativeEndorsementProductName");
			    DataObject productNameDataBG = getBOFactory()
							.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementproductnamebg",
									"NegativeEndorsementProductNameBG");
			    productNameData.setString("parameter1", draftTicketInput.getString("productName"));	
			    productNameData.setString("parameter2", draftTicketInput.getString("productName"));	
			    productNameDataBG.set("NegativeEndorsementProductName", productNameData);
			    DataObject productNameDataResponseBO = (DataObject) this.locateService_DBInterfacePartner().invoke("retrieveallNegativeEndorsementProductNameBG",productNameDataBG);
			    String productName = productNameDataResponseBO.getString("retrieveallNegativeEndorsementProductNameBGOutput/NegativeEndorsementProductName[0]/product_name");
				System.out.println("Product Name from master : "+productName);
			    if("Invalid Product Name".equalsIgnoreCase(productName))
				{
					   validationErrors.add("Invalid value input in Product Name, Product Name unavailable in negative endorsement master. Please recheck the value");
					   return validationErrors;
				}
			    else
			    draftTicketInput.setString("productName",productName);
				}
				catch (ServiceBusinessException sbe) {
					
					DataObject exceptionData = (DataObject) sbe.getData();
					validationErrors.add(exceptionData.get("message").toString()+" error while validating the negative endorsement product name from master");
				} 
				catch (ServiceRuntimeException sre) {
					validationErrors.add(sre.getMessage().toString()+" error while validating the negative endorsement product name from master");
				} 
				catch (Exception e) {
					validationErrors.add(e.getMessage()+" error while validating the negative endorsement product name from master");
				}
			}
			
			
			try{
				DataObject lobProductData = getBOFactory()
							.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementlobproduct",
									"NegativeEndorsementLobProduct");
			    DataObject lobProductDataBG = getBOFactory()
							.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementlobproductbg",
									"NegativeEndorsementLobProductBG");
			    lobProductData.setString("parameter1", draftTicketInput.getString("lob"));	
			    lobProductData.setString("parameter2", draftTicketInput.getString("productCode"));	
			    lobProductData.setString("parameter3", draftTicketInput.getString("productName"));	
			    lobProductDataBG.set("NegativeEndorsementLobProduct", lobProductData);
			    DataObject lobProductDataResponseBO = (DataObject) this.locateService_DBInterfacePartner().invoke("retrieveallNegativeEndorsementLobProductBG",lobProductDataBG);
			    String lobProductResult = lobProductDataResponseBO.getString("retrieveallNegativeEndorsementLobProductBGOutput/NegativeEndorsementLobProduct[0]/result");
				System.out.println("Lob Product from master : "+lobProductResult);
			    if("Invalid Combination of LOB, Product Code and Product Name".equalsIgnoreCase(lobProductResult))
				{
					   validationErrors.add("Invalid Combination of LOB, Product Code and Product Name for Negative Endorsement Draft Ticket Creation. Please recheck the value");
					   return validationErrors;
				}
				}
				catch (ServiceBusinessException sbe) {
					
					DataObject exceptionData = (DataObject) sbe.getData();
					validationErrors.add(exceptionData.get("message").toString()+" error while validating the negative endorsement lob, product code and product name from master");
				} 
				catch (ServiceRuntimeException sre) {
					validationErrors.add(sre.getMessage().toString()+" error while validating the negative endorsement lob, product code and product name from master");
				} 
				catch (Exception e) {
					validationErrors.add(e.getMessage()+" error while validating the negative endorsement lob, product code and product name from master");
				}
			
			if (draftTicketInput.getString("endorsementProposalNumber") == null
					|| "".equalsIgnoreCase(draftTicketInput.getString("endorsementProposalNumber"))) {
				validationErrors.add("Endorsement Proposal Number cannot be blank");
				return validationErrors;
			}
			
			String  endorsementProposalNumberPattern = "^[A-Za-z0-9]{15}$";
			if (!"".equalsIgnoreCase(draftTicketInput.getString("endorsementProposalNumber"))&& draftTicketInput.getString("endorsementProposalNumber") != null) {
				if (!(draftTicketInput.getString("endorsementProposalNumber").matches(endorsementProposalNumberPattern))) {
					validationErrors.add("Invalid value input in Endorsement Proposal Number, Only 15 alphanumeric characters value should be input in Endorsement Proposal Number. Please recheck the value");
					return validationErrors;
				}
			}
			

			if (draftTicketInput.getString("requestDate") == null
					|| "".equalsIgnoreCase(draftTicketInput
							.getString("requestDate"))) {
				validationErrors.add("Request Date cannot be blank");
				return validationErrors;
			}

			String requestDatePattern = "([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/((19|20)\\d\\d)";
			if (!(draftTicketInput.getString("requestDate")
					.matches(requestDatePattern))) {
				validationErrors
						.add("Invalid value input in Request Date, Only date value in the format dd/MM/yyyy should be input in Request Date. Please recheck the value");
				return validationErrors;
			}


			if (draftTicketInput.getString("refundAmount") == null
					|| "".equalsIgnoreCase(draftTicketInput
							.getString("refundAmount"))) {
				validationErrors.add("Refund Amount cannot be blank");
				return validationErrors;
			}

			String refundAmountPattern ="^(?=.*[1-9])\\d*(?:\\.\\d{1,2})?$";
			if (!(draftTicketInput.getString("refundAmount")
					.matches(refundAmountPattern))) {
				validationErrors
						.add("Invalid value input in Refund Amount, Only numeric and greater than 0 value upto 2 decimal points should be input in Refund Amount. Please recheck the value");
				return validationErrors;
			}

			if (draftTicketInput.getString("proposerName") == null
					|| "".equalsIgnoreCase(draftTicketInput.getString("proposerName"))) {
				validationErrors.add("Proposer Name cannot be blank");
				return validationErrors;
			}
			
			int proposerNameLength = draftTicketInput.getString(
					"proposerName").length();
			if (proposerNameLength > 200) {
				validationErrors
						.add("Invalid value input in Proposer Name, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			if (draftTicketInput.getString("customerName") == null
					|| "".equalsIgnoreCase(draftTicketInput.getString("customerName"))) {
				validationErrors.add("Customer Name cannot be blank");
				return validationErrors;
			}
			
			int customerNameLength = draftTicketInput.getString(
					"customerName").length();
			if (proposerNameLength > 200) {
				validationErrors
						.add("Invalid value input in Customer Name, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			
			if (draftTicketInput.getString("policyStatus") == null
					|| "".equalsIgnoreCase(draftTicketInput.getString("policyStatus"))) {
				validationErrors.add("Policy Status cannot be blank");
				return validationErrors;
			}
			
			int policyStatusLength = draftTicketInput.getString(
					"policyStatus").length();
			if (policyStatusLength > 200) {
				validationErrors
						.add("Invalid value input in Policy Status, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			if (draftTicketInput.getString("policyPremium") == null
					|| "".equalsIgnoreCase(draftTicketInput
							.getString("policyPremium"))) {
				validationErrors.add("Policy Premium cannot be blank");
				return validationErrors;
			}

			String policyPremiumPattern = "^(?=.*[1-9])\\d*(?:\\.\\d{1,2})?$";
			if (!(draftTicketInput.getString("policyPremium")
					.matches(policyPremiumPattern))) {
				validationErrors
						.add("Invalid value input in Policy Premium, Only numeric and greater than 0 value value upto 2 decimal points should be input in Policy Premium. Please recheck the value");
				return validationErrors;
			}
			
			if (draftTicketInput.getString("applicationDate") == null
					|| "".equalsIgnoreCase(draftTicketInput
							.getString("applicationDate"))) {
				validationErrors.add("Application Date cannot be blank");
				return validationErrors;
			}

			String applicationDatePattern = "([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/((19|20)\\d\\d)";
			if (!(draftTicketInput.getString("applicationDate")
					.matches(applicationDatePattern))) {
				validationErrors
						.add("Invalid value input in Application Date, Only date value in the format dd/MM/yyyy should be input in Application Date. Please recheck the value");
				return validationErrors;
			}
			
			if (draftTicketInput.getString("policyStartDate") == null
					|| "".equalsIgnoreCase(draftTicketInput
							.getString("policyStartDate"))) {
				validationErrors.add("Policy Start Date cannot be blank");
				return validationErrors;
			}

			String policyStartDatePattern = "([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/((19|20)\\d\\d)";
			if (!(draftTicketInput.getString("policyStartDate")
					.matches(policyStartDatePattern))) {
				validationErrors
						.add("Invalid value input in Policy Start Date, Only date value in the format dd/MM/yyyy should be input in Policy Start Date. Please recheck the value");
				return validationErrors;
			}
			
			
			if (draftTicketInput.getString("policyEndDate") == null
					|| "".equalsIgnoreCase(draftTicketInput
							.getString("policyEndDate"))) {
				validationErrors.add("Policy End Date cannot be blank");
				return validationErrors;
			}

			String policyEndDatePattern = "([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/((19|20)\\d\\d)";
			if (!(draftTicketInput.getString("policyEndDate")
					.matches(policyEndDatePattern))) {
				validationErrors
						.add("Invalid value input in Policy End Date, Only date value in the format dd/MM/yyyy should be input in Policy End Date. Please recheck the value");
				return validationErrors;
			}
			
			if (draftTicketInput.getString("conversionDate") == null
					|| "".equalsIgnoreCase(draftTicketInput
							.getString("conversionDate"))) {
				validationErrors.add("Conversion Date cannot be blank");
				return validationErrors;
			}

			String conversionDatePattern = "([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/((19|20)\\d\\d)";
			if (!(draftTicketInput.getString("conversionDate")
					.matches(conversionDatePattern))) {
				validationErrors
						.add("Invalid value input in Conversion Date, Only date value in the format dd/MM/yyyy should be input in Conversion Date. Please recheck the value");
				return validationErrors;
			}
			if("Motor".equalsIgnoreCase(draftTicketInput.getString("lob")))
			{
			if (draftTicketInput.getString("policyCoverType") == null
					|| "".equalsIgnoreCase(draftTicketInput.getString("policyCoverType"))) {
				validationErrors.add("Policy Cover Type cannot be blank for Motor lob");
				return validationErrors;
			}
			}
			int policyCoverTypeLength = draftTicketInput.getString(
					"policyCoverType").length();
			if (policyCoverTypeLength > 200) {
				validationErrors
						.add("Invalid value input in Policy Cover Type, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			if (draftTicketInput.getString("customerCode") == null
					|| "".equalsIgnoreCase(draftTicketInput.getString("customerCode"))) {
				validationErrors.add("Customer Code cannot be blank");
				return validationErrors;
			}
			
			int customerCodeLength = draftTicketInput.getString(
					"customerCode").length();
			if (customerCodeLength > 200) {
				validationErrors
						.add("Invalid value input in Customer Code, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			
			
			int aadhaarNumberLength = draftTicketInput.getString(
					"aadhaarNumber").length();
			if (aadhaarNumberLength > 200) {
				validationErrors
						.add("Invalid value input in Aadhaar Number, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			
			
			int lanNumberLength = draftTicketInput.getString(
					"lanNumber").length();
			if (lanNumberLength > 200) {
				validationErrors
						.add("Invalid value input in LAN Number, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			
			
			int certificateNumberLength = draftTicketInput.getString(
					"certificateNumber").length();
			if (certificateNumberLength > 200) {
				validationErrors
						.add("Invalid value input in Certificate Number, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			
			
			int engineNumberLength = draftTicketInput.getString(
					"engineNumber").length();
			if (engineNumberLength > 200) {
				validationErrors
						.add("Invalid value input in Engine Number, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			
			
			
			int chasisNumberLength = draftTicketInput.getString(
					"chasisNumber").length();
			if (chasisNumberLength > 200) {
				validationErrors
						.add("Invalid value input in Chasis Number, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			
			int registrationNumberLength = draftTicketInput.getString(
					"registrationNumber").length();
			if (registrationNumberLength > 200) {
				validationErrors
						.add("Invalid value input in Registration Number, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			
			
			int producerNameLength = draftTicketInput.getString(
					"producerName").length();
			if (producerNameLength > 200) {
				validationErrors
						.add("Invalid value input in Producer Name, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			
			
			
			int producerCodeLength = draftTicketInput.getString(
					"producerCode").length();
			if (producerCodeLength > 200) {
				validationErrors
						.add("Invalid value input in Producer Code, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
		
			
			int dealerNameLength = draftTicketInput.getString(
					"dealerName").length();
			if (dealerNameLength > 200) {
				validationErrors
						.add("Invalid value input in Dealer Name, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			
			
			int dealerCodeLength = draftTicketInput.getString(
					"dealerCode").length();
			if (dealerCodeLength > 200) {
				validationErrors
						.add("Invalid value input in Dealer Code, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			
		
			
			int financierNameLength = draftTicketInput.getString(
					"financierName").length();
			if (financierNameLength > 200) {
				validationErrors
						.add("Invalid value input in Financier Name, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			
			
			int financierCodeLength = draftTicketInput.getString(
					"financierCode").length();
			if (financierCodeLength > 200) {
				validationErrors
						.add("Invalid value input in Financier Code, Value exceeding field length limit 200 characters. Please recheck the value");
				return validationErrors;
			}
			
			
			if (draftTicketInput.getString("crsNumber") == null
					|| "".equalsIgnoreCase(draftTicketInput.getString("crsNumber"))) {
				validationErrors.add("CRS Number cannot be blank");
				return validationErrors;
			}
			
			String  crsNumberPattern = "^[A-Za-z0-9]{15}$";
			if (!"".equalsIgnoreCase(draftTicketInput.getString("crsNumber"))&& draftTicketInput.getString("crsNumber") != null) {
				if (!(draftTicketInput.getString("crsNumber").matches(crsNumberPattern))) {
					validationErrors.add("Invalid value input in CRS Number, Only 15 alphanumeric characters value should be input in CRS Number. Please recheck the value");
					return validationErrors;
				}
			}
			
			return validationErrors;
			
			
		}
		


	/**
	 * Method generated to support implementation of operation "createNegativeEndorsementDraftTicket" defined for WSDL port type 
	 * named "RASGCIntegrationService".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a parameter 
	 * type conveys that it is a complex type. Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public DataObject createNegativeEndorsementDraftTicket(
			DataObject draftTicketRequest) throws IOException, ParseException {
		// To create a DataObject, use the creation methods on the BOFactory:
		// com.ibm.websphere.bo.BOFactory boFactory = (com.ibm.websphere.bo.BOFactory) ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");
		//
		// To get or set attributes for a DataObject such as draftTicketRequest, use the APIs as shown below:
		// To set a string attribute in draftTicketRequest, use draftTicketRequest.setString(stringAttributeName, stringValue)
		// To get a string attribute in draftTicketRequest, use draftTicketRequest.getString(stringAttributeName)
		// To set a dataObject attribute in draftTicketRequest, use draftTicketRequest.setDataObject(stringAttributeName, dataObjectValue)
		// To get a dataObject attribute in draftTicketRequest, use draftTicketRequest.getDataObject(stringAttributeName)
		DataObject negativeEndorsementDraftTicketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_gc_negative_endorsement_draft_ticketbg",
						"DboRas_Gc_Negative_Endorsement_Draft_TicketBG");
		
		DataObject negativeEndoDraftTicketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_gc_negative_endorsement_draft_ticketbg",
						"DboRas_Gc_Negative_Endorsement_Draft_TicketBG");
		int size = 0;
		Boolean exists = false;
		DataObject negativeEndoDraftTicketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_gc_negative_endorsement_draft_ticket",
						"DboRas_Gc_Negative_Endorsement_Draft_Ticket");
		
		
		DataObject negativeEndorsementDraftTicketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_gc_negative_endorsement_draft_ticket",
						"DboRas_Gc_Negative_Endorsement_Draft_Ticket");
		
        DataObject draftTicketOut = getBOFactory().create(
				"http://RASAccess-ApplicationDB/RASGCIntegrationData",
				"NegativeEndorsementDraftTicketOutput");
    	DataObject exceptionData = null;
        int serviceId = convertDraftTicketRequestObjectToXML(draftTicketRequest);
		int draftId =0;
		
		if (draftTicketRequest == null) {
			draftTicketOut.setString("status", "FAILED");
			draftTicketOut.setString("message","RAS Negative Endorsement Draft Ticket Mandatory Validations");
			draftTicketOut.setString("errorCode", "100001");
			draftTicketOut.setString("errorDetail","draftTicketRequest object cannot be blank");
		} else {
			String errorString = "";
			List<String> valErrors = new ArrayList();
			valErrors = validateNegativeEndorsementDraftTicketInput(draftTicketRequest);
			if (errorString == null || "".equalsIgnoreCase(errorString))
			{
			if ((valErrors != null) && (!valErrors.isEmpty())) {
				for (String error : valErrors) {
					if (errorString == "") {
						errorString = errorString + error;
					} else
						errorString = errorString + " ," + error;
				}
				
				draftTicketOut.setString("status", "FAILED");
				draftTicketOut.setString("message",
						"RAS Negative Endorsement Draft Ticket Mandatory Validations");
				draftTicketOut.setString("errorCode", "100001");
				draftTicketOut.setString("errorDetail",
						errorString);
				
				negativeEndorsementDraftTicketData.setString("status", "FAILED");
				negativeEndorsementDraftTicketData.setString("error_code", "100001");
				negativeEndorsementDraftTicketData.setString("error_details", errorString);
				negativeEndorsementDraftTicketData.setString("gc_error", "");

			}
			}
			if (errorString == null || "".equalsIgnoreCase(errorString)) {
				draftId = incrementSequence("NEGATIVE ENDORSEMENT DRAFT ID SEQUENCE");
				System.out.println("Negative Endorsement Draft ID "+draftId);
				negativeEndorsementDraftTicketData.setInt("ras_gc_negative_endorsement_draft_ticket_id", draftId);
				negativeEndorsementDraftTicketData.setInt("ras_gc_negative_endorsement_service_logging_id", serviceId);
				negativeEndorsementDraftTicketData.setString("user_id",draftTicketRequest.getString("userId"));
				negativeEndorsementDraftTicketData.setString("lob",draftTicketRequest.getString("lob"));
				negativeEndorsementDraftTicketData.setString("policy_number", draftTicketRequest.getString("policyNumber"));
				negativeEndorsementDraftTicketData.setString("product_code",draftTicketRequest.getString("productCode"));
				negativeEndorsementDraftTicketData.setString("product_name",draftTicketRequest.getString("productName"));
				
				negativeEndorsementDraftTicketData.setString("endorsement_proposal_number",draftTicketRequest.getString("endorsementProposalNumber"));
				
				negativeEndorsementDraftTicketData.setDate("request_date", formatStringDate(draftTicketRequest.getString("requestDate")));
				
				double refundAmount = Double.parseDouble(draftTicketRequest.getString("refundAmount"));
				BigDecimal refundAmountDecimal = BigDecimal.valueOf(refundAmount);
				negativeEndorsementDraftTicketData.setBigDecimal("refund_amount",refundAmountDecimal);
				
				negativeEndorsementDraftTicketData.setString("proposer_name", draftTicketRequest.getString("proposerName"));
				negativeEndorsementDraftTicketData.setString("customer_name",draftTicketRequest.getString("customerName"));
				negativeEndorsementDraftTicketData.setString("policy_status",draftTicketRequest.getString("policyStatus"));
				
				double policyPremium = Double.parseDouble(draftTicketRequest.getString("policyPremium"));
				BigDecimal policyPremiumDecimal = BigDecimal.valueOf(policyPremium);
				negativeEndorsementDraftTicketData.setBigDecimal("policy_premium",policyPremiumDecimal);
				
				negativeEndorsementDraftTicketData.setDate("application_date", formatStringDate(draftTicketRequest.getString("applicationDate")));
				negativeEndorsementDraftTicketData.setDate("policy_start_date", formatStringDate(draftTicketRequest.getString("policyStartDate")));
				negativeEndorsementDraftTicketData.setDate("policy_end_date", formatStringDate(draftTicketRequest.getString("policyEndDate")));
				negativeEndorsementDraftTicketData.setDate("conversion_date", formatStringDate(draftTicketRequest.getString("conversionDate")));
				negativeEndorsementDraftTicketData.setString("policy_cover_type",draftTicketRequest.getString("policyCoverType"));
				negativeEndorsementDraftTicketData.setString("customer_code",draftTicketRequest.getString("customerCode"));
				negativeEndorsementDraftTicketData.setString("aadhaar_number",draftTicketRequest.getString("aadhaarNumber"));
				negativeEndorsementDraftTicketData.setString("lan_number",draftTicketRequest.getString("lanNumber"));
				negativeEndorsementDraftTicketData.setString("certificate_number", draftTicketRequest.getString("certificateNumber"));
				negativeEndorsementDraftTicketData.setString("engine_number",draftTicketRequest.getString("engineNumber"));
				negativeEndorsementDraftTicketData.setString("chasis_number",draftTicketRequest.getString("chasisNumber"));
				negativeEndorsementDraftTicketData.setString("registration_number",draftTicketRequest.getString("registrationNumber"));
				negativeEndorsementDraftTicketData.setString("producer_name",draftTicketRequest.getString("producerName"));
				negativeEndorsementDraftTicketData.setString("producer_code",draftTicketRequest.getString("producerCode"));
				negativeEndorsementDraftTicketData.setString("dealer_name", draftTicketRequest.getString("dealerName"));
				negativeEndorsementDraftTicketData.setString("dealer_code",draftTicketRequest.getString("dealerCode"));
				negativeEndorsementDraftTicketData.setString("financier_name",draftTicketRequest.getString("financierName"));
				negativeEndorsementDraftTicketData.setString("financier_code", draftTicketRequest.getString("financierCode"));
				negativeEndorsementDraftTicketData.setString("crs_number",draftTicketRequest.getString("crsNumber"));
				negativeEndorsementDraftTicketData.setDate("created_on", formatDate(new Date()));
				negativeEndorsementDraftTicketData.setString("created_by",draftTicketRequest.getString("userId"));
                System.out.println("before try");
				try {
				if (errorString == null || "".equalsIgnoreCase(errorString)) {
				draftTicketOut.setString("status", "SUCCESS");
				draftTicketOut.setString("message","Success Response");
				draftTicketOut.setString("policyNumber", draftTicketRequest.getString("policyNumber"));
				draftTicketOut.setString("endorsementProposalNumber",draftTicketRequest.getString("endorsementProposalNumber"));
				
				negativeEndoDraftTicketData.setString("endorsement_proposal_number", draftTicketRequest.getString("endorsementProposalNumber"));
				negativeEndoDraftTicketDataBG.setDataObject("DboRas_Gc_Negative_Endorsement_Draft_Ticket", negativeEndoDraftTicketData);
				DataObject negativeResult = null ;
				try{
				 negativeResult = (DataObject) this.locateService_DBInterfacePartner().invoke("retrieveallDboRas_Gc_Negative_Endorsement_Draft_TicketBG",negativeEndoDraftTicketDataBG);
				//System.out.println("Negative endorsement Result --> "+negativeResult.getDataObject(0));
                
				size = negativeResult.getList(
						"retrieveallDboRas_Gc_Negative_Endorsement_Draft_TicketBGOutput/DboRas_Gc_Negative_Endorsement_Draft_TicketBG").size();
				System.out.println("size --> "+size);
				}
				catch(Exception e){
				System.out.println("size --> "+size);
				
				}
				String negativeEndorsementdraftTicketNo = "";
				if(size==0){
			    negativeEndorsementdraftTicketNo = getNegativeEndorsementDraftTicketNumber("NEGATIVE ENDORSEMENT DRAFT SERIES");
			    draftTicketOut.setString("draftTicketNumber",negativeEndorsementdraftTicketNo);
				}else{
					negativeEndorsementdraftTicketNo = negativeResult.getString(
							"retrieveallDboRas_Gc_Negative_Endorsement_Draft_TicketBGOutput/DboRas_Gc_Negative_Endorsement_Draft_TicketBG[0]/DboRas_Gc_Negative_Endorsement_Draft_Ticket/draft_ticket_no");
				System.out.println(negativeResult.getString(
						"retrieveallDboRas_Gc_Negative_Endorsement_Draft_TicketBGOutput/DboRas_Gc_Negative_Endorsement_Draft_TicketBG[0]/DboRas_Gc_Negative_Endorsement_Draft_Ticket/draft_ticket_no"));
				System.out.println(negativeResult.getInt(
						"retrieveallDboRas_Gc_Negative_Endorsement_Draft_TicketBGOutput/DboRas_Gc_Negative_Endorsement_Draft_TicketBG[0]/DboRas_Gc_Negative_Endorsement_Draft_Ticket/ras_gc_negative_endorsement_draft_ticket_id"));
					draftTicketOut.setString("draftTicketNumber",negativeEndorsementdraftTicketNo);
					negativeEndorsementDraftTicketData.setInt("ras_gc_negative_endorsement_draft_ticket_id", negativeResult.getInt(
							"retrieveallDboRas_Gc_Negative_Endorsement_Draft_TicketBGOutput/DboRas_Gc_Negative_Endorsement_Draft_TicketBG[0]/DboRas_Gc_Negative_Endorsement_Draft_Ticket/ras_gc_negative_endorsement_draft_ticket_id"));
					negativeEndorsementDraftTicketData.setDate("last_modified_on",formatDate(new Date()));
					negativeEndorsementDraftTicketData.setString("last_modified_by",draftTicketRequest.getString("userId"));
				
				}
				
				draftTicketOut.setString("errorCode", "");
				draftTicketOut.setString("errorDetail","");
				negativeEndorsementDraftTicketData.setString("status",draftTicketOut.getString("status"));
				negativeEndorsementDraftTicketData.setString("error_code",draftTicketOut.getString("errorCode"));
				negativeEndorsementDraftTicketData.setString("error_details",draftTicketOut.getString("errorDetail"));
				negativeEndorsementDraftTicketData.setString("draft_ticket_no",negativeEndorsementdraftTicketNo);
				
				negativeEndorsementDraftTicketDataBG.set("DboRas_Gc_Negative_Endorsement_Draft_Ticket",negativeEndorsementDraftTicketData);
				
				
				if(size==0)
				    this.locateService_DBInterfacePartner().invoke("createDboRas_Gc_Negative_Endorsement_Draft_TicketBG",negativeEndorsementDraftTicketDataBG);
				else
					this.locateService_DBInterfacePartner().invoke("updateDboRas_Gc_Negative_Endorsement_Draft_TicketBG",negativeEndorsementDraftTicketDataBG);	
				
				
				//CreateRASNegativeEndorsementDraftTicketBPEL partner createRASNegativeEndorsementDraftTicket operation invoke asynchronously by passing draftTicketNo as input
				if(draftTicketOut.getString("draftTicketNumber")!=null && !"".equalsIgnoreCase(draftTicketOut.getString("draftTicketNumber")))
				this.locateService_NegativeEndorsementDraftTicketPartner().invokeAsync("createRASNegativeEndorsementDraftTicket", draftTicketOut.getString("draftTicketNumber"));
				}
			}
				catch (ServiceBusinessException sbe) {
					exceptionData = (DataObject) sbe.getData();
					errorString = exceptionData.get("message").toString();

				} catch (ServiceRuntimeException sre) {
					errorString = sre.getMessage().toString();

				} catch (Exception e) {
					errorString =e.getMessage();
				}
		}
		}
		convertdraftTicketResponseObjectToXML(draftTicketOut,serviceId,draftTicketRequest.getString("userId"));
		return draftTicketOut;	
	}

	/**
	 * Method generated to support implementation of operation "findNegativeEndorsementDraftTicketDetails" defined for WSDL port type 
	 * named "RASGCIntegrationService".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a parameter 
	 * type conveys that it is a complex type. Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public DataObject findNegativeEndorsementDraftTicketDetails(
			String draftTicketNo) {
		//TODO Needs to be implemented.
		DataObject draftTicketResponseBO = null;
		DataObject rasException = getBOFactory().create("http://RACASBO",
				"RASException");
		DataObject exceptionData = null;
		DataObject ticketBO = getBOFactory().create("http://RACASBO",
				"TicketBO");
		DataObject draftTicketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementdraftticketbg",
						"NegativeEndorsementDraftTicketBG");

		DataObject draftTicketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementdraftticket",
						"NegativeEndorsementDraftTicket");
		List draftTicketDataList =null;
		String policyType = "";

		if (draftTicketNo != null && !("".equalsIgnoreCase(draftTicketNo.trim()))) {
			draftTicketData.setString("parameter1", draftTicketNo);

		} else {
			throw new RuntimeException("Draft Ticket Number cannot be blank or empty");
		}
		draftTicketDataBG.setDataObject("NegativeEndorsementDraftTicket", draftTicketData);
		// invoke service

		try {
			
			draftTicketResponseBO = (DataObject) locateService_DBInterfacePartner()
					.invoke("retrieveallNegativeEndorsementDraftTicketBG", draftTicketDataBG);
			System.out.println("EXECUTED");
			System.out.print("source"+draftTicketResponseBO
			.getString("retrieveallNegativeEndorsementDraftTicketBGOutput/NegativeEndorsementDraftTicket[0]"));
			
			draftTicketDataList =draftTicketResponseBO
			.getList("retrieveallNegativeEndorsementDraftTicketBGOutput/NegativeEndorsementDraftTicket");
			draftTicketData = (DataObject) draftTicketDataList.get(0);
	       
			try{
				DataObject negativeEndorsementMasterData = getBOFactory()
							.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementmaster",
									"NegativeEndorsementMaster");
			    DataObject negativeEndorsementMasterDataBG = getBOFactory()
							.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementmasterbg",
									"NegativeEndorsementMasterBG");
			    negativeEndorsementMasterData.setString("parameter1", draftTicketData.getString("lob"));	
			    negativeEndorsementMasterData.setString("parameter2", draftTicketData.getString("product_code"));	
			    negativeEndorsementMasterData.setString("parameter3", draftTicketData.getString("product_name"));	
			    negativeEndorsementMasterDataBG.set("NegativeEndorsementMaster", negativeEndorsementMasterData);
			    DataObject negativeEndorsementMasterDataResponseBO = (DataObject) this.locateService_DBInterfacePartner().invoke("retrieveallNegativeEndorsementMasterBG",negativeEndorsementMasterDataBG);
			    policyType = negativeEndorsementMasterDataResponseBO.getString("retrieveallNegativeEndorsementMasterBGOutput/NegativeEndorsementMaster[0]/policy_type");
				System.out.println("Policy Type from master : "+policyType);
				}
			catch (ServiceBusinessException sbe) {

				exceptionData = (DataObject) sbe.getData();
				rasException.setString("status", "FAILED");
				rasException.setString("error[0]/errorCode", "100002");
				rasException.setString("error[0]/message",
						exceptionData.get("message").toString()+" error while fetching negative endorsement policy type and refund mode from master");
				throw new ServiceBusinessException(rasException);

			} catch (ServiceRuntimeException sre) {
				throw new ServiceRuntimeException(sre.getMessage()+" error while fetching negative endorsement policy type and refund mode from master", sre);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage()+" error while fetching negative endorsement policy type and refund mode from master", e);
			}
			
			ticketBO.setString("refundType",Constants.NegativeEndorsementRefund.getValue());
			ticketBO.setString("lob", draftTicketData.getString("lob"));
			ticketBO.setString("policyNo", draftTicketData.getString("policy_number"));
			ticketBO.setString("applicationNo", draftTicketData.getString("endorsement_proposal_number"));
			ticketBO.setString("ticketType", Constants.IndividualTicket.getValue());
			ticketBO.setString("stage", "BOPS");
			ticketBO.setString("status", "New Ticket Raised");
			ticketBO.setString("raisedBy", draftTicketData.getString("user_id"));
			ticketBO.setString("actionBy", draftTicketData.getString("user_id"));
			ticketBO.setString("product", draftTicketData.getString("product_code"));
			ticketBO.setString("productName", draftTicketData.getString("product_name"));
			ticketBO.setString("source",Constants.GC.getValue());
			ticketBO.setString("subReceipt", "N");
			ticketBO.setBoolean("uploadedTo",false);
			ticketBO.setString("ticketRemarks", "Negative endorsement auto generated draft ticket");
			ticketBO.setDate("requestedDate", draftTicketData.getDate("request_date"));
			ticketBO.setDouble("refundAmount", draftTicketData.getDouble("refund_amount"));
			ticketBO.setString("rasDraftTicketNo", draftTicketData.getString("draft_ticket_no"));
			ticketBO.setString("draftId", draftTicketData.getString("draft_ticket_no"));
			if(policyType!=null &&!"".equalsIgnoreCase(policyType))
			ticketBO.setString("policyDetail/policyType", policyType);
			ticketBO.setString("policyDetail/lob", draftTicketData.getString("lob"));
			ticketBO.setString("policyDetail/policyNo", draftTicketData.getString("policy_number"));
			ticketBO.setString("policyDetail/proposalNo", draftTicketData.getString("endorsement_proposal_number"));
			ticketBO.setString("policyDetail/product", draftTicketData.getString("product_code"));
			ticketBO.setString("policyDetail/proposerName", draftTicketData.getString("proposer_name"));
			ticketBO.setString("accountDetail/customerName", draftTicketData.getString("customer_name"));
			ticketBO.setString("policyDetail/policyStatus", draftTicketData.getString("policy_status"));
			ticketBO.setDouble("policyDetail/policyPremium", draftTicketData.getDouble("policy_premium"));
			ticketBO.setDate("policyDetail/applicationDate", draftTicketData.getDate("application_date"));
			ticketBO.setDate("policyDetail/policyStartDate", draftTicketData.getDate("policy_start_date"));
			ticketBO.setDate("policyDetail/policyEndDate", draftTicketData.getDate("policy_end_date"));
			ticketBO.setDate("policyDetail/conversionDate", draftTicketData.getDate("conversion_date"));
			ticketBO.setString("policyDetail/policyCoverType", draftTicketData.getString("policy_cover_type"));
			ticketBO.setString("policyDetail/customerCode", draftTicketData.getString("customer_code"));
			ticketBO.setString("policyDetail/aadharNo", draftTicketData.getString("aadhaar_number"));
			ticketBO.setString("policyDetail/lanNumber",  draftTicketData.getString("lan_number"));
			ticketBO.setString("policyDetail/certificateNo",  draftTicketData.getString("certificate_number"));
			ticketBO.setString("policyDetail/engineNumber",  draftTicketData.getString("engine_number"));
			ticketBO.setString("policyDetail/chasisNumber",  draftTicketData.getString("chasis_number"));
			ticketBO.setString("policyDetail/registrationNo",  draftTicketData.getString("registration_number"));
			ticketBO.setString("policyDetail/producerName",  draftTicketData.getString("producer_name"));
			ticketBO.setString("policyDetail/producerCode",  draftTicketData.getString("producer_code"));
			ticketBO.setString("policyDetail/dealerName",  draftTicketData.getString("dealer_name"));
			ticketBO.setString("policyDetail/dealerCode",  draftTicketData.getString("dealer_code"));
			ticketBO.setString("policyDetail/financierName",  draftTicketData.getString("financier_name"));
			ticketBO.setString("policyDetail/financierCode",  draftTicketData.getString("financier_code"));
			ticketBO.setString("receiptDetail[0]/crsNo",  draftTicketData.getString("crs_number"));	
			ticketBO.setString("policyDetail/actionBy",  draftTicketData.getString("user_id"));
		}
	
		catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();
			rasException.setString("status", "FAILED");
			rasException.setString("error[0]/errorCode", "100002");
			rasException.setString("error[0]/message",
					exceptionData.get("message").toString()+" error while fetching negative endorsement draft ticket details from draft ticket no");
			throw new ServiceBusinessException(rasException);

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage()+" error while fetching negative endorsement draft ticket details from draft ticket no", sre);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage()+" error while fetching negative endorsement draft ticket details from draft ticket no", e);
		}
		return ticketBO;
	}

	/**
	 * Method generated to support implementation of operation "findNegativeEndorsementDraftDocumentDetails" defined for WSDL port type 
	 * named "RASGCIntegrationService".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a parameter 
	 * type conveys that it is a complex type. Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public DataObject findNegativeEndorsementDraftDocumentDetails(String lob,
			String refundType, String draftTicketNo) {
		//TODO Needs to be implemented.

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
		if (lob == null || "".equalsIgnoreCase(lob)) {
			throw new RuntimeException("LOB cannot be Empty");
		}
		if (refundType == null || "".equalsIgnoreCase(refundType)) {
			throw new RuntimeException("refundType cannot be Empty");
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
				docs.setString("isMandatory",
						responseBO.getString(xpathString + "/is_mandatory"));
				if (responseBO.get(xpathString + "/docid") != null) {
					docs.setDouble("docId",
							responseBO.getDouble(xpathString + "/docid"));
				}
				if (responseBO.get(xpathString + "/ticket_id") != null) {
					docs.setDouble("ticketId",
							responseBO.getDouble(xpathString + "/ticket_id"));
				}
				if(responseBO.get(xpathString + "/is_submitted")==null || "".equals(responseBO.get(xpathString + "/is_submitted")))
				{
					docs.setBoolean("isSubmitted",false);
				
					docs.setInt("dmsFlag", 0);
				}
				else
				{
				/*docs.setBoolean("isSubmitted",
						responseBO.getBoolean(xpathString + "/is_submitted"));  */
					
					docs.setBoolean("isSubmitted",
							true); 
				docs.setInt("dmsFlag",
						responseBO.getInt(xpathString + "/dms_flag"));
				
				}
				/*docs.setString("dmsRemarks",
						responseBO.getString(xpathString + "/dms_remarks"));
				docs.setString("dmsUploadedBy",
						responseBO.getString(xpathString + "/dms_uploaded_by"));
				docs.setDate("dmsUploadedDate",
						responseBO.getDate(xpathString + "/dms_uploaded_date"));  */
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
			rasException.setString("error[0]/message", exceptionData.get("message").toString()+" error while fetching negative endorsement document master from refund type and lob");
			throw new ServiceBusinessException(rasException);
			
			 }
		catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage()+" error while fetching negative endorsement document master from refund type and lob",sre);
			 }
		catch (Exception e) {
			throw new RuntimeException(e.getMessage()+" error while fetching negative endorsement document master from refund type and lob",e);
		     }
	}
	/**
	 * Method generated to support implementation of operation "saveNegativeEndorsementDraftErrorDetails" defined for WSDL port type 
	 * named "RASGCIntegrationService".
	 * 
	 * Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public void saveNegativeEndorsementDraftErrorDetails(String errorCode,
			String errorDetail, String gcError, String draftTicketNo,
			String instanceId) {
		//TODO Needs to be implemented.
		DataObject exceptionData = null;
		DataObject draftTicketResponseBO = null;
		try
		{
		
		DataObject updateDraftBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updatenegativeendorsementdraftticketdetailsbg",
						"UpdateNegativeEndorsementDraftTicketDetailsBG");

		DataObject updateDraftInput = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updatenegativeendorsementdraftticketdetails",
						"UpdateNegativeEndorsementDraftTicketDetails");
		
		DataObject draftTicketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementdraftticketbg",
						"NegativeEndorsementDraftTicketBG");

		DataObject draftTicketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/negativeendorsementdraftticket",
						"NegativeEndorsementDraftTicket");
		
		if (draftTicketNo != null && !("".equalsIgnoreCase(draftTicketNo.trim())))
			draftTicketData.setString("parameter1", draftTicketNo);
		else 
		throw new RuntimeException("Draft Ticket Number cannot be blank or empty");
		
		draftTicketDataBG.setDataObject("NegativeEndorsementDraftTicket", draftTicketData);
		// invoke retrieve negative endorsement draft ticket details service
		draftTicketResponseBO= (DataObject) this.locateService_DBInterfacePartner().invoke("retrieveallNegativeEndorsementDraftTicketBG", draftTicketDataBG);
		String userId = draftTicketResponseBO.getString("retrieveallNegativeEndorsementDraftTicketBGOutput/NegativeEndorsementDraftTicket[0]/user_id");
        updateDraftInput.setString("statement1parameter8", draftTicketNo);
		updateDraftInput.setString("statement1parameter2", errorCode);
		updateDraftInput.setString("statement1parameter3", errorDetail);
		updateDraftInput.setString("statement1parameter4", gcError);
		updateDraftInput.setString("statement1parameter5", instanceId);
	    if (instanceId != null && !("".equalsIgnoreCase(instanceId.trim())))
	    {
		updateDraftInput.setString("statement1parameter1", "SUCCESS");
		updateDraftInput.setString("statement1parameter2", "");
		updateDraftInput.setString("statement1parameter3", "");
		updateDraftInput.setString("statement1parameter4", "");
	    }
		else 
		updateDraftInput.setString("statement1parameter1", "FAILED");
	    
		updateDraftInput.setDate("statement1parameter6", formatDate(new Date()));
		updateDraftInput.setString("statement1parameter7", userId);
		updateDraftBG.setDataObject("UpdateNegativeEndorsementDraftTicketDetails",updateDraftInput);
		this.locateService_DBInterfacePartner().invoke("executeUpdateNegativeEndorsementDraftTicketDetailsBG", updateDraftBG);
	}
		catch (ServiceBusinessException sbe) {
			exceptionData  = (DataObject)sbe.getData();
			throw new ServiceBusinessException(exceptionData.get("message").toString()+" error while updating negative endorsement draft ticket error details and instance id");
			
			 }
		catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage()+" error while updating negative endorsement draft ticket error details and instance id",sre);
			 }
		catch (Exception e) {
			throw new RuntimeException(e.getMessage()+" error while updating negative endorsement draft ticket error details and instance id",e);
		     }
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
	 * for the operation "DBInterface#applychangesDboRas_TicketBG(DataObject applychangesDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_TicketBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Policy_DetailBG(DataObject applychangesDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Policy_DetailBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_RefundBG(DataObject applychangesDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_RefundBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Receipt_DetailBG(DataObject applychangesDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Receipt_DetailBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_InsuredBG(DataObject applychangesDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_InsuredBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Account_Detail_HistBG(DataObject applychangesDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Account_Detail_HistBGResponse(
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
	 * for the operation "DBInterface#applychangesDboRas_App_ConfigBG(DataObject applychangesDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_App_ConfigBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_ApproverBG(DataObject applychangesDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_ApproverBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Cancellation_OptionBG(DataObject applychangesDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Cancellation_OptionBGResponse(
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
	 * for the operation "DBInterface#applychangesDboRas_Cancellation_ReasonBG(DataObject applychangesDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Cancellation_ReasonBGResponse(
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
	 * for the operation "DBInterface#applychangesDboRas_Document_MasterBG(DataObject applychangesDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Document_MasterBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Exception_HistBG(DataObject applychangesDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Exception_HistBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_JobsBG(DataObject applychangesDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_JobsBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_LobBG(DataObject applychangesDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_LobBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_StatusBG(DataObject applychangesDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_StatusBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Ticket_DocumentsBG(DataObject applychangesDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Ticket_DocumentsBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
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
	 * for the operation "DBInterface#applychangesDboRas_Ticket_HistBG(DataObject applychangesDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Ticket_HistBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_ProductBG(DataObject applychangesDboRasProductBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_ProductBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Account_DetailBG(DataObject applychangesDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Account_DetailBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Account_MasterBG(DataObject applychangesDboRasAccountMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Account_MasterBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Bulk_File_LogBG(DataObject applychangesDboRasBulkFileLogBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Bulk_File_LogBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Holiday_ScheduleBG(DataObject applychangesDboRasHolidayScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Holiday_ScheduleBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
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
	 * for the operation "DBInterface#applychangesDboRas_Refund_HistBG(DataObject applychangesDboRasRefundHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Refund_HistBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Report_AccessBG(DataObject applychangesDboRasReportAccessBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Report_AccessBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Uploaded_FileBG(DataObject applychangesDboRasUploadedFileBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Uploaded_FileBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_User_DetailsBG(DataObject applychangesDboRasUserDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_User_DetailsBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Valid_Proposal_StatusBG(DataObject applychangesDboRasValidProposalStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Valid_Proposal_StatusBGResponse(
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
	 * for the operation "DBInterface#applychangesDboRas_Work_ScheduleBG(DataObject applychangesDboRasWorkScheduleBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Work_ScheduleBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Approval_GridBG(DataObject applychangesDboRasApprovalGridBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Approval_GridBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Bank_DetailsBG(DataObject applychangesDboRasBankDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Bank_DetailsBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Icrm_Service_ResponseBG(DataObject applychangesDboRasIcrmServiceResponseBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Icrm_Service_ResponseBGResponse(
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
	 * for the operation "DBInterface#applychangesDboRas_Excess_Refund_ServiceBG(DataObject applychangesDboRasExcessRefundServiceBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Excess_Refund_ServiceBGResponse(
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
	 * for the operation "DBInterface#applychangesDboRas_Reverse_Feed_Status_MasterBG(DataObject applychangesDboRasReverseFeedStatusMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Reverse_Feed_Status_MasterBGResponse(
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
	 * for the operation "DBInterface#applychangesDboRas_Bops_Channel_Ntid_ConfigBG(DataObject applychangesDboRasBopsChannelNtidConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Bops_Channel_Ntid_ConfigBGResponse(
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
	 * for the operation "DBInterface#applychangesDboRas_Excess_Refund_DraftBG(DataObject applychangesDboRasExcessRefundDraftBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Excess_Refund_DraftBGResponse(
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
	 * for the operation "DBInterface#applychangesDboRas_Excess_Refund_Reverse_FeedBG(DataObject applychangesDboRasExcessRefundReverseFeedBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Excess_Refund_Reverse_FeedBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Error_Code_MasterBG(DataObject createDboRasErrorCodeMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Error_Code_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Error_Code_MasterBG(DataObject updateDboRasErrorCodeMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Error_Code_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Error_Code_MasterBG(DataObject updateallDboRasErrorCodeMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Error_Code_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Error_Code_MasterBG(DataObject retrieveDboRasErrorCodeMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Error_Code_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Error_Code_MasterBG(DataObject retrieveallDboRasErrorCodeMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Error_Code_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Error_Code_MasterBG(DataObject existsDboRasErrorCodeMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Error_Code_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Error_Code_MasterBG(DataObject upsertDboRasErrorCodeMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Error_Code_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Error_Code_MasterBG(DataObject batchcreateDboRasErrorCodeMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Error_Code_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Error_Code_MasterBG(DataObject batchupdateDboRasErrorCodeMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Error_Code_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Error_Code_MasterBG(DataObject applychangesDboRasErrorCodeMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Error_Code_MasterBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Gc_TransactionBG(DataObject createDboRasGcTransactionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Gc_TransactionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Gc_TransactionBG(DataObject updateDboRasGcTransactionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Gc_TransactionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Gc_TransactionBG(DataObject updateallDboRasGcTransactionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Gc_TransactionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Gc_TransactionBG(DataObject retrieveDboRasGcTransactionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Gc_TransactionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Gc_TransactionBG(DataObject retrieveallDboRasGcTransactionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Gc_TransactionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Gc_TransactionBG(DataObject existsDboRasGcTransactionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Gc_TransactionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Gc_TransactionBG(DataObject upsertDboRasGcTransactionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Gc_TransactionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Gc_TransactionBG(DataObject batchcreateDboRasGcTransactionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Gc_TransactionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Gc_TransactionBG(DataObject batchupdateDboRasGcTransactionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Gc_TransactionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Gc_TransactionBG(DataObject applychangesDboRasGcTransactionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Gc_TransactionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Scheduler_StatusBG(DataObject createDboRasSchedulerStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Scheduler_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Scheduler_StatusBG(DataObject updateDboRasSchedulerStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Scheduler_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Scheduler_StatusBG(DataObject updateallDboRasSchedulerStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Scheduler_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Scheduler_StatusBG(DataObject retrieveDboRasSchedulerStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Scheduler_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Scheduler_StatusBG(DataObject retrieveallDboRasSchedulerStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Scheduler_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Scheduler_StatusBG(DataObject existsDboRasSchedulerStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Scheduler_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Scheduler_StatusBG(DataObject upsertDboRasSchedulerStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Scheduler_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Scheduler_StatusBG(DataObject batchcreateDboRasSchedulerStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Scheduler_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Scheduler_StatusBG(DataObject batchupdateDboRasSchedulerStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Scheduler_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Scheduler_StatusBG(DataObject applychangesDboRasSchedulerStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Scheduler_StatusBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallAppConfigBG(DataObject retrieveallAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallAppConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallBopsChannelNtidConfigBG(DataObject retrieveallBopsChannelNtidConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallBopsChannelNtidConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallProductBG(DataObject retrieveallProductBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallProductBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallReverseFeedBG(DataObject retrieveallReverseFeedBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallReverseFeedBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallReverseFeedStatusBG(DataObject retrieveallReverseFeedStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallReverseFeedStatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallBankDetailsBG(DataObject retrieveallBankDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallBankDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDraftTicketBG(DataObject retrieveallDraftTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDraftTicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDraftTicketIdBG(DataObject retrieveallDraftTicketIdBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDraftTicketIdBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDraftIdBG(DataObject retrieveallDraftIdBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDraftIdBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallServiceIdBG(DataObject retrieveallServiceIdBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallServiceIdBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDraftNoBG(DataObject retrieveallDraftNoBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDraftNoBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallReverseFeedIdBG(DataObject retrieveallReverseFeedIdBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallReverseFeedIdBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallGCTransactionIdBG(DataObject retrieveallGCTransactionIdBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallGCTransactionIdBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallGCTransactionDetailsBG(DataObject retrieveallGCTransactionDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallGCTransactionDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallLobBG(DataObject retrieveallLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallLobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallCancellationOptionBG(DataObject retrieveallCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallCancellationOptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallCancellationReasonBG(DataObject retrieveallCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallCancellationReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallTicketDetailsBG(DataObject retrieveallTicketDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallTicketDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallPolicyDetailsBG(DataObject retrieveallPolicyDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallPolicyDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallAccountDetailsBG(DataObject retrieveallAccountDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallAccountDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallRefundDetailsBG(DataObject retrieveallRefundDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallRefundDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallReceiptDetailsBG(DataObject retrieveallReceiptDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallReceiptDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallInsuredDetailsBG(DataObject retrieveallInsuredDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallInsuredDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallTicketDetailsPolicyNoBG(DataObject retrieveallTicketDetailsPolicyNoBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallTicketDetailsPolicyNoBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallTicketDetailsReceiptNoBG(DataObject retrieveallTicketDetailsReceiptNoBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallTicketDetailsReceiptNoBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallTicketDetailsPolicyNoApplicationNoBG(DataObject retrieveallTicketDetailsPolicyNoApplicationNoBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallTicketDetailsPolicyNoApplicationNoBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallPolicyDetailsPolicyNoProposalNoBG(DataObject retrieveallPolicyDetailsPolicyNoProposalNoBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallPolicyDetailsPolicyNoProposalNoBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallPolicyDetailsPolicyNoCertificateNoBG(DataObject retrieveallPolicyDetailsPolicyNoCertificateNoBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallPolicyDetailsPolicyNoCertificateNoBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallPolicyDetailsPolicyNoLanNoBG(DataObject retrieveallPolicyDetailsPolicyNoLanNoBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallPolicyDetailsPolicyNoLanNoBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallCCMRequestIDBG(DataObject retrieveallCCMRequestIDBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallCCMRequestIDBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Gc_Negative_Endorsement_Service_LoggingBG(DataObject createDboRasGcNegativeEndorsementServiceLoggingBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Gc_Negative_Endorsement_Service_LoggingBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Gc_Negative_Endorsement_Service_LoggingBG(DataObject updateDboRasGcNegativeEndorsementServiceLoggingBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Gc_Negative_Endorsement_Service_LoggingBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Gc_Negative_Endorsement_Service_LoggingBG(DataObject updateallDboRasGcNegativeEndorsementServiceLoggingBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Gc_Negative_Endorsement_Service_LoggingBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Gc_Negative_Endorsement_Service_LoggingBG(DataObject retrieveDboRasGcNegativeEndorsementServiceLoggingBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Gc_Negative_Endorsement_Service_LoggingBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Gc_Negative_Endorsement_Service_LoggingBG(DataObject retrieveallDboRasGcNegativeEndorsementServiceLoggingBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Gc_Negative_Endorsement_Service_LoggingBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Gc_Negative_Endorsement_Service_LoggingBG(DataObject existsDboRasGcNegativeEndorsementServiceLoggingBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Gc_Negative_Endorsement_Service_LoggingBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Gc_Negative_Endorsement_Service_LoggingBG(DataObject upsertDboRasGcNegativeEndorsementServiceLoggingBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Gc_Negative_Endorsement_Service_LoggingBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Gc_Negative_Endorsement_Service_LoggingBG(DataObject batchcreateDboRasGcNegativeEndorsementServiceLoggingBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Gc_Negative_Endorsement_Service_LoggingBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Gc_Negative_Endorsement_Service_LoggingBG(DataObject batchupdateDboRasGcNegativeEndorsementServiceLoggingBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Gc_Negative_Endorsement_Service_LoggingBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Gc_Negative_Endorsement_Service_LoggingBG(DataObject applychangesDboRasGcNegativeEndorsementServiceLoggingBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Gc_Negative_Endorsement_Service_LoggingBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#createDboRas_Gc_Negative_Endorsement_Draft_TicketBG(DataObject createDboRasGcNegativeEndorsementDraftTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onCreateDboRas_Gc_Negative_Endorsement_Draft_TicketBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateDboRas_Gc_Negative_Endorsement_Draft_TicketBG(DataObject updateDboRasGcNegativeEndorsementDraftTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateDboRas_Gc_Negative_Endorsement_Draft_TicketBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#updateallDboRas_Gc_Negative_Endorsement_Draft_TicketBG(DataObject updateallDboRasGcNegativeEndorsementDraftTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpdateallDboRas_Gc_Negative_Endorsement_Draft_TicketBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveDboRas_Gc_Negative_Endorsement_Draft_TicketBG(DataObject retrieveDboRasGcNegativeEndorsementDraftTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveDboRas_Gc_Negative_Endorsement_Draft_TicketBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallDboRas_Gc_Negative_Endorsement_Draft_TicketBG(DataObject retrieveallDboRasGcNegativeEndorsementDraftTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallDboRas_Gc_Negative_Endorsement_Draft_TicketBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#existsDboRas_Gc_Negative_Endorsement_Draft_TicketBG(DataObject existsDboRasGcNegativeEndorsementDraftTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExistsDboRas_Gc_Negative_Endorsement_Draft_TicketBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#upsertDboRas_Gc_Negative_Endorsement_Draft_TicketBG(DataObject upsertDboRasGcNegativeEndorsementDraftTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onUpsertDboRas_Gc_Negative_Endorsement_Draft_TicketBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchcreateDboRas_Gc_Negative_Endorsement_Draft_TicketBG(DataObject batchcreateDboRasGcNegativeEndorsementDraftTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchcreateDboRas_Gc_Negative_Endorsement_Draft_TicketBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#batchupdateDboRas_Gc_Negative_Endorsement_Draft_TicketBG(DataObject batchupdateDboRasGcNegativeEndorsementDraftTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onBatchupdateDboRas_Gc_Negative_Endorsement_Draft_TicketBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#applychangesDboRas_Gc_Negative_Endorsement_Draft_TicketBG(DataObject applychangesDboRasGcNegativeEndorsementDraftTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Gc_Negative_Endorsement_Draft_TicketBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallNegativeEndorsementDraftIDBG(DataObject retrieveallNegativeEndorsementDraftIDBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallNegativeEndorsementDraftIDBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallNegativeEndorsementDraftNoBG(DataObject retrieveallNegativeEndorsementDraftNoBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallNegativeEndorsementDraftNoBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallNegativeEndorsementLoggingIDBG(DataObject retrieveallNegativeEndorsementLoggingIDBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallNegativeEndorsementLoggingIDBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallUserDetailsBG(DataObject retrieveallUserDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallUserDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#retrieveallNegativeEndorsementDraftTicketBG(DataObject retrieveallNegativeEndorsementDraftTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onRetrieveallNegativeEndorsementDraftTicketBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateAppConfigBG(DataObject executeUpdateAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateAppConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateExcessRefundServiceBG(DataObject executeUpdateExcessRefundServiceBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateExcessRefundServiceBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateExcessRefundDraftBG(DataObject executeUpdateExcessRefundDraftBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateExcessRefundDraftBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateExcessRefundReverseFeedBG(DataObject executeUpdateExcessRefundReverseFeedBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateExcessRefundReverseFeedBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateExcessRefundDraftIdBG(DataObject executeUpdateExcessRefundDraftIdBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateExcessRefundDraftIdBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateTransactionCountBG(DataObject executeUpdateTransactionCountBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateTransactionCountBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateDraftErrorDetailsBG(DataObject executeUpdateDraftErrorDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateDraftErrorDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateGCTransactionDetailsBG(DataObject executeUpdateGCTransactionDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateGCTransactionDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateAppCongfigSlnoBG(DataObject executeUpdateAppCongfigSlnoBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateAppCongfigSlnoBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateTicketStatusBG(DataObject executeUpdateTicketStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateTicketStatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateTicketDetailsBG(DataObject executeUpdateTicketDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateTicketDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateInsuredDetailsBG(DataObject executeUpdateInsuredDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateInsuredDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdatePolicyDetailsBG(DataObject executeUpdatePolicyDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdatePolicyDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateReceiptDetailsBG(DataObject executeUpdateReceiptDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateReceiptDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateAccountDetailsBG(DataObject executeUpdateAccountDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateAccountDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateRefundDetailsBG(DataObject executeUpdateRefundDetailsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateRefundDetailsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateTicketDetailStatusBG(DataObject executeUpdateTicketDetailStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateTicketDetailStatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateCCMTransactionDeatilsBG(DataObject executeUpdateCCMTransactionDeatilsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateCCMTransactionDeatilsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#executeUpdateNegativeEndorsementServiceLoggingBG(DataObject executeUpdateNegativeEndorsementServiceLoggingBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onExecuteUpdateNegativeEndorsementServiceLoggingBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "NegativeEndorsementDraftTicket#createRASNegativeEndorsementDraftTicket(String draftTicketNo)"
	 * of wsdl interface "NegativeEndorsementDraftTicket"	
	 */
	public void onCreateRASNegativeEndorsementDraftTicketResponse(
			Ticket __ticket, String returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

}