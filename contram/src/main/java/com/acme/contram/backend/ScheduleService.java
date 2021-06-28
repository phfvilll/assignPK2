package com.acme.contram.backend;

import com.acme.contram.backend.model.Session;
import com.acme.contram.backend.model.Talk;
import com.acme.contram.backend.model.Track;
import com.acme.contram.backend.model.TalkRecord;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;

/**
 * ScheduleService manages to create a Track-based schedule for talks from a passed String that includes proposals.
 * ScheduleService also contains an output-list to hand over an arranged schedule.
 * The class stores various constants to specify the components of a morning session and an afternoon session.
 * @author  philippk
 * @version 1.0
 */
public class ScheduleService {

    // final values for the morning session
    /**
     *
     */
    public final static int AM_TIME_SPAN = 180;
    /**
     *
     */
    public final static String AM_END_EVENT_TITLE = "Lunch";
    /**
     *
     */
    public final static LocalTime AM_START_TIME = LocalTime.of(9,00);
    /**
     *
     */
    public final static LocalTime AM_END_EVENT_TIME = LocalTime.of(12,00);

    // final values for the afternoon session
    /**
     *
     */
    public final static int PM_TIME_SPAN = 240;
    /**
     *
     */
    public final static String PM_END_EVENT_TITLE = "Networking Event";
    /**
     *
     */
    public final static LocalTime PM_START_TIME = LocalTime.of(13,00);
    // final value is only known at the end of the scheduling process
    private LocalTime pmEndEventTime;

    private LinkedList<Talk> unplannedTalks;
    private LinkedList<Track> trackList;
    private LinkedList<String> outputList;

    /**
     *
     * recognize talks in a passed String of proposals which are seperated in the passed String by carriage return.
     * Accepted input formats are:
     * "[talk title without numbers] [number between 1 and 240]min"
     * and
     * "[talk title without numbers] lightning".
     * After reading the proposal-string and scheduling the talks, the arranged schedule is reachable by a getter-method.
     * @param input
     * @return
     */
    // if the reading loop is finished a method is triggered, that schedules the recognized talks to tracks.
    // The result of the scheduling process is reeachable through the getOutputList()-method.
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
            // add only free Space, if there is another word to add to the title
            if(!(k ==proposalParts.length-2)){
                talkTitle += " ";
            }
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

        pmEndEventTime = LocalTime.of(16,00);

        for(int i = 0; i < unplannedTalks.size(); i++){
            System.out.println(unplannedTalks.get(i).getTitle()+" "+
                    unplannedTalks.get(i).getDuration());
        }

        System.out.println(" ");

        Collections.sort(unplannedTalks, Collections.reverseOrder());


        for(int i = 0; i < unplannedTalks.size(); i++){
            System.out.println(unplannedTalks.get(i).getTitle()+" "+
                    unplannedTalks.get(i).getDuration());
        }

        // create new tracks and fill them with talks as long as there are talks that aren't scheduled
        while(unplannedTalks.size()>0){

            Track newTrack = new Track(
                    trackList.size()+1,
                    new Session(AM_TIME_SPAN, AM_END_EVENT_TITLE, AM_END_EVENT_TIME),
                    new Session(PM_TIME_SPAN, PM_END_EVENT_TITLE, pmEndEventTime));

            // fill up the morning session of the new track with talks
            assignTalksToSession(newTrack.getMorningSession(), AM_START_TIME);

            addLastEvent(newTrack.getMorningSession());

            // fill up the afternoon session of the new track with talks
            assignTalksToSession(newTrack.getAfternoonSession(), PM_START_TIME);

            // The closing event of the afternoon session is added at a later stage, when the earliest possible time of date is known

            trackList.add(newTrack);
        }

        // The earliest possible time of date for the last Event of the afternoon is now known and can be added to the afternoon sessions of every track
        for(int i = 0; i < trackList.size(); i++){
            trackList.get(i).getAfternoonSession().setLastEventTime(pmEndEventTime);
            addLastEvent(trackList.get(i).getAfternoonSession());
        }
    }

    private void addLastEvent(Session session){
        // add the closing event of a session to the records with a duration of 0 to identify it later in the getOutputList()-method
        session.getRecords().add(new TalkRecord(new Talk(session.getLastEventTitle(),0),session.getLastEventTime()));
    }

    private void assignTalksToSession(Session session, LocalTime startTime){

        LocalTime currentTime = startTime;
        System.out.println("StartTime: "+currentTime);
        for (int i = 0; i < unplannedTalks.size(); i++){

            Talk currentTalk = unplannedTalks.get(i);

            if(currentTalk.getDuration()<=session.getTimeLeft()){

                session.getRecords().add(new TalkRecord(currentTalk,currentTime));
                session.setTimeLeft(session.getTimeLeft()-currentTalk.getDuration());
                currentTime = currentTime.plusMinutes(currentTalk.getDuration());
                System.out.println(currentTime);

                // update the earliest possible time of date for the last Event of the afternoon
                if(currentTime.isAfter(pmEndEventTime)){
                    pmEndEventTime = currentTime;
                }

                unplannedTalks.remove(i);
                // reset the run variable to start again from the beginning of the list to avoid to skip list-elements as a result of the previous used remove()-method
                i = -1;
            }
        }
    }

    /**
     *
     * @return
     */
    public LinkedList<String> getOutputList(){

        outputList = new LinkedList<>();

        if(trackList.size()==0){
            return outputList;
        }

        // complete outputList track per track
        for(int i = 0; i < trackList.size(); i++){

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

    private void addTalkRecords(Session session){

        // set time format for the output text
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");

        // add every talk from the session to the outputList
        for (int j = 0; j < session.getRecords().size();j++){

            TalkRecord currentRecord = session.getRecords().get(j);

            // Catch final Event of a session
            if(currentRecord.getTalk().getDuration()==0){
                // put String together for final Event output-line
                this.outputList.add(currentRecord.getTimeOfDate().format(formatter)+
                        " "+
                        currentRecord.getTalk().getTitle());

            } else if(currentRecord.getTalk().getDuration()==5){
                // catch Lightning Event
                // put String together for lightning output-line
                this.outputList.add(currentRecord.getTimeOfDate().format(formatter)+
                        " "+
                        currentRecord.getTalk().getTitle() +
                        " lightning");

            } else {
                // put String together for regular output-line
                this.outputList.add(currentRecord.getTimeOfDate().format(formatter)+
                        " "+
                        currentRecord.getTalk().getTitle() +
                        " "+
                        currentRecord.getTalk().getDuration()+
                        "min");
            }
        }
    }
}
