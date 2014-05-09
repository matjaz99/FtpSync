package si.matjazcerkvenik.ftpsync;



public class App {
	
	public static String propsFile = "ftpsync.properties";
	
	public static void main(String[] args) {
		
		if (args.length > 0) {
			propsFile = args[0];
		}
		
		Util.loadProperties(propsFile);
		
		System.out.println("Local directory: file://" + Util.SYNC_SOURCE);
		System.out.println("Remote directory: ftp://" + Util.FTP_USERNAME + ":***"
				+ "@" + Util.FTP_HOSTNAME + Util.SYNC_DESTINATION);
		
		// FtpTransfer.initialUpload();
		
		WatchThread w = new WatchThread();
		w.start();
		
		
	}
	
}
