package com.ef.Util;

import com.ef.Entities.CmdArgs;
import com.ef.Entities.Log;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class used for making transactions with database
 * @author: Srdan Ristic 
 * @date: Apr 9th 2018
 */
public class Database {

    // Instance variables
    private static Database instance;
    private static Connection connection;
    private static PreparedStatement ps;
    private static ResultSet rs;

    /**
     * Since instance of Database object
     *
     * @return
     * @throws SQLException
     */
    public synchronized static Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }


    /**
     * Default constructor
     *
     * @throws SQLException
     */
    private Database() throws SQLException {
        StringBuilder sb = new StringBuilder();
        String db_url = sb.append(Constants.DB_URL).append(Constants.DB_NAME).append(Constants.DB_USE_SSL).
                append(Constants.DB_BATCH_STATEMENTS).toString();
        connection = DriverManager.getConnection(db_url, Constants.DB_USERNAME, Constants.DB_PASSWORD);
    }

    /**
     * Method which calles stored procedure in order to truncate all the tables in database
     *
     * @throws SQLException
     */
    public void truncate() throws SQLException {
        ps = connection.prepareStatement(
                "CALL truncate_tables()");
        ps.executeUpdate();
    }

    /**
     * Method for adding logs into the database
     *
     * @param logs
     * @throws SQLException
     */
    public void addLogs(ArrayList<Log> logs) throws SQLException {
        ps = connection.prepareStatement(
                "INSERT INTO log (DateTime, IP, Request, Status, UserAgent) VALUES (?,?,?,?,?)");
        for (int i = 0; i < logs.size(); i++) {
            ps.setString(1, logs.get(i).getDate().toString());
            ps.setString(2, logs.get(i).getIP());
            ps.setString(3, logs.get(i).getRequest());
            ps.setInt(4, logs.get(i).getStatus());
            ps.setString(5, logs.get(i).getUserAgent());
            ps.addBatch();
        }
        ps.executeBatch();
    }

    /**
     * Method for retrieving logs from database based on command line arguments
     *
     * @param cmdArgs
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getLogs(CmdArgs cmdArgs) throws SQLException {
        LocalDateTime startDate = cmdArgs.getStartDate();
        LocalDateTime endDate = cmdArgs.getStartDate();
        switch (cmdArgs.getDuration()) {
            case Constants.DURATION_HOURLY:
                endDate = endDate.plusHours(1);
                break;
            case Constants.DURATION_DAILY:
                endDate = endDate.plusDays(1);
                break;
        }

        String stDate = startDate.toString().replace("T", ".");
        System.out.println("Start Date: " + stDate);
        String enDate = endDate.toString().replace("T", ".");
        System.out.println("End Date: " + enDate);
        System.out.println("Threshold: " + cmdArgs.getThreshold());

        endDate.toString().replace("T", ".");

        ps = connection.prepareStatement(
                "SELECT IP FROM log WHERE DateTime" +
                        " BETWEEN ? AND ?" +
                        " GROUP BY IP " +
                        " HAVING COUNT(IP) > ?");
        ps.setString(1, stDate);
        ps.setString(2, enDate);
        ps.setString(3, String.valueOf(cmdArgs.getThreshold()));

        rs = ps.executeQuery();

        ArrayList<String> ips = new ArrayList<>();
        while (rs.next()) {
            ips.add(rs.getString(Constants.LOG_IP));
        }

        return ips;
    }

    /**
     * Method for adding log to blocked table in database
     *
     * @param ips
     * @throws SQLException
     */
    public void addBlocked(ArrayList<String> ips) throws SQLException {
        ps = connection.prepareStatement(
                "INSERT INTO blocked (IP, Comment) VALUES (?,?)");
        for (String ip : ips) {
            ps.setString(1, ip);
            ps.setString(2, Constants.LOG_COMMENT_REASON);
            ps.executeUpdate();
        }
    }

    /**
     * Closing db connection
     */
    public void closeConn() {
        System.out.println("Closing DB connection...");
        try {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
