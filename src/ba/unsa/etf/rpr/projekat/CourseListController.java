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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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

    public void initialize() {
        database = new Database();
        sortSubjects();
        textWelcome.setText("Welcome, " + LoginFormController.getCurrentUser().getFullName());
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

        undergraduateTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                // NEW ACTION with selectedItem.getValue()
                System.out.println(selectedItem.getChildren().get(0).getValue());
                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/subjectView.fxml"));
                        SubjectViewController controller = new SubjectViewController();
                        loader.setController(controller);
                        Parent root = loader.load();
                        Stage primaryStage = (Stage) textDate.getScene().getWindow();
                       // primaryStage.setTitle(.getValue());
                        primaryStage.setScene(new Scene(root, 600, 400));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });


        masterTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                // NEW ACTION with selectedItem.getValue()

            }
        });

        phdTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                // NEW ACTION with selectedItem.getValue()
            }
        });

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
}


