package com.acme.contram.userinterface;

import com.acme.contram.service.ScheduleService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;

/**
 * MainView provides a Vaadin-based user interface.
 * The class is the entry point for the application on clientside in a browser.
 * @author  philippk
 * @version 1.0
 */
@Route
@StyleSheet("/styles.css")
public class MainView extends VerticalLayout {

    /**
     *
     */
    public static final String INPUT_PLACEHOLDER = "Please enter your proposals here (one per line)." +
            "\n You can choose between the following 2 formats:" +
            "\n [talk title without numbers] [number between 1 and 240]min" +
            "\n [talk title without numbers] lightning" +
            "\n e.g.:" +
            "\n Innovative Technology Solutions 42min" +
            "\n or:" +
            "\n History of Cloud Engineering lightning" +
            "\n (Note that incorrectly formatted proposals will be ignored during the scheduling process.)";

    private ScheduleService scheduleService;

    private H1 headerApp;
    private H2 headerPurpose;
    private TextArea textAreaInput;
    private Button buttonSchedule;
    private Label labelOutput;
    private Span spanOutput;

    /**
     *
     */
    public MainView(@Autowired ScheduleService scheduleService) {

        System.out.println("Someone load me :)");

        this.scheduleService = scheduleService;

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        addClassName("main-view");

        this.headerApp = new H1("ConTraM");
        headerApp.getElement().getThemeList().add("dark");
        //Object[] test = header.getElement().getThemeList().toArray();
        //System.out.println(test[0].toString());
        add(headerApp);

        this.headerPurpose = new H2("Conference Track Management");
        add(headerPurpose);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        //addClassName("centered-content");
        // This TextArea provides text input of proposals
        this.textAreaInput = new TextArea();
        textAreaInput.setPlaceholder(INPUT_PLACEHOLDER);
        add(textAreaInput);

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        // button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.buttonSchedule = new Button("schedule proposals");
        buttonSchedule.getElement().getThemeList().add("primary");
        add(buttonSchedule);

        this.labelOutput = new Label(" ");
        add(labelOutput);

        // Span to show the schedule
        this.spanOutput = new Span();
        spanOutput.getElement().setProperty("innerHTML","");
        add(spanOutput);

        // Button click listener to trigger the scheduling process
        buttonSchedule.addClickListener(click -> processData());

    }

    private void processData(){

        // splitting the input String line per line by carriage return to get an array of all proposals
        String[] proposals = textAreaInput.getValue().split("\n");

        // send the proposals to the scheduling service to receive a schedule including the amount of errors
        String[] schedule = scheduleService.createSchedule(proposals);

        try {

            // get the amount of errors that occurred by the sent proposals
            int errorCount = Integer.valueOf(schedule[0]);

            // set up a notification message to inform the user about incorrect formatted proposals
            Notification.show(buildMessage(errorCount));

            // check if there is even a created schedule
            if(schedule.length==1) {

                labelOutput.setText("No talks to schedule.");

            } else {

                labelOutput.setText("Conference Schedule:");

                String outputString = "";

                // starting to build the schedule at i = 1, because in field 0 is the amount of errors located
                for(int i = 1; i < schedule.length; i++){
                    // the string will be declared as HTML, so <br> can be interpreted as carriage return in the browser window
                    outputString += schedule[i] + "<br>";
                }

                // force an last carriage return to leave some space to the bottom of the page
                outputString+="&nbsp;";

                // attach the built schedule to the span to present it to the user
                spanOutput.getElement().setProperty("innerHTML",outputString);
            }

        } catch (Exception e) {
            Notification.show("Service crashed. Please try again.");
        }
    }

    private String buildMessage(int errors){

        String notificationMessage = " Scheduling process completed with " + errors;

        // end the message dependent on the amount of errors
        if (errors == 0) {
            notificationMessage += " errors.";
        } else if (errors == 1) {
            notificationMessage += " error. Please check the format of your proposals and try again to capture ignored proposals.";
        } else {
            notificationMessage += " errors. Please check the format of your proposals and try again to capture ignored proposals.";
        }

        return notificationMessage;
    }
}
