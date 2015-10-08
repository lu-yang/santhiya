package com.betalife.sushibuffet.util;

import static com.betalife.sushibuffet.util.DodoroUtil.HUNDRED;
import static com.betalife.sushibuffet.util.DodoroUtil.ONE;
import static com.betalife.sushibuffet.util.DodoroUtil.ZERO;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.betalife.sushibuffet.dao.TaxgroupsMapper;
import com.betalife.sushibuffet.model.Taxgroups;

import freemarker.template.Template;

public abstract class TempletePOSUtil extends TempleteUtil {

	@Autowired
	private TaxgroupsMapper taxgroupsMapper;

	private final static String ESC_STR = "\\u001b";
	private final static String ESC = "\u001b";

	private final static String NL_STR = "\\n";
	private final static String NL = "\n";

	protected static final String TAKEAWAY_PREFIX = "takeaway_";
	protected static final String FOOD = "food";
	protected static final String ALCOHOL = "alcool";

	private static NumberFormat percentInstance;
	static {
		percentInstance = NumberFormat.getPercentInstance();
		percentInstance.setMaximumFractionDigits(4);
	}

	@PostConstruct
	public void init() throws IOException {
		File file = getFile();
		String content = FileUtils.readFileToString(file);
		content = content.replace(ESC_STR, ESC);
		content = content.replace(NL_STR, NL);

		template = new Template(null, new StringReader(content), null);
		template.setEncoding("UTF-8");

	}

	protected void putTotal(float tax, String kind, BigDecimal kindTotal, Map<String, Object> map) {
		String percent = percentInstance.format(tax);
		map.put(kind + "_tax", percent);
		if (kindTotal == null || kindTotal.equals(ZERO)) {
			map.put(kind + "_tax_paid", "0");
			map.put(kind + "_total_paid", "0");
			map.put(kind + "_paid", "0");
		} else {
			BigDecimal kindTaxRate = new BigDecimal(tax);
			BigDecimal kindTotalTax = kindTotal.multiply(kindTaxRate).divide(
					(kindTaxRate.add(ONE)).multiply(HUNDRED), 2, BigDecimal.ROUND_DOWN);
			map.put(kind + "_tax_paid", kindTotalTax.floatValue());
			BigDecimal kindTotalF = kindTotal.divide(HUNDRED, 2, BigDecimal.ROUND_DOWN);
			map.put(kind + "_total_paid", kindTotalF.floatValue());
			map.put(kind + "_paid", "" + kindTotalF.subtract(kindTotalTax).floatValue());
		}
	}

	protected Map<String, Taxgroups> getTaxgroupMap() {
		List<Taxgroups> taxgroups = taxgroupsMapper.selectAll();
		Map<String, Taxgroups> taxgroupMap = new HashMap<String, Taxgroups>();
		for (Taxgroups taxgroup : taxgroups) {
			taxgroupMap.put(taxgroup.getName(), taxgroup);
		}
		return taxgroupMap;
	}

}
