package com.acme.contram.service.model;

/**
 * Talk consists of a title and a duration and can be scheduled for a session.
 * Talk duration is comparable.
 * Talk is the bottom of the model hierarchy in the current version.
 * @author  Philipp Kraatz
 * @version 1.0
 * @see     Session
 */
public class Talk implements Comparable<Talk> {

    private String title;
    private Integer duration;

    /**
     * The constructor of Talk initializes the talk title and the duration of the talk.
     * @param title
     * @param duration
     */
    public Talk(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    /**
     * {@return the talk title}.
     */
    public String getTitle() {
        return title;
    }

    /**
     * {@return the duration of the talk}.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * The method returns 1 if the compared parameter has a shorter duration than the duration-property
     * of the object and returns -1 if the compared parameter has a longer duration than the duration-property
     * of the object.
     * @param talk
     * @return 1 if parameter-talk is shorter or -1 if parameter-talk is longer
     */
    @Override
    public int compareTo(Talk talk) { return this.duration.compareTo(talk.getDuration()); }
}
