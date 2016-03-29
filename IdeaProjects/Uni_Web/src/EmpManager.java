/**
 * Created by IntelliJ IDEA.
 * User: kasun
 * Date: Nov 29, 2006
 * Time: 3:46:20 PM
 * To change this template use File | Settings | File Templates.
 */
package ocs;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class EmpManager extends HttpServlet{

	public void doPost(HttpServletRequest req ,HttpServletResponse res)throws IOException , ServletException{
		String name	=req.getParameter("txtName");

		String address	=req.getParameter("txtAddress");


		PrintWriter out=res.getWriter();

		out.println();

	}


}
