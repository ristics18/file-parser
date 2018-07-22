package com.ef.Entities;

import java.time.LocalDateTime;

/**
 * Class Log which represents entity for log
 * @author: Srdan Ristic 
 * @date: Apr 9th 2018
 */
public class Log {
    // Instance variables
    private LocalDateTime date;
    private String IP;
    private String request;
    private int status;
    private String userAgent;

    /**
     * Default constructor
     */
    public Log() {

    }

    /**
     * Overridden constructor
     *
     * @param date
     * @param IP
     * @param request
     * @param status
     * @param userAgent
     */
    public Log(LocalDateTime date, String IP, String request, int status, String userAgent) {
        this.date = date;
        this.IP = IP;
        this.request = request;
        this.status = status;
        this.userAgent = userAgent;
    }

    /**
     * Getters and setters
     *
     * @return
     */
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "Log{" +
                "date=" + date +
                ", IP='" + IP + '\'' +
                ", request='" + request + '\'' +
                ", status=" + status +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}
