/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This Servlet handles user authentication, it is called when the user has
 * filled in and submitted their details on either the desktop or mobile login pages.
 * This servlet then checks their account credentials and redirects them accordingly.
 * @author max
 */
@WebServlet(name = "AuthenticationServlet", urlPatterns = {"/AuthenticationServlet/*"})
public class AuthenticationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        System.out.println("AuthenticationServlet: executing");
        String loginAttemptEmail = request.getParameter("email");
        String loginAttemptPassword = request.getParameter("password");

        UserObject currentUser = UserAccounts.getUserMap().get(loginAttemptEmail);
        ServletContext sc = this.getServletContext();
        
            //if the account exists
            if(currentUser != null)
            {
                System.out.println("AuthenticationServlet: account exists");
                //if password is correct
                if(currentUser.getPassword().equals(loginAttemptPassword))
                {
                    System.out.println("AuthenticationServlet: password correct");
                    //create new session
                    HttpSession session = request.getSession(true);
                    session.setAttribute(ClientAPI.getClientRequestIdentifier(), loginAttemptEmail);
                    session.setMaxInactiveInterval(GlobalValues.getMaxInactiveInterval());
                    
                    
                    /**
                     * go to main page
                     * if encodedURL detects cookies are disabled it will add a jsessionid to the end of the response URL
                     * if cookies are enabled it will do nothing, so we check for a jsessionid with the method getJSessionID to see if one needs to be added
                     * to the current valid session pool of jsessionids
                    **/
                    String encodedURL = response.encodeRedirectURL(sc.getContextPath() +"/"+ GlobalValues.getWebPagesDirectory() +"/"+ GlobalValues.getMainPage());
                    System.out.println("AuthenticationServlet: enclodedURL = " + encodedURL);
                    String jsessionid = this.getJsessionid(encodedURL);
                    if(jsessionid != null)
                    {                            
                        Jsessionid jsessionidObject = new Jsessionid(jsessionid, loginAttemptEmail, LocalDateTime.now());  
                        SessionManager.jsessionidAddSession(jsessionid, jsessionidObject);
                    }
                    response.sendRedirect(encodedURL);
                }
                else
                {
                    System.out.println("AuthenticationServlet: wrong password");
                    //if wrong password, back to login page
                    //response.sendRedirect(sc.getContextPath() +"/"+ GlobalValues.getURLLoginPage());
                    
                    
                    
                    RequestDispatcher rd = sc.getRequestDispatcher("/"+GlobalValues.getWebPagesDirectory()+ "/" + GlobalValues.getLoginPage());
                    PrintWriter out= response.getWriter();
                    out.println("<font color=red>Invalid email or password</font>");
                    rd.include(request, response);
                }

            }
            else
            {
                System.out.println("AuthenticationServlet: account dosent exist");
                //if account dosent exist, back to login page
                //response.sendRedirect(sc.getContextPath() +"/"+ GlobalValues.getURLLoginPage());
                
                RequestDispatcher rd = sc.getRequestDispatcher("/"+GlobalValues.getWebPagesDirectory()+ "/" + GlobalValues.getLoginPage());
                PrintWriter out= response.getWriter();
                out.println("<font color=red>Invalid email or password</font>");
                rd.include(request, response);
            }
        
        }
    
    /**
     * Gets the jsessionID from an encoded URL e.g an input of
     * "/fitness_tracker_servlet_maven/desktopPages/desktopMainPage.html;jsessionid=7dbdcd1141959f4ac2a5945a29b8"
     * would return "7dbdcd1141959f4ac2a5945a29b8"
     * @param encodedURL
     * @return a jsessionID in the URL or an empty string if there is none
     */
    public String getJsessionid(String encodedURL)
    {
        String jsessionidSearchParameter = ";jsessionid=";
        int jsessionLocation = encodedURL.indexOf(jsessionidSearchParameter);
        String jsessionid = "";
        if(jsessionLocation != -1)
        {
             jsessionid = encodedURL.substring(jsessionLocation);
        }

        return jsessionid;
    }
        

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
