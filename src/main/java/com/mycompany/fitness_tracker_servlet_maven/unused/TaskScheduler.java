/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.unused;

import com.mycompany.fitness_tracker_servlet_maven.core.DatabaseAccess;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author max
 */
public class TaskScheduler
{
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public TaskScheduler()
    {

    }

    public void setupTasks()
    {
        System.out.println("TaskScheduler executing: setting up tasks");
        service = Executors.newSingleThreadScheduledExecutor();

        CleanDatabase cleanDatabase = new CleanDatabase();
        //run cleanDatabase task, 0 delay before start, 1 day between repeat executions
        service.scheduleAtFixedRate(cleanDatabase, 1L, 1L, TimeUnit.DAYS);
    }

    private class CleanDatabase implements Runnable
    {

        @Override
        public void run()
        {
            System.out.println("CleanDatabase executing");
            DatabaseAccess.removeExpiredTokens();
        }
    }

    public void shutdownTasks()
    {
        service.shutdown();
    }
}
