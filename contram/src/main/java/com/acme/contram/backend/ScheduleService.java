package com.acme.contram.backend;

import com.acme.contram.backend.model.Talk;
import java.util.LinkedList;

public class ScheduleService {

    private LinkedList<Talk> unplannedTalks;

    public String parseProposals(String input) {
        int errorTracker = 23;

        // splitting the input String line per line by carriage return to get an array of all proposals
        String[] lines = input.split("\n");

        for (int i = 0; i < lines.length;i++){
            System.out.println(lines[i]);
            //handle one proposal
            String[] proposalParts = lines[i].split(" ");
            for (int j = 0; j < proposalParts.length;j++) {
                System.out.println(proposalParts[j]);
            }
            if(proposalParts[proposalParts.length-1].equals("lightning")){
                errorTracker = 0;
                System.out.println("HelloWorld");

                String talkTitle = "";
                for(int j = 0; j < proposalParts.length-1; j++){
                    talkTitle += proposalParts[j];
                }
                // 5, because a lightning lasts 5 minutes
                unplannedTalks.add(new Talk(talkTitle,5));
            } else {

            }

        }

        scheduleTalks();

        String errorMessage = "Please check the format of your proposals and try again.";
        if (errorTracker == 0) {
            return "0 errors.";
        } else if (errorTracker == 1) {
            return "1 error." + errorMessage;
        } else {
            return errorTracker+" errors. " + errorMessage;
        }
    }

    private void scheduleTalks(){
    }

    public LinkedList<String> scheduleToStringArray(){
        LinkedList<String> outputSchedule = new LinkedList<>();
        outputSchedule.add("Track 1:");
        outputSchedule.add("09:00AM Writing Fast Tests Against Enterprise Rails 60min");
        outputSchedule.add("10:00AM Overdoing it in Python 45min");
        outputSchedule.add("10:45AM Lua for the Masses 30min");
        outputSchedule.add("11:15AM Ruby Errors from Mismatched Gem Versions 45min");
        outputSchedule.add("12:00PM Lunch");
        outputSchedule.add("01:00PM Ruby on Rails: Why We Should Move On 60min");
        outputSchedule.add("02:00PM Common Ruby Errors 45min");
        outputSchedule.add("02:45PM Pair Programming vs Noise 45min");
        outputSchedule.add("03:30PM Programming in the Boondocks of Seattle 30min");
        outputSchedule.add("04:00PM Ruby vs. Clojure for Back-End Development 30min");
        outputSchedule.add("04:30PM User Interface CSS in Rails Apps 30min");
        outputSchedule.add("05:00PM Networking Event");
        outputSchedule.add(" ");
        outputSchedule.add("Track 2:");
        outputSchedule.add("09:00AM Communicating Over Distance 60min");
        outputSchedule.add("10:00AM Rails Magic 60min");
        outputSchedule.add("11:00AM Woah 30min");
        outputSchedule.add("11:30AM Sit Down and Write 30min");
        outputSchedule.add("12:00PM Lunch");
        outputSchedule.add("01:00PM Accounting-Driven Development 45min");
        outputSchedule.add("01:45PM Clojure Ate Scala (on my project) 45min");
        outputSchedule.add("02:30PM A World Without HackerNews 30min");
        outputSchedule.add("03:00PM Ruby on Rails Legacy App Maintenance 60min");
        outputSchedule.add("04:00PM Rails for Python Developers lightning");
        outputSchedule.add("05:00PM Networking Event");
        outputSchedule.add(" ");
        return outputSchedule;
    }
}
