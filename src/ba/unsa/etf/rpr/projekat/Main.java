package ba.unsa.etf.rpr.projekat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginForm.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Classroom materials");
        primaryStage.setScene(new Scene(root, 650, 450));
        primaryStage.setResizable(false);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}

