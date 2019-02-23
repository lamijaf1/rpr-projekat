package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Scanner;

public class Database {
    private Connection conn;
    private PreparedStatement getAllPersons, getAllMaterials, getAllSubjects;
    private PreparedStatement getProfessorById;

    private void regenerateDatabase() {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("projectDatabase.db.sql"));
            String sqlQuery = "";
            while (input.hasNext()) {
                sqlQuery += input.nextLine();
                if (sqlQuery.charAt(sqlQuery.length() - 1) == ';') {
                    try {
                        Statement stmnt = conn.createStatement();
                        stmnt.execute(sqlQuery);
                        sqlQuery = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        input.close();
    }

    public Database() {

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:projectDatabase.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            getAllPersons = conn.prepareStatement("SELECT * FROM person ORDER BY id");
        } catch (Exception e) {
            regenerateDatabase();
            try {
                getAllPersons = conn.prepareStatement("SELECT * FROM person ORDER BY id");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            getAllMaterials = conn.prepareStatement("SELECT  * FROM person ORDER BY id");
            getAllSubjects = conn.prepareStatement("SELECT  * FROM subjects ORDER BY id");
            getProfessorById=conn.prepareStatement("SELECT * FROM person WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<Person> getPersons() {
        ObservableList<Person> persons = FXCollections.observableArrayList();
        try {
            ResultSet rs = getAllPersons.executeQuery();

            while (rs.next()) {
                Person person = getPersonFromResultSet(rs);
                persons.add(person);
            }
            //conn.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return persons;
    }

    private Person getPersonFromResultSet(ResultSet rs) throws SQLException {
        return new Person(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5));

    }

    public ObservableList<Subject> getSubjects() {
        ObservableList<Subject> subjects = FXCollections.observableArrayList();
        try {
            ResultSet rs = getAllSubjects.executeQuery();

            while (rs.next()) {
                Subject subject = getSubjectFromResultSet(rs);
                subjects.add(subject);
            }
            //conn.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return subjects;
    }

    private Subject getSubjectFromResultSet(ResultSet rs) throws SQLException {
        Subject subject = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), null);
        subject.setProfessor(getProfessor(rs.getInt(4)));

        return subject;

    }

    private Person getProfessor(int id) {
        try {
            getProfessorById.setInt(1,id);
            ResultSet rs = getProfessorById.executeQuery();
            if (!rs.next()) return null;
            return getPersonFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
