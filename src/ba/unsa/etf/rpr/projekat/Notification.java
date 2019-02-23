package ba.unsa.etf.rpr.projekat;

public class Notification {
    private int id;
    private Subject subject;
    private String text;

    public Notification(int id, Subject subject, String text) {
        this.id = id;
        this.subject = subject;
        this.text = text;
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
}
