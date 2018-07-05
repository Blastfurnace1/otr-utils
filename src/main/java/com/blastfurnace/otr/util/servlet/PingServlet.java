package com.blastfurnace.otr.util.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.blastfurnace.otr.constants.Constants;

@WebServlet("/ping")
public class PingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** Override and Check the Status of the resources used. */
	protected String checkServices() {
		return Constants.PING_SERVICE_RESPONSE;
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PingServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Set response content type
    	response.setContentType("text/html");
    	PrintWriter out = response.getWriter();
   		out.println(checkServices());
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
