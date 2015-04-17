/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import com.mycompany.fitness_tracker_servlet_maven.core.GlobalValues;
import com.mycompany.fitness_tracker_servlet_maven.core.ServletRequestFormatter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *This servlet determines whether to send the client to the mobile login page
 * or the desktop login page, if they are already logged in it will send the client 
 * to the appropriate main page
 * @author max
 */
@WebServlet(name = "LoginPageServlet", urlPatterns =
{
    "/LoginPageServlet/*"
    //,"/index.html"
    //comment out the above line if ditching web.xml welcome file configuration,
    //without web.xml, index.html is the default initial resource called and this 
    //servlet is the one that should respond to such a request
})
public class LoginPageServlet extends HttpServlet
{

    /**
     * This servlet is called when the client tries to access a login page, the
     * servlet will determine which login page the client should see, either the mobile 
     * or desktop login page. Also it will redirect the client accordingly should they already
     * be logged in.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {   
        System.out.println("LoginPageRequestServlet: executing");
        HttpSession session = request.getSession(false);
        ServletContext sc = this.getServletContext();
        
        //TEST USER REMEMBER TO DELETE!!
        //UserObject testUser = new UserObject("max", "power", "maxp779@gmail.com", "sun");
        //UserAccounts.getUserMap().put(testUser.getEmail(), testUser);
        
        //if no session exists i.e. user has to login and is referred to a login page
        if(session == null)
        {
            response.sendRedirect(sc.getContextPath() +"/"+ GlobalValues.getWebPagesDirectory() +"/"+ GlobalValues.getLoginPage());
        }
        else if(session.getAttribute("username") == null) //if no username exists i.e. session is in inconsistent state and user must log in and is referred to a login page
        {
            response.sendRedirect(sc.getContextPath() +"/"+ GlobalValues.getWebPagesDirectory() +"/"+ GlobalValues.getLoginPage());
        }
        else //if session is valid user is already logged in, puts them back at the main page
        {
            RequestDispatcher rd;
            rd = request.getRequestDispatcher("/MainPageServlet");
            rd.forward(request, response);
            
            //response.sendRedirect(sc.getContextPath() +"/"+ GlobalValues.getWebPagesDirectory() +"/"+ GlobalValues.getMainPage());
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
            throws ServletException, IOException
    {
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
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
