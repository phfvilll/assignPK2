package com.acme.contram.userinterface;

import com.acme.contram.service.ScheduleService;
import com.vaadin.flow.component.UI;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainViewTest {

    private ScheduleService scheduleService;
    private MainView mainview;
    private UI ui;

    @BeforeAll
    void beforeAll() {
        this.ui = new UI();
        this.scheduleService = new ScheduleService();
        this.mainview = new MainView(this.scheduleService);
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("test app header")
    void testAppHeader() {
        // given: main header
        // when: user interface instantiated
        // then: the main header should be created with the name of the app
        Assertions.assertEquals("ConTraM",this.mainview.getHeaderAppText());
    }

    @Test
    @DisplayName("test purpose header")
    void testPurposeHeader() {
        // given: second header
        // when: user interface instantiated
        // then: the second header should be created to show the purpose of the app
        Assertions.assertEquals("Conference Track Management",this.mainview.getHeaderPurposeText());
    }

    @Test
    @DisplayName("test input text area")
    void testInputTextArea() {
        // given: text area
        // when: user interface instantiated
        // then: the text area should be ready for input
        this.mainview.setTextAreaInput("Overdoing it in Python 45min");

        Assertions.assertEquals("Overdoing it in Python 45min",this.mainview.getTextAreaInputString());
    }

    @Test
    @DisplayName("test label and span")
    void testLabelAndSpan() {
        // given: a label and a span
        // when: user interface instantiated
        // then: the label and the span should be ready to receive text value
        this.mainview.setLabelOutputText("No talks to schedule.");

        this.mainview.setSpanOutputText("Track 1:");

        Assertions.assertEquals("No talks to schedule.",this.mainview.getLabelOutputText());

        Assertions.assertEquals("Track 1:",this.mainview.getSpanOutputText());
    }

    @Test
    @DisplayName("test button with zero errors")
    void testButtonWithZeroErrors() {
        // given: the input area is empty
        this.mainview.setTextAreaInput("");
        this.mainview.setSpanOutputText("");
        // when: button is clicked
        UI.setCurrent(ui);
        this.mainview.getButtonSchedule().click();
        // then: no schedule should be created, no record in the output span with a no-schedule-header in the
        // output label and a notification with a zero-error-message
        Assertions.assertNotNull(this.mainview.getButtonSchedule());
        Assertions.assertEquals("No talks to schedule.",this.mainview.getLabelOutputText());

        Assertions.assertEquals("",this.mainview.getSpanOutputText());

        Assertions.assertEquals("Scheduling process completed with 0 errors.",this.mainview.getNotification());
    }

    @Test
    @DisplayName("test button with one error")
    void testButtonWithOneError() {
        // given: the input area contains a proposal with an error
        this.mainview.setTextAreaInput("Writing Fast Tests Against Enterprise Rails 60min\n"+
                                        "Overdoing it in Python 2 45min");
        // when: button is clicked
        UI.setCurrent(ui);
        this.mainview.getButtonSchedule().click();
        // then: a schedule should be created that is recorded in the output span with a schedule-header in the
        // output label and a notification with a one-error-message
        Assertions.assertEquals("Conference Schedule:",this.mainview.getLabelOutputText());

        Assertions.assertNotNull(this.mainview.getSpanOutputText());

        Assertions.assertEquals("Track 1:<br>09:00AM Writing Fast Tests Against Enterprise Rails 60min<br>" +
                "12:00PM Lunch<br>04:00PM Networking Event<br>" +
                "&nbsp;",this.mainview.getSpanOutputText());

        Assertions.assertEquals("Scheduling process completed with 1 error. Please check the format " +
                "of your proposals and try again to capture ignored proposals.",this.mainview.getNotification());
    }

    @Test
    @DisplayName("test button with multiple errors")
    void testButtonWithMultipleErrors() {
        // given: the input area contains multiple proposals with multiple errors
        this.mainview.setTextAreaInput("Writing Fast Tests Against Enterprise Rails 60min\n"+
                "Overdoing it in Python 2 45min\n"+
                "Lua for the Masses 30min\n"+
                "Ruby Errors from Mismatched Gem Versions 45min\n"+
                "Common Ruby Errors 45min\n"+
                "Rails for Python Developers lightning\n"+
                "Communicating Over Distance 60min\n"+
                "Accounting-Driven Development 45min\n"+
                "Woah 30min\n"+
                " \n"+
                "   \n"+
                "23min\n"+
                "Hello 2x1min\n"+
                "Talk title 21minXY\n"+
                " \n"+
                "Sit Down and Write 30min\n"+
                "Pair Programming vs Noise 45min\n"+
                "Rails Magic 60min\n"+
                "Ruby on Rails: Why We Should Move On 60min\n"+
                "Clojure Ate Scala (on my project) 45min\n"+
                "Programming in the Boondocks of Seattle 30min\n"+
                "Ruby vs. Clojure for Back-End Development 30min\n"+
                "Ruby on Rails Legacy App Maintenance 60min\n"+
                "A World Without HackerNews 30min\n"+
                "User Interface CSS in Rails Apps 30min");
        // when: button is clicked
        UI.setCurrent(ui);
        this.mainview.getButtonSchedule().click();
        // then: a schedule should be created that is recorded in the output span with a schedule-header in the
        // output label and a notification with a one-error-message
        Assertions.assertEquals("Conference Schedule:",this.mainview.getLabelOutputText());

        Assertions.assertEquals(
                "Track 1:<br>" +
                        "09:00AM Writing Fast Tests Against Enterprise Rails 60min<br>" +
                        "10:00AM Communicating Over Distance 60min<br>" +
                        "11:00AM Rails Magic 60min<br>" +
                        "12:00PM Lunch<br>" +
                        "01:00PM Ruby on Rails: Why We Should Move On 60min<br>" +
                        "02:00PM Ruby on Rails Legacy App Maintenance 60min<br>" +
                        "03:00PM Ruby Errors from Mismatched Gem Versions 45min<br>" +
                        "03:45PM Common Ruby Errors 45min<br>" +
                        "04:30PM Lua for the Masses 30min<br>" +
                        "05:00PM Networking Event<br>" +
                        " <br>" +
                        "Track 2:<br>" +
                        "09:00AM Accounting-Driven Development 45min<br>" +
                        "09:45AM Pair Programming vs Noise 45min<br>" +
                        "10:30AM Clojure Ate Scala (on my project) 45min<br>" +
                        "11:15AM Woah 30min<br>" +
                        "11:45AM Rails for Python Developers lightning<br>" +
                        "12:00PM Lunch<br>" +
                        "01:00PM Sit Down and Write 30min<br>" +
                        "01:30PM Programming in the Boondocks of Seattle 30min<br>" +
                        "02:00PM Ruby vs. Clojure for Back-End Development 30min<br>" +
                        "02:30PM A World Without HackerNews 30min<br>" +
                        "03:00PM User Interface CSS in Rails Apps 30min<br>" +
                        "05:00PM Networking Event<br>&nbsp;"
                ,this.mainview.getSpanOutputText());

        Assertions.assertEquals("Scheduling process completed with 4 errors. " +
                "Please check the format of your proposals and try again to capture ignored proposals."
                ,this.mainview.getNotification());
    }

    @Test
    @DisplayName("test button with one proposal")
    void testButtonWithOneProposal() {
        // given: the input area contains one proposal
        this.mainview.setTextAreaInput("Writing Fast Tests Against Enterprise Rails 60min");
        // when: button is clicked
        UI.setCurrent(ui);
        this.mainview.getButtonSchedule().click();
        // then: no schedule should be created, no record in the output span with a no-schedule-header in the
        // output label and a notification with a zero-error-message

        Assertions.assertEquals("Conference Schedule:",this.mainview.getLabelOutputText());

        Assertions.assertEquals("Track 1:<br>" +
                "09:00AM Writing Fast Tests Against Enterprise Rails 60min<br>" +
                "12:00PM Lunch<br>" +
                "04:00PM Networking Event<br>" +
                "&nbsp;",this.mainview.getSpanOutputText());

        Assertions.assertEquals("Scheduling process completed with 0 errors.",this.mainview.getNotification());
    }
}
