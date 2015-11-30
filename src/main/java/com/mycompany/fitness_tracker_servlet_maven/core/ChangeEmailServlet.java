/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

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
 *
 * @author max
 */
@WebServlet(name = "ChangeEmailServlet", urlPatterns =
{
    "/ChangeEmailServlet"
})
public class ChangeEmailServlet extends HttpServlet
{

    private static final String emailChanged = "<div class=\"alert alert-success\" role=\"alert\">Email changed successfully.</div>";
    private static final String emailChangeFailed = "<div class=\"alert alert-danger\" role=\"alert\">Email change request failed.</div>";
    private static final String emailAlreadyExists = "<div class=\"alert alert-danger\" role=\"alert\">Email already exists!</div>";
    private static final String incorrectPassword = "<div class=\"alert alert-danger\" role=\"alert\">Incorrect password.</div>";

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
        HttpSession session = request.getSession();
        String id_user = (String) session.getAttribute("id_user");
        String password = request.getParameter("password");

        if (!Authorization.isCurrentUserAuthorized(password, id_user))
        {
            writeOutput(request, response, incorrectPassword);
            return;
        }

        String newEmail = request.getParameter("newEmail");
        if (DatabaseAccess.userAlreadyExistsCheckEmail(newEmail))
        {
            writeOutput(request, response, emailAlreadyExists);
            return;
        }

        if (DatabaseAccess.changeEmail(newEmail, id_user))
        {
            writeOutput(request, response, emailChanged);

        }
        else
        {
            writeOutput(request, response, emailChangeFailed);
        }
    }

    private void writeOutput(HttpServletRequest request, HttpServletResponse response, String output) throws ServletException, IOException
    {
        ServletContext servletContext = this.getServletContext();
        RequestDispatcher rd = servletContext.getRequestDispatcher("/" + GlobalValues.getWebPagesDirectory() + "/" + GlobalValues.getSettingsPage());
        PrintWriter out = response.getWriter();
        out.println(output);
        rd.include(request, response);
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
