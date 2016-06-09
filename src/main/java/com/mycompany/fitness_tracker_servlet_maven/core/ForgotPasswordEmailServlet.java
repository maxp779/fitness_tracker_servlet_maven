/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import com.mycompany.fitness_tracker_servlet_maven.serverAPI.ErrorCode;
import com.mycompany.fitness_tracker_servlet_maven.email.Email;
import com.mycompany.fitness_tracker_servlet_maven.database.DatabaseAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.UUID;
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
@WebServlet(name = "ForgotPasswordEmailServlet", urlPatterns =
{
    "/ForgotPasswordEmailServlet"
})
public class ForgotPasswordEmailServlet extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(ForgotPasswordEmailServlet.class);
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
        log.trace("doPost()");
        String loginDetails = ServletUtilities.getPOSTRequestJSONString(request);
        Map<String, String> loginDetailsMap = ServletUtilities.convertJSONFormDataToMap(loginDetails);
        String email = loginDetailsMap.get("email");

        boolean userExists = DatabaseAccess.userAlreadyExistsCheckEmail(email);
        StandardOutputObject outputObject = new StandardOutputObject();
        outputObject.setData(loginDetailsMap);
        outputObject.setSuccess(userExists);
        if (!userExists)
        {
            log.debug("user dosent exist");
            outputObject.setErrorCode(ErrorCode.ACCOUNT_DOSENT_EXIST);
            writeOutput(response, outputObject);
            return;
        }

        log.debug("user exists");
        String id_user = DatabaseAccess.getid_user(email);
        UUID identifierToken = DatabaseAccess.createForgotPasswordRecord(id_user, email);

        String subject = "simplfitness.co.uk password change request";
        String content = "Hello, please click the following link to change your password, it is valid for 60 minutes: "
                + "http://localhost:8080/FrontControllerServlet/changePasswordPage?identifierToken="
                + identifierToken.toString();

        boolean sendEmailSuccess = Email.sendEmail(email, subject, content);
        outputObject.setSuccess(sendEmailSuccess);
        if (!sendEmailSuccess)
        {
            log.debug("sending email failed");
            outputObject.setErrorCode(ErrorCode.SENDING_EMAIL_FAILED);
            writeOutput(response, outputObject);
            return;
        }
        log.debug("sending email successful");
        writeOutput(response, outputObject);

    }

    private void writeOutput(HttpServletResponse response, StandardOutputObject outputObject)
    {
        log.trace("writeOutput()");
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
