package com.ja_cabili.passifi;

public class Event {
    public int id;
    public String title;
    public int avatar;
    public String description;
    public String date;
    public String time;
    public String location;
    public String organizer;
    public String organizer_email;
    public String organizer_approval;
    public String user_id;
    public String invite_code;
    public int total_attendees;
    public int current_attendees;

    public Event(String title, int avatar, String description, String date, String time, String location, String organizer, String organizer_email, String organizer_approval, String user_id, String invite_code, int total_attendees, int current_attendees, int id) {
        this.title = title;
        this.description = description;
        this.avatar = avatar;
        this.date = date;
        this.time = time;
        this.location = location;
        this.organizer = organizer;
        this.organizer_email = organizer_email;
        this.organizer_approval = organizer_approval;
        this.user_id = user_id;
        this.invite_code = invite_code;
        this.total_attendees = total_attendees;
        this.current_attendees = current_attendees;
        this.id = id;
    }
}