package si.matjazcerkvenik.ftpsync;

import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;

public class WatchThread implements FileListener {
	
//	public static String watchedDirectory = "/Users/matjaz/Dropbox/MyWorkspace/FtpSync";
	
	private FileSystemManager manager;
	private FileObject file;

	private DefaultFileMonitor fm = new DefaultFileMonitor(this);
		
	public void start() {
		
		try {
			manager = VFS.getManager();
			file = manager.resolveFile(Util.SYNC_SOURCE);
		} catch (FileSystemException e) {
			e.printStackTrace();
		}
		
		fm.setDelay(Util.getSyncInterval() * 1000);
		fm.setRecursive(true);
		fm.addFile(file); 
		
		fm.start();
		
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void fileChanged(FileChangeEvent arg0) throws Exception {
		String file = arg0.getFile().getURL().toString();
//		System.out.println("file changed: " + file);
		FtpTransfer.upload(file);
	}

	@Override
	public void fileCreated(FileChangeEvent arg0) throws Exception {
		String file = arg0.getFile().getURL().toString();
//		System.out.println("file created: " + file);
		FtpTransfer.upload(file);
	}

	@Override
	public void fileDeleted(FileChangeEvent arg0) throws Exception {
		String file = arg0.getFile().getURL().toString();
//		System.out.println("file deleted: " + file);
		FtpTransfer.delete(file);
	}
	
	
}
