package com.rplsd.clasche.parser;

import com.rplsd.clasche.scheduler.Classroom;
import com.rplsd.clasche.scheduler.Course;
import com.rplsd.clasche.scheduler.Lecturer;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class ClascheWalker extends ClascheBaseListener {
    private String roomId;
    private String className;
    private int capacity;
    private String lecturerName;
    private int attendeesCount;
    private int duration;
    private int maxCapacity;
    private Set<String> lecturers = new TreeSet<>();
    private Set<String> facilities = new TreeSet<>();
    private ArrayList<String> teachingHours = new ArrayList<>();

    @Override
    public void exitDefineClassroom(ClascheParser.DefineClassroomContext ctx) {
        System.out.println(String.format("Define Classroom: %s %s %s", roomId, capacity, facilities));
        Classroom classroom = new Classroom(
                roomId, capacity, facilities
        );

        classroom.toString();
    }

    @Override
    public void exitDefineLecturer(ClascheParser.DefineLecturerContext ctx) {
        System.out.println(String.format("Define Lecturer: %s %s", lecturerName, teachingHours));
        Lecturer lecturer = new Lecturer(
                lecturerName, availabilityParser(teachingHours)
        );
        lecturer.toString();
    }

    @Override
    public void enterDefineClass(ClascheParser.DefineClassContext ctx) {
        maxCapacity = 1000;
    }

    @Override
    public void exitDefineClass(ClascheParser.DefineClassContext ctx) {
        System.out.println(String.format(
                "Define Class: %s %s %s %s %s %s",
                className, attendeesCount, maxCapacity, facilities, duration, lecturers
        ));
        Course course = new Course(
                className, attendeesCount, maxCapacity, facilities, duration, lecturers
        );
        course.toString();
    }

    @Override
    public void exitRoom_id(ClascheParser.Room_idContext ctx) {
        roomId = ctx.getText();
    }

    @Override
    public void exitCapacity(ClascheParser.CapacityContext ctx) {
        capacity = Integer.parseInt(ctx.getText());
    }

    @Override
    public void enterArray_of_facilities(ClascheParser.Array_of_facilitiesContext ctx) {
        facilities = new TreeSet<>();
    }

    @Override
    public void exitFacility(ClascheParser.FacilityContext ctx) {
        facilities.add(ctx.getText());
    }

    @Override
    public void enterArray_of_teaching_hours(ClascheParser.Array_of_teaching_hoursContext ctx) {
        teachingHours = new ArrayList<>();
    }

    @Override
    public void enterTeaching_hour(ClascheParser.Teaching_hourContext ctx) {
        teachingHours.add(ctx.getText());
    }

    @Override
    public void enterLecturer_name(ClascheParser.Lecturer_nameContext ctx) {
        lecturerName = ctx.getText();
        lecturers.add(lecturerName);
    }

    @Override
    public void enterClass_name(ClascheParser.Class_nameContext ctx) {
        className = ctx.getText();
    }

    @Override
    public void enterAttendees_count(ClascheParser.Attendees_countContext ctx) {
        attendeesCount = Integer.parseInt(ctx.getText());
    }

    @Override
    public void enterMax_capacity(ClascheParser.Max_capacityContext ctx) {
        maxCapacity = Integer.parseInt(ctx.getText());
    }

    @Override
    public void enterDuration(ClascheParser.DurationContext ctx) {
        duration = Integer.parseInt(ctx.getText());
    }

    @Override
    public void enterArray_of_lecturers(ClascheParser.Array_of_lecturersContext ctx) {
        lecturers = new TreeSet<>();
    }

    public static ArrayList< ArrayList<Boolean> > availabilityParser(ArrayList<String> teachingHours) {
        ArrayList<ArrayList<Boolean>> res = new ArrayList<>();
        for(int i = 0; i<5; i++) {
            ArrayList<Boolean> buff = new ArrayList<Boolean>(11);
            for(int j = 0; j<11; j++) {
                buff.add(false);
            }
            res.add(buff);
        }
        for(String i : teachingHours) {
            int day = Integer.parseInt(i.substring(0,1))-1;
            int hour = Integer.parseInt(i.substring(1))-1;
            res.get(day).set(hour, true);
        }
        return res;
    }
    
}
