package org.aig.ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.ibm.websphere.bo.BOFactory;
import com.ibm.websphere.sca.Service;
import com.ibm.websphere.sca.ServiceBusinessException;
import com.ibm.websphere.sca.ServiceManager;
import com.ibm.websphere.sca.ServiceRuntimeException;
import com.ibm.websphere.sca.Ticket;


import commonj.sdo.DataObject;

/**
 * @author darkhorse
 *
 */
/**
 * @author darkhorse
 *
 */
/**
 * @author darkhorsesave
 * 
 */

/**
 * @author sareddy1
 *
 */
/**
 * @author sareddy1
 *
 */
public class MaintainTicketJavaCpmtImpl {
	/**
	 * Default constructor.
	 */

	public MaintainTicketJavaCpmtImpl() {
		super();
	}

	public enum Constants {
		CancellationRefund("CANCELLATION REFUND"),
		ExcessRefund("EXCESS REFUND"),
		BillDesk("BILLDESK"), 
		DebitAuthorization("DEBIT AUTHORIZATION"),
		Cheque("CHEQUE"), 
		ManualCheque("MANUAL CHEQUE"), 
	    SystemCheque("AUTO CHEQUE"),
		NEFT("NEFT"),
		Customer("CUSTOMER");
		String constant;

		Constants(String constant) {
			this.constant = constant;
		}

		public String getValue() {
			return this.constant;
		}
	}



	public Boolean effectiveDateValiadtion(Date effectiveDate,Date requestedDate,Date policyStartDate,Date policyEndDate)
	{
		Boolean flag=true;



		if(effectiveDate.after(policyEndDate) && effectiveDate.before(policyStartDate))
		{

		}

		return flag;
	}

	/**
	 * Return a reference to the component service instance for this
	 * implementation class. This method should be used when passing this
	 * service to a partner reference or if you want to invoke this component
	 * service asynchronously.
	 * 
	 * @generated (com.ibm.wbit.java)
	 */

	@SuppressWarnings("unused")
	private Object getMyService() {
		return (Object) ServiceManager.INSTANCE.locateService("self");
	}

