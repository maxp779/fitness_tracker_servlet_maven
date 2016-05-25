/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import com.mycompany.fitness_tracker_servlet_maven.serverAPI.ErrorCode;
import com.mycompany.fitness_tracker_servlet_maven.database.DatabaseAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author max
 */
@WebServlet(name = "ChangeEmailServlet", urlPatterns =
{
    "/ChangeEmailServlet"
})
public class ChangeEmailServlet extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(ChangeEmailServlet.class);

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
            throws ServletException
    {
        log.trace("doPost");
        HttpSession session = request.getSession();
        UserObject currentUser = ServletUtilities.getCurrentUser(request);
        String requestDetails = ServletUtilities.getPOSTRequestJSONString(request);
        Map<String, String> requestDetailsMap = ServletUtilities.convertJSONFormDataToMap(requestDetails);
        String password = requestDetailsMap.get("changeEmailPassword");

        StandardOutputObject outputObject = new StandardOutputObject();
        if (!Authorization.isCurrentUserAuthorized(password, currentUser.getId_user()))
        {
            log.debug("user not authorized");
            outputObject.setSuccess(false);
            outputObject.setErrorCode(ErrorCode.AUTHORIZATION_FAILED);
            writeOutput(response, outputObject);
            return;
        }

        String newEmail = requestDetailsMap.get("newEmail");
        if (DatabaseAccess.userAlreadyExistsCheckEmail(newEmail))
        {
            log.debug("new email already exists");
            outputObject.setSuccess(false);
            outputObject.setErrorCode(ErrorCode.ACCOUNT_ALREADY_EXISTS);
            writeOutput(response, outputObject);
            return;
        }

        if (DatabaseAccess.changeEmail(newEmail, currentUser.getId_user()))
        {
            log.debug("email changed successfully");
            outputObject.setSuccess(true);
            Map<String, String> tempMap = new HashMap<>();
            tempMap.put("newEmail", newEmail);
            UserObject user = (UserObject) session.getAttribute("user");
            tempMap.put("oldEmail", user.getEmail());
            user.setEmail(newEmail);
            outputObject.setData(tempMap);
            writeOutput(response, outputObject);

        } else
        {
            log.debug("email change failed");
            outputObject.setSuccess(false);
            outputObject.setErrorCode(ErrorCode.CHANGE_EMAIL_FAILED);
            writeOutput(response, outputObject);
        }
    }

    private void writeOutput(HttpServletResponse response, StandardOutputObject outputMap)
    {
        log.trace("writeOutput");
        String outputJSON = outputMap.getJSONString();
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
