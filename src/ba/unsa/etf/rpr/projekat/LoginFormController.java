package ba.unsa.etf.rpr.projekat;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginBtn;
    public Button guestBtn;
    private Database database;
    public TextField statusMsg;
    private static Person currentUser;


    @FXML
    public void initialize() {
        usernameField.setEditable(true);
        usernameField.selectHome();
       //usernameField.setFocusTraversable(true);
        database = database.getInstance();
        statusMsg.setVisible(false);
    }

    public void Login(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        ObservableList<Person> persons = database.getPersons();
        for (Person p : persons) {
            if (p.getFullName().equals(username)) {
                if (p.getPassword().equals(password)) {
                    currentUser = p;
                    courseList();
                    statusMsg.setVisible(true);
                    statusMsg.setText("Please wait...");
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
        primaryStage.setScene(new Scene(root, 600, 400));
    }

    public void LoginGuest(ActionEvent actionEvent) {
    }

    public static Person getCurrentUser() {
        return currentUser;
    }
}
