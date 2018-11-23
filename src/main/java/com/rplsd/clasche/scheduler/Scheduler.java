package com.rplsd.clasche.scheduler;

import com.google.gson.Gson;
import javafx.util.Pair;

import java.util.*;

import static com.rplsd.clasche.Constants.workDaysInAWeek;
import static com.rplsd.clasche.Constants.workHoursInADay;

public class Scheduler {
    private ArrayList<ArrayList<List<ScheduleItem>>> schedules;

    private ArrayList<Classroom> classrooms;
    private ArrayList<Course> courses;
    private ArrayList<Lecturer> lecturers;
    private ScheduleRule constraintScheduleRule;
    private ScheduleRule preferredScheduleRule;

    private Map<String, ArrayList<ArrayList<Boolean>>> classroomsAvailability;
    private Map<String, ArrayList<ArrayList<Boolean>>> lecturersAvailability;

    private Set<String> currentAddedCourses;

    public Scheduler(ArrayList<Classroom> classrooms,
                     ArrayList<Course> courses,
                     ArrayList<Lecturer> lecturers,
                     ScheduleRule constraintScheduleRule,
                     ScheduleRule preferredScheduleRule) {
        this.classrooms = classrooms;
        this.courses = courses;
        this.lecturers = lecturers;
        this.constraintScheduleRule = constraintScheduleRule;
        this.preferredScheduleRule = preferredScheduleRule;

        // Declare Table of Schedule with shape workDaysInAWeek x workHoursInADay
        schedules = new ArrayList<>();
        for (int day = 0; day < workDaysInAWeek; ++day) {
            schedules.add(new ArrayList<>(workDaysInAWeek));
            for (int hour = 0; hour < workHoursInADay; ++hour) {
                schedules.get(day).add(new ArrayList<>());
            }
        }

        // Declare Table of Classroom Availability
        classroomsAvailability = new HashMap<>();
        for (Classroom classroom: classrooms) {
            ArrayList<ArrayList<Boolean>> availability = new ArrayList<>();
            for (int day = 0; day < workDaysInAWeek; ++day) {
                availability.add(new ArrayList<>());
                for (int hour = 0; hour < workHoursInADay; ++hour) {
                    availability.get(day).add(true);
                }
            }
            classroomsAvailability.put(classroom.getId(), availability);
        }

        // Declare Table of Lecture Availability
        lecturersAvailability = new HashMap<>();
        for (Lecturer lecturer : lecturers) {
            lecturersAvailability.put(lecturer.getName(), lecturer.getAvailability());
        }

        currentAddedCourses = new TreeSet<>();
    }

    public ArrayList<ArrayList<List<ScheduleItem>>> getSchedules() {
        return schedules;
    }

    public ArrayList<Classroom> getClassrooms() {
        return classrooms;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }

    public ScheduleRule getConstraintScheduleRule() {
        return constraintScheduleRule;
    }

    public ScheduleRule getPreferredScheduleRule() {
        return preferredScheduleRule;
    }

    public Map<String, ArrayList<ArrayList<Boolean>>> getLecturersAvailability() {
        return lecturersAvailability;
    }

    public Map<String, ArrayList<ArrayList<Boolean>>> getClassroomsAvailability() {
        return classroomsAvailability;
    }

