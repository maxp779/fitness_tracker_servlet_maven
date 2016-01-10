/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.AJAXServlets;

import com.mycompany.fitness_tracker_servlet_maven.core.ServletUtilities;
import com.mycompany.fitness_tracker_servlet_maven.core.DatabaseAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author max
 */
@WebServlet(name = "AJAX_GetEatenFoodList", urlPatterns =
{
    "/AJAX_GetEatenFoodList"
})
public class AJAX_GetEatenFoodList extends HttpServlet
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
        System.out.println("AJAX_GetEatenFoodList executing: " + request.getRequestURL());
        System.out.println("AJAX_GetEatenFoodList query string: " + request.getQueryString());
        
        //format query string correctly so it can be a valid JSON
        String queryString = request.getQueryString();
        queryString = queryString.replaceAll("%22", "\"");
        Map<String, String> queryMap = ServletUtilities.convertJsonStringToMap(queryString);
        
        //UNIXTime is the 
        String UNIXTime = queryMap.get("UNIXtime");
        Timestamp timestamp = new Timestamp(Long.parseLong(UNIXTime));
        String id_user = (String) request.getSession().getAttribute("id_user");
        System.out.println("Getting eaten foods for user " + id_user + " for date " + timestamp.toString() + " UNIX time:" + UNIXTime);

        String JSONString = DatabaseAccess.getEatenFoodList(id_user, timestamp);
        System.out.println("AJAX_GetEatenFoodList sending JSON object: " + JSONString);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter();)
        {
            out.print(JSONString);
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
