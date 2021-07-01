package com.acme.contram.service.model;

import org.junit.jupiter.api.*;

class TalkTest {

    @Test
    @DisplayName("test getting talk title and duration from a title")
    void testGettingTitleAndDuration() {
        // given: talk;

        // when: initialized;
        Talk talk = new Talk("Rails Magic",60);

        // then: talk title and duration of the talk should be accessible
        Assertions.assertEquals("Rails Magic",talk.getTitle());
        Assertions.assertEquals(60,talk.getDuration());
    }

    @Test
    @DisplayName("test compare duration of two talks")
    void testCompareTalks() {
        //given: two talks with different duration;
        Talk talk1 = new Talk("Common Ruby Errors",45);
        Talk talk2 = new Talk("Lua for the Masses",30);
        Talk talk3 = new Talk("Sit Down and Write",30);

        //when: compared;

        //then: one talk have to be shorter than the other

        // return 1 if parameter is shorter
        Assertions.assertEquals(1,talk1.compareTo(talk2));
        // return -1 if parameter is longer
        Assertions.assertEquals(1,talk1.compareTo(talk2));
        // return 0 if parameter has the same duration
        Assertions.assertEquals(0,talk2.compareTo(talk3));
        Assertions.assertEquals(0,talk3.compareTo(talk2));
    }
}