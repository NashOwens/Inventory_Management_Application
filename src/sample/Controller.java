package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static sample.Main.userDetails;


public class Controller {

    @FXML
    public Label loginMessageLabel;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public Button login;
    @FXML
    public Label numAttempt;

    private static int attempt = 0;
    private static String dataUsername = null;
    private static String dataPassword = null;
    private static String dataRole = null;
    private static int dataEmployee_ID;

    Stage dialogStage = new Stage();

    @FXML
    public void loginOnAction(){
        if(!username.getText().isBlank() && !password.getText().isBlank()){
            validateLogin();
        }else{
            loginMessageLabel.setText("Incorrect Username and/or Password");
        }
    }

    public String[] validateLogin() {
        Connection conn = databaseConnection.connect();
        Parent root;
        try {
            String user = username.getText();
            String pass = password.getText();
            String sql = "SELECT EMPLOYEE_ID, USERNAME, PASSWORD, ROLE FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            boolean success = false;
            while (rs.next()) {
                dataEmployee_ID = rs.getInt("EMPLOYEE_ID");
                dataUsername = rs.getString("USERNAME");
                dataPassword = rs.getString("PASSWORD");
                dataRole = rs.getString("ROLE");
                if (dataUsername.equals(user) && dataPassword.equals(pass)) {
                    success = true;
                    Stage stage = (Stage) login.getScene().getWindow();
                    stage.close();
                    if (dataRole.equals("Admin")) {
                        userDetails = new String[]{String.valueOf(dataEmployee_ID), user, pass, dataRole};
                        root = FXMLLoader.load(getClass().getResource("adminHome.fxml"));
                    } else {
                        userDetails = new String[]{String.valueOf(dataEmployee_ID), user, pass, dataRole};
                        root = FXMLLoader.load(getClass().getResource("homeEmployee.fxml"));
                    }
                    dialogStage.setTitle("Home");
                    dialogStage.setScene(new Scene(root, 1280, 800));
                    dialogStage.show();
                }
                if (success) {
                    break;
                }
            }
            if (!success) {
                attempt += 1;
                if (attempt == 3) {
                    System.exit(0); //WANT TO GET RID OF
                } else {
                    loginMessageLabel.setText("Username and/or Password Incorrect");
                    numAttempt.setText((3 - attempt) + " more attempt(s)");
                }
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return userDetails;
    }

    public void homeOnAction(ActionEvent actionEvent) {
    }

    public void viewAllOnAction(ActionEvent actionEvent) {
    }

    public void logoutOnAction(ActionEvent actionEvent) {
    }

    public void searchOnAction(ActionEvent actionEvent) {
    }
}
