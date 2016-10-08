package com.owen.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * 
 * @author OwenWilliam
 * @Date 2016-10-10
 * @version 2.0.0
 *
 */
public final class StringUtil
{

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str)
	{
		if (str != null)
		{
			str = str.trim();
		}

		return StringUtils.isEmpty(str);
	}

	/**
	 * 判断字符串是否非空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str)
	{
		return !isEmpty(str);
	}
}
