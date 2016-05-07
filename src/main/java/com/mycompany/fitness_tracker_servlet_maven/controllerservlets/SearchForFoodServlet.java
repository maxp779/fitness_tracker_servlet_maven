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
@WebServlet(name = "SearchForFoodServlet", urlPatterns =
{
    "/SearchForFoodServlet"
})
public class SearchForFoodServlet extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(SearchForFoodServlet.class);

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
//        String queryString = request.getQueryString();
//        queryString = queryString.replaceAll("%22", "\"");
//        queryString = queryString.replaceAll("%20", " ");
//        System.out.println("SearchForFoodServlet: request data: " + queryString);

        String searchInput = request.getParameter("searchInput");
        log.debug("searchInput");

        //Map<String, String> JSONMap = ServletUtilities.convertJSONStringToMap(queryString);
        //String searchInput = JSONMap.get("searchInput");
        List matchingFoods = DatabaseAccess.searchForFood(searchInput);
        boolean success = (matchingFoods != null);
        StandardOutputObject outputObject = new StandardOutputObject();
        outputObject.setSuccess(success);
        if (success)
        {
            outputObject.setData(matchingFoods);
            writeOutput(response, outputObject);
        } else
        {
            outputObject.setErrorCode(ErrorCode.SEARCH_FOR_FOOD_FAILED);
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
