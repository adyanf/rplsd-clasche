package com.rplsd.clasche.scheduler;

import javafx.util.Pair;

import java.util.*;

public class ScheduleRule {
    private Map<String, Set<String>> nonConflictingClasses;
    private Map<String, Set<Pair<Integer, Integer>>> fixedClassSchedules;
    private Set<Pair<Integer, Integer>> restrictedTime;
    private int maxLecturerHourInADay;

    public ScheduleRule(Map<String, Set<String>> nonConflictingClasses,
                        Map<String, Set<Pair<Integer, Integer>>> fixedClassSchedules,
                        Set<Pair<Integer, Integer>> restrictedTime,
                        int maxLecturerHourInADay
    ) {
        this.nonConflictingClasses = nonConflictingClasses;
        this.fixedClassSchedules = fixedClassSchedules;
        this.restrictedTime = restrictedTime;
        this.maxLecturerHourInADay = maxLecturerHourInADay;
    }

    public Map<String, Set<String>> getNonConflictingClasses() {
        return nonConflictingClasses;
    }

    public void setNonConflictingClasses(Map<String, Set<String>> nonConflictingClasses) {
        this.nonConflictingClasses = nonConflictingClasses;
    }

    public Map<String, Set<Pair<Integer, Integer>>> getFixedClassSchedules() {
        return fixedClassSchedules;
    }

    public void setFixedClassSchedules(Map<String, Set<Pair<Integer, Integer>>> fixedClassSchedules) {
        this.fixedClassSchedules = fixedClassSchedules;
    }

    public Set<Pair<Integer, Integer>> getRestrictedTime() {
        return restrictedTime;
    }

    public void setRestrictedTime(Set<Pair<Integer, Integer>> restrictedTime) {
        this.restrictedTime = restrictedTime;
    }

    public int getMaxLecturerHourInADay() {
        return maxLecturerHourInADay;
    }

    public void setMaxLecturerHourInADay(int maxLecturerHourInADay) {
        this.maxLecturerHourInADay = maxLecturerHourInADay;
    }

    public void addNonConflictingClassRule(String className, String otherClassName) {
        try {
            nonConflictingClasses.get(className).add(otherClassName);
        } catch (NullPointerException e) {
            nonConflictingClasses.put(className, new HashSet<String>(){{
                add(otherClassName);
            }});
        }
        try {
            nonConflictingClasses.get(otherClassName).add(className);
        } catch (NullPointerException e) {
            nonConflictingClasses.put(otherClassName, new HashSet<String>(){{
                add(className);
            }});
        }
    }

    public void addFixedClassSchedule(String className, Set<Pair<Integer, Integer>> time) {
        if (fixedClassSchedules.containsKey(className)) {
            fixedClassSchedules.replace(className, time);
        } else {
            fixedClassSchedules.put(className, time);
        }
    }

    public void addRestrictedTime(int day, int time) {
        restrictedTime.add(new Pair<>(day, time));
    }

    public ScheduleRule add(ScheduleRule scheduleRule) {
        Map<String, Set<String>> nonConflictingClasses = new HashMap<>();
        Map<String, Set<Pair<Integer, Integer>>> fixedClassSchedules = new HashMap<>();
        Set<Pair<Integer, Integer>> restrictedTime = new TreeSet<>(
                new Comparator<Pair<Integer, Integer>>(){
                    @Override
                    public int compare(Pair<Integer, Integer> curr, Pair<Integer, Integer> other) {
                        if (curr.getValue() < other.getValue()) return 1;
                        if (curr.getValue().equals(other.getValue())) {
                            return curr.getKey().compareTo(other.getKey());
                        }
                        return -1;
                    }
                }
        );
        for (Map.Entry<String, Set<String>> entry: scheduleRule.getNonConflictingClasses().entrySet()) {
            nonConflictingClasses.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Set<String>> entry: this.getNonConflictingClasses().entrySet()) {
            nonConflictingClasses.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Set<Pair<Integer, Integer>>> entry: scheduleRule.getFixedClassSchedules().entrySet()) {
            fixedClassSchedules.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Set<Pair<Integer, Integer>>> entry: this.getFixedClassSchedules().entrySet()) {
            fixedClassSchedules.put(entry.getKey(), entry.getValue());
        }
        restrictedTime.addAll(scheduleRule.getRestrictedTime());
        restrictedTime.addAll(this.getRestrictedTime());
        int maxLecturerHourInADay = Integer.min(this.maxLecturerHourInADay, scheduleRule.maxLecturerHourInADay);
        return new ScheduleRule(nonConflictingClasses, fixedClassSchedules, restrictedTime, maxLecturerHourInADay);
    }
}
