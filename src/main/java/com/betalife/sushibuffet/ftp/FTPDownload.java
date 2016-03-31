package com.betalife.sushibuffet.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FTPDownload {
	/**
	 * <pre>
	 * ftp [options] <hostname> <username> <password> [<remote file> [<local file>]]
	 * Default behavior is to download a file and use ASCII transfer mode.
	 * -a - use local active mode (default is local passive)
	 * -A - anonymous login
	 * -b - use binary transfer mode
	 * -c cmd - issue arbitrary command (remote is used as a parameter if provided) 
	 * -d - list directory details using MLSD (remote is used as the pathname if provided)
	 * -e - use EPSV with IPv4 (default false)
	 * -f - issue FEAT command (remote and local files are ignored)
	 * -h - list hidden files (applies to -l and -n only)
	 * -k secs - use keep-alive timer (setControlKeepAliveTimeout)
	 * -l - list files using LIST (remote is used as the pathname if provided)
	 * -L - use lenient future dates (server dates may be up to 1 day into future)
	 * -n - list file names using NLST (remote is used as the pathname if provided)
	 * -p true|false|protocol[,true|false] - use FTPSClient with the specified protocol and/or isImplicit setting
	 * -s - store file on server (upload)
	 * -t - list file details using MLST (remote is used as the pathname if provided)
	 * -w msec - wait time for keep-alive reply (setControlKeepAliveReplyTimeout)
	 * -T  all|valid|none - use one of the built-in TrustManager implementations (none = JVM default)
	 * -PrH server[:port] - HTTP Proxy host and optional port[80] 
	 * -PrU user - HTTP Proxy server username -PrP password - HTTP Proxy server password
	 * -# - add hash display during transfers;
	 * </pre>
	 */
	private final Log log = LogFactory.getLog(getClass());

	private FTPClient ftp;

	@Value("${ftp.server}")
	private String server;
	@Value("${ftp.port}")
	private int port;
	@Value("${ftp.username}")
	private String username;
	@Value("${ftp.password}")
	private String password;
	@Value("${ftp.remote}")
	private String remote;
	@Value("${ftp.local}")
	private String local;
	@Value("${ftp.production}")
	private boolean production;

	public FTPDownload() {
		init();
	}

	@PostConstruct
	public void init() {
		ftp = new FTPClient();

		ftp.setCopyStreamListener(createListener());

		// suppress login details
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(new LogOutputStream(log)), true));
	}

	public void download() {

		try {

			ftp.connect(server, port);
			log.debug("Connected to " + server + " on " + (port > 0 ? port : ftp.getDefaultPort()));

			// After connection attempt, you should check the reply code to
			// verify success.
			int reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.error("FTP server refused connection.");
				return;
			}
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					// do nothing
				}
			}
			log.error("Could not connect to server.");
			log.debug(e, e);
			return;
		}

		try {
			if (!ftp.login(username, password)) {
				ftp.logout();
				return;
			}

			log.debug("Remote system is " + ftp.getSystemType());
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);

			download(remote, local);

			ftp.noop(); // check that control connection is working OK

			ftp.logout();
			log.debug("ftp logout");
		} catch (FTPConnectionClosedException e) {
			log.error("Server closed connection.");
			log.debug(e, e);
		} catch (IOException e) {
			log.error(e.getMessage());
			log.debug(e, e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					// do nothing
				}
			}
		}

		return;
	} // end main

	private void download(String remote, String where) throws IOException {
		FTPFile[] listFiles = ftp.listFiles(remote);
		log.debug("downloading from " + remote + ", [size: " + listFiles == null ? 0 : listFiles.length + "]");
		if (!ArrayUtils.isEmpty(listFiles)) {
			File localFolder = new File(where);
			if (!localFolder.exists()) {
				localFolder.mkdirs();
			}
			for (int i = 0; i < listFiles.length; i++) {
				FTPFile ftpFile = listFiles[i];
				if (".".equals(ftpFile.getName()) || "..".equals(ftpFile.getName())) {
					continue;
				}
				String name = ftpFile.getName();
				if (ftpFile.isDirectory()) {
					download(fileDirectory(remote, name, "/"), fileDirectory(where, name, File.separator));
					continue;
				}

				OutputStream output = new FileOutputStream(fileDirectory(where, name, File.separator));
				ftp.retrieveFile(fileDirectory(remote, name, "/"), output);
				IOUtils.closeQuietly(output);

				if (production) {
					ftp.deleteFile(fileDirectory(remote, name, "/"));
				}
			}
		}

	}

	private String fileDirectory(String path, String name, String separator) {
		if (path.endsWith(separator)) {
			return path + name;
		} else {
			return path + separator + name;
		}
	}

	private CopyStreamListener createListener() {
		return new CopyStreamListener() {
			private long megsTotal = 0;

			public void bytesTransferred(CopyStreamEvent event) {
				bytesTransferred(event.getTotalBytesTransferred(), event.getBytesTransferred(), event.getStreamSize());
			}

			public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
				long megs = totalBytesTransferred / 1000000;
				for (long l = megsTotal; l < megs; l++) {
					log.error("#");
				}
				megsTotal = megs;
			}
		};
	}

	public static void main(String[] args) {
		String local = "C:\\Users\\pc\\Desktop\\aaa\\";
		FTPDownload download = new FTPDownload();
		download.init();
		download.download();
	}

}
