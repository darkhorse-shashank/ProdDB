package org.aig.ticket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;

import com.ibm.websphere.bo.BOFactory;

import com.ibm.websphere.sca.Service;
import com.ibm.websphere.sca.ServiceBusinessException;
import com.ibm.websphere.sca.ServiceRuntimeException;
import com.ibm.websphere.sca.Ticket;
import commonj.sdo.DataObject;
import com.ibm.websphere.sca.ServiceManager;

public class RetrieveTicketJavaCmptImpl {
	/**
	 * Default constructor.
	 */
	public RetrieveTicketJavaCmptImpl() {
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

	public BOFactory getBOFactory() {
		return (BOFactory) ServiceManager.INSTANCE
				.locateService("com/ibm/websphere/bo/BOFactory");
	}

	/**
	 * Method generated to support implementation of operation "searchPolicy"
	 * defined for WSDL port type named "RetrieveTicket".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a
	 * parameter type conveys that it is a complex type. Please refer to the
	 * WSDL Definition for more information on the type of input, output and
	 * fault(s).
	 */
	/**
	 * Before raising a new ticket we will first check if a ticket has already
	 * been raised for the policy/receipt no or not,
	 * 
	 * first check in RAS transaction table if there is a record with the given
	 * policy/receipt no means a ticket already existing. if no go for second
	 * check second check in GC system: check if the policy/receipt no existing
	 * and in a open status or not, if the policy/receipt does not existing or
	 * already cancelled then throw error. if a ticket has already been raised
	 * for the input policy/receipt no. then, we will throw a Exception Message
	 * saying "A Ticket has already been raised for the policy/receipt no." if
	 * no then, we will return the policy/receipt detail fetched from
	 * 
	 * @param application
	 * @param receiptNo
	 * @param lob
	 * @return
	 */

	public String getUpdatedChequeNumber(String category) {
		System.out.println("Enter Into getUpdatedChequeNumber"+category);

		DataObject appConfigBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_app_configbg",
						"DboRas_App_ConfigBG");

		DataObject appConfigData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_app_config",
						"DboRas_App_Config");
		// RASException Object
		DataObject rasException = getBOFactory().create("http://RACASBO",
				"RASException");
		// Object to store Business Exception Data
		DataObject exceptionData = null;

		if (category == null || "".equalsIgnoreCase(category.trim())) {
			throw new RuntimeException("Category cannot be null or empty");
		}
		try {
			appConfigData.setString("category", category);
			appConfigBG.setDataObject("DboRas_App_Config", appConfigData);
			DataObject response = (DataObject) this
					.locateService_DBInterfacePartner().invoke(
							"retrieveallDboRas_App_ConfigBG", appConfigBG);
			String chequeFormat = response
					.getString("retrieveallDboRas_App_ConfigBGOutput/DboRas_App_ConfigBG[0]/DboRas_App_Config/name");
			Integer lastUpdatedvalue = Integer
					.parseInt(response
							.getString("retrieveallDboRas_App_ConfigBGOutput/DboRas_App_ConfigBG[0]/DboRas_App_Config/value"));
			System.out.println("Format --> " + chequeFormat
					+ " Updated Value --> " + lastUpdatedvalue);
			String chequeNo = "";

			if (category.equalsIgnoreCase("SERIES")) {
				chequeNo = String.format("%0" + chequeFormat.length() + "d",
						lastUpdatedvalue + 1);
			} else if (category.equalsIgnoreCase("DRAFT SERIES")) {
				chequeNo = chequeFormat + String.valueOf(lastUpdatedvalue + 1);
			}

			else {
				chequeNo = String.valueOf(lastUpdatedvalue + 1);
			}
			DataObject updateAppConfigBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_app_configupdateallinputbg",
							"DboRas_App_ConfigUpdateAllInputBG");

