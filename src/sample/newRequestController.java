package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static sample.Main.userDetails;

public class newRequestController {

    Stage dialogStage = new Stage();
    Scene scene;

    @FXML
    public Button home;
    @FXML
    public Button newRequest;
    @FXML
    public Button logout;
    @FXML
    public MenuItem hardware;
    @FXML
    public MenuItem software;
    @FXML
    public MenuItem comm;
    @FXML
    public MenuItem general;
    @FXML
    public MenuItem security;
    @FXML
    public MenuItem other;
    @FXML
    public TextArea details;
    @FXML
    public TextField lol;
    @FXML
    public Button newReq;

    public String type;

    public String hardwareOnAction(){
        type = "Hardware";
        return type;
    }

    public String softwareOnAction(){
        type = "Software";
        return type;
    }

    public String commOnAction(){
        type = "Communications";
        return type;
    }

    public String generalOnAction(){
        type = "General";
        return type;
    }

    public String secOnAction(){
        type = "Security";
        return type;
    }

    public String otherOnAction(){
        type = "Other";
        return type;
    }

    public void newProduct() {
        //sets up the database connection
        Connection conn = databaseConnection.connect();
        String loc = lol.getText();
        try {
            //the sql string to add a new product
            String sql = "INSERT INTO requests(TYPE,ISSUE,EMPLOYEE_ID,LOCATION,STATUS) VALUES(?,?,?,?,?)";
            //prepares the sql statement by linking it to the database
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //if the values are the correct data types than they are added to the database
            pstmt.setString(1,type);
            pstmt.setString(2, details.getText());
            pstmt.setInt(3, Integer.parseInt(userDetails[0]));
            pstmt.setString(4, loc);
            pstmt.setString(5, "Active");

            pstmt.executeUpdate();

        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                //if the database connection isn't closed than it is closed here
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void homeOnAction() throws IOException {
        Stage stage = (Stage) home.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("homeEmployee.fxml"));
        dialogStage.setTitle("Home");
        dialogStage.setScene(new Scene(root, 1280, 800));
        dialogStage.show();
    }

    public void newRequestOnAction() throws IOException {
        Stage stage = (Stage) newRequest.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("newRequest.fxml"));
        dialogStage.setTitle("New Request");
        dialogStage.setScene(new Scene(root, 1280, 800));
        dialogStage.show();
    }

    public void logoutOnAction() throws IOException {
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        dialogStage.setTitle("Log in");
        dialogStage.setScene(new Scene(root, 1280, 800));
        dialogStage.show();
    }

    public void newReqOnAction(){
        newProduct();
    }
}
