package com.betalife.sushibuffet.weborder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	private final Log log = LogFactory.getLog(getClass());

	@Scheduled(cron = "${ftp.download.cronExpression}")
	public void downloadWebOrders() {
		log.info("定时下载任务开始");
		ftp.download();
		log.info("定时下载任务结束");
	}

	@Scheduled(cron = "${ftp.process.cronExpression}")
	public void processWebOrders() {
		log.info("定时处理web订单任务开始");
		processor.process();
		log.info("定时处理web订单任务结束");
	}
}
