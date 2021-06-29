package com.acme.contram.service.model;

import java.util.LinkedList;
import java.time.LocalTime;

/**
 * Session serves as a container for scheduled Talks(TalkRecords) to organize the morning session and the afternoon session of a track.
 * Every Session is limited by a time constraint and ends with a closing event, that show up as a property defined by title and time of date.
 * @author  philippk
 * @version 1.0
 * @see     Track
 */
public class Session {

    private LinkedList<TalkRecord> records;
    private int timeLeft;
    private String lastEventTitle;
    private LocalTime lastEventTime;

    /**
     *
     * @param timeLeft
     * @param lastEventTitle
     * @param lastEventTime
     */
    public Session(int timeLeft, String lastEventTitle, LocalTime lastEventTime) {
        this.records = new LinkedList<TalkRecord>();
        this.timeLeft = timeLeft;
        this.lastEventTitle = lastEventTitle;
        this.lastEventTime = lastEventTime;
    }

    /**
     *
     * @return
     */
    public LinkedList<TalkRecord> getRecords() { return records; }

    /**
     *
     * @return
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     *
     * @param timeLeft
     */
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     *
     * @return
     */
    public String getLastEventTitle() { return lastEventTitle; }

    /**
     *
     * @return
     */
    public LocalTime getLastEventTime() { return lastEventTime; }

    /**
     *
     * @param lastEventTime
     */
    public void setLastEventTime(LocalTime lastEventTime) {
        this.lastEventTime = lastEventTime;
    }
}
