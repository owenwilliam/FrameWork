package com.owen.framework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除客户
 * 
 * @author OwenWilliam
 * @Date 2016-10-08
 * @version 2.0.0
 *
 */
@WebServlet("/customer_delete")
public class CustomerDeleteServlet extends HttpServlet
{

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}

}
