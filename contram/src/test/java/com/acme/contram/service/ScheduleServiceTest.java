package com.acme.contram.service;

import org.junit.jupiter.api.*;

class ScheduleServiceTest  {

    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        this.scheduleService = new ScheduleService();
    }

    @Test
    @DisplayName("test converting proposals to a schedule")
    void testCreateSchedule() {
        // given: array of proposal-lines
        String[] proposalLines = {
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Overdoing it in Python 45min",
                "Lua for the Masses 30min",
                "Ruby Errors from Mismatched Gem Versions 45min",
                "Common Ruby Errors 45min",
                "Rails for Python Developers lightning",
                "Communicating Over Distance 60min",
                "Accounting-Driven Development 45min",
                "Woah 30min",
                "Sit Down and Write 30min",
                "Pair Programming vs Noise 45min",
                "Rails Magic 60min",
                "Ruby on Rails: Why We Should Move On 60min",
                "Clojure Ate Scala (on my project) 45min",
                "Programming in the Boondocks of Seattle 30min",
                "Ruby vs. Clojure for Back-End Development 30min",
                "Ruby on Rails Legacy App Maintenance 60min",
                "A World Without HackerNews 30min",
                "User Interface CSS in Rails Apps 30min"};

        // when: scheduling completed (used method should schedule the talks with the longest duration first)
        String[] schedule = scheduleService.createSchedule(proposalLines);

        // then: array of schedule-lines is received in sequence of used method (longest talks first)
        String[] scheduleLines = {"0","Track 1:",
                "09:00AM Writing Fast Tests Against Enterprise Rails 60min",
                "10:00AM Communicating Over Distance 60min",
                "11:00AM Rails Magic 60min",
                "12:00PM Lunch",
                "01:00PM Ruby on Rails: Why We Should Move On 60min",
                "02:00PM Ruby on Rails Legacy App Maintenance 60min",
                "03:00PM Overdoing it in Python 45min",
                "03:45PM Ruby Errors from Mismatched Gem Versions 45min",
                "04:30PM Lua for the Masses 30min",
                "05:00PM Networking Event",
                " ",
                "Track 2:",
                "09:00AM Common Ruby Errors 45min",
                "09:45AM Accounting-Driven Development 45min",
                "10:30AM Pair Programming vs Noise 45min",
                "11:15AM Clojure Ate Scala (on my project) 45min",
                "12:00PM Lunch",
                "01:00PM Woah 30min",
                "01:30PM Sit Down and Write 30min",
                "02:00PM Programming in the Boondocks of Seattle 30min",
                "02:30PM Ruby vs. Clojure for Back-End Development 30min",
                "03:00PM A World Without HackerNews 30min",
                "03:30PM User Interface CSS in Rails Apps 30min",
                "04:00PM Rails for Python Developers lightning",
                "05:00PM Networking Event"};

        // test if the created schedule has the expected length
        Assertions.assertEquals(scheduleLines.length,schedule.length);

        // test if the created schedule has the expected content
        Assertions.assertArrayEquals(scheduleLines,schedule);
    }

    @Test
    @DisplayName("test creating no track")
    void testCreateNoTrack() {
        // given: empty array of proposals
        String[] proposalLines = {};

        // when: no talks to schedule
        String[] schedule = scheduleService.createSchedule(proposalLines);

        // then: zero tracks should be received

        // the number of free lines in the output schedule lines indicates how
        // many tracks are created (there should be "[number of free lines]+1" tracks
        // when the received schedule contains more than one line, otherwise zero tracks)
        int numberOfTracks = 0;
        if (schedule.length>1){
            numberOfTracks++;
        }
        for (int i = 0; i < schedule.length ; i++){
            if ( schedule[i] == " "){
                numberOfTracks++;
            }
        }

        // test if the created schedule has the expected amount of tracks (in this case: 1)
        Assertions.assertEquals(0,numberOfTracks);
    }

    @Test
    @DisplayName("test creating one track")
    void testCreateOneTrack() {
        // given: array of proposals that would fit in one track
        String[] proposalLines = {
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Overdoing it in Python 45min",
                "Lua for the Masses 30min",
                "Ruby Errors from Mismatched Gem Versions 45min",
                "Common Ruby Errors 45min",
                "Rails for Python Developers lightning",
                "Communicating Over Distance 60min",
                "Accounting-Driven Development 45min",
                "Woah 30min",
                "Sit Down and Write 30min"};

        // when: talks do fit in one track
        String[] schedule = scheduleService.createSchedule(proposalLines);

        // then: one track should be received

        // the number of free lines in the output schedule lines indicates how
        // many tracks are created (there should be "[number of free lines]+1" tracks
        // when the received schedule contains more than one line, otherwise zero tracks)
        int numberOfTracks = 0;
        if (schedule.length>1){
            numberOfTracks++;
        }
        for (int i = 0; i < schedule.length ; i++){
            if ( schedule[i] == " "){
                numberOfTracks++;
            }
        }

        // test if the created schedule has the expected amount of tracks (in this case: 1)
        Assertions.assertEquals(1,numberOfTracks);
    }

    @Test
    @DisplayName("test creating two tracks")
    void testCreateTwoTracks() {
        // given: array of proposals that would not fit in one track
        String[] proposalLines = {
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Overdoing it in Python 45min",
                "Lua for the Masses 30min",
                "Ruby Errors from Mismatched Gem Versions 45min",
                "Common Ruby Errors 45min",
                "Rails for Python Developers lightning",
                "Communicating Over Distance 60min",
                "Accounting-Driven Development 45min",
                "Woah 30min",
                "Sit Down and Write 30min",
                "Programming in the Boondocks of Seattle 30min"};

        // when: talks do not fit in a single track
        String[] schedule = scheduleService.createSchedule(proposalLines);

        // then: a second track should be created

        // the number of free lines in the output schedule lines indicates how
        // many tracks are created (there should be "[number of free lines]+1" tracks
        // when the received schedule contains more than one line, otherwise zero tracks)
        int numberOfTracks = 0;
        if (schedule.length>1){
            numberOfTracks++;
        }
        for (int i = 0; i < schedule.length ; i++){
            if ( schedule[i] == " "){
                numberOfTracks++;
            }
        }

        // test if the created schedule has the expected amount of tracks (in this case: 1)
        Assertions.assertEquals(2,numberOfTracks);
    }

    @Test
    @DisplayName("test creating three tracks")
    void testCreateThreeTracks() {
        // given: array of proposals that would not fit in two tracks
        String[] proposalLines = {
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Overdoing it in Python 45min",
                "Lua for the Masses 30min",
                "Ruby Errors from Mismatched Gem Versions 45min",
                "Common Ruby Errors 45min",
                "Rails for Python Developers lightning",
                "Communicating Over Distance 60min",
                "Accounting-Driven Development 45min",
                "Woah 30min",
                "Sit Down and Write 30min",
                "Pair Programming vs Noise 45min",
                "Rails Magic 60min",
                "Ruby on Rails: Why We Should Move On 60min",
                "Clojure Ate Scala (on my project) 45min",
                "Programming in the Boondocks of Seattle 30min",
                "Ruby vs. Clojure for Back-End Development 30min",
                "Ruby on Rails Legacy App Maintenance 60min",
                "A World Without HackerNews 30min",
                "User Interface CSS in Rails Apps 30min",
                "Writing Fast Tests Against Enterprise Rails 60min",
                "Overdoing it in Python 45min",
                "Lua for the Masses 30min",
                "Ruby Errors from Mismatched Gem Versions 45min",
                "Common Ruby Errors 45min",
                "Rails for Python Developers lightning",
                "Communicating Over Distance 60min",
                "Accounting-Driven Development 45min",
                "Woah 30min"};

        // when: talks do not fit in two tracks
        String[] schedule = scheduleService.createSchedule(proposalLines);

        // then: a third track should be created

        // the number of free lines in the output schedule lines indicates how
        // many tracks are created (there should be "[number of free lines]+1" tracks
        // when the received schedule contains more than one line, otherwise zero tracks)
        int numberOfTracks = 0;
        if (schedule.length>1){
            numberOfTracks++;
        }
        for (int i = 0; i < schedule.length ; i++){
            if ( schedule[i] == " "){
                numberOfTracks++;
            }
        }

        // test if the created schedule has the expected amount of tracks (in this case: 1)
        Assertions.assertEquals(3,numberOfTracks);
    }

    @Test
    @DisplayName("test minute based input format of a proposal")
    void testCheckFormatOfMinuteProposal() {
        // given: proposal in format "[characters without numbers][Free Space][Number]'min'"
        String[] proposal = {
                "Writing Fast Tests Against Enterprise Rails 60min"};

        // when: proposal parsed
        String[] schedule = scheduleService.createSchedule(proposal);

        // then: zero errors should be returned
        Assertions.assertEquals("0",schedule[0]);
    }

    @Test
    @DisplayName("test lightning based input format of a proposal")
    void testCheckFormatOfLightningProposal() {
        // given: proposal in format "[characters without numbers][Free Space]'lightning'"
        String[] proposal = {
                "Rails for Python Developers lightning"};

        // when: proposal parsed
        String[] schedule = scheduleService.createSchedule(proposal);

        // then: zero errors should be returned
        Assertions.assertEquals("0",schedule[0]);
    }

    @Test
    @DisplayName("test proposal with number in talk title")
    void testProposalWithNumberInTalkTitle() {
        // given: proposal with number in talk title
        String[] proposal = {
                "Writing 2 Fast Tests Against Enterprise Rails 60min"};

        // when: proposal parsed
        String[] schedule = scheduleService.createSchedule(proposal);

        // then: An error should be returned
        Assertions.assertEquals("1",schedule[0]);
    }

    @Test
    @DisplayName("test empty lines of proposals")
    void testEmptyLinesOfProposals() {
        // given: proposal with empty lines
        String[] proposal = { "","","   "};

        // when: proposal parsed
        String[] schedule = scheduleService.createSchedule(proposal);

        // then: zero errors should be returned
        Assertions.assertEquals("0",schedule[0]);
    }

    @Test
    @DisplayName("test empty proposals")
    void testEmptyProposals() {
        // given: empty proposal
        String[] proposal = {};

        // when: proposal parsed
        String[] schedule = scheduleService.createSchedule(proposal);

        // then: zero error should be returned
        Assertions.assertEquals("0",schedule[0]);
    }

    @Test
    @DisplayName("test proposal without talk title")
    void testProposalsWithoutTalkTitle() {
        // given: proposal without talk title
        String[] proposal = {"60min"};

        // when: proposal parsed
        String[] schedule = scheduleService.createSchedule(proposal);

        // then: An error should be returned
        Assertions.assertEquals("1",schedule[0]);
    }

    @Test
    @DisplayName("test proposal with incorrect duration")
    void testProposalsWithIncorrectDuration() {
        // given: proposal with incorrect duration
        String[] proposal = {"Writing Fast Tests Against Enterprise Rails 60ßmin"};

        // when: proposal parsed
        String[] schedule = scheduleService.createSchedule(proposal);

        // then: An error should be returned
        Assertions.assertEquals("1",schedule[0]);
    }

    @Test
    @DisplayName("test proposal with duration too long")
    void testProposalsWithDurationTooLong() {
        // given: proposal with duration longer than the sessions;
        String[] proposal = {"Writing Fast Tests Against Enterprise Rails 360min"};

        // when: proposal parsed
        String[] schedule = scheduleService.createSchedule(proposal);

        // then: An error should be returned
        Assertions.assertEquals("1",schedule[0]);
    }
}