/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.webpageservlets;

import com.mycompany.fitness_tracker_servlet_maven.globalvalues.GlobalValues;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author max
 */
@WebServlet(name = "MainPageServlet", urlPatterns =
{
    "/MainPageServlet"
})
public class MainPageServlet extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(MainPageServlet.class);

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
        log.trace("doGet()");
        ServletContext servletContext = this.getServletContext();
        String webPageURL = servletContext.getContextPath()
                + "/"
                + GlobalValues.getWEB_PAGES_DIRECTORY()
                + "/"
                + GlobalValues.getMAIN_PAGE_FOLDER()
                + "/"
                + GlobalValues.getMAIN_PAGE();
        response.sendRedirect(webPageURL);
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
