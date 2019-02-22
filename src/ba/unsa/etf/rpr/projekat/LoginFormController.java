package ba.unsa.etf.rpr.projekat;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class LoginFormController {
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginBtn;
    public Button guestBtn;
    private Database database;
    public TextField statusMsg;


    @FXML
    public void initialize() {

        database = new Database();
        statusMsg.setVisible(false);
    }

    public void Login(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        ObservableList<Person> persons = database.getPersons();
        for (Person p : persons) {
            if (p.getFullName().equals(username)) {
                if (p.getPassword().equals(password)) {
                    //SUCCESS
                    statusMsg.setVisible(true);
                    statusMsg.setText("Please wait...");
                } else {
                    statusMsg.setVisible(true);
                    statusMsg.setText("Incorrect password. Try again.");
                }
            } else {
                if (username.equals("") || (username.equals(null))){
                    statusMsg.setVisible(true);
                    statusMsg.setText("Please enter username");
                } else {
                    statusMsg.setVisible(true);
                    statusMsg.setText("Username does not exist");
                }
            }
        }
    }

    public void LoginGuest(ActionEvent actionEvent) {
    }
}
