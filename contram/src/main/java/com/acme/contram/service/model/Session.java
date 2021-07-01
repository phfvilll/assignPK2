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
    private LocalTime clockHand;

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
        this.clockHand = startTime;
        this.lastEventTime = lastEventTime;
        this.lastEventTitle = lastEventTitle;
    }

    /**
     * Schedule a talk if enough time is available to fit it into the session.
     * if the talk fits, then assign it to the session together with the time
     * of date and reducing the time which left to the session by the duration
     * of the new scheduled talk.
     * @param talk
     * @return true if the talk was added and false if there is not enough time
     * to fit the talk into the session.
     */
    public boolean addTalk(Talk talk){

        // schedule a talk if enough time is available to fit it into the session
        if(talk.getDuration()<=this.timeLeft) {

            // the talk fits in, so assign it to the session
            this.scheduledTalks.add(talk);
            this.blockedTimeOfDay.add(clockHand);

            // block the time for the new talk
            clockHand = clockHand.plusMinutes(talk.getDuration());

            // postpone the last event of the afternoon session up to 5PM
            if(clockHand.isAfter(LocalTime.of(16,00))){
                this.lastEventTime = clockHand;
            }

            // reducing the time which left to the session by the duration of the new
            // scheduled talk
            this.timeLeft -= talk.getDuration();

            return true;

        } else {

            return false;
        }
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
     * {@return the time of date when the session begins.}
     */
    public LocalTime getStartTime() { return startTime; }

    /**
     * {@return the next possible time of date for the next talk or event.}
     */
    public LocalTime getClockHand() { return clockHand; }

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
