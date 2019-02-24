package ba.unsa.etf.rpr.projekat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Date;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginForm.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Classroom materials");
        primaryStage.setScene(new Scene(root, 650, 450));
        primaryStage.setResizable(false);
        primaryStage.show();


    }


    public static void main(String[] args) throws IOException {
        launch(args);
        try (var listener = new ServerSocket(59090)) {
            System.out.println("The date server is running...");
            while (true) {
                try (var socket = listener.accept()) {
                    var out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(new Date().toString());
                }
            }
        }
    }
}

