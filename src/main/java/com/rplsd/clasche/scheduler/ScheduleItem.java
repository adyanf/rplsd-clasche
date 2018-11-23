package com.rplsd.clasche.scheduler;

import java.util.Set;

public class ScheduleItem {
    private String courseName;
    private String classRoomId;
    private Set<String> lecturerNames;

    public ScheduleItem(String courseName, String classRoomId, Set<String> lecturerNames) {
        this.courseName = courseName;
        this.classRoomId = classRoomId;
        this.lecturerNames = lecturerNames;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassRoomId() {
        return classRoomId;
    }

    public Set<String> getLecturerNames() {
        return lecturerNames;
    }

    public String toString() {
        String lecturersCombinedNames = "";
        boolean first = true;
        for (String lecturerName: lecturerNames) {
            if (first) {
                first = false;
            } else {
                lecturersCombinedNames = lecturersCombinedNames.concat("/");
            }
            lecturersCombinedNames = lecturersCombinedNames.concat(lecturerName);
        }
        return String.format("%s : %s - %s", courseName, classRoomId, lecturersCombinedNames);
    }
}
