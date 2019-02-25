package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class TypeOfMaterialController {
    public MenuButton menuButton;
    public MenuItem lecturesMenu;
    public MenuItem labsMenu;
    public MenuItem groupsMenu;
    public String currentType;

    @FXML
    public void initialize() {
        currentType = "lecture";
        lecturesMenu.setOnAction(event -> {
            currentType = "lecture";
            menuButton.setText(currentType);
        });
        labsMenu.setOnAction(event -> {
            currentType = "lab";
            menuButton.setText(currentType);
        });
        groupsMenu.setOnAction(event -> {
            currentType = "group";
            menuButton.setText(currentType);
        });


    }

    public void save(ActionEvent actionEvent) throws IOException, SQLException {
        SubjectViewController.setSubjectType(currentType);
        Stage stage = (Stage) menuButton.getScene().getWindow();
        //stage.close();
        SubjectViewController.openChooser();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/subjectView.fxml"));
            SubjectViewController controller = new SubjectViewController();
            loader.setController(controller);
            Parent root = loader.load();
            Stage primaryStage = (Stage) menuButton.getScene().getWindow();
            primaryStage.setTitle("");
            primaryStage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
