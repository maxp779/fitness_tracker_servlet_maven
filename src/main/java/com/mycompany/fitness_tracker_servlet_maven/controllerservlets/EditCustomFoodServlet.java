/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.controllerservlets;

import com.mycompany.fitness_tracker_servlet_maven.core.ServletUtilities;
import com.mycompany.fitness_tracker_servlet_maven.core.StandardOutputObject;
import com.mycompany.fitness_tracker_servlet_maven.database.DatabaseAccess;
import com.mycompany.fitness_tracker_servlet_maven.serverAPI.ErrorCode;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "EditCustomFoodServlet", urlPatterns =
{
    "/EditCustomFoodServlet"
})
public class EditCustomFoodServlet extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(EditCustomFoodServlet.class);

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
        String id_user = (String) request.getSession().getAttribute("id_user");
        String customFoodJSONString = ServletUtilities.getPOSTRequestJSONString(request);
        log.debug(customFoodJSONString);
        Map<String, String> customFoodMap = ServletUtilities.convertJSONStringToMap(customFoodJSONString);
        //customFoodMap.put("id_user", id_user); <-- this could break this method, I do not know why this line is needed so I commented it out
        boolean success = DatabaseAccess.editCustomFood(customFoodMap, id_user);

        StandardOutputObject outputObject = new StandardOutputObject();
        outputObject.setSuccess(success);
        outputObject.setData(customFoodJSONString);

        if (success)
        {
            log.info("custom food edited successfully");
            writeOutput(response, outputObject);
        } else
        {
            outputObject.setErrorCode(ErrorCode.EDIT_CUSTOM_FOOD_FAILED);
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
