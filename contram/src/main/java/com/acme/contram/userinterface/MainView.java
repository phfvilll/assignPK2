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

/**
 * MainView provides a Vaadin-based user interface.
 * It consists of six components: a main header for the application name, a second header to point out
 * the core service of the web app, a text area for user input, a button to call the service, a label
 * that serves as header for the answer and a span to print the created schedule.
 * The class references to a Cascading Style Sheet (.css) to customize the arrangement of the components.
 * MainView is the entry point for the running application when connecting to the server in the browser.
 * @author  Philipp Kraatz
 * @version 1.0
 */
@Route
@StyleSheet("/styles.css")
public class MainView extends VerticalLayout {

    /**
     * The INPUT_PLACEHOLDER constant serves as placeholder for the input-textArea to inform the user
     * about correct formatting of proposals.
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
    private String notification;

    /**
     * The constructor initializes all components of the user interface and implements the API of the
     * ScheduleService through a button click-listener.
     * The scheduleService is instantiated through dependency injection.
     */
    public MainView(@Autowired ScheduleService scheduleService) {

        this.scheduleService = scheduleService;

        // set all components per default on center alignment
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // set layout to the whole browser window
        setSizeFull();

        // add style for the header to the layout
        addClassName("main-view");

        // the main header shows the application name
        this.headerApp = new H1("ConTraM");
        this.headerApp.getElement().getThemeList().add("dark");
        add(headerApp);

        // the purpose header points out the core service of the application
        this.headerPurpose = new H2("Conference Track Management");
        add(headerPurpose);

        // the input text area provides the text input of proposals
        this.textAreaInput = new TextArea();
        this.textAreaInput.setPlaceholder(INPUT_PLACEHOLDER);
        add(textAreaInput);

        // the schedule button serves to call the scheduling service
        this.buttonSchedule = new Button("schedule proposals");
        this.buttonSchedule.getElement().getThemeList().add("primary");
        add(buttonSchedule);

        // the output label serves as header for the answer of the scheduling service
        this.labelOutput = new Label(" ");
        add(labelOutput);

        // the output span contains the schedule after calling the service
        this.spanOutput = new Span();
        this.spanOutput.getElement().setProperty("innerHTML","");
        add(spanOutput);

        // a button click listener to trigger the scheduling process
        buttonSchedule.addClickListener(click -> processData());
    }

    public void processData(){

        // splitting the input String line per line by carriage return to get an array of all proposals
        String[] proposals = textAreaInput.getValue().split("\n");

        // send the proposals to the scheduling service to receive a schedule including the amount of errors
        String[] schedule = scheduleService.createSchedule(proposals);

        // get the amount of errors that occurred by the sent proposals
        int errorCount = Integer.valueOf(schedule[0]);

        // set up a notification message to inform the user about incorrect formatted proposals
        buildMessage(errorCount);
        Notification.show(notification);

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
            spanOutput.setText(outputString);
            spanOutput.getElement().setProperty("innerHTML",outputString);
        }
    }

    private void buildMessage(int errors){

        notification = "Scheduling process completed with " + errors;

        // end the message depending on the amount of errors
        if (errors == 0) {
            notification += " errors.";
        } else if (errors == 1) {
            notification += " error. Please check the format of your proposals and try again to capture ignored proposals.";
        } else {
            notification += " errors. Please check the format of your proposals and try again to capture ignored proposals.";
        }
    }

    /**
     * {@return the text of the main header which contains the name of the application}
     */
    public String getHeaderAppText() {
        return headerApp.getText();
    }

    /**
     * {@return the text of the header that naming the purpose of the provided service}
     */
    public String getHeaderPurposeText() {
        return headerPurpose.getText();
    }

    /**
     * {@return the value of the text area where the user types in his proposals}
     */
    public String getTextAreaInputString() {
        return textAreaInput.getValue();
    }

    /**
     * set the text of the text area where the user types in his proposals
     * @param textAreaInputString
     */
    public void setTextAreaInput(String textAreaInputString) {
        this.textAreaInput.setValue(textAreaInputString);
    }

    /**
     * {@return the text of the output label that contains whether or not a schedule was created
     * after calling the ScheduleService}
     */
    public String getLabelOutputText() {
        return labelOutput.getText();
    }

    public void setLabelOutputText(String labelOutputText) {
        this.labelOutput.setText(labelOutputText);
    }
    /**
     * {@return the text of the output span that contains the received schedule in html-format after
     * calling the ScheduleService }
     */
    public String getSpanOutputText() {
        return spanOutput.getElement().getProperty("innerHTML");
    }

    /**
     * set the text of the output span that contains the received schedule in html-format after
     * calling the ScheduleService }
     * @param spanOutputText
     */
    public void setSpanOutputText(String spanOutputText) {
        this.spanOutput.getElement().setProperty("innerHTML",spanOutputText);
    }

    /**
     * {@return the main button whose Listener reads the input text area, calls the ScheduleService,
     * updates the output label and output span and triggers a notification when clicked}
     */
    public Button getButtonSchedule() {
        return buttonSchedule;
    }

    /**
     * {@return the current notification status message
     */
    public String getNotification() {
        return notification;
    }
}
