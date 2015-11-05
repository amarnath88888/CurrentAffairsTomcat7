package com.affairs.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.affairs.Handler.BusinessHandler;
import com.affairs.constants.ServletConstants;
import com.mongodb.BasicDBObject;

/**
 * This is the Servlet controller class used to invoke each of the service classes like entity and business
 * @author Amar
 *
 */
public class AffairsServlet extends HttpServlet{
	
	private static final long serialVersionUID = -8204614439848597538L;
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException	{
		// Create empty DBObjects by default
		BasicDBObject jsonDBObject = new BasicDBObject();
		// set the response type as json type
		response.setContentType(ServletConstants.JSON_CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		
		String author = request.getParameter(ServletConstants.AUTHOR);
		String newsDesc = request.getParameter(ServletConstants.NEWS_DESC);
		
		/*Iterator<String> itr = keyset.iterator();
		while (itr.hasNext())	{
			String key = itr.next();
			String val = jsonObject.get(key).toString();
			jsonDBObject.append(key, val);
		}*/
		// convert the request json objects to basicDBObjects
		/*if(json != null)	{
			jsonDBObject = JsonUtil.getBasicDbObjectFromJson(json);
		}*/
		jsonDBObject.append(ServletConstants.AUTHOR, author);
		jsonDBObject.append(ServletConstants.NEWS_DESC, newsDesc);
		
		BusinessHandler businessHandler = new BusinessHandler();
		out.println(businessHandler.getResponse("insert", jsonDBObject));
	}
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException	{
		doPost(request, response);
	}
}
