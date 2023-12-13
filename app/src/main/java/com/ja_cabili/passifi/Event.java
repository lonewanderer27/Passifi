package com.ja_cabili.passifi;

public class Event {
    private int photo;
    private String name;
    private int currentAttendees;
    private int totalAttendees;
    private String formattedDate;
    private String location;
    private String organizer;

    // Constructor
    public Event(int photo, String name, int currentAttendees, int totalAttendees, String formattedDate, String location, String organizer) {
        this.photo = photo;
        this.name = name;
        this.currentAttendees = currentAttendees;
        this.totalAttendees = totalAttendees;
        this.formattedDate = formattedDate;
        this.location = location;
        this.organizer = organizer;
    }

    // Getters
    public int getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public int getCurrentAttendees() {
        return currentAttendees;
    }

    public int getTotalAttendees() {
        return totalAttendees;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public String getLocation() {
        return location;
    }

    public String getOrganizer() {
        return organizer;
    }
}