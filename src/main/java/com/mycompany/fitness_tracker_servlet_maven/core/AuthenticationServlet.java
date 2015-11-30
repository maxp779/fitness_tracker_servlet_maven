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
 * This Servlet handles user authentication, it is called when the user has
 * filled in and submitted their details on either the desktop or mobile login
 * pages. This servlet then checks their account credentials and redirects them
 * accordingly.
 *
 * @author max
 */
@WebServlet(name = "AuthenticationServlet", urlPatterns =
{
    "/AuthenticationServlet/*"
})
public class AuthenticationServlet extends HttpServlet
{

    private static final String accountDosentExist = "<div class=\"alert alert-danger\" role=\"alert\">Invalid email or password</div>";
    private static final String wrongPassword = "<div class=\"alert alert-danger\" role=\"alert\">Invalid email or password</div>";
    private static final String forwardRequestTo = "/MainPageServlet";

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
        Map<String, String> userCredentials = (HashMap) DatabaseAccess.getUserCredentialsFromEmail(loginAttemptEmail);

        if (userCredentials == null)
        {
            System.out.println("AuthenticationServlet: account dosent exist");
            writeOutput(request, response, accountDosentExist);
            return;
        }

        String storedHashedPassword = userCredentials.get("hashedPassword");
        String loginAttemptPassword = request.getParameter("password");
        if (!Security.passwordMatch(loginAttemptPassword, storedHashedPassword))
        {
            System.out.println("AuthenticationServlet: password incorrect");
            writeOutput(request, response, wrongPassword);

        } else
        {
            System.out.println("AuthenticationServlet: password correct");
            createNewSession(request, userCredentials);
            forwardRequest(request, response);
        }
    }

    private void writeOutput(HttpServletRequest request, HttpServletResponse response, String output) throws IOException, ServletException
    {
        ServletContext servletContext = this.getServletContext();
        RequestDispatcher rd = servletContext.getRequestDispatcher("/" + GlobalValues.getWebPagesDirectory() + "/" + GlobalValues.getLoginPage());
        PrintWriter out = response.getWriter();
        out.println(output);
        rd.include(request, response);
    }

    private void createNewSession(HttpServletRequest request, Map userCredentials) throws ServletException, IOException
    {
        HttpSession session = request.getSession(true);
        session.setAttribute(ClientAPI.getClientRequestIdentifier(), userCredentials.get("email"));
        session.setAttribute("id_user", userCredentials.get("id_user"));
        session.setMaxInactiveInterval(GlobalValues.getMaxInactiveInterval());
    }

    private void forwardRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forwardRequestTo);
        requestDispatcher.forward(request, response);
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
