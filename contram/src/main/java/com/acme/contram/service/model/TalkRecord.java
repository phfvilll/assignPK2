package com.acme.contram.service.model;

import java.time.LocalTime;

/**
 * TalkRecord makes an unplanned talk able to be scheduled for a session by assigning a time of date to the
 * specific talk.
 * @author  Philipp Kraatz
 * @version 1.0
 * @see     Session
 */
public class TalkRecord {

    private Talk talk;
    private LocalTime timeOfDate;

    /**
     * The constructor instantiate a talk record by assigning a talk to it including the date of time at which
     * the talk takes place.
     * @param talk
     * @param timeOfDate
     */
    public TalkRecord(Talk talk, LocalTime timeOfDate) {
        this.talk = talk;
        this.timeOfDate = timeOfDate;
    }

    /**
     * {@return the talk that is scheduled by the talkRecord.}
     */
    public Talk getTalk() {
        return talk;
    }

    /**
     * {@return the time at which the talk takes place.}
     */
    public LocalTime getTimeOfDate() {
        return timeOfDate;
    }
}
