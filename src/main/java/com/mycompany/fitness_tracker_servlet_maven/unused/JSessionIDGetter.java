/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.unused;

/**
 *
 * @author max
 */
public class JSessionIDGetter
{
    /**
     * Gets the jsessionID from an encoded URL e.g an input of
     * "/fitness_tracker_servlet_maven/desktopPages/desktopMainPage.html;jsessionid=7dbdcd1141959f4ac2a5945a29b8"
     * would return "7dbdcd1141959f4ac2a5945a29b8"
     * @param encodedURL
     * @return a jsessionID in the URL or an empty string if there is none
     */
    public static String getJSessionID(String encodedURL)
    {
        String jSessionSearchParameter = ";jsessionid=";
        int jSessionLocation = encodedURL.indexOf(jSessionSearchParameter);
        String jSessionID = "";
        if(jSessionLocation != -1)
        {
             jSessionID = encodedURL.substring(jSessionLocation);
        }

        return jSessionID;
    }
}
