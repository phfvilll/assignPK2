package com.acme.contram.backend.model;

public class Talk implements Comparable<Talk> {

    private String title;
    private Integer duration;

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

    @Override
    public int compareTo(Talk o) {
        return this.duration.compareTo(o.getDuration());
    }
}
