package com.universe.jdkapi.number;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author 刘亚楼
 * @date 2020/8/2
 */
public class BigDecimalUtils {

	/**
	 * 默认舍入模式为四舍五入
	 */
	private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

	public static BigDecimal toPreciseBigDecimal(BigDecimal value, int precision) {
		return toPreciseBigDecimal(value, precision, null);
	}

	/**
	 * 保留精度(有效位)
	 * @param value
	 * @param precision 精度
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal toPreciseBigDecimal(BigDecimal value, int precision, RoundingMode roundingMode) {
		return dividePrecisely(value, BigDecimal.ONE, precision, roundingMode);
	}

	public static BigDecimal toScaledBigDecimal(BigDecimal value, int scale) {
		return toScaledBigDecimal(value, scale, null);
	}

	/**
	 * 保留小数位
	 * @param value
	 * @param scale 小数位
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal toScaledBigDecimal(BigDecimal value, int scale, RoundingMode roundingMode) {
		if (value == null) {
			return null;
		}
		return value.setScale(scale, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
	}

	public static BigDecimal add(BigDecimal addend, BigDecimal augend, int scale) {
		return add(addend, augend, scale, null);
	}

	/**
	 * 两数相加保留小数位
	 * @param addend 加数
	 * @param augend 被加数
	 * @param scale 小数位
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal add(BigDecimal addend, BigDecimal augend, int scale, RoundingMode roundingMode) {
		if (addend == null) {
			return null;
		}

		BigDecimal decimal = addend.add(augend);
		return toScaledBigDecimal(decimal, scale, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
	}

	public static BigDecimal addPrecisely(BigDecimal addend, BigDecimal augend, int precision) {
		return addPrecisely(addend, augend, precision, null);
	}

	/**
	 * 两数相加保留精度
	 * @param addend
	 * @param augend 被加数
	 * @param precision 精度
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal addPrecisely(BigDecimal addend, BigDecimal augend, int precision, RoundingMode roundingMode) {
		if (addend == null) {
			return null;
		}
		MathContext mc = new MathContext(precision, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
		return addend.add(augend, mc);
	}

	public static BigDecimal substract(BigDecimal minuend, BigDecimal subtrahend, int scale) {
		return substract(minuend, subtrahend, scale, null);
	}

	public static BigDecimal substract(BigDecimal minuend, BigDecimal subtrahend, int sacle, RoundingMode roundingMode) {
		if (minuend == null) {
			return null;
		}

		BigDecimal result = minuend.subtract(subtrahend);
		return toScaledBigDecimal(result, sacle, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
	}

	public static BigDecimal subtractPrecisely(BigDecimal minuend, BigDecimal subtrahend, int precision) {
		return subtractPrecisely(minuend, subtrahend, precision, null);
	}

	/**
	 * 两数相乘保留精度
	 * @param minuend
	 * @param subtrahend 被乘数
	 * @param precision 精度
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal subtractPrecisely(BigDecimal minuend, BigDecimal subtrahend, int precision, RoundingMode roundingMode) {
		if (minuend == null) {
			return null;
		}
		MathContext mc = new MathContext(precision, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
		return minuend.subtract(subtrahend, mc);
	}

	public static BigDecimal multiply(BigDecimal multiplier, BigDecimal multiplicand, int scale) {
		return multiply(multiplier, multiplicand, scale, null);
	}

	/**
	 * 两数相乘保留小数位
	 * @param multiplier 乘数
	 * @param multiplicand 被乘数
	 * @param scale 小数位
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal multiplier, BigDecimal multiplicand, int scale, RoundingMode roundingMode) {
		if (multiplier == null) {
			return null;
		}

		BigDecimal decimal = multiplier.multiply(multiplicand);
		return toScaledBigDecimal(decimal, scale, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
	}

	public static BigDecimal multiplyPrecisely(BigDecimal multiplier, BigDecimal multiplicand, int precision) {
		return multiplyPrecisely(multiplier, multiplicand, precision, null);
	}

	/**
	 * 两数相乘保留精度
	 * @param multiplier
	 * @param multiplicand 乘数
	 * @param precision 精度
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal multiplyPrecisely(BigDecimal multiplier, BigDecimal multiplicand, int precision, RoundingMode roundingMode) {
		if (multiplier == null) {
			return null;
		}
		MathContext mc = new MathContext(precision, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
		return multiplier.multiply(multiplicand, mc);
	}

	/**
	 * 两数相除保留精度(有效位)，舍入规则默认四舍五入
	 * @param dividend
	 * @param divisor
	 * @param precision
	 * @return
	 */
	public static BigDecimal dividePrecisely(BigDecimal dividend, BigDecimal divisor, int precision) {
		return dividePrecisely(dividend, divisor, precision, null);
	}

	/**
	 * 两数相除保留精度(有效位)
	 * @param dividend
	 * @param divisor 除数
	 * @param precision 精度
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal dividePrecisely(BigDecimal dividend, BigDecimal divisor, int precision, RoundingMode roundingMode) {
		if (dividend == null) {
			return null;
		}
		MathContext mc = new MathContext(precision, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
		return dividend.divide(divisor, mc);
	}

	public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale) {
		return divide(dividend, divisor, scale, null);
	}

	/**
	 * 两数相除保留小数位
	 * @param dividend
	 * @param divisor 除数
	 * @param scale 小数位数
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale, RoundingMode roundingMode) {
		if (dividend == null) {
			return null;
		}

		return dividend.divide(divisor, scale, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
	}

	public static BigDecimal max(BigDecimal first, BigDecimal second) {
		return first != null && second != null ? first.max(second) : null;
	}

	public static BigDecimal min(BigDecimal first, BigDecimal second) {
		return first != null && second != null ? first.min(second) : null;
	}

	public static boolean greaterThan(BigDecimal first, BigDecimal second) {
		return first != null && second != null && first.compareTo(second) > 0;
	}

	public static boolean greaterEqualThan(BigDecimal first, BigDecimal second) {
		return first != null && second != null && first.compareTo(second) >= 0;
	}

	public static boolean lessThan(BigDecimal first, BigDecimal second) {
		return first != null && second != null && first.compareTo(second) < 0;
	}

	public static boolean lessEqualThan(BigDecimal first, BigDecimal second) {
		return first != null && second != null && first.compareTo(second) <= 0;
	}


}
