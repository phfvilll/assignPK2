package com.acme.contram.service.model;

import java.util.LinkedList;
import java.time.LocalTime;

/**
 * Session contains scheduled Talks and their blocked time of day to structure the sessions of a track.
 * Every Session is limited by a time constraint that begins at a start time and ends with a closing event,
 * that is bound as a property to the session defined by title and time of date.
 * @author  Philipp Kraatz
 * @version 1.0
 * @see     Track
 */
public class Session {

    private LinkedList<Talk> scheduledTalks;

    private LinkedList<LocalTime> blockedTimeOfDay;
    private int timeLeft;
    private LocalTime startTime;
    private LocalTime lastEventTime;
    private String lastEventTitle;

    /**
     * The constructor instantiate a session by initializing a list of talks and a list of their blocked time
     * to schedule talks.
     * The constructor als sets the time constraint of the session which represents the amount of time that
     * is left to fit talks into the session.
     * In addition the beginning of the session is determined and the closing event of the session is defined
     * by title and the time at which it takes place.
     * @param timeLeft
     * @param startTime
     * @param lastEventTime
     * @param lastEventTitle
     */
    public Session(int timeLeft, LocalTime startTime, LocalTime lastEventTime, String lastEventTitle) {
        this.scheduledTalks = new LinkedList<Talk>();
        this.blockedTimeOfDay = new LinkedList<LocalTime>();
        this.timeLeft = timeLeft;
        this.startTime = startTime;
        this.lastEventTime = lastEventTime;
        this.lastEventTitle = lastEventTitle;
    }

    /**
     * {@return a list of all talks that the session contains.}
     */
    public LinkedList<Talk> getScheduledTalks() { return scheduledTalks; }

    /**
     * {@return a list of the blocked time of date of all talks that the session contains.}
     */
    public LinkedList<LocalTime> getBlockedTimeOfDay() { return blockedTimeOfDay; }

    /**
     * {@return the amount of time that is left to fit talks into the session.}
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * Update the amount of time that is left to fit talks into the session.
     * @param timeLeft
     */
    public void setTimeLeft(int timeLeft) {
        // avoid negative time values
        if(timeLeft < 0){
            this.timeLeft = 0;
        }
        this.timeLeft = timeLeft;
    }

    /**
     * {@return the time of date when the session begins.}
     */
    public LocalTime getStartTime() { return startTime; }

    /**
     * @return the time of date when the session is over and the last event begins.}
     */
    public LocalTime getLastEventTime() { return lastEventTime; }

    /**
     * update the start time of the last event.
     * @param lastEventTime
     */
    public void setLastEventTime(LocalTime lastEventTime) { this.lastEventTime = lastEventTime; }

    /**
     * {@return the title of the last event of the session as a String.}
     */
    public String getLastEventTitle() { return lastEventTitle; }
}
