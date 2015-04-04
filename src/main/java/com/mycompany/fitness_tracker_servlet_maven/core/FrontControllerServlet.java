/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;


import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This is the servlet that routes all client requests to the appropriate resource,
 * it also attaches a jsessionid to the request object if there is a jsessionid.
 * Certain servlets require additional commands e.g /AuthenticationServlet/mobileLoginPage
 * as those servlets will serve both mobile and desktop clients and take appropriate action
 * @author max
 */
@WebServlet(name = "FrontControllerServlet", urlPatterns =
{
    "/FrontControllerServlet/*"
})
public class FrontControllerServlet extends HttpServlet
{
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
        System.out.println("FrontController: executing");
        System.out.println("FrontController: current request URI = " + request.getRequestURI());
        String currentRequestString = ServletRequestGetter.getRequest(request);
        RequestDispatcher rd;
        System.out.println("FrontController: current request = " + currentRequestString);
        
        
        //Request routing
        if (currentRequestString.equals(ClientAPI.getLoginPageRequest()))
        {
            System.out.println("FrontController: login page request");
            rd = request.getRequestDispatcher("/LoginPageRequestServlet/" + currentRequestString);
            rd.forward(request, response);
        }
        else if (currentRequestString.equals(ClientAPI.getLogoutRequest()))
        {
            System.out.println("FrontController: logout request");
            rd = request.getRequestDispatcher("/LogoutServlet/" + currentRequestString);
            rd.forward(request, response);
        }
        else if (currentRequestString.equals(ClientAPI.getLoginRequest()))
        {
            System.out.println("FrontController: authentication request");
            rd = request.getRequestDispatcher("/AuthenticationServlet/" + currentRequestString);
            rd.forward(request, response);
        }
        else if (currentRequestString.equals(ClientAPI.getMainPageRequest()))
        {
            System.out.println("FrontController: main page request");
            rd = request.getRequestDispatcher("/MainPageServlet");
            rd.forward(request, response);
        }
        else if (currentRequestString.equals(ClientAPI.getSessionPlaceholderRequest()))
        {
            System.out.println("FrontController: session placeholder page request");
            rd = request.getRequestDispatcher("/SessionPlaceholderServlet");
            rd.forward(request, response);
        }
        else if (currentRequestString.equals(ClientAPI.getCreateAccountRequest()))
        {
            System.out.println("FrontController: create account request");
            rd = request.getRequestDispatcher("/CreateAccountServlet");
            rd.forward(request, response);
        }
        else
        {
            response.sendRedirect("/fitness_tracker_servlet_maven/invalid.html");
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