    public Set<String> getCurrentAddedCourses() {
        return currentAddedCourses;
    }

    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
        ArrayList<ArrayList<Boolean>> availability = new ArrayList<>();
        for (int day = 0; day < workDaysInAWeek; ++day) {
            availability.add(new ArrayList<>());
            for (int hour = 0; hour < workHoursInADay; ++hour) {
                availability.get(day).add(true);
            }
        }
        classroomsAvailability.put(classroom.getId(), availability);
    }

    public void addLecture(Lecturer lecturer) {
        lecturers.add(lecturer);
        ArrayList<ArrayList<Boolean>> availability = new ArrayList<>();
        for (int day = 0; day < workDaysInAWeek; ++day) {
            availability.add(new ArrayList<>());
            for (int hour = 0; hour < workHoursInADay; ++hour) {
                availability.get(day).add(lecturer.getAvailability().get(day).get(hour));
            }
        }
        lecturersAvailability.put(lecturer.getName(), availability);
    }

    public void addClassRequirement(Course course) {
        courses.add(course);
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void setConstraintScheduleRule(ScheduleRule constraintScheduleRule) {
        this.constraintScheduleRule = constraintScheduleRule;
    }

    public void setPreferredScheduleRule(ScheduleRule preferredScheduleRule) {
        this.preferredScheduleRule = preferredScheduleRule;
    }

    private Course findCourse(String courseName) {
        for (Course course : courses) {
            if (course.getCourseName().equals(courseName)) return course;
        }
        return null;
    }

    private List<Classroom> findValidConfigurationClassrooms(Course course) {
        List<Classroom> validClassrooms = new ArrayList<>();
        for (Classroom classroom : classrooms) {
            if (classroom.isSuitable(course)) {
                validClassrooms.add(classroom);
            }
        }
        return validClassrooms;
    }

    private Boolean isAllLecturesAvailable(Set<String> requiredLecturers, int day, int hour) {
        for (String lecturer : requiredLecturers) {
            if (!lecturersAvailability.get(lecturer).get(day).get(hour)) return false;
        }
        return true;
    }

    private Boolean isAllValidNonConflict(ScheduleRule scheduleRule, String courseName, int day, int hour) {
        for (ScheduleItem scheduleItem : schedules.get(day).get(hour)) {
            if (scheduleRule.getNonConflictingClasses().containsKey(courseName)) {
                if (scheduleRule.getNonConflictingClasses().get(courseName).contains(scheduleItem.getCourseName())) {
                    return false;
                }
            }
        }
        return true;
    }

    private Boolean isFixedSchedule(ScheduleRule scheduleRule, String courseName, int day, int hour) {
        if (scheduleRule.getFixedClassSchedules().containsKey(courseName)) {
            return scheduleRule.getFixedClassSchedules().get(courseName).contains(new Pair<>(day, hour));
        }
        return false;
    }

    private Boolean isValidLectureMaxHourInADay(ScheduleRule scheduleRule, Set<String> lecturers, int day) {
        int count = 0;
        for (List<ScheduleItem> scheduleItems : schedules.get(day)) {
            for (ScheduleItem scheduleItem : scheduleItems) {
                for (String lecturer : lecturers) {
                    if (scheduleItem.getLecturerNames().contains(lecturer)) {
                        ++count;
                        if (count >= scheduleRule.getMaxLecturerHourInADay()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isValidConstraints(ScheduleRule rule, Course course, Classroom classroom, int day, int time) {
        if (!(classroomsAvailability.get(classroom.getId()).get(day).get(time))) return false;
        if (!isAllLecturesAvailable(course.getLecturers(), day, time)) return false;
        if (!isAllValidNonConflict(rule, course.getCourseName(), day, time)) return false;
        if (rule.getRestrictedTime().contains(new Pair<>(day, time))) return false;
        if (isFixedSchedule(rule, course.getCourseName(), day, time)) return false;
        if (!isValidLectureMaxHourInADay(rule, course.getLecturers(), day)) return false;
        return true;
    }

    private boolean schedule(ScheduleRule rule, int currentClassRequirementIndex, int currentHour) {
        if (currentClassRequirementIndex >= courses.size()) return true;
        Course currentCourse = courses.get(currentClassRequirementIndex);

        List<Classroom> validClassrooms = findValidConfigurationClassrooms(currentCourse);
        for (Classroom satisfyingClassroom : validClassrooms) {
            for (int day = 0; day < workDaysInAWeek; day++) {
                for (int time = 0; time < workHoursInADay; time++) {
                    if (isValidConstraints(rule, currentCourse, satisfyingClassroom, day, time)) {
                        ScheduleItem scheduleItem = new ScheduleItem(currentCourse.getCourseName(), satisfyingClassroom.getId(), currentCourse.getLecturers());
                        System.out.println(scheduleItem.getCourseName() + " " + scheduleItem.getClassRoomId() + " " + scheduleItem.getLecturerNames() + " " + day + " " + time);
                        schedules.get(day).get(time).add(scheduleItem);
                        classroomsAvailability.get(satisfyingClassroom.getId()).get(day).set(time, false);
                        for (String lectureName : currentCourse.getLecturers()) {
                            lecturersAvailability.get(lectureName).get(day).set(time, false);
                        }
                        currentAddedCourses.add(currentCourse.getCourseName());
                        int nextHour = currentHour < currentCourse.getDurations() - 1 ? currentHour + 1 : 0;
                        int nextClassRequirementIndex = nextHour == 0 ? currentClassRequirementIndex + 1 : currentClassRequirementIndex;
                        if (schedule(rule, nextClassRequirementIndex, nextHour)) return true;
                        schedules.get(day).get(time).remove(scheduleItem);
                        classroomsAvailability.get(satisfyingClassroom.getId()).get(day).set(time, true);
                        for (String lectureName : currentCourse.getLecturers()) {
                            lecturersAvailability.get(lectureName).get(day).set(time, true);
                        }
                        currentAddedCourses.remove(currentCourse.getCourseName());
                    }
                }
            }
        }
        return false;
    }

    private void sortClassroomAscendingByCapacity() {
        class SortByCapacity implements Comparator<Classroom> {
            // Used for sorting in ascending order of capacity
            @Override
            public int compare(Classroom o1, Classroom o2) {
                return o1.getCapacity() - o2.getCapacity();
            }
        }
        classrooms.sort(new SortByCapacity());
    }

    public boolean schedule() {
        sortClassroomAscendingByCapacity(); //To prioritize class room with smaller capacity in room selection
        if (schedule(constraintScheduleRule.add(preferredScheduleRule), 0, 0)) return true;
        return schedule(constraintScheduleRule, 0, 0);
    }

    public void printSchedule() {
        for (int day = 0; day < workDaysInAWeek; day++) {
            for (int time = 0; time < workHoursInADay; time++) {
                System.out.println(String.format("Day %s - Time %s: [", day, time));
                for (ScheduleItem scheduleItem: schedules.get(day).get(time)) {
                    System.out.println(scheduleItem.toString());
                }
                System.out.println("]");
            }
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}