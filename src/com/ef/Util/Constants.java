package com.ef.Util;

/**
 * Class that holds constants
 * @author: Srdan Ristic 
 * @date: Apr 9th 2018
 */
public class Constants {

    /**
     * Command line flags
     */
    public static final String FLAG_ACCESS_LOG = "--accesslog";
    public static final String FLAG_START_DATE = "--startDate";
    public static final String FLAG_DURATION = "--duration";
    public static final String FLAG_THRESHOLD = "--threshold";

    /**
     * Delimiters
     */
    public static final String CMD_LINE_FLAG_DELIMITER = "=";
    public static final String LOG_LINE_DELIMITER = "\\|";

    /**
     * Duration options
     */
    public static final String DURATION_HOURLY = "hourly";
    public static final String DURATION_DAILY = "daily";

    /**
     * Date formatting
     */
    public static final String ARGS_DATE_FORMAT = "yyyy-MM-dd.HH:mm:ss";
    public static final String LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * Database config
     */
    public static final String DB_URL = "jdbc:mysql://localhost:3306/";
    public static final String DB_NAME = "accesslog";
    public static final String DB_USERNAME = "tester";
    public static final String DB_PASSWORD = "Tester123!";
    public static final String DB_USE_SSL = "?useSSL=false";
    public static final String DB_BATCH_STATEMENTS = "&rewriteBatchedStatements=true";

    /**
     * Database attributes
     */
    public static final String LOG_IP = "IP";

    /**
     * Reason why IP is blocked
     */
    public static final String LOG_COMMENT_REASON = "Reason why it's blocked.";

}
