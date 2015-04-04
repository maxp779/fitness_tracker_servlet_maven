/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.unused;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author max
 */
public class StartupClass
{
    public void start()
    {
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("javascript");
    try {
      engine.put("name", "abcde");
      engine.eval("var output = '';for (i = 0; i <= name.length; i++) {"
          + "  output = name.charAt(i)+'-' + output" + "}");
      String name = (String) engine.get("output");
      System.out.println(name);
    } catch (ScriptException e) {
      System.err.println(e);
    }
    }
}
