package si.matjazcerkvenik.ftpsync;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {

	private static Properties props = null;

	public static String FTP_HOSTNAME = null;
	public static String FTP_USERNAME = null;
	public static String FTP_PASSWORD = null;
	public static String SYNC_DESTINATION = null;
	public static String SYNC_SOURCE = null;
	public static String SYNC_EXCLUDE = null;

	public static void loadProperties(String propsFile) {
		
		System.out.println("Loading properties: " + propsFile);

		if (props == null) {

			props = new Properties();

			try {

				props.load(new FileInputStream(propsFile));

				FTP_HOSTNAME = props.getProperty("ftp.hostname");
				FTP_USERNAME = props.getProperty("ftp.username");
				FTP_PASSWORD = props.getProperty("ftp.password");
				SYNC_SOURCE = props.getProperty("sync.source");
				SYNC_DESTINATION = props.getProperty("sync.destination");
				SYNC_EXCLUDE = props.getProperty("sync.exclude");

			}

			catch (IOException e) {
				System.err.println("Cannot load properties file: " + propsFile);
				System.exit(0);
			}

		}
	}
	
	public static boolean exclude(String filePath) {
		
		String[] tempTab = SYNC_EXCLUDE.split(",");
		for (int i = 0; i < tempTab.length; i++) {
			if (filePath.endsWith(tempTab[i].trim())) {
				return true;
			}
			if (filePath.contains("/" + tempTab[i].trim())) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public static int getSyncInterval() {
		int i = 5;
		try {
			i = Integer.parseInt(props.getProperty("sync.interval"));
		} catch (NumberFormatException e) {
			i = 5;
		}
		return i;
	}

}
