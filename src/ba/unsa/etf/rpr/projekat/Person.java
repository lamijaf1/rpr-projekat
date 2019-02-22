package ba.unsa.etf.rpr.projekat;

public class Person {
    private String username;
    private String password;
    private String fullName;
    private boolean isProfessor;

    public Person(String username, String password, String fullName, boolean isProfessor) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.isProfessor = isProfessor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isProfessor() {
        return isProfessor;
    }

    public void setProfessor(boolean professor) {
        isProfessor = professor;
    }
}
