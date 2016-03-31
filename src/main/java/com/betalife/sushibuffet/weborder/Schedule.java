package com.betalife.sushibuffet.weborder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.betalife.sushibuffet.ftp.FTPDownload;

@Component
public class Schedule {
	@Autowired
	private FTPDownload ftp;

	@Autowired
	private WebOrderProcessor processor;

	@Scheduled(cron = "${ftp.download.cronExpression}")
	public void downloadWebOrders() {
		ftp.download();
	}

	@Scheduled(cron = "${ftp.process.cronExpression}")
	public void processWebOrders() {
		processor.process();
	}
}
