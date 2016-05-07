/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.controllerservlets;

import com.mycompany.fitness_tracker_servlet_maven.serverAPI.ErrorCode;
import com.mycompany.fitness_tracker_servlet_maven.core.ServletUtilities;
import com.mycompany.fitness_tracker_servlet_maven.core.StandardOutputObject;
import com.mycompany.fitness_tracker_servlet_maven.database.DatabaseAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
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
@WebServlet(name = "CreateCustomFoodServlet", urlPatterns =
{
    "/CreateCustomFoodServlet"
})
public class CreateCustomFoodServlet extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(CreateCustomFoodServlet.class);

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
        log.trace("doPost");
        String requestDetails = ServletUtilities.getPOSTRequestJSONString(request);
        Map<String, String> customFoodMap = ServletUtilities.convertJSONFormDataToMap(requestDetails);
        String id_user = (String) request.getSession().getAttribute("id_user");
        //customFoodMap.put("id_user", id_user);

        boolean success = false;
        try
        {
            success = DatabaseAccess.createCustomFood(customFoodMap, id_user);
        } catch (SQLException ex)
        {
            log.error("DatabaseAccess.createCustomFood failed", ex);
        }

        StandardOutputObject outputObject = new StandardOutputObject();
        outputObject.setSuccess(success);

        if (success)
        {
            log.info("custom food created successfully");
            outputObject.setData(customFoodMap);
            writeOutput(response, outputObject);
        } else
        {
            outputObject.setErrorCode(ErrorCode.ADD_CUSTOM_FOOD_FAILED);
            writeOutput(response, outputObject);
        }
    }

    private void writeOutput(HttpServletResponse response, StandardOutputObject outputObject)
    {
        log.trace("writeOutput");
        String outputJSON = outputObject.getJSONString();
        log.debug(outputJSON);
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter())
        {
            out.print(outputJSON);
        } catch (IOException ex)
        {
            log.error(ErrorCode.SENDING_CLIENT_DATA_FAILED.toString(), ex);
        }
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
