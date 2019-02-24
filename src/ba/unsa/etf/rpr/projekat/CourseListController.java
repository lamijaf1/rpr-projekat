package ba.unsa.etf.rpr.projekat;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class CourseListController {
    @FXML
    private TreeView<String> undergraduateTree;
    @FXML
    private TreeView<String> masterTree;
    @FXML
    private TreeView<String> phdTree;
    @FXML
    private Label textDate, textWelcome;
    private Database database;
    private String[] undergraduateSubjects = new String[100];
    private String[] masterSubjects = new String[100];
    private String[] phdSubjects = new String[100];
    private static String value;
    public static boolean editOnSelectSubject;
    public  boolean professor;
    int idOfSubject;

    public void initialize() {
        database = database.getInstance();
        sortSubjects();
        if(!LoginFormController.getCurrentUser().isProfessor())editOnSelectSubject=false; //if isProfessor is false immediately he cant edit on subjects
        if(!LoginFormController.isGuest()) textWelcome.setText("Welcome, " + LoginFormController.getCurrentUser().getFullName());
       //else if(!database.getProfessor1(LoginFormController.getCurrentUser()).isProfessor())
        else textWelcome.setText("Welcome on coursware,\n you are signed as guest. ");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Calendar time = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                                textDate.setText(simpleDateFormat.format(time.getTime()));
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(60))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        onItemClickListener(undergraduateTree);
        onItemClickListener(masterTree);
        onItemClickListener(phdTree);

        loadUndergraduateItems(undergraduateSubjects);
        loadMasterItems(masterSubjects);
        loadPhdItems(phdSubjects);

    }

    private void sortSubjects() {
        ObservableList<Subject> subjects = database.getSubjects();
        int x = 0, y = 0, z = 0;
        for (Subject subject : subjects) {
            if (subject.getProgram().toLowerCase().equals("undergraduate")) {
                undergraduateSubjects[x++] = subject.getSubjectName();
            } else if (subject.getProgram().toLowerCase().equals("master")) {
                masterSubjects[y++] = subject.getSubjectName();
            } else if (subject.getProgram().toLowerCase().equals("phd")) {
                phdSubjects[z++] = subject.getSubjectName();
            }
        }
    }

    public void loadUndergraduateItems(String... rootItems) {
        TreeItem<String> root = new TreeItem<String>("Undergraduate");
        for (String itemString : rootItems) {
            TreeItem<String> item = new TreeItem<String>(itemString);
            root.getChildren().add(item);

        }

        undergraduateTree.setRoot(root);
    }

    public void loadMasterItems(String... rootItems) {
        TreeItem<String> root = new TreeItem<String>("Master");
        for (String itemString : rootItems) {
            root.getChildren().add(new TreeItem<String>(itemString));
        }

        masterTree.setRoot(root);
    }

    public void loadPhdItems(String... rootItems) {
        TreeItem<String> root = new TreeItem<String>("Phd");
        for (String itemString : rootItems) {
            root.getChildren().add(new TreeItem<String>(itemString));
        }

        phdTree.setRoot(root);
    }

    public void onItemClickListener(TreeView<String> tree) {
        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {

                value = observable.getValue().toString();
                // System.out.println(value);
                value = value.substring(value.indexOf(":") + 1, value.indexOf("]")).trim();
                int currentId=-1;
                if (LoginFormController.getCurrentUser() != null) {
                     currentId = LoginFormController.getCurrentUser().getId();
                }

                Subject subject = database.getSubjectByName(value);
                if (subject != null) idOfSubject = subject.getId();
                if (currentId == idOfSubject) {
                    System.out.println("OK, predajes na ovom predmetu");
                    editOnSelectSubject = true;
                } else {
                    editOnSelectSubject = false;
                    if(!LoginFormController.getCurrentUser().isProfessor()) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning Dialog");
                            alert.setHeaderText("Disabled editing on a subject");
                            alert.setContentText("ops, you are not a professor on this predmet!");
                            alert.showAndWait();
                        });
                    }
                }
                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/subjectView.fxml"));
                        SubjectViewController controller = new SubjectViewController();
                        loader.setController(controller);
                        Parent root = loader.load();
                        Stage primaryStage = (Stage) textDate.getScene().getWindow();
                        primaryStage.setTitle(value);
                        primaryStage.setScene(new Scene(root, 600, 400));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }
        });
    }

    public void signOut(ActionEvent actionEvent) throws Exception {
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginForm.fxml"));
        Main main = new Main();

        Stage primaryStage = (Stage) textDate.getScene().getWindow();
        main.start(primaryStage);

    }


    public static String getSubjectTitle() {
        return value;
    }

    public static boolean isEditOnSelectSubject() {
        return editOnSelectSubject;
    }
    public void about(ActionEvent actionEvent) {
        try {
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
            Parent root = loader.load();
            newStage.setTitle("Something about aplication");
            newStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            newStage.setResizable(false);
            newStage.show();
        }catch (Exception e){
            e.getMessage();
        }
    }
    public void print (ActionEvent actionEvent) {
        Platform.runLater(() -> {
            try {
                new CourswareReport().showReport(Database.getInstance().getConn());
            } catch (JRException e1) {
                e1.printStackTrace();
            }
        });
    }
    public void saveAs(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML File (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter1);
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("DOCX (*.docx)", "*.docx");
        fileChooser.getExtensionFilters().add(extFilter2);
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            CourswareReport gradoviReport = new CourswareReport();
            try {
                String name = file.getName();
                int lastIndexOf = name.lastIndexOf(".");
                if (lastIndexOf == -1) {
                    name=""; // empty extension
                }
                gradoviReport.saveAs(database.getInstance().getConn(),name, file.getCanonicalPath());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}


