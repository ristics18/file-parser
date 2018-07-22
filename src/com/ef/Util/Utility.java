package com.ef.Util;

import com.ef.Entities.CmdArgs;
import com.ef.Entities.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class, which contains the implementation of helper and validation methods
 * @author: Srdan Ristic 
 * @date: Apr 9th 2018
 */
public class Utility {

    /**
     * Method which checks provided command line arguments and creates an object based on them
     *
     * @param args
     * @return CmdArgs object
     */
    public static CmdArgs cmdArgs(String[] args) {
        CmdArgs obj = new CmdArgs();
        loop:
        for (String arg : args) {
            String[] keyValue = arg.split(Constants.CMD_LINE_FLAG_DELIMITER); // key represents flags (--startDate, --duration, --threshold)
            String flag = keyValue[0];
            String value = keyValue[1];
            switch (flag) {
                case Constants.FLAG_ACCESS_LOG:
                    obj.setAccesslog(value);
                    break;
                case Constants.FLAG_START_DATE:
                    LocalDateTime date = formatDate(value, Constants.ARGS_DATE_FORMAT);
                    obj.setStartDate(date);
                    break;
                case Constants.FLAG_DURATION:
                    obj.setDuration(value);
                    break;
                case Constants.FLAG_THRESHOLD:
                    obj.setThreshold(Integer.parseInt(value));
                    break;
            }
        }
        return obj;
    }

    /**
     * Method for reading the file
     *
     * @param filePath
     * @return
     */
    public static BufferedReader readFile(String filePath) {
        BufferedReader br = null;
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                exit("File does not exist " + filePath);
            }
            br = new BufferedReader(new FileReader(f));
            return br;
        } catch (IOException e) {
            exit(e.getMessage());
        }
        return br;
    }

    /**
     * Method for parsing each line in the file based on delimiter
     *
     * @param line
     * @return
     */
    public static Log parseLine(String line) {
        String[] values = line.split(Constants.LOG_LINE_DELIMITER);
        /**
         * values[0] - datetime
         * values[1] - IP
         * values[2] - request
         * values[3] - status
         * values[4] - userAgent
         */
        LocalDateTime date = formatDate(values[0], Constants.LOG_DATE_FORMAT);
        Log log = new Log(date, values[1], values[2], Integer.parseInt(values[3]), values[4]);
        return log;
    }

    /**
     * Method for parsing the date, based on provided formatting
     *
     * @param argDate
     * @param formatting
     * @return
     */
    public static LocalDateTime formatDate(String argDate, String formatting) {
        LocalDateTime datetime = null;
        try {
            datetime = LocalDateTime.parse(argDate, DateTimeFormatter.ofPattern(formatting));
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("An error occurred while parsing the date. Please make sure the formatting is ").append(formatting).append(".");
            exit(sb.toString());
        }
        return datetime;
    }

    /**
     * Method for exiting the execution of the program. Used when some of the parameters are not set correctly
     * or an exceptions occurred.
     *
     * @param message
     */
    public static void exit(String message) {
        System.err.println(message);
        System.exit(0);
    }

}
