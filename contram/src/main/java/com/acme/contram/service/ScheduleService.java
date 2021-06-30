package com.acme.contram.service;

import com.acme.contram.service.model.Session;
import com.acme.contram.service.model.Talk;
import com.acme.contram.service.model.Track;
import com.acme.contram.service.model.TalkRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;

/**
 * ScheduleService provides the method createSchedule(String[] proposals) to create a Track-based schedule
 * of talks from a passed String-Array that contains the proposals for the talks.
 *
 * The method createSchedule(String[] proposals) consists of three parts.
 *
 * Through the first part the method recognizes talks in a passed String-Array of proposals.
 * It checks if a talk title exists, if a String is completly empty, if a talk title contains numbers,
 * if the duration of a talk would even fit in a session (for this it have to be between 1 and 240 minutes)
 * and if the proposal have a correct time indication at the end of its String.
 *
 * Accepted input formats for proposals are:
 * "[talk title without numbers] [number between 1 and 240]min"
 * and
 * "[talk title without numbers] lightning".
 *
 * Through the secand part, if the reading of the proposals is finished, the scheduling process begins
 * to assign the recognized talks to tracks through sessions as talk-records.
 *
 * Through the third part, as a result of the scheduling process the method converts the created schedule
 * to a printable list of Strings.
 *
 * The method returns a String-Array including the printable schedule which first field serves to hand over
 * the amount of errors that occur during the reading of the proposals.
 * @author  Philipp Kraatz
 * @version 1.0
 */
@Service
public class ScheduleService {

    private LinkedList<Talk> unplannedTalks;
    private LinkedList<Track> trackList;
    private LinkedList<String> outputList;
    private LocalTime endTimeOfTrack;

    /**
     * Accepted input formats for the proposals in the parameter-String[] are:
     * "[talk title without numbers] [number between 1 and 240]min"
     * and
     * "[talk title without numbers] lightning".
     *
     * Proposals in an incorrect format are counted as errors and will be ignored in the scheduling process.
     *
     * The method returns a String-Array including the printable schedule which first field serves to hand over
     * the amount of errors that occur during the reading of the proposals.
     *
     * @param proposals
     * @return a String[] that contains the amount of errors in the first field and the printable schedule in the
     * other fields.
     */
    public String[] createSchedule(String[] proposals) {

        unplannedTalks = new LinkedList<Talk>();

        // variable to count incorrect formatted proposals
        int errorCounter = 0;

        // checking all proposals for the correct format and list the correct ones as talks
        for (int i = 0; i < proposals.length;i++){
            //handle one proposal
            if(!checkProposal(proposals[i])){
                errorCounter++;
            }
        }

        // schedule the listed talks
        scheduleTalks();

        // convert the created track-list to a printable string-list
        LinkedList<String> outputList = getOutputList();

        // create return parameter
        String[] schedule = new String[outputList.size()+1];

        // attach the number of occured errors while parsing the incoming proposal-Array to the first field of the return parameter
        schedule[0] = String.valueOf(errorCounter);

        // reference the objects of the output-list to the schedule-Array
        for(int i = 0; i < outputList.size();i++){
            schedule[i+1] = outputList.get(i);
        }

        return schedule;
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

        // set the time for the Networking Event per default at 4PM and postpone it through the scheduling
        // process up to 5PM to update all afternoon sessions when all talks are scheduled
        endTimeOfTrack = LocalTime.of(16,00);

        // sort the list of unplanned talks from the longest to the shortest talk duration to be able to
        // schedule the longest talks first through a simple loop
        Collections.sort(unplannedTalks, Collections.reverseOrder());

        // create new tracks and fill them with talks as long as there are talks that aren't scheduled
        while(unplannedTalks.size()>0){

            Track newTrack = new Track();

            // fill up the morning session of the new track with talks
            assignTalksToSession(newTrack.getMorningSession());

            addLastEvent(newTrack.getMorningSession());

            // fill up the afternoon session of the new track with talks
            assignTalksToSession(newTrack.getAfternoonSession());

            // The closing event of the afternoon session is added at a later stage, when the earliest possible time of date is known

            trackList.add(newTrack);
        }

        // The earliest possible time of date for the last Event of the afternoon is now known and can be added to the afternoon sessions of every track
        for(int i = 0; i < trackList.size(); i++){
            trackList.get(i).getAfternoonSession().setLastEventTime(endTimeOfTrack);
            addLastEvent(trackList.get(i).getAfternoonSession());
        }
    }

    private void assignTalksToSession(Session session){

        // keep the point of time to assign the correct time of date to new talk records
        LocalTime currentTime = session.getStartTime();

        // try to fit talks into session from the longest to the shortest
        for (int i = 0; i < unplannedTalks.size(); i++){

            Talk currentTalk = unplannedTalks.get(i);

            // schedule a talk if enough time is available to fit it into the session
            if(currentTalk.getDuration()<=session.getTimeLeft()){

                // the talk fits in, so assign it to the session through a new talk record
                session.getRecords().add(new TalkRecord(currentTalk,currentTime));

                // reducing the time which left to the session by substractiong the duration of the new
                // scheduled talk
                session.setTimeLeft(session.getTimeLeft()-currentTalk.getDuration());

                // update the point of time by the duration of the new scheduled talk
                currentTime = currentTime.plusMinutes(currentTalk.getDuration());

                // update the earliest possible time of date for the last Event of the afternoon session
                if(currentTime.isAfter(endTimeOfTrack)){
                    endTimeOfTrack = currentTime;
                }

                // remove scheduled talk from the list for unplanned talks
                unplannedTalks.remove(i);

                // reset the run variable to start again from the beginning of the list to avoid to skip
                // list-elements as a result of the previous used remove()-method
                i = -1;
            }
        }
    }

    private void addLastEvent(Session session){
        // add the closing event of a session to the records with a duration of 0 to identify it later in the getOutputList()-method
        session.getRecords().add(new TalkRecord(new Talk(session.getLastEventTitle(),0),session.getLastEventTime()));
    }

    private LinkedList<String> getOutputList(){

        outputList = new LinkedList<String>();

        if(trackList.size()==0){
            return outputList;
        }

        // complete outputList track per track
        for(int i = 0; i < trackList.size(); i++){

            Track currentTrack = trackList.get(i);

            outputList.add("Track "+(i+1)+":");

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
