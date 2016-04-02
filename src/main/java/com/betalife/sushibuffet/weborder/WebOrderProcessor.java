package com.betalife.sushibuffet.weborder;

import java.io.File;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.betalife.sushibuffet.manager.CustomerManager;

@Service
public class WebOrderProcessor {
	private final Log log = LogFactory.getLog(getClass());
	@Value("${ftp.local}")
	private String local;

	@Value("${weborder.done}")
	private String done;

	@Autowired
	private CustomerManager customerManager;

	synchronized public void process() {
		process(local, done);
	}

	private void process(String local, String moveTo) {
		File root = new File(local);
		File[] listFiles = root.listFiles();
		log.debug("process from " + local + ", [size: " + listFiles == null ? 0 : listFiles.length + "]");
		if (!ArrayUtils.isEmpty(listFiles)) {

			for (int i = 0; i < listFiles.length; i++) {
				File file = listFiles[i];
				if (".".equals(file.getName()) || "..".equals(file.getName())) {
					continue;
				}
				String name = file.getName();
				if (file.isDirectory()) {
					process(fileDirectory(local, name), fileDirectory(moveTo, name));
					continue;
				}

				try {
					customerManager.addWebOrders(file, moveTo);
				} catch (Exception e) {
					log.debug(e);
					log.error("web order file can't be processed: " + file.getAbsolutePath() + ".");
				}
			}
		}

	}

	private String fileDirectory(String path, String name) {
		if (path.endsWith(File.separator)) {
			return path + name;
		} else {
			return path + File.separator + name;
		}
	}

}
