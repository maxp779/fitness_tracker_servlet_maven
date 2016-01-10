/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.AJAXServlets;

import com.mycompany.fitness_tracker_servlet_maven.core.ServletUtilities;
import com.mycompany.fitness_tracker_servlet_maven.core.DatabaseAccess;
import com.mycompany.fitness_tracker_servlet_maven.core.ErrorCodes;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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
@WebServlet(name = "AJAX_ModifySelectedAttributes", urlPatterns =
{
    "/AJAX_ModifySelectedAttributes"
})
public class AJAX_ModifySelectedAttributes extends HttpServlet
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
        System.out.println("AJAX_ModifySelectedAttributes executing: " + request.getRequestURL());

        String jsonString = ServletUtilities.getRequestData(request);
        Map<String,String> selectedAttributesMap = ServletUtilities.convertJsonStringToMap(jsonString);
        String id_user = (String) request.getSession().getAttribute("id_user");
        selectedAttributesMap.put("id_user", id_user);

        //execute database command and send response to client
        boolean success = DatabaseAccess.modifySelectedAttributes(selectedAttributesMap, id_user);
        
        Map<String,String> outputMap = new HashMap<>();
        if(success)
        {
            outputMap.put("success", "true");
            writeOutput(response, outputMap);
        }
        else
        {
            outputMap.put("success", "false");
            outputMap.put("errorCode", ErrorCodes.getUPDATE_ATTRIBUTES_FAILED());
            writeOutput(response, outputMap);
        }
    }
    
    private void writeOutput(HttpServletResponse response, Map<String,String> outputMap) throws IOException
    {
        String JSONString = ServletUtilities.convertMapToJSONString(outputMap);
        try (PrintWriter writer = response.getWriter())
        {
            writer.write(JSONString);
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
