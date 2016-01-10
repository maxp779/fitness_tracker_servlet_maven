/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author max
 */
@WebFilter(filterName = "AuthorizationFilter", urlPatterns =
{
    "/*"
})
public class AuthorizationFilter implements Filter
{
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public AuthorizationFilter()
    {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException
    {
//        if (debug)
//        {
//            log("AuthorizationFilter:DoBeforeProcessing");
//        }

	// Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
	// For example, a logging filter might log items on the request object,
        // such as the parameters.
	/*
         for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         String values[] = request.getParameterValues(name);
         int n = values.length;
         StringBuffer buf = new StringBuffer();
         buf.append(name);
         buf.append("=");
         for(int i=0; i < n; i++) {
         buf.append(values[i]);
         if (i < n-1)
         buf.append(",");
         }
         log(buf.toString());
         }
         */
        
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException
    {
//        if (debug)
//        {
//            log("AuthorizationFilter:DoAfterProcessing");
//        }

	// Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
	// For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
	/*
         for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         Object value = request.getAttribute(name);
         log("attribute: " + name + "=" + value.toString());

         }
         */
	// For example, a filter might append something to the response.
	/*
         PrintWriter respOut = new PrintWriter(response.getWriter());
         respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException
    {
        
//        if (debug)
//        {
//            log("AuthorizationFilter:doFilter()");
//        }
//        
        doBeforeProcessing(request, response);
//        
//        Throwable problem = null;

        HttpServletRequest aRequest = (HttpServletRequest) request;
        HttpServletResponse aResponse = (HttpServletResponse) response;
        String currentURL = aRequest.getRequestURL().toString();
        //System.out.println("AuthorizationFilter: currentURL " + currentURL);
        ServletContext sc = getFilterConfig().getServletContext();
        boolean sessionValid;
       
        
//        String requestPath = aRequest.getRequestURI();
//
//        if (needsAuthentication(requestPath) &&
//            session == null) { // change "user" for the session attribute you have defined
//
//            aResponse.sendRedirect(sc.getContextPath()+"/"+ GlobalValues.getFirstLoginServlet()); // No logged-in user found, so redirect to login page.
//        } else {
//            chain.doFilter(aRequest, aResponse); // Logged-in user found, so just continue request.
//        }

        
        doAfterProcessing(request, response);
        
        //if request is for FrontController servlet
        //if(currentURI.contains(ClientAPI.getFrontController()))
        
        //if request needs auth
        if(this.needsAuthentication(currentURL))
        {
            sessionValid = SessionManager.sessionValidate((HttpServletRequest) request);
            //if valid session pass on request
            if(sessionValid)
            {
                chain.doFilter(request, response);
            }
            else//if invalid session, back to login page
            {
                System.out.println("AuthorizationFilter: redirecting back to login page!");
//                //if invalid session but contains requests to login, pass on request
//                if(currentURL.contains(ClientAPI.getLoginPageRequest()) || currentURL.contains(ClientAPI.getLoginRequest()))
//                {
//                    chain.doFilter(request, response);
//                }
//                else //if invalid session and contains no login requests, kick back to login page
//                {
                    aResponse.sendRedirect(sc.getContextPath()+"/"+ GlobalValues.getFIRST_LOGIN_SERVLET());
                //}
            }
        }
        else //request dosent need auth so can be ignored
        {
            chain.doFilter(request, response);
        }


	// If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
//        if (problem != null)
//        {
//            if (problem instanceof ServletException)
//            {
//                throw (ServletException) problem;
//            }
//            if (problem instanceof IOException)
//            {
//                throw (IOException) problem;
//            }
//            sendProcessingError(problem, response);
//        }
    }
    
    /**
     * This method contains a list of all resources that do not
     * require authentication
     * @param url
     * @return if auth is required true is returned, false if it
     * is not required
     */
    private boolean needsAuthentication(String url) 
    {   
        String[] nonAuthResources = GlobalValues.getNON_AUTH_RESOURCES();
        
        for(String nonAuthRequest : nonAuthResources) 
        {
            if(url.equals(GlobalValues.getWEB_ADDRESS()))
            {
                //System.out.println("AuthorizationFilter: skipping authentication as currentURI is initial request");
                return false;
            }
            else if (url.contains(nonAuthRequest)) 
            {
                //System.out.println("AuthorizationFilter: skipping authentication as currentURI contains: " + nonAuthRequest);
                return false;
            }
        }
        System.out.println("AuthorizationFilter: authentication required for " + url);
        return true;
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig()
    {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy()
    {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig)
    {        
        this.filterConfig = filterConfig;
        if (filterConfig != null)
        {
            if (debug)
            {                
                log("AuthorizationFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString()
    {
        if (filterConfig == null)
        {
            return ("AuthorizationFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthorizationFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response)
    {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals(""))
        {
            try
            {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex)
            {
            }
        } else
        {
            try
            {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex)
            {
            }
        }
    }
    
    public static String getStackTrace(Throwable t)
    {
        String stackTrace = null;
        try
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex)
        {
        }
        return stackTrace;
    }
    
    public void log(String msg)
    {
        filterConfig.getServletContext().log(msg);        
    }
    
}
////