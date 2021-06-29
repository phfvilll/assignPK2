package com.acme.contram.service.model;

/**
 * Track combines scheduled talks in a morning session and an afternoon session and is distinguishable by a track number.
 * Track is the top of the object model hierarchy in the current version.
 * @author  philippk
 * @version 1.0
 */
public class Track {

    private int trackNumber;
    private Session morningSession;
    private Session afternoonSession;

    /**
     *
     * @param trackNumber
     * @param morningSession
     * @param afternoonSession
     */
    public Track(int trackNumber, Session morningSession, Session afternoonSession) {
        this.trackNumber = trackNumber;
        this.morningSession = morningSession;
        this.afternoonSession = afternoonSession;
    }

    /**
     *
     * @return
     */
    public int getTrackNumber() { return trackNumber; }

    /**
     *
     * @return
     */
    public Session getMorningSession() {
        return morningSession;
    }

    /**
     *
     * @return
     */
    public Session getAfternoonSession() {
        return afternoonSession;
    }
}
