package com.owen.framework.util;

/**
 * 转型操作工具类
 * 
 * @author OwenWilliam
 * @Date 2016-10-10
 * @version 1.0.0
 *
 */
public final class CastUtil
{

	/**
	 * 转为String型
	 * 
	 * @param object
	 * @return
	 */
	public static String castString(Object object)
	{
		return CastUtil.castString(object, "");
	}

	/**
	 * 转型为String型（提供默认值）
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static String castString(Object obj, String defaultValue)
	{
		return obj != null ? String.valueOf(obj) : defaultValue;
	}

	/**
	 * 转为double型
	 * 
	 * @param obj
	 * @return
	 */
	public static double castDouble(Object obj)
	{
		return CastUtil.castDouble(obj, 0);
	}

	/**
	 * 转为double型（提供默认值）
	 * 
	 * @param obj
	 * @param defaultValute
	 * @return
	 */
	public static double castDouble(Object obj, double defaultValute)
	{
		double doubleValue = defaultValute;
		if (obj != null)
		{
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue))
			{
				try
				{
					doubleValue = Double.parseDouble(strValue);
				} catch (NumberFormatException e)
				{
					doubleValue = defaultValute;
				}

			}
		}

		return doubleValue;
	}

	/**
	 * 转为long型
	 * 
	 * @param obj
	 * @return
	 */
	public static long castLong(Object obj)
	{
		return CastUtil.castLong(obj, 0);
	}

	/**
	 * 转型为long型（提供默认值）
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static long castLong(Object obj, long defaultValue)
	{
		long longValue = defaultValue;
		if (obj != null)
		{
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue))
			{
				try
				{
					longValue = Long.parseLong(strValue);
				} catch (NumberFormatException e)
				{
					longValue = defaultValue;
				}
			}
		}

		return longValue;
	}

	/**
	 * 转型为int型
	 * 
	 * @param obj
	 * @return
	 */
	public static int castInt(Object obj)
	{
		return CastUtil.castInt(obj, 0);
	}

	/**
	 * 转型为int型（提供默认值）
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static int castInt(Object obj, int defaultValue)
	{
		int intValue = defaultValue;
		if (obj != null)
		{
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue))
			{
				try
				{
					intValue = Integer.parseInt(strValue);
				} catch (NumberFormatException e)
				{
					intValue = defaultValue;
				}
			}
		}

		return intValue;
	}

	/**
	 * 转为boolean型
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean castBoolean(Object obj)
	{
		return CastUtil.castBoolean(obj, false);
	}

	/**
	 * 转为boolean型（提供默认值）
	 * 
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static boolean castBoolean(Object obj, boolean defaultValue)
	{
		boolean booleanValue = defaultValue;
		if (obj != null)
		{
			booleanValue = Boolean.parseBoolean(castString(obj));
		}

		return booleanValue;
	}
}
