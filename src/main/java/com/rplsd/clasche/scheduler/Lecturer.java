package com.rplsd.clasche.scheduler;

import java.util.ArrayList;

public class Lecturer {
    private String name;
    private ArrayList<ArrayList<Boolean>> availability;

    public Lecturer(String name, ArrayList<ArrayList<Boolean>> availability) {
        this.name = name;
        this.availability = availability;
    }

    public ArrayList<ArrayList<Boolean>> getAvailability() {
        return availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailability(Integer day, Integer hour, Boolean statusAvailability) {
        if (day < availability.size() && hour < availability.get(0).size()) {
            availability.get(day).set(hour, statusAvailability);
        }
    }

    public String toString() {
        return String.format("%s - %s", name, availability.toString());
    }
}
