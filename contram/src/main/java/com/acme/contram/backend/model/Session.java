package com.acme.contram.backend.model;

import java.util.Calendar;
import java.util.LinkedList;
import java.time.LocalTime;
import com.philippk.cotrama.model.TalkRecord;

public class Session {

    private LinkedList<TalkRecord> records;
    private int timeLeft;
    private String lastEventTitle;
    private LocalTime lastEventTime;

    public Session(int timeLeft, String lastEventTitle, LocalTime lastEventTime) {
        this.records = new LinkedList<TalkRecord>();
        this.timeLeft = timeLeft;
        this.lastEventTitle = lastEventTitle;
        this.lastEventTime = lastEventTime;
    }

    public LinkedList<TalkRecord> getRecords() {
        return records;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getLastEventTitle() { return lastEventTitle; }

    public LocalTime getLastEventTime() { return lastEventTime; }
}
