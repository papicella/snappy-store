/*
Copyright (C) MARCH-2014 Pivotal Software, Inc.

All rights reserved. This program and the accompanying materials
are made available under the terms of the under the Apache License,
Version 2.0 (the "License”); you may not use this file except in compliance
with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package pivotal.au.se.gemfirexdweb.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pivotal.au.se.gemfirexdweb.dao.GemFireXDWebDAOFactory;
import pivotal.au.se.gemfirexdweb.dao.diskstores.DiskStore;
import pivotal.au.se.gemfirexdweb.dao.diskstores.DiskStoreDAO;
import pivotal.au.se.gemfirexdweb.dao.hdfsstores.HdfsStore;
import pivotal.au.se.gemfirexdweb.dao.hdfsstores.HdfsStoreDAO;
import pivotal.au.se.gemfirexdweb.main.Result;
import pivotal.au.se.gemfirexdweb.main.UserPref;
import pivotal.au.se.gemfirexdweb.utils.AdminUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HdfsStoreController
{
	protected static Logger logger = Logger.getLogger("controller");
    private static final String FILENAME = "%s.sql";
    private static final String SAVE_CONTENT_TYPE = "application/x-download";

	@RequestMapping(value = "/hdfsstores", method = RequestMethod.GET)
    public String showHdfsstores
    (Model model, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception 
    {
    	if (session.getAttribute("user_key") == null)
    	{
    		logger.debug("user_key is null new Login required");
    		response.sendRedirect(request.getContextPath() + "/GemFireXD-Web/login");
    		return null;
    	}
        else
        {
            Connection conn = AdminUtil.getConnection((String) session.getAttribute("user_key"));
            if (conn == null )
            {
                response.sendRedirect(request.getContextPath() + "/GemFireXD-Web/login");
                return null;
            }
            else
            {
                if (conn.isClosed())
                {
                    response.sendRedirect(request.getContextPath() + "/GemFireXD-Web/login");
                    return null;
                }
            }

        }
    	
    	logger.debug("Received request to show hdfs stores");

		int startAtIndex = 0, endAtIndex = 0;
		javax.servlet.jsp.jstl.sql.Result allHdfsStoreInfoResult = null;
		String schema = null; 
    	
    	HdfsStoreDAO hdfsDAO = GemFireXDWebDAOFactory.getHdfsStoreDAO();
    	Result result = new Result();
    	
    	String hdfsAction = request.getParameter("hdfsAction");
   	
    	if (hdfsAction != null)
    	{
    		logger.debug("hdfsAction = " + hdfsAction);
    		
    		if (hdfsAction.equals("ALLHDFSSTOREINFO"))
    		{
    			allHdfsStoreInfoResult = hdfsDAO.getHdfsStoreInfo
    					((String)request.getParameter("hdfsName"),
					     (String)session.getAttribute("user_key"));
    			model.addAttribute("allHdfsStoreInfoResult", allHdfsStoreInfoResult);
    			model.addAttribute("hdfsName", (String)request.getParameter("hdfsName"));
    		}
    		else
    		{
	            result = null;
	            result =
	              hdfsDAO.simplehdfsStoreCommand
                          ((String) request.getParameter("hdfsName"),
                                  hdfsAction,
                                  (String) session.getAttribute("user_key"));
	              
	            model.addAttribute("result", result);
    		}
            
    	}
    	
    	List<HdfsStore> hdfsStores = hdfsDAO.retrieveHdfsStoreList
                ("",
                null,
                (String) session.getAttribute("user_key"));
    	
    	model.addAttribute("records", hdfsStores.size());
    	model.addAttribute("estimatedrecords", hdfsStores.size());
    	        
    	UserPref userPref = (UserPref) session.getAttribute("prefs");
        
        if (hdfsStores.size() <= userPref.getRecordsToDisplay())
        {
        	model.addAttribute("hdfsstores", hdfsStores);
        }
        else
        {
          if (request.getParameter("startAtIndex") != null)
          {
            startAtIndex = Integer.parseInt(request.getParameter("startAtIndex"));
          }
          
          if (request.getParameter("endAtIndex") != null)
          {
            endAtIndex = Integer.parseInt(request.getParameter("endAtIndex"));
            if (endAtIndex > hdfsStores.size())
            {
              endAtIndex = hdfsStores.size();
            }
          }
          else
          {
            endAtIndex = userPref.getRecordsToDisplay();
          }
          
          List subList = hdfsStores.subList(startAtIndex, endAtIndex);
          model.addAttribute("hdfsstores", subList);
        }  
        
        model.addAttribute("startAtIndex", startAtIndex);
        model.addAttribute("endAtIndex", endAtIndex);
    	
    	// This will resolve to /WEB-INF/jsp/hdfsstores.jsp
    	return "hdfsstores";
    }

	@RequestMapping(value = "/hdfsstores", method = RequestMethod.POST)
    public String performHdfsStoreAction
    (Model model, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws Exception 
    {
    	if (session.getAttribute("user_key") == null)
    	{
    		logger.debug("user_key is null new Login required");
    		response.sendRedirect(request.getContextPath() + "/GemFireXD-Web/login");
    		return null;
    	}
        else
        {
            Connection conn = AdminUtil.getConnection((String)session.getAttribute("user_key"));
            if (conn == null )
            {
                response.sendRedirect(request.getContextPath() + "/GemFireXD-Web/login");
                return null;
            }
            else
            {
                if (conn.isClosed())
                {
                    response.sendRedirect(request.getContextPath() + "/GemFireXD-Web/login");
                    return null;
                }
            }

        }

    	int startAtIndex = 0, endAtIndex = 0;
    	Result result = new Result();
    	List<HdfsStore> hdfsStores = null;
        String ddlString = null;

    	logger.debug("Received request to perform an action on the hdfs stores");

        HdfsStoreDAO hdfsDAO = GemFireXDWebDAOFactory.getHdfsStoreDAO();

    	if (request.getParameter("search") != null)
    	{
	    	hdfsStores = hdfsDAO.retrieveHdfsStoreList
	    			("", 
	    			 (String)request.getParameter("search"), 
	    			 (String)session.getAttribute("user_key"));
	    	
	    	model.addAttribute("search", (String)request.getParameter("search"));
    	}
    	else
    	{
	        String[] tableList  = request.getParameterValues("selected_hdfs[]");
	        String   commandStr = request.getParameter("submit_mult");
	        
	        logger.debug("tableList = " + Arrays.toString(tableList));
	        logger.debug("command = " + commandStr);
	        
	        // start actions now if tableList is not null
	        
	        if (tableList != null)
	        {
	        	List al = new ArrayList<Result>();
                List<String> al2 = new ArrayList<String>();

	        	for (String table: tableList)
	        	{
                    if (commandStr.equalsIgnoreCase("DDL") || commandStr.equalsIgnoreCase("DDL_FILE"))
                    {
                        ddlString = hdfsDAO.generateDDL(table, (String)session.getAttribute("user_key"));
                        al2.add(ddlString);
                    }
                    else
                    {
                        result = null;
                        result =
                          hdfsDAO.simplehdfsStoreCommand
                            (table,
                             commandStr,
                             (String)session.getAttribute("user_key"));
                        al.add(result);
                    }
	        	}

                if (commandStr.equalsIgnoreCase("DDL"))
                {
                    request.setAttribute("arrayresultddl", al2);
                }
                else if (commandStr.equalsIgnoreCase("DDL_FILE"))
                {
                    response.setContentType(SAVE_CONTENT_TYPE);
                    response.setHeader("Content-Disposition", "attachment; filename=" +
                            String.format(FILENAME, "HDFSStoreDDL"));

                    ServletOutputStream out = response.getOutputStream();
                    for (String ddl: al2)
                    {
                        out.println(ddl);
                    }

                    out.close();
                    return null;
                }
                else
                {
                    model.addAttribute("arrayresult", al);
                }
	        }
	        
	    	hdfsStores = hdfsDAO.retrieveHdfsStoreList
	    			("", 
	    			 null, 
	    			 (String)session.getAttribute("user_key"));
    	}
    	
    	model.addAttribute("records", hdfsStores.size());
    	model.addAttribute("estimatedrecords", hdfsStores.size());
    	
    	UserPref userPref = (UserPref) session.getAttribute("prefs");
        
        if (hdfsStores.size() <= userPref.getRecordsToDisplay())
        {
        	model.addAttribute("hdfsstores", hdfsStores);
        }
        else
        {
          if (request.getParameter("startAtIndex") != null)
          {
            startAtIndex = Integer.parseInt(request.getParameter("startAtIndex"));
          }
          
          if (request.getParameter("endAtIndex") != null)
          {
            endAtIndex = Integer.parseInt(request.getParameter("endAtIndex"));
            if (endAtIndex > hdfsStores.size())
            {
              endAtIndex = hdfsStores.size();
            }
          }
          else
          {
            endAtIndex = userPref.getRecordsToDisplay();
            
          }
          
          List subList = hdfsStores.subList(startAtIndex, endAtIndex);
          model.addAttribute("hdfsstores", subList);
        }  
        
        model.addAttribute("startAtIndex", startAtIndex);
        model.addAttribute("endAtIndex", endAtIndex);
    	
    	// This will resolve to /WEB-INF/jsp/hdfsstores.jsp
    	return "hdfsstores";
    	
    }

}
