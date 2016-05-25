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
@WebServlet(name = "ChangePasswordServlet", urlPatterns =
{
    "/ChangePasswordServlet"
})
public class ChangePasswordServlet extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(ChangePasswordServlet.class);

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException
    {
        log.trace("doPost");
        HttpSession session = request.getSession(false);
        StandardOutputObject outputObject;
        String requestDetails = ServletUtilities.getPOSTRequestJSONString(request);
        Map<String, String> requestDetailsMap = ServletUtilities.convertJSONFormDataToMap(requestDetails);
        if (session == null)
        {
            outputObject = changePasswordWithoutSession(request, requestDetailsMap);
        } else
        {
            outputObject = changePasswordWithSession(request, requestDetailsMap);
        }

        writeOutput(response, outputObject);
    }

    /**
     * User is not logged in, likely cannot log in and has likely forgotten
     * their password
     *
     * @param request
     * @param requestDetails
     * @return
     */
    private StandardOutputObject changePasswordWithoutSession(HttpServletRequest request, Map<String, String> requestDetails)
    {
        log.trace("changePasswordWithoutSession");

        String identifierToken = requestDetails.get("identifierToken");
        boolean tokenValid = DatabaseAccess.validateForgotPasswordRequest(identifierToken);
        StandardOutputObject outputObject = new StandardOutputObject();

        if (!tokenValid)
        {
            log.debug("token invalid");
            outputObject.setSuccess(false);
            outputObject.setErrorCode(ErrorCode.FORGOT_PASSWORD_TOKEN_EXPIRED_OR_ALREADY_USED);
            return outputObject;
        }

        Map<String, String> tempMap = new HashMap<>();
        String email = requestDetails.get("email");
        tempMap.put("email", email);
        outputObject.setData(tempMap); //store so client can tell the user "password has been successfully changed for etc@email.com"

        String newHashedPassword = PasswordEncoder.hashPassword(requestDetails.get("password"));
        DatabaseAccess.removeExpiredTokens(); //clear out expired tokens from database
        boolean success = DatabaseAccess.changePasswordByEmail(email, newHashedPassword);

        if (success)
        {
            log.debug("password changed successfully");
            DatabaseAccess.removeToken(identifierToken);
            outputObject.setSuccess(true);
        } else
        {
            log.debug("password change failed");
            outputObject.setSuccess(false);
            outputObject.setErrorCode(ErrorCode.PASSWORD_CHANGE_FAILED);
        }
        return outputObject;
    }

    /**
     * User is logged in and simply wants to change their password
     *
     * @param request
     * @param requestDetails
     * @return
     */
    private StandardOutputObject changePasswordWithSession(HttpServletRequest request, Map<String, String> requestDetails)
    {
        log.trace("changePasswordWithSession");
        UserObject currentUser = ServletUtilities.getCurrentUser(request);
        StandardOutputObject outputObject = new StandardOutputObject();

        String oldPassword = requestDetails.get("oldPassword");
        if (Authorization.isCurrentUserAuthorized(oldPassword, currentUser.getId_user()))
        {
            log.debug("user authorized to change password");
            String newHashedPassword = PasswordEncoder.hashPassword(requestDetails.get("newPassword"));
            boolean success = DatabaseAccess.changePassword(currentUser.getId_user(), newHashedPassword);
            outputObject.setSuccess(success);
            if (success)
            {
                log.debug("password change succeeded");
                outputObject.setData(currentUser.toMap());
            } else
            {
                log.debug("password change failed");
                outputObject.setErrorCode(ErrorCode.PASSWORD_CHANGE_FAILED);
            }

        } else
        {
            log.trace("user not authorized to change password");
            outputObject.setSuccess(false);
            outputObject.setErrorCode(ErrorCode.AUTHORIZATION_FAILED);
        }
        return outputObject;
    }

    private void writeOutput(HttpServletResponse response, StandardOutputObject outputObject)
    {
        log.trace("writeOutput");
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
