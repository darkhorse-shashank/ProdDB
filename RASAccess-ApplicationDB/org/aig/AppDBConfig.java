package org.aig;
import com.ibm.websphere.sca.Service;
import com.ibm.websphere.sca.ServiceManager;

public class AppDBConfig {

	private static Service _JDBCImportPartner = null;

	public static Service locateService_JDBCImportPartner() {
		if (_JDBCImportPartner == null) {
			_JDBCImportPartner = (Service) ServiceManager.INSTANCE
					.locateService("JDBCImportPartner");
		}

		return _JDBCImportPartner;
	}
	
}
