/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.controllerservlets;

import com.mycompany.fitness_tracker_servlet_maven.core.StandardOutputObject;
import com.mycompany.fitness_tracker_servlet_maven.core.UserObject;
import com.mycompany.fitness_tracker_servlet_maven.database.DatabaseAccess;
import com.mycompany.fitness_tracker_servlet_maven.serverAPI.ErrorCode;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
@WebServlet(name = "GetViewableAttributesListServlet", urlPatterns =
{
    "/GetViewableAttributesListServlet"
})
public class GetViewableAttributesListServlet extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(GetViewableAttributesListServlet.class);

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
        UserObject currentUser = (UserObject) request.getSession().getAttribute("user");
        Map viewableAttributesMap = DatabaseAccess.getViewableAttributesList(currentUser.getId_user());
        boolean success = (viewableAttributesMap != null);
        StandardOutputObject outputObject = new StandardOutputObject();
        outputObject.setSuccess(success);
        if (success)
        {
            outputObject.setData(viewableAttributesMap);
            writeOutput(response, outputObject);
        } else
        {
            outputObject.setErrorCode(ErrorCode.GET_VIEWABLE_ATTRIBUTES_FAILED);
            writeOutput(response, outputObject);
        }
    }

    private void writeOutput(HttpServletResponse response, StandardOutputObject outputObject)
    {
        log.trace("writeOutput()");
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
