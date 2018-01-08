package com.betalife.sushibuffet.print;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import jpos.POSPrinterControl113;

public class EpsonPrinter implements Printer {

	POSPrinterControl113 ptr = (POSPrinterControl113) new POSPrinter();
	private String logo;

	private String logicName;

	public void print(List<?> list, boolean logo, int times) throws Exception {
		if (list == null || list.isEmpty()) {
			return;
		}
		try {
			init();
			if (logo) {
				logo();
			}
			for (int i = 0; i < times; i++) {
				for (Object object : list) {
					if (object instanceof String) {
						String line = (String) object;
						// printNormal(int station, String data)
						// A string is sent by using the method "printNormal",
						// and
						// it is
						// printed.
						// "\n" is the standard code for starting a new line.
						// When the end of the line have no "\n",printing by
						// using the method "printNormal" doesn't start, may be.
						ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, line);
					} else if (object instanceof byte[]) {
						byte[] img = (byte[]) object;
						ptr.setMapMode(POSPrinterConst.PTR_MM_METRIC);
						ptr.setRecLetterQuality(true);
						ptr.printMemoryBitmap(POSPrinterConst.PTR_S_RECEIPT, img, POSPrinterConst.PTR_BMT_JPEG, ptr.getRecLineWidth(),
								POSPrinterConst.PTR_BM_CENTER);
					}
				}
			}
		} finally {
			closing();
		}
	}

	/**
	 * Outline The processing code required in order to enable or to disable use
	 * of service is written here.
	 * 
	 * @exception JposException
	 *                This exception is fired toward the failure of the method
	 *                which JavaPOS defines.
	 */
	/**
	 * When the window was closed
	 * 
	 * @throws JposException
	 */
	protected void init() throws JposException {
		/** When the window open */
		// JavaPOS's code for Step1
		// Open the device.
		// Use the name of the device that connected with your computer.
		ptr.open(logicName);

		// Get the exclusive control right for the opened device.
		// Then the device is disable from other application.
		ptr.claim(1000);

		// Enable the device.
		ptr.setDeviceEnabled(true);
		// JavaPOS's code for Step1--END
	}

	/**
	 * Outline The code to finish a service.
	 * 
	 * @throws JposException
	 */
	void closing() throws JposException {
		// JavaPOS's code for Step1
		// Cancel the device.
		ptr.setDeviceEnabled(false);

		// Release the device exclusive control right.
		ptr.release();

		// Finish using the device.
		ptr.close();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
	}

	void logo() throws JposException {

		// Output by the high quality mode
		ptr.setRecLetterQuality(true);
		// Register a bitmap
		if (ptr.getCapRecBitmap() == true) {
			// Register a bitmap
			ptr.setBitmap(100, POSPrinterConst.PTR_S_RECEIPT, logo, POSPrinterConst.PTR_BM_ASIS, POSPrinterConst.PTR_BM_CENTER);
		}
		// print logo
		// ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|1B");
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCutPaper() {
		// cut paper
		// ptr.printNormal(POSPrinterConst.PTR_S_RECEIPT, "\u001b|fP");
		return "\u001b|fP";
	}

	public void setLogicName(String logicName) {
		this.logicName = logicName;
	}

	public static void main(String[] args) throws Exception {
		EpsonPrinter printer = new EpsonPrinter();
		printer.setLogicName("POSPrinter1");
		List<Object> list = new ArrayList<Object>();
		File file = new File("c:\\Users\\mbp-bobhao\\Desktop\\java\\test1.jpeg");
		byte[] bs = FileUtils.readFileToByteArray(file);
		list.add(bs);
		list.add(printer.getCutPaper());
		printer.print(list, false, 1);
	}
}
