package com.blastfurnace.otr.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.server.WebServerException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Servlet implementation class FileServer
 */
@WebServlet("/log")
public class LogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private String logPath = "C:/wildfly-12.0.0.Final/standalone/log/";

	private String logFile = "server.log";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private String getFileName() {
    	return logPath + logFile;
    }

    /** Output the file to the response. */
    private void sendFile(HttpServletResponse response, File my_file) throws IOException {
    	// This should send the file to browser
    	OutputStream out = response.getOutputStream();
    	FileInputStream in = new FileInputStream(my_file);
    	byte[] buffer = new byte[4096];
    	int length;
    	while ((length = in.read(buffer)) > 0){
    		out.write(buffer, 0, length);
    	}
    	in.close();
    	out.flush();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        String fileName = getFileName();
    	System.out.println(fileName);
    	File my_file = new File(fileName);
    	response.setHeader("Content-disposition", "attachment; filename="+ logFile.replaceAll(" ", "_"));
    	 // Set response content type
        response.setContentType("text/plain");
    	if (my_file.exists() == false) {
    		throw new WebServerException("File Not Found", new Exception("File Not Found"));
    	} else {
    		try {
    			sendFile(response, my_file);
    		} catch  (Exception e) {
    			System.out.println(e.getMessage());
    			e.printStackTrace();
    			throw new WebServerException("Could not send file", new Exception(e.getMessage()));
    		}
    	}
   
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
