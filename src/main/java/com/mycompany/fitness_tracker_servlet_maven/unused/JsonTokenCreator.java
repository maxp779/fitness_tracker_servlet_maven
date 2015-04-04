/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.unused;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author max
 */
public class JsonTokenCreator 
{
    private static final String currentSeparator = "+";

    public static JsonObject createToken(String anEmail)
    {
        UUID aUUID = UUID.randomUUID();
        LocalDateTime creationTime = LocalDateTime.now();
        String token = aUUID +currentSeparator+ anEmail +currentSeparator+ creationTime; 

        JsonObjectBuilder aJsonObjectBuilder = Json.createObjectBuilder();
        aJsonObjectBuilder.add("UUID", aUUID.toString());
        aJsonObjectBuilder.add("email", anEmail);
        aJsonObjectBuilder.add("creationTime", creationTime.toString());
        aJsonObjectBuilder.add("separator", currentSeparator);
        aJsonObjectBuilder.add("token", token);
        JsonObject aJsonToken = aJsonObjectBuilder.build();

        return aJsonToken;
    }     
}
