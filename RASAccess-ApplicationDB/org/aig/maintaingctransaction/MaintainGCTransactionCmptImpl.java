package org.aig.maintaingctransaction;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.websphere.bo.BOFactory;
import com.ibm.websphere.sca.Service;
import com.ibm.websphere.sca.ServiceBusinessException;
import com.ibm.websphere.sca.ServiceRuntimeException;
import com.ibm.websphere.sca.Ticket;
import commonj.sdo.DataObject;
import com.ibm.websphere.sca.ServiceManager;

public class MaintainGCTransactionCmptImpl {
	/**
	 * Default constructor.
	 */
	public MaintainGCTransactionCmptImpl() {
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
	// formatDate Method to format date into DB Accepted mode
	public Timestamp formatDate(Date date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss a");
		String newDate = dateFormat.format(date);
		Date parsedDate = dateFormat.parse(newDate);
		Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		return timestamp;
	}
	/**
	 * Method generated to support implementation of operation "saveGCTransaction" defined for WSDL port type 
	 * named "MaintainGCTransaction".
	 * 
	 * Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public Integer saveGCTransaction(String transactionName, String refundType,
			String rasTicketNo, String createdBy, String callType) {
		//TODO Needs to be implemented.
		Integer nextValue=0;
		DataObject gcTransactionIdBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/gctransactionidbg",
						"GCTransactionIdBG");
		DataObject gcTransactionId = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/gctransactionid",
						"GCTransactionId");
		DataObject gcTransactionDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_gc_transactionbg",
						"DboRas_Gc_TransactionBG");
		DataObject gcTransactionData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_gc_transaction",
						"DboRas_Gc_Transaction");
		
		DataObject exceptionData = null;

		try
		{
			
		gcTransactionIdBG.setDataObject("GCTransactionId", gcTransactionId);
		
		DataObject gcTransactionSeries = (DataObject) locateService_DBInterfacePartner().invoke(
				"retrieveallGCTransactionIdBG", gcTransactionIdBG);
		 
		nextValue = 
				gcTransactionSeries.getInt("retrieveallGCTransactionIdBGOutput/GCTransactionId/next_value");
		
		DataObject updateAppConfigBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateappconfigbg",
						"UpdateAppConfigBG");

		DataObject updateAppConfigInput = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updateappconfig",
						"UpdateAppConfig");

		updateAppConfigInput.setString("statement1parameter2", "GC TRANSACTION SEQUENCE");
		updateAppConfigInput.setString("statement1parameter1",String.valueOf(nextValue));
	
		updateAppConfigBG.setDataObject("UpdateAppConfig",
				updateAppConfigInput);

		this.locateService_DBInterfacePartner().invoke(
				"executeUpdateAppConfigBG", updateAppConfigBG);
		gcTransactionData.setInt("transaction_id",
				nextValue);
		gcTransactionData.setString("transaction_name",
				transactionName);
		gcTransactionData.setString("call_type",
				callType);
		gcTransactionData.setString("refund_type",
				refundType);
		gcTransactionData.setString("ras_ticket_no",
				rasTicketNo);
		gcTransactionData.setString("created_by",
				createdBy);
		gcTransactionData.setDate("created_on",
				formatDate(new Date()));
		gcTransactionDataBG.setDataObject("DboRas_Gc_Transaction", gcTransactionData);
		this.locateService_DBInterfacePartner().invoke("createDboRas_Gc_TransactionBG", gcTransactionDataBG);
		Double rasTicketId = Double.parseDouble(rasTicketNo.split("-")[1]);
		return nextValue;
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

	/**
	 * Method generated to support implementation of operation "getTicketId" defined for WSDL port type 
	 * named "MaintainGCTransaction".
	 * 
	 * Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public String getTicketId(Integer transactionId) {
		//TODO Needs to be implemented.
		DataObject gcTransactionDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/gctransactiondetailsbg",
						"GCTransactionDetailsBG");
		DataObject gcTransactionData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/gctransactiondetails",
						"GCTransactionDetails");
		DataObject exceptionData = null;
		try
		{
			
		
		gcTransactionData.setInt("parameter1",
				transactionId);
		gcTransactionDataBG.setDataObject("GCTransactionDetails", gcTransactionData);
		DataObject responseBO = (DataObject) locateService_DBInterfacePartner()
				.invoke("retrieveallGCTransactionDetailsBG", gcTransactionDataBG);
		String rasTicketId = responseBO.getString("retrieveallGCTransactionDetailsBGOutput/GCTransactionDetails/ras_ticket_no").split("-")[1];
		return rasTicketId;
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

	/**
	 * Method generated to support implementation of operation "updateGCTransaction" defined for WSDL port type 
	 * named "MaintainGCTransaction".
	 * 
	 * Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public String updateGCTransaction(Integer transactionId, String callType) {
		//TODO Needs to be implemented.
	if(transactionId!=0)
	{
		DataObject updateGCTransactionDataBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updategctransactiondetailsbg",
						"UpdateGCTransactionDetailsBG");

		DataObject updateGCgcTransactionData = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/updategctransactiondetails",
						"UpdateGCTransactionDetails");
		
		DataObject exceptionData = null;
		try
		{
		
		updateGCgcTransactionData.setInt("statement1parameter10",transactionId);
		updateGCgcTransactionData.setString("statement1parameter1",callType);
		updateGCgcTransactionData.setString("statement1parameter2","");
		updateGCgcTransactionData.setString("statement1parameter3","");
		updateGCgcTransactionData.setString("statement1parameter4","");
		updateGCgcTransactionData.setString("statement1parameter5","");
		updateGCgcTransactionData.setString("statement1parameter6","");
		updateGCgcTransactionData.setDate("statement1parameter7",null);
		updateGCgcTransactionData.setDate("statement1parameter8",null);
		updateGCgcTransactionData.setDate("statement1parameter9",formatDate(new Date()));
	
		updateGCTransactionDataBG.setDataObject(
				"UpdateGCTransactionDetails",
				updateGCgcTransactionData);
		this.locateService_DBInterfacePartner().invoke(
				"executeUpdateGCTransactionDetailsBG", updateGCTransactionDataBG);
		return "SUCCESS";
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
	else
		return "FAILED";
		
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
	 * for the operation "DBInterface#applychangesDboRas_Error_Code_MasterBG(DataObject applychangesDboRasErrorCodeMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Error_Code_MasterBGResponse(
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
	 * for the operation "DBInterface#applychangesDboRas_Gc_TransactionBG(DataObject applychangesDboRasGcTransactionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Gc_TransactionBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#applychangesDboRas_Scheduler_StatusBG(DataObject applychangesDboRasSchedulerStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onApplychangesDboRas_Scheduler_StatusBGResponse(
			Ticket __ticket, DataObject returnValue, Exception exception) {
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

}