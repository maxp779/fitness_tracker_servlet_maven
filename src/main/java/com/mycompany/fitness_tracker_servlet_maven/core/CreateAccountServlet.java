/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author max
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns =
{
    "/CreateAccountServlet"
})
public class CreateAccountServlet extends HttpServlet
{

    private static final String accountCreated = "<div class=\"alert alert-success\" role=\"alert\">Account created for #EMAIL </div>";
    private static final String accountAlreadyExists = "<div class=\"alert alert-danger\" role=\"alert\">An account for #EMAIL already exists. Please try a different email.</div>";
    private static final String passwordTooShort = "<div class=\"alert alert-danger\" role=\"alert\">Password must be at least 6 characters long.</div>";

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
        System.out.println("CreateAccountServlet: executing");

        String accountDetails = ServletUtilities.getRequestData(request);
        Map<String, String> accountDetailsMap = ServletUtilities.convertJSONFormDataToMap(accountDetails);

        String newAccountPassword = accountDetailsMap.get("password");
        //if somehow the user has managed to submit a request for a new account with a password < 6 characters this stops them
        //and issues a message, javascript on the front end should prevent this from ever happening, if the user disables
        //javascript the form shouldnt even submit but better safe than sorry
        if (newAccountPassword.length() < 6)
        {
            System.out.println("CreateAccountServlet: password is of insufficient length");

            writeOutput(response,ErrorCodes.getPASSWORD_TOO_SHORT());
            return;
        }

        String newAccountEmail = accountDetailsMap.get("email");
        String hashedPassword = Security.hashPassword(newAccountPassword);
        boolean userAdded = DatabaseAccess.addUser(newAccountEmail, hashedPassword);
        if (userAdded)
        {
            System.out.println("CreateAccountServlet: account created for " + newAccountEmail);
            writeOutput(response,"true");

        } else
        {
            System.out.println("CreateAccountServlet: account already exists, no action taken");
            writeOutput(response,ErrorCodes.getACCOUNT_ALREADY_EXISTS());
        }

        //String newAccountPassword = request.getParameter("password");
        //if somehow the user has managed to submit a request for a new account with a password < 6 characters this stops them
        //and issues a message, javascript on the front end should prevent this from ever happening, if the user disables
        //javascript the form shouldnt submit anyway but better safe than sorry
//        if (newAccountPassword.length() < 6)
//        {
//            System.out.println("CreateAccountServlet: password is of insufficient length");
//
//            writeOutput(request, response, passwordTooShort, false);
//            return;
//        }
//        String newAccountEmail = request.getParameter("email");
//        String hashedPassword = Security.hashPassword(newAccountPassword);
//        boolean userAdded = DatabaseAccess.addUser(newAccountEmail, hashedPassword);
//        if (userAdded)
//        {
//            System.out.println("CreateAccountServlet: account created for " + newAccountEmail);
//            writeOutput(request, response, accountCreated.replaceAll("#EMAIL", newAccountEmail), true);
//
//        } else
//        {
//            System.out.println("CreateAccountServlet: account already exists, no action taken");
//            writeOutput(request, response, accountAlreadyExists.replaceAll("#EMAIL", newAccountEmail), false);
//        }
    }

    private void writeOutput(HttpServletResponse response, String output) throws IOException
    {
        try (PrintWriter writer = response.getWriter())
        {
            writer.write(output);
        }
    }

//    private void writeOutput(HttpServletRequest request, HttpServletResponse response, String output, boolean accountCreationSuccess) throws ServletException, IOException
//    {
//        ServletContext servletContext = this.getServletContext();
//        RequestDispatcher requestDispatcher;
//        if (accountCreationSuccess)
//        {
//            requestDispatcher = servletContext.getRequestDispatcher("/" + GlobalValues.getWEB_PAGES_DIRECTORY() + "/" + GlobalValues.getLOGIN_PAGE());
//        } else
//        {
//            requestDispatcher = servletContext.getRequestDispatcher("/" + GlobalValues.getWEB_PAGES_DIRECTORY() + "/" + GlobalValues.getCREATE_ACCOUNT_PAGE());
//        }
//        PrintWriter out = response.getWriter();
//        out.println(output);
//        requestDispatcher.include(request, response);
//    }
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
