
public class getUserName {

	/**
	 * Sample method that can be called from a Mapping Custom Java transform.
	 * The content of this method provides the implementation for the Custom Java transform.
	 */
	public static String sampleTransform() {
		return com.ibm.ws.security.core.SecurityContext.getName();
	}

}
