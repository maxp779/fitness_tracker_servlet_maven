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
@WebServlet(name = "DeleteAccountServlet", urlPatterns =
{
    "/DeleteAccountServlet"
})
public class DeleteAccountServlet extends HttpServlet
{

    private static final String DELETE_ACCOUNT_SUCCESS = "<div class=\"alert alert-success\" role=\"alert\">The account for #EMAIL has been deleted successfully.</div>";
    private static final String DELETE_ACCOUNT_FAIL = "<div class=\"alert alert-danger\" role=\"alert\">An error occurred, please try again.</div>";
    private static final String INCORRECT_PASSWORD = "<div class=\"alert alert-danger\" role=\"alert\">Incorrect password.</div>";

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
        String password = request.getParameter("deleteAccountPassword");
        boolean authorized = Authorization.isCurrentUserAuthorized(password, id_user);
        boolean success;
        
        //THIS IF CONDITION CAN BE DELETED WHEN OUT OF TESTING! It is only to prevent test account deletion
        if ("30".equals(id_user))
        {
            ServletContext servletContext = this.getServletContext();
            PrintWriter out = response.getWriter();
            out.println("<div class=\"alert alert-danger\" role=\"alert\">Sorry, cant let you delete the test account ;)</div>");
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/" + GlobalValues.getWEB_PAGES_DIRECTORY() + "/" + GlobalValues.getSETTINGS_PAGE());
            requestDispatcher.include(request, response);
            return;
        }

        if (authorized)
        {
            success = DatabaseAccess.deleteAccount(id_user);
        } else
        {
            writeOutput(request, response, INCORRECT_PASSWORD, false);
            return;
        }

        if (success)
        {
            String email = (String) session.getAttribute("email");
            writeOutput(request, response, DELETE_ACCOUNT_SUCCESS.replaceAll("#EMAIL", email), true);
        } else
        {
            writeOutput(request, response, DELETE_ACCOUNT_FAIL, false);
        }

    }

    private void writeOutput(HttpServletRequest request, HttpServletResponse response, String output, boolean accountDeleted) throws IOException, ServletException
    {
        ServletContext servletContext = this.getServletContext();
        RequestDispatcher requestDispatcher;

        if (accountDeleted)
        {
            requestDispatcher = servletContext.getRequestDispatcher("/" + GlobalValues.getWEB_PAGES_DIRECTORY() + "/" + GlobalValues.getLOGIN_PAGE_FOLDER()+ "/" + GlobalValues.getLOGIN_PAGE());
            SessionManager.httpSessionRemove(request.getSession());
        } else
        {
            requestDispatcher = servletContext.getRequestDispatcher("/" + GlobalValues.getWEB_PAGES_DIRECTORY() + "/" + GlobalValues.getSETTINGS_PAGE_FOLDER()+ "/" + GlobalValues.getSETTINGS_PAGE());
        }

        PrintWriter out = response.getWriter();
        out.println(output);
        requestDispatcher.include(request, response);
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
