package com.rplsd.clasche.parser;

import com.rplsd.clasche.scheduler.Classroom;
import com.rplsd.clasche.scheduler.Course;
import com.rplsd.clasche.scheduler.Lecturer;
import javafx.util.Pair;

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
    private ArrayList<Pair<String, String>> classes =new ArrayList<>();
    private String currentRuleType;
    private String class1;
    private String class2;
    private ArrayList<String> classFilter = new ArrayList<>();

    @Override
    public void exitDefineClassroom(ClascheParser.DefineClassroomContext ctx) {
        System.out.println(String.format("Define Classroom: %s %s %s", roomId, capacity, facilities));
        Classroom classroom = new Classroom(
                roomId, capacity, facilities
        );

        facilities = new TreeSet<>();
        ClascheContext.getInstance().getScheduler().addClassroom(classroom);
    }

    @Override
    public void exitDefineLecturer(ClascheParser.DefineLecturerContext ctx) {
        System.out.println(String.format("Define Lecturer: %s %s", lecturerName, teachingHours));
        Lecturer lecturer = new Lecturer(
                lecturerName, ClascheContext.availabilityParser(teachingHours)
        );
        ClascheContext.getInstance().getScheduler().addLecture(lecturer);
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
        facilities = new TreeSet<>();
        lecturers = new TreeSet<>();
        ClascheContext.getInstance().getScheduler().addClassRequirement(course);
    }

    @Override
    public void enterStartSchedule(ClascheParser.StartScheduleContext ctx) {
        if (ClascheContext.getInstance().getScheduler().schedule()) {
            System.out.println("Schedule Created");
        } else {
            System.out.println("No schedule can satisfy all constraint");
        }
        ClascheContext.getInstance().getScheduler().printSchedule();

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

    @Override
    public void enterFixed_schedule(ClascheParser.Fixed_scheduleContext ctx) {
        currentRuleType = ClascheContext.FIXED_SCHEDULE;
    }

    @Override
    public void enterNon_conflict(ClascheParser.Non_conflictContext ctx) {
        currentRuleType = ClascheContext.NON_CONFLICT;
        classes = new ArrayList<>();
    }
    @Override public void enterTeaching_duration_limit(ClascheParser.Teaching_duration_limitContext ctx) {
        currentRuleType = ClascheContext.MAX_LECTURER_HOUR;
    }
    @Override
    public void exitClass_1(ClascheParser.Class_1Context ctx) {
        class1 = className;
    }

    @Override
    public void exitClass_2(ClascheParser.Class_2Context ctx) {
        class2 = className;
    }

    @Override
    public void exitPair_of_class_name(ClascheParser.Pair_of_class_nameContext ctx) {
        classes.add(new Pair(class1,class2));
    }
    @Override
    public void enterUnavailable(ClascheParser.UnavailableContext ctx) {
        currentRuleType = ClascheContext.RESTRICTED_TIME;
        teachingHours = new ArrayList<>();

    }

    @Override
    public void exitDefineConstraint(ClascheParser.DefineConstraintContext ctx) {
        switch (currentRuleType) {
            case ClascheContext.FIXED_SCHEDULE:
                System.out.println(String.format("Define Constraint Fixed Schedule for Class %s : %s", className, teachingHours));
                ClascheContext.getInstance().getScheduler().getConstraintScheduleRule().addFixedClassSchedule(
                        className, ClascheContext.timeParser(teachingHours)
                );
                teachingHours = new ArrayList<>();
                break;
            case ClascheContext.NON_CONFLICT:
                System.out.println("Define Constraint Non-Conflicting classes "+ classes);
                for(Pair<String,String> i : classes) {
                    ClascheContext.getInstance().getScheduler().getConstraintScheduleRule().addNonConflictingClassRule(
                            i.getKey(), i.getValue()
                    );
                }
                classes = new ArrayList<>();
                break;
            case ClascheContext.MAX_LECTURER_HOUR:
                System.out.println("Define Constraint Teaching duration limit per day "+ duration+ "hour");
                ClascheContext.getInstance().getScheduler().getConstraintScheduleRule().setMaxLecturerHourInADay(
                        duration
                );
                break;
            case ClascheContext.RESTRICTED_TIME:
                System.out.println("Define Constraint Unavailable at "+ teachingHours);
                for(String i : teachingHours) {
                    Pair<Integer,Integer> pairTimeDay = ClascheContext.timeConverter(i);
                    ClascheContext.getInstance().getScheduler().getConstraintScheduleRule().addRestrictedTime(
                            pairTimeDay.getKey(), pairTimeDay.getValue()
                    );
                }
                teachingHours = new ArrayList<>();
                break;
        }
    }

    public void exitDefinePreference(ClascheParser.DefinePreferenceContext ctx) {
        switch (currentRuleType) {
            case ClascheContext.FIXED_SCHEDULE:
                System.out.println(String.format("Define Preference Fixed Schedule for Class %s : %s", className, teachingHours));
                ClascheContext.getInstance().getScheduler().getPreferredScheduleRule().addFixedClassSchedule(
                        className, ClascheContext.timeParser(teachingHours)
                );
                teachingHours = new ArrayList<>();
                break;
            case ClascheContext.NON_CONFLICT:
                System.out.println("Define Preference Non-Conflicting classes "+ classes);
                for(Pair<String,String> i : classes) {
                    ClascheContext.getInstance().getScheduler().getPreferredScheduleRule().addNonConflictingClassRule(
                            i.getKey(), i.getValue()
                    );
                }
                break;
            case ClascheContext.MAX_LECTURER_HOUR:
                System.out.println("Define Preference Teaching duration limit per day "+ duration+ "hour");
                ClascheContext.getInstance().getScheduler().getPreferredScheduleRule().setMaxLecturerHourInADay(
                        duration
                );
                break;
            case ClascheContext.RESTRICTED_TIME:
                System.out.println("Define Preference Unavailable at "+ teachingHours);
                for(String i : teachingHours) {
                    Pair<Integer,Integer> pairTimeDay = ClascheContext.timeConverter(i);
                    ClascheContext.getInstance().getScheduler().getPreferredScheduleRule().addRestrictedTime(
                            pairTimeDay.getKey(), pairTimeDay.getValue()
                    );
                }
                teachingHours = new ArrayList<>();
                break;
        }
    }
    
}
