package com.philippk.cotrama.model;

import com.acme.contram.backend.model.Talk;

import java.time.LocalTime;
import java.util.Date;

public class TalkRecord {

    private Talk talk;
    private LocalTime timeOfDate;

    public TalkRecord(Talk talk, LocalTime timeOfDate) {
        this.talk = talk;
        this.timeOfDate = timeOfDate;
    }

    public Talk getTalk() {
        return talk;
    }

    public LocalTime getTimeOfDate() {
        return timeOfDate;
    }
}
