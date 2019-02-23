package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

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
                notificationString += (notification.getDate()+"-> "+ notification.getText() + "\n");
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

    public void clearNotification(ActionEvent actionEvent) throws SQLException {
        statusMsg.setText("Welcome to "+subjectName);
        database.deleteNotification(subjectName);
    }
    public void back(ActionEvent actionEvent) throws Exception  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/courseList.fxml"));
        CourseListController controller = new CourseListController();
        loader.setController(controller);
        Parent root = loader.load();
        Stage primaryStage = (Stage) statusMsg.getScene().getWindow();
        primaryStage.setTitle("Course List");
        primaryStage.setScene(new Scene(root, 600, 400));
    }
    public void signOut(ActionEvent actionEvent)  throws Exception{
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginForm.fxml"));
        Main main=new Main();

        Stage primaryStage = (Stage) statusMsg.getScene().getWindow();
        main.start(primaryStage);

    }


}
