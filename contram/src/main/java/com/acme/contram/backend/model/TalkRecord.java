package com.philippk.cotrama.model;

import com.acme.contram.backend.model.Talk;

import java.util.Date;

public class TalkRecord {

    private Talk talk;
    private Date timeOfDate;

    public TalkRecord(Talk talk, Date timeOfDate) {
        this.talk = talk;
        this.timeOfDate = timeOfDate;
    }

    public Talk getTalk() {
        return talk;
    }

    public Date getTimeOfDate() {
        return timeOfDate;
    }
}
