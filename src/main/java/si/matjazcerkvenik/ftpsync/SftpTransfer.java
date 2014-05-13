package si.matjazcerkvenik.ftpsync;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

/**
 * SFTP client
 * 
 * @author cerkvenik
 *
 */
public class SftpTransfer {

	public static final String FTP_URL = "192.168.1.101/home/matjaz"; // ftp.xyz.com
	public static final String FTP_USERNAME = "matjaz"; // ftp_user_01
	public static final String FTP_PASSWORD = "object00"; // secret_123

	public void upload() {

		String fileName = "test_" + System.currentTimeMillis() + ".txt";
		String filePath = System.getProperty("java.io.tmpdir") + fileName;

		System.out.println("File Path: " + filePath);

		StandardFileSystemManager manager = new StandardFileSystemManager();
		try {
			// if uploaded file not exist, create a new one
			BufferedWriter writerAnsi = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(filePath),
							"UTF-8"));
			writerAnsi.write("Sample text");
			writerAnsi.flush();
			writerAnsi.close();

			// e.g:
			// sftp://your-ftp-username":your-ftp-password@your-ftp-url/afolder/
			String sftpUri = "sftp://" + FTP_USERNAME + ":" + FTP_PASSWORD
					+ "@" + FTP_URL;

			FileSystemOptions opts = new FileSystemOptions();
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(
					opts, "no");

			manager.init();

			FileObject fileObject = manager.resolveFile(sftpUri + "/"
					+ fileName, opts);

			FileObject localFileObject = manager.resolveFile(filePath);

			fileObject.copyFrom(localFileObject, Selectors.SELECT_SELF);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			manager.close();
		}

	}

}
