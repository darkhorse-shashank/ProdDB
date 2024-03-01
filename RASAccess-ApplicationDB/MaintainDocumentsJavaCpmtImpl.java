import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.websphere.bo.BOFactory;
import com.ibm.websphere.sca.Service;
import com.ibm.websphere.sca.ServiceBusinessException;
import com.ibm.websphere.sca.ServiceRuntimeException;
import com.ibm.websphere.sca.Ticket;
import commonj.sdo.DataObject;
import com.ibm.websphere.sca.ServiceManager;

public class MaintainDocumentsJavaCpmtImpl {
	/**
	 * Default constructor.
	 */
	public MaintainDocumentsJavaCpmtImpl() {
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
	 * Method generated to support implementation of operation "updateDocument" defined for WSDL port type 
	 * named "MaintainDocument".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a parameter 
	 * type conveys that it is a complex type. Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 */
	public BOFactory getBOFactory() {
		return (BOFactory) ServiceManager.INSTANCE
				.locateService("com/ibm/websphere/bo/BOFactory");
	}

	// formatDate Method to format date into DB Accepted mode
	public Timestamp formatDate(Date date) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss a");
		String newDate = dateFormat.format(date);
		Date parsedDate = dateFormat.parse(newDate);
		Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		return timestamp;
	}

