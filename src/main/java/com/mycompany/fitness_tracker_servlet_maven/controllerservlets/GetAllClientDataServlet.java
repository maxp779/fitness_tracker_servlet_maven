/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.controllerservlets;

import com.mycompany.fitness_tracker_servlet_maven.core.ServletUtilities;
import com.mycompany.fitness_tracker_servlet_maven.core.StandardOutputObject;
import com.mycompany.fitness_tracker_servlet_maven.core.UserObject;
import com.mycompany.fitness_tracker_servlet_maven.database.DatabaseAccess;
import com.mycompany.fitness_tracker_servlet_maven.globalvalues.GlobalValues;
import com.mycompany.fitness_tracker_servlet_maven.serverAPI.ErrorCode;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.HashMap;
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
@WebServlet(name = "GetAllClientDataServlet", urlPatterns =
{
    "/GetAllClientDataServlet/*"
})
public class GetAllClientDataServlet extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(EditCustomFoodServlet.class);

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

        String UnixTime = request.getParameter("UnixTime");
        log.debug("UnixTime:" + UnixTime);
        LocalDateTime inputTime = LocalDateTime.ofEpochSecond(Long.parseLong(UnixTime), 0, ZoneOffset.UTC);

        UserObject currentUser = ServletUtilities.getCurrentUser(request);

        /**
         * This is purely for testing purposes user 30 is the testing account
         * test@test.com
         */
        if (currentUser == null)
        {
            currentUser = new UserObject();
            currentUser.setId_user("30");
        }

        List customFoodList = DatabaseAccess.getCustomFoodList(currentUser.getId_user());
        Map<String, String> friendlyNamesMap = GlobalValues.getFRIENDLY_VALUES_MAP();
        Map viewableAttributesMap = DatabaseAccess.getViewableAttributesList(currentUser.getId_user());
        Map userStatsMap = DatabaseAccess.getUserStats(currentUser.getId_user());
        List eatenFoodList = DatabaseAccess.getEatenFoodList(currentUser.getId_user(), inputTime);

        boolean success = (customFoodList != null
                && friendlyNamesMap != null
                && viewableAttributesMap != null
                && userStatsMap != null
                && eatenFoodList != null);

        StandardOutputObject outputObject = new StandardOutputObject();
        outputObject.setSuccess(success);

        if (success)
        {
            Map<String, Object> data = new HashMap<>();
            data.put("customFoods", customFoodList);
            data.put("friendlyNames", friendlyNamesMap);
            data.put("viewableAttributes", viewableAttributesMap);
            data.put("userStats", userStatsMap);
            data.put("eatenFoods", eatenFoodList);
            outputObject.setData(data);
            writeOutput(response, outputObject);

        } else
        {
            outputObject.setErrorCode(ErrorCode.GET_ALL_CLIENT_DATA_FAILED);
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
