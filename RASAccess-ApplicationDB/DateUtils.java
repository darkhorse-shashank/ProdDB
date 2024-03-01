import java.sql.Timestamp;
import java.text.*;
import java.util.Date;
public class DateUtils {

	/**
	 * Sample method that can be called from a Mapping Custom Java transform.
	 * The content of this method provides the implementation for the Custom Java transform.
	 * @throws ParseException 
	 */
	public static Timestamp sampleTransform(Date assignDate) throws ParseException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		String newDate = dateFormat.format(assignDate);
		Date parsedDate = dateFormat.parse(newDate);
		Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		System.out.println(timestamp);
		return timestamp;
	}

}