	/**
	 * This method is used to locate the service for the reference named
	 * "JDBCImportPartner". This will return an instance of
	 * {@link com.ibm.websphere.sca.Service}. This is the dynamic interface
	 * which is used to invoke operations on the reference service either
	 * synchronously or asynchronously. You will need to pass the operation name
	 * in order to invoke an operation on the service.
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
	 * Method generated to support implementation of operation "saveTicket"
	 * defined for WSDL port type named "MaintainTicket".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a
	 * parameter type conveys that it is a complex type. Please refer to the
	 * WSDL Definition for more information on the type of input, output and
	 * fault(s).
	 */
	public BOFactory getBOFactory() {
		return (BOFactory) ServiceManager.INSTANCE
				.locateService("com/ibm/websphere/bo/BOFactory");
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

	// update Ticket_id's with job_id during disbursement
	public Boolean updateTicketJobs(Integer jobId, DataObject disbursement)
			throws ParseException {
System.out.println("Enter Into updateTicketJobs");
		DataObject ticketJobsListBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticketlistbg",
						"DboRas_TicketListBG");
		DataObject ticketJobsList = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticketlist",
						"DboRas_TicketList");
		// RASException Object
		DataObject rasException = getBOFactory().create("http://RACASBO",
				"RASException");
		// Object to store Business Exception Data
		DataObject exceptionData = null;
		List disbursementList = new ArrayList();
		int size = disbursement.getList(0).size();
		for (int i = 1; i <= size; i++) {
			DataObject ticketJobs = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticket",
							"DboRas_Ticket");
			ticketJobs.setDouble("ticket_id",
					disbursement.getDouble("disbursement[" + i + "]/ticketId"));
			ticketJobs.setString(
					"refund_type",
					disbursement.getString("disbursement[" + i
							+ "]/disbursementType"));
			ticketJobs.setString("status",
					disbursement.getString("disbursement[" + i + "]/status"));
			ticketJobs.setString("remarks",
					disbursement.getString("disbursement[" + i + "]/comments"));
			ticketJobs.setDouble("job_id", jobId);
			ticketJobs.setString("stage", "Disbursement");
			ticketJobs.setString("last_modified_by",com.ibm.ws.security.core.SecurityContext.getName());
			ticketJobs.setDate("last_modified_on", formatDate(new Date()));
			disbursementList.add(ticketJobs);
		}
		ticketJobsList.setList("businessobjects", disbursementList);
		ticketJobsListBG.setDataObject("DboRas_TicketList", ticketJobsList);
		try {
			locateService_DBInterfacePartner().invoke(
					"batchupdateDboRas_TicketBG", ticketJobsListBG);
			return true;
		} catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();
			rasException.setString("status", "FAILED");
			rasException.setString("error[0]/message",
					exceptionData.get("message").toString());
			throw new ServiceBusinessException(rasException);

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage().toString());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Method generated to support implementation of operation
	 * "updateTicketJobs" defined for WSDL port type named "MaintainTicket".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a
	 * parameter type conveys that it is a complex type. Please refer to the
	 * WSDL Definition for more information on the type of input, output and
	 * fault(s).
	 */

	public List<String> validateTicket(DataObject ticket) throws ParseException {
		System.out.println("Enter Into ValidateTicket"+ticket.getString("ticketNo"));
		List<String> validationErrors = new ArrayList<String>();
		String valError = "";

		/*
		 * if (ticket.getDouble("ticketNo")== 0 ){ valError =
		 * "TicketId cannot be empty"; validationErrors.add(valError); }
		 */

		if (ticket.get("refundType") == null
				|| "".equalsIgnoreCase(ticket.get("refundType").toString())) {
			valError = "Refund Type cannot be blank or empty";
			validationErrors.add(valError);
			return validationErrors;
		}
		
		if(!("CANCELLATION ONLY".equalsIgnoreCase(ticket.getString("refundType")))){
		if (ticket.get("payeeType") == null
				|| "".equalsIgnoreCase(ticket.get("payeeType").toString())) {
			valError = "Payee Type cannot be blank or empty";
			validationErrors.add(valError);
			return validationErrors;
		}
		
		if ((ticket.getString("refundMode") == null) || 
				("".equalsIgnoreCase(ticket.getString("refundMode")))) {
			validationErrors.add("Refund Mode cannot be blank or empty");
		}
		
		if (ticket.get("payeeName") == null
				|| "".equalsIgnoreCase(ticket.get("payeeName").toString())) {
			valError = "Payee Name cannot be blank or empty";
			validationErrors.add(valError);
			return validationErrors;
		}
		}
		
		
		//Beneficiary Name/Payee Name field length validation for NEFT and CHEQUE refund modes
		if(Constants.NEFT.getValue().equalsIgnoreCase(ticket.getString("refundMode")) || Constants.Cheque.getValue().equalsIgnoreCase(ticket.getString("refundMode")))
		{
		if (ticket.getString("payeeName") != null
				&& !"".equalsIgnoreCase(ticket.get("payeeName").toString())) {
			int payeeNameLen = ticket.getString("payeeName").length();
			if(payeeNameLen>50){
				valError = "Invalid value input in Beneficiary Name/Payee Name, Value exceeding field length limit 50 characters for NEFT and CHEQUE refund modes. Please recheck the value";
				validationErrors.add(valError);
				return validationErrors;
			}
		}
		}
		
	/*	if(!("CANCELLATION ONLY".equalsIgnoreCase(ticket.getString("refundType")))){
		if (ticket.get("bank") == null
				|| "".equalsIgnoreCase(ticket.get("bank").toString())) {
			valError = "Bank cannot be blank or empty";
			validationErrors.add(valError);
			return validationErrors;
		}
		}  */
		
	
        String ticketRemarksPattern = "^[a-zA-Z0-9,. ]*$";
		if(ticket.getString("ticketRemarks") == null || "".equalsIgnoreCase(ticket.getString("ticketRemarks"))){
			valError = "Ticket Remarks cannot be blank or empty";
			validationErrors.add(valError);
			return validationErrors;
		}
        if(!(ticket.getString("ticketRemarks").matches(ticketRemarksPattern))){
        	valError = "Ticket Remarks Accepts only Alphanumeric characters";
			validationErrors.add(valError);
			return validationErrors;
        }
        
        if(ticket.getString("comments")!=null&&!"".equalsIgnoreCase(ticket.getString("comments"))){
        if(!(ticket.getString("comments").matches(ticketRemarksPattern))){
        	valError = "Comments Accepts only Alphanumeric characters";
			validationErrors.add(valError);
			return validationErrors;
        }
        }
		int ticketRemarksLen = ticket.getString("ticketRemarks").length();
		if(ticketRemarksLen>400){
			valError = "Ticket Remarks length cannot be greater than 400 characters.";
			validationErrors.add(valError);
			return validationErrors;
		}
		
		
		if(Constants.DebitAuthorization.getValue().equalsIgnoreCase(ticket.getString("refundMode"))
			&& !("CANCELLATION ONLY".equalsIgnoreCase(ticket.getString("refundType")))	){
		//if(ticket.getString("refundMode").equalsIgnoreCase(Constants.DebitAuthorization.getValue())){
			
			//ticketData.setString("bank", ticket.getString("bank"));
			if (ticket.getList("receiptDetail") != null)
			{
				if (ticket.getList("receiptDetail").size() != 0) {
					if (ticket.getString("receiptDetail[0]/authCode")==null ||"".equalsIgnoreCase(ticket.getString("receiptDetail[0]/authCode")))
					{
					    validationErrors.add("AuthCode Transaction Number cannot be blank or empty for Debit Authorization Refund Mode");
						return validationErrors;
					}
				}	
			}
		}

		if(Constants.NEFT.getValue().equalsIgnoreCase(ticket.getString("refundMode")) 
				&&!("CANCELLATION ONLY".equalsIgnoreCase(ticket.getString("refundType")))){
			if (ticket.get("accountDetail") == null) {
				validationErrors.add("Account Details cannot be blank or empty for NEFT Refund mode");
				return validationErrors;
			}
			if (ticket.get("accountDetail") != null) {
				
				if(Constants.Customer.getValue().equalsIgnoreCase(ticket.getString("payeeType")))
				{
				if("".equalsIgnoreCase(ticket.getString("accountDetail/accountSource"))|| ticket.getString("accountDetail/accountSource")==null){
					validationErrors.add("Account Source cannot be blank or empty for NEFT Refund mode and CUSTOMER Payee type");
					return validationErrors;
				}
				}

				if("".equalsIgnoreCase(ticket.getString("accountDetail/customerName"))||ticket.getString("accountDetail/customerName")==null){
					validationErrors.add("Customer Name cannot be blank or empty for NEFT Refund mode");
					return validationErrors;
				}
				
				//Beneficiary Name/Payee Name field length validation for NEFT and CHEQUE refund modes
				if(Constants.NEFT.getValue().equalsIgnoreCase(ticket.getString("refundMode")) || Constants.Cheque.getValue().equalsIgnoreCase(ticket.getString("refundMode")))
				{
				if (ticket.getString("accountDetail/customerName") != null
						&& !"".equalsIgnoreCase(ticket.get("accountDetail/customerName").toString())) {
					int customerNameLen = ticket.getString("accountDetail/customerName").length();
					if(customerNameLen>50){
						valError = "Invalid value input in Beneficiary Name, Value exceeding field length limit 50 characters for NEFT and CHEQUE refund modes. Please recheck the value";
						validationErrors.add(valError);
						return validationErrors;
					}
				}
				}
				
				/*if("".equalsIgnoreCase(ticket.getString("accountDetail/customerBank"))||ticket.getString("accountDetail/customerBank")==null){
					validationErrors.add("Beneficiary Bank cannot be null or empty for NEFT Refund mode");
					return validationErrors;
				}*/
				
				//
				if(  (ticket.getString("cancellationReason")!=null) &&
					 ("Misrepresentation of Fact".equalsIgnoreCase(ticket.getString("cancellationReason"))||
					    ticket.getString("cancellationReason")=="Misrepresentation of Fact" ) &&
					 (ticket.getString("cancellationOption")!=null) &&
					 ("Nil Refund".equalsIgnoreCase(ticket.getString("cancellationOption"))||
							    ticket.getString("cancellationOption")=="Nil Refund"  || 
					 ("Nill Refund".equalsIgnoreCase(ticket.getString("cancellationOption"))||
							    ticket.getString("cancellationOption")=="Nill Refund"))
					 ) {
					System.out.println("Validation skip for Misrepresentation of fact and Nil Refund");
				}
				else {
				if(!"".equalsIgnoreCase(ticket.getString("accountDetail/customerBank"))&& ticket.getString("accountDetail/customerBank")!=null)
				{
				int customerBankLen = ticket.getString("accountDetail/customerBank").length();
				if(customerBankLen>60){
					valError = "Invalid value input in Beneficiary Bank, Value exceeding field length limit 60 characters. Please recheck the value";
					validationErrors.add(valError);
					return validationErrors;
				}
				}
				if("".equalsIgnoreCase(ticket.getString("accountDetail/customerIfscCode"))||ticket.getString("accountDetail/customerIfscCode")==null){
					validationErrors.add("IFSC Code cannot be blank or empty for NEFT Refund mode");
					return validationErrors;
				}
				String IFSC_Validation = "^[A-Za-z]{4}0[A-Za-z0-9]{6}$";
				if(ticket.getString("accountDetail/customerIfscCode")!=null&&!"".equalsIgnoreCase(ticket.getString("accountDetail/customerIfscCode"))){
					 if(!(ticket.getString("accountDetail/customerIfscCode").matches(IFSC_Validation))){
					        throw new RuntimeException("Invalid value input in Beneficiary IFSC Code, Only value with 4 alphabets followed by 0 followed by 6 alphanumeric characters should be input in Beneficiary IFSC Code and should not contain space in value. Please recheck the value");
					 }
				}
				if("".equalsIgnoreCase(ticket.getString("accountDetail/bankAccountNo"))||ticket.getString("accountDetail/bankAccountNo")==null){
					validationErrors.add("Beneficiary Bank Account Number cannot be blank or empty for NEFT Refund mode");
					return validationErrors;
				}
				if(!"".equalsIgnoreCase(ticket.getString("accountDetail/bankAccountNo"))&& ticket.getString("accountDetail/bankAccountNo")!=null)
				{
				int bankAccountNoLen = ticket.getString("accountDetail/bankAccountNo").length();
				if(bankAccountNoLen>20){
					valError = "Invalid value input in Beneficiary Bank Account Number, Value exceeding field length limit 20 characters. Please recheck the value";
					validationErrors.add(valError);
					return validationErrors;
				}
				}
				
				}
				
			//
			
			}

		}
		//validation for address details
		if(Constants.Cheque.getValue().equalsIgnoreCase(ticket.getString("refundMode"))
				&& !("CANCELLATION ONLY".equalsIgnoreCase(ticket.getString("refundType")))){
			
		
			if("".equalsIgnoreCase(ticket.getString("accountDetail/addressLine1"))||ticket.getString("accountDetail/addressLine1")==null){
				validationErrors.add("Address Line 1 cannot be blank or empty for CHEQUE Refund mode");
				return validationErrors;
			}  
			if(!"".equalsIgnoreCase(ticket.getString("accountDetail/addressLine1"))&& ticket.getString("accountDetail/addressLine1")!=null)
			{
			int addressLine1Len = ticket.getString("accountDetail/addressLine1").length();
			if(addressLine1Len>35){
				valError = "Invalid value input in Address Line 1, Value exceeding field length limit 35 characters. Please recheck the value";
				validationErrors.add(valError);
				return validationErrors;
			}
			}
			/*
			if("".equalsIgnoreCase(ticket.getString("accountDetail/addressLine2"))||ticket.getString("accountDetail/addressLine2")==null){
				validationErrors.add("Address Line 2 cannot be blank or empty for CHEQUE Refund mode");
				return validationErrors;
			}*/
			
			if(!"".equalsIgnoreCase(ticket.getString("accountDetail/addressLine2"))&& ticket.getString("accountDetail/addressLine2")!=null)
			{
			int addressLine2Len = ticket.getString("accountDetail/addressLine2").length();
			if(addressLine2Len>35){
				valError = "Invalid value input in Address Line 2, Value exceeding field length limit 35 characters. Please recheck the value";
				validationErrors.add(valError);
				return validationErrors;
			}
			}
			/*
			if("".equalsIgnoreCase(ticket.getString("accountDetail/addressLine3"))||ticket.getString("accountDetail/addressLine3")==null){
				validationErrors.add("Address Line 3 cannot be blank or empty for CHEQUE Refund mode");
				return validationErrors;
			}*/
			if(!"".equalsIgnoreCase(ticket.getString("accountDetail/addressLine3"))&& ticket.getString("accountDetail/addressLine3")!=null)
			{
			int addressLine3Len = ticket.getString("accountDetail/addressLine3").length();
			if(addressLine3Len>35){
				valError = "Invalid value input in Address Line 3, Value exceeding field length limit 35 characters. Please recheck the value";
				validationErrors.add(valError);
				return validationErrors;
			}
			}
			
			if(!"".equalsIgnoreCase(ticket.getString("accountDetail/landmark"))&& ticket.getString("accountDetail/landmark")!=null)
			{
			int landmarkLen = ticket.getString("accountDetail/landmark").length();
			if(landmarkLen>35){
				valError = "Invalid value input in Landmark, Value exceeding field length limit 35 characters. Please recheck the value";
				validationErrors.add(valError);
				return validationErrors;
			}
			}  
			
			if(!"".equalsIgnoreCase(ticket.getString("accountDetail/district"))&& ticket.getString("accountDetail/district")!=null)
			{
			int districtLen = ticket.getString("accountDetail/district").length();
			if(districtLen>35){
				valError = "Invalid value input in District, Value exceeding field length limit 35 characters. Please recheck the value";
				validationErrors.add(valError);
				return validationErrors;
			}
			}    
			
			/*
			if("".equalsIgnoreCase(ticket.getString("accountDetail/state"))||ticket.getString("accountDetail/state")==null){
				validationErrors.add("State cannot be blank or empty for CHEQUE Refund mode");
				return validationErrors;
			}*/
			if(!"".equalsIgnoreCase(ticket.getString("accountDetail/state"))&& ticket.getString("accountDetail/state")!=null)
			{
			int stateLen = ticket.getString("accountDetail/state").length();
			if(stateLen>30){
				valError = "Invalid value input in State, Value exceeding field length limit 30 characters. Please recheck the value";
				validationErrors.add(valError);
				return validationErrors;
			}
			   /*
			     String statePattern = "^[a-zA-z]+([\\s][a-zA-Z]+)*$";
			
				 if(!(ticket.getString("accountDetail/state").matches(statePattern))){
				        throw new RuntimeException("Invalid value input in State, Only alphabets should be input in State. Please recheck the value");
				 }*/
		     }
			/*
			if("".equalsIgnoreCase(ticket.getString("accountDetail/city"))||ticket.getString("accountDetail/city")==null){
				validationErrors.add("City cannot be blank or empty for CHEQUE Refund mode");
				return validationErrors;
			}*/
			if(!"".equalsIgnoreCase(ticket.getString("accountDetail/city"))&& ticket.getString("accountDetail/city")!=null)
			{
			int cityLen = ticket.getString("accountDetail/city").length();
			if(cityLen>30){
				valError = "Invalid value input in City, Value exceeding field length limit 30 characters. Please recheck the value";
				validationErrors.add(valError);
				return validationErrors;
			}
			/*
			     String cityPattern = "^[a-zA-z]+([\\s][a-zA-Z]+)*$";
			
				 if(!(ticket.getString("accountDetail/city").matches(cityPattern))){
				        throw new RuntimeException("Invalid value input in City, Only alphabets should be input in State. Please recheck the value");
				 }
			*/
		}
			/*
			if("".equalsIgnoreCase(ticket.getString("accountDetail/area"))||ticket.getString("accountDetail/area")==null){
				validationErrors.add("Area cannot be blank or empty for CHEQUE Refund mode");
				return validationErrors;
			}*/
			if(!"".equalsIgnoreCase(ticket.getString("accountDetail/area"))&& ticket.getString("accountDetail/area")!=null)
			{
			int areaLen = ticket.getString("accountDetail/area").length();
			if(areaLen>30){
				valError = "Invalid value input in Area, Value exceeding field length limit 30 characters. Please recheck the value";
				validationErrors.add(valError);
				return validationErrors;
			}
			}
			if("".equalsIgnoreCase(ticket.getString("accountDetail/pincode"))||ticket.getString("accountDetail/pincode")==null){
				validationErrors.add("Pincode cannot be blank or empty for CHEQUE Refund mode");
				return validationErrors;
			}  
		
			if(!"".equalsIgnoreCase(ticket.getString("accountDetail/pincode"))&& ticket.getString("accountDetail/pincode")!=null)
			{
			
			
			     String pincodePattern = "^[0-9]{6}$";
			
				 if(!(ticket.getString("accountDetail/pincode").matches(pincodePattern))){
				        throw new RuntimeException("Invalid value input in Pincode, Only 6 digits numeric value should be input in Pincode. Please recheck the value");
				 }
		}
			
		}
		
		
		if(!("CANCELLATION ONLY".equalsIgnoreCase(ticket.getString("refundType")))){
		if (ticket.get("accountDetail") != null) {
			
			String pattern = "^[a-zA-Z0-9,. ]*$";
			if(ticket.getString("accountDetail/customerBank")!=null&&!"".equalsIgnoreCase(ticket.getString("accountDetail/customerBank"))){
				 if(!(ticket.getString("accountDetail/customerBank").matches(pattern))){
				        throw new RuntimeException("Invalid value input in Beneficiary Bank Name, Only alphanumerics value should be input in Beneficiary Bank Name. Please recheck the value");
				 }
			}
			
			String bankAccountNoPattern = "^[a-zA-Z0-9]*$";
			if(ticket.getString("accountDetail/bankAccountNo")!=null&&!"".equalsIgnoreCase(ticket.getString("accountDetail/bankAccountNo"))){
				 if(!(ticket.getString("accountDetail/bankAccountNo").matches(bankAccountNoPattern))){
				        throw new RuntimeException("Invalid value input in Beneficiary Bank Account Number, Only alphanumerics value should be input in Beneficiary Bank Account Number and should not contain space in value. Please recheck the value");
				 }
			}
			
			
			if(ticket.getString("accountDetail/bankAccountNoVerification")!=null&&!"".equalsIgnoreCase(ticket.getString("accountDetail/bankAccountNoVerification"))){
				 if(!(ticket.getString("accountDetail/bankAccountNoVerification").matches(bankAccountNoPattern))){
				        throw new RuntimeException("Invalid value input in Beneficiary Bank Account Number Verification, Only alphanumerics value should be input in Beneficiary Bank Account Number Verification and should not contain space in value. Please recheck the value");
				 }
			}
			
			String contactNoPattern = "^[0-9]{10}$";
			if(ticket.getString("accountDetail/contactNo")!=null&&!"".equalsIgnoreCase(ticket.getString("accountDetail/contactNo"))){
				 if(!(ticket.getString("accountDetail/contactNo").matches(contactNoPattern))){
				        throw new RuntimeException("Invalid value input in Contact No, Only 10 digits numeric value should be input in Contact No. Please recheck the value");
				 }
			}
			if(ticket.getList("receiptDetail")!=null && ticket.getList("receiptDetail").size()!=0 && ticket.get("receiptDetail[0]")!=null)
			{
				if("GC Receipt".equalsIgnoreCase(ticket.getString("accountDetail/accountSource")) && Constants.Cheque.getValue().equalsIgnoreCase(ticket.getString("receiptDetail[0]/payementMode")))
				{
				if("".equalsIgnoreCase(ticket.getString("ocrOmniDocumentsURL"))||ticket.getString("ocrOmniDocumentsURL")==null){
					validationErrors.add("OCR Omni Documents URL cannot be blank or empty for CHEQUE Refund mode");
					return validationErrors;
				} 
				}
			}
			
			
		
		}
		}
		

		if (ticket.get("refund" +
				"Type").equals(
						Constants.CancellationRefund.getValue())) {
			validationErrors = validateCancellationMode(ticket);
		} else if(ticket.get("refund" +
				"Type").equals(
						Constants.ExcessRefund.getValue())){
			validationErrors = validateExcessRefundMode(ticket);
		}

		/*
		 * if(ticket.getString("product")==null ||
		 * "".equalsIgnoreCase(ticket.getString("product"))) {
		 * validationErrors.add("product cannot be blank or empty"); }
		 */
		/*
		 * if(ticket.getString("source")==null ||
		 * "".equalsIgnoreCase(ticket.getString("source"))) {
		 * validationErrors.add("source cannot be blank or empty"); }
		 */
		if (ticket.getString("lob") == null
				|| "".equalsIgnoreCase(ticket.getString("lob"))) {
			validationErrors.add("LOB cannot be blank or empty");
		}
		return validationErrors;
	}

	/**
	 * 
	 * This method is used to validate the fields related to
	 * "Cancellation Refund" mode
	 * 
	 * @param ticket
	 * @return
	 * @throws ParseException
	 */
	public List<String> validateCancellationMode(DataObject ticket)
			throws ParseException
			{
		System.out.println("Enter Into validateCancellationMode"+ticket.getString("ticketNo"));
		List<String> validationErrors = new ArrayList();
	
		if ((ticket.getString("policyNo") == null) || 
				("".equalsIgnoreCase(ticket.getString("policyNo")))) {
			validationErrors.add("Policy Number cannot be blank or empty");
		}

		String proposalNoPattern = "^[a-zA-Z0-9]*$";
		
		if(ticket.getString("policyNo")!=null&&!"".equalsIgnoreCase(ticket.getString("policyNo"))){
			 if(!(ticket.getString("policyNo").matches(proposalNoPattern))){
			        throw new RuntimeException("Invalid value input in Policy Number, Only alphanumerics value should be input in Policy Number and should not contain space in value. Please recheck the value");
			 }
		} 
		
		if(ticket.getString("applicationNo")!=null&&!"".equalsIgnoreCase(ticket.getString("applicationNo"))){
			 if(!(ticket.getString("applicationNo").matches(proposalNoPattern))){
			        throw new RuntimeException("Invalid value input in Proposal Number, Only alphanumerics value should be input in Proposal Number and should not contain space in value. Please recheck the value");
			 }
		}

		if ((ticket.getString("cancellationReason") == null) || 
				("".equalsIgnoreCase(ticket.getString("cancellationReason")))) {
			validationErrors.add("Cancellation Reason cannot be blank or empty");
		}
		if("Motor".equalsIgnoreCase(ticket.getString("lob")))
		{
			if("Request From Insured- Double Insurance(Information on the other insurance details cancellation)".equalsIgnoreCase(ticket.getString("cancellationReason"))||"Double insurance".equalsIgnoreCase(ticket.getString("cancellationReason"))){
				if(ticket.getString("officeCodeAddress")==null || "".equalsIgnoreCase(ticket.getString("officeCodeAddress"))){
					validationErrors.add("Office Code and Address cannot be blank or empty");
					return validationErrors;
				}
				 String officeCodePattern = "^[0-9]{4}$";
				 if(!(ticket.getString("officeCodeAddress").matches(officeCodePattern))){
				        throw new RuntimeException("Office code should be 4 digits only");
				 }
				if(ticket.getString("renewedPolicyNo")==null || "".equalsIgnoreCase(ticket.getString("renewedPolicyNo"))){
					validationErrors.add("Double insurance policy number cannot be blank or empty");
				}
				
				if(ticket.getString("coverType")==null || "".equalsIgnoreCase(ticket.getString("coverType"))){
					validationErrors.add("Alternate policy cover type cannot be blank or empty");
				}
				
				if(ticket.getString("nameOfInsureedCompany")==null || "".equalsIgnoreCase(ticket.getString("nameOfInsureedCompany"))){
					validationErrors.add("Insurance company of alternate policy cannot be blank or empty");
				}
				if(ticket.getDate("periodFromDate")==null){
					validationErrors.add("Alternate policy start date cannot be blank or empty");
				}
				
				if(ticket.getDate("periodToDate")==null){
					validationErrors.add("Alternate policy end date cannot be blank or empty");
				}
			}
		}
		if ((ticket.getString("cancellationOption") == null) || 
				("".equalsIgnoreCase(ticket.getString("cancellationOption")))) {
			validationErrors.add("Cancellation Option cannot be blank or empty");
		}
		//validation for balance days for LTA lob and cancellation option Pro-Rata Refund
		if("LTA".equalsIgnoreCase(ticket.getString("lob")) && "Pro-Rata Refund".equalsIgnoreCase(ticket.getString("cancellationOption")))
		{
			int daysBetween=0;
		 
			 Calendar cal = Calendar.getInstance();
		     cal.setTime(ticket.getDate("effectiveDate"));
		     cal.set(Calendar.HOUR_OF_DAY, 0);
		     cal.set(Calendar.MINUTE, 0);
		     cal.set(Calendar.SECOND, 0);
		     cal.set(Calendar.MILLISECOND, 0);
			if(ticket.getDate("effectiveDate")!=null && ticket.getDate("policyDetail/policyEndDate")!= null)
			{
			daysBetween = (int) Math.abs(Math.floor(ticket.getDate("policyDetail/policyEndDate").getTime() - cal.getTimeInMillis())/(3600 * 24 * 1000)) ;
			System.out.println("Effective Date time"+cal.getTimeInMillis());
			System.out.println("Days Between policy end date and effective date"+daysBetween);
			if(ticket.getInt("balanceDays") != daysBetween)
			{
				validationErrors.add("Invalid value input in Balance Days, Balance Days must be exactly difference between policy end date and cancellation effective date");
			}
			}
			
		}
		
		//validation for Minimum premium retain amount field for liability lob, product 2770 , Trade and Credit LOB and Health lob,product 2890 and cancellation option Partial Refund
		if("Trade Credit".equalsIgnoreCase(ticket.getString("lob")) ||("Liability".equalsIgnoreCase(ticket.getString("lob"))&&"2770".equals(ticket.getString("product")))|| ("Health".equalsIgnoreCase(ticket.getString("lob")) && ("2890".equals(ticket.getString("product")) || "2808".equals(ticket.getString("product"))) && "Partial Refund".equalsIgnoreCase(ticket.getString("cancellationOption"))))
		{
			if("Liability".equalsIgnoreCase(ticket.getString("lob"))&&"2770".equals(ticket.getString("product"))&& ticket.getDouble("minPremiumRetainAmount")<2500.00)
			{
				validationErrors.add("Invalid value input in Minimum Premium Retain Amount, Minimum Premium Retain Amount cannot be less than 2500. Please recheck the value");
			}
			if(ticket.getDouble("minPremiumRetainAmount")>ticket.getDouble("policyDetail/policyPremium"))
			{
				validationErrors.add("Invalid value input in Minimum Premium Retain Amount, Minimum Premium Retain Amount cannot be greater than policy premium : "+ticket.getDouble("policyDetail/policyPremium")+". Please recheck the value");
			}
			
		}
		//Refund amount field validation for liability lob and product 2771,2772,2774&2776, Financial Line lob and product 6105,6106,6107,6108, 6109 & 6101, Marine Cargo Lob and product 2151& 2154 and Partial Refund, Aviation Lob
		
		if(("Liability".equalsIgnoreCase(ticket.getString("lob"))&&("2771".equals(ticket.getString("product")) || "2772".equals(ticket.getString("product")) || "2774".equals(ticket.getString("product")) || "2776".equals(ticket.getString("product")))) || ("Financial Line".equalsIgnoreCase(ticket.getString("lob")) && ("6105".equals(ticket.getString("product")) || "6106".equals(ticket.getString("product")) || "6108".equals(ticket.getString("product")) || "6107".equals(ticket.getString("product")) || "6109".equals(ticket.getString("product")) ||  "6101".equals(ticket.getString("product")))) || ("Marine Cargo".equalsIgnoreCase(ticket.getString("lob")) &&("2151".equals(ticket.getString("product")) || "2154".equals(ticket.getString("product"))) &&  "Partial Refund".equalsIgnoreCase(ticket.getString("cancellationOption"))) || "Aviation".equalsIgnoreCase(ticket.getString("lob")))
		{
		if(ticket.getDouble("refundAmount")>ticket.getDouble("policyDetail/policyPremium"))
		{
		validationErrors.add("Invalid value input in Refund Amount, Refund Amount cannot be greater than policy premium : "+ticket.getDouble("policyDetail/policyPremium")+". Please recheck the value");
		}
		}
		//Endorsement amount and Endorsement Premium for Environmental Relief Fund field validation for liability lob and product 2771
		if("Liability".equalsIgnoreCase(ticket.getString("lob")) &&"2771".equals(ticket.getString("product")))
		{
			if(ticket.getDouble("endorsementAmount")>ticket.getDouble("policyDetail/policyPremium"))
			{
			validationErrors.add("Invalid value input in Endorsement Amount, Endorsement Amount cannot be greater than policy premium : "+ticket.getDouble("policyDetail/policyPremium")+". Please recheck the value");
			}
			if(ticket.getDouble("endorsementERFAmount")>ticket.getDouble("policyDetail/policyPremium"))
			{
			validationErrors.add("Invalid value input in Endorsement Premium for Environmental Relief Fund, Endorsement Premium for Environmental Relief Fund cannot be greater than policy premium : "+ticket.getDouble("policyDetail/policyPremium")+". Please recheck the value");
			}
		}
		//Agreed Premium Amount field validation for Aig combined lob and cancellationOption Agreed Premium Cancellation
		if("Aig Combined".equalsIgnoreCase(ticket.getString("lob")) &&  "Agreed Premium Cancellation".equalsIgnoreCase(ticket.getString("cancellationOption")))
		{
			if(ticket.getDouble("aigAgreedPremiumAmount")>ticket.getDouble("policyDetail/policyPremium"))
			{
			validationErrors.add("Invalid value input in Endorsement Premium for Agreed Premium Amount, Agreed Premium Amount cannot be greater than policy premium : "+ticket.getDouble("policyDetail/policyPremium")+". Please recheck the value");
			}
		}
		if(ticket.getString("cancellationReason").equalsIgnoreCase("Others")){
			
			if(ticket.getString("otherReason")==null||"".equalsIgnoreCase(ticket.getString("otherReason"))){
				validationErrors.add("Other Reason cannot be blank or empty");
				return validationErrors;
			}
			int othersLen = ticket.getString("otherReason").length();
			if(othersLen>400){
				validationErrors.add("Other Reason length cannot be more than 400 characters.");
				return validationErrors;
			}
			 String otherReasonPattern = "^[0-9a-zA-Z., ]*$";
			 if(!(ticket.getString("otherReason").matches(otherReasonPattern))){
				 validationErrors.add("Other Reason should contain alphanumerics Only");
					return validationErrors;  
			 }
		}

		if (ticket.getDate("requestedDate") == null) {
			validationErrors.add("Requested Date cannot be blank or empty");
		}

		if (ticket.getDate("effectiveDate") == null) {
			validationErrors.add("Effective Date cannot be blank or empty");
		}
        
		/*
		if (ticket.getString("cancellationEffectiveTime") == null) {
			validationErrors.add("Cancellation Effective Time cannot be blank or empty");
		}*/


		if("Request From Insured-Sale of Vehicle".equalsIgnoreCase(ticket.getString("cancellationReason"))||"Sale of vehicle".equalsIgnoreCase(ticket.getString("cancellationReason"))){
			if(ticket.getDate("ncbReservingEffectiveDate")==null){
				validationErrors.add("NCB reserving Effective Date cannot be blank or empty");
			}
		}
		
		if (ticket.get("policyDetail") == null) {
			validationErrors.add("policy Details cannot be blank or empty");
		}
		
		
		if (ticket.get("policyDetail") != null) {
			
			if ((ticket.getString("policyDetail/policyNo") == null) || 

					("".equalsIgnoreCase(ticket.getString("policyDetail/policyNo")))) {
				validationErrors.add("Policy Number Cannot be blank or empty");
			}
	
			
			if(ticket.getString("policyDetail/policyNo")!=null&&!"".equalsIgnoreCase(ticket.getString("policyDetail/policyNo"))){
				 if(!(ticket.getString("policyDetail/policyNo").matches(proposalNoPattern))){
				        throw new RuntimeException("Invalid value input in Policy Number, Only alphanumerics value should be input in Policy Number and should not contain space in value. Please recheck the value");
				 }
			}
			
			if ((ticket.getString("policyDetail/proposalNo") == null) || 

					("".equalsIgnoreCase(ticket.getString("policyDetail/proposalNo")))) {
				validationErrors.add("Proposal Number Cannot be blank or empty");
			}
			
			if(ticket.getString("policyDetail/proposalNo")!=null&&!"".equalsIgnoreCase(ticket.getString("policyDetail/proposalNo"))){
				 if(!(ticket.getString("policyDetail/proposalNo").matches(proposalNoPattern))){
				        throw new RuntimeException("Invalid value input in Proposal Number, Only alphanumerics value should be input in Proposal Number and should not contain space in value. Please recheck the value");
				 }
			}

			if (ticket.getDate("policyDetail/policyStartDate") == null)
			{
				validationErrors.add("Policy  Start Date cannot be blank or empty");
			}

			if (ticket.getDate("policyDetail/policyEndDate") == null)
			{
				validationErrors.add("Policy  End Date cannot be blank or empty");
			}

			if ((ticket.getDate("policyDetail/policyStartDate") != null) && 
					(ticket.getDate("policyDetail/policyEndDate") != null)) {
				Date policyStartDate = formatDate(ticket
						.getDate("policyDetail/policyStartDate"));
				Date policyEndDate = formatDate(ticket
						.getDate("policyDetail/policyEndDate"));
				Date effectiveDate = formatDate(ticket.getDate("effectiveDate"));

				int comparison = effectiveDate.compareTo(formatDate(ticket.getDate("requestedDate")));



				if(  (ticket.getString("cancellationReason")!=null) &&
						 ("Misrepresentation of Fact".equalsIgnoreCase(ticket.getString("cancellationReason"))||
						    ticket.getString("cancellationReason")=="Misrepresentation of Fact" ) &&
						 (ticket.getString("cancellationOption")!=null) &&
						 ("Nil Refund".equalsIgnoreCase(ticket.getString("cancellationOption"))||
								    ticket.getString("cancellationOption")=="Nil Refund"  || 
						 ("Nill Refund".equalsIgnoreCase(ticket.getString("cancellationOption"))||
								    ticket.getString("cancellationOption")=="Nill Refund"))
						 ) {
						System.out.println("Validation skip for Misrepresentation of fact and Nil Refund");
					}
				else {
				if ((effectiveDate.after(policyStartDate)) && (policyEndDate.before(effectiveDate)))
				{
					throw new RuntimeException("Received Date should be Less than Requested Date and in Between Policy Start Date and Policy End Date");
				}
				}
				
				
				
				
				
				
				if("Request From Insured-Sale of Vehicle".equalsIgnoreCase(ticket.getString("cancellationReason"))||"Sale of vehicle".equalsIgnoreCase(ticket.getString("cancellationReason")))
				{
				Date ncbReservingEffectiveDate = formatDate(ticket.getDate("ncbReservingEffectiveDate"));
				if(ncbReservingEffectiveDate.before(policyStartDate) || ncbReservingEffectiveDate.after(ticket.getDate("requestedDate")))
				{
					throw new RuntimeException("NCB reserving effective Date should be between policy start date and cancellation requested date");
				}
				}
			}
			/*
			if ((ticket.getString("refundMode") != null) && 
					(ticket.getString("refundMode") != ""))
			{
				if ((ticket.getString("refundMode").equalsIgnoreCase(Constants.DebitAuthorization.getValue())) && (
						(ticket.getString("policyDetail/authCode") == null) || 

						("".equalsIgnoreCase(ticket.getString("policyDetail/authCode")))))
				{
					validationErrors.add("AuthCode Transaction Number cannot be blank or empty for Debit Authorization Refund Mode");
				}
			}*/
		}



		return validationErrors;
			}
	/**
	 * This method is used to validate the fields related to "Excess Refund"
	 * mode
	 * 
	 * @param ticket
	 * @return
	 */
	public boolean refundAmountValidation(Double balanceAmount,
			Double totalAmount, Double crsAmount) {
		System.out.println("Enter Into refundAmountValidation");
		BigDecimal differenceAmount = new BigDecimal(crsAmount - totalAmount).setScale(2, RoundingMode.HALF_UP);
        double differenceDouble = differenceAmount.doubleValue();
		System.out.println("Differnce between crs amount and total amount "+differenceDouble);

		if (balanceAmount < 0) {
			return false;
		}
		if (balanceAmount > differenceDouble) {
			return false;
		} else
			return true;
	}

	public List<String> validateExcessRefundMode(DataObject ticket) {
		System.out.println("Enter Into validateExcessRefundMode"+ticket.getString("ticketNo"));
		List<String> validationErrors = new ArrayList();

         if(ticket.getString("refundType")!="NEGATIVE ENDORSEMENT REFUND"){
		if ((ticket.getString("receiptNo") == null) || 
				("".equalsIgnoreCase(ticket.getString("receiptNo")))) {
			validationErrors.add("Receipt Number cannot be blank or empty");
		}
		
        String receiptNoPattern = "^[a-zA-Z0-9]*$";
		if(ticket.getString("receiptNo")!=null&&!"".equalsIgnoreCase(ticket.getString("receiptNo"))){
			 if(!(ticket.getString("receiptNo").matches(receiptNoPattern))){
			        throw new RuntimeException("Invalid value input in Receipt Number, Only alphanumerics value should be input in Receipt Number and should not contain space in value. Please recheck the value");
			 }
		} 

		if ((ticket.getString("refundMode") == null) || 
				("".equalsIgnoreCase(ticket.getString("refundMode")))) {
			validationErrors.add("Refund Mode cannot be blank or empty");
		}
		
		if ((ticket.getString("requestType") == null) || 
				("".equalsIgnoreCase(ticket.getString("requestType")))) {
			validationErrors.add("Request Type cannot be blank or empty");
		}

		if (ticket.getList("receiptDetail") == null)
		{
			validationErrors.add("Receipt Details cannot be blank or empty");
		}

		if ((ticket.getString("refundMode") != null) && 
				(ticket.getString("refundMode") != "")) {
			System.out.println("refund------->" + 
					ticket.getString("refundMode"));
			System.out
			.println("Evaluated to---->" + (
					(ticket.getString("refundMode").replaceAll(" ", 
							"") != Constants.ManualCheque.getValue()) && (ticket
									.getString("refundMode")
									.replaceAll(" ", "") != Constants.SystemCheque
									.getValue())));


			/*if (!ticket.getString("refundMode").replaceAll("_", " ").equals(Constants.ManualCheque.getValue()))
			{
				if (!ticket.getString("refundMode").replaceAll("_", " ").equals(Constants.SystemCheque.getValue()))
				{
					validationErrors.add("Refund Mode should be either Manual Cheque or Auto Cheque");
				}
			} */
		}

		if (ticket.getDouble("refundAmount") < 0.0D) {
			validationErrors.add("Refund Amount cannot be less than 0");
		}
		if (ticket.getDouble("receiptRefundAmount") <= 0.0D) {
			validationErrors.add("Receipt Refund Amount cannot be less than or equal to 0");
		}
		
		System.out.println("Inside validate Excess refund 2 ");
		if (ticket.getList("receiptDetail") != null)
		{
			System.out.println("Inside validate Excess refund 3 ");
			if (ticket.getList("receiptDetail").size() != 0) {
				if (ticket.getDouble("receiptRefundAmount")>ticket.getDouble("receiptDetail[0]/balanceAmount"))
				{
					validationErrors.add("Invalid value input in Receipt Refund Amount. Receipt Refund Amount value cannot exceed balance amount "+ticket.getDouble("receiptDetail[0]/balanceAmount")+". Please recheck the value");
				}
				System.out.println("Inside validate Excess refund 4 ");
				if("Health".equalsIgnoreCase(ticket.getString("lob")))
				{
					double sum=ticket.getDouble("receiptRefundAmount")+ticket.getDouble("PPCAmount");
					if(sum>ticket.getDouble("receiptDetail[0]/balanceAmount"))
					{
						validationErrors.add("Invalid value input in PPCAmount. Sum of Receipt Refund Amount and PPCAmount value cannot exceed balance amount "+ticket.getDouble("receiptDetail[0]/balanceAmount")+". Please recheck the value");
					}
				}
				System.out.println("Inside validate Excess refund 5 ");
			}
			if (ticket.getList("receiptDetail").size() == 0) {
				validationErrors.add("Receipt Details cannot be blank or empty");
			}
			System.out.println("Inside validate Excess refund 6 ");
			for (int i = 0; i < ticket.getList("receiptDetail").size(); i++)
			{
				if (ticket.getDouble("receiptDetail[" + i + "]/crsAmount") == 0.0D) {
					validationErrors.add("CRS Amount cannot be 0");
				}


				System.out.println("Inside validate Excess refund 7 ");



				if (ticket.getDouble("receiptDetail[" + i + "]/balanceAmount") < 0.0D)
				{
					validationErrors.add("Refund Amount cannot be less than Zero");
				}
				System.out.println("Inside validate Excess refund 8 ");
				if ((ticket.getString("refundType").equalsIgnoreCase(Constants.ExcessRefund.getValue())) && 
						(ticket.getDouble("receiptDetail[" + i + "]/crsAmount") != 0.0D) && 
						(ticket.getDouble("receiptDetail[" + i + 
								"]/totalAmount") != 0.0D) /* && !ticket.getString("searchParameter").equalsIgnoreCase("CD NUMBER") */)
				{

					System.out.println("Inside validate Excess refund 9 ");



					/*if (!refundAmountValidation(Double.valueOf(ticket.getDouble("refundAmount")), Double.valueOf(ticket.getDouble("receiptDetail[" + i + "]/totalAmount")), Double.valueOf(ticket.getDouble("receiptDetail[" + i + "]/crsAmount"))))
					{
						validationErrors.add("Refund Amount should be less than or equal to the difference between CRS Amount & Total Amount");
					}  */
				}
			}
		}


		 }

		return validationErrors;
	}
	public void saveTicketHistory(DataObject ticket) throws ParseException {
		System.out.println("Enter Into saveTicketHistory"+ticket.getString("ticket_no"));
		DataObject ticketHistory = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticket_hist",
						"DboRas_Ticket_Hist");
		DataObject ticketHistoryBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticket_histbg",
						"DboRas_Ticket_HistBG");
		// ticketHistory.setDouble("id", 545);
		// set Ticket Parameters
		if (ticket.getString("lob") != null)
			ticketHistory.setString("lob", ticket.getString("lob"));
		if (ticket.getString("stage") != null)
			ticketHistory.setString("stage", ticket.getString("stage"));
		if (ticket.getString("policy_no") != null)
			ticketHistory.setString("policy_no", ticket.getString("policy_no"));
		if (ticket.getString("application_no") != null)
			ticketHistory.setString("application_no",
					ticket.getString("application_no"));
		if (ticket.getString("rcs_no") != null)
			ticketHistory.setString("rcs_no", ticket.getString("rcs_no"));
		if (ticket.getString("request_type") != null)
			ticketHistory.setString("request_type", ticket.getString("request_type"));
		if (ticket.getString("ticket_type") != null)
			ticketHistory.setString("ticket_type", ticket.getString("ticket_type"));
		if (ticket.getString("discrepancy_type") != null)
			ticketHistory.setString("discrepancy_type", ticket.getString("discrepancy_type"));
		if (ticket.getString("ftr_description") != null)
			ticketHistory.setString("ftr_description", ticket.getString("ftr_description"));
		if (ticket.getString("lan_number") != null)
			ticketHistory.setString("lan_number", ticket.getString("lan_number"));
		if (ticket.getString("ocr_omni_documents_url") != null)
			ticketHistory.setString("ocr_omni_documents_url", ticket.getString("ocr_omni_documents_url"));
		if (ticket.getDate("requested_date") != null)
			ticketHistory.setDate("ticket_request_on",
					ticket.getDate("requested_date"));
		else{
			ticketHistory.setDate("ticket_request_on",formatDate(new Date()));
		}
		
		if (ticket.getDouble("refund_amount") != 0){
			double refundAmount  = ticket.getDouble("refund_amount");
			BigDecimal refundAmountDecimal = BigDecimal.valueOf(refundAmount); 
			//float floatRefAmount = (float)refundAmount;
			ticketHistory.setBigDecimal("refund_amount", refundAmountDecimal);
		}
		
		if (ticket.getDouble("premium_calculation_amount") != 0){
			double premiumCalculationAmount  = ticket.getDouble("premium_calculation_amount");
			BigDecimal premiumCalculationAmountDecimal = BigDecimal.valueOf(premiumCalculationAmount); 
			//float floatRefAmount = (float)refundAmount;
			ticketHistory.setBigDecimal("premium_calculation_amount", premiumCalculationAmountDecimal);
		}
		
		if (ticket.getDouble("receipt_refund_amount") != 0){
			double receiptrefundAmount  = ticket.getDouble("receipt_refund_amount");
			BigDecimal receiptrefundAmountDecimal = BigDecimal.valueOf(receiptrefundAmount); 
			//float floatreceiptrefundAmount = (float)receiptrefundAmount;
			ticketHistory.setBigDecimal("receipt_refund_amount",
					receiptrefundAmountDecimal);
		}
		
		if (ticket.getDouble("ppc_amount") != 0){
			double ppcAmount  = ticket.getDouble("ppc_amount");
			BigDecimal ppcAmountDecimal = BigDecimal.valueOf(ppcAmount); 
			//float floatppcAmount = (float)ppcAmount;
			ticketHistory.setBigDecimal("ppc_amount",
					ppcAmountDecimal);
		}
		
		if (ticket.getDouble("min_premium_retain_amount") != 0){
			double minPremiumRetainAmount = ticket.getDouble("min_premium_retain_amount");
			BigDecimal minPremiumRetainAmountDecimal = BigDecimal.valueOf(minPremiumRetainAmount); 
			//float floatminPremiumRetainAmount = (float)minPremiumRetainAmount;
			ticketHistory.setBigDecimal("min_premium_retain_amount", minPremiumRetainAmountDecimal);
		}
		
		if (ticket.getDouble("exchange_rate") != 0)
		{
		double exchangeRate = ticket.getDouble("exchange_rate");
		BigDecimal exchangeRateDecimal = BigDecimal.valueOf(exchangeRate); 
		//float floatexchangeRate = (float)exchangeRate;
		ticketHistory.setBigDecimal("exchange_rate", exchangeRateDecimal);
		}
		
		if (ticket.getDouble("endorsement_amount") != 0)
		{
		double endorsementAmount = ticket.getDouble("endorsement_amount");
		BigDecimal endorsementAmountDecimal = BigDecimal.valueOf(endorsementAmount); 
		//float floatendorsementAmount = (float)endorsementAmount;
		ticketHistory.setBigDecimal("endorsement_amount", endorsementAmountDecimal);
		}
		
		if (ticket.getDouble("endorsement_erf_amount") != 0)
		{
		double endorsementERFAmount = ticket.getDouble("endorsement_erf_amount");
		BigDecimal endorsementERFAmountDecimal = BigDecimal.valueOf(endorsementERFAmount); 
		//float floatendorsementERFAmount = (float)endorsementERFAmount;
		ticketHistory.setBigDecimal("endorsement_erf_amount", endorsementERFAmountDecimal);
		}
		
		if (ticket.getDouble("agreed_premium_amount") != 0)
		{
		double aigAgreedPremiumAmount = ticket.getDouble("agreed_premium_amount");
		BigDecimal aigAgreedPremiumAmountDecimal = BigDecimal.valueOf(aigAgreedPremiumAmount); 
		//float floataigAgreedPremiumAmount = (float)aigAgreedPremiumAmount;
		ticketHistory.setBigDecimal("agreed_premium_amount", aigAgreedPremiumAmountDecimal);
		}
		
		if (ticket.getDouble("commercial_refund_amount") != 0)
		{
		double commercialRefundAmount = ticket.getDouble("commercial_refund_amount");
		BigDecimal commercialRefundAmountDecimal = BigDecimal.valueOf(commercialRefundAmount); 
		//float floatcommercialRefundAmount = (float)commercialRefundAmount;
		ticketHistory.setBigDecimal("commercial_refund_amount", commercialRefundAmountDecimal);
		}
		
		if (ticket.getString("icrm_ticket_number") != null)
			ticketHistory.setString("icrm_ticket_number",
					ticket.getString("icrm_ticket_number"));
		
		if (ticket.getString("icrm_username") != null)
			ticketHistory.setString("icrm_username",
					ticket.getString("icrm_username"));
		
		if (ticket.getString("icrm_customer_id") != null)
			ticketHistory.setString("icrm_customer_id",
					ticket.getString("icrm_customer_id"));
		
		if (ticket.getString("subreceipt") != null)
			ticketHistory.setString("subreceipt",
					ticket.getString("subreceipt"));
	
		ticketHistory.setInt("balance_days",
					ticket.getInt("balance_days"));
		
		if (ticket.getString("ras_draft_ticket_no") != null)
			ticketHistory.setString("ras_draft_ticket_no",
					ticket.getString("ras_draft_ticket_no"));
		
		if (ticket.getString("bops_channel") != null)
			ticketHistory.setString("bops_channel",
					ticket.getString("bops_channel"));
		
		if (ticket.getString("source_unique_id") != null)
			ticketHistory.setString("source_unique_id",
					ticket.getString("source_unique_id"));
		
		if (ticket.getString("source_user_name") != null)
			ticketHistory.setString("source_user_name",
					ticket.getString("source_user_name"));
		
		if (ticket.getString("omni_documents_url") != null)
			ticketHistory.setString("omni_documents_url",
					ticket.getString("omni_documents_url"));
		
		if (ticket.getString("channel_mode") != null)
			ticketHistory.setString("channel_mode",
					ticket.getString("channel_mode"));
		
		if (ticket.getString("cancellation_reason") != null)
			ticketHistory.setString("ticket_reason",
					ticket.getString("cancellation_reason"));
		if (ticket.getString("ticket_remarks") != null)
			ticketHistory.setString("description",
					ticket.getString("ticket_remarks"));
		if (ticket.getString("comments") != null)
			ticketHistory.setString("comment", ticket.getString("comments"));
		if (ticket.getString("remarks") != null)
			ticketHistory.setString("remark", ticket.getString("remarks"));
		//if (com.ibm.ws.security.core.SecurityContext.getName() != null)
		System.out.println("created by in save ticket history"+	ticket.getString("last_modified_by"));
		ticketHistory.setString("created_by",
					ticket.getString("last_modified_by"));

		ticketHistory.setDate("created_on", formatDate(new Date()));
		if (ticket.getDouble("ticket_id") != 0)
			ticketHistory.setDouble("ticket_id", ticket.getDouble("ticket_id"));
		if (ticket.getString("customer_name") != null)
			ticketHistory.setString("customer_name",
					ticket.getString("customer_name"));
		if (ticket.getString("product") != null)
			ticketHistory.setString("product", ticket.getString("product"));
		if (ticket.getString("status") != null)
			ticketHistory.setString("status", ticket.getString("status"));
		if (ticket.getString("bank") != null)
			ticketHistory.setString("bank", ticket.getString("bank"));
		if (ticket.getString("name_of_insured_company") != null)
			ticketHistory.setString("name_of_insured_company", ticket.getString("name_of_insured_company"));
		if (ticket.getString("cover_type") != null)
			ticketHistory.setString("cover_type", ticket.getString("cover_type"));
		if (ticket.getString("office_code_address") != null)
			ticketHistory.setString("office_code_address", ticket.getString("office_code_address"));
		if (ticket.getString("renewed_policy_no") != null)
			ticketHistory.setString("renewed_policy_no", ticket.getString("renewed_policy_no"));
		if (ticket.getDate("period_from_date") != null)
			ticketHistory.setDate("period_from_date", ticket.getDate("period_from_date"));
		if (ticket.getDate("period_to_date") != null)
			ticketHistory.setDate("period_to_date", ticket.getDate("period_to_date"));
		if (ticket.getDate("voucher_generated_date") != null)
			ticketHistory.setDate("voucher_generated_date", formatDate(ticket.getDate("voucher_generated_date")));
		if (ticket.getString("ncb_reserving_effective_date") != null)
			ticketHistory.setDate("ncb_reserving_effective_date", ticket.getDate("ncb_reserving_effective_date"));


		ticketHistoryBG.setDataObject("DboRas_Ticket_Hist", ticketHistory);
		// call create ticket history method
		try {
		locateService_DBInterfacePartner().invoke("createDboRas_Ticket_HistBG",
				ticketHistoryBG);
		 } catch (Exception e) {
		// Handle the Exception Raised While SavingTicketHistory
			 throw new RuntimeException(e.getMessage());
		}
	}

	public DataObject prepareTicketResponse(DataObject target,DataObject ticketSource,DataObject policySource,DataObject refundSource,List receiptSource,DataObject accountSource,List insuredSource) {
		System.out.println("Enter Into prepareTicketResponse"+ticketSource.getString("ticket_no"));
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
		target.setBoolean("uploadedTo", ticketSource.getBoolean("upload_to"));
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

	public void saveAccountHistory(DataObject accountDetail) throws ParseException {
		System.out.println("Enter Into saveAccountHistory"+accountDetail.getDouble("ticket_id"));
		DataObject accountHistory = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_account_detail_hist",
						"DboRas_Account_Detail_Hist");
		DataObject accountHistoryBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_account_detail_histbg",
						"DboRas_Account_Detail_HistBG");
		// set account history parameters
		// accountHistory.setDouble("slno",32);
		accountHistory.setDouble("account_id",
				accountDetail.getDouble("account_id"));
		accountHistory.setDouble("ticket_id",
				accountDetail.getDouble("ticket_id"));
		accountHistory.setString("payee_name",
				accountDetail.getString("payee_name"));
		accountHistory.setString("bank_name",
				accountDetail.getString("bank_name"));
		accountHistory.setString("ifsc_code",
				accountDetail.getString("ifsc_code"));
		accountHistory.setString("account_no",
				accountDetail.getString("account_no"));
		accountHistory.setString("source", accountDetail.getString("source"));
		if(accountDetail.getString("email_id")!=null && accountDetail.getString("email_id")!="")
		{
			accountHistory.setString("email_id", accountDetail.getString("email_id"));
		}
		accountHistory.setString("contact_no",
				accountDetail.getString("contact_no"));
		accountHistory.setString("created_by",
				accountDetail.getString("last_modified_by"));
		accountHistory.setDate("created_on",
				accountDetail.getDate("last_modified_on"));
		
		accountHistory.setString("address_line1",
				accountDetail.getString("address_line1"));
		accountHistory.setString("address_line2",
				accountDetail.getString("address_line2"));
		accountHistory.setString("address_line3",
				accountDetail.getString("address_line3"));
		accountHistory.setString("state",
				accountDetail.getString("state"));
		accountHistory.setString("city",
				accountDetail.getString("city"));
		accountHistory.setString("area",
				accountDetail.getString("area"));
		accountHistory.setString("pincode",
				accountDetail.getString("pincode"));
		accountHistory.setString("bank_account_verification",
				accountDetail.getString("bank_account_verification"));
		accountHistory.setString("account_source",
				accountDetail.getString("account_source"));
		accountHistory.setString("landmark",
				accountDetail.getString("landmark"));
		accountHistory.setString("district",
				accountDetail.getString("district")); 
		accountHistoryBG.setDataObject("DboRas_Account_Detail_Hist",
				accountHistory);
		// fire create account history method
		try {
			locateService_DBInterfacePartner().invoke(
					"createDboRas_Account_Detail_HistBG", accountHistoryBG);
		} catch (Exception e) {
			// Handle the Exception Raised While SavingAccountHistory
			throw new RuntimeException(e.getMessage());
		}
	}

	public DataObject saveTicket(DataObject ticket)
			throws ParseException, NoSuchFieldException, SecurityException
			{
		System.out.println("Entered into Save Ticket in IID ");
		DataObject responseBO = null;
		String applicationNo = null;
		DataObject ticketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticketbg", 
						"DboRas_TicketBG");
		DataObject ticketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticket", 
						"DboRas_Ticket");

		DataObject rasException = getBOFactory().create("http://RACASBO", 
				"RASException");

		DataObject exceptionData = null;
		if (ticket == null) {
			throw new RuntimeException(
					"Save Ticket Service Failed: ticket object cannot be empty");
		}
		
		System.out.println("Entered into Save Ticket in IID 2 ");
		
		
		System.out.println("Enter Into saveTicket"+ticket.getString("ticketNo"));

		/* if ((ticket.getString("accountDetail/emailId") == null) || (ticket.getString("accountDetail/emailId") == "")) {
		      throw new RuntimeException("Email ID cannot be blank");
		    }
		 */

		if(ticket.getString("accountDetail/emailId")!=null && ticket.getString("accountDetail/emailId")!="")
		{
			Boolean validEmail = validateEmail(ticket.getString("accountDetail/emailId").trim());
			if(!validEmail){
				throw new RuntimeException(ticket.getString("accountDetail/emailId")+" Invalid value input in Email ID. Please enter Email ID value in correct format");
			}
		}
         
		System.out.println("Entered into Save Ticket in IID 3 ");
		
		List<String> valErrors = new ArrayList();

		valErrors = validateTicket(ticket);
		String errorString = "";
		if ((valErrors != null) && (!valErrors.isEmpty())) {
			for (String error : valErrors) {
				if (errorString == "") {
					errorString = errorString + error;
				} else
					errorString = errorString + " ," + error;
			}
			throw new RuntimeException("Validation Errors: " + errorString);
		}
		//validateAccountDetail(ticket.getDataObject("accountDetail"));
		ticketData.setString("lob", ticket.getString("lob"));
		ticketData.setString("priority", ticket.getString("priorityStatus"));
		ticketData.setString("product", ticket.getString("product"));
		ticketData.setString("source", ticket.getString("source"));
		System.out.println("Entered into Save Ticket in IID 4 ");
		if ((ticket.getString("rasDraftTicketNo") != null) && 
				(!"".equalsIgnoreCase(ticket.getString("rasDraftTicketNo")))) {
			ticketData.setString("ras_draft_ticket_no", ticket.getString("rasDraftTicketNo"));
		}
		System.out.println("Entered into Save Ticket in IID 5 ");
		if ((ticket.getString("bopsChannel") != null) && 
				(!"".equalsIgnoreCase(ticket.getString("bopsChannel")))) {
		ticketData.setString("bops_channel", ticket.getString("bopsChannel"));
		}
		System.out.println("Entered into Save Ticket in IID 6 ");
		if ((ticket.getString("sourceUniqueId") != null) && 
				(!"".equalsIgnoreCase(ticket.getString("sourceUniqueId")))) {
		ticketData.setString("source_unique_id", ticket.getString("sourceUniqueId"));
		}
		System.out.println("Entered into Save Ticket in IID 7 ");
		if ((ticket.getString("sourceUserName") != null) && 
				(!"".equalsIgnoreCase(ticket.getString("sourceUserName")))) {
		ticketData.setString("source_user_name", ticket.getString("sourceUserName"));
		}
		System.out.println("Entered into Save Ticket in IID 8 ");
		if ((ticket.getString("omniDocumentsURL") != null) && 
				(!"".equalsIgnoreCase(ticket.getString("omniDocumentsURL")))) {
		ticketData.setString("omni_documents_url", ticket.getString("omniDocumentsURL"));
		}
		System.out.println("Entered into Save Ticket in IID 9 ");
		if ((ticket.getString("channelMode") != null) && 
				(!"".equalsIgnoreCase(ticket.getString("channelMode")))) {
		ticketData.setString("channel_mode", ticket.getString("channelMode"));
		}
		if (ticket.getString("discrepancyType") != null)
		{
			ticketData.setString("discrepancy_type", ticket.getString("discrepancyType"));
		}
		System.out.println("Entered into Save Ticket in IID 10 ");
		if (ticket.getString("ftrDescription") != null)
		{
			ticketData.setString("ftr_description", ticket.getString("ftrDescription"));
		}
		System.out.println("Entered into Save Ticket in IID 11 ");
		if ((ticket.getString("lanNumber") != null) && 
				(!"".equalsIgnoreCase(ticket.getString("lanNumber")))) {
			ticketData.setString("lan_number", ticket.getString("lanNumber"));
		}
		System.out.println("Entered into Save Ticket in IID 12 ");
		if ((ticket.getString("ocrOmniDocumentsURL") != null) && 
				(!"".equalsIgnoreCase(ticket.getString("ocrOmniDocumentsURL")))) {
			ticketData.setString("ocr_omni_documents_url", ticket.getString("ocrOmniDocumentsURL"));
		}
		ticketData.setString("refund_mode", ticket.getString("refundMode"));
		System.out.println("Entered into Save Ticket in IID 13 ");
		/*Added by @Sarika*/
		if(ticket.getString("refundMode").equalsIgnoreCase(Constants.BillDesk.getValue())){
			//ticketData.setString("bank", ticket.getString("bank"));
			
		}
		System.out.println("Entered into Save Ticket in IID 14 ");
        if ("CANCELLATION ONLY".equalsIgnoreCase(ticket.getString("refundType"))) {
			ticketData.setString("policy_no", ticket.getString("policyNo"));
			ticketData.setString("application_no", 
					ticket.getString("policyDetail/proposalNo"));
		}
        System.out.println("Entered into Save Ticket in IID 15 ");
		if (ticket.get("refundType").equals(Constants.CancellationRefund.getValue()) || 
                  "NEGATIVE ENDORSEMENT REFUND".equalsIgnoreCase(ticket.getString("refundType"))) {
					  if(ticket.getDate("requestedDate")!=null){
			ticketData.setDate("requested_date", 
					formatDate(ticket.getDate("requestedDate")));
					  }
					  if(ticket.getDate("effectiveDate")!=null){
			ticketData.setDate("effective_date", 
					formatDate(ticket.getDate("effectiveDate")));
					  }
					  System.out.println("Entered into Save Ticket in IID 16 ");
			ticketData.setString("cancellation_reason", 
					ticket.getString("cancellationReason"));
			ticketData.setString("cancellation_option", 
					ticket.getString("cancellationOption"));
			ticketData.setString("other_reason", 
					ticket.getString("otherReason"));
			ticketData.setString("policy_no", ticket.getString("policyNo"));
			ticketData.setString("application_no", 
					ticket.getString("policyDetail/proposalNo"));
			System.out.println("Balance days in save ticket"+ticket.getInt("balanceDays"));
			if("LTA".equalsIgnoreCase(ticket.getString("lob")) && "Pro-Rata Refund".equalsIgnoreCase(ticket.getString("cancellationOption")))
			{
				ticketData.setInt("balance_days",
						ticket.getInt("balanceDays"));
			}
			System.out.println("Entered into Save Ticket in IID 17 ");
			if("Trade Credit".equalsIgnoreCase(ticket.getString("lob")) ||("Liability".equalsIgnoreCase(ticket.getString("lob"))&&"2770".equals(ticket.getString("product"))) || ("Health".equalsIgnoreCase(ticket.getString("lob")) && ("2890".equals(ticket.getString("product")) || "2808".equals(ticket.getString("product"))) && "Partial Refund".equalsIgnoreCase(ticket.getString("cancellationOption"))))
			{
				double minPremiumRetainAmount = ticket.getDouble("minPremiumRetainAmount");
				BigDecimal minPremiumRetainAmountDecimal = BigDecimal.valueOf(minPremiumRetainAmount); 
				//float floatminPremiumRetainAmount = (float)minPremiumRetainAmount;
				ticketData.setBigDecimal("min_premium_retain_amount", minPremiumRetainAmountDecimal);
			}
			System.out.println("Entered into Save Ticket in IID 18 ");
			if(("Liability".equalsIgnoreCase(ticket.getString("lob")) &&("2774".equals(ticket.getString("product")) || "2776".equals(ticket.getString("product"))))||("Financial Line".equalsIgnoreCase(ticket.getString("lob")) && ("6105".equals(ticket.getString("product")) || "6106".equals(ticket.getString("product")) || "6108".equals(ticket.getString("product")) || "6107".equals(ticket.getString("product")) || "6109".equals(ticket.getString("product"))))||"Aviation".equalsIgnoreCase(ticket.getString("lob")))
			{
				double exchangeRate = ticket.getDouble("exchangeRate");
				BigDecimal exchangeRateDecimal = BigDecimal.valueOf(exchangeRate); 
				//float floatexchangeRate = (float)exchangeRate;
				ticketData.setBigDecimal("exchange_rate", exchangeRateDecimal);
			}
			if("Liability".equalsIgnoreCase(ticket.getString("lob")) &&"2771".equals(ticket.getString("product")))
			{
				double endorsementAmount = ticket.getDouble("endorsementAmount");
				BigDecimal endorsementAmountDecimal = BigDecimal.valueOf(endorsementAmount); 
				//float floatendorsementAmount = (float)endorsementAmount;
				ticketData.setBigDecimal("endorsement_amount", endorsementAmountDecimal);
				
				double endorsementERFAmount = ticket.getDouble("endorsementERFAmount");
				BigDecimal endorsementERFAmountDecimal = BigDecimal.valueOf(endorsementERFAmount); 
				//float floatendorsementERFAmount = (float)endorsementERFAmount;
				ticketData.setBigDecimal("endorsement_erf_amount", endorsementERFAmountDecimal);
			}
			System.out.println("Entered into Save Ticket in IID 19 ");
			if("Aig Combined".equalsIgnoreCase(ticket.getString("lob")) &&  "Agreed Premium Cancellation".equalsIgnoreCase(ticket.getString("cancellationOption")))
			{
				double aigAgreedPremiumAmount = ticket.getDouble("aigAgreedPremiumAmount");
				BigDecimal aigAgreedPremiumAmountDecimal = BigDecimal.valueOf(aigAgreedPremiumAmount); 
				//float floataigAgreedPremiumAmount = (float)aigAgreedPremiumAmount;
				ticketData.setBigDecimal("agreed_premium_amount", aigAgreedPremiumAmountDecimal);
			}
			System.out.println("Entered into Save Ticket in IID 20 ");
			if(("Liability".equalsIgnoreCase(ticket.getString("lob")) &&("2771".equals(ticket.getString("product")) || "2772".equals(ticket.getString("product")) || "2774".equals(ticket.getString("product")) || "2776".equals(ticket.getString("product"))))||("Financial Line".equalsIgnoreCase(ticket.getString("lob")) && ("6105".equals(ticket.getString("product")) || "6106".equals(ticket.getString("product")) || "6108".equals(ticket.getString("product")) || "6107".equals(ticket.getString("product")) || "6109".equals(ticket.getString("product")) || "6101".equals(ticket.getString("product")))) || ("Marine Cargo".equalsIgnoreCase(ticket.getString("lob")) &&("2151".equals(ticket.getString("product")) || "2154".equals(ticket.getString("product"))) &&  "Partial Refund".equalsIgnoreCase(ticket.getString("cancellationOption")))||"Aviation".equalsIgnoreCase(ticket.getString("lob")))
			{
				double commercialRefundAmount = ticket.getDouble("commercialRefundAmount");
				BigDecimal commercialRefundAmountDecimal = BigDecimal.valueOf(commercialRefundAmount); 
				//float floatcommercialRefundAmount = (float)commercialRefundAmount;
				ticketData.setBigDecimal("commercial_refund_amount", commercialRefundAmountDecimal);
			}
			// Assigning payee_name value
			System.out.println("Entered into Save Ticket in IID 21 ");
			if ((ticket.getString("payeeType") != null) && 
					(!"".equalsIgnoreCase(ticket.getString("payeeType")))){
				
				  if(ticket.getString("payeeType").equalsIgnoreCase("CUSTOMER")){
					  ticketData.setString("payee_name", ticket.getString("accountDetail/customerName"));
				  }
                  if(ticket.getString("payeeType").equalsIgnoreCase("DEALER")){
                	  ticketData.setString("payee_name", ticket.getString("policyDetail/dealerName"));
				  }
                  if(ticket.getString("payeeType").equalsIgnoreCase("INTERMEDIARY")){
                	  ticketData.setString("payee_name", ticket.getString("policyDetail/producerName"));
				  }
                  if(ticket.getString("payeeType").equalsIgnoreCase("FINANCIER")){
                	  ticketData.setString("payee_name", ticket.getString("policyDetail/financierName"));
				  }
			}
			System.out.println("Entered into Save Ticket in IID 21 ");
			
			if ((ticket.getString("nameOfInsureedCompany") != null) && 
					(!"".equalsIgnoreCase(ticket.getString("nameOfInsureedCompany")))) {
				ticketData.setString("name_of_insured_company", 
						ticket.getString("nameOfInsureedCompany"));
			}
			if ((ticket.getString("coverType") != null) && 
					(!"".equalsIgnoreCase(ticket.getString("coverType")))) {
				ticketData.setString("cover_type", 
						ticket.getString("coverType"));
			}
			if ((ticket.getString("officeCodeAddress") != null) && 
					(!"".equalsIgnoreCase(ticket.getString("officeCodeAddress")))) {
				ticketData.setString("office_code_address", 
						ticket.getString("officeCodeAddress"));
			}
			System.out.println("Entered into Save Ticket in IID 22 ");
			if ((ticket.getString("renewedPolicyNo") != null) && 
					(!"".equalsIgnoreCase(ticket.getString("renewedPolicyNo")))) {
				ticketData.setString("renewed_policy_no", 
						ticket.getString("renewedPolicyNo"));
			}
			if (ticket.getDate("ncbReservingEffectiveDate") != null) {
				ticketData.setDate("ncb_reserving_effective_date", 
						formatDate(ticket.getDate("ncbReservingEffectiveDate")));
			}
			if (ticket.getDate("periodFromDate") != null) {
				ticketData.setDate("period_from_date", 
						formatDate(ticket.getDate("periodFromDate")));
			}
			System.out.println("Entered into Save Ticket in IID 23 ");
			if (ticket.getDate("periodToDate") != null) {
				ticketData.setDate("period_to_date", 
						formatDate(ticket.getDate("periodToDate")));
			}
		} 
		
		else {
			System.out.println("Entered into Save Ticket in IID 24 ");
			ticketData.setString("application_no", 
					ticket.getString("receiptNo"));
			ticketData.setString("rcs_no", ticket.getString("receiptNo"));
			System.out.println("Entered into Save Ticket in IID 25 ");
			if ((ticket.getString("payeeType") != null) && 
					(!"".equalsIgnoreCase(ticket.getString("payeeType")))){
				
				ticketData.setString("payee_name", ticket.getString("receiptDetail[1]/payeeName"));
				 
			}
			System.out.println("Entered into Save Ticket in IID 26 ");
			if ((ticket.getString("requestType") != null) && 
					(!"".equalsIgnoreCase(ticket.getString("requestType")))) {
				ticketData.setString("request_type", ticket.getString("requestType"));
			}
			System.out.println("Entered into Save Ticket in IID 27 ");
			double receiptRefundAmount = ticket.getDouble("receiptRefundAmount");
			BigDecimal receiptRefundAmountDecimal = BigDecimal.valueOf(receiptRefundAmount); 
			//float floatreceiptRefundAmount = (float)receiptRefundAmount;
			ticketData.setBigDecimal("receipt_refund_amount", receiptRefundAmountDecimal);
			System.out.println("Entered into Save Ticket in IID 28 ");
            if ("Health".equalsIgnoreCase(ticket.getString("lob"))){
				
				double ppcAmount = ticket.getDouble("PPCAmount");
				BigDecimal ppcAmountDecimal = BigDecimal.valueOf(ppcAmount); 
				//float floatPPCAmount = (float)ppcAmount;
				ticketData.setBigDecimal("ppc_amount", ppcAmountDecimal);
				 
			}
            System.out.println("Entered into Save Ticket in IID 29 ");
		}
		
		if(!("CANCELLATION ONLY".equalsIgnoreCase(ticket.getString("refundType")))){
		if(ticketData.getString("payee_name")==null|| "".equalsIgnoreCase(ticketData.getString("payee_name"))){
			
			throw new RuntimeException("Payee Name cannot be Empty");
		}
		}
		System.out.println("Entered into Save Ticket in IID 30 ");
		if ((ticket.getString("icrmTicketNumber") != null) && 
				(!"".equalsIgnoreCase(ticket.getString("icrmTicketNumber")))) {
			ticketData.setString("icrm_ticket_number", 
					ticket.getString("icrmTicketNumber"));
		}
		
		if ((ticket.getString("icrmCustomerId") != null) && 
				(!"".equalsIgnoreCase(ticket.getString("icrmCustomerId")))) {
			ticketData.setString("icrm_customer_id", 
					ticket.getString("icrmCustomerId"));
		}
		
		if ((ticket.getString("icrmUsername") != null) && 
				(!"".equalsIgnoreCase(ticket.getString("icrmUsername")))) {
			ticketData.setString("icrm_username", 
					ticket.getString("icrmUsername"));
		}
		ticketData.setString("subreceipt", ticket.getString("subReceipt"));
		
		if ((ticket.getString("bank") != null) && 
				(!"".equalsIgnoreCase(ticket.getString("bank")))) {
			ticketData.setString("bank", 
					ticket.getString("bank"));
		}
		System.out.println("Entered into Save Ticket in IID 31 ");
		ticketData.setString("refund_type", ticket.getString("refundType"));
		ticketData.setString("ticket_type", ticket.getString("ticketType"));
		ticketData.setString("payer_type", ticket.getString("payeeType"));
		double refundAmount = ticket.getDouble("refundAmount");
		BigDecimal refundAmountDecimal = BigDecimal.valueOf(refundAmount); 
		//float floatRefundAmount = (float)refundAmount;
		ticketData.setBigDecimal("refund_amount", refundAmountDecimal);
		System.out.println("Entered into Save Ticket in IID 32 ");
		double premiumCalculationAmount = ticket.getDouble("premiumCalculationAmount");
		BigDecimal premiumCalculationAmountDecimal = BigDecimal.valueOf(premiumCalculationAmount); 
		//float floatRefundAmount = (float)refundAmount;
		ticketData.setBigDecimal("premium_calculation_amount", premiumCalculationAmountDecimal);
		
		/*
		ticketData.setString("refund_amount", 
				ticket.getString("refundAmount"));*/
	System.out.println("Created by in save ticket"+ticket.getString("raisedBy"));
		ticketData.setString("created_by", ticket.getString("raisedBy"));
		ticketData.setDate("created_on", formatDate(new Date()));
		ticketData.setDate("last_modified_on", formatDate(new Date()));
		ticketData.setString("status", ticket.getString("status"));

		if ((ticket.getString("policyDetail/proposerName") != null) && (!"".equalsIgnoreCase(ticket.getString("policyDetail/proposerName").trim()))) {
			ticketData.setString("customer_name", ticket.getString("policyDetail/proposerName"));
		}
		System.out.println("Entered into Save Ticket in IID 33 ");
		ticketData.setString("voucher_no", ticket.getString("voucherNo"));
		ticketData.setString("ticket_remarks", 
				ticket.getString("ticketRemarks"));
		ticketData.setString("stage", ticket.getString("stage"));
		System.out.println("last modified by in save ticket"+	ticket.getString("actionBy"));
		ticketData.setString("last_modified_by", 
				ticket.getString("actionBy"));
		ticketData.setString("instance_id", ticket.getString("processId"));
		ticketData.setBoolean("upload_to", ticket.getBoolean("uploadedTo"));
		System.out.println("Entered into Save Ticket in IID 34 ");
		ticketDataBG.set("DboRas_Ticket", ticketData);
		try
		{
			// checking for duplicate ticket while raising a ticket
            String searchBy = "";
			RetrieveTicketJavaCmptImpl ticketExists = new RetrieveTicketJavaCmptImpl();
			// checking for group policy or not and assigning values
			// cancellation refund
			System.out.println("Entered into Save Ticket in IID 35 ");
			if(Constants.CancellationRefund.getValue().equalsIgnoreCase(ticket.getString("refundType"))&& 
					! "GROUP".equalsIgnoreCase(ticket.getString("policyDetail/policyType"))){
				applicationNo = ticketData.getString("policy_no");
			}
			else if("CANCELLATION ONLY".equalsIgnoreCase(ticket.getString("refundType")) 
					&& ! "GROUP".equalsIgnoreCase(ticket.getString("policyDetail/policyType"))){
				applicationNo = ticket.getString("policyNo");
			}
			else if("BALANCE REFUND".equalsIgnoreCase(ticket.getString("refundType")) 
					&& ! "GROUP".equalsIgnoreCase(ticket.getString("policyDetail/policyType"))){
				applicationNo = ticket.getString("policyNo");
			}
			
			else if("NEGATIVE ENDORSEMENT REFUND".equalsIgnoreCase(ticket.getString("refundType")) 
					&& ! "GROUP".equalsIgnoreCase(ticket.getString("policyDetail/policyType"))){
				applicationNo = ticket.getString("policyNo");
			}
			//excess refund
			else if(Constants.ExcessRefund.getValue().equalsIgnoreCase(ticket.getString("refundType"))){
				System.out.println("Entered into Save Ticket in IID 36 ");
				applicationNo = ticketData.getString("rcs_no");
			}
			// Group policy
			else{
				// assigning value based on search parameter for group policy
				System.out.println("Entered into Save Ticket in IID 37 ");
				searchBy = ticket.getString("policyDetail/searchBy");
				if("PROPOSAL NUMBER".equalsIgnoreCase(searchBy))
				applicationNo = ticket.getString("policyDetail/proposalNo");
				else if("CERTIFICATE NUMBER".equalsIgnoreCase(searchBy))
					applicationNo = ticket.getString("policyDetail/certificateNo");
				else
					applicationNo = ticket.getString("policyDetail/lanNumber");
			}
			boolean isExists = false;
			// calling isTicketExists method for group policy
			if("GROUP".equalsIgnoreCase(ticket.getString("policyDetail/policyType")) && 
					"Health".equalsIgnoreCase(ticket.getString("lob"))){
				// sending refund type as group for checking group policy duplicate ticket in order to reduce no of inputs
				System.out.println("Group policy duplicate check, application no "+applicationNo+" search by "+ticket.getString("policyDetail/searchBy"));
			isExists = ticketExists.isTicketExist(applicationNo, "GROUP",ticket.getString("policyDetail/searchBy"),ticket.getString("policyDetail/policyNo")).booleanValue();
			}
			// calling isTicketExists method for normal policy
			else{
				System.out.println("Normal policy duplicate check");
			isExists = ticketExists.isTicketExist(applicationNo, ticket.getString("refundType"),ticket.getString("policyDetail/searchBy"),"").booleanValue();
			}
			List<DataObject> insuredResult = new ArrayList();
			List<DataObject> receiptResult = new ArrayList();
			if (!isExists) {
				responseBO = (DataObject)locateService_DBInterfacePartner()
						.invoke("createDboRas_TicketBG", ticketDataBG);
				System.out.println("Entered into Save Ticket in IID 38 ");
				System.out.println("Entered into Save Ticket in IID Ticket BO -->  "+ticketDataBG);
				return prepareTicketResponse(
						ticket, 
						responseBO
						.getDataObject("createDboRas_TicketBGOutput/DboRas_Ticket"),null,null,receiptResult,null,insuredResult);
			}

			return null;
		}
		catch (ServiceBusinessException sbe)
		{
			exceptionData = (DataObject)sbe.getData();
			rasException.setString("status", "FAILED");
			rasException.setString("error[0]/message", 
					exceptionData.get("message").toString());
			throw new ServiceBusinessException(rasException);
		}
		catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage().toString());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
			}

	public Boolean validateEmail(String email){
		System.out.println("Enter Into validateEmail");
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		return email.matches(EMAIL_PATTERN);
	}


	/**
	 * Method generated to support implementation of operation "updateTicket"
	 * defined for WSDL port type named "MaintainTicket".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a
	 * parameter type conveys that it is a complex type. Please refer to the
	 * WSDL Definition for more information on the type of input, output and
	 * fault(s).
	 * 
	 * @throws ParseException
	 */
	public DataObject updateTicket(DataObject ticket) throws ParseException {
		System.out.println("Enter Into updateTicket"+ticket.getString("ticketNo"));
		// To create a DataObject, use the creation methods on the BOFactory:
		// com.ibm.websphere.bo.BOFactory boFactory =
		// (com.ibm.websphere.bo.BOFactory)
		// ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");
		//
		// To get or set attributes for a DataObject such as ticket, use the
		// APIs as shown below:
		// To set a string attribute in ticket, use
		// ticket.setString(stringAttributeName, stringValue)
		// To get a string attribute in ticket, use
		// ticket.getString(stringAttributeName)
		// To set a dataObject attribute in ticket, use
		// ticket.setDataObject(stringAttributeName, dataObjectValue)
		// To get a dataObject attribute in ticket, use
		// ticket.getDataObject(stringAttributeName)
		DataObject responseBO = null;
		// RASException Object
		DataObject rasException = getBOFactory().create("http://RACASBO",
				"RASException");
		// Object to store Business Exception Data
		DataObject exceptionData = null;
		DataObject createAccount = null;
		String authCode = null;
		DataObject ticketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticketbg",
						"DboRas_TicketBG");
		DataObject ticketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticket",
						"DboRas_Ticket");
		
		DataObject updateTicketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateticketdetailsbg",
						"UpdateTicketDetailsBG");
		DataObject updateTicketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateticketdetails",
						"UpdateTicketDetails");

		// set the property for the ticket object
		if (ticket.get("ticketID") != null && ticket.getDouble("ticketID") != 0)
		{
			ticketData.setDouble("ticket_id", ticket.getDouble("ticketID"));
			updateTicketData.setDouble("statement1parameter58", ticket.getDouble("ticketID"));
		}
		else {
			throw new RuntimeException(
					"Ticket update failed: TicketId cannot be null or empty");
		}

		ticketData.setString("status", ticket.getString("status"));
		updateTicketData.setString("statement1parameter9", ticket.getString("status"));
		ticketData.setString("bank", ticket.getString("bank"));
		updateTicketData.setString("statement1parameter35", ticket.getString("bank"));
		ticketData.setString("priority", ticket.getString("priorityStatus"));
		updateTicketData.setString("statement1parameter37", ticket.getString("priorityStatus"));
		ticketData.setString("stage", ticket.getString("stage"));
		updateTicketData.setString("statement1parameter14", ticket.getString("stage"));
		System.out.println("application No "+ticket.getString("applicationNo"));
		
		if((ticket.getString("refundType").equalsIgnoreCase(Constants.CancellationRefund.getValue()) || 
				"CANCELLATION REFUND".equalsIgnoreCase(ticket.getString("refundType")) || 
				"BALANCE REFUND".equalsIgnoreCase(ticket.getString("refundType"))) 
				&& (ticket.getString("applicationNo")==null || ticket.getString("applicationNo").equalsIgnoreCase(""))){
			ticketData.setString("application_no",
					ticket.getString("policyDetail/proposalNo"));
			updateTicketData.setString("statement1parameter1", ticket.getString("policyDetail/proposalNo"));
			
		}
		else{
			ticketData.setString("application_no",
					ticket.getString("applicationNo"));
			updateTicketData.setString("statement1parameter1", ticket.getString("applicationNo"));
			
		}
		if(ticket.getString("refundType").equalsIgnoreCase(Constants.ExcessRefund.getValue()))
		{
			ticketData.setString("application_no",
					ticket.getString("applicationNo"));
				updateTicketData.setString("statement1parameter1", ticket.getString("applicationNo"));
		}
		
		//ticketData.set("ticket_no",generateTicketSeries(ticket.getInt("ticketID")) );
		 
		ticketData.setString("comments", ticket.getString("comments"));
		updateTicketData.setString("statement1parameter23", ticket.getString("comments"));
		ticketData.setString("remarks", ticket.getString("remarks"));
		updateTicketData.setString("statement1parameter24", ticket.getString("remarks"));
		ticketData.setString("payee_name", ticket.getString("payeeName"));
		updateTicketData.setString("statement1parameter36", ticket.getString("payeeName"));

		if (ticket.getDate("voucherGeneratedDate") != null)
		{
			ticketData.setDate("voucher_generated_date",
					formatDate(ticket.getDate("voucherGeneratedDate")));
			updateTicketData.setDate("statement1parameter32",
					formatDate(ticket.getDate("voucherGeneratedDate")));
		}
		if (ticket.getString("voucherNo") != null
				&& !"".equalsIgnoreCase(ticket.getString("voucherNo")))
		{
			ticketData.setString("voucher_no", ticket.getString("voucherNo"));
			updateTicketData.setString("statement1parameter10", ticket.getString("voucherNo"));
		}
		List<String> valErrors = new ArrayList<String>();

		valErrors = validateTicket(ticket);
		String errorString = "";
		if (valErrors != null && !valErrors.isEmpty()) {
			for (String error : valErrors) {
				if (errorString == "")
					errorString = errorString + error;
				else
					errorString = errorString + " ," + error;
			}
			throw new RuntimeException("Validation Errors: " + errorString);
		}
		else{
			if (ticket.getString("refundMode") != null
					&& !"".equalsIgnoreCase(ticket.getString("refundMode")))
			{
				ticketData.setString("refund_mode", ticket.getString("refundMode"));
				updateTicketData.setString("statement1parameter12", ticket.getString("refundMode"));
			}
			if (ticket.getString("cancellationOption") != null
					&& !"".equalsIgnoreCase(ticket.getString("cancellationOption")))
			{
				ticketData.setString("cancellation_option",
						ticket.getString("cancellationOption"));
				updateTicketData.setString("statement1parameter11",
						ticket.getString("cancellationOption"));
			}
			if (ticket.getString("cancellationReason") != null
					&& !"".equalsIgnoreCase(ticket.getString("cancellationReason")))
			{
				ticketData.setString("cancellation_reason",
						ticket.getString("cancellationReason"));
				updateTicketData.setString("statement1parameter5", ticket.getString("cancellationReason"));
			}
			
		
			
			if ((ticket.getString("icrmTicketNumber") != null) && 
					(!"".equalsIgnoreCase(ticket.getString("icrmTicketNumber")))) {
				ticketData.setString("icrm_ticket_number", 
						ticket.getString("icrmTicketNumber"));
				updateTicketData.setString("statement1parameter46", 
						ticket.getString("icrmTicketNumber"));
			}
			
			if ((ticket.getString("icrmCustomerId") != null) && 
					(!"".equalsIgnoreCase(ticket.getString("icrmCustomerId")))) {
				ticketData.setString("icrm_customer_id", 
						ticket.getString("icrmCustomerId"));
				updateTicketData.setString("statement1parameter49", 
						ticket.getString("icrmCustomerId"));
			}
			
			if ((ticket.getString("icrmUsername") != null) && 
					(!"".equalsIgnoreCase(ticket.getString("icrmUsername")))) {
				ticketData.setString("icrm_username", 
						ticket.getString("icrmUsername"));
				updateTicketData.setString("statement1parameter47", 
						ticket.getString("icrmUsername"));
			}
			
			
			if ((ticket.getString("ticketType") != null) && 
					(!"".equalsIgnoreCase(ticket.getString("ticketType")))) {
				ticketData.setString("ticket_type", 
						ticket.getString("ticketType"));
				updateTicketData.setString("statement1parameter52", 
						ticket.getString("ticketType"));
			}
			

			if (ticket.getString("discrepancyType") != null) {
				ticketData.setString("discrepancy_type", ticket.getString("discrepancyType"));
				updateTicketData.setString("statement1parameter53", ticket.getString("discrepancyType"));
			}
			
			if (ticket.getString("ftrDescription") != null) {
				ticketData.setString("ftr_description", ticket.getString("ftrDescription"));
				updateTicketData.setString("statement1parameter54", ticket.getString("ftrDescription"));
			}
			
			if (ticket.getString("lanNumber") != null && 
					(!"".equalsIgnoreCase(ticket.getString("lanNumber")))) {
				ticketData.setString("lan_number", ticket.getString("lanNumber"));
				updateTicketData.setString("statement1parameter56", ticket.getString("lanNumber"));
			}
			
			if (ticket.getString("ocrOmniDocumentsURL") != null && 
					(!"".equalsIgnoreCase(ticket.getString("ocrOmniDocumentsURL")))) {
				ticketData.setString("ocr_omni_documents_url", ticket.getString("ocrOmniDocumentsURL"));
				updateTicketData.setString("statement1parameter57", ticket.getString("ocrOmniDocumentsURL"));
			}
			
			ticketData.setString("subreceipt", ticket.getString("subReceipt"));
			updateTicketData.setString("statement1parameter51", ticket.getString("subReceipt"));
			
			ticketData.setString("other_reason",
						ticket.getString("otherReason"));
			updateTicketData.setString("statement1parameter6", ticket.getString("otherReason"));
			
			if (ticket.getString("ticketRemarks") != null
					&& !"".equalsIgnoreCase(ticket.getString("ticketRemarks")))
			{
				ticketData.setString("ticket_remarks",
						ticket.getString("ticketRemarks"));
				updateTicketData.setString("statement1parameter13",
						ticket.getString("ticketRemarks"));
			}
			
			if (ticket.getDate("requestedDate") != null)
			{
				ticketData.setDate("requested_date",
						formatDate(ticket.getDate("requestedDate")));
				updateTicketData.setDate("statement1parameter2",
						formatDate(ticket.getDate("requestedDate")));
			}
			if (ticket.getDate("effectiveDate") != null)
			{
				ticketData.setDate("effective_date",
						formatDate(ticket.getDate("effectiveDate")));
				updateTicketData.setDate("statement1parameter26",
						formatDate(ticket.getDate("effectiveDate")));
			}
			
				ticketData.setString("name_of_insured_company",
						ticket.getString("nameOfInsureedCompany"));
				updateTicketData.setString("statement1parameter27",
						ticket.getString("nameOfInsureedCompany"));
				
			
				ticketData.setString("cover_type",
						ticket.getString("coverType"));
				updateTicketData.setString("statement1parameter28",
						ticket.getString("coverType"));
				
			
				ticketData.setString("office_code_address",
						ticket.getString("officeCodeAddress"));
				updateTicketData.setString("statement1parameter29",
						ticket.getString("officeCodeAddress"));
			
				ticketData.setString("renewed_policy_no",
						ticket.getString("renewedPolicyNo"));
				updateTicketData.setString("statement1parameter31",
						ticket.getString("renewedPolicyNo"));

			if (ticket.getString("payeeType") != null
					&& !"".equalsIgnoreCase(ticket.getString("payeeType")))
			{
				ticketData.setString("payer_type",
						ticket.getString("payeeType"));
				updateTicketData.setString("statement1parameter25",
						ticket.getString("payeeType"));
			}
			if (ticket.getDate("ncbReservingEffectiveDate") != null)
			{
				ticketData.setDate("ncb_reserving_effective_date",
						formatDate(ticket.getDate("ncbReservingEffectiveDate")));
				updateTicketData.setDate("statement1parameter30",
						formatDate(ticket.getDate("ncbReservingEffectiveDate")));
			}
			else
			{
				ticketData.setDate("ncb_reserving_effective_date",null);
				updateTicketData.setDate("statement1parameter30",
						null);
			}
		}

		if (ticket.getDate("periodFromDate") != null)
		{
			ticketData.setDate("period_from_date",
					formatDate(ticket.getDate("periodFromDate")));
			updateTicketData.setDate("statement1parameter33",
					formatDate(ticket.getDate("periodFromDate")));
		}
		else
		{
			ticketData.setDate("period_from_date",null);
			updateTicketData.setDate("statement1parameter33",
					null);
		}
		if (ticket.getDate("periodToDate") != null)
		{
			ticketData.setDate("period_to_date",
					formatDate(ticket.getDate("periodToDate")));
			updateTicketData.setDate("statement1parameter34",
					formatDate(ticket.getDate("periodToDate")));
		}
		else
		{
			ticketData.setDate("period_to_date",null);
			updateTicketData.setDate("statement1parameter34",
					null);
		}
