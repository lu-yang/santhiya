package com.betalife.sushibuffet.util;

import java.math.BigDecimal;

import com.betalife.sushibuffet.model.Turnover;

public class DodoroUtil {
	public static final BigDecimal TEN_THOUSAND = new BigDecimal(10000);
	public static final BigDecimal HUNDRED = new BigDecimal(100);
	public static final BigDecimal ONE = new BigDecimal(1);
	public static final BigDecimal ZERO = new BigDecimal(0);

	public static String getDisplayPrice(int price) {
		BigDecimal displayPrice = new BigDecimal(price).divide(HUNDRED);
		return getDisplayPrice(displayPrice);
	}

	public static String getDisplayPrice(BigDecimal displayPrice) {
		return displayPrice.setScale(2, BigDecimal.ROUND_DOWN).toString();
	}

	// public static BigDecimal getDiscountPrice(Integer total, Integer
	// discount) {
	// if (total == null) {
	// return ZERO;
	// }
	// int percent = 0;
	// if (discount == null) {
	// percent = 0;
	// } else if (discount == 0) {
	// percent = 100;
	// } else {
	// percent = discount;
	// }
	// BigDecimal discountPrice = new BigDecimal(total * (100 -
	// percent)).divide(TEN_THOUSAND);
	// return discountPrice;
	// }

	public static BigDecimal divide(Integer total, BigDecimal divider) {
		if (total == null || total == 0) {
			return ZERO;
		}
		return new BigDecimal(total).divide(divider);
	}

	public static boolean isTakeaway(Turnover turnover) {
		return turnover.getTableId() == 0;
	}
}
