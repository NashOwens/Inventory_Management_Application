package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static sample.Main.requestID;

public class adminHomeController implements Initializable {

    public Button viewEmployee;
    Stage dialogStage = new Stage();
    Scene scene;

    @FXML
    public Button home;
    @FXML
    public Button viewAll;
    @FXML
    public Button logout;
    @FXML
    public Button search;
    @FXML
    public TextField searchBar;


    private int id;
    private String type;
    private String issue;
    private int employee_id;
    private String location;
    private String status;



    @FXML
    private TableView<Request> tableview;
    @FXML
    private TableColumn<Request, Integer> idColumn;
    @FXML
    private TableColumn<Request, String> employee_idColumn;
    @FXML
    private TableColumn<Request,String> typeColumn;
    @FXML
    private TableColumn<Request,String> issueColumn;
    @FXML
    private TableColumn<Request,String> locationColumn;
    @FXML
    private TableColumn<Request,String> statusColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert tableview != null;
        //the column labelled Username is set the value of username within the user class
        idColumn.setCellValueFactory(
                new PropertyValueFactory<Request,Integer>("id"));
        //the column labelled Password is set the value of password within the user class
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<Request,String>("type"));
        //the column labelled Role is set the value of role within the user class
        issueColumn.setCellValueFactory(
                new PropertyValueFactory<Request,String>("issue"));
        employee_idColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("employee_id"));
        locationColumn.setCellValueFactory(
                new PropertyValueFactory<Request,String>("location"));
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<Request,String>("status"));
        try {
            displayAll();
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    public void searchOnAction(){
        tableview.getItems().clear();
        menuBoolean = false;
        //if the textfield searchProduct is not blank
        if(!searchBar.getText().isBlank()) {
            //gets the text from the search bar
            searching = searchBar.getText();
            //creates a new sql statement
            sql = "SELECT * FROM requests WHERE REQUEST_ID = ?";
            //runs the method viewProducts
            displayAll();
        }
    }

    private String sql  = "SELECT * FROM requests WHERE STATUS = 'Active'";
    //initialises string searching
    private static String searching = null;
    //initialises string boolean
    private static boolean menuBoolean = true;
    private ResultSet res;

    public void displayAll() {
        //makes data a new empty observable list that is backed by an arraylist.
        ObservableList<Request> data = FXCollections.observableArrayList();
        //creates a connection to the database
        Connection conn = databaseConnection.connect();
        try {
            if(menuBoolean) {
                //assigns the ability to create an sql statement to the variable stmt
                Statement stmt = conn.createStatement();
                //assigns the ability to execute the sql statement (get the result set) to the variable res
                res = stmt.executeQuery(sql);
                //if b is equal to false the products are ordered in a certain way depending on the sql statement
            }if(!menuBoolean){
                //assigns the ability to prepare an sql statement to the variable found
                PreparedStatement found = conn.prepareStatement(sql);
                //sets the value of ? in the sql statement to the value of the variable searching
                found.setString(1, searching);
                //assigns the ability to execute the sql statement to the varuable res
                res = found.executeQuery();
            }
            while (res.next()) {
                //a new user is created based off thr class user
                Request request = new Request(id, type, issue, employee_id, location, status);
                request.setID(res.getInt("REQUEST_ID"));

                request.setType(res.getString("TYPE"));

                request.setIssue(res.getString("ISSUE"));

                request.setEmployee_ID(res.getInt("EMPLOYEE_ID"));

                request.setLocation(res.getString("LOCATION"));

                request.setStatus(res.getString("STATUS"));
                //user is added to the observable list data
                data.addAll(request);
            }
            //sets the item within the correct row and column in the database
            tableview.setItems(data);
        } catch (
                SQLException e) {
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

    private final String[] optionFile = {"adminHome.fxml","viewAllAdmin.fxml", "login.fxml", "employeeDetails.fxml"};
    private final String[] setTitle = {"Home", "View All", "Login in", "Employee details"};

    public void OnAction(String choiceClass, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(choiceClass));
        dialogStage.setTitle(title);
        dialogStage.setScene(new Scene(root, 1280, 800));
        dialogStage.show();
    }
    public void homeOnAction() throws IOException  {
        menuBoolean = true;
        Stage stage = (Stage) home.getScene().getWindow();
        stage.close();
        OnAction(optionFile[0], setTitle[0]);
    }
    public void viewAllOnAction() throws IOException {
        menuBoolean = true;
        Stage stage = (Stage) viewAll.getScene().getWindow();
        stage.close();
        OnAction(optionFile[1], setTitle[1]);
    }
    public void logoutOnAction() throws IOException {
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
        OnAction(optionFile[2], setTitle[2]);
    }
    public void viewEmployeeOnAction() throws IOException {
        displayemployeeID();
        menuBoolean = true;
        Stage stage = (Stage) home.getScene().getWindow();
        stage.close();
        OnAction(optionFile[3], setTitle[3]);
    }

    public int displayemployeeID(){
        Request request = tableview.getSelectionModel().getSelectedItem();
        requestID = request.getID();
        return requestID;
    }



    public void solveIssue(String statusInsert){
        Connection conn = databaseConnection.connect();
        try {
            if (requestID == 0) {
                JOptionPane.showMessageDialog(null, "Please select a request from the table first");
            } else {
                Request request = tableview.getSelectionModel().getSelectedItem();
                requestID = request.getID();
            }
            String sql = "UPDATE requests SET STATUS = '" + statusInsert + "' WHERE REQUEST_ID = ?";
            //links the sql statement to the database
            PreparedStatement ps = conn.prepareStatement(sql);
            //sets the ? to the id of the row selected
            ps.setInt(1, requestID);
            //executes the sql statement
            ps.executeUpdate();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                //if the database connection isn't closed, its closed here
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    private final String[] choiceStatus = {"Solved", "Ongoing"};

    public void solvedOnAction() {
        solvedOrOnGoingOnAction(choiceStatus[0]);
    }
    public void solvedOrOnGoingOnAction(String option){
        ObservableList<Request> allRequest, SingleRequest;
        //assigns all the data in the table to the observale list allUser
        allRequest = tableview.getItems();
        //assigns the data from the row selected to the observable list SingleUser
        SingleRequest = tableview.getSelectionModel().getSelectedItems();
        //removes the vale of singleUser from the observable list allUser
        solveIssue(option);
        SingleRequest.forEach(allRequest::remove);
    }

    public void onGoingOnAction(){
        solvedOrOnGoingOnAction(choiceStatus[1]);

    }

}
