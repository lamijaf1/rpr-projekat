package ba.unsa.etf.rpr.projekat;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class LoginFormController {
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginBtn;
    public Button guestBtn;
    private CoursewareDAO coursewareDAO;
    public TextField statusMsg;
    private static Person currentUser;
    private static boolean isGuest;


    @FXML
    public void initialize() {
        isGuest = false;
        usernameField.setEditable(true);
        coursewareDAO = coursewareDAO.getInstance();
        statusMsg.setVisible(false);
    }

    public void Login(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println(username);
        ObservableList<Person> persons = coursewareDAO.getPersons();
        for (Person p : persons) {
            if (p.getUsername().equals(username)) {
                if (p.getPassword().equals(password)) {
                    currentUser = p;
                    courseList();
                    statusMsg.setVisible(true);
                    statusMsg.setText("Please wait...");
                    isGuest = false;
                } else {
                    statusMsg.setVisible(true);
                    statusMsg.setText("Incorrect password. Try again.");
                }
            } else {
                if (username.equals("") || (username.equals(null))) {
                    statusMsg.setVisible(true);
                    statusMsg.setText("Please enter username");
                } else {
                    statusMsg.setVisible(true);
                    statusMsg.setText("Username does not exist");
                }
            }
        }


    }

    private void courseList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/courseList.fxml"));
        CourseListController controller = new CourseListController();
        loader.setController(controller);
        Parent root = loader.load();
        Stage primaryStage = (Stage) usernameField.getScene().getWindow();
        primaryStage.setTitle("Course List");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
    }

    public void LoginGuest(ActionEvent actionEvent) throws IOException {
        isGuest = true;
        courseList();
    }

    public static Person getCurrentUser() {
        return currentUser;
    }

    public static boolean isGuest() {
        return isGuest;
    }
}
