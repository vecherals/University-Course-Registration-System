package com.university.service;
import com.university.model.Section;
import com.university.model.TimeSlot;

public class ScheduleConflictChecker {

    public boolean hasConflict(Section section1, Section section2) {
        for (TimeSlot slot1 : section1.getMeetingTimes()) { //loop through all time slots of the first section
            for (TimeSlot slot2 : section2.getMeetingTimes()) { //second section

                if (slot1.getDay().equals(slot2.getDay())) {
                    if (timesOverlap(slot1, slot2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean timesOverlap(TimeSlot t1, TimeSlot t2) {
        boolean start1BeforeEnd2 = t1.getStartTime().compareTo(t2.getEndTime()) < 0;
        boolean end1AfterStart2  = t1.getEndTime().compareTo(t2.getStartTime()) > 0;

        return start1BeforeEnd2 && end1AfterStart2;
    }
}