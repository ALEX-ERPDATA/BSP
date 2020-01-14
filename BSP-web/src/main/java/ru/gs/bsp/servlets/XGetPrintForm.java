/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.gs.bsp.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// сделать на JAX-RS @Get
public class XGetPrintForm extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
        String fullName = (String)  request.getSession().getAttribute("UserFullName");
        
        try {
		//URL url = new URL("http://localhost:8080/RESTfulExample/json/product/post");
                URL url = new URL("http://localhost:9080/BSP-web/app/makePrint");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
                
		conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Cookie","JSESSIONID="+request.getRequestedSessionId() );
                              
		String input = "{\"qty\":100,\"name\":\"iPad 4\"}";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("==Failed : HTTP error code : "
				+ conn.getResponseCode() + " "
                                + conn.getResponseMessage()
                                                   );
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		System.out.println("==Output from Server .... \n");
                
                PrintWriter out = response.getWriter();
                
		while ((output = br.readLine()) != null) {
			System.out.println(output);
                        out.println(output);
		}
                
		conn.disconnect();
                                               

	  } catch (MalformedURLException e) {
		e.printStackTrace();
	  } catch (IOException e) {
		e.printStackTrace();
          }
        
        
    } 
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    } 
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    } 
}