//Excess Refund Data Save
if(ticket.getString("refundType").equalsIgnoreCase(Constants.ExcessRefund.getValue()))
{
		if ((ticket.getString("requestType") != null) && 
				(!"".equalsIgnoreCase(ticket.getString("requestType")))) {
			ticketData.setString("request_type", ticket.getString("requestType"));
			updateTicketData.setString("statement1parameter38", ticket.getString("requestType"));
		} 
		
		double receiptRefundAmount = ticket.getDouble("receiptRefundAmount");
		BigDecimal receiptRefundAmountDecimal = BigDecimal.valueOf(receiptRefundAmount); 
		//float floatreceiptRefundAmount = (float)receiptRefundAmount;
		ticketData.setBigDecimal("receipt_refund_amount", receiptRefundAmountDecimal);
		updateTicketData.setBigDecimal("statement1parameter39", receiptRefundAmountDecimal);
		
	    if ("Health".equalsIgnoreCase(ticket.getString("lob"))){
			
			double ppcAmount = ticket.getDouble("PPCAmount");
			BigDecimal ppcAmountDecimal = BigDecimal.valueOf(ppcAmount); 
			//float floatPPCAmount = (float)ppcAmount;
			ticketData.setBigDecimal("ppc_amount", ppcAmountDecimal);
			updateTicketData.setBigDecimal("statement1parameter40", ppcAmountDecimal);
		}
}
		if (ticket.getString("refundType").equalsIgnoreCase(
				Constants.CancellationRefund.getValue())) {

			double gst = ticket.getDouble("gst");
			BigDecimal gstDecimal = BigDecimal.valueOf(gst); 
			//float floatGst = (float)gst;
			ticketData.setBigDecimal("gst",gstDecimal);
			updateTicketData.setBigDecimal("statement1parameter16", gstDecimal);

			double jk_gst = ticket.getDouble("jk_gst");
			BigDecimal jk_gstDecimal = BigDecimal.valueOf(jk_gst); 
			//float floatJkGst = (float)jk_gst;
			ticketData.setBigDecimal("jk_gst", jk_gstDecimal);
			updateTicketData.setBigDecimal("statement1parameter17", jk_gstDecimal);

			double stampDuty = ticket.getDouble("stampDuty");
			BigDecimal stampDutyDecimal = BigDecimal.valueOf(stampDuty); 
			//float floatstampDuty = (float)stampDuty;
			ticketData.setBigDecimal("stamp_duty", stampDutyDecimal);
			updateTicketData.setBigDecimal("statement1parameter19", stampDutyDecimal);

			double netPremium = ticket.getDouble("netPremium");
			BigDecimal netPremiumDecimal = BigDecimal.valueOf(netPremium); 
			//float floatNetPremium = (float)netPremium;
			ticketData.setBigDecimal("net_premium", netPremiumDecimal);
			updateTicketData.setBigDecimal("statement1parameter15", netPremiumDecimal);

			double totalPremium = ticket.getDouble("totalPremium");
			BigDecimal totalPremiumDecimal = BigDecimal.valueOf(totalPremium);
			//float floatTotalPremium = (float)totalPremium;
			ticketData.setBigDecimal("total_premium",totalPremiumDecimal);
			updateTicketData.setBigDecimal("statement1parameter18", totalPremiumDecimal);
			
			double refundAmount = ticket.getDouble("refundAmount");
			BigDecimal refundAmountDecimal = BigDecimal.valueOf(refundAmount); 
			//float floatRefundAmount = (float)refundAmount;
			ticketData.setBigDecimal("refund_amount", refundAmountDecimal);
			updateTicketData.setBigDecimal("statement1parameter4", refundAmountDecimal);
			
			double premiumCalculationAmount = ticket.getDouble("premiumCalculationAmount");
			BigDecimal premiumCalculationAmountDecimal = BigDecimal.valueOf(premiumCalculationAmount); 
			//float floatRefundAmount = (float)refundAmount;
			ticketData.setBigDecimal("premium_calculation_amount", premiumCalculationAmountDecimal);
			updateTicketData.setBigDecimal("statement1parameter3", premiumCalculationAmountDecimal);
			
			
			System.out.println("Balance days in update ticket"+ticket.getInt("balanceDays"));
			if("LTA".equalsIgnoreCase(ticket.getString("lob")) && "Pro-Rata Refund".equalsIgnoreCase(ticket.getString("cancellationOption")))
			{
				ticketData.setInt("balance_days",
						ticket.getInt("balanceDays"));
				updateTicketData.setInt("statement1parameter50",
						ticket.getInt("balanceDays"));
			}
			
			if("Trade Credit".equalsIgnoreCase(ticket.getString("lob")) ||("Liability".equalsIgnoreCase(ticket.getString("lob"))&&"2770".equals(ticket.getString("product"))) || ("Health".equalsIgnoreCase(ticket.getString("lob")) && ("2890".equals(ticket.getString("product")) || "2808".equals(ticket.getString("product"))) && "Partial Refund".equalsIgnoreCase(ticket.getString("cancellationOption"))))
			{
				double minPremiumRetainAmount = ticket.getDouble("minPremiumRetainAmount");
				BigDecimal minPremiumRetainAmountDecimal = BigDecimal.valueOf(minPremiumRetainAmount); 
				//float floatminPremiumRetainAmount = (float)minPremiumRetainAmount;
				ticketData.setBigDecimal("min_premium_retain_amount", minPremiumRetainAmountDecimal);
				updateTicketData.setBigDecimal("statement1parameter41", minPremiumRetainAmountDecimal);
			}
			if(("Liability".equalsIgnoreCase(ticket.getString("lob")) &&("2774".equals(ticket.getString("product")) || "2776".equals(ticket.getString("product"))))||("Financial Line".equalsIgnoreCase(ticket.getString("lob")) && ("6105".equals(ticket.getString("product")) || "6106".equals(ticket.getString("product")) || "6108".equals(ticket.getString("product")) || "6107".equals(ticket.getString("product")) || "6109".equals(ticket.getString("product"))))||"Aviation".equalsIgnoreCase(ticket.getString("lob")))
			{
				double exchangeRate = ticket.getDouble("exchangeRate");
				BigDecimal exchangeRateDecimal = BigDecimal.valueOf(exchangeRate);
				//float floatexchangeRate = (float)exchangeRate;
				ticketData.setBigDecimal("exchange_rate", exchangeRateDecimal);
				updateTicketData.setBigDecimal("statement1parameter42", exchangeRateDecimal);
			}
			if("Liability".equalsIgnoreCase(ticket.getString("lob")) &&"2771".equals(ticket.getString("product")))
			{
				double endorsementAmount = ticket.getDouble("endorsementAmount");
				BigDecimal endorsementAmountDecimal = BigDecimal.valueOf(endorsementAmount); 
				//float floatendorsementAmount = (float)endorsementAmount;
				ticketData.setBigDecimal("endorsement_amount", endorsementAmountDecimal);
				updateTicketData.setBigDecimal("statement1parameter43", endorsementAmountDecimal);
				
				double endorsementERFAmount = ticket.getDouble("endorsementERFAmount");
				BigDecimal endorsementERFAmountDecimal = BigDecimal.valueOf(endorsementERFAmount); 
				//float floatendorsementERFAmount = (float)endorsementERFAmount;
				ticketData.setBigDecimal("endorsement_erf_amount", endorsementERFAmountDecimal);
				updateTicketData.setBigDecimal("statement1parameter44", endorsementERFAmountDecimal);
			}
			if("Aig Combined".equalsIgnoreCase(ticket.getString("lob")) &&  "Agreed Premium Cancellation".equalsIgnoreCase(ticket.getString("cancellationOption")))
			{
				double aigAgreedPremiumAmount = ticket.getDouble("aigAgreedPremiumAmount");
				BigDecimal aigAgreedPremiumAmountDecimal = BigDecimal.valueOf(aigAgreedPremiumAmount); 
				//float floataigAgreedPremiumAmount = (float)aigAgreedPremiumAmount;
				ticketData.setBigDecimal("agreed_premium_amount", aigAgreedPremiumAmountDecimal);
				updateTicketData.setBigDecimal("statement1parameter45", aigAgreedPremiumAmountDecimal);
			}
			if(("Liability".equalsIgnoreCase(ticket.getString("lob")) &&("2771".equals(ticket.getString("product")) || "2772".equals(ticket.getString("product")) || "2774".equals(ticket.getString("product")) || "2776".equals(ticket.getString("product"))))||("Financial Line".equalsIgnoreCase(ticket.getString("lob")) && ("6105".equals(ticket.getString("product")) || "6106".equals(ticket.getString("product")) || "6108".equals(ticket.getString("product")) || "6107".equals(ticket.getString("product")) || "6109".equals(ticket.getString("product")) || "6101".equals(ticket.getString("product")))) || ("Marine Cargo".equalsIgnoreCase(ticket.getString("lob")) &&("2151".equals(ticket.getString("product")) || "2154".equals(ticket.getString("product"))) &&  "Partial Refund".equalsIgnoreCase(ticket.getString("cancellationOption")))||"Aviation".equalsIgnoreCase(ticket.getString("lob")))
			{
				double commercialRefundAmount = ticket.getDouble("commercialRefundAmount");
				BigDecimal commercialRefundAmountDecimal = BigDecimal.valueOf(commercialRefundAmount); 
				//float floatcommercialRefundAmount = (float)commercialRefundAmount;
				ticketData.setBigDecimal("commercial_refund_amount", commercialRefundAmountDecimal);
				updateTicketData.setBigDecimal("statement1parameter48", commercialRefundAmountDecimal);
			}

		} /*else if (ticket.getString("stage").equalsIgnoreCase("COPS")) {
			if ((ticket.getString("refundType").equalsIgnoreCase(Constants.ExcessRefund.getValue())) && !(refundAmountValidation(ticket.getDouble("refundAmount"),
					ticket.getDouble("receiptDetail[1]/totalAmount"),
					ticket.getDouble("receiptDetail[1]/crsAmount")))) {
				throw new RuntimeException(
						"Refund Amount should be less than or equal to the difference between CRS Amount & Total Amount");
			} else{

				double refundAmount = ticket.getDouble("refundAmount");
				BigDecimal refundAmountDecimal = BigDecimal.valueOf(refundAmount); 
				//float floatRefundAmount = (float)refundAmount;
				ticketData.setBigDecimal("refund_amount", refundAmountDecimal);
				updateTicketData.setBigDecimal("statement1parameter4", refundAmountDecimal);

			}

		}  */
		else
		{
			double refundAmount = ticket.getDouble("refundAmount");
			BigDecimal refundAmountDecimal = BigDecimal.valueOf(refundAmount); 
			//float floatRefundAmount = (float)refundAmount;
			ticketData.setBigDecimal("refund_amount", refundAmountDecimal);
			updateTicketData.setBigDecimal("statement1parameter4", refundAmountDecimal);
		}

		/*if(ticket.getString("accountDetail/emailId")==null || ticket.getString("accountDetail/emailId")==""){
	    	throw new RuntimeException("Email ID cannot be blank");
	    }*/

		if(ticket.getString("accountDetail/emailId")!=null && ticket.getString("accountDetail/emailId")!="")
		{
			Boolean validEmail = validateEmail(ticket.getString("accountDetail/emailId").trim());
			if(!validEmail){
				throw new RuntimeException(ticket.getString("accountDetail/emailId")+" Invalid value input in Email ID. Please enter Email ID value in correct format");
			}
		}


		ticketData.setDate("last_modified_on", formatDate(new Date()));
		updateTicketData.setDate("statement1parameter8", formatDate(new Date()));
		System.out.println("last modified by in update ticket"+	ticket.getString("actionBy"));
		ticketData.setString("last_modified_by", ticket.getString("actionBy"));
		updateTicketData.setString("statement1parameter7", ticket.getString("actionBy"));

		// set the properties for the child object if they are present in the
		// request
		// check for account details
		if (ticket.getDataObject("accountDetail") != null) {
			// set the property for the account detail object
			DataObject accountdata = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_account_detail",
							"DboRas_Account_Detail");
			DataObject accountdataBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_account_detailbg",
							"DboRas_Account_DetailBG");
			Double id = ticket.getDouble("accountDetail/id");
			String pattern = "^[0-9]{10}$";
			String contactNo = ticket.getString("accountDetail/contactNo");
			//validateAccountDetail(ticket.getDataObject("accountDetail"));
			if (id == null || id == 0) {
				// create new account detail
				String accNo = ticket.getString("accountDetail/bankAccountNo");
				
				/*
				 * if (accNo == null || "".equalsIgnoreCase(accNo.trim())) {
				 * throw new RuntimeException(
				 * "Ticket update failed: Error while Saving the new account detail, account no cannot be null or blank"
				 * );
				 */
				// } else {
				// setting auto increment of account id
				/*
				 * accountdata.setDouble("account_id",
				 * ticket.getDouble("accountDetail/id"));
				 */
				accountdata
				.setDouble("ticket_id", ticket.getDouble("ticketID"));
				accountdata.setString("payee_name",
						ticket.getString("accountDetail/customerName"));
				accountdata.setString("bank_name",
						ticket.getString("accountDetail/customerBank"));
				accountdata.setString("ifsc_code",
						ticket.getString("accountDetail/customerIfscCode"));
				accountdata.setString("account_no", accNo);
				accountdata.setString("source",
						ticket.getString("accountDetail/source"));
				accountdata.setString("is_active", "Y");
				accountdata.setString("created_by",
					ticket.getString("accountDetail/createdBy")	);

				if(ticket.getString("accountDetail/emailId")!=null && ticket.getString("accountDetail/emailId")!="")
				{
					accountdata.setString("email_id",
							ticket.getString("accountDetail/emailId").trim());
				}
				
				accountdata.setString("contact_no",
						ticket.getString("accountDetail/contactNo"));
				
				accountdata.setString("last_modified_by",
						ticket.getString("actionBy"));
				accountdata.setDate("created_on", formatDate(new Date()));
				accountdata.setDate("last_modified_on", formatDate(new Date()));
				// }
				
				
				accountdata.setString("address_line1",
						ticket.getString("accountDetail/addressLine1"));
				accountdata.setString("address_line2",
						ticket.getString("accountDetail/addressLine2"));
				accountdata.setString("address_line3",
						ticket.getString("accountDetail/addressLine3"));
				accountdata.setString("state",
						ticket.getString("accountDetail/state"));
				accountdata.setString("city",
						ticket.getString("accountDetail/city"));
				accountdata.setString("area",
						ticket.getString("accountDetail/area"));
				accountdata.setString("pincode",
						ticket.getString("accountDetail/pincode"));
				accountdata.setString("bank_account_verification",
						ticket.getString("accountDetail/bankAccountNoVerification"));
				accountdata.setString("account_source",
						ticket.getString("accountDetail/accountSource"));
				accountdata.setString("landmark",
						ticket.getString("accountDetail/landmark"));
				accountdata.setString("district",
						ticket.getString("accountDetail/district"));   
				accountdataBG.setDataObject("DboRas_Account_Detail", accountdata);
			
				try {
					createAccount = (DataObject) locateService_DBInterfacePartner().invoke(
							"createDboRas_Account_DetailBG", accountdataBG);
					updateTicketData.setDouble("statement1parameter22", createAccount.getDouble("createDboRas_Account_DetailBGOutput/DboRas_Account_Detail/account_id"));
					accountdata.setDouble("account_id",
							createAccount.getDouble("createDboRas_Account_DetailBGOutput/DboRas_Account_Detail/account_id"));
				} 	
				catch (ServiceBusinessException sbe)
				{
					exceptionData = (DataObject)sbe.getData();
					rasException.setString("status", "FAILED");
					rasException.setString("error[0]/message", 
							exceptionData.get("message").toString());
					throw new ServiceBusinessException(rasException);
				}
				catch (ServiceRuntimeException sre) {
					throw new ServiceRuntimeException(sre.getMessage().toString());
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
				
			} else {
				accountdata.setDouble("account_id",
						ticket.getDouble("accountDetail/id"));
				accountdata
				.setDouble("ticket_id", ticket.getDouble("ticketID"));
				if (ticket.getString("accountDetail/customerName") != null)
					accountdata.setString("payee_name",
							ticket.getString("accountDetail/customerName"));
				if (ticket.getString("accountDetail/customerBank") != null)
					accountdata.setString("bank_name",
							ticket.getString("accountDetail/customerBank"));
				if (ticket.getString("accountDetail/customerIfscCode") != null)
					accountdata.setString("ifsc_code",
							ticket.getString("accountDetail/customerIfscCode"));
				if (ticket.getString("accountDetail/bankAccountNo") != null)
					accountdata.setString("account_no",
							ticket.getString("accountDetail/bankAccountNo"));
				if (ticket.getString("accountDetail/source") != null)
					accountdata.setString("source",
							ticket.getString("accountDetail/source"));
				if (ticket.getString("accountDetail/contactNo") != null)
				accountdata.setString("contact_no",
						ticket.getString("accountDetail/contactNo"));
				
				if(ticket.getString("accountDetail/emailId")!=null && ticket.getString("accountDetail/emailId")!="")
				{
					accountdata.setString("email_id",
							ticket.getString("accountDetail/emailId").trim());
				}
              
				accountdata.setString("is_active", "Y");
				accountdata.setString("created_by",
						ticket.getString("accountDetail/createdBy"));
				accountdata.setString("last_modified_by",
						ticket.getString("actionBy"));
				accountdata.setDate("last_modified_on", formatDate(new Date()));
				
				if (ticket.getString("accountDetail/addressLine1") != null)
				accountdata.setString("address_line1",
						ticket.getString("accountDetail/addressLine1"));
				if (ticket.getString("accountDetail/addressLine2") != null)
				accountdata.setString("address_line2",
						ticket.getString("accountDetail/addressLine2"));
				if (ticket.getString("accountDetail/addressLine3") != null)
				accountdata.setString("address_line3",
						ticket.getString("accountDetail/addressLine3"));
				if (ticket.getString("accountDetail/state") != null)
				accountdata.setString("state",
						ticket.getString("accountDetail/state"));
				if (ticket.getString("accountDetail/city") != null)
				accountdata.setString("city",
						ticket.getString("accountDetail/city"));
				if (ticket.getString("accountDetail/area") != null)
				accountdata.setString("area",
						ticket.getString("accountDetail/area"));
				if (ticket.getString("accountDetail/pincode") != null)
				accountdata.setString("pincode",
						ticket.getString("accountDetail/pincode"));
				if (ticket.getString("accountDetail/bankAccountNoVerification") != null)
				accountdata.setString("bank_account_verification",
						ticket.getString("accountDetail/bankAccountNoVerification"));
				if (ticket.getString("accountDetail/accountSource") != null)
					accountdata.setString("account_source",
							ticket.getString("accountDetail/accountSource"));
				if (ticket.getString("accountDetail/landmark") != null)
					accountdata.setString("landmark",
							ticket.getString("accountDetail/landmark"));
				if (ticket.getString("accountDetail/district") != null)
						accountdata.setString("district",
								ticket.getString("accountDetail/district"));  
				// update account detail
				DataObject updateAccountData = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateaccountdetails",
								"UpdateAccountDetails");
				DataObject updateAccountDataBG = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateaccountdetailsbg",
								"UpdateAccountDetailsBG");
				updateTicketData.setInt("statement1parameter22", ticket.getInt("accountDetail/id"));
				updateAccountData
				.setDouble("statement1parameter22", ticket.getDouble("ticketID"));
				if (ticket.getString("accountDetail/customerName") != null)
					updateAccountData.setString("statement1parameter1",
							ticket.getString("accountDetail/customerName"));
				if (ticket.getString("accountDetail/customerBank") != null)
					updateAccountData.setString("statement1parameter2",
							ticket.getString("accountDetail/customerBank"));
				if (ticket.getString("accountDetail/customerIfscCode") != null)
					updateAccountData.setString("statement1parameter3",
							ticket.getString("accountDetail/customerIfscCode"));
				if (ticket.getString("accountDetail/bankAccountNo") != null)
					updateAccountData.setString("statement1parameter4",
							ticket.getString("accountDetail/bankAccountNo"));
				if (ticket.getString("accountDetail/source") != null)
					updateAccountData.setString("statement1parameter5",
							ticket.getString("accountDetail/source"));
				if (ticket.getString("accountDetail/contactNo") != null)
					updateAccountData.setString("statement1parameter10",
						ticket.getString("accountDetail/contactNo"));
				
				if(ticket.getString("accountDetail/emailId")!=null && ticket.getString("accountDetail/emailId")!="")
				{
					updateAccountData.setString("statement1parameter9",
							ticket.getString("accountDetail/emailId").trim());
				}
              
				updateAccountData.setString("statement1parameter8", "Y");
			
				updateAccountData.setString("statement1parameter6",
						ticket.getString("actionBy"));
				updateAccountData.setDate("statement1parameter7", formatDate(new Date()));
				
				if (ticket.getString("accountDetail/addressLine1") != null)
					updateAccountData.setString("statement1parameter11",
						ticket.getString("accountDetail/addressLine1"));
				if (ticket.getString("accountDetail/addressLine2") != null)
					updateAccountData.setString("statement1parameter12",
						ticket.getString("accountDetail/addressLine2"));
				if (ticket.getString("accountDetail/addressLine3") != null)
					updateAccountData.setString("statement1parameter13",
						ticket.getString("accountDetail/addressLine3"));
				if (ticket.getString("accountDetail/landmark") != null)
					updateAccountData.setString("statement1parameter14",
						ticket.getString("accountDetail/landmark"));
				if (ticket.getString("accountDetail/district") != null)
					updateAccountData.setString("statement1parameter15",
						ticket.getString("accountDetail/district"));   
				if (ticket.getString("accountDetail/state") != null)
					updateAccountData.setString("statement1parameter16",
						ticket.getString("accountDetail/state"));
				if (ticket.getString("accountDetail/city") != null)
					updateAccountData.setString("statement1parameter17",
						ticket.getString("accountDetail/city"));
				if (ticket.getString("accountDetail/area") != null)
					updateAccountData.setString("statement1parameter18",
						ticket.getString("accountDetail/area"));
				if (ticket.getString("accountDetail/pincode") != null)
					updateAccountData.setString("statement1parameter19",
						ticket.getString("accountDetail/pincode"));
				if (ticket.getString("accountDetail/bankAccountNoVerification") != null)
					updateAccountData.setString("statement1parameter20",
						ticket.getString("accountDetail/bankAccountNoVerification"));
				if (ticket.getString("accountDetail/accountSource") != null)
					updateAccountData.setString("statement1parameter21",
						ticket.getString("accountDetail/accountSource"));
				
				updateAccountDataBG.setDataObject("UpdateAccountDetails", updateAccountData);
				try {
					createAccount =	(DataObject) locateService_DBInterfacePartner().invoke(
							"executeUpdateAccountDetailsBG", updateAccountDataBG);
				} 	
				catch (ServiceBusinessException sbe)
				{
					exceptionData = (DataObject)sbe.getData();
					rasException.setString("status", "FAILED");
					rasException.setString("error[0]/message", 
							exceptionData.get("message").toString());
					throw new ServiceBusinessException(rasException);
				}
				catch (ServiceRuntimeException sre) {
					throw new ServiceRuntimeException(sre.getMessage().toString());
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
			ticketData.set("ras_account_detailobj", accountdata);
	
		}

		// check for Receipt details List is Empty
		if (ticket.getList("receiptDetail") != null
				&& ticket.getList("receiptDetail").size() != 0) {

			Double id = (double) 0;
			// List to Hold all the Records of Receipt Detail
			List receiptList = new ArrayList();

			// Accessing Receipt Detail one by one and Assigning them to a List
			for (int i = 1; i <= ticket.getList("receiptDetail").size(); i++) {
				id = ticket.getDouble("receiptDetail[" + i + "]/id");
				
				if (id == 0 || id == null) {
					
					String crsNo = ticket.getString("receiptDetail[" + i
							+ "]/crsNo");
					
					DataObject receiptData = getBOFactory()
							.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_receipt_detail",
									"DboRas_Receipt_Detail");
					DataObject receiptDataBG = getBOFactory()
							.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_receipt_detailbg",
									"DboRas_Receipt_DetailBG");
					/*
					 * if (crsNo == null || "".equalsIgnoreCase(crsNo.trim())) {
					 * throw new RuntimeException(
					 * "Ticket update failed: Error while inserting the new Receipt detail, Receipt no cannot be null or blank"
					 * ); }
					 */

				
					// setting auto increment of account id
					/*
					 * receiptData.setDouble("slno",
					 * ticket.getDouble("receiptDetail["+i+"]/id"));
					 */
					/*if (ticket.getDouble("receiptDetail[" + i + "]/crsAmount") != 0
							&& ticket.getDouble("receiptDetail[" + i
									+ "]/totalAmount") != 0) {

						if ((ticket.getString("refundType").equalsIgnoreCase(Constants.ExcessRefund.getValue())) && !(refundAmountValidation(
								ticket.getDouble("refundAmount"),
								ticket.getDouble("receiptDetail[" + i
										+ "]/totalAmount"),
										ticket.getDouble("receiptDetail[" + i
												+ "]/crsAmount"))))
							throw new RuntimeException(
									" Refund Amount should be less than or equal to the difference between CRS Amount & Total Amount");

					}  */
					receiptData.setDouble("ticket_id",
							ticket.getDouble("ticketID"));
					authCode = ticket.getString("receiptDetail[" + i+ "]/authCode");
					receiptData.setString(
							"auth_code",
							ticket.getString("receiptDetail[" + i
									+ "]/authCode"));
					receiptData.setString("crs_number",
							ticket.getString("receiptDetail[" + i + "]/crsNo"));
					receiptData.setString(
							"crs_status",
							ticket.getString("receiptDetail[" + i
									+ "]/crsStatus"));

					double crsAmount = ticket.getDouble("receiptDetail[" + i+ "]/crsAmount");
					BigDecimal crsAmountDecimal = BigDecimal.valueOf(crsAmount); 
				    //float floatCRSAmount = (float)crsAmount;
					receiptData.setBigDecimal("crs_amount",crsAmountDecimal);

					double balanceAmount = ticket.getDouble("receiptDetail[" + i+ "]/balanceAmount");
					BigDecimal balanceAmountDecimal = BigDecimal.valueOf(balanceAmount); 
				    //float floatbalanceAmount = (float)balanceAmount;
					receiptData.setBigDecimal("balance_amount",balanceAmountDecimal);

					double totalAmount = ticket.getDouble("receiptDetail[" + i+ "]/totalAmount");
					BigDecimal totalAmountDecimal = BigDecimal.valueOf(totalAmount);
					//float floattotalAmount = (float)totalAmount;
					receiptData.setBigDecimal("total_amount",totalAmountDecimal);
					
					double taggedAmount = ticket.getDouble("receiptDetail[" + i+ "]/taggedAmount");
					BigDecimal taggedAmountDecimal = BigDecimal.valueOf(taggedAmount);
					//float floattaggedAmount = (float)taggedAmount;
					receiptData.setBigDecimal("tagged_amount",taggedAmountDecimal);


					receiptData.setString("payer_type",ticket.getString("payeeType"));
					receiptData.setString(
							"payee_name",
							ticket.getString("receiptDetail[" + i
									+ "]/payeeName"));
					receiptData.setString(
							"payer_id",
							ticket.getString("receiptDetail[" + i
									+ "]/CRSPayerID"));
					receiptData.setString(
							"payment_received_method",
							ticket.getString("receiptDetail[" + i
									+ "]/paymentReceivedMethod"));
					receiptData.setString(
							"payee_bank",
							ticket.getString("receiptDetail[" + i
									+ "]/payeeBank"));
					receiptData.setString(
							"payee_ifsc_code",
							ticket.getString("receiptDetail[" + i
									+ "]/payeeIfscCode"));
					receiptData.setString(
							"payee_account_number",
							ticket.getString("receiptDetail[" + i
									+ "]/payeeBankAccountNo"));
					receiptData.setString(
							"payement_source",
							ticket.getString("receiptDetail[" + i
									+ "]/payementSource"));
					receiptData.setString(
							"house_bank_name",
							ticket.getString("receiptDetail[" + i
									+ "]/houseBankName"));
					receiptData.setString(
							"house_bank_account_no",
							ticket.getString("receiptDetail[" + i
									+ "]/houseBankAccountNo"));
					receiptData.setString(
							"payment_mode",
							ticket.getString("receiptDetail[" + i
									+ "]/payementMode"));
					receiptData.setString(
							"policy_no",
							ticket.getString("receiptDetail[" + i
									+ "]/policyNumbers"));
					receiptData.setString(
							"subreceipt_no",
							ticket.getString("receiptDetail[" + i
									+ "]/subreceiptNumbers"));
					receiptData.setDate(
							"receipt_date",
							ticket.getDate("receiptDetail[" + i
									+ "]/receiptDate"));
					receiptData.setString(
							"drawer_name",
							ticket.getString("receiptDetail[" + i
									+ "]/drawerName"));
					receiptData.setString(
							"lan_number",
							ticket.getString("receiptDetail[" + i
									+ "]/receiptLANNo"));
					receiptData.setString(
							"application_number",
							ticket.getString("receiptDetail[" + i
									+ "]/receiptApplicationNo"));
					receiptData.setString(
							"receipt_account_number",
							ticket.getString("receiptDetail[" + i
									+ "]/receiptAccountNo"));
					receiptData.setString(
							"receipt_ifsc_code",
							ticket.getString("receiptDetail[" + i
									+ "]/receiptIFSCCode"));
					receiptData.setString(
							"lob",
							ticket.getString("receiptDetail[" + i
									+ "]/lob"));
					receiptData.setString(
							"cd_account_number",
							ticket.getString("receiptDetail[" + i
									+ "]/cdAcccountNumber"));
					receiptData.setString(
							"payer_name",
							ticket.getString("receiptDetail[" + i
									+ "]/receiptPayerName"));
					receiptData.setDate(
							"instrument_date",
							ticket.getDate("receiptDetail[" + i
									+ "]/instrumentDate"));
					receiptData.setString(
							"claim_ifsc_code",
							ticket.getString("receiptDetail[" + i
									+ "]/claimIFSCCode"));
					receiptData.setString(
							"claim_account_number",
							ticket.getString("receiptDetail[" + i
									+ "]/claimAccountNo"));
					receiptData.setString(
							"claim_bank_name",
							ticket.getString("receiptDetail[" + i
									+ "]/claimBankName"));
					receiptData.setString(
							"claim_email_id",
							ticket.getString("receiptDetail[" + i
									+ "]/claimEmailID"));
					receiptData.setString(
							"claim_contact_number",
							ticket.getString("receiptDetail[" + i
									+ "]/claimMobileNo"));
					receiptData.setString(
							"claimant_name",
							ticket.getString("receiptDetail[" + i
									+ "]/claimantName"));
					receiptData.setString("created_by",
							ticket.getString("raisedBy"));

					receiptData.setString("last_modified_by",
							ticket.getString("actionBy"));

					receiptData.setDate("created_on", formatDate(new Date()));
					receiptData.setDate("last_modified_on",
							formatDate(new Date()));

					// Adding Record to a List
					receiptList.add(receiptData);
					// Adding ReceiptList to Ticket BO
					if (receiptList != null && receiptList.size() > 0)
						ticketData.setList("ras_receipt_detailobj", receiptList);
					receiptDataBG.setDataObject("DboRas_Receipt_Detail", receiptData);
					try {
						locateService_DBInterfacePartner().invoke(
								"createDboRas_Receipt_DetailBG", receiptDataBG);
						
					} 	
					catch (ServiceBusinessException sbe)
					{
						exceptionData = (DataObject)sbe.getData();
						rasException.setString("status", "FAILED");
						rasException.setString("error[0]/message", 
								exceptionData.get("message").toString());
						throw new ServiceBusinessException(rasException);
					}
					catch (ServiceRuntimeException sre) {
						throw new ServiceRuntimeException(sre.getMessage().toString());
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				else
				{
					//update receipt detail
					DataObject updateReceiptData = getBOFactory()
							.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updatereceiptdetails",
									"UpdateReceiptDetails");
					DataObject updateReceiptDataBG = getBOFactory()
							.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updatereceiptdetailsbg",
									"UpdateReceiptDetailsBG");
					String crsNo = ticket.getString("receiptDetail[" + i
							+ "]/crsNo");
					/*
					 * if (crsNo == null || "".equalsIgnoreCase(crsNo.trim())) {
					 * throw new RuntimeException(
					 * "Ticket update failed: Error while inserting the new Receipt detail, Receipt no cannot be null or blank"
					 * ); }
					 */

					
					// setting auto increment of account id
					/*
					 * receiptData.setDouble("slno",
					 * ticket.getDouble("receiptDetail["+i+"]/id"));
					 */
					/*if (ticket.getDouble("receiptDetail[" + i + "]/crsAmount") != 0
							&& ticket.getDouble("receiptDetail[" + i
									+ "]/totalAmount") != 0) {

						if ((ticket.getString("refundType").equalsIgnoreCase(Constants.ExcessRefund.getValue())) && !(refundAmountValidation(
								ticket.getDouble("refundAmount"),
								ticket.getDouble("receiptDetail[" + i
										+ "]/totalAmount"),
										ticket.getDouble("receiptDetail[" + i
												+ "]/crsAmount"))))
							throw new RuntimeException(
									" Refund Amount should be less than or equal to the difference between CRS Amount & Total Amount");

					}   */
					
					updateReceiptData.setDouble("statement1parameter39",
							ticket.getDouble("ticketID"));
					authCode = ticket.getString("receiptDetail[" + i+ "]/authCode");
					updateReceiptData.setString(
							"statement1parameter2",
							ticket.getString("receiptDetail[" + i
									+ "]/authCode"));
					updateReceiptData.setString("statement1parameter1",
							ticket.getString("receiptDetail[" + i + "]/crsNo"));
					updateReceiptData.setString(
							"statement1parameter3",
							ticket.getString("receiptDetail[" + i
									+ "]/crsStatus"));

					double crsAmount = ticket.getDouble("receiptDetail[" + i+ "]/crsAmount");
					BigDecimal crsAmountDecimal = BigDecimal.valueOf(crsAmount); 
				    //float floatCRSAmount = (float)crsAmount;
					updateReceiptData.setBigDecimal("statement1parameter4",crsAmountDecimal);

					double balanceAmount = ticket.getDouble("receiptDetail[" + i+ "]/balanceAmount");
					BigDecimal balanceAmountDecimal = BigDecimal.valueOf(balanceAmount); 
				    //float floatbalanceAmount = (float)balanceAmount;
					updateReceiptData.setBigDecimal("statement1parameter13",balanceAmountDecimal);

					double totalAmount = ticket.getDouble("receiptDetail[" + i+ "]/totalAmount");
					BigDecimal totalAmountDecimal = BigDecimal.valueOf(totalAmount);
					//float floattotalAmount = (float)totalAmount;
					updateReceiptData.setBigDecimal("statement1parameter14",totalAmountDecimal);
					
					double taggedAmount = ticket.getDouble("receiptDetail[" + i+ "]/taggedAmount");
					BigDecimal taggedAmountDecimal = BigDecimal.valueOf(taggedAmount);
					//float floattaggedAmount = (float)taggedAmount;
					updateReceiptData.setBigDecimal("statement1parameter16",taggedAmountDecimal);


					updateReceiptData.setString("statement1parameter5",ticket.getString("payeeType"));
					updateReceiptData.setString(
							"statement1parameter6",
							ticket.getString("receiptDetail[" + i
									+ "]/payeeName"));
					updateReceiptData.setString(
							"statement1parameter15",
							ticket.getString("receiptDetail[" + i
									+ "]/CRSPayerID"));
					updateReceiptData.setString(
							"statement1parameter7",
							ticket.getString("receiptDetail[" + i
									+ "]/paymentReceivedMethod"));
					updateReceiptData.setString(
							"statement1parameter8",
							ticket.getString("receiptDetail[" + i
									+ "]/payeeBank"));
					updateReceiptData.setString(
							"statement1parameter9",
							ticket.getString("receiptDetail[" + i
									+ "]/payeeIfscCode"));
					updateReceiptData.setString(
							"statement1parameter10",
							ticket.getString("receiptDetail[" + i
									+ "]/payeeBankAccountNo"));
					updateReceiptData.setString(
							"statement1parameter17",
							ticket.getString("receiptDetail[" + i
									+ "]/payementSource"));
					updateReceiptData.setString(
							"statement1parameter18",
							ticket.getString("receiptDetail[" + i
									+ "]/houseBankName"));
					updateReceiptData.setString(
							"statement1parameter19",
							ticket.getString("receiptDetail[" + i
									+ "]/houseBankAccountNo"));
					updateReceiptData.setString(
							"statement1parameter20",
							ticket.getString("receiptDetail[" + i
									+ "]/payementMode"));
					updateReceiptData.setString(
							"statement1parameter21",
							ticket.getString("receiptDetail[" + i
									+ "]/policyNumbers"));
					updateReceiptData.setString(
							"statement1parameter22",
							ticket.getString("receiptDetail[" + i
									+ "]/subreceiptNumbers"));
					updateReceiptData.setDate(
							"statement1parameter23",
							ticket.getDate("receiptDetail[" + i
									+ "]/receiptDate"));
					updateReceiptData.setString(
							"statement1parameter24",
							ticket.getString("receiptDetail[" + i
									+ "]/drawerName"));
					updateReceiptData.setString(
							"statement1parameter25",
							ticket.getString("receiptDetail[" + i
									+ "]/receiptLANNo"));
					updateReceiptData.setString(
							"statement1parameter26",
							ticket.getString("receiptDetail[" + i
									+ "]/receiptApplicationNo"));
					updateReceiptData.setString(
							"statement1parameter27",
							ticket.getString("receiptDetail[" + i
									+ "]/receiptAccountNo"));
					updateReceiptData.setString(
							"statement1parameter28",
							ticket.getString("receiptDetail[" + i
									+ "]/receiptIFSCCode"));
					updateReceiptData.setString(
							"statement1parameter29",
							ticket.getString("receiptDetail[" + i
									+ "]/cdAcccountNumber"));
					updateReceiptData.setString(
							"statement1parameter30",
							ticket.getString("receiptDetail[" + i
									+ "]/lob"));
					updateReceiptData.setDate(
							"statement1parameter31",
							ticket.getDate("receiptDetail[" + i
									+ "]/instrumentDate"));
					updateReceiptData.setString(
							"statement1parameter32",
							ticket.getString("receiptDetail[" + i
									+ "]/claimBankName"));
					updateReceiptData.setString(
							"statement1parameter33",
							ticket.getString("receiptDetail[" + i
									+ "]/claimAccountNo"));
					updateReceiptData.setString(
							"statement1parameter34",
							ticket.getString("receiptDetail[" + i
									+ "]/claimIFSCCode"));
					updateReceiptData.setString(
							"statement1parameter35",
							ticket.getString("receiptDetail[" + i
									+ "]/claimEmailID"));
					updateReceiptData.setString(
							"statement1parameter36",
							ticket.getString("receiptDetail[" + i
									+ "]/claimMobileNo"));
					updateReceiptData.setString(
							"statement1parameter37",
							ticket.getString("receiptDetail[" + i
									+ "]/claimantName"));
					updateReceiptData.setString(
							"statement1parameter38",
							ticket.getString("receiptDetail[" + i
									+ "]/receiptPayerName"));
					updateReceiptData.setString("statement1parameter12",
							ticket.getString("actionBy"));
					updateReceiptData.setDate("statement1parameter11",
							formatDate(new Date()));

					// Adding Record to a List
					receiptList.add(updateReceiptData);
					
					updateReceiptDataBG.setDataObject("UpdateReceiptDetails", updateReceiptData);
					try {
						locateService_DBInterfacePartner().invoke(
								"executeUpdateReceiptDetailsBG", updateReceiptDataBG);
					} 	
					catch (ServiceBusinessException sbe)
					{
						exceptionData = (DataObject)sbe.getData();
						rasException.setString("status", "FAILED");
						rasException.setString("error[0]/message", 
								exceptionData.get("message").toString());
						throw new ServiceBusinessException(rasException);
					}
					catch (ServiceRuntimeException sre) {
						throw new ServiceRuntimeException(sre.getMessage().toString());
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				
			}
			
		}

		// check for Policy Detail Object

		if ((ticket.getString("refundType").equals(Constants.CancellationRefund.getValue()) || 
		"CANCELLATION ONLY".equalsIgnoreCase(ticket.getString("refundType")) || "NEGATIVE ENDORSEMENT REFUND".equalsIgnoreCase(ticket.getString("refundType"))) 
				&& ticket.getDataObject("policyDetail") != null) {
			// set the property for the policy detail object
			DataObject policyData = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_policy_detail",
							"DboRas_Policy_Detail");
			
			DataObject policyDataBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_policy_detailbg",
							"DboRas_Policy_DetailBG");
			Double id = ticket.getDouble("policyDetail/id");
			if (id == null || id == 0) {
				String policyNo = ticket.getString("policyDetail/policyNo");
				if (policyNo == null || "".equalsIgnoreCase(policyNo.trim())) {
					throw new RuntimeException(
							"Ticket update failed: Error while inserting the new policy detail, Policy no cannot be null or blank");
				} else {
					// setting auto increment of policy_id
					/*
					 * policyData.setDouble("slno",
					 * ticket.getDouble("policyDetail/id"));
					 */
					policyData.setDouble("ticket_id",
							ticket.getDouble("ticketID"));


					double aggreedPremium = ticket.getDouble("policyDetail/aggreedPremiumAmount");
					BigDecimal aggreedPremiumDecimal = BigDecimal.valueOf(aggreedPremium); 
					//float floataggreedPremium = (float)aggreedPremium;
					policyData.setBigDecimal("agreedpremiumamount", aggreedPremiumDecimal);



					double policyPremium = ticket.getDouble("policyDetail/policyPremium");
					BigDecimal policyPremiumDecimal = BigDecimal.valueOf(policyPremium); 
					//float floatpolicyPremium = (float)policyPremium;
					policyData.setBigDecimal("policy_premium",policyPremiumDecimal);

					policyData.setString("policyno",
							ticket.getString("policyDetail/policyNo"));
					policyData.setString("proposalno",
							ticket.getString("policyDetail/proposalNo"));
					policyData.setString("lob", ticket.getString("lob"));
					policyData.setString("product",
							ticket.getString("policyDetail/product"));
					policyData.setString("proposer_name",
							ticket.getString("policyDetail/proposerName"));
					policyData.setString("aadhar_no",
							ticket.getString("policyDetail/aadharNo"));
					policyData.setString("producer_code",
							ticket.getString("policyDetail/producerCode"));
					policyData.setString("producer_name",
							ticket.getString("policyDetail/producerName"));
					policyData.setString("status",
							ticket.getString("policyDetail/policyStatus"));
					policyData.setDate("application_date",
							ticket.getDate("policyDetail/applicationDate"));
					policyData.setDate("polcy_start_date",
							ticket.getDate("policyDetail/policyStartDate"));
					policyData.setDate("policy_end_date",
							ticket.getDate("policyDetail/policyEndDate"));
					policyData.setDate("conversion_date",
							ticket.getDate("policyDetail/conversionDate"));
					policyData.setString("created_by",
							ticket.getString("raisedBy"));
					policyData.setString("last_modified_by",
							ticket.getString("actionBy"));
					policyData.setString("auth_code",
							authCode);
					policyData.setString("lan_number",
							ticket.getString("policyDetail/lanNumber"));
					
					policyData.setString("search_by",
							ticket.getString("policyDetail/searchBy"));
					
					policyData.setString("certificate_number",
							ticket.getString("policyDetail/certificateNo"));
					policyData.setString("policy_type",
							ticket.getString("policyDetail/policyType"));
					
					policyData.setString("engine_number",
							ticket.getString("policyDetail/engineNumber"));
					policyData.setString("chasis_number",
							ticket.getString("policyDetail/chasisNumber"));
					
					policyData.setString("registration_no",
							ticket.getString("policyDetail/registrationNo"));
					
					policyData.setString("customer_code",
							ticket.getString("policyDetail/customerCode"));
					policyData.setString("policy_cover_type",
							ticket.getString("policyDetail/policyCoverType"));
					
					
					policyData.setString("dealer_name",
							ticket.getString("policyDetail/dealerName"));
					policyData.setString("dealer_code",
							ticket.getString("policyDetail/dealerCode"));
					policyData.setString("financier_name",
							ticket.getString("policyDetail/financierName"));
					policyData.setString("financier_code",
							ticket.getString("policyDetail/financierCode"));
					policyData.setString("application_number",
							ticket.getString("policyDetail/applicationNumber"));
					
					policyData.setDate("created_on", formatDate(new Date()));
					policyData.setDate("last_modified_on",
							formatDate(new Date()));

					// Check for Insured Detail Object is Empty
					if (ticket.getList("policyDetail/insuredBO") != null
							&& ticket.getList("policyDetail/insuredBO").size() != 0) {
						// List to Store all the Records of Insured BO
						List InsuredList = new ArrayList();
						// Accessing and assigning records of Insured BO to a
						// List
						for (int i = 1; i <= ticket.getList(
								"policyDetail/insuredBO").size(); i++) {

							DataObject insuredData = getBOFactory()
									.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_insured",
											"DboRas_Insured");
							DataObject insuredDataBG = getBOFactory()
									.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_insuredbg",
											"DboRas_InsuredBG");

							insuredData.setString(
									"name",
									ticket.getString("policyDetail/insuredBO["
											+ i + "]/name"));
							insuredData.setString(
									"contactno",
									ticket.getString("policyDetail/insuredBO["
											+ i + "]/contactNo"));
							insuredData.setString(
									"address",
									ticket.getString("policyDetail/insuredBO["
											+ i + "]/address"));
							if (ticket.getDate("policyDetail/insuredBO[" + i
									+ "]/dob") != null) {
								insuredData.setDate("dob", formatDate(ticket
										.getDate("policyDetail/insuredBO[" + i
												+ "]/dob")));
							}
							insuredData.setDouble("ticket_id",
									ticket.getDouble("ticketID"));
							insuredData.setDouble(
									"slno",
									ticket.getDouble("policyDetail/insuredBO["
											+ i + "]/id"));
							insuredDataBG.setDataObject("DboRas_Insured", insuredData);
							try {
								DataObject createPolicy = (DataObject) locateService_DBInterfacePartner().invoke(
										"createDboRas_InsuredBG", insuredDataBG);
							} 	
							catch (ServiceBusinessException sbe)
							{
								exceptionData = (DataObject)sbe.getData();
								rasException.setString("status", "FAILED");
								rasException.setString("error[0]/message", 
										exceptionData.get("message").toString());
								throw new ServiceBusinessException(rasException);
							}
							catch (ServiceRuntimeException sre) {
								throw new ServiceRuntimeException(sre.getMessage().toString());
							} catch (Exception e) {
								throw new RuntimeException(e.getMessage());
							}
						
							InsuredList.add(insuredData);
						}
						// Assign Insured details Records to PolicyData BO
						policyData.setList("ras_insuredobj", InsuredList);
					}

				}
				System.out.println("enter update");
				ticketData.set("ras_policy_detailobj", policyData);
				policyDataBG.setString("verb", "Create");
				policyDataBG.setDataObject("DboRas_Policy_Detail", policyData);
				System.out.println("after update"+policyData.getString("policyno"));
				
				try {
					DataObject createPolicy = (DataObject) locateService_DBInterfacePartner().invoke(
							"createDboRas_Policy_DetailBG", policyDataBG);
					updateTicketData.setDouble("statement1parameter20", createPolicy.getDouble("createDboRas_Policy_DetailBGOutput/DboRas_Policy_Detail/slno"));
				} 	
				catch (ServiceBusinessException sbe)
				{
					exceptionData = (DataObject)sbe.getData();
					rasException.setString("status", "FAILED");
					rasException.setString("error[0]/message", 
							exceptionData.get("message").toString());
					throw new ServiceBusinessException(rasException);
				}
				catch (ServiceRuntimeException sre) {
					throw new ServiceRuntimeException(sre.getMessage().toString());
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			
			}
			else
				updateTicketData.setDouble("statement1parameter20", ticket.getDouble("policyDetail/id"));
			}
			
		
	
		// check for refund object
		if (ticket.getDataObject("refundDetail") != null) {
			// set the property for the refund detail object
			
			Double id = ticket.getDouble("refundDetail/refundId");
			if (id == null || id == 0) {
				DataObject refundData = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_refund",
								"DboRas_Refund");
				DataObject refundDataBG = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_refundbg",
								"DboRas_RefundBG");
				
				// create new refund object
				// setting auto_increment of refund_id
				/* refundData.setDouble("refund_id", id); */
				if (ticket.get("ticketID") != null)
					refundData.setDouble("ticket_id",
							ticket.getDouble("ticketID"));

				if (ticket.getString("refundDetail/policyNo") != null)
					refundData.setString("policy_no",
							ticket.getString("refundDetail/policyNo"));
				if (ticket.getString("refundDetail/refundType") != null)
					refundData.setString("refund_type",
							ticket.getString("refundDetail/refundType"));

				if (ticket.getString("refundDetail/beneName") != null)
					refundData.setString("bene_name",
							ticket.getString("refundDetail/beneName"));

				/*	if (ticket.getDouble("refundDetail/refundAmount") != 0)
					refundData.setDouble("refund_amount",
							ticket.getDouble("refundDetail/refundAmount"));*/

				double refundAmount = ticket.getDouble("refundDetail/refundAmount");
				BigDecimal refundAmountDecimal = BigDecimal.valueOf(refundAmount); 
				//float floatRefundAmount = (float)refundAmount;
				refundData.setBigDecimal("refund_amount", refundAmountDecimal);


				if (ticket.getDate("refundDetail/txnDate") != null)
					refundData.setDate("txn_date",
							formatDate(ticket.getDate("refundDetail/txnDate")));

				if (ticket.getString("refundDetail/txnNo") != null)
					refundData.setString("txn_no",
							ticket.getString("refundDetail/txnNo"));

				if (ticket.getString("voucherNo") != null)
					refundData.setString("voucher_no",
							ticket.getString("voucherNo"));

				/*if (ticket.getDouble("refundDetail/premiumAmount") != 0)
					refundData.setDouble("premium_amount",
							ticket.getDouble("refundDetail/premiumAmount"));  */

				double premiumAmount = ticket.getDouble("refundDetail/premiumAmount");
				BigDecimal premiumAmountDecimal = BigDecimal.valueOf(premiumAmount); 
				//float floatpremiumAmount = (float)premiumAmount;
				refundData.setBigDecimal("premium_amount", premiumAmountDecimal);
				
				if (ticket.getString("refundDetail/ifscCode") != null)
					refundData.setString("ifsc_code",
							ticket.getString("refundDetail/ifscCode"));

				if (ticket.getString("refundDetail/chequeNo") != null)
					refundData.setString("cheque_no",
							ticket.getString("refundDetail/chequeNo"));

				if (ticket.getString("refundDetail/bankName") != null)
					refundData.setString("bank_name",
							ticket.getString("refundDetail/bankName"));

				if (ticket.getString("refundDetail/branchName") != null)
					refundData.setString("branch_name",
							ticket.getString("refundDetail/branchName"));

				if (ticket.getString("refundDetail/address1") != null)
					refundData.setString("address1",
							ticket.getString("refundDetail/address1"));

				if (ticket.getString("refundDetail/address2") != null)
					refundData.setString("address2",
							ticket.getString("refundDetail/address2"));

				if (ticket.getString("refundDetail/address3") != null)
					refundData.setString("address3",
							ticket.getString("refundDetail/address3"));

				if (ticket.getString("refundDetail/city") != null)
					refundData.setString("city",
							ticket.getString("refundDetail/city"));

				if (ticket.getString("refundDetail/emailId") != null)
					refundData.setString("emailid",
							ticket.getString("refundDetail/emailId"));

				if (ticket.getString("refundDetail/status") != null)
					refundData.setString("status",
							ticket.getString("refundDetail/status"));

				if (ticket.getString("refundDetail/remarks") != null)
					refundData.setString("remarks",
							ticket.getString("refundDetail/remarks"));

				if (ticket.getString("accountDetail/emailId") != null)
					refundData.setString("emailid",
							ticket.getString("accountDetail/emailId"));
				
			
				if(ticket.getString("refundDetail/accountNo")!=null){
					refundData.setString("account_no",ticket.getString("refundDetail/accountNo"));
				}
				
				refundData
				.setString("created_by", ticket.getString("raisedBy"));
				refundData.setString("last_modified_by",
						ticket.getString("actionBy"));
				refundData.setDate("created_on", formatDate(new Date()));
				refundData.setDate("last_modified_on", formatDate(new Date()));
				ticketData.set("ras_refundobj", refundData);
				refundDataBG.setString("verb", "Create");
				refundDataBG.setDataObject("DboRas_Refund", refundData);
				
				try {
					DataObject createRefund = (DataObject) locateService_DBInterfacePartner().invoke(
							"createDboRas_RefundBG", refundDataBG);
					updateTicketData.setDouble("statement1parameter21", createRefund.getDouble("createDboRas_RefundBGOutput/DboRas_Refund/refund_id"));
					
				} 	
				catch (ServiceBusinessException sbe)
				{
					exceptionData = (DataObject)sbe.getData();
					rasException.setString("status", "FAILED");
					rasException.setString("error[0]/message", 
							exceptionData.get("message").toString());
					throw new ServiceBusinessException(rasException);
				}
				catch (ServiceRuntimeException sre) {
					throw new ServiceRuntimeException(sre.getMessage().toString());
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			
			} else {
				// update refund detail
				DataObject updateRefundData = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updaterefunddetails",
								"UpdateRefundDetails");
				DataObject updateRefundDataBG = getBOFactory()
						.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updaterefunddetailsbg",
								"UpdateRefundDetailsBG");
				updateTicketData.setInt("statement1parameter21", ticket.getInt("refundDetail/refundId"));
				updateRefundData.setDouble("statement1parameter26", ticket.getDouble("ticketID"));
				updateRefundData.setString("statement1parameter1",
						ticket.getString("refundDetail/policyNo"));
				if (ticket.getString("refundDetail/refundType") != null)
					updateRefundData.setString("statement1parameter2",
							ticket.getString("refundDetail/refundType"));
				if (ticket.getString("refundDetail/beneName") != null)
					updateRefundData.setString("statement1parameter3",
							ticket.getString("refundDetail/beneName"));

				/*	if (ticket.get("refundDetail/refundAmount") != null)
					refundData.setDouble("refund_amount",
							ticket.getDouble("refundDetail/refundAmount"));*/

				double refundAmount = ticket.getDouble("refundDetail/refundAmount");
				BigDecimal refundAmountDecimal = BigDecimal.valueOf(refundAmount); 
				//float floatRefundAmount = (float)refundAmount;
				updateRefundData.setBigDecimal("statement1parameter4", refundAmountDecimal);


				if (ticket.getDate("refundDetail/txnDate") != null)
					updateRefundData.setDate("statement1parameter5",
							formatDate(ticket.getDate("refundDetail/txnDate")));
				if (ticket.getString("refundDetail/txnNo") != null)
					updateRefundData.setString("statement1parameter6",
							ticket.getString("refundDetail/txnNo"));

				/*	if (ticket.get("refundDetail/premiumAmount") != null)
					refundData.setDouble("premium_amount",
							ticket.getDouble("refundDetail/premiumAmount"));*/
				
				double premiumAmount = ticket.getDouble("refundDetail/premiumAmount");
				BigDecimal premiumAmountDecimal = BigDecimal.valueOf(premiumAmount); 
				//float floatpremiumAmount = (float)premiumAmount;
				updateRefundData.setBigDecimal("statement1parameter7", premiumAmountDecimal);

				if (ticket.getString("refundDetail/ifscCode") != null)
					updateRefundData.setString("statement1parameter8",
							ticket.getString("refundDetail/ifscCode"));
				if (ticket.getString("refundDetail/chequeNo") != null)
					updateRefundData.setString("statement1parameter9",
							ticket.getString("refundDetail/chequeNo"));
				if (ticket.getString("refundDetail/bankName") != null)
					updateRefundData.setString("statement1parameter10",
							ticket.getString("refundDetail/bankName"));
				if (ticket.getString("refundDetail/branchName") != null)
					updateRefundData.setString("statement1parameter11",
							ticket.getString("refundDetail/branchName"));
				if (ticket.getString("refundDetail/address1") != null)
					updateRefundData.setString("statement1parameter12",
							ticket.getString("refundDetail/address1"));
				if (ticket.getString("refundDetail/address2") != null)
					updateRefundData.setString("statement1parameter13",
							ticket.getString("refundDetail/address2"));
				if (ticket.getString("refundDetail/address3") != null)
					updateRefundData.setString("statement1parameter14",
							ticket.getString("refundDetail/address3"));
				if (ticket.getString("refundDetail/city") != null)
					updateRefundData.setString("statement1parameter15",
							ticket.getString("refundDetail/city"));
				if (ticket.getString("refundDetail/emailId") != null)
					updateRefundData.setString("statement1parameter16",
							ticket.getString("refundDetail/emailId"));
				if (ticket.getString("refundDetail/status") != null)
					updateRefundData.setString("statement1parameter17",
							ticket.getString("refundDetail/status"));
				if (ticket.getString("refundDetail/remarks") != null)
					updateRefundData.setString("statement1parameter18",
							ticket.getString("refundDetail/remarks"));

				if(ticket.getString("refundDetail/accountNo")!=null){
					updateRefundData.setString("statement1parameter21",ticket.getString("refundDetail/accountNo"));
				}
				if (ticket.getString("voucherNo") != null)
					updateRefundData.setString("statement1parameter22",
							ticket.getString("voucherNo"));
				
				if(ticket.getString("refundDetail/product")!=null){
					updateRefundData.setString("statement1parameter23",ticket.getString("refundDetail/product"));
				}
				if(ticket.getString("refundDetail/counterPartyName")!=null){
					updateRefundData.setString("statement1parameter24",ticket.getString("refundDetail/counterPartyName"));
				}
				if(ticket.getString("refundDetail/orderPartyName")!=null){
					updateRefundData.setString("statement1parameter25",ticket.getString("refundDetail/orderPartyName"));
				}
				
				updateRefundData.setString("statement1parameter19",
						ticket.getString("actionBy"));
				updateRefundData.setDate("statement1parameter20", formatDate(new Date()));
				updateRefundDataBG.setDataObject("UpdateRefundDetails", updateRefundData);
				try {
					locateService_DBInterfacePartner().invoke(
							"executeUpdateRefundDetailsBG", updateRefundDataBG);
				} 	
				catch (ServiceBusinessException sbe)
				{
					exceptionData = (DataObject)sbe.getData();
					rasException.setString("status", "FAILED");
					rasException.setString("error[0]/message", 
							exceptionData.get("message").toString());
					throw new ServiceBusinessException(rasException);
				}
				catch (ServiceRuntimeException sre) {
					throw new ServiceRuntimeException(sre.getMessage().toString());
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		
		}

		ticketDataBG.setDataObject("DboRas_Ticket", ticketData);
		// fire update services;
		DataObject smo = null;
		try {
			String tickeNo = generateTicketSeries(ticket.getInt("ticketID")) ;
			ticketData.setString("ticket_no",tickeNo);
			updateTicketData.setString("statement1parameter55",tickeNo);
			updateTicketDataBG.setDataObject("UpdateTicketDetails", updateTicketData);
			smo = (DataObject) locateService_DBInterfacePartner().invoke(
					"executeUpdateTicketDetailsBG", updateTicketDataBG);
			// calling saveAccountHistory Method to store account history
			if (ticketData.getDataObject("ras_account_detailobj")!=null ) {
				saveAccountHistory(ticketData.getDataObject("ras_account_detailobj"));
			}
			// calling saveTicketHistory Method to store ticket History Details
			saveTicketHistory(ticketData);
			// call get service to retrieve the updated data from the server
			ticketDataBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/ticketdetailsbg",
							"TicketDetailsBG");
			ticketData = null;
			ticketData = getBOFactory()
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
			ticketData.setDouble("ticket_id", ticket.getDouble("ticketID"));
			ticketData.setDouble("parameter1", ticket.getDouble("ticketID"));
			policyData.setDouble("parameter1", ticket.getDouble("ticketID"));
			refundData.setDouble("parameter1", ticket.getDouble("ticketID"));
			accountData.setDouble("parameter1", ticket.getDouble("ticketID"));
			receiptData.setDouble("parameter1", ticket.getDouble("ticketID"));
			insuredData.setDouble("parameter1", ticket.getDouble("ticketID"));
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
				insuredResult=null;
				System.out.println("Error from fetching insured details in maintain ticket "+e.getMessage());
			}
			
				
				ticketResponseBO = (DataObject) locateService_DBInterfacePartner()
						.invoke("retrieveallTicketDetailsBG", ticketDataBG);
				ticketResult = ticketResponseBO
						.getDataObject("retrieveallTicketDetailsBGOutput/TicketDetails[1]");
				if("CANCELLATION REFUND".equalsIgnoreCase(ticketResponseBO
						.getString("retrieveallTicketDetailsBGOutput/TicketDetails[1]/refund_type")) || 
						"BALANCE REFUND".equalsIgnoreCase(ticketResponseBO
								.getString("retrieveallTicketDetailsBGOutput/TicketDetails[1]/refund_type")) ||
						"CANCELLATION ONLY".equalsIgnoreCase(ticketResponseBO
								.getString("retrieveallTicketDetailsBGOutput/TicketDetails[1]/refund_type")) ||
                                        "NEGATIVE ENDORSEMENT REFUND".equalsIgnoreCase(ticketResponseBO
                                                                                .getString("retrieveallTicketDetailsBGOutput/TicketDetails[1]/refund_type")
						))
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
			throw new ServiceRuntimeException(sre.getMessage().toString(), sre);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
			}
		

	}

	

	/**
	 * Method generated to support implementation of operation
	 * "updateTicketStatus" defined for WSDL port type named "MaintainTicket".
	 * 
	 * Please refer to the WSDL Definition for more information on the type of
	 * input, output and fault(s).
	 * 
	 * @throws ParseException
	 */
	public Boolean updateTicketStatus(String status, String stage,
			String remarks, String comments, String updatedBy, Double ticketId,String instanceId)
					throws ParseException {
		System.out.println("Enter Into updateTicketStatus"+ticketId);
		DataObject ticketDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateticketstatusbg",
						"UpdateTicketStatusBG");
		DataObject ticketData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateticketstatus",
						"UpdateTicketStatus");
		DataObject updateTicketStatusBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateticketdetailstatusbg",
						"UpdateTicketDetailStatusBG");
		DataObject updateTicketStatus = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateticketdetailstatus",
						"UpdateTicketDetailStatus");
		// RASException Object
		DataObject rasException = getBOFactory().create("http://RACASBO",
				"RASException");
		// Object to store Business Exception Data
		DataObject exceptionData = null;
		// set the property for the ticket object
		if (ticketId != 0) {
			// check for ticket_id Exists or not in DataBase
			ticketData.setDouble("statement1parameter8", ticketId);
			updateTicketStatus.setDouble("statement1parameter7", ticketId);
		} else {
			throw new RuntimeException(
					"Ticket update failed: TicketId cannot be null or empty");
		}
		// set properties for ticket object if they are not empty
		if(stage!=null && stage!="")
		{
			ticketData.setString("statement1parameter1", stage);
			updateTicketStatus.setString("statement1parameter1", stage);
		}
		if(status!=null && status!="")
		{
			ticketData.setString("statement1parameter2", status);
			updateTicketStatus.setString("statement1parameter2", status);
		}
		if(remarks!=null && remarks!="")
		{
			ticketData.setString("statement1parameter3", remarks);
			updateTicketStatus.setString("statement1parameter3", remarks);
		}
		if(comments!=null && comments!="")
		{
			ticketData.setString("statement1parameter4", comments);
			updateTicketStatus.setString("statement1parameter4", comments);
		}
		if(instanceId!=null && instanceId!="")
			ticketData.setString("statement1parameter5", instanceId);

		ticketData.setString("statement1parameter6", updatedBy);
		updateTicketStatus.setString("statement1parameter5", updatedBy);
		Date lastModfied = formatDate(new Date());
		ticketData.setDate("statement1parameter7", lastModfied);
		updateTicketStatus.setDate("statement1parameter6", lastModfied);
		ticketDataBG.setDataObject("UpdateTicketStatus", ticketData);
		updateTicketStatusBG.setDataObject("UpdateTicketDetailStatus", updateTicketStatus);
		// run update service;
		DataObject smo = null;
		try {
			if(instanceId!=null && instanceId!="")
			smo = (DataObject) locateService_DBInterfacePartner().invoke(
					"executeUpdateTicketStatusBG", ticketDataBG);
			else
			smo = (DataObject) locateService_DBInterfacePartner().invoke(
					"executeUpdateTicketDetailStatusBG", updateTicketStatusBG);
			DataObject ticket = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticket",
							"DboRas_Ticket");
			if(stage!=null && stage!="")
			ticket.setString("stage", stage);
			if(status!=null && status!="")
			ticket.setString("status", status);
			if(remarks!=null && remarks!="")
			ticket.setString("remarks", remarks);
			if(comments!=null && comments!="")
			ticket.setString("comments", comments);
			if(instanceId!=null && instanceId!="")
			ticket.setString("instance_id", instanceId);
			ticket.setString("last_modified_by", updatedBy);
			ticket.setDate("last_modified_on", lastModfied);
			ticket.setDouble("ticket_id", ticketId);
			saveTicketHistory(ticket);
			return true;
		} catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();
			rasException.setString("status", "FAILED");
			rasException.setString("error[0]/message",
					exceptionData.get("message").toString());
			throw new ServiceBusinessException(rasException);

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage().toString());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	/**
	 * 
	 * Method generated to support the implementation generate ticket series method
	 * This method is used to generate a ticket number based on ticket series provided as input to it.
	 * @param format
	 * @param lastUpdatedValue
	 * @return
	 */
	public synchronized String generateTicketSeries(int ticketNo){
		System.out.println("Enter Into generateTicketSeries"+ticketNo);
		
		DataObject appConfigBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/appconfigbg",
						"AppConfigBG");
		DataObject appConfig = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/appconfig",
						"AppConfig");
		appConfig.setString("parameter1", "TICKET_SERIES");
		appConfigBG.setDataObject("AppConfig", appConfig);
		
		 DataObject series = (DataObject) locateService_DBInterfacePartner().invoke(
				"retrieveallAppConfigBG", appConfigBG);
		 
		 String format = series.getString("retrieveallAppConfigBGOutput/AppConfig[1]/name");
		
		 System.out.println("format "+format);
		 
		/*   String prefix = format.substring(0,format.indexOf("X"));
		   String suffix = format.substring(format.indexOf("X"));*/
		   
		  // int reqLen = suffix.length();
		   String ticketSeries = format+ticketNo;
		   System.out.println("Ticket Series "+ticketSeries);
		   
		   // updating current ticket ticket number in app config table
		 
			DataObject updateAppConfigBG = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateappconfigbg",
							"UpdateAppConfigBG");

			DataObject updateAppConfigInput = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateappconfig",
							"UpdateAppConfig");

			updateAppConfigInput.setString("statement1parameter2", "TICKET_SERIES");
			updateAppConfigInput.setString("statement1parameter1",String.valueOf(ticketNo));
		
			updateAppConfigBG.setDataObject("UpdateAppConfig",
					updateAppConfigInput);

			this.locateService_DBInterfacePartner().invoke(
					"executeUpdateAppConfigBG", updateAppConfigBG);
			
		return ticketSeries;	
		
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
	 * "JDBCImport#upsertDboRas_LobBG(DataObject upsertDboRasLobBGInput)" of
	 * wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_LobBGResponse(Ticket __ticket,
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
	 * "JDBCImport#upsertDboRas_App_ConfigBG(DataObject upsertDboRasAppConfigBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_App_ConfigBGResponse(Ticket __ticket,
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
	 * "JDBCImport#upsertDboRas_Cancellation_OptionBG(DataObject upsertDboRasCancellationOptionBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_Cancellation_OptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
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
	 * "JDBCImport#upsertDboRas_Cancellation_ReasonBG(DataObject upsertDboRasCancellationReasonBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_Cancellation_ReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
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
	 * "JDBCImport#upsertDboRas_Document_MasterBG(DataObject upsertDboRasDocumentMasterBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_Document_MasterBGResponse(Ticket __ticket,
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
	 * "JDBCImport#upsertDboRas_JobsBG(DataObject upsertDboRasJobsBGInput)" of
	 * wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_JobsBGResponse(Ticket __ticket,
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
	 * "JDBCImport#upsertDboRas_TicketBG(DataObject upsertDboRasTicketBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_TicketBGResponse(Ticket __ticket,
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
	 * "JDBCImport#upsertDboRas_Account_DetailBG(DataObject upsertDboRasAccountDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_Account_DetailBGResponse(Ticket __ticket,
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
	 * "JDBCImport#upsertDboRas_Policy_DetailBG(DataObject upsertDboRasPolicyDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_Policy_DetailBGResponse(Ticket __ticket,
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
	 * "JDBCImport#upsertDboRas_Receipt_DetailBG(DataObject upsertDboRasReceiptDetailBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_Receipt_DetailBGResponse(Ticket __ticket,
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
	 * "JDBCImport#createDboRas_RefundBG(DataObject createDboRasRefundBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_RefundBG(DataObject updateDboRasRefundBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_RefundBG(DataObject updateallDboRasRefundBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_RefundBG(DataObject retrieveDboRasRefundBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_RefundBG(DataObject retrieveallDboRasRefundBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_RefundBG(DataObject applychangesDboRasRefundBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_RefundBG(DataObject existsDboRasRefundBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#upsertDboRas_RefundBG(DataObject upsertDboRasRefundBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_RefundBG(DataObject batchcreateDboRasRefundBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_RefundBG(DataObject batchupdateDboRasRefundBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_RefundBGResponse(Ticket __ticket,
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
	 * "JDBCImport#upsertDboRas_ApproverBG(DataObject upsertDboRasApproverBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_ApproverBGResponse(Ticket __ticket,
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
	 * "JDBCImport#upsertDboRas_Account_Detail_HistBG(DataObject upsertDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_Account_Detail_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
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
	 * "JDBCImport#upsertDboRas_Ticket_HistBG(DataObject upsertDboRasTicketHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_Ticket_HistBGResponse(Ticket __ticket,
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
	 * "JDBCImport#upsertDboRas_Exception_HistBG(DataObject upsertDboRasExceptionHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_Exception_HistBG(DataObject batchcreateDboRasExceptionHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateRefundRas_Exception_HistBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
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
	 * "JDBCImport#createDboRas_Refund_HistBG(DataObject createDboRasRefundHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onCreateDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateDboRas_Refund_HistBG(DataObject updateDboRasRefundHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#updateallDboRas_Refund_HistBG(DataObject updateallDboRasRefundHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpdateallDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveDboRas_Refund_HistBG(DataObject retrieveDboRasRefundHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#retrieveallDboRas_Refund_HistBG(DataObject retrieveallDboRasRefundHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onRetrieveallDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#applychangesDboRas_Refund_HistBG(DataObject applychangesDboRasRefundHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onApplychangesDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#existsDboRas_Refund_HistBG(DataObject existsDboRasRefundHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onExistsDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#upsertDboRas_Refund_HistBG(DataObject upsertDboRasRefundHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchcreateDboRas_Refund_HistBG(DataObject batchcreateDboRasRefundHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchcreateDboRas_Refund_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		// TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback for
	 * the operation
	 * "JDBCImport#batchupdateDboRas_Refund_HistBG(DataObject batchupdateDboRasRefundHistBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onBatchupdateDboRas_Refund_HistBGResponse(Ticket __ticket,
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
	 * "JDBCImport#upsertDboRas_InsuredBG(DataObject upsertDboRasInsuredBGInput)"
	 * of wsdl interface "JDBCImport"
	 */
	public void onUpsertDboRas_InsuredBGResponse(Ticket __ticket,
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

}