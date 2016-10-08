package com.owen.framework.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.owen.framework.helper.DatabaseHelper;
import com.owen.framework.model.Customer;
import com.owen.framework.util.PropsUtil;

/**
 * 提供客户数据服务
 * 
 * @author OwenWilliam
 * @Date 2016-10-08
 * @version v2.0.0
 *
 */
public class CustomerService
{
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerService.class);

	/**
	 * 获取客户列表
	 */
	public List<Customer> getCustomerList()
	{
		String sql = "SELECT * FROM customer";
		return DatabaseHelper.queryEntityList(Customer.class, sql);
	}

	/**
	 * 获取客户
	 */
	public Customer getCustomer(long id)
	{
		String sql = "SELECT * FROM customer WHERE id = ?";
		return DatabaseHelper.queryEntity(Customer.class, sql, id);
	}

	/**
	 * 创建客户
	 */
	public boolean createCustomer(Map<String, Object> fieldMap)
	{
		return DatabaseHelper.insertEntity(Customer.class, fieldMap);
	}

	/**
	 * 更新客户
	 */
	public boolean updateCustomer(long id, Map<String, Object> fieldMap)
	{
		return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
	}

	/**
	 * 删除客户
	 */
	public boolean deleteCustomer(long id)
	{
		return DatabaseHelper.deleteEntity(Customer.class, id);
	}
}
