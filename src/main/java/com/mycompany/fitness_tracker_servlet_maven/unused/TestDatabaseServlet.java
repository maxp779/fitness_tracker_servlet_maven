/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.unused;

import com.mycompany.fitness_tracker_servlet_maven.core.DatabaseAccess;
import com.mycompany.fitness_tracker_servlet_maven.core.DatabaseUtils;
import com.mycompany.fitness_tracker_servlet_maven.core.GlobalValues;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "TestDatabaseServlet", urlPatterns =
{
    "/TestDatabaseServlet"
})
public class TestDatabaseServlet extends HttpServlet
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
        System.out.println("TestDatabaseServlet executing: " + request.getRequestURL());
        ServletContext sc = request.getServletContext();
            this.testDatabase();
            
            String encodedURL = response.encodeRedirectURL(sc.getContextPath()+"/"+ GlobalValues.getWEB_PAGES_DIRECTORY() +"/"+ "testPage.html");
            response.sendRedirect(encodedURL);

    }
    
        //Testing method, can be deleted later
    public void testDatabase()
    {
        Connection aConnection = DatabaseUtils.getDatabaseConnection();
        ResultSet resultSet = null;
        Statement statement = null;
        String query = "SELECT * FROM usertable;";
        
        try
        {
            //statement = aConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement = aConnection.createStatement();
            resultSet = statement.executeQuery(query);
            
            //resultSet.first();
            resultSet.next();
            resultSet.next();
            String output = resultSet.getString("email");
            System.out.println(output);
            
            resultSet.close();
            statement.close();
            
        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                aConnection.close();
            } catch (SQLException ex)
            {
                Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            aConnection = null;
            
            //DatabaseUtils.closeConnections(aConnection, resultSet, statement);
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
