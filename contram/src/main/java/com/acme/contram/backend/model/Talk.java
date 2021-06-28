package com.acme.contram.backend.model;

public class Talk {

    private String title;
    private int duration;

    public Talk(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }
}
