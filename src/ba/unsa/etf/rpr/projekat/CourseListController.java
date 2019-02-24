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
import javafx.stage.Stage;
import javafx.util.Duration;

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
    int idOfSubject;

    public void initialize() {
        database = database.getInstance();
        sortSubjects();
        if(LoginFormController.isProfessor()) textWelcome.setText("Welcome, " + LoginFormController.getCurrentUser().getFullName());
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
                int currentId = LoginFormController.getCurrentUser().getId();
                Subject subject = database.getSubjectByName(value);
                if (subject != null) idOfSubject = subject.getId();
                if (currentId == idOfSubject) {
                    System.out.println("OK, predajes na ovom predmetu");
                    editOnSelectSubject = true;
                } else {
                    editOnSelectSubject = false;
                      /*  Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning Dialog");
                        alert.setHeaderText("Look, a Warning Dialog");
                        alert.setContentText("ops, you are not a professor on this predmet!");
                        alert.showAndWait();*/
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
}


