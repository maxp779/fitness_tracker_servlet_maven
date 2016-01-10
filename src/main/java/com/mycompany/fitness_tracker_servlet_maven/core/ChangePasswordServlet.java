/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

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

//    private static final String passwordChangeSuccess = "<div class=\"alert alert-success\" role=\"alert\">Password for #EMAIL has been changed successfully.</div>";
//    private static final String errorOccurred = "<div class=\"alert alert-danger\" role=\"alert\">An error occurred, please try again.</div>";
//    private static final String incorrectPassword = "<div class=\"alert alert-danger\" role=\"alert\">Incorrect password.</div>";

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
        HttpSession session = request.getSession(false);
        Map<String,String> outputMap = new HashMap<>();
        //user cannot login, has forgotten password and needed to change it via email link
        if (session == null)
        {
            System.out.println("ChangePasswordServlet: user is not logged in, has forgotten password");
            
            String requestDetails = ServletUtilities.getRequestData(request);
            Map<String, String> requestDetailsMap = ServletUtilities.convertJSONFormDataToMap(requestDetails);
            
            String identifierToken = requestDetailsMap.get("identifierToken");           
            boolean tokenValid = DatabaseAccess.validateForgotPasswordRequest(identifierToken);
            
            if(!tokenValid)
            {
                outputMap.put("success", "false");
                outputMap.put("errorCode", ErrorCodes.getFORGOT_PASSWORD_TOKEN_EXPIRED_OR_ALREADY_USED());
                writeOutput(response, outputMap);
                return;
            }
 
            String email = requestDetailsMap.get("email");
            outputMap.put("email", email);
            
            String newHashedPassword = Security.hashPassword(requestDetailsMap.get("password"));
            DatabaseAccess.removeExpiredTokens(); //clear out expired tokens from database
            boolean passwordChangeSuccess = DatabaseAccess.changePasswordByEmail(email, newHashedPassword);
            
            if(passwordChangeSuccess)
            {
                DatabaseAccess.removeToken(identifierToken);
                outputMap.put("success", "true");
                writeOutput(response,outputMap);
            }
            else
            {
                outputMap.put("success", "false");
                outputMap.put("errorCode", ErrorCodes.getPASSWORD_CHANGE_FAILED());
                writeOutput(response, outputMap);
            }
            
            
//            String email = request.getParameter("email");
//            String newHashedPassword = Security.hashPassword(request.getParameter("password"));
//            DatabaseAccess.removeExpiredTokens(); //clear out expired tokens from database
//            boolean success = DatabaseAccess.changePasswordByEmail(email, newHashedPassword);
//
//            if (success)
//            {
//                System.out.println("ChangePasswordServlet password changed successfully for user with email " + email);
//                writeOutput(request, response, passwordChangeSuccess.replaceAll("#EMAIL", email), false);
//            } else
//            {
//                writeOutput(request, response, errorOccurred, false);
//            }
        } else //user is logged in, wants to change password from the settings page
        {
            System.out.println("ChangePasswordServlet: user is logged in and wants to change password");
            
            String requestDetails = ServletUtilities.getRequestData(request);
            Map<String, String> requestDetailsMap = ServletUtilities.convertJSONFormDataToMap(requestDetails);
            String email = (String) session.getAttribute("email");
            outputMap.put("email", email);
            
            String id_user = (String) request.getSession().getAttribute("id_user");
            String oldPassword = requestDetailsMap.get("oldPassword");
            
            
//            String id_user = (String) request.getSession().getAttribute("id_user");
//            String oldPassword = request.getParameter("oldPassword");        
            
            if (Authorization.isCurrentUserAuthorized(oldPassword, id_user))
            {
                String newHashedPassword = Security.hashPassword(requestDetailsMap.get("newPassword"));
                DatabaseAccess.changePassword(id_user, newHashedPassword);
                outputMap.put("success", "true");
                writeOutput(response, outputMap);
                //writeOutput(request, response, passwordChangeSuccess.replaceAll("#EMAIL", email), true);

            } else
            {
                outputMap.put("success", "false");
                outputMap.put("errorCode", ErrorCodes.getUSER_NOT_AUTHORIZED());
                writeOutput(response, outputMap);
                //writeOutput(request, response, incorrectPassword, true);
            }
        }
    }
    
    private void writeOutput(HttpServletResponse response, Map<String,String> outputMap) throws IOException
    {
        String JSONString = ServletUtilities.convertMapToJSONString(outputMap);
        
        try (PrintWriter writer = response.getWriter())
        {
            writer.write(JSONString);
        }
    }

//    private void writeOutput(HttpServletRequest request, HttpServletResponse response, String output, boolean validSessionExists) throws ServletException, IOException
//    {
//        ServletContext servletContext = this.getServletContext();
//        RequestDispatcher rd;
//        if (validSessionExists)
//        {
//            rd = servletContext.getRequestDispatcher("/" + GlobalValues.getWEB_PAGES_DIRECTORY() + "/" + GlobalValues.getSETTINGS_PAGE_FOLDER()+ "/" + GlobalValues.getSETTINGS_PAGE());
//        } else
//        {
//            rd = servletContext.getRequestDispatcher("/" + GlobalValues.getWEB_PAGES_DIRECTORY() + "/" + GlobalValues.getLOGIN_PAGE_FOLDER()+ "/" + GlobalValues.getLOGIN_PAGE());
//        }
//
//        PrintWriter out = response.getWriter();
//        out.println(output);
//        rd.include(request, response);
//    }
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
