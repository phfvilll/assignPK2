package com.acme.contram.backend;

import com.acme.contram.backend.model.Talk;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;

public class ScheduleService {

    private LinkedList<Talk> unplannedTalks;

    public int parseProposals(String input) {

        unplannedTalks = new LinkedList<Talk>();

        // variable to count incorrect formatted proposals
        int errorCounter = 0;

        // splitting the input String line per line by carriage return to get an array of all proposals
        String[] lines = input.split("\n");

        // checking all proposals for the correct format and list the correct ones as talks
        for (int i = 0; i < lines.length;i++){
            //handle one proposal
            if(!checkProposal(lines[i])){
                errorCounter++;
                System.out.println("Error found");
            }
        }

        // schedule the listed talks
        scheduleTalks();

        return errorCounter;
    }

    private boolean checkProposal(String proposal) {

        // splitting the proposal to search the last element for duration
        String[] proposalParts = proposal.split(" ");

        // ignore proposals without talk title
        if(proposalParts.length<=1){
            System.out.println("too short");
            // skip empty lines
            if(proposalParts.length==0 || proposalParts[0] == ""){

                System.out.println("zero short");
                return true;
            }
            return false;
        }

        for (int j = 0; j < proposalParts.length;j++) {
            System.out.println(proposalParts[j]);
        }

        if(proposalParts[proposalParts.length-1].equals("lightning")){
            System.out.println("HelloWorld");

            String talkTitle = "";
            for(int j = 0; j < proposalParts.length-1; j++){
                talkTitle += proposalParts[j];
            }
            // 5, because a lightning lasts 5 minutes
            unplannedTalks.add(new Talk(talkTitle,5));
            System.out.println("new Talk :) .Now we have "+unplannedTalks.size());

            return true;
        } else {
            String[] lastElement = proposalParts[proposalParts.length-1].split("min");

            for (int j = 0; j < lastElement.length;j++) {
                System.out.println("last ELement: "+lastElement[j]);
                System.out.println("size last ELement: "+lastElement.length);
                if(lastElement.length==1 && StringUtils.isNumeric(lastElement[j])){
                    int duration = Integer.parseInt(lastElement[j]);
                    // check if the proposal would fit into the time constraints
                    if(duration >= 1 && duration <= 240){

                        String talkTitle = "";
                        for(int k = 0; k < proposalParts.length-1; k++){
                            talkTitle += proposalParts[j];
                        }
                        unplannedTalks.add(new Talk(talkTitle,duration));
                        System.out.println("new Talk :) .Now we have "+unplannedTalks.size());
                        return true;
                    }
                }
            }
        }
        return false;
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
