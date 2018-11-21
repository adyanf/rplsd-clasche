package scheduler;

import java.util.Set;

public class Classroom {
    private String id;
    private Integer capacity;
    private Set<String> facilities;

    public Classroom(String id, Integer capacity, Set<String> facilities) {
        this.id = id;
        this.capacity = capacity;
        this.facilities = facilities;
    }

    public String getId() {
        return id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Set<String> getFacilities() {
        return facilities;
    }

    public void removeFacilities(String facility) {
        facilities.remove(facility);
    }

    public void addFacilities(String facility) {
        facilities.add(facility);
    }

}
