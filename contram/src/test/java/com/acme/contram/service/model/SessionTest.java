package com.acme.contram.service.model;

import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.LinkedList;

class SessionTest {

    private Session morningSession;
    private Session afternoonSession;

    @BeforeEach
    void setUp() {
        this.morningSession = new Session(180, LocalTime.of(9,00),
                LocalTime.of(12,00),"Lunch");
        this.afternoonSession = new Session(240,LocalTime.of(13,00),
                LocalTime.of(16,00),"Networking Event");
    }

    @Test
    @DisplayName("test start time of morning session")
    void testStartTimeOfMorningSession() {
        // given: a morning session;
        // when: initialized;
        // then: start time of the morning session should be 9AM
        Assertions.assertEquals(LocalTime.of(9,00), morningSession.getStartTime());
    }

    @Test
    @DisplayName("test time left of morning session")
    void testTimeLeftOfMorningSession() {
        // given: a morning session;
        // when: initialized;
        // then: time left to schedule of the morning session should be 3 hours(180 minutes)
        Assertions.assertEquals(180, morningSession.getTimeLeft());
    }

    @Test
    @DisplayName("test last event of morning session")
    void testLastEventOfMorningSession() {
        // given: a morning session;
        // when: initialized;
        // then: last event time of the morning session should be 12PM and last event title should be lunch
        Assertions.assertEquals(LocalTime.of(12,00), morningSession.getLastEventTime());
        Assertions.assertEquals("Lunch", morningSession.getLastEventTitle());
    }

    @Test
    @DisplayName("test time constraint of morning session")
    void testTimeConstraintOfMorningSession() {
        // given: list of talks;
        LinkedList<Talk> talks = new LinkedList<Talk>();
        talks.add(new Talk("Writing Fast Tests Against Enterprise Rails",60));
        talks.add(new Talk("Overdoing it in Python",45));
        talks.add(new Talk("Lua for the Masses",30));
        talks.add(new Talk("Rails for Python Developers",5));
        talks.add(new Talk("Communicating Over Distance",60));
        talks.add(new Talk("Accounting-Driven Development",45));
        talks.add(new Talk("Woah",30));

        // when: talks scheduled to morning session until no time left;
        for(int i = 0; i < talks.size();i++){
            morningSession.addTalk(talks.get(i));
        }

        // then: sum of talk durations should be less than 3 hours
        Assertions.assertTrue(morningSession.getTimeLeft()>=0);

        // test if scheduling worked
        Assertions.assertEquals("Writing Fast Tests Against Enterprise Rails",
                morningSession.getScheduledTalks().get(0).getTitle());
        Assertions.assertEquals(60, morningSession.getScheduledTalks().get(0).getDuration());
        Assertions.assertEquals(LocalTime.of(9,00), morningSession.getBlockedTimeOfDay().getFirst());


        int sum = 0;
        // sum up all scheduled talk durations of the session
        for(int i = 0; i < morningSession.getScheduledTalks().size() ; i++){
            sum += morningSession.getScheduledTalks().get(i).getDuration();
        }

        Assertions.assertTrue(sum<=180);
    }

    @Test
    @DisplayName("test start time of afternoon session")
    void testStartTimeOfAfternoonSession() {
        // given: an afternoon session;
        // when: initialized;
        // then: start time of the afternoon session should be 1PM
        Assertions.assertEquals(LocalTime.of(13,00), afternoonSession.getStartTime());
    }

    @Test
    @DisplayName("test time left of afternoon session")
    void testTimeLeftOfAfternoonSession() {
        // given: an afternoon session;
        // when: initialized;
        // then: time left to schedule of the afternoon session should be 4 hours(240 minutes)
        Assertions.assertEquals(240, afternoonSession.getTimeLeft());
    }

    @Test
    @DisplayName("test last event of afternoon session")
    void testLastEventOfAfternoonSession() {
        // given: list of talks;
        LinkedList<Talk> talks = new LinkedList<Talk>();
        talks.add(new Talk("Writing Fast Tests Against Enterprise Rails",60));
        talks.add(new Talk("Overdoing it in Python",45));
        talks.add(new Talk("Lua for the Masses",30));
        talks.add(new Talk("Rails for Python Developers",5));
        talks.add(new Talk("Communicating Over Distance",60));
        talks.add(new Talk("Accounting-Driven Development",45));
        talks.add(new Talk("Woah",30));

        // when: all talks are scheduled;
        // then: last event time of the afternoon session should be between 4PM and 5PM and
        // last event title should be Network Event

        // last event time before scheduling
        Assertions.assertEquals(LocalTime.of(16,00), afternoonSession.getLastEventTime());
        Assertions.assertEquals("Networking Event", afternoonSession.getLastEventTitle());

        // talks scheduled to afternoon session until no time left;
        for(int i = 0; i < talks.size();i++){
            afternoonSession.addTalk(talks.get(i));
        }

        // last event time after scheduling should now be at 4:50PM
        Assertions.assertEquals(LocalTime.of(16,50), afternoonSession.getLastEventTime());

        // clock hand and last Event time should be the same without manually manipulation
        Assertions.assertEquals(afternoonSession.getClockHand(), afternoonSession.getLastEventTime());

        // setting the last Event time manually
        afternoonSession.setLastEventTime(LocalTime.of(17,00));
        Assertions.assertEquals(LocalTime.of(17,00), afternoonSession.getLastEventTime());
    }

    @Test
    @DisplayName("test time constraint of afternoon session")
    void testTimeConstraintOfAfternoonSession() {
        // given: list of talks;
        LinkedList<Talk> talks = new LinkedList<Talk>();
        talks.add(new Talk("Writing Fast Tests Against Enterprise Rails",60));
        talks.add(new Talk("Overdoing it in Python",45));
        talks.add(new Talk("Lua for the Masses",30));
        talks.add(new Talk("Rails for Python Developers",5));
        talks.add(new Talk("Communicating Over Distance",60));
        talks.add(new Talk("Accounting-Driven Development",45));
        talks.add(new Talk("Woah",30));

        // when: talks scheduled to afternoon session until no time left;
        for(int i = 0; i < talks.size();i++){
            afternoonSession.addTalk(talks.get(i));
        }

        // then: sum of talk durations should be less than 4 hours
        Assertions.assertTrue(afternoonSession.getTimeLeft()>=0);

        // test if scheduling worked
        Assertions.assertEquals("Writing Fast Tests Against Enterprise Rails",
                afternoonSession.getScheduledTalks().get(0).getTitle());
        Assertions.assertEquals(60, afternoonSession.getScheduledTalks().get(0).getDuration());
        Assertions.assertEquals(LocalTime.of(9,00), afternoonSession.getBlockedTimeOfDay().getFirst());

        int sum = 0;
        // sum up all scheduled talk durations of the session
        for(int i = 0; i < afternoonSession.getScheduledTalks().size() ; i++){
            sum += afternoonSession.getScheduledTalks().get(i).getDuration();
        }

        Assertions.assertTrue(sum<=240);
    }
}