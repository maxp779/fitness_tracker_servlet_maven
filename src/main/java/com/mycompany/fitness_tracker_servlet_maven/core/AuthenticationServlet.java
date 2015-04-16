/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
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
                    //session.setMaxInactiveInterval(GlobalValues.getMaxInactiveInterval());
                    //String encodedURL = response.encodeRedirectURL(sc.getContextPath() +"/"+ GlobalValues.getWebPagesDirectory() +"/"+ GlobalValues.getMainPage());
                    //response.sendRedirect(sc.getContextPath() +"/"+ GlobalValues.getWebPagesDirectory() +"/"+ GlobalValues.getLoginSuccessReferrer());
                    
                    
                    RequestDispatcher rd;
                    rd = request.getRequestDispatcher("/MainPageServlet");
                    rd.forward(request, response);
                }
                else
                {
                    System.out.println("AuthenticationServlet: wrong password");
                    //if wrong password, back to login page
                    //response.sendRedirect(sc.getContextPath() +"/"+ GlobalValues.getFirstLoginServlet());
                    
                    
                    
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
                //response.sendRedirect(sc.getContextPath() +"/"+ GlobalValues.getFirstLoginServlet());
                
                RequestDispatcher rd = sc.getRequestDispatcher("/"+GlobalValues.getWebPagesDirectory()+ "/" + GlobalValues.getLoginPage());
                PrintWriter out= response.getWriter();
                out.println("<font color=red>Invalid email or password</font>");
                rd.include(request, response);
            }
        
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
