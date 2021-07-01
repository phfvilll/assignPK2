package com.acme.contram.service.model;

import java.time.LocalTime;

/**
 * Track combines scheduled talks in a morning session and an afternoon session.
 * Track is the top of the model hierarchy in the current version.
 * @author  Philipp Kraatz
 * @version 1.0
 */
public class Track {

    private Session morningSession;
    private Session afternoonSession;

    /**
     * The constructor of Track initializes the morning session and the afternoon session.
     * The morning session is defined by a time constraint of 180 minutes, starting at 9AM and ending at 12PM
     * with Lunch as last Event.
     * The afternoon session is defined by a time constraint of 240 minutes, starting at 1PM and is initialized
     * with an ending at 4PM with a Networking Event.
     * Through the time constraint of 240 minutes, the real end of the afternoon session could be postponed
     * until 5PM.
     */
    public Track() {
        this.morningSession = new Session(180,LocalTime.of(9,00),
                LocalTime.of(12,00),"Lunch");
        this.afternoonSession = new Session(240,LocalTime.of(13,00),
                LocalTime.of(16,00),"Networking Event");
    }

    /**
     * {@return the morning session of the track}
     */
    public Session getMorningSession() {
        return morningSession;
    }

    /**
     * {@return the afternoon session of the track}
     */
    public Session getAfternoonSession() {
        return afternoonSession;
    }
}
