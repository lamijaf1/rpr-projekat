package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SubjectViewController {

    public TableView<Material> tableOfLectures, tableOfLabs, tableOfGroups;
    public TableColumn<Material,String> columnName, columnNameLab, columnNameGroup;
    public  Database database;
    public Material currentMaterial;
    public Label statusMsg;
    private String subjectName;
    @FXML
    public void initialize() {
        database=database.getInstance();
        subjectName = CourseListController.getSubjectTitle();
        String notificationString="";
        if (database.getNotification(subjectName)!=null){
            ObservableList<Notification> notifications = database.getNotification(subjectName);
            for (Notification notification:notifications) {
                notificationString = notificationString + "\n";
            }
            statusMsg.setText(notificationString);
        } else {
            statusMsg.setText("Welcome to "+subjectName);
        }

        fillLectureTable();
        fillLabTable();
        fillGroupTable();


    }

    private void fillGroupTable() {

        tableOfGroups.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial=newSelection;        }
        });
        currentMaterial=tableOfGroups.getSelectionModel().getSelectedItem();
        ObservableList<Material> groups=database.getGroups(subjectName);


        columnNameGroup.setCellValueFactory(
                new PropertyValueFactory<Material,String>("nameMaterial")
        );
        tableOfGroups.setItems(groups);

    }

    private void fillLabTable() {
        tableOfLabs.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial=newSelection;        }
        });
        currentMaterial=tableOfLabs.getSelectionModel().getSelectedItem();
        ObservableList<Material> labs=database.getLabs(subjectName);

        columnNameLab.setCellValueFactory(new PropertyValueFactory<Material,String>("nameMaterial"));

        tableOfLabs.setItems(labs);
    }

    private void fillLectureTable() {
        tableOfLectures.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial=newSelection;        }
        });
        currentMaterial=tableOfLectures.getSelectionModel().getSelectedItem();
        ObservableList<Material> lectures=database.getLectures(subjectName);

        columnName.setCellValueFactory(
                new PropertyValueFactory<Material,String>("nameMaterial")
        );
        tableOfLectures.setItems(lectures);
    }

}