	// we will update only Document Details But not DMS Details here
	public Boolean updateDocument(DataObject document) throws Exception {
		DataObject documentsBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticket_documentsbg",
						"DboRas_Ticket_DocumentsBG");
		DataObject documents = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticket_documents",
						"DboRas_Ticket_Documents");
		DataObject exceptionData = null;
		DataObject rasException = getBOFactory().create("http://RACASBO",
				"RASException");
		if (document.getDouble("docId") == 0) {
			throw new RuntimeException("Document Id Cannot be Empty or 0");
		} else {
			documents.setDouble("slno", document.getDouble("docId"));
			documents.setString("name", document.getString("docName"));
			documents.setString("path", document.getString("docPath"));
			documents.setBoolean("is_submitted",
					document.getBoolean("isSubmitted"));
			documents.setInt("dms_flag", document.getInt("dmsFlag"));
			documents.setString("dms_uploaded_by", document.getString("dmsUploadedBy"));
			documents.setString("dms_remarks", document.getString("dmsRemarks"));  
			documents.setDate("dms_uploaded_date", formatDate(new Date()));
			
			documentsBG.setDataObject("DboRas_Ticket_Documents", documents);
			try {
				DataObject responseBO = (DataObject) locateService_DBInterfacePartner()
						.invoke("updateDboRas_Ticket_DocumentsBG", documentsBG);
				return true;
			}

			catch (ServiceBusinessException sbe) {

				exceptionData = (DataObject) sbe.getData();
				rasException.setString("status", "FAILED");
				rasException.setString("error[0]/message",
						exceptionData.get("message").toString());
				throw new ServiceBusinessException(rasException);

			} catch (ServiceRuntimeException sre) {
				throw new ServiceRuntimeException(sre.getMessage());
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}

		}
	}

	/**
	 * Method generated to support implementation of operation "saveDocuments" defined for WSDL port type 
	 * named "MaintainDocument".
	 * 
	 * The presence of commonj.sdo.DataObject as the return type and/or as a parameter 
	 * type conveys that it is a complex type. Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 * @throws ParseException 
	 */
	public Boolean saveDocuments(DataObject documents, String applicationNo)
			throws Exception {
		DataObject documentsListBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticket_documentslistbg",
						"DboRas_Ticket_DocumentsListBG");
		DataObject documentsList = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticket_documentslist",
						"DboRas_Ticket_DocumentsList");
		DataObject rasException = getBOFactory().create("http://RACASBO",
				"RASException");
		DataObject exceptionData = null;

		List docsList = new ArrayList();
		int size = documents.getList(0).size();
		for (int i = 1; i <= size; i++) {

			DataObject documentsDB = getBOFactory()
					.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticket_documents",
							"DboRas_Ticket_Documents");
			documentsDB.setDouble("slno",
					documents.getDouble("document[" + i + "]/docId"));
			documentsDB.setDouble("ticket_id",
					documents.getDouble("document[" + i + "]/ticketId"));
			documentsDB.setString("doc_type",
					documents.getString("document[" + i + "]/docType"));
			documentsDB.setString("name",
					documents.getString("document[" + i + "]/docName"));
			documentsDB.setString("path",
					documents.getString("document[" + i + "]/docPath"));
			documentsDB.setBoolean("is_submitted",
					documents.getBoolean("document[" + i + "]/isSubmitted"));
			documentsDB.setString("dms_flag",
					documents.getString("document[" + i + "]/dmsFlag"));
			documentsDB.setString("dms_remarks",
					documents.getString("document[" + i + "]/dmsRemarks")); 
			documentsDB.setString("dms_uploaded_by",
					documents.getString("document[" + i + "]/dmsUploadedBy"));
			documentsDB.setString("is_mandatory",
					documents.getString("document[" + i + "]/isMandatory"));

			documentsDB.setString("application_no", applicationNo);

			documentsDB.setDate(
					"dms_uploaded_date",
					formatDate(documents.getDate("document[" + i
							+ "]/dmsUploadedDate")));
			docsList.add(documentsDB);
		}

		documentsList.setList("businessobjects", docsList);
		documentsListBG.setDataObject("DboRas_Ticket_DocumentsList",
				documentsList);
		try {
			DataObject response = (DataObject) locateService_DBInterfacePartner()
					.invoke("batchcreateDboRas_Ticket_DocumentsBG",
							documentsListBG);
			return true;
		}

		catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();
			rasException.setString("status", "FAILED");
			rasException.setString("error[0]/message",
					exceptionData.get("message").toString());
			throw new ServiceBusinessException(rasException);

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	/**
	 * Method generated to support implementation of operation "writeDataFromOmni" defined for WSDL port type 
	 * named "MaintainDocumentAccess2".
	 * 
	 * Please refer to the WSDL Definition for more information 
	 * on the type of input, output and fault(s).
	 * @throws Exception 
	 */
	public Boolean writeDataFromOmni(String name, String path, Double docId)
			throws Exception {
		DataObject documentsBG = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticket_documentsbg",
						"DboRas_Ticket_DocumentsBG");
		DataObject documents = getBOFactory()
				.create("http://www.ibm.com/xmlns/prod/websphere/j2ca/jdbc/dboras_ticket_documents",
						"DboRas_Ticket_Documents");
		DataObject rasException = getBOFactory().create("http://RACASBO",
				"RASException");
		DataObject exceptionData = null;
		documents.setDouble("slno", docId);
		documents.setString("name", name);
		documents.setString("path", path);
		documents.setBoolean("is_submitted", true);
		documents.setInt("dms_flag", 1);
		documents.setString("dms_uploaded_by", "Omni");
		documents.setDate("dms_uploaded_date", formatDate(new Date()));
		documentsBG.setDataObject("DboRas_Ticket_Documents", documents);
		try {
			DataObject response = (DataObject) locateService_DBInterfacePartner()
					.invoke("updateDboRas_Ticket_DocumentsBG", documentsBG);
			return true;
		}

		catch (ServiceBusinessException sbe) {

			exceptionData = (DataObject) sbe.getData();
			rasException.setString("status", "FAILED");
			rasException.setString("error[0]/message",
					exceptionData.get("message").toString());
			throw new ServiceBusinessException(rasException);

		} catch (ServiceRuntimeException sre) {
			throw new ServiceRuntimeException(sre.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
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
	 * for the operation "DBInterface#deleteDboRas_TicketBG(DataObject deleteDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_TicketBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_TicketBG(DataObject deleteallDboRasTicketBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_TicketBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_Account_DetailBG(DataObject deleteDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_Account_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_Account_DetailBG(DataObject deleteallDboRasAccountDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_Account_DetailBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_Policy_DetailBG(DataObject deleteDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_Policy_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_Policy_DetailBG(DataObject deleteallDboRasPolicyDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_Policy_DetailBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_RefundBG(DataObject deleteDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_RefundBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_RefundBG(DataObject deleteallDboRasRefundBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_RefundBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_Receipt_DetailBG(DataObject deleteDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_Receipt_DetailBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_Receipt_DetailBG(DataObject deleteallDboRasReceiptDetailBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_Receipt_DetailBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_InsuredBG(DataObject deleteDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_InsuredBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_InsuredBG(DataObject deleteallDboRasInsuredBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_InsuredBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_Account_Detail_HistBG(DataObject deleteDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_Account_Detail_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_Account_Detail_HistBG(DataObject deleteallDboRasAccountDetailHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_Account_Detail_HistBGResponse(
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
	 * for the operation "DBInterface#deleteDboRas_App_ConfigBG(DataObject deleteDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_App_ConfigBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_App_ConfigBG(DataObject deleteallDboRasAppConfigBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_App_ConfigBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_ApproverBG(DataObject deleteDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_ApproverBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_ApproverBG(DataObject deleteallDboRasApproverBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_ApproverBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_Cancellation_OptionBG(DataObject deleteDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_Cancellation_OptionBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_Cancellation_OptionBG(DataObject deleteallDboRasCancellationOptionBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_Cancellation_OptionBGResponse(
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
	 * for the operation "DBInterface#deleteDboRas_Cancellation_ReasonBG(DataObject deleteDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_Cancellation_ReasonBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_Cancellation_ReasonBG(DataObject deleteallDboRasCancellationReasonBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_Cancellation_ReasonBGResponse(
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
	 * for the operation "DBInterface#deleteDboRas_Document_MasterBG(DataObject deleteDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_Document_MasterBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_Document_MasterBG(DataObject deleteallDboRasDocumentMasterBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_Document_MasterBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_Exception_HistBG(DataObject deleteDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_Exception_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_Exception_HistBG(DataObject deleteallDboRasExceptionHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_Exception_HistBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_JobsBG(DataObject deleteDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_JobsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_JobsBG(DataObject deleteallDboRasJobsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_JobsBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_LobBG(DataObject deleteDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_LobBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_LobBG(DataObject deleteallDboRasLobBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_LobBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_StatusBG(DataObject deleteDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_StatusBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_StatusBG(DataObject deleteallDboRasStatusBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_StatusBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_Ticket_DocumentsBG(DataObject deleteDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_Ticket_DocumentsBG(DataObject deleteallDboRasTicketDocumentsBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_Ticket_DocumentsBGResponse(Ticket __ticket,
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
	 * for the operation "DBInterface#deleteDboRas_Ticket_HistBG(DataObject deleteDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteDboRas_Ticket_HistBGResponse(Ticket __ticket,
			DataObject returnValue, Exception exception) {
		//TODO Needs to be implemented.
	}

	/**
	 * Method generated to support the async implementation using callback
	 * for the operation "DBInterface#deleteallDboRas_Ticket_HistBG(DataObject deleteallDboRasTicketHistBGInput)"
	 * of wsdl interface "DBInterface"	
	 */
	public void onDeleteallDboRas_Ticket_HistBGResponse(Ticket __ticket,
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

}