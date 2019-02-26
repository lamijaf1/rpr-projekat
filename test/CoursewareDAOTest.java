import ba.unsa.etf.rpr.projekat.*;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoursewareDAOTest {

    @Test
    void regenerateFile() {
        CoursewareDAO.removeInstance();
        File dbfile = new File("projectDatabase.db");
        dbfile.delete();
        CoursewareDAO dao = CoursewareDAO.getInstance();
        ObservableList<Material> material = dao.getMaterials();
        //ISTO DODATI KASNIJE KADA BAZU KONACNO POPUNIM
        assertEquals("RPR", material.get(0).getSubject());
        assertEquals("lecture", material.get(1).getType());
    }

    @Test
    void getPersonsTest() {
        CoursewareDAO.removeInstance();
        File dbfile = new File("projectDatabase.db");
        dbfile.delete();
        CoursewareDAO dao = CoursewareDAO.getInstance();
        ObservableList<Person> persons = dao.getPersons();
        assertEquals("student", persons.get(0).getFullName());
        assertEquals("lfazlija1", persons.get(1).getUsername());
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
        assertEquals("izvjestaj", materials.get(0).getNameMaterial());
        assertEquals("lecture", materials.get(1).getType());
    }

    @Test
    void getNotification() throws SQLException {
        CoursewareDAO.removeInstance();
        File dbfile = new File("projectDatabase.db");
        dbfile.delete();
        CoursewareDAO dao = CoursewareDAO.getInstance();
        ObservableList<Notification> notifications = dao.getNotification("RPR");
        assertEquals("No class today", notifications.get(0).getText());

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
        assertEquals("RPR", subjects.get(0).getSubjectName());
        assertEquals("master", subjects.get(1).getProgram());
    }


}
