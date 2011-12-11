/**
 * @(#)com.douya.base.util.NumberHelper.java
 * 版权声明 山东益信通科贸有限公司, 版权所有 违者必究
 *
 *<br> Copyright:Copyright (c) 2010-2011
 *<br> Company： 山东益信
 *<br> Author： 葛云杰(geyj@douya.cn)
 *<br> Date：2011-09-17
 *<br> Version：1.0
 */
package com.douya.base.util;

/**
 * 数字格式转换工具类
 * @author GeYunjie
 *
 */
public class NumberHelper {

	public static String leftPad_tow_zero(int str) {
		java.text.DecimalFormat format = new java.text.DecimalFormat("00");
		return format.format(str);
	}

}
