package com.owen.framework.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.owen.framework.util.CollectionUtil;
import com.owen.framework.util.PropsUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * 数据库操作助手
 * 
 * @author OwenWilliam
 * @Date 2016-10-10
 * @version 2.0.0
 * 
 */
public final class DatabaseHelper
{

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

	/** 数据库操作 */
	private static final QueryRunner QUERY_RUNNER;

	/** 存放连接connection */
	private static final ThreadLocal<Connection> CONNECTION_HOLDER;

	/** 不用自己写那么多的数据库连接，而是使用这个参数来执行 */
	private static final BasicDataSource DATA_SOURCE;

	static
	{

		/** 确保线程只有一个Connection，使用Threadlocal来存放本地线程变量 */
		CONNECTION_HOLDER = new ThreadLocal<Connection>();

		QUERY_RUNNER = new QueryRunner();

		/**
		 * 从config.properties文件中读取信息
		 */
		Properties conf = PropsUtil.loadProps("config.properties");
		String driver = conf.getProperty("jdbc.driver");
		String url = conf.getProperty("jdbc.url");
		String username = conf.getProperty("jdbc.username");
		String password = conf.getProperty("jdbc.password");

		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setDriverClassName(driver);
		DATA_SOURCE.setUrl(url);
		DATA_SOURCE.setUsername(username);
		DATA_SOURCE.setPassword(password);

	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnection()
	{
		// 先在ThreadLocal中寻找，如果不存在，则创建一个新的Connection
		Connection conn = CONNECTION_HOLDER.get();
		if (conn == null)
		{
			try
			{
				// 数据库连接
				conn = DATA_SOURCE.getConnection();
			} catch (SQLException e)
			{
				LOGGER.error("get connection failure ", e);
				throw new RuntimeException(e);
			} finally
			{
				// 将新Connection放入ThreadLocal中
				CONNECTION_HOLDER.set(conn);
			}
		}

		return conn;
	}

	/**
	 * 查询实体列表:多表查寻时，输入一个SQL和多个List对象
	 * 
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> List<T> queryEntityList(Class<T> entityClass, String sql,
			Object... params)
	{
		List<T> entityList;
		try
		{
			Connection conn = getConnection();
			entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(
					entityClass), params);
		} catch (SQLException e)
		{
			LOGGER.error("query entity list failure", e);
			throw new RuntimeException(e);
		}

		return entityList;
	}

	/**
	 * 执行更新语句(update insert delete)
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int executeUpdate(String sql, Object... params)
	{
		int rows = 0;
		try
		{
			Connection conn = getConnection();
			rows = QUERY_RUNNER.update(conn, sql, params);
		} catch (SQLException e)
		{
			LOGGER.error("execute update failure", e);
			throw new RuntimeException(e);
		}
		
		return rows;
	}

	/**
	 * 查询实体
	 * 
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> T queryEntity(Class<T> entityClass, String sql,
			Object... params)
	{
		T entity;
		try
		{
			Connection conn = getConnection();
			entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(
					entityClass), params);
		} catch (SQLException e)
		{
			LOGGER.error("query entity failure", e);
			throw new RuntimeException(e);
		}
		
		return entity;
	}

	/**
	 * 插入实体
	 * 
	 * @param entityClass
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean insertEntity(Class<T> entityClass,
			Map<String, Object> fieldMap)
	{
		if (CollectionUtil.isEmpty(fieldMap))
		{
			LOGGER.error("can not insert entity:fieldMap is empty");
			return false;
		}

		String sql = "INSERT INTO " + getTableName(entityClass);
		// 字段名
		StringBuilder columns = new StringBuilder("(");
		// 插入值
		StringBuilder values = new StringBuilder("(");
		for (String fieldName : fieldMap.keySet())
		{
			columns.append(fieldName).append(", ");
			values.append("?, ");
		}

		columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
		values.replace(values.lastIndexOf(", "), values.length(), ")");
		// 整合成一个sql语句
		sql += columns + "VALUES " + values;
		Object[] params = fieldMap.values().toArray();
		return executeUpdate(sql, params) == 1;
	}

	/**
	 * 更新实体
	 * 
	 * @param entityClass
	 * @param id
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean updateEntity(Class<T> entityClass, long id,
			Map<String, Object> fieldMap)
	{
		if (CollectionUtil.isEmpty(fieldMap))
		{
			LOGGER.error("can not update entity:fieldMap is empty");
			return false;
		}

		String sql = "UPDATE " + getTableName(entityClass) + " SET ";
		StringBuilder columns = new StringBuilder();
		for (String fieldName : fieldMap.keySet())
		{
			columns.append(fieldName).append("=?, ");
		}
		sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id=?";

		List<Object> paramList = new ArrayList<Object>();
		paramList.addAll(fieldMap.values());
		paramList.add(id);
		Object[] params = paramList.toArray();

		return executeUpdate(sql, params) == 1;
	}

	/**
	 * 删除实体
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public static <T> boolean deleteEntity(Class<T> entityClass, long id)
	{
		String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id=?";
		return executeUpdate(sql, id) == 1;
	}

	/**
	 * 查找单个用户
	 * 
	 * @param entityClass
	 * @param sql
	 * @return
	 */
	public static <T> T getEntity(Class<T> entityClass, String sql)
	{
		T entity;
		try
		{
			Connection conn = getConnection();
			entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(
					entityClass));
		} catch (SQLException e)
		{
			LOGGER.error("query entity list failure", e);
			throw new RuntimeException(e);
		}

		return entity;

	}

	/**
	 * 获取表名
	 * 
	 * @param entityClass
	 * @return
	 */
	private static String getTableName(Class<?> entityClass)
	{
		return entityClass.getSimpleName();
	}

	/**
	 * 执行SQL文件
	 * 
	 * @param filePath
	 */
	public static void executeSqlFile(String filePath)
	{
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		try
		{
			String sql;
			while ((sql = reader.readLine()) != null)
			{
				executeUpdate(sql);
			}
		} catch (Exception e)
		{
			LOGGER.error("execute sql file failure", e);
			throw new RuntimeException(e);
		}

	}
}
