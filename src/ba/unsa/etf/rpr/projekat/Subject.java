package ba.unsa.etf.rpr.projekat;

public class Subject {
    private int id;
    private String subjectName;
    private String program;
    private Person professor;

    public Subject(int id, String subjectName, String program, Person professor) {
        this.id = id;
        this.subjectName = subjectName;
        this.program = program;
        this.professor=professor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public Person getProfessor() {
        return professor;
    }

    public void setProfessor(Person professor) {
        this.professor = professor;
    }
}
