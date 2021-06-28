package com.acme.contram.backend.model;

public class Track {

    private int trackNumber;
    private Session morningSession;
    private Session afternoonSession;

    public Track(int trackNumber, Session morningSession, Session afternoonSession) {
        this.trackNumber = trackNumber;
        this.morningSession = morningSession;
        this.afternoonSession = afternoonSession;
    }

    public int getTrackNumber() { return trackNumber; }

    public Session getMorningSession() {
        return morningSession;
    }

    public Session getAfternoonSession() {
        return afternoonSession;
    }
}
