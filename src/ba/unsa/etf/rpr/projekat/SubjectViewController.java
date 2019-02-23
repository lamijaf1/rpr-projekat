package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.SQLException;
import javafx.scene.control.CheckBox;

public class SubjectViewController {

    public TableView<Material> tableOfLectures, tableOfLabs, tableOfGroups;
    public TableColumn<Material, String> columnName, columnNameLab, columnNameGroup;
    public static Database database;
    public Material currentMaterial;
    public Label statusMsg;
    private static String subjectName;
    private static String subjectType;

    @FXML
    public void initialize() {
        database = database.getInstance();
        subjectName = CourseListController.getSubjectTitle();
        String notificationString = "";
        if (database.getNotification(subjectName) != null) {
            ObservableList<Notification> notifications = database.getNotification(subjectName);
            for (Notification notification : notifications) {
                notificationString += (notification.getDate() + "-> " + notification.getText() + "\n");
            }
            statusMsg.setText(notificationString);
        } else {
            statusMsg.setText("Welcome to " + subjectName);
        }

        refillTables();


    }

    private void fillGroupTable() {

        tableOfGroups.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial = newSelection;

            }
        });
        currentMaterial = tableOfGroups.getSelectionModel().getSelectedItem();
        ObservableList<Material> groups = database.getGroups(subjectName);


        columnNameGroup.setCellValueFactory(
                new PropertyValueFactory<Material, String>("nameMaterial")
        );
        tableOfGroups.setItems(groups);




    }

    private void fillLabTable() {
        tableOfLabs.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial = newSelection;
            }
        });
        currentMaterial = tableOfLabs.getSelectionModel().getSelectedItem();
        ObservableList<Material> labs = database.getLabs(subjectName);

        columnNameLab.setCellValueFactory(new PropertyValueFactory<Material, String>("nameMaterial"));

        tableOfLabs.setItems(labs);
    }

    private void fillLectureTable() {
        tableOfLectures.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial = newSelection;
            }
        });
        currentMaterial = tableOfLectures.getSelectionModel().getSelectedItem();
        ObservableList<Material> lectures = database.getLectures(subjectName);

        columnName.setCellValueFactory(
                new PropertyValueFactory<Material, String>("nameMaterial")
        );
        tableOfLectures.setItems(lectures);
    }

    public void clearNotification(ActionEvent actionEvent) throws SQLException {
        statusMsg.setText("Welcome to " + subjectName);
        database.deleteNotification(subjectName);
    }

    public void back(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/courseList.fxml"));
        CourseListController controller = new CourseListController();
        loader.setController(controller);
        Parent root = loader.load();
        Stage primaryStage = (Stage) statusMsg.getScene().getWindow();
        primaryStage.setTitle("Course List");
        primaryStage.setScene(new Scene(root, 600, 400));
    }

    public void signOut(ActionEvent actionEvent) throws Exception {
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginForm.fxml"));
        Main main = new Main();

        Stage primaryStage = (Stage) statusMsg.getScene().getWindow();
        main.start(primaryStage);

    }

    public void openMaterial(ActionEvent actionEvent) throws IOException {
            File file = new File("./resources/pdfs/" + currentMaterial.getNameMaterial() + ".pdf");
            Desktop.getDesktop().open(file);

    }

    public void hideMaterial(ActionEvent actionEvent) throws IOException, SQLException {
        if(currentMaterial!=null) currentMaterial.setVisible(0);
        database.changeMaterial(currentMaterial);
        refillTables();
        if(currentMaterial!=null) System.out.println(currentMaterial.isVisible());
    }



    public void saveMaterials(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/typeOfMaterial.fxml"));
        TypeOfMaterialController controller = new TypeOfMaterialController();
        loader.setController(controller);
        Parent root = loader.load();
        Stage primaryStage = (Stage) statusMsg.getScene().getWindow();
        primaryStage.setTitle("Type of material");
        primaryStage.setScene(new Scene(root, 400, 140));
        primaryStage.setResizable(false);

    }

    public static void setSubjectType(String subject) {
          subjectType = subject;
    }

    public static void openChooser() throws SQLException, IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileFilter(new FileNameExtensionFilter("", "pdf"));
        if (chooser.showSaveDialog(chooser) == JFileChooser.APPROVE_OPTION) {
            String filename = chooser.getSelectedFile().getName();
            String currFIle=filename;
            if (!filename.endsWith(".pdf"))
                filename += ".pdf";

            Path from = Paths.get(chooser.getSelectedFile().toURI());
            Path to = Paths.get("./resources/pdfs/"+filename);
            CopyOption[] options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(from, to, options);
            int id=database.getMaxIdOfMaterials();
            Material material=new Material(id, currFIle,subjectName,subjectType, 1);
            database.addNewMaterial(material);

        }
    }

    public void refillTables(){
        ObservableList<Material> groups = FXCollections.observableArrayList();
        ObservableList<Material> labs = FXCollections.observableArrayList();
        ObservableList<Material> lectures = FXCollections.observableArrayList();
        tableOfGroups.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial = newSelection;
            }
        });
        currentMaterial = tableOfGroups.getSelectionModel().getSelectedItem();

        for(Material m:database.getGroups(subjectName)){
            if (m.isVisible()==1){
                groups.add(m);
            }
        }


        columnNameGroup.setCellValueFactory(
                new PropertyValueFactory<Material, String>("nameMaterial")
        );
        tableOfGroups.setItems(groups);

        tableOfLabs.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial = newSelection;
            }
        });
        currentMaterial = tableOfLabs.getSelectionModel().getSelectedItem();
        for(Material m:database.getLabs(subjectName)){
            if (m.isVisible()==1){
                labs.add(m);
            }
        }

        columnNameLab.setCellValueFactory(new PropertyValueFactory<Material, String>("nameMaterial"));

        tableOfLabs.setItems(labs);

        tableOfLectures.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentMaterial = newSelection;
            }
        });
        currentMaterial = tableOfLectures.getSelectionModel().getSelectedItem();

        for(Material m:database.getLectures(subjectName)){
            if (m.isVisible()==1){
                lectures.add(m);
            }
        }
        columnName.setCellValueFactory(
                new PropertyValueFactory<Material, String>("nameMaterial")
        );
        tableOfLectures.setItems(lectures);

    }


}
