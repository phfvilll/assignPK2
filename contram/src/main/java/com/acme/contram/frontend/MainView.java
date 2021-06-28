package com.acme.contram.frontend;

import com.acme.contram.backend.ScheduleService;
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

import java.util.LinkedList;

@Route
@StyleSheet("/styles.css")
public class MainView extends VerticalLayout {

    private final String inputPlaceholder = "Please enter your proposals here (one per line)." +
            "\n You can choose between the following 2 formats:" +
            "\n [talk title without numbers] [number between 1 and 240]min" +
            "\n [talk title without numbers] lightning" +
            "\n e.g.:" +
            "\n Innovative Technology Solutions 42min" +
            "\n or:" +
            "\n History of Cloud Engineering lightning" +
            "\n (Note that incorrectly formatted proposals will be ignored during the scheduling process.)";

    public MainView() {

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        //addClassName("main-view");

        H1 header = new H1("ConTraM");
        header.getElement().getThemeList().add("dark");
        //Object[] test = header.getElement().getThemeList().toArray();
        //System.out.println(test[0].toString());
        header.getElement().getStyle().set("width","100%");
        header.getElement().getStyle().set("margin","0");
        header.getElement().getStyle().set("padding","16px");
        add(header);

        H2 header2 = new H2("Conference Track Management");
        header2.getElement().getStyle().set("width","100%");
        header2.getElement().getStyle().set("margin","0");
        header2.getElement().getStyle().set("padding-top","12px");
        header2.getElement().getStyle().set("text-align","center");
        add(header2);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        //addClassName("centered-content");
        // This TextArea provides text input of proposals
        TextArea inputArea = new TextArea();
        inputArea.getElement().getStyle().set("width","100%");
        inputArea.getElement().getStyle().set("height","40%");
        inputArea.setPlaceholder(inputPlaceholder);
        add(inputArea);

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        // button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button button = new Button("schedule proposals");
        button.getElement().getStyle().set("width","100%");
        button.getElement().getThemeList().add("primary");
        add(button);

        Label label = new Label(" ");
        label.getElement().getStyle().set("width","100%");
        label.getElement().getStyle().set("font-size","large");
        label.getElement().getStyle().set("font-weight","bold");
        add(label);

        // Span to show the schedule
        Span outputSpan = new Span();
        outputSpan.getElement().getStyle().set("width","100%");
        outputSpan.getElement().getStyle().set("height","30%");
        outputSpan.getElement().setProperty("innerHTML","");
        outputSpan.getElement().getStyle().set("padding-left","16px");
        add(outputSpan);

        // Button click listener to trigger the parsing and scheduling process
        ScheduleService scheduleService = new ScheduleService();
        button.addClickListener(e -> {


            // set up a notification message to inform the user about incorrect formatted proposals
            int countedErrors = scheduleService.parseProposals(inputArea.getValue());
            String notificationMessage = " Reading proposals completed with " + countedErrors;

            if (countedErrors == 0) {
                notificationMessage += " errors.";
            } else if (countedErrors == 1) {
                notificationMessage += " error. Please check the format of your proposals and try again.";
            } else {
                notificationMessage += " errors. Please check the format of your proposals and try again.";
            }

            Notification.show(notificationMessage);

            // ask for the created conference schedule as a List
            LinkedList<String> schedule = scheduleService.getOutputList();

            if(schedule.size()==0) {
                label.setText("No talks to schedule.");
            } else {
                label.setText("Conference Schedule:");
            }

            String outputString = "";
            for(int i = 0; i < schedule.size(); i++){
                outputString += schedule.get(i) + "<br>";
            }
            outputString+="&nbsp;";

            outputSpan.getElement().setProperty("innerHTML",outputString);
        });
    }
}
