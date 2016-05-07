/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.controllerservlets;

import com.mycompany.fitness_tracker_servlet_maven.core.StandardOutputObject;
import com.mycompany.fitness_tracker_servlet_maven.database.DatabaseAccess;
import com.mycompany.fitness_tracker_servlet_maven.serverAPI.ErrorCode;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
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
@WebServlet(name = "GetEatenFoodListServlet", urlPatterns =
{
    "/GetEatenFoodListServlet"
})
public class GetEatenFoodListServlet extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(GetEatenFoodListServlet.class);

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
        log.trace("doGet");

        //format query string correctly so it can be a valid JSON
        //String queryString = request.getQueryString();
        String UNIXTime = request.getParameter("UNIXtime");
        log.debug(UNIXTime);
        //queryString = queryString.replaceAll("%22", "\"");
        //Map<String, String> queryMap = ServletUtilities.convertJSONStringToMap(queryString);

        //UNIXTime is the 
        //String UNIXTime = queryMap.get("UNIXtime");
        Timestamp timestamp = new Timestamp(Long.parseLong(UNIXTime));
        String id_user = (String) request.getSession().getAttribute("id_user");
        System.out.println("Getting eaten foods for user " + id_user + " for date " + timestamp.toString() + " UNIX time:" + UNIXTime);

        List eatenFoodList = DatabaseAccess.getEatenFoodList(id_user, timestamp);
        StandardOutputObject outputObject = new StandardOutputObject();
        boolean success = (eatenFoodList != null);
        outputObject.setSuccess(success);

        if (success)
        {
            outputObject.setData(eatenFoodList);
            writeOutput(response, outputObject);

        } else
        {
            outputObject.setErrorCode(ErrorCode.GET_EATEN_FOOD_LIST_FAILED);
            writeOutput(response, outputObject);
        }
    }

    private void writeOutput(HttpServletResponse response, StandardOutputObject outputObject)
    {
        log.trace("writeOutput");
        response.setContentType("application/json");
        String outputJSON = outputObject.getJSONString();
        log.debug(outputJSON);
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
