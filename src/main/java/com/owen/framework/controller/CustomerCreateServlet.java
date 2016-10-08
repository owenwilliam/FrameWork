package com.owen.framework.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owen.framework.model.Customer;
import com.owen.framework.service.CustomerService;

/**
 * 创建客户
 * 
 * @author OwenWilliam
 * @Date 2016-10-10
 * @version 2.0.0
 *
 */

@WebServlet("/customer_create")
public class CustomerCreateServlet extends HttpServlet
{
	private CustomerService customerService;

	@Override
	public void init() throws ServletException
	{
		customerService = new CustomerService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		List<Customer> customerList = customerService.getCustomerList();
		req.setAttribute("customerList", customerList);
		//显示所有用户
		req.getRequestDispatcher("/WEB-INF/view/customer.jsp").forward(req,
				resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		super.doPost(req, resp);
	}

}
