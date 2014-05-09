package si.matjazcerkvenik.ftpsync;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;

public class FtpTransfer {

//	public static final String FTP_URL = "192.168.1.101/home/matjaz/test"; // ftp.xyz.com
//	public static final String FTP_USERNAME = "matjaz"; // ftp_user_01
//	public static final String FTP_PASSWORD = "object00"; // secret_123
	
	public static int count = 1;

	public static void upload(String filePath) {

//		String filePath = "/Users/matjaz/Dropbox/MyWorkspace/FtpSync/test.txt";
		
		String fileName = parseRelativeFilePath(filePath);

//		System.out.println("File Path: " + filePath);
//		System.out.println("File Name: " + fileName);
		
		if (Util.exclude(filePath)) {
			System.out.println("skip:   " + filePath);
			return;
		}

		
		try {
			FileSystemManager manager = VFS.getManager();
			FileSystemOptions opts = new FileSystemOptions();
			
			FtpFileSystemConfigBuilder.getInstance().setPassiveMode(opts, true);
			
			// ftp://username:password@ftp.url/afolder/
			String ftpUri = "ftp://" + Util.FTP_USERNAME + ":" + Util.FTP_PASSWORD
					+ "@" + Util.FTP_HOSTNAME + Util.SYNC_DESTINATION + "/" + fileName;
			String ftpUri2 = "ftp://" + Util.FTP_USERNAME + ":***"
					+ "@" + Util.FTP_HOSTNAME + Util.SYNC_DESTINATION + "/" + fileName;

			System.out.println("[" + count++ + "] upload: " + ftpUri2);
			FileObject remoteFileObject = manager.resolveFile(ftpUri, opts);

			FileObject localFileObject = manager.resolveFile(filePath);
//			System.out.println("copying");
			remoteFileObject.copyFrom(localFileObject, Selectors.SELECT_SELF);
//			System.out.println("upload finished");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void delete(String filePath) {
		
		String fileName = parseRelativeFilePath(filePath);

//		System.out.println("File Path: " + filePath);
//		System.out.println("File Name: " + fileName);
		
		if (Util.exclude(filePath)) {
			System.out.println("skip:   " + filePath);
			return;
		}
		
		try {
			FileSystemManager manager = VFS.getManager();
			FileSystemOptions opts = new FileSystemOptions();
			
			FtpFileSystemConfigBuilder.getInstance().setPassiveMode(opts, true);
			
			// ftp://username:password@ftp.url/afolder/
			String ftpUri = "ftp://" + Util.FTP_USERNAME + ":" + Util.FTP_PASSWORD
					+ "@" + Util.FTP_HOSTNAME + Util.SYNC_DESTINATION + "/" + fileName;
			String ftpUri2 = "ftp://" + Util.FTP_USERNAME + ":***"
					+ "@" + Util.FTP_HOSTNAME + Util.SYNC_DESTINATION + "/" + fileName;

			System.out.println("[" + count++ + "] delete: " + ftpUri2);
			FileObject remoteFileObject = manager.resolveFile(ftpUri, opts);
			
			remoteFileObject.delete(Selectors.SELECT_SELF);

//			System.out.println("delete finished");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void initialUpload() {
		
		try {
			FileSystemManager manager = VFS.getManager();
			FileSystemOptions opts = new FileSystemOptions();
			
			FtpFileSystemConfigBuilder.getInstance().setPassiveMode(opts, true);
			
			// ftp://username:password@ftp.url/afolder/
			String ftpUri = "ftp://" + Util.FTP_USERNAME + ":" + Util.FTP_PASSWORD
					+ "@" + Util.FTP_HOSTNAME + Util.SYNC_DESTINATION;
			String ftpUri2 = "ftp://" + Util.FTP_USERNAME + ":***"
					+ "@" + Util.FTP_HOSTNAME + Util.SYNC_DESTINATION;

			System.out.println("initialUpload: " + ftpUri2);
			FileObject remoteFileObject = manager.resolveFile(ftpUri, opts);

			FileObject localFileObject = manager.resolveFile(Util.SYNC_SOURCE);
			System.out.println("copying");
			remoteFileObject.copyFrom(localFileObject, Selectors.SELECT_SELF_AND_CHILDREN);
			System.out.println("initialUpload finished");
		} catch (FileSystemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static String parseRelativeFilePath(String filePath) {
		
//		return filePath.substring(filePath.lastIndexOf("/")+1);
		return filePath.substring(Util.SYNC_SOURCE.length() + 8);
		
	}

}
