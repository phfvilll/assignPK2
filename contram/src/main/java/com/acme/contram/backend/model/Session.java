package com.acme.contram.backend.model;

import java.util.LinkedList;
import com.philippk.cotrama.model.TalkRecord;

public class Session {

    private LinkedList<TalkRecord> records;
    private int timeLeft;
    private String lastEvent;

    public Session(LinkedList<TalkRecord> records, int timeLeft, String lastEvent) {
        this.records = records;
        this.timeLeft = timeLeft;
        this.lastEvent = lastEvent;
    }

    public LinkedList<TalkRecord> getRecords() {
        return records;
    }

    public void setRecords(LinkedList<TalkRecord> records) {
        this.records = records;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(String lastEvent) {
        this.lastEvent = lastEvent;
    }
}
