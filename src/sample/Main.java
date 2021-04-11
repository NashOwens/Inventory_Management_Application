package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static String[] userDetails;
    public static int requestID;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.show();
    }
    public static String[] getUserDetails() {
        return userDetails;
    }
    public static void setUserDetails(String[] newUserDetails) {
        userDetails = newUserDetails;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
