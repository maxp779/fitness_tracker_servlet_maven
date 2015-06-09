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
public class UserObject 
{
    private String forename = "";
    private String surname = "";
    private String email = "";
    private String password = "";
    
    public UserObject ()
    {
        
    }
    
    public UserObject (String anEmail, String aPassword)
    {
        email = anEmail;
        password = aPassword;
    }
    
    public UserObject (String aForename, String aSurname, String anEmail, String aPassword)
    {
        forename = aForename;
        surname = aSurname;
        email = anEmail;
        password = aPassword;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
 
}
