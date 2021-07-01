package com.acme.contram.service.model;

import org.junit.jupiter.api.*;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TrackTest {

    private Track track;

    @BeforeEach
    void setUp() {
        track = new Track();
    }

    @Test
    @DisplayName("test getting the morning session and the afternoon session of a track")
    void testGetSessions() {
        //given: track;

        //when: initialized;

        //then: morning session and afternoon session of the track should be accessible

        // access to morning session of the track
        Assertions.assertEquals(LocalTime.of(9,00), track.getMorningSession().getStartTime());
        Assertions.assertEquals(180, track.getMorningSession().getTimeLeft());
        Assertions.assertEquals(LocalTime.of(12,00), track.getMorningSession().getLastEventTime());
        Assertions.assertEquals("Lunch", track.getMorningSession().getLastEventTitle());

        // access to afternoon session of the track
        Assertions.assertEquals(LocalTime.of(13,00), track.getAfternoonSession().getStartTime());
        Assertions.assertEquals(240,track.getAfternoonSession().getTimeLeft());
        Assertions.assertEquals(LocalTime.of(16,00), track.getAfternoonSession().getLastEventTime());
        Assertions.assertEquals("Networking Event", track.getAfternoonSession().getLastEventTitle());
    }
}