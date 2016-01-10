/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is the servlet that routes all client requests to the appropriate
 * resource, it also attaches a jsessionid to the request object if there is a
 * jsessionid. Certain servlets require additional commands e.g
 * /AuthenticationServlet/mobileLoginPage as those servlets will serve both
 * mobile and desktop clients and take appropriate action
 *
 * @author max
 */
@WebServlet(name = "FrontControllerServlet", urlPatterns =
{
    "/FrontControllerServlet/*"
})
public class FrontControllerServlet extends HttpServlet
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
        System.out.println("FrontController: executing");
        System.out.println("FrontController: current request URI = " + request.getRequestURI());
        String currentRequest = getRequest(request);
        //String currentRequest = request.getRequestURI();
        RequestDispatcher rd;
        System.out.println("FrontController: current request = " + currentRequest);

        //String currentServlet = ClientAPI.clientAPIMap.get(currentRequest);
        //if initial API request
//        if (currentRequest.equals("AJAX_GetAPI"))
//        {
//            System.out.println("FrontController: login page request");
//            rd = request.getRequestDispatcher("/AJAX_GetAPI");
//            rd.forward(request, response);
//
//        } else //else client has API already
//        {
//            System.out.println("FrontController: Request for " + currentRequest);
//            rd = request.getRequestDispatcher("/" + currentRequest);
//            rd.forward(request, response);
//        }
        //Request routing
        if (currentRequest.equals(ClientAPI.getLOGIN_PAGE_REQUEST()))
        {
            System.out.println("FrontController: login page request");
            rd = request.getRequestDispatcher("/LoginPageServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getLOGOUT_REQUEST()))
        {
            System.out.println("FrontController: logout request");
            rd = request.getRequestDispatcher("/LogoutServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getLOGIN_REQUEST()))
        {
            System.out.println("FrontController: authentication request");
            rd = request.getRequestDispatcher("/AuthenticationServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getMAIN_PAGE_REQUEST()))
        {
            System.out.println("FrontController: main page request");
            rd = request.getRequestDispatcher("/MainPageServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getCREATE_ACCOUNT_REQUEST()))
        {
            System.out.println("FrontController: create account request");
            rd = request.getRequestDispatcher("/CreateAccountServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getCREATE_ACCOUNT_PAGE_REQUEST()))
        {
            System.out.println("FrontController: create account page request");
            rd = request.getRequestDispatcher("/CreateAccountPageServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getWORKOUTLOG_PAGE_REQUEST()))
        {
            System.out.println("FrontController: workout log page request");
            rd = request.getRequestDispatcher("/WorkoutLogPageServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getCUSTOM_FOODS_PAGE_REQUEST()))
        {
            System.out.println("FrontController: custom foods page request");
            rd = request.getRequestDispatcher("/CustomFoodsPageServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getMY_STATS_PAGE_REQUEST()))
        {
            System.out.println("FrontController: my stats page request");
            rd = request.getRequestDispatcher("/MyStatsPageServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_GET_CUSTOM_FOOD_LIST()))
        {
            System.out.println("FrontController: get custom food list AJAX request");
            rd = request.getRequestDispatcher("/AJAX_GetCustomFoodList");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_REMOVE_CUSTOM_FOOD()))
        {
            System.out.println("FrontController: remove a custom food AJAX request");
            rd = request.getRequestDispatcher("/AJAX_RemoveCustomFood");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_ADD_CUSTOM_FOOD()))
        {
            System.out.println("FrontController: add a custom food AJAX request");
            rd = request.getRequestDispatcher("/AJAX_AddCustomFood");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_EDIT_CUSTOM_FOOD()))
        {
            System.out.println("FrontController: edit a custom food AJAX request");
            rd = request.getRequestDispatcher("/AJAX_EditCustomFood");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_ADD_EATEN_FOOD()))
        {
            System.out.println("FrontController: add a food that was eaten AJAX request");
            rd = request.getRequestDispatcher("/AJAX_AddEatenFood");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_GET_EATEN_FOOD_LIST()))
        {
            System.out.println("FrontController: get eaten food list AJAX request");
            rd = request.getRequestDispatcher("/AJAX_GetEatenFoodList");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_REMOVE_EATEN_FOOD()))
        {
            System.out.println("FrontController: remove eaten food AJAX request");
            rd = request.getRequestDispatcher("/AJAX_RemoveEatenFood");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_SEARCH_FOR_FOOD()))
        {
            System.out.println("FrontController: food search AJAX request");
            rd = request.getRequestDispatcher("/AJAX_SearchForFood");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_MODIFY_SELECTED_ATTRIBUTES()))
        {
            System.out.println("FrontController: modify selected food attributes AJAX request");
            rd = request.getRequestDispatcher("/AJAX_ModifySelectedAttributes");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_GET_VIEWABLE_ATTRIBUTES()))
        {
            System.out.println("FrontController: get selected food attributes list AJAX request");
            rd = request.getRequestDispatcher("/AJAX_GetViewableAttributesList");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_GET_FRIENDLY_NAMES()))
        {
            System.out.println("FrontController: get selected friendly names for food attributes AJAX request");
            rd = request.getRequestDispatcher("/AJAX_GetFriendlyNames");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_MODIFY_USER_STATS()))
        {
            System.out.println("FrontController: modify user stats AJAX request");
            rd = request.getRequestDispatcher("/AJAX_ModifyUserStats");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_GET_USER_STATS()))
        {
            System.out.println("FrontController: get user stats AJAX request");
            rd = request.getRequestDispatcher("/AJAX_GetUserStats");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getFORGOT_PASSWORD_PAGE_REQUEST()))
        {
            System.out.println("FrontController: forgotten password request");
            rd = request.getRequestDispatcher("/ForgotPasswordPageServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getFORGOT_PASSWORD_EMAIL_REQUEST()))
        {
            System.out.println("FrontController: forgotten password email request");
            rd = request.getRequestDispatcher("/ForgotPasswordEmailServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getCHANGE_PASSWORD_PAGE_REQUEST()))
        {
            System.out.println("FrontController: change password page request");
            rd = request.getRequestDispatcher("/ChangePasswordPageServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getCHANGE_PASSWORD_REQUEST()))
        {
            System.out.println("FrontController: change password request");
            rd = request.getRequestDispatcher("/ChangePasswordServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getAJAX_GET_IDENTIFIER_TOKEN_EMAIL()))
        {
            System.out.println("FrontController: identifier token email request");
            rd = request.getRequestDispatcher("/AJAX_GetIdentifierTokenEmail");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getSETTINGS_PAGE_REQUEST()))
        {
            System.out.println("FrontController: setting page request");
            rd = request.getRequestDispatcher("/SettingsPageServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getCHANGE_EMAIL_REQUEST()))
        {
            System.out.println("FrontController: change email request");
            rd = request.getRequestDispatcher("/ChangeEmailServlet");
            rd.forward(request, response);
        } else if (currentRequest.equals(ClientAPI.getDELETE_ACCOUNT_REQUEST()))
        {
            System.out.println("FrontController: delete account request");
            rd = request.getRequestDispatcher("/DeleteAccountServlet");
            rd.forward(request, response);
        }else
        {
            response.sendRedirect("/fitness_tracker_servlet_maven/invalid.html");
        }
        
    }

    /**
     * This method returns the end part of an HttpServletRequest object e.g an
     * HttpServletRequest of /SomeServlet/Stuff/mobile.html would return
     * "mobile.html"
     *
     * @param request
     * @return A String representing the request made
     */
    public static String getRequest(HttpServletRequest request)
    {
        //get and format the request from the URL given
        String output = "";
        String currentRequestURL = request.getRequestURI();
        StringBuilder currentRequestStringBuilder = new StringBuilder(currentRequestURL);

        //get the command
        int slashIndex = currentRequestStringBuilder.lastIndexOf("/");
        currentRequestStringBuilder = currentRequestStringBuilder.delete(0, slashIndex + 1);

        //if jsessionid is part of the URI remove it
        //strangely enough this seems to be needed with tomee and not glassfish
        int jsessionidIndex = currentRequestStringBuilder.indexOf(";jsessionid=");
        if (jsessionidIndex != -1)
        {
            currentRequestStringBuilder = currentRequestStringBuilder.delete(jsessionidIndex, currentRequestStringBuilder.length());

        }

        return currentRequestStringBuilder.toString();
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
