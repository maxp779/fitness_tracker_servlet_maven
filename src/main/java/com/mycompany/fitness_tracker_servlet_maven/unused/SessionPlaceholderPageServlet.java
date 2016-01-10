/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.unused;

import com.mycompany.fitness_tracker_servlet_maven.core.GlobalValues;
import com.mycompany.fitness_tracker_servlet_maven.core.SessionManager;
import java.io.IOException;
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
@WebServlet(name = "SessionPlaceholderPageServlet", urlPatterns =
{
    "/SessionPlaceholderPageServlet"
})
public class SessionPlaceholderPageServlet extends HttpServlet
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
        System.out.println("SessionPlaceholderServlet executing: " + request.getRequestURL());
        ServletContext sc = request.getServletContext();   
        //String jsessionid = (String) request.getAttribute("jsessionid");
//        boolean sessionValid = SessionManager.sessionValidate(request);
//        //if session invalid, redirect back to login page
//        if(!sessionValid)
//        {
//            response.sendRedirect(sc.getContextPath()+"/"+ GlobalValues.getWebPagesDirectory() +"/"+ GlobalValues.getLoginPage());
//        }
//        else //if session valid execute normalls
//        {



    //        if(jsessionid == null)
    //        {
    //            RequestDispatcher view = request.getRequestDispatcher(GlobalValues.getDesktopPagesDirectory() +"/"+ GlobalValues.getDesktopSessionPlaceholderPage());
    //            view.forward(request, response);
    //        }
    //        else
    //        {
    //            RequestDispatcher view = request.getRequestDispatcher(GlobalValues.getDesktopPagesDirectory() +"/"+ GlobalValues.getDesktopSessionPlaceholderPage() + request.getAttribute("jsessionid"));
    //            view.forward(request, response);
    //        }
    //        boolean sessionValid = SessionManager.sessionValidate(request);
    //        if(!sessionValid) //path for invalid session
    //        {
    //            response.sendRedirect(sc.getContextPath() +"/"+ GlobalValues.getFirstLoginServlet());
    //        }
    //        else //path for valid session
    //        {
    //            response.sendRedirect(sc.getContextPath() +"/"+ GlobalValues.getDesktopPagesDirectory() +"/"+ GlobalValues.getDesktopSessionPlaceholderPage()+ request.getAttribute("jsessionid"));
    //        }
        
//        String encodedURL = response.encodeRedirectURL(sc.getContextPath() +"/"+ GlobalValues.getWEB_PAGES_DIRECTORY() +"/"+ GlobalValues.getSessionPlaceholderPage());
//        response.sendRedirect(encodedURL);

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
