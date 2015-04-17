/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This class does things on startup, anything that must be done before the application
 * is running should be done here in the contextInitialized() method. 
 * This class also takes care of things when the application closes with the
 * contextDestroyed() method.
 * @author max
 */
@WebListener
public class StartupWebListener implements ServletContextListener {
  public void contextInitialized(ServletContextEvent event)
  {
    //do on application init
      
        //load the database driver
        DatabaseUtils.loadDatabaseDriver();     
        
        
        
    //this sets the servlet context to ONLY use URL rewriting, even if the client uses cookies
    //event.getServletContext().setSessionTrackingModes(EnumSet.of(SessionTrackingMode.URL));
    
  }

  public void contextDestroyed(ServletContextEvent event)
  {
    //do on application destroy
      
        //deregister database driver **this may not even be needed**
        DatabaseUtils.dersgisterDatabaseDriver();
      
  }

}
