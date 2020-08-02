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

	/**
	 * 两数相除保留精度(有效位)，舍入规则默认四舍五入
	 * @param value
	 * @param divisor
	 * @param precision
	 * @return
	 */
	public static BigDecimal dividePrecisely(BigDecimal value, BigDecimal divisor, int precision) {
		return dividePrecisely(value, divisor, precision, null);
	}

	/**
	 * 两数相除保留精度(有效位)
	 * @param value
	 * @param divisor 除数
	 * @param precision 精度
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal dividePrecisely(BigDecimal value, BigDecimal divisor, int precision, RoundingMode roundingMode) {
		if (value == null) {
			return null;
		}
		MathContext mc = new MathContext(precision, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
		return value.divide(divisor, mc);
	}

	public static BigDecimal divideScalably(BigDecimal value, BigDecimal divisor, int scale) {
		return divideScalably(value, divisor, scale, null);
	}

	/**
	 * 两数相除保留小数位
	 * @param value
	 * @param divisor 除数
	 * @param scale 小数位数
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal divideScalably(BigDecimal value, BigDecimal divisor, int scale, RoundingMode roundingMode) {
		if (value == null) {
			return null;
		}

		return value.divide(divisor, scale, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
	}

	public static BigDecimal multiplyPrecisely(BigDecimal value, BigDecimal multiplicand, int precision) {
		return multiplyPrecisely(value, multiplicand, precision, null);
	}

	/**
	 * 两数相乘保留精度
	 * @param value
	 * @param multiplicand 乘数
	 * @param precision 精度
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal multiplyPrecisely(BigDecimal value, BigDecimal multiplicand, int precision, RoundingMode roundingMode) {
		if (value == null) {
			return null;
		}
		MathContext mc = new MathContext(precision, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
		return value.multiply(multiplicand, mc);
	}

	public static BigDecimal subtractPrecisely(BigDecimal value, BigDecimal subtrahend, int precision) {
		return subtractPrecisely(value, subtrahend, precision, null);
	}

	/**
	 * 两数相乘保留精度
	 * @param value
	 * @param subtrahend 被乘数
	 * @param precision 精度
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal subtractPrecisely(BigDecimal value, BigDecimal subtrahend, int precision, RoundingMode roundingMode) {
		if (value == null) {
			return null;
		}
		MathContext mc = new MathContext(precision, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
		return value.subtract(subtrahend, mc);
	}

	public static BigDecimal addPrecisely(BigDecimal value, BigDecimal augend, int precision) {
		return addPrecisely(value, augend, precision, null);
	}

	/**
	 * 两数相加保留精度
	 * @param value
	 * @param augend 被加数
	 * @param precision 精度
	 * @param roundingMode 舍入模式
	 * @return
	 */
	public static BigDecimal addPrecisely(BigDecimal value, BigDecimal augend, int precision, RoundingMode roundingMode) {
		if (value == null) {
			return null;
		}
		MathContext mc = new MathContext(precision, roundingMode == null ? DEFAULT_ROUNDING_MODE : roundingMode);
		return value.add(augend, mc);
	}
}
