import ba.unsa.etf.rpr.projekat.*;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CoursewareDAOTest {

    @Test
    void regenerateFile() {
        CoursewareDAO.removeInstance();
        File dbfile = new File("projectDatabase.db");
        dbfile.delete();
        CoursewareDAO dao = CoursewareDAO.getInstance();
        ObservableList<Material> material = dao.getMaterials();
        //ISTO DODATI KASNIJE KADA BAZU KONACNO POPUNIM
        assertEquals("math", material.get(0).getSubject());
        assertEquals("group", material.get(1).getType());
    }

    @Test
    void getPersonsTest() {
        CoursewareDAO.removeInstance();
        File dbfile = new File("projectDatabase.db");
        dbfile.delete();
        CoursewareDAO dao = CoursewareDAO.getInstance();
        ObservableList<Person> persons = dao.getPersons();
        assertEquals("DODATIKASNIJE", persons.get(0).getFullName());
        assertEquals("DODATIKASNIJE", persons.get(1).getUsername());
    }

    @Test
    void deleteMaterial() throws SQLException {
        CoursewareDAO.removeInstance();
        File dbfile = new File("projectDatabase.db");
        dbfile.delete();
        CoursewareDAO dao = CoursewareDAO.getInstance();
        Material material = new Material(3, "group1", "math", "group", 1);
        dao.deleteMaterial(material);
        ObservableList<Material> materials = dao.getMaterials();
        //ISTO DODATI KASNIJE KADA BAZU KONACNO POPUNIM
        assertEquals("lecture1", materials.get(0).getNameMaterial());
        assertEquals("group", materials.get(1).getType());
    }

    @Test
    void deleteNotification() throws SQLException {
        CoursewareDAO.removeInstance();
        File dbfile = new File("projectDatabase.db");
        dbfile.delete();
        CoursewareDAO dao = CoursewareDAO.getInstance();
        Subject subject = new Subject(1, "math1", "undergraduate", null);
        Notification notification = new Notification(1, subject, "No class today", "21.12.2020");
        dao.deleteNotification(notification.getSubject().getSubjectName());
        ObservableList<Notification> notifications = dao.getNotification(notification.getSubject().getSubjectName());
        assertNotEquals(1, notifications.get(0).getId());
        // i ovo modifikovati kad ostalo zavrsiim

    }

    @Test
    void addNewMaterial() throws SQLException {
        CoursewareDAO.removeInstance();
        File dbfile = new File("projectDatabase.db");
        dbfile.delete();
        CoursewareDAO dao = CoursewareDAO.getInstance();
        Material material = new Material(10, "lecture 1a", "LAG", "lecture", 1);
        dao.addNewMaterial(material);
        ObservableList<Material> materials = dao.getMaterials();
        //ovo dopuniti kada fino popunim bazu
        //assertEquals();
    }

    @Test
    void changeMaterial() throws SQLException {
        CoursewareDAO.removeInstance();
        File dbfile = new File("projectDatabase.db");
        dbfile.delete();
        CoursewareDAO dao = CoursewareDAO.getInstance();
        Material material = new Material(10, "lecture 1a", "LAG", "lecture", 1);
        dao.changeMaterial(material);
        ObservableList<Material> materials = dao.getMaterials();
        //ovo dopuniti kada fino popunim bazu
        //assertEquals();
    }

    @Test
    void getSubjects() {
        CoursewareDAO.removeInstance();
        File dbfile = new File("projectDatabase.db");
        dbfile.delete();
        CoursewareDAO dao = CoursewareDAO.getInstance();
        ObservableList<Subject> subjects = dao.getSubjects();
        assertEquals("", subjects.get(0).getSubjectName());
        assertEquals("", subjects.get(1).getProgram());
    }


}
