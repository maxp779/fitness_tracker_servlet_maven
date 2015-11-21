/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import java.io.IOException;
import java.io.PrintWriter;
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
 *
 * @author max
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns =
{
    "/ChangePasswordServlet"
})
public class ChangePasswordServlet extends HttpServlet
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
        ServletContext sc = this.getServletContext();
        HttpSession session = request.getSession(false);

        //user cannot login, has likely forgotten password
        if (session == null)
        {
            System.out.println("ChangePasswordServlet: user is not logged in, has forgotten password");
            String email = request.getParameter("email");
            String newPassword = request.getParameter("password");
            String newHashedPassword = Security.hashPassword(newPassword);

            boolean success = DatabaseAccess.changePassword(email, newHashedPassword);

            if (success)
            {
                System.out.println("ChangePasswordServlet password changed successfully for user with email " + email);
                RequestDispatcher rd = sc.getRequestDispatcher("/" + GlobalValues.getWebPagesDirectory() + "/" + GlobalValues.getLoginPage());
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success\" role=\"alert\">Password for " + email + " has been changed successfully.</div>");
                rd.include(request, response);
            } else
            {
                System.out.println("ChangePasswordServlet password change unsuccessful for user with email " + email);
                RequestDispatcher rd = sc.getRequestDispatcher("/" + GlobalValues.getWebPagesDirectory() + "/" + GlobalValues.getLoginPage());
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger\" role=\"alert\">An error occurred, please try again.</div>");
                rd.include(request, response);
            }
        } else //user is logged in, wants to change password from the settings page
        {
            System.out.println("ChangePasswordServlet: user is logged in and wants to change password");
            //String id_user = (String) request.getSession().getAttribute("id_user");

            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("password");
            
            if(Authorization.isCurrentUserAuthorized(oldPassword, request))
            {
                String email = (String) session.getAttribute("email");
                String newHashedPassword = Security.hashPassword(newPassword);
                DatabaseAccess.changePassword(email, newHashedPassword);
                
                RequestDispatcher rd = sc.getRequestDispatcher("/" + GlobalValues.getWebPagesDirectory() + "/" + GlobalValues.getSettingsPage());
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-success\" role=\"alert\">Password changed successfully.</div>");
                rd.include(request, response);
            }
            else
            {
                RequestDispatcher rd = sc.getRequestDispatcher("/" + GlobalValues.getWebPagesDirectory() + "/" + GlobalValues.getSettingsPage());
                PrintWriter out = response.getWriter();
                out.println("<div class=\"alert alert-danger\" role=\"alert\">Incorrect password.</div>");
                rd.include(request, response);
            }
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
