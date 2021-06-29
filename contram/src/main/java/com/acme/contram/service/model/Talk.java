package com.acme.contram.service.model;

/**
 * Talk consists of a title and a duration and can be scheduled for a session used by a TalkRecord.
 * Talk duration is comparable.
 * @author  philippk
 * @version 1.0
 * @see     TalkRecord
 */
public class Talk implements Comparable<Talk> {

    private String title;
    private Integer duration;

    /**
     *
     * @param title
     * @param duration
     */
    public Talk(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Talk o) {
        return this.duration.compareTo(o.getDuration());
    }
}
