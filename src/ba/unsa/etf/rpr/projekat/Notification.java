package ba.unsa.etf.rpr.projekat;

import java.io.Serializable;

public class Notification implements Serializable {
    private int id;
    private Subject subject;
    private String text;
    private String date;

    public Notification() {
    }

    public Notification(int id, Subject subject, String text, String date) {
        this.id = id;
        this.subject = subject;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
