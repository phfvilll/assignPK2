package com.acme.contram.backend.model;

import java.time.LocalTime;

/**
 * TalkRecord makes an unplanned talk able to be scheduled for a session by assigning a time of date to the talk.
 * @author  philippk
 * @version 1.0
 * @see     Session
 */
public class TalkRecord {

    private Talk talk;
    private LocalTime timeOfDate;

    /**
     *
     * @param talk
     * @param timeOfDate
     */
    public TalkRecord(Talk talk, LocalTime timeOfDate) {
        this.talk = talk;
        this.timeOfDate = timeOfDate;
    }

    /**
     *
     * @return
     */
    public Talk getTalk() {
        return talk;
    }

    /**
     *
     * @return
     */
    public LocalTime getTimeOfDate() {
        return timeOfDate;
    }
}
