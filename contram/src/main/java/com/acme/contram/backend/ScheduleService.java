package com.acme.contram.backend;

import com.acme.contram.backend.model.Session;
import com.acme.contram.backend.model.Talk;
import com.acme.contram.backend.model.Track;
import com.philippk.cotrama.model.TalkRecord;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class ScheduleService {

    private LinkedList<Talk> unplannedTalks;
    private LinkedList<Track> trackList;
    private LinkedList<String> outputList;

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
            // skipping empty lines
            if(proposalParts.length==0 || proposalParts[0] == ""){
                return true;
            }
            return false;
        }

        // put talk title together
        String talkTitle = "";
        for(int k = 0; k < proposalParts.length-1; k++){
            talkTitle += proposalParts[k];
        }

        // ignore talk titles with numbers in it
        if(talkTitle.matches(".*\\d.*")){
            return false;
        }

        // catch lightning talks by checking the last word of the proposal
        if(proposalParts[proposalParts.length-1].equals("lightning")){

            // a new talk is found (duration = 5, because a lightning lasts 5 minutes)
            unplannedTalks.add(new Talk(talkTitle,5));
            return true;

        } else {
            // catch other talks by checking the end of the proposal for duration
            String[] lastElement = proposalParts[proposalParts.length-1].split("min");

            // check if the end of the proposal isn't followed by Characters and check if the duration is given as an Integer
            if(lastElement.length==1 && StringUtils.isNumeric(lastElement[0])){

                int duration = Integer.parseInt(lastElement[0]);

                // check if the proposal would fit into the time constraints
                if(duration >= 1 && duration <= 240){

                    // a new talk is found
                    unplannedTalks.add(new Talk(talkTitle,duration));
                    return true;
                    }
                }
        }
        return false;
    }

    private void scheduleTalks(){

        trackList = new LinkedList<Track>();

        //while(unplannedTalks.size()>=0){
            //Track currentTrack = new Track(trackList.size()+1,new Session();
        //}
        //LocalTime time1 = LocalTime.of(20,43);

    }

    public LinkedList<String> getOutputList(){

        outputList = new LinkedList<>();

        // complete outputList track per track
        for(int i = 0; i <= trackList.size(); i++){

            Track currentTrack = trackList.get(i);

            outputList.add("Track "+currentTrack.getTrackNumber()+":");

            // add all talks from morning session to the outputList
            addTalkRecords(currentTrack.getMorningSession());

            // add all talks from afternoon session to the outputList
            addTalkRecords(currentTrack.getAfternoonSession());

            outputList.add(" ");
        }
        return outputList;
    }

    public void addTalkRecords(Session session){

        // set time format for the output text
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");

        // add every talk from the session to the outputList
        for (int j = 0; j < session.getRecords().size();j++){

            TalkRecord currentRecord = session.getRecords().get(j);

            // catch Lightning Event
            if(currentRecord.getTalk().getDuration()==5){
                // put String together for lightning output-line
                this.outputList.add(currentRecord.getTimeOfDate().format(formatter)+
                        " "+
                        currentRecord.getTalk() +
                        " lightning");

            } else {
                // put String together for regular output-line
                this.outputList.add(currentRecord.getTimeOfDate().format(formatter)+
                        " "+
                        currentRecord.getTalk() +
                        " "+
                        currentRecord.getTalk().getDuration()+
                        "min");
            }
        }
    }
}
