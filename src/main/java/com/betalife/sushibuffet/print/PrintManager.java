package com.betalife.sushibuffet.print;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.templete.ContentTemplete;
import com.betalife.sushibuffet.templete.LedgerTemplete;
import com.betalife.sushibuffet.templete.OrderTemplete;
import com.betalife.sushibuffet.templete.ReceiptTemplete;

@Service
public class PrintManager implements ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(PrintManager.class);

	@Value("${print.times}")
	private int times;

	@Resource(name = "printers")
	private Properties printers;

	@Autowired
	private OrderTemplete orderTemplete;

	@Autowired
	private ReceiptTemplete receiptTemplete;

	@Autowired
	private LedgerTemplete ledgerTemplete;

	private ApplicationContext applicationContext;

	synchronized private void print(String barName, Object content, boolean logo, int times)
			throws Exception {
		Printer printer = getPrinter(barName);
		List<Object> list = new ArrayList<Object>();
		list.add(content);
		list.add(printer.getCutPaper());
		printer.print(list, logo, times);
	}

	private Printer getPrinter(String barName) {
		Printer printer = applicationContext.getBean("printer", Printer.class);
		String logicName = printers.getProperty("printer." + barName);
		logicName = StringUtils.isEmpty(logicName) ? printers.getProperty("printer.default") : logicName;

		printer.setLogicName(logicName);
		return printer;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void printOrders(Turnover turnover, List<Order> orders, String locale, boolean logo)
			throws Exception {
		if (CollectionUtils.isEmpty(orders)) {
			return;
		}
		Map<String, Map<String, Object>> barnameParamMap = orderTemplete.buildBarnameParam(turnover, orders,
				locale, null);
		print(barnameParamMap, logo, times, orderTemplete);
	}

	// public void print(String barName, Map<String, Object> content, boolean
	// logo, int times,
	// ContentTemplete templetePOSUtil) throws Exception {
	// logger.info("####" + content);
	// String html = templetePOSUtil.format(content);
	// logger.info("print receipt list:");
	// logger.info(html);
	// print(barName, html, logo, times);
	// }

	public void printReceipt(Turnover turnover, List<Order> orders, String locale, boolean logo,
			Takeaway takeaway) throws Exception {
		if (CollectionUtils.isEmpty(orders)) {
			return;
		}

		Map<String, Map<String, Object>> barnameParamMap = receiptTemplete.buildBarnameParam(turnover, orders,
				locale, takeaway);
		print(barnameParamMap, false, times, receiptTemplete);
	}

	public void printLedger(List<Order> orders) throws Exception {
		if (CollectionUtils.isEmpty(orders)) {
			return;
		}

		Map<String, Map<String, Object>> barnameParamMap = ledgerTemplete.buildBarnameParam(null, orders,
				null, null);
		print(barnameParamMap, false, times, ledgerTemplete);
	}

	private void print(Map<String, Map<String, Object>> barnameParamMap, boolean logo, int times,
			ContentTemplete contentTemplete) throws Exception {
		Set<String> keySet = barnameParamMap.keySet();
		for (String barName : keySet) {
			Map<String, Object> map = barnameParamMap.get(barName);
			Object object = contentTemplete.getPosTemplete().format(map);
			print(barName, object, logo, times);
		}

	}

	public void printLedger(Map<String, Map<String, Object>> barnameParam) throws Exception {
		print(barnameParam, false, times, ledgerTemplete);
	}

}
