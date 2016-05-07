/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.startup;

import com.mycompany.fitness_tracker_servlet_maven.controllerservlets.AddEatenFoodServlet;
import com.mycompany.fitness_tracker_servlet_maven.serverAPI.ServerAPI;
import com.mycompany.fitness_tracker_servlet_maven.database.DatabaseUtils;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class does things on startup, anything that must be done before the
 * application is running should be done here in the contextInitialized()
 * method. This class also takes care of things when the application closes with
 * the contextDestroyed() method.
 *
 * @author max
 */
@WebListener
public class StartupWebListener implements ServletContextListener
{
    private static final Logger log = LoggerFactory.getLogger(StartupWebListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        log.trace("contextInitialized");
    //do on application init

        //load the database driver
        DatabaseUtils.loadDatabaseDriver();
        ServerAPI.setupServerAPI();
        //can test stuff here
        //DatabaseAccess.getCustomFoods(1);
    //this sets the servlet context to ONLY use URL rewriting, even if the client uses cookies
        //event.getServletContext().setSessionTrackingModes(EnumSet.of(SessionTrackingMode.URL));
        //sets up the API map, this is needed so the map can be turned into a JSON easily and then sent to the client
        //this helps removes the need to maintain a separate ServerAPI.js on the clientside
        //ClientAPI.setupAPIMap();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
        log.trace("contextDestroyed");
    //do on application destroy

        //deregister database driver **this may not even be needed**
        DatabaseUtils.dersgisterDatabaseDriver();        
    }

}
