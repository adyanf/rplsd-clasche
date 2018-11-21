package scheduler;

import java.util.List;

public class ScheduleItem {
    private String courseName;
    private String classRoomId;
    private List<String> lecturerNames;

    public ScheduleItem(String courseName, String classRoomId, List<String> lecturerNames) {
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

    public List<String> getLecturerNames() {
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

