/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.AJAXServlets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.mycompany.fitness_tracker_servlet_maven.core.DatabaseAccess;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author max
 */
@WebServlet(name = "AJAX_AddCustomFood", urlPatterns =
{
    "/AJAX_AddCustomFood"
})
public class AJAX_AddCustomFood extends HttpServlet
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
        System.out.println("AJAX_AddCustomFood executing: " + request.getRequestURL());
        boolean output = false;
        Integer id_user = (Integer) request.getSession().getAttribute("id_user");

        //get request data, should be a string with json formatting
        //JsonReader jsonReader = new JsonReader(request.getReader());
        BufferedReader reader = request.getReader();
        StringBuilder buffer = new StringBuilder();
        String currentLine = "";

        //reader.readLine() is within the while head to avoid "null" being
        //appended on at the end, this happens if it is in the body
        while ((currentLine = reader.readLine()) != null)
        {
            buffer.append(currentLine);
        }
        String jsonString = buffer.toString();
        System.out.println("AJAX_AddCustomFood string of food: " + jsonString);
        //parse string into json object and add the id_user
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonString);
        //jsonObject.addProperty("id_user", userID);

        System.out.println("AJAX_AddCustomFood adding food: " + jsonObject);

        //execute database command and send response to client
        output = DatabaseAccess.addCustomFood(jsonObject, id_user);
        PrintWriter writer = response.getWriter();
        writer.print(output);
        writer.close();
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
