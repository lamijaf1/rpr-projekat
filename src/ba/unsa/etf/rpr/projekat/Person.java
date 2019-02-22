package ba.unsa.etf.rpr.projekat;

import java.util.Objects;

public class Person {
    private  int id;
    private String username;
    private String password;
    private String fullName;
    private boolean isProfessor;

    public Person(int id,String username, String password, String fullName, boolean isProfessor) {
        this.id=id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                isProfessor == person.isProfessor &&
                Objects.equals(username, person.username) &&
                Objects.equals(password, person.password) &&
                Objects.equals(fullName, person.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, fullName, isProfessor);
    }
}
