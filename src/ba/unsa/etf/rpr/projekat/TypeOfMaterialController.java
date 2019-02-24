package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
        currentType = "lectures";
        lecturesMenu.setOnAction(event -> {
            currentType = "lectures";
            menuButton.setText(currentType);
        });
        labsMenu.setOnAction(event -> {
            currentType = "labs";
            menuButton.setText(currentType);
        });
        groupsMenu.setOnAction(event -> {
            currentType = "groups";
            menuButton.setText(currentType);
        });


    }

    public void save(ActionEvent actionEvent) throws IOException, SQLException {
        SubjectViewController.setSubjectType(currentType);
        Stage stage = (Stage) menuButton.getScene().getWindow();
        stage.close();
        SubjectViewController.openChooser();

    }
}
