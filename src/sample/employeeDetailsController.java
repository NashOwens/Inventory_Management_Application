package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.Main.requestID;

public class employeeDetailsController implements Initializable {
    public static boolean menuBoolean = true;
    Stage dialogStage = new Stage();
    Scene scene;

    @FXML
    public Label employeeEmail;
    @FXML
    public Button close;
    @FXML
    public Label employeeNum;
    @FXML
    public Button home;
    @FXML
    public Button viewAll;
    @FXML
    public Button logout;

    public ResultSet res;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayDetails();
    }

    public int employee_id;

    public void displayDetails(){
        Connection conn = databaseConnection.connect();
        try{
            String sql = "SELECT EMPLOYEE_ID FROM requests WHERE REQUEST_ID = ?";
            PreparedStatement found = conn.prepareStatement(sql);
            //sets the value of ? in the sql statement to the value of the variable searching
            found.setInt(1, requestID);
            //assigns the ability to execute the sql statement to the varuable res
            res = found.executeQuery();

            employee_id = res.getInt("EMPLOYEE_ID");

            getDetails();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                //if the database connection is not closed, it is closed here
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void getDetails(){
        Connection conn = databaseConnection.connect();
        try{
            String sql = "SELECT EMAIL, PHONE_NUM FROM users WHERE EMPLOYEE_ID = ?";
            PreparedStatement found = conn.prepareStatement(sql);
            //sets the value of ? in the sql statement to the value of the variable searching
            found.setInt(1, employee_id);
            //assigns the ability to execute the sql statement to the varuable res
            res = found.executeQuery();

            String email = res.getString("EMAIL");
            String phone = res.getString("PHONE_NUM");

            employeeEmail.setText(email);
            employeeNum.setText("0" + phone);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                //if the database connection is not closed, it is closed here
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void homeOnAction() throws IOException {
        menuBoolean = true;
        Stage stage = (Stage) home.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("adminHome.fxml"));
        dialogStage.setTitle("Home");
        dialogStage.setScene(new Scene(root, 1280, 800));
        dialogStage.show();
    }

    public void viewAllOnAction() throws IOException {
        menuBoolean = true;
        Stage stage = (Stage) viewAll.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("viewAllAdmin.fxml"));
        dialogStage.setTitle("View All");
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

    public void closeOnAction() throws IOException {
        menuBoolean = true;
        Stage stage = (Stage) home.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("adminHome.fxml"));
        dialogStage.setTitle("Home");
        dialogStage.setScene(new Scene(root, 1280, 800));
        dialogStage.show();
    }
}
