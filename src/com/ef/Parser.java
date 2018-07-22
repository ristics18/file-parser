package com.ef;

import com.ef.Entities.CmdArgs;
import com.ef.Entities.Log;
import com.ef.Util.Database;
import com.ef.Util.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Parser class which triggers the program execution
 * @author: Srdan Ristic 
 * @date: Apr 9th 2018
 */
public class Parser {

    /**
     * Main method
     *
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        System.out.println("---------------------------------------------------");
        // Checking command line arguments (--startDate, --duration, --threshold)
        System.out.println("Checking command line arguments...");
        CmdArgs cmdArgs = Utility.cmdArgs(args);
        if (cmdArgs == null || !cmdArgs.allSet()) {
            Utility.exit("Command line arguments are not passed correctly, please check the formatting " +
                    "and provide all the arguments (--accesslog, --startDate, --duration, --threshold).");
        }
        System.out.println("Command line arguments are set correctly! " + cmdArgs);

        System.out.println("---------------------------------------------------");

        // Reading the file
        String filePath = cmdArgs.getAccesslog();
        System.out.println("Reading file " + filePath);
        BufferedReader br = Utility.readFile(filePath);
        System.out.println("Successfully read the file!");

        System.out.println("---------------------------------------------------");

        // Reading each line and storing it in ArrayList
        ArrayList<Log> lines = new ArrayList<>();
        try {
            String line = "";
            System.out.println("Reading lines...");
            while ((line = br.readLine()) != null) {
                Log log = Utility.parseLine(line);
                lines.add(log);
            }
            System.out.println("Successfully read all the lines from file!");
        } catch (IOException ioe) {
            Utility.exit("An error occurred while reading lines from file!");
        }

        System.out.println("---------------------------------------------------");

        // Connecting to DB
        System.out.println("Connecting to DB...");
        Database.getInstance();
        System.out.println("Successfully connected to DB!");

        System.out.println("---------------------------------------------------");


        System.out.println("Truncating tables...");
        Database.getInstance().truncate();
        System.out.println("Successfully emptied all tables!");

        System.out.println("---------------------------------------------------");

        System.out.println("Adding logs to DB...");
        Database.getInstance().addLogs(lines);
        System.out.println("Successfully added logs to DB!");

        System.out.println("---------------------------------------------------");
        System.out.println("Filtered IPs based on command line arguments:");
        ArrayList<String> ips = Database.getInstance().getLogs(cmdArgs);
        System.out.println("IPs:");
        for (String ip : ips) {
            System.out.println(ip);
        }
        System.out.println("---------------------------------------------------");

        System.out.println("Adding filtered IPs to blocked table...");
        Database.getInstance().addBlocked(ips);
        System.out.println("Successfully added filtered IPs to blocked table!");

        System.out.println("---------------------------------------------------");

        Database.getInstance().closeConn();
        System.out.println("Connection closed!");
    }
}
