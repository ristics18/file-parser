package com.ef.Entities;

import com.ef.Util.Constants;
import com.ef.Util.Utility;

import java.time.LocalDateTime;

/**
 * Class CmdArgs represents entity for command line arguments
 * @author: Srdan Ristic 
 * @date: Apr 9th 2018
 */
public class CmdArgs {
    // Instance variables
    private String accesslog;
    private LocalDateTime startDate;
    private String duration;
    private int threshold;

    /**
     * Default constructor
     */
    public CmdArgs() {

    }

    /**
     * Getters and Setters
     */
    public String getAccesslog() {
        return accesslog;
    }

    public void setAccesslog(String accesslog) {
        this.accesslog = accesslog;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        switch (duration) {
            case Constants.DURATION_HOURLY:
                this.duration = duration;
                break;
            case Constants.DURATION_DAILY:
                this.duration = duration;
                break;
            default:
                Utility.exit("Duration has to be either hourly or daily.");
        }

        this.duration = duration;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        if (threshold <= 0) {
            Utility.exit("Threshold needs to be grater than 0.");
        }
        this.threshold = threshold;
    }

    /**
     * Method for checking if all arguments are set
     *
     * @return
     */
    public boolean allSet() {
        if (this.accesslog != null && this.startDate != null && this.duration != null && this.threshold != 0) {
            return true;
        }
        return false;
    }

    /**
     * toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "CmdArgs{" +
                "accesslog='" + accesslog + '\'' +
                ", startDate=" + startDate +
                ", duration='" + duration + '\'' +
                ", threshold=" + threshold +
                '}';
    }
}
