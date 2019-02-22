package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class Database {
    private Connection conn;
    private PreparedStatement getAllPersons, getAllMaterials;

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
            System.out.println("CONNECTED");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("NOT CONNECTED");
        }

        try {
            getAllPersons = conn.prepareStatement("SELECT * FROM person ORDER BY id");
            System.out.println("GOT ALL PERSONS QUERY");
        } catch (Exception e) {
            regenerateDatabase();
            System.out.println("REGENERATED DB");
            try {
                getAllPersons = conn.prepareStatement("SELECT * FROM person ORDER BY id");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            getAllMaterials = conn.prepareStatement("SELECT  * FROM person ORDER BY id");
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

}