			DataObject updateAppConfigInput = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_app_configupdateallinput",
							"DboRas_App_ConfigUpdateAllInput");

			DataObject appConfigDataQuery = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_app_config",
							"DboRas_App_Config");
			if (category.equalsIgnoreCase("SERIES")) {
				appConfigDataQuery.setString("category", "SERIES");
				appConfigData.setString("value", String.valueOf((chequeNo)));
			} else if (category.equalsIgnoreCase("DRAFT SERIES")) {
				appConfigDataQuery.setString("category", "DRAFT SERIES");
				appConfigData.setString("value",
						String.valueOf(lastUpdatedvalue + 1));
			} else {
				appConfigDataQuery.setString("category", "NEFT_SERIES");
				appConfigData.setString("value", String.valueOf((chequeNo)));
			}

			updateAppConfigInput.setDataObject("querysample",
					appConfigDataQuery);
			updateAppConfigInput.setDataObject("valuesample", appConfigData);
			updateAppConfigBG.setDataObject("DboRas_App_ConfigUpdateAllInput",
					updateAppConfigInput);

			this.locateService_DBInterfacePartner().invoke(
					"updateallDboRas_App_ConfigBG", updateAppConfigBG);
			return chequeNo;
		} catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();
			rasException.setString("status", "FAILED");
			rasException.setString("error[0]/message",
					exceptionData.get("message").toString());
			throw new ServiceBusinessException(rasException);

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage().toString(), sre);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e.getMessage().toString(), e);
		}

	}

	public Boolean isTicketExist(String applicationNo, String refundType,
			String searchBy, String groupPolicyNo) {
		System.out.println("Enter Into isTicketExist"+applicationNo);
            boolean isGroupPolicyRejected=true;
		DataObject ticketObject = getBOFactory().create("http://RACASBO",
				"TicketBO");
		// RASException Object
		DataObject rasException = getBOFactory().create("http://RACASBO",
				"RASException");
		// Object to store Business Exception Data
		DataObject exceptionData = null;

		DataObject ticketDataPolicyNoBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/ticketdetailspolicynobg",
						"TicketDetailsPolicyNoBG");
		DataObject ticketDataPolicyNo = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/ticketdetailspolicyno",
						"TicketDetailsPolicyNo");
		
		DataObject ticketDatareceiptNoBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/ticketdetailsreceiptnobg",
						"TicketDetailsReceiptNoBG");
		DataObject ticketDatareceiptNo = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/ticketdetailsreceiptno",
						"TicketDetailsReceiptNo");
		
		DataObject ticketDataPolicyNoApplicationNoBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/ticketdetailspolicynoapplicationnobg",
						"TicketDetailsPolicyNoApplicationNoBG");
		
		DataObject ticketDataPolicyNoApplicationNo = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/ticketdetailspolicynoapplicationno",
						"TicketDetailsPolicyNoApplicationNo");

		DataObject policyDataProposalNo = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/policydetailspolicynoproposalno",
						"PolicyDetailsPolicyNoProposalNo");
		DataObject policyDataProposalNoBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/policydetailspolicynoproposalnobg",
						"PolicyDetailsPolicyNoProposalNoBG");
		
		DataObject policyDataCertificateNo = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/policydetailspolicynocertificateno",
						"PolicyDetailsPolicyNoCertificateNo");
		DataObject policyDataCertificateNoBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/policydetailspolicynocertificatenobg",
						"PolicyDetailsPolicyNoCertificateNoBG");
		
		
		DataObject policyDataLanNo = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/policydetailspolicynolanno",
						"PolicyDetailsPolicyNoLanNo");
		DataObject policyDataLanNoBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/policydetailspolicynolannobg",
						"PolicyDetailsPolicyNoLanNoBG");

		if (applicationNo == null || "".equalsIgnoreCase(applicationNo.trim())) {
			throw new RuntimeException(
					"Please enter either policyno or receiptno, both cannot be empty");

		} else {
			// Call existTicketDetail to check if a record existing with the
			// given receipt and application no
			try {
             System.out.println("inside try");
				// check for duplicate ticket if not group policy
				if (!"GROUP".equalsIgnoreCase(refundType)) {
					// Set the search parameters
					if (applicationNo != null
							&& !"".equalsIgnoreCase(applicationNo.trim())) {
						System.out.println("not group policy");
						if ("CANCELLATION REFUND".equalsIgnoreCase(refundType)
								|| "NEGATIVE ENDORSEMENT REFUND".equalsIgnoreCase(refundType)
								|| "BALANCE REFUND".equalsIgnoreCase(refundType)) {
							String policyNoPattern = "^[a-zA-Z0-9]*$";
							if (!(applicationNo.matches(policyNoPattern))) {
								throw new RuntimeException(
										"Policy number should be alphanumerics only");
							}
							System.out.println("after policy number validation");
							ticketDataPolicyNo.setString("parameter1", applicationNo);
							ticketDataPolicyNoBG.setDataObject("TicketDetailsPolicyNo", ticketDataPolicyNo);
							DataObject response = (DataObject) this
									.locateService_DBInterfacePartner().invoke(
											"retrieveallTicketDetailsPolicyNoBG", ticketDataPolicyNoBG);
							
							for (int i = 1; i <= response.getList(
									"retrieveallTicketDetailsPolicyNoBGOutput/TicketDetailsPolicyNo")
									.size(); i++) {
								String stage = response
										.getString("retrieveallTicketDetailsPolicyNoBGOutput/TicketDetailsPolicyNo["
												+ i + "]/stage");
								String status = response
										.getString("retrieveallTicketDetailsPolicyNoBGOutput/TicketDetailsPolicyNo["
												+ i + "]/status");
								String ticketNo = response
										.getString("retrieveallTicketDetailsPolicyNoBGOutput/TicketDetailsPolicyNo["
												+ i + "]/ticket_no");

								/*if (stage == null || status == null) {
									throw new RuntimeException("Ticket Already Raised for "
											+ applicationNo);
								}*/
								System.out.println("before cops rejected condition");
								if (!(status.equalsIgnoreCase("COPS Rejected") || status.equalsIgnoreCase("QC Rejected") 
										|| status.equalsIgnoreCase("GC Rejected") || 
										(("BALANCE REFUND".equalsIgnoreCase(refundType)|| "CANCELLATION REFUND".equalsIgnoreCase(refundType))
												&& status.equalsIgnoreCase("Completed") && stage.equalsIgnoreCase("Disbursement")) ))
								{
									throw new RuntimeException(
											"Ticket already raised for policy number : "
													+ applicationNo+" with ticket number : "+ticketNo);
								}
							
							
							}
						}

						else {
							String receiptNoPattern = "^[a-zA-Z0-9]*$";
							if (!(applicationNo.matches(receiptNoPattern))) {
								throw new RuntimeException(
										"Receipt number should be alphanumerics only");
							}
							System.out.println("after receipt number validation");
							ticketDatareceiptNo.setString("parameter1", applicationNo);
							ticketDatareceiptNoBG.setDataObject("TicketDetailsReceiptNo", ticketDatareceiptNo);
							DataObject response = (DataObject) this
									.locateService_DBInterfacePartner().invoke(
											"retrieveallTicketDetailsReceiptNoBG", ticketDatareceiptNoBG);
							for (int i = 1; i <= response.getList(
									"retrieveallTicketDetailsReceiptNoBGOutput/TicketDetailsReceiptNo")
									.size(); i++) {
								String stage = response
										.getString("retrieveallTicketDetailsReceiptNoBGOutput/TicketDetailsReceiptNo["
												+ i + "]/stage");
								String status = response
										.getString("retrieveallTicketDetailsReceiptNoBGOutput/TicketDetailsReceiptNo["
												+ i + "]/status");
								String ticketNo = response
										.getString("retrieveallTicketDetailsReceiptNoBGOutput/TicketDetailsReceiptNo["
												+ i + "]/ticket_no");

								/*if (stage == null || status == null) {
									throw new RuntimeException("Ticket Already Raised for "
											+ applicationNo);
								}*/
						
								System.out.println("before receipt number conditions");
									if (!(status.equalsIgnoreCase("COPS Rejected") || status.equalsIgnoreCase("QC Rejected") 
											|| (stage.equalsIgnoreCase("Disbursement") && status.equalsIgnoreCase("Completed")))){
										throw new RuntimeException(
												"Ticket already raised for receipt number : "
														+ applicationNo+" with ticket number : "+ticketNo);
									}
								
							}
						}

					}
					return false;
				} else {
					String searchValuePattern = "^[a-zA-Z0-9]*$";
					if (!(applicationNo.matches(searchValuePattern))) {
						throw new RuntimeException(
								searchBy+" should be alphanumerics only");
					}
					
					if (!(groupPolicyNo.matches(searchValuePattern))) {
						throw new RuntimeException(
								"Policy number should be alphanumerics only");
					}
					String searchType = "";
					String policyNo="";
					// Set the search parameters
					if (searchBy != null
							&& !"".equalsIgnoreCase(searchBy.trim())) {

						if ("PROPOSAL NUMBER".equalsIgnoreCase(searchBy)) {
							policyDataProposalNo.setString("parameter1", groupPolicyNo);
							policyDataProposalNo.setString("parameter2", applicationNo);
							searchType = "proposal no";
							policyDataProposalNoBG.setDataObject("PolicyDetailsPolicyNoProposalNo",
									policyDataProposalNo);
							DataObject policyDataResponse = (DataObject) this
									.locateService_DBInterfacePartner().invoke(
											"retrieveallPolicyDetailsPolicyNoProposalNoBG",
											policyDataProposalNoBG);
							 policyNo = policyDataResponse
									.getString("retrieveallPolicyDetailsPolicyNoProposalNoBGOutput/PolicyDetailsPolicyNoProposalNo[1]/policyno");
						}

						else if ("CERTIFICATE NUMBER"
								.equalsIgnoreCase(searchBy)) {
							policyDataCertificateNo.setString("parameter1", groupPolicyNo);
							policyDataCertificateNo.setString("parameter2", applicationNo);
							searchType = "certificate no";
							policyDataCertificateNoBG.setDataObject("PolicyDetailsPolicyNoCertificateNo",
									policyDataCertificateNo);
							DataObject policyDataResponse = (DataObject) this
									.locateService_DBInterfacePartner().invoke(
											"retrieveallPolicyDetailsPolicyNoCertificateNoBG",
											policyDataCertificateNoBG);
							 policyNo = policyDataResponse
									.getString("retrieveallPolicyDetailsPolicyNoCertificateNoBGOutput/PolicyDetailsPolicyNoCertificateNo[1]/policyno");
						} else {
							policyDataLanNo.setString("parameter1", groupPolicyNo);
							policyDataLanNo.setString("parameter2", applicationNo);
							searchType = "lan no";
							policyDataLanNoBG.setDataObject("PolicyDetailsPolicyNoLanNo",
									policyDataLanNo);
							DataObject policyDataResponse = (DataObject) this
									.locateService_DBInterfacePartner().invoke(
											"retrieveallPolicyDetailsPolicyNoLanNoBG",
											policyDataLanNoBG);
							 policyNo = policyDataResponse
									.getString("retrieveallPolicyDetailsPolicyNoLanNoBGOutput/PolicyDetailsPolicyNoLanNo[1]/policyno");
						}
						ticketDataPolicyNoApplicationNo.setString("parameter2", applicationNo);
						ticketDataPolicyNoApplicationNo.setString("parameter1", groupPolicyNo);
					
					}
					
					ticketDataPolicyNoApplicationNoBG.setDataObject("TicketDetailsPolicyNoApplicationNo", ticketDataPolicyNoApplicationNo);
					DataObject response = (DataObject) this
							.locateService_DBInterfacePartner().invoke(
									"retrieveallTicketDetailsPolicyNoApplicationNoBG", ticketDataPolicyNoApplicationNoBG);
                    String groupTicketNo="";
					for (int i = 1; i <= response.getList(
							"retrieveallTicketDetailsPolicyNoApplicationNoBGOutput/TicketDetailsPolicyNoApplicationNo")
							.size(); i++) {
						String stage = response
								.getString("retrieveallTicketDetailsPolicyNoApplicationNoBGOutput/TicketDetailsPolicyNoApplicationNo["
										+ i + "]/stage");
						String status = response
								.getString("retrieveallTicketDetailsPolicyNoApplicationNoBGOutput/TicketDetailsPolicyNoApplicationNo["
										+ i + "]/status");
						String ticketNo = response
								.getString("retrieveallTicketDetailsPolicyNoApplicationNoBGOutput/TicketDetailsPolicyNoApplicationNo["
										+ i + "]/ticket_no");

						/*if (stage == null || status == null) {
							throw new RuntimeException("Ticket Already Raised for "
									+ applicationNo);
						}*/

						if (!(status.equalsIgnoreCase("COPS Rejected") || status.equalsIgnoreCase("QC Rejected") 
								|| status.equalsIgnoreCase("GC Rejected"))){
							isGroupPolicyRejected=false;
							groupTicketNo = ticketNo;
						}
					}
					
					if(isGroupPolicyRejected==false)
					{
					throw new RuntimeException(
							"Ticket already raised for policy number : "
									+ policyNo + " and " + searchType + " : "
									+ applicationNo+" with ticket number : "+groupTicketNo);
					}
		return false;
				}
			} catch (ServiceBusinessException sbe) {

				exceptionData = (DataObject) sbe.getData();
				rasException.setString("status", "FAILED");
				rasException.setString("error[0]/message",
						exceptionData.get("message").toString());

				if (exceptionData.get("message").toString()
						.equalsIgnoreCase("No matching records found")) {
					return false;
				}

				throw new ServiceBusinessException(rasException);

			} catch (ServiceRuntimeException sre) {
				throw new ServiceRuntimeException(sre.getMessage().toString(),
						sre);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		
		
		
		
		
	}

	/**
	 * Method generated to support implementation of operation
	 * "findTicketDetail" defined for WSDL port type named
	 * "MaintainTicketDetail".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a
	 * parameter type conveys that it is a complex type. Please refer to the
	 * WSDL Definition for more information on the type of input, output and
	 * fault(s).
	 * 
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public DataObject findTicketDetail(String ticketId)
			throws UnsupportedEncodingException, IOException,
			IllegalArgumentException, IllegalAccessException {
		System.out.println("Enter Into findTicketDetail"+ticketId);

		DataObject ticketResponseBO = null;
		DataObject policyResponseBO = null;
		DataObject refundResponseBO = null;
		DataObject accountResponseBO = null;
		DataObject receiptResponseBO = null;
		DataObject insuredResponseBO = null;
		DataObject policyResult = null;
		List<DataObject> insuredResult = new ArrayList();
		List<DataObject> receiptResult = new ArrayList();
		DataObject refundResult = null;
		DataObject accountResult = null;
		DataObject ticketResult = null;
		DataObject rasException = getBOFactory().create("http://RACASBO",
				"RASException");
		DataObject exceptionData = null;
		DataObject ticketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/ticketdetailsbg",
						"TicketDetailsBG");

		DataObject ticketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/ticketdetails",
						"TicketDetails");
		
		DataObject policyDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/policydetailsbg",
						"PolicyDetailsBG");

		DataObject policyData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/policydetails",
						"PolicyDetails");
		
		DataObject refundDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/refunddetailsbg",
						"RefundDetailsBG");

		DataObject refundData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/refunddetails",
						"RefundDetails");
		
		DataObject accountDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/accountdetailsbg",
						"AccountDetailsBG");

		DataObject accountData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/accountdetails",
						"AccountDetails");
		
		DataObject receiptDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/receiptdetailsbg",
						"ReceiptDetailsBG");

		DataObject receiptData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/receiptdetails",
						"ReceiptDetails");
		
		DataObject insuredDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/insureddetailsbg",
						"InsuredDetailsBG");

		DataObject insuredData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/insureddetails",
						"InsuredDetails");

		if (ticketId != null && !("".equalsIgnoreCase(ticketId.trim()))) {
			ticketData.setDouble("ticket_id", Double.parseDouble(ticketId));
			ticketData.setDouble("parameter1", Double.parseDouble(ticketId));
			policyData.setDouble("parameter1", Double.parseDouble(ticketId));
			refundData.setDouble("parameter1", Double.parseDouble(ticketId));
			accountData.setDouble("parameter1", Double.parseDouble(ticketId));
			receiptData.setDouble("parameter1", Double.parseDouble(ticketId));
			insuredData.setDouble("parameter1", Double.parseDouble(ticketId));

		} else {
			throw new RuntimeException("Ticket ID Cannot be Empty");
		}
		ticketDataBG.setDataObject("TicketDetails", ticketData);
		policyDataBG.setDataObject("PolicyDetails", policyData);
		refundDataBG.setDataObject("RefundDetails", refundData);
		accountDataBG.setDataObject("AccountDetails", accountData);
		receiptDataBG.setDataObject("ReceiptDetails", receiptData);
		insuredDataBG.setDataObject("InsuredDetails", insuredData);
		// invoke service
        try{
        refundResponseBO = (DataObject) locateService_DBInterfacePartner()
						.invoke("retrieveallRefundDetailsBG", refundDataBG);
        refundResult = refundResponseBO
				.getDataObject("retrieveallRefundDetailsBGOutput/RefundDetails[1]");
	    insuredResponseBO = (DataObject) locateService_DBInterfacePartner()
			.invoke("retrieveallInsuredDetailsBG", insuredDataBG);
	    insuredResult = insuredResponseBO
	       .getList("retrieveallInsuredDetailsBGOutput/InsuredDetails");
	   
			
          }
        catch(Exception e)
        {
	     insuredResult = null;
	     System.out.println("Error from fetching insured details in retrieve ticket "+e.getMessage());
        }
		try {
			System.out.println("Ticket Id " + ticketId);
			ticketResponseBO = (DataObject) locateService_DBInterfacePartner()
					.invoke("retrieveallTicketDetailsBG", ticketDataBG);
			ticketResult = ticketResponseBO
					.getDataObject("retrieveallTicketDetailsBGOutput/TicketDetails[1]");
			if("CANCELLATION REFUND".equalsIgnoreCase(ticketResponseBO
					.getString("retrieveallTicketDetailsBGOutput/TicketDetails[1]/refund_type")) || 
					"CANCELLATION ONLY".equalsIgnoreCase(ticketResponseBO
							.getString("retrieveallTicketDetailsBGOutput/TicketDetails[1]/refund_type")) || 
							"NEGATIVE ENDORSEMENT REFUND".equalsIgnoreCase(ticketResponseBO
							.getString("retrieveallTicketDetailsBGOutput/TicketDetails[1]/refund_type"))|| 
							"BALANCE REFUND".equalsIgnoreCase(ticketResponseBO
							.getString("retrieveallTicketDetailsBGOutput/TicketDetails[1]/refund_type"))
							
					)
			{
			policyResponseBO = (DataObject) locateService_DBInterfacePartner()
					.invoke("retrieveallPolicyDetailsBG", policyDataBG);
			policyResult = policyResponseBO
					.getDataObject("retrieveallPolicyDetailsBGOutput/PolicyDetails[1]");
		
			}
			else
			{
			policyResult = null;
			
			}
		  
		     
			receiptResponseBO = (DataObject) locateService_DBInterfacePartner()
					.invoke("retrieveallReceiptDetailsBG", receiptDataBG);
			receiptResult = receiptResponseBO
					.getList("retrieveallReceiptDetailsBGOutput/ReceiptDetails");
			accountResponseBO = (DataObject) locateService_DBInterfacePartner()
					.invoke("retrieveallAccountDetailsBG", accountDataBG);
			accountResult = accountResponseBO
					.getDataObject("retrieveallAccountDetailsBGOutput/AccountDetails[1]");
			DataObject ticketBO = getBOFactory().create("http://RACASBO",
					"TicketBO");
			return prepareTicketResponse(
					ticketBO,
					ticketResult,policyResult,refundResult,receiptResult,accountResult,insuredResult);
			
		}

		catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();
			rasException.setString("status", "FAILED");
			rasException.setString("error[0]/message",
					exceptionData.get("message").toString());
			throw new ServiceBusinessException(rasException);

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage(), sre);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public String getProductName(String productCode) {
		System.out.println("Enter Into getProductName"+productCode);
		DataObject responseBO = null;
		DataObject rasException = getBOFactory().create("http://RACASBO",
				"RASException");
		DataObject exceptionData = null;
		DataObject productBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_productbg",
						"DboRas_ProductBG");

		DataObject productData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_product",
						"DboRas_Product");
		if (productCode != null && !("".equalsIgnoreCase(productCode.trim()))) {
			productData.setString("product_code", productCode);

		} else {
			throw new RuntimeException("Product Code cannot be empty");
		}
		productBG.setDataObject("DboRas_Product", productData);

		try {
			responseBO = (DataObject) locateService_DBInterfacePartner()
					.invoke("retrieveallDboRas_ProductBGInput", productBG);

			String productName = responseBO
					.getString("retrieveallDboRas_ProductBGInput/DboRas_Product[0]/product_name");

			return productName;
		} catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();
			rasException.setString("status", "FAILED");
			rasException.setString("error[0]/message",
					exceptionData.get("message").toString());
			throw new ServiceBusinessException(rasException);

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage(), sre);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	public DataObject prepareTicketResponse(DataObject target,DataObject ticketSource,DataObject policySource,DataObject refundSource,List receiptSource,DataObject accountSource,List insuredSource) {
		System.out.println("Enter Into prepareTicketResponse in retrieve ticket"+ticketSource.getString("ticket_no"));
        target.setDouble("ticketID", ticketSource.getDouble("ticket_id"));
		target.setString("lob", ticketSource.getString("lob"));
		target.setString("policyNo", ticketSource.getString("policy_no"));
		target.setString("applicationNo", ticketSource.getString("application_no"));
		target.setString("receiptNo", ticketSource.getString("rcs_no"));
		target.setString("product", ticketSource.getString("product"));
		target.setString("source", ticketSource.getString("source"));
		target.setString("priorityStatus", ticketSource.getString("priority"));
		target.setDate("requestedDate", ticketSource.getDate("requested_date"));
		target.setDate("effectiveDate", ticketSource.getDate("effective_date"));
		target.setString("refundType", ticketSource.getString("refund_type"));
		target.setString("ticketType", ticketSource.getString("ticket_type"));
		target.setString("discrepancyType", ticketSource.getString("discrepancy_type"));
		target.setString("ftrDescription", ticketSource.getString("ftr_description"));
		target.setString("lanNumber", ticketSource.getString("lan_number"));
		target.setString("ocrOmniDocumentsURL", ticketSource.getString("ocr_omni_documents_url"));
		target.setDouble("refundAmount", ticketSource.getDouble("refund_amount"));
		target.setDouble("premiumCalculationAmount", ticketSource.getDouble("premium_calculation_amount"));
		target.setString("cancellationReason",
				ticketSource.getString("cancellation_reason"));
		target.setString("otherReason", ticketSource.getString("other_reason"));
		target.setString("raisedBy", ticketSource.getString("created_by"));
		target.setString("actionBy", ticketSource.getString("last_modified_by"));
		// ticketData.setDate("created_on", new Date());
		target.setString("status", ticketSource.getString("status"));
		target.setString("stage", ticketSource.getString("stage"));
		
	//	target.setString("cdAccountNo", ticketSource.getString("cd_account_no"));
		
	//	target.setString("descOfSale", ticketSource.getString("description_of_sale"));
		
	//	target.setString("searchParameter", ticketSource.getString("search_parameter"));
		
	//	target.setDate("dateOfSale",
	//			ticketSource.getDate("date_of_sale"));
		
		//target.setString("customerPriorityType", ticketSource.getString("customer_priority_type"));
		
	//	target.setString("customerPriorityValue", ticketSource.getString("customer_priority_value"));
		
		
		// ticketData.setString("customer_name", ticket.getString("lob"));

		target.setString("ticketNo", ticketSource.getString("ticket_no"));
		target.setString("nameOfInsureedCompany",
				ticketSource.getString("name_of_insured_company"));
		target.setDouble("gst", ticketSource.getDouble("gst"));
		target.setDouble("jk_gst", ticketSource.getDouble("jk_gst"));
		target.setDouble("stampDuty", ticketSource.getDouble("stamp_duty"));
		target.setDouble("netPremium", ticketSource.getDouble("net_premium"));
		target.setDouble("totalPremium", ticketSource.getDouble("total_premium"));
		target.setString("requestType", ticketSource.getString("request_type"));
		target.setDouble("receiptRefundAmount", ticketSource.getDouble("receipt_refund_amount"));
		target.setDouble("PPCAmount", ticketSource.getDouble("ppc_amount"));
		target.setDouble("exchangeRate", ticketSource.getDouble("exchange_rate"));
		target.setDouble("minPremiumRetainAmount", ticketSource.getDouble("min_premium_retain_amount"));
		target.setDouble("endorsementAmount", ticketSource.getDouble("endorsement_amount"));
		target.setDouble("endorsementERFAmount", ticketSource.getDouble("endorsement_erf_amount"));
		target.setDouble("aigAgreedPremiumAmount", ticketSource.getDouble("agreed_premium_amount"));
		target.setDouble("commercialRefundAmount", ticketSource.getDouble("commercial_refund_amount"));
		target.setString("payeeType", ticketSource.getString("payer_type"));
		target.setInt("balanceDays", ticketSource.getInt("balance_days"));
		target.setString("bank", ticketSource.getString("bank"));
		target.setString("payeeName", ticketSource.getString("payee_name"));
		target.setString("processId", ticketSource.getString("instance_id"));
		target.setString("voucherNo", ticketSource.getString("voucher_no"));
		target.setString("cancellationOption",
				ticketSource.getString("cancellation_option"));
		target.setString("refundMode", ticketSource.getString("refund_mode"));
		target.setString("ticketRemarks", ticketSource.getString("ticket_remarks"));
		target.setString("remarks", ticketSource.getString("remarks"));
		target.setString("comments", ticketSource.getString("comments"));
		target.setString("icrmTicketNumber", ticketSource.getString("icrm_ticket_number"));
		target.setString("icrmCustomerId", ticketSource.getString("icrm_customer_id"));
		target.setString("icrmUsername", ticketSource.getString("icrm_username"));
		target.setString("subReceipt", ticketSource.getString("subreceipt"));
		target.setString("rasDraftTicketNo", ticketSource.getString("ras_draft_ticket_no"));
		target.setString("bopsChannel", ticketSource.getString("bops_channel"));
		target.setString("sourceUniqueId", ticketSource.getString("source_unique_id"));
		target.setString("sourceUserName", ticketSource.getString("source_user_name"));
		target.setString("omniDocumentsURL", ticketSource.getString("omni_documents_url"));
		target.setString("channelMode", ticketSource.getString("channel_mode"));
		//target.setBoolean("uploadedTo", ticketSource.getBoolean("upload_to"));
		target.setString("nameOfInsureedCompany",
				ticketSource.getString("name_of_insured_company"));
		target.setString("coverType", ticketSource.getString("cover_type"));
		target.setString("officeCodeAddress",
				ticketSource.getString("office_code_address"));
		target.setString("renewedPolicyNo",
				ticketSource.getString("renewed_policy_no"));
		target.setDate("ncbReservingEffectiveDate",
				ticketSource.getDate("ncb_reserving_effective_date"));
		target.setDate("periodToDate", ticketSource.getDate("period_to_date"));
		target.setDate("periodFromDate", ticketSource.getDate("period_from_date"));
		if (accountSource!= null) {

			target.setDouble("accountDetail/id",
					accountSource.getDouble("account_id"));
			target.setString("accountDetail/customerName",
					accountSource.getString("payee_name"));
			target.setString("accountDetail/customerBank",
					accountSource.getString("bank_name"));
			target.setString("accountDetail/customerIfscCode",
					accountSource.getString("ifsc_code"));
			target.setString("accountDetail/bankAccountNo",
					accountSource.getString("account_no"));
			target.setString("accountDetail/source",
					accountSource.getString("source"));
			// target.setString("is_active","Y");
			target.setString("accountDetail/createdBy",
					accountSource.getString("created_by"));
		//target.setString("accountDetail/actionBy",source.getString("last_modified_by"));
			target.setString("accountDetail/emailId",
					accountSource.getString("email_id"));
			target.setString("accountDetail/contactNo",
					accountSource.getString("contact_no"));
			target.setString("accountDetail/addressLine1",
					accountSource.getString("address_line1"));
			target.setString("accountDetail/addressLine2",
					accountSource.getString("address_line2"));
			target.setString("accountDetail/addressLine3",
					accountSource.getString("address_line3"));
			target.setString("accountDetail/state",
					accountSource.getString("state"));
			target.setString("accountDetail/city",
					accountSource.getString("city"));
			target.setString("accountDetail/area",
					accountSource.getString("area"));
			target.setString("accountDetail/pincode",
					accountSource.getString("pincode"));
			target.setString("accountDetail/bankAccountNoVerification",
					accountSource.getString("bank_account_verification"));
			target.setString("accountDetail/accountSource",
					accountSource.getString("account_source"));
			target.setString("accountDetail/landmark",
					accountSource.getString("landmark"));
			target.setString("accountDetail/district",
					accountSource.getString("district"));
		} else {
			target.setDataObject("accountDetail", null);

		}
		if (policySource != null) {
			target.setString("policyDetail/id",
					policySource.getString("slno"));
			target.setString("policyDetail/policyNo",
					policySource.getString("policyno"));
			target.setString("policyDetail/proposalNo",
					policySource.getString("proposalno"));
			target.setString("policyDetail/lob",
					policySource.getString("lob"));
			target.setString("policyDetail/product",
					policySource.getString("product"));
			target.setString("policyDetail/proposerName",
					policySource.getString("proposer_name"));
			target.setString("policyDetail/aadharNo",
					policySource.getString("aadhar_no"));
			target.setDouble("policyDetail/policyPremium",
					policySource.getDouble("policy_premium"));
			target.setDouble("policyDetail/aggreedPremiumAmount", policySource
					.getDouble("agreedpremiumamount"));
			target.setString("policyDetail/producerCode",
					policySource.getString("producer_code"));
			target.setString("policyDetail/producerName",
					policySource.getString("producer_name"));

			target.setString("policyDetail/engineNumber",
					policySource.getString("engine_number"));

			target.setString("policyDetail/chasisNumber",
					policySource.getString("chasis_number"));

			target.setString("policyDetail/searchBy",
					policySource.getString("search_by"));

			target.setString("policyDetail/certificateNo",
					policySource.getString("certificate_number"));
			target.setString("policyDetail/policyType",
					policySource.getString("policy_type"));
			
			target.setString("policyDetail/registrationNo",
					policySource.getString("registration_no"));
			
			target.setString("policyDetail/lanNumber",
					policySource.getString("lan_number"));
			target.setString("policyDetail/dealerName",
					policySource.getString("dealer_name"));

			target.setString("policyDetail/dealerCode",
					policySource.getString("dealer_code"));

			target.setString("policyDetail/financierName",
					policySource.getString("financier_name"));

			target.setString("policyDetail/financierCode",
					policySource.getString("financier_code"));

			target.setString("policyDetail/policyStatus",
					policySource.getString("status"));
			target.setString("policyDetail/authCode",
					policySource.getString("auth_code"));
			target.setString("policyDetail/customerCode",
					policySource.getString("customer_code"));
			target.setString("policyDetail/policyCoverType",
					policySource.getString("policy_cover_type"));
			target.setString("policyDetail/actionBy",
					policySource.getString("last_modified_by"));
			target.setDate("policyDetail/applicationDate",
					policySource.getDate("application_date"));
			target.setDate("policyDetail/policyStartDate",
					policySource.getDate("polcy_start_date"));
			target.setDate("policyDetail/policyEndDate",
					policySource.getDate("policy_end_date"));
			target.setDate("policyDetail/conversionDate",
					policySource.getDate("conversion_date"));
			target.setString("policyDetail/applicationNumber",
					policySource.getString("application_number"));
			if(insuredSource != null)
			{
			List<DataObject> insuredItr = insuredSource;
					
			if (insuredItr.size() != 0) {
				List insuredList = new ArrayList();
				for (int i = 0; i < insuredItr.size(); i++) {
					DataObject insured = getBOFactory().create(
							"http://RACASBO", "InsuredBO");

					insured.setString("name", insuredItr.get(i).getString("name"));
					insured.setString("address", insuredItr.get(i).getString("address"));
					insured.setString("contactNo", insuredItr.get(i).getString("contactno"));
					insured.setDate("dob", insuredItr.get(i).getDate("dob"));
					insured.setDouble("id", insuredItr.get(i).getDouble("slno"));

					insuredList.add(insured);
				}
				target.setList("policyDetail/insuredBO", insuredList);
			}
			}
		} else {
			target.setDataObject("policyDetail", null);
		}
		if(receiptSource!=null)
		{
		List<DataObject> receiptItr = receiptSource;
		if (receiptItr.size() != 0) {
			List receiptList = new ArrayList();
			for (int i = 0; i < receiptItr.size(); i++) {
			
				DataObject receipt = getBOFactory().create("http://RACASBO",
						"ReceiptBO");
                
				receipt.setString(
						"id",
						receiptItr.get(i).getString("slno"));
				
				receipt.setString(
						"crsNo",
						receiptItr.get(i).getString("crs_number"));
				receipt.setString(
						"authCode",
						receiptItr.get(i).getString("auth_code"));
				receipt.setString(
						"crsStatus",
						receiptItr.get(i).getString("crs_status"));
				receipt.setString(
						"crsAmount",
						receiptItr.get(i).getString("crs_amount"));
				receipt.setString(
						"payerType",
						receiptItr.get(i).getString("payer_type"));
				receipt.setString(
						"CRSPayerID",
						receiptItr.get(i).getString("payer_id"));
				receipt.setString(
						"payeeName",
						receiptItr.get(i).getString("payee_name"));
				receipt.setString(
						"paymentReceivedMethod",
						receiptItr.get(i).getString("payment_received_method"));
				receipt.setString(
						"payeeBank",
						receiptItr.get(i).getString("payee_bank"));
				receipt.setString(
						"payeeIfscCode",
						receiptItr.get(i).getString("payee_ifsc_code"));
				receipt.setString(
						"payeeBankAccountNo",
						receiptItr.get(i).getString("payee_account_number"));
				receipt.setDouble(
						"balanceAmount",
						receiptItr.get(i).getDouble("balance_amount"));
				receipt.setDouble(
						"totalAmount",
						receiptItr.get(i).getDouble("total_amount"));
				receipt.setDouble(
						"taggedAmount",
						receiptItr.get(i).getDouble("tagged_amount"));
				receipt.setString(
						"payementSource",
						receiptItr.get(i).getString("payement_source"));
				receipt.setString(
						"houseBankName",
						receiptItr.get(i).getString("house_bank_name"));
				receipt.setString(
						"houseBankAccountNo",
						receiptItr.get(i).getString("house_bank_account_no"));
				receipt.setString(
						"payementMode",
						receiptItr.get(i).getString("payment_mode"));
				receipt.setString(
						"policyNumbers",
						receiptItr.get(i).getString("policy_no"));
				receipt.setString(
						"subreceiptNumbers",
						receiptItr.get(i).getString("subreceipt_no"));
				//receipt.setString("actionBy",source.getString("+ "]/last_modified_by"));
				receipt.setDate(
						"receiptDate",
						receiptItr.get(i).getDate("receipt_date"));
				receipt.setString(
						"drawerName",
						receiptItr.get(i).getString("drawer_name"));
				receipt.setString(
						"receiptLANNo",
						receiptItr.get(i).getString("lan_number"));
				receipt.setString(
						"receiptApplicationNo",
						receiptItr.get(i).getString("application_number"));
				receipt.setString(
						"receiptAccountNo",
						receiptItr.get(i).getString("receipt_account_number"));
				receipt.setString(
						"receiptIFSCCode",
						receiptItr.get(i).getString("receipt_ifsc_code"));
				receipt.setString(
						"lob",
						receiptItr.get(i).getString("lob"));
				receipt.setString(
						"cdAcccountNumber",
						receiptItr.get(i).getString("cd_account_number"));
				receipt.setString(
						"receiptPayerName",
						receiptItr.get(i).getString("payer_name"));
				receipt.setDate(
						"instrumentDate",
						receiptItr.get(i).getDate("instrument_date"));
				receipt.setString(
						"claimIFSCCode",
						receiptItr.get(i).getString("claim_ifsc_code"));
				receipt.setString(
						"claimAccountNo",
						receiptItr.get(i).getString("claim_account_number"));
				receipt.setString(
						"claimBankName",
						receiptItr.get(i).getString("claim_bank_name"));
				receipt.setString(
						"claimEmailID",
						receiptItr.get(i).getString("claim_email_id"));
				receipt.setString(
						"claimMobileNo",
						receiptItr.get(i).getString("claim_contact_number"));
				receipt.setString(
						"claimantName",
						receiptItr.get(i).getString("claimant_name"));
				receipt.setDouble(
						"id",
						receiptItr.get(i).getDouble("slno"));
				receiptList.add(receipt);
		}
			target.setList("receiptDetail", receiptList);
		}
		}
		else
		target.setList("receiptDetail", null);	
		if (refundSource != null) {
			target.setDouble("refundDetail/refundId",
					refundSource.getDouble("refund_id"));
			target.setDouble("refundDetail/ticketId",
					refundSource.getDouble("ticket_id"));
			target.setString("refundDetail/policyNo",
					refundSource.getString("policy_no"));
			target.setString("refundDetail/refundType",
					refundSource.getString("refund_type"));
			target.setString("refundDetail/beneName",
					refundSource.getString("bene_name"));
			target.setDouble("refundDetail/refundAmount",
					refundSource.getDouble("refund_amount"));
			target.setDate("refundDetail/txnDate",
					refundSource.getDate("txn_date"));
			target.setString("refundDetail/txnNo",
					refundSource.getString("txn_no"));
			target.setDouble("refundDetail/premiumAmount",
					refundSource.getDouble("premium_amount"));
			target.setString("refundDetail/ifscCode",
					refundSource.getString("ifsc_code"));
			target.setString("refundDetail/chequeNo",
					refundSource.getString("cheque_no"));
			target.setString("refundDetail/bankName",
					refundSource.getString("bank_name"));
			target.setString("refundDetail/branchName",
					refundSource.getString("branch_name"));
			target.setString("refundDetail/address1",
					refundSource.getString("address1"));
			target.setString("refundDetail/address2",
					refundSource.getString("address2"));
			target.setString("refundDetail/address3",
					refundSource.getString("address3"));
			target.setString("refundDetail/city",
					refundSource.getString("city"));
			target.setString("refundDetail/emailId",
					refundSource.getString("emailid"));
			target.setString("refundDetail/status",
					refundSource.getString("status"));
			target.setString("refundDetail/remarks",
					refundSource.getString("remarks"));
			target.setString("refundDetail/accountNo",
					refundSource.getString("account_no"));
			target.setString("refundDetail/voucherNo",
					refundSource.getString("voucher_no"));
			//target.setString("refundDetail/actionBy",source.getString("last_modified_by"));
		}
		else {
			target.setDataObject("refundDetail", null);
		}

		return target;
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_LobBG(DataObject createDboRasLobBGInput)" of
	 * wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_LobBG(DataObject updateDboRasLobBGInput)" of
	 * wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_LobBG(DataObject updateallDboRasLobBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_LobBG(DataObject deleteDboRasLobBGInput)" of
	 * wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_LobBG(DataObject deleteallDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_LobBG(DataObject retrieveDboRasLobBGInput)" of
	 * wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_LobBG(DataObject retrieveallDboRasLobBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_LobBG(DataObject applychangesDboRasLobBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_LobBG(DataObject batchcreateDboRasLobBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_LobBG(DataObject batchupdateDboRasLobBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#createDboRas_StatusBG(DataObject createDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onCreateDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#updateDboRas_StatusBG(DataObject updateDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpdateDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#updateallDboRas_StatusBG(DataObject updateallDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpdateallDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_StatusBG(DataObject deleteDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_StatusBG(DataObject deleteallDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#retrieveDboRas_StatusBG(DataObject retrieveDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onRetrieveDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#retrieveallDboRas_StatusBG(DataObject retrieveallDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onRetrieveallDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#applychangesDboRas_StatusBG(DataObject applychangesDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onApplychangesDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#existsDboRas_StatusBG(DataObject existsDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onExistsDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_StatusBG(DataObject upsertDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#batchcreateDboRas_StatusBG(DataObject batchcreateDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onBatchcreateDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#batchupdateDboRas_StatusBG(DataObject batchupdateDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onBatchupdateDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_LobBG(DataObject existsDboRasLobBGInput)" of
	 * wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_LobBG(DataObject upsertDboRasLobBGInput)" of
	 * wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_App_ConfigBG(DataObject createDboRasAppConfigBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_App_ConfigBG(DataObject updateDboRasAppConfigBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_App_ConfigBG(DataObject updateallDboRasAppConfigBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_App_ConfigBG(DataObject deleteDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_App_ConfigBG(DataObject deleteallDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_App_ConfigBG(DataObject retrieveDboRasAppConfigBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_App_ConfigBG(DataObject retrieveallDboRasAppConfigBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_App_ConfigBG(DataObject applychangesDboRasAppConfigBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_App_ConfigBG(DataObject batchcreateDboRasAppConfigBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_App_ConfigBG(DataObject batchupdateDboRasAppConfigBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_App_ConfigBG(DataObject existsDboRasAppConfigBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_App_ConfigBG(DataObject upsertDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_Cancellation_OptionBG(DataObject createDboRasCancellationOptionBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_Cancellation_OptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_Cancellation_OptionBG(DataObject updateDboRasCancellationOptionBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_Cancellation_OptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_Cancellation_OptionBG(DataObject updateallDboRasCancellationOptionBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_Cancellation_OptionBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_Cancellation_OptionBG(DataObject deleteDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_Cancellation_OptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_Cancellation_OptionBG(DataObject deleteallDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_Cancellation_OptionBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_Cancellation_OptionBG(DataObject retrieveDboRasCancellationOptionBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_Cancellation_OptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_Cancellation_OptionBG(DataObject retrieveallDboRasCancellationOptionBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_Cancellation_OptionBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_Cancellation_OptionBG(DataObject applychangesDboRasCancellationOptionBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_Cancellation_OptionBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_Cancellation_OptionBG(DataObject batchcreateDboRasCancellationOptionBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_Cancellation_OptionBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_Cancellation_OptionBG(DataObject batchupdateDboRasCancellationOptionBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_Cancellation_OptionBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_Cancellation_OptionBG(DataObject existsDboRasCancellationOptionBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_Cancellation_OptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_Cancellation_OptionBG(DataObject upsertDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_Cancellation_OptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_Cancellation_ReasonBG(DataObject createDboRasCancellationReasonBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_Cancellation_ReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_Cancellation_ReasonBG(DataObject updateDboRasCancellationReasonBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_Cancellation_ReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_Cancellation_ReasonBG(DataObject updateallDboRasCancellationReasonBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_Cancellation_ReasonBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_Cancellation_ReasonBG(DataObject deleteDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_Cancellation_ReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_Cancellation_ReasonBG(DataObject deleteallDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_Cancellation_ReasonBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_Cancellation_ReasonBG(DataObject retrieveDboRasCancellationReasonBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_Cancellation_ReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_Cancellation_ReasonBG(DataObject retrieveallDboRasCancellationReasonBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_Cancellation_ReasonBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_Cancellation_ReasonBG(DataObject applychangesDboRasCancellationReasonBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_Cancellation_ReasonBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_Cancellation_ReasonBG(DataObject batchcreateDboRasCancellationReasonBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_Cancellation_ReasonBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_Cancellation_ReasonBG(DataObject batchupdateDboRasCancellationReasonBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_Cancellation_ReasonBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_Cancellation_ReasonBG(DataObject existsDboRasCancellationReasonBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_Cancellation_ReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_Cancellation_ReasonBG(DataObject upsertDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_Cancellation_ReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_Document_MasterBG(DataObject createDboRasDocumentMasterBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_Document_MasterBG(DataObject updateDboRasDocumentMasterBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_Document_MasterBG(DataObject updateallDboRasDocumentMasterBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_Document_MasterBG(DataObject deleteDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_Document_MasterBG(DataObject deleteallDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_Document_MasterBG(DataObject retrieveDboRasDocumentMasterBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_Document_MasterBG(DataObject retrieveallDboRasDocumentMasterBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_Document_MasterBG(DataObject applychangesDboRasDocumentMasterBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_Document_MasterBG(DataObject batchcreateDboRasDocumentMasterBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_Document_MasterBG(DataObject batchupdateDboRasDocumentMasterBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_Document_MasterBG(DataObject existsDboRasDocumentMasterBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_Document_MasterBG(DataObject upsertDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_JobsBG(DataObject createDboRasJobsBGInput)" of
	 * wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_JobsBG(DataObject updateDboRasJobsBGInput)" of
	 * wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_JobsBG(DataObject updateallDboRasJobsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_JobsBG(DataObject deleteDboRasJobsBGInput)" of
	 * wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_JobsBG(DataObject deleteallDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_JobsBG(DataObject retrieveDboRasJobsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_JobsBG(DataObject retrieveallDboRasJobsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_JobsBG(DataObject applychangesDboRasJobsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_JobsBG(DataObject batchcreateDboRasJobsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_JobsBG(DataObject batchupdateDboRasJobsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_JobsBG(DataObject existsDboRasJobsBGInput)" of
	 * wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_JobsBG(DataObject upsertDboRasJobsBGInput)" of
	 * wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_ApproverBG(DataObject createDboRasApproverBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_ApproverBG(DataObject updateDboRasApproverBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_ApproverBG(DataObject updateallDboRasApproverBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_ApproverBG(DataObject deleteDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_ApproverBG(DataObject deleteallDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_ApproverBG(DataObject retrieveDboRasApproverBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_ApproverBG(DataObject retrieveallDboRasApproverBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_ApproverBG(DataObject applychangesDboRasApproverBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_ApproverBG(DataObject batchcreateDboRasApproverBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_ApproverBG(DataObject batchupdateDboRasApproverBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_ApproverBG(DataObject existsDboRasApproverBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_ApproverBG(DataObject upsertDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_Account_Detail_HistBG(DataObject createDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_Account_Detail_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_Account_Detail_HistBG(DataObject updateDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_Account_Detail_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_Account_Detail_HistBG(DataObject updateallDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_Account_Detail_HistBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_Account_Detail_HistBG(DataObject deleteDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_Account_Detail_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_Account_Detail_HistBG(DataObject deleteallDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_Account_Detail_HistBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_Account_Detail_HistBG(DataObject retrieveDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_Account_Detail_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_Account_Detail_HistBG(DataObject retrieveallDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_Account_Detail_HistBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_Account_Detail_HistBG(DataObject applychangesDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_Account_Detail_HistBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_Account_Detail_HistBG(DataObject batchcreateDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_Account_Detail_HistBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_Account_Detail_HistBG(DataObject batchupdateDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_Account_Detail_HistBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_Account_Detail_HistBG(DataObject existsDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_Account_Detail_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_Account_Detail_HistBG(DataObject upsertDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_Account_Detail_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_Ticket_HistBG(DataObject createDboRasTicketHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_Ticket_HistBG(DataObject updateDboRasTicketHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_Ticket_HistBG(DataObject updateallDboRasTicketHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_Ticket_HistBG(DataObject deleteDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_Ticket_HistBG(DataObject deleteallDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_Ticket_HistBG(DataObject retrieveDboRasTicketHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_Ticket_HistBG(DataObject retrieveallDboRasTicketHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_Ticket_HistBG(DataObject applychangesDboRasTicketHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_Ticket_HistBG(DataObject batchcreateDboRasTicketHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_Ticket_HistBG(DataObject batchupdateDboRasTicketHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#retrieveallDocumentMatrixBG(DataObject retrieveallDocumentMatrixBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onRetrieveallDocumentMatrixBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
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
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_Ticket_HistBG(DataObject existsDboRasTicketHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_Ticket_HistBG(DataObject upsertDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_Exception_HistBG(DataObject createDboRasExceptionHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_Exception_HistBG(DataObject updateDboRasExceptionHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_Exception_HistBG(DataObject updateallDboRasExceptionHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_Exception_HistBG(DataObject deleteDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_Exception_HistBG(DataObject deleteallDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_Exception_HistBG(DataObject retrieveDboRasExceptionHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_Exception_HistBG(DataObject retrieveallDboRasExceptionHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_Exception_HistBG(DataObject applychangesDboRasExceptionHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_Exception_HistBG(DataObject batchcreateDboRasExceptionHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_Exception_HistBG(DataObject batchupdateDboRasExceptionHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_Exception_HistBG(DataObject existsDboRasExceptionHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_Exception_HistBG(DataObject upsertDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_InsuredBG(DataObject createDboRasInsuredBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_InsuredBG(DataObject updateDboRasInsuredBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_InsuredBG(DataObject updateallDboRasInsuredBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_InsuredBG(DataObject deleteDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_InsuredBG(DataObject deleteallDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_InsuredBG(DataObject retrieveDboRasInsuredBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_InsuredBG(DataObject retrieveallDboRasInsuredBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_InsuredBG(DataObject applychangesDboRasInsuredBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_InsuredBG(DataObject batchcreateDboRasInsuredBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_InsuredBG(DataObject batchupdateDboRasInsuredBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_InsuredBG(DataObject existsDboRasInsuredBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_InsuredBG(DataObject upsertDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_Ticket_DocumentsBG(DataObject createDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_Ticket_DocumentsBG(DataObject updateDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_Ticket_DocumentsBG(DataObject updateallDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_Ticket_DocumentsBG(DataObject deleteDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_Ticket_DocumentsBG(DataObject deleteallDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_Ticket_DocumentsBG(DataObject retrieveDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_Ticket_DocumentsBG(DataObject retrieveallDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_Ticket_DocumentsBG(DataObject applychangesDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_Ticket_DocumentsBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_Ticket_DocumentsBG(DataObject batchcreateDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_Ticket_DocumentsBG(DataObject batchupdateDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_Ticket_DocumentsBG(DataObject existsDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_Ticket_DocumentsBG(DataObject upsertDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDocumentU32MatrixBG(DataObject retrieveallDocumentU32MatrixBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDocumentU32MatrixBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_TicketBG(DataObject createDboRasTicketBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_TicketBG(DataObject updateDboRasTicketBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_TicketBG(DataObject updateallDboRasTicketBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_TicketBG(DataObject deleteDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_TicketBG(DataObject deleteallDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_TicketBG(DataObject retrieveDboRasTicketBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_TicketBG(DataObject retrieveallDboRasTicketBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_TicketBG(DataObject applychangesDboRasTicketBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_TicketBG(DataObject batchcreateDboRasTicketBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_TicketBG(DataObject batchupdateDboRasTicketBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_TicketBG(DataObject existsDboRasTicketBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_TicketBG(DataObject upsertDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_DboBG(DataObject createDboRasDboBGInput)" of
	 * wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_DboBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_DboBG(DataObject updateDboRasDboBGInput)" of
	 * wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_DboBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_DboBG(DataObject updateallDboRasDboBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_DboBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_DboBG(DataObject retrieveDboRasDboBGInput)" of
	 * wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_DboBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_DboBG(DataObject retrieveallDboRasDboBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_DboBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_DboBG(DataObject applychangesDboRasDboBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_DboBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_DboBG(DataObject batchcreateDboRasDboBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_DboBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_DboBG(DataObject batchupdateDboRasDboBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_DboBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_DboBG(DataObject existsDboRasDboBGInput)" of
	 * wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_DboBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_Receipt_DetailBG(DataObject createDboRasReceiptDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_Receipt_DetailBG(DataObject updateDboRasReceiptDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_Receipt_DetailBG(DataObject updateallDboRasReceiptDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_Receipt_DetailBG(DataObject deleteDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_Receipt_DetailBG(DataObject deleteallDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_Receipt_DetailBG(DataObject retrieveDboRasReceiptDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_Receipt_DetailBG(DataObject retrieveallDboRasReceiptDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_Receipt_DetailBG(DataObject applychangesDboRasReceiptDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_Receipt_DetailBG(DataObject batchcreateDboRasReceiptDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_Receipt_DetailBG(DataObject batchupdateDboRasReceiptDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_Receipt_DetailBG(DataObject existsDboRasReceiptDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_Receipt_DetailBG(DataObject upsertDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_Policy_DetailBG(DataObject createDboRasPolicyDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_Policy_DetailBG(DataObject updateDboRasPolicyDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_Policy_DetailBG(DataObject updateallDboRasPolicyDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_Policy_DetailBG(DataObject deleteDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_Policy_DetailBG(DataObject deleteallDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_Policy_DetailBG(DataObject retrieveDboRasPolicyDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_Policy_DetailBG(DataObject retrieveallDboRasPolicyDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_Policy_DetailBG(DataObject applychangesDboRasPolicyDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_Policy_DetailBG(DataObject batchcreateDboRasPolicyDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_Policy_DetailBG(DataObject batchupdateDboRasPolicyDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#createDboRas_RefundBG(DataObject createDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onCreateDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#updateDboRas_RefundBG(DataObject updateDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpdateDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#updateallDboRas_RefundBG(DataObject updateallDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpdateallDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_RefundBG(DataObject deleteDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_RefundBG(DataObject deleteallDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#retrieveDboRas_RefundBG(DataObject retrieveDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onRetrieveDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#retrieveallDboRas_RefundBG(DataObject retrieveallDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onRetrieveallDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#applychangesDboRas_RefundBG(DataObject applychangesDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onApplychangesDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#existsDboRas_RefundBG(DataObject existsDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onExistsDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_RefundBG(DataObject upsertDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#batchcreateDboRas_RefundBG(DataObject batchcreateDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onBatchcreateDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#batchupdateDboRas_RefundBG(DataObject batchupdateDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onBatchupdateDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_Policy_DetailBG(DataObject existsDboRasPolicyDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_Policy_DetailBG(DataObject upsertDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#createDboRas_Account_DetailBG(DataObject createDboRasAccountDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_Account_DetailBG(DataObject updateDboRasAccountDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_Account_DetailBG(DataObject updateallDboRasAccountDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteDboRas_Account_DetailBG(DataObject deleteDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#deleteallDboRas_Account_DetailBG(DataObject deleteallDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onDeleteallDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_Account_DetailBG(DataObject retrieveDboRasAccountDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_Account_DetailBG(DataObject retrieveallDboRasAccountDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_Account_DetailBG(DataObject applychangesDboRasAccountDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_Account_DetailBG(DataObject batchcreateDboRasAccountDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_Account_DetailBG(DataObject batchupdateDboRasAccountDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_Account_DetailBG(DataObject existsDboRasAccountDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "DBInterface#upsertDboRas_Account_DetailBG(DataObject upsertDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"
	 */
	public void onUpsertDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

}