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
    private boolean userAdded = false;
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
        ServletContext sc = this.getServletContext();

        String newAccountEmail = request.getParameter("inputEmail");
        String newAccountPassword = request.getParameter("inputPassword");
        
        //if somehow the user has managed to submit a request for a new account with a password < 6 characters this stops them
        //and issues a message, javascript on the front end should prevent this from ever happening, if the user disables
        //javascript the form shouldnt submit anyway but better safe than sorry
        if(newAccountPassword.length() < 6)
        {
            RequestDispatcher rd = sc.getRequestDispatcher("/"+GlobalValues.getWebPagesDirectory()+ "/" + GlobalValues.getCreateNewAccountPage());
            PrintWriter out= response.getWriter();
            out.println("<div class=\"alert alert-danger\" role=\"alert\">Password must be at least 6 characters long.</div>");
            rd.forward(request, response);
        }
        else //normal execution with javascript working on the front end to ensure only valid emails and passwords are submitted
        {
//            if(DatabaseAccess.userAlreadyExistsCheck(newAccountEmail))
//            {
//                System.out.println("CreateAccountServlet: account already exists, no action taken");
//                //response.sendRedirect(sc.getContextPath() +"/"+ GlobalValues.getFirstLoginServlet());
//                
//                RequestDispatcher rd = sc.getRequestDispatcher("/"+GlobalValues.getWebPagesDirectory()+ "/" + GlobalValues.getCreateNewAccountPage());
//                PrintWriter out= response.getWriter();
//                out.println("<div class=\"alert alert-danger\" role=\"alert\">An account for "+newAccountEmail+" already exists. Please try a different email.</div>");
//                rd.include(request, response);
//            }
//            else
//            {
                
            boolean userAdded = DatabaseAccess.addUser(newAccountEmail, newAccountPassword);
            if(userAdded)
            {
                System.out.println("CreateAccountServlet: account created for " + newAccountEmail);
                RequestDispatcher rd = sc.getRequestDispatcher("/"+GlobalValues.getWebPagesDirectory()+ "/" + GlobalValues.getLoginPage());
                PrintWriter out= response.getWriter();
                //out.println("<font color=green>Account created for " +newAccountEmail+ " </font>");
                out.println("<div class=\"alert alert-success\" role=\"alert\">Account created for " +newAccountEmail+ "</div>");

                rd.include(request, response);
            }
            else
            {
                System.out.println("CreateAccountServlet: account already exists, no action taken");
                //response.sendRedirect(sc.getContextPath() +"/"+ GlobalValues.getFirstLoginServlet());

                RequestDispatcher rd = sc.getRequestDispatcher("/"+GlobalValues.getWebPagesDirectory()+ "/" + GlobalValues.getCreateNewAccountPage());
                PrintWriter out= response.getWriter();
                out.println("<div class=\"alert alert-danger\" role=\"alert\">An account for "+newAccountEmail+" already exists. Please try a different email.</div>");
                rd.include(request, response);
            }         
                //response.sendRedirect(sc.getContextPath() +"/"+ GlobalValues.getFirstLoginServlet());   
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
