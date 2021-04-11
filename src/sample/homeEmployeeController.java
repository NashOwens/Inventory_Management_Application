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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.Main.userDetails;

public class homeEmployeeController implements Initializable {

    Stage dialogStage = new Stage();
    Scene scene;

    @FXML
    public Button home;
    @FXML
    public Button logout;
    @FXML
    public Button search;
    @FXML
    public TextField searchBar;
    @FXML
    public Button newRequest;

    private ObservableList<Request> data;

    private int id;
    private String type;
    private String issue;
    private int employee_id;
    private String location;
    private String status;

    public String sql  = "SELECT * FROM requests WHERE EMPLOYEE_ID = ?";
    //initialises string searching
    public static String searching = null;
    //initialises string boolean
    public static boolean menuBoolean = true;
    public ResultSet res;


    @FXML
    private TableView<Request> tableview;
    @FXML
    private TableColumn<Request, Integer> idColumn;
    @FXML
    private TableColumn<Request, Integer> employee_idColumn;
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
                new PropertyValueFactory<Request, Integer>("id"));
        //the column labelled Password is set the value of password within the user class
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<Request,String>("type"));
        //the column labelled Role is set the value of role within the user class
        issueColumn.setCellValueFactory(
                new PropertyValueFactory<Request,String>("issue"));
        employee_idColumn.setCellValueFactory(
                new PropertyValueFactory<Request, Integer>("employee_id"));
        locationColumn.setCellValueFactory(
                new PropertyValueFactory<Request,String>("location"));
        statusColumn.setCellValueFactory(
                new PropertyValueFactory<Request,String>("status"));
        System.out.println("GAY");
        try {
            displayAll();
            System.out.println("HELLO");
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
            sql = "SELECT * FROM requests WHERE REQUEST_ID = ? AND EMPLOYEE_ID = ?";
            //runs the method viewProducts
            displayAll();
        }
    }

    public void displayAll() {
        //makes data a new empty observable list that is backed by an arraylist.
        data = FXCollections.observableArrayList();
        //creates a connection to the database
        Connection conn = databaseConnection.connect();
        try {
            if(menuBoolean) {
                //assigns the ability to create an sql statement to the variable stmt
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, userDetails[0]);
                //assigns the ability to execute the sql statement (get the result set) to the variable res
                res = stmt.executeQuery();
                //if b is equal to false the products are ordered in a certain way depending on the sql statement
            }if(!menuBoolean){
                //assigns the ability to parepare an sql statement to the variable found
                PreparedStatement found = conn.prepareStatement(sql);
                //sets the value of ? in the sql statement to the value of the variable searching
                found.setString(1, searching);
                found.setString(2, userDetails[0]);
                //assigns the ability to execute the sql statement to the varuable res
                res = found.executeQuery();
            }
            while (res.next()) {
                //a new user is created based off thr class user
                Request request = new Request(id, type, issue, employee_id, location, status);
                request.setID(res.getInt("EMPLOYEE_ID"));

                request.setType(res.getString("TYPE"));

                request.setIssue(res.getString("ISSUE"));

                request.setEmployee_ID(res.getInt(3));

                request.setLocation(res.getString("LOCATION"));
                request.setStatus(res.getString("STATUS"));
                //user is added to the observable list data
                data.add(request);
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

    public void activeOnAction(){
        menuBoolean = true;
        sql = "SELECT * FROM requests WHERE STATUS = 'Active' AND EMPLOYEE_ID = ?";
        displayAll();
    }

    public void deactiveOnAction(){
        menuBoolean = true;
        sql = "SELECT * FROM requests WHERE STATUS = 'Solved' AND EMPLOYEE_ID = ?";
        displayAll();
    }
    private final String[] optionFile = {"HomeEmployee.fxml","newRequest.fxml", "login.fxml"};
    private final String[] setTitle = {"Home", "New Request", "Login in"};

    public void OnAction(String choiceClass, String title) throws IOException{
        menuBoolean = true;
        Parent root = FXMLLoader.load(getClass().getResource(choiceClass));
        dialogStage.setTitle(title);
        dialogStage.setScene(new Scene(root, 1280, 800));
        dialogStage.show();
    }

    public void homeOnAction() throws IOException {
        Stage stage = (Stage) home.getScene().getWindow();
        stage.close();
        OnAction(optionFile[0], setTitle[0]);
    }

    public void newRequestOnAction() throws IOException {
        Stage stage = (Stage) newRequest.getScene().getWindow();
        stage.close();
        OnAction(optionFile[1], setTitle[1]);
    }

    public void logoutOnAction() throws IOException {
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
        OnAction(optionFile[2], setTitle[2]);
    }
}
