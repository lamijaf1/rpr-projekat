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
    private static Database instance;
    private PreparedStatement getAllPersons, getAllMaterials, getAllSubjects, getAllNotifications;
    private PreparedStatement getProfessorById,getSubjectById,deleteNotifications,findMaxIdOfMaterials,addNewMaterial;

    public static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

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

    private Database() {

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
            getAllMaterials = conn.prepareStatement("SELECT  * FROM materials ORDER BY id");
            getAllSubjects = conn.prepareStatement("SELECT  * FROM subjects ORDER BY id");
            getProfessorById=conn.prepareStatement("SELECT * FROM person WHERE id=?");
            getAllNotifications=conn.prepareStatement("SELECT * FROM notifications ORDER BY id");
            getSubjectById=conn.prepareStatement("SELECT * FROM subjects WHERE id=?");
            deleteNotifications=conn.prepareStatement("DELETE FROM notifications WHERE id=?");
            findMaxIdOfMaterials=conn.prepareStatement("SELECT MAX(id)+1 FROM materials");
            addNewMaterial=conn.prepareStatement( "INSERT INTO materials(id, name_material,subject,type) VALUES (?,?,?,?)");
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
    public ObservableList<Material> getMaterials() {
        ObservableList<Material> materials = FXCollections.observableArrayList();
        try {
            ResultSet rs = getAllMaterials.executeQuery();

            while (rs.next()) {
                Material material= getMaterialFromResultSet(rs);
                materials.add(material);
            }
            //conn.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return materials;
    }

    public ObservableList<Material> getMaterialsBySubject(String subjectName) {
        ObservableList<Material> materials = FXCollections.observableArrayList();
        try {
            ResultSet rs = getAllMaterials.executeQuery();

            while (rs.next()) {
                Material material= getMaterialFromResultSet(rs);
                if (material.getSubject().equals(subjectName)){
                    materials.add(material);
                }
            }
            //conn.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return materials;
    }

    private Material getMaterialFromResultSet(ResultSet rs) throws SQLException {
        return new Material(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4),rs.getInt(5));
    }

    public ObservableList<Material> getLectures(String subjectName){

        ObservableList<Material> materials = getMaterialsBySubject(subjectName);
        ObservableList<Material> lectures=FXCollections.observableArrayList();;

        for (Material material:materials) {
            if (material.getType().equals("lecture")){
                lectures.add(material);
            }
        }
        return lectures;
    }

    public ObservableList<Material> getLabs(String subjectName){

        ObservableList<Material> materials = getMaterialsBySubject(subjectName);
        ObservableList<Material> labs=FXCollections.observableArrayList();;

        for (Material material:materials) {
            if (material.getType().equals("lab")){
                labs.add(material);
            }
        }
        return labs;
    }

    public ObservableList<Material> getGroups(String subjectName){

        ObservableList<Material> materials = getMaterialsBySubject(subjectName);
        ObservableList<Material> groups=FXCollections.observableArrayList();;

        for (Material material:materials) {
            if (material.getType().equals("group")){
                groups.add(material);
            }
        }
        return groups;
    }

    public ObservableList<Notification> getNotification(String subjectName) {
        ObservableList<Notification> notifications = FXCollections.observableArrayList();
        try {
            ResultSet rs = getAllNotifications.executeQuery();

            while (rs.next()) {
                Notification notification = getNotificationFromResultSet(rs);
                if (notification.getSubject().getSubjectName().toLowerCase().equals(subjectName.toLowerCase())){
                    notifications.add(notification);
                }
            }
            //conn.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return notifications;
    }
    private Subject getSubject(int id) {
        try {
            getSubjectById.setInt(1,id);
            ResultSet rs = getSubjectById.executeQuery();
            if (!rs.next()) return null;
            return getSubjectFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Notification getNotificationFromResultSet(ResultSet rs) throws SQLException {
        Notification notification=  new Notification(rs.getInt(1), null, rs.getString(3),rs.getString(4));
        notification.setSubject(getSubject(rs.getInt(2)));
        return notification;
    }

    public void deleteNotification(String subjectName) throws SQLException {
        ObservableList<Notification> notifications = getNotification(subjectName);
        for(Notification notification:notifications){
            deleteNotifications.setInt(1, notification.getId());
            deleteNotifications.executeUpdate();
        }

    }
    public int getMaxIdOfMaterials() throws SQLException {
        int id = 1;
        ResultSet rs = findMaxIdOfMaterials.executeQuery();
        if (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }
    public void addNewMaterial(Material material) throws SQLException {
        addNewMaterial.setInt(1,material.getId());
        addNewMaterial.setString(2,material.getNameMaterial());
        addNewMaterial.setString(3,material.getSubject());
        addNewMaterial.setString(4,material.getType());
        addNewMaterial.executeUpdate();
    }


}
