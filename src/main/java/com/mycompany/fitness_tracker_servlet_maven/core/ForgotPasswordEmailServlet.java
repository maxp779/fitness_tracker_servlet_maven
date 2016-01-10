/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        System.out.println("ForgotPasswordEmailServlet: Executing");
        String loginDetails = ServletUtilities.getRequestData(request);
        Map<String, String> loginDetailsMap = ServletUtilities.convertJSONFormDataToMap(loginDetails);
        String email = loginDetailsMap.get("email");

        boolean userExists = DatabaseAccess.userAlreadyExistsCheckEmail(email);

        if (userExists)
        {
            String id_user = DatabaseAccess.getid_user(email);
            UUID identifierToken = DatabaseAccess.createForgotPasswordRecord(id_user, email);
            
            String subject = "simplfitness.co.uk password change request";
            String content = "Hello, please click the following link to change your password, it is valid for 60 minutes: "
                    + "http://localhost:8080/FrontControllerServlet/changePasswordPage?identifierToken="
                    + identifierToken.toString();

            boolean sendEmailSuccess = Email.sendEmail(email, subject, content);

//            // Sender's email ID needs to be mentioned
//            String from = "simplfitness779@gmail.com";//change accordingly
//            final String username = "simplfitness779@gmail.com";//change accordingly
//            final String password = "sunny779";//change accordingly
//
//            // Assuming you are sending email through relay.jangosmtp.net
//            String host = "smtp.gmail.com";
//
//            Properties props = new Properties();
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.smtp.host", host);
//            props.put("mail.smtp.port", "587");
//
//            // Get the Session object.
//            Session session = Session.getInstance(props,
//                    new javax.mail.Authenticator()
//            {
//                @Override
//                protected PasswordAuthentication getPasswordAuthentication()
//                {
//                    return new PasswordAuthentication(username, password);
//                }
//            });
//
//            try
//            {
//                // Create a default MimeMessage object.
//                Message message = new MimeMessage(session);
//
//                // Set From: header field of the header.
//                message.setFrom(new InternetAddress(from));
//
//                // Set To: header field of the header.
//                message.setRecipients(Message.RecipientType.TO,
//                        InternetAddress.parse(to));
//
//                // Set Subject: header field
//                message.setSubject(subject);
//
//                // Now set the actual message
//                message.setText(content);
//
//                // Send message
//                Transport.send(message);
//
//                System.out.println("ForgottonPasswordServlet: Sent password reset email to " + to);
//
//            } catch (MessagingException e)
//            {
//                throw new RuntimeException(e);
//            }

//            RequestDispatcher rd = sc.getRequestDispatcher("/" + GlobalValues.getWEB_PAGES_DIRECTORY() + "/" + GlobalValues.getFORGOT_PASSWORD_PAGE());
//            PrintWriter out = response.getWriter();
//            out.println("<div class=\"alert alert-success\" role=\"alert\">An email was sent to "+to+" please check it for details on how to change your password.</div>");
//            rd.include(request, response);
            try (PrintWriter out = response.getWriter())
            {
                out.write("true");
            }

        } else
        {
//            RequestDispatcher rd = sc.getRequestDispatcher("/" + GlobalValues.getWEB_PAGES_DIRECTORY() + "/" + GlobalValues.getFORGOT_PASSWORD_PAGE());
//            PrintWriter out = response.getWriter();
//            out.println("<div class=\"alert alert-danger\" role=\"alert\">Sorry we couldnt find an account for " + to + "</div>");
//            rd.include(request, response);
            try (PrintWriter out = response.getWriter())
            {
                out.write(ErrorCodes.getACCOUNT_DOSENT_EXIST());
            }
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
