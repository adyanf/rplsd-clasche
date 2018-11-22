package com.rplsd.clasche.scheduler;

import java.util.Set;

public class Course {
    private String id;
    private String courseName;
    private Integer minimumCapacity;
    private Integer maximumCapacity;
    private Set<String> facilities;
    private Integer durations;
    private Set<String> lectures;

    public Course(String id, String courseName, Integer minimumCapacity, Integer maximumCapacity,
                  Set<String> facilities, Integer durations, Set<String> lectures) {
        this.id = id;
        this.courseName = courseName;
        this.minimumCapacity = minimumCapacity;
        this.maximumCapacity = maximumCapacity;
        this.facilities = facilities;
        this.durations = durations;
        this.lectures = lectures;
    }

    public String getId() {
        return id;
    }

    public Set<String> getFacilities() {
        return facilities;
    }

    public Integer getDurations() {
        return durations;
    }

    public Integer getMaximumCapacity() {
        return maximumCapacity;
    }

    public Integer getMinimumCapacity() {
        return minimumCapacity;
    }

    public Set<String> getLectures() {
        return lectures;
    }

    public String getCourseName() {
        return courseName;
    }

    public void addLecture(String lecture) {
        lectures.add(lecture);
    }

    public void removeLecture(String lecture) {
        lectures.remove(lecture);
    }

    public void setMinimumCapacity(Integer minimumCapacity) {
        this.minimumCapacity = minimumCapacity;
    }

    public void setMaximumCapacity(Integer maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public void setDurations(Integer durations) {
        this.durations = durations;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String toString() {
        return String.format("Course %s has %d <= capacity %d." +
                "Has durations %d and need facilities %s. " +
                "The lecture is %s", courseName, minimumCapacity, maximumCapacity, durations, facilities.toString(),
                lectures);
    }
}
