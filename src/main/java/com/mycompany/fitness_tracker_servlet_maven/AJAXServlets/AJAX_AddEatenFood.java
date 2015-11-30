/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.AJAXServlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mycompany.fitness_tracker_servlet_maven.core.DatabaseAccess;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AJAX_AddEatenFood", urlPatterns =
{
    "/AJAX_AddEatenFood"
})
public class AJAX_AddEatenFood extends HttpServlet
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
        System.out.println("AJAX_AddEatenFood executing: " + request.getRequestURL());
//        boolean output = false;
//        String id_user = (String) request.getSession().getAttribute("id_user");
//        
//        //get request data, should be a string with json formatting
//        //JsonReader jsonReader = new JsonReader(request.getReader());
//        BufferedReader reader = request.getReader();
//        StringBuilder buffer = new StringBuilder();
//        String currentLine = "";
//
//        //reader.readLine() is within the while head to avoid "null" being
//        //appended on at the end, this happens if it is in the body
//        while ((currentLine = reader.readLine()) != null)
//        {
//            buffer.append(currentLine);
//        }
        
        String eatenFoodJSONString = ServletUtilities.getRequestData(request);
        Map<String,String> eatenFoodMap  = ServletUtilities.convertJsonStringToMap(eatenFoodJSONString);
        String id_user = (String) request.getSession().getAttribute("id_user");
        eatenFoodMap.put("id_user", id_user);

        //execute database command and send response to client
        boolean output = DatabaseAccess.addEatenFood(eatenFoodMap, id_user);
        try (PrintWriter writer = response.getWriter())
        {
            writer.print(output);
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
