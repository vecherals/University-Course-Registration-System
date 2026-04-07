package com.university.model;

public class TimeSlot {
    private String day;       
    private String startTime; 
    private String endTime;   
    private String room;

    
    public TimeSlot(String day, String startTime, String endTime, String room) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    
    public String getDay() { 
    	return day; 
    }
    public String getStartTime() { 
    	return startTime; 
    }
    public String getEndTime() { 
    	return endTime; 
    }
    public String getRoom() { 
    	return room; 
    }
    
    @Override
    public String toString() {
        return day + " " + startTime + "-" + endTime + " (" + room + ")";
    }
}