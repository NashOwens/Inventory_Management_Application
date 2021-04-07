package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Request {

    private SimpleIntegerProperty id;
    private SimpleStringProperty type;
    private SimpleStringProperty issue;
    private SimpleIntegerProperty employee_id;
    private SimpleStringProperty location;
    private SimpleStringProperty status;

    //allows the data to be used across classes
    public Request(Integer id, String type, String issue, Integer employee_id, String location, String status) {
        this.id = new SimpleIntegerProperty(Integer.parseInt(String.valueOf(id)));
        this.type = new SimpleStringProperty(type);
        this.issue = new SimpleStringProperty(issue);
        this.employee_id = new SimpleIntegerProperty(employee_id);
        this.location = new SimpleStringProperty(location);
        this.status = new SimpleStringProperty(status);
    }

    public String getStatus() {
        return status.getValue();
    }

    public String setStatus(String status) {
        this.status = new SimpleStringProperty(status);
        return status;
    }

    public String getLocation() {
        return location.getValue();
    }

    public String setLocation(String location) {
        this.location = new SimpleStringProperty(location);
        return location;
    }

    public int getID(){ return id.getValue();}

    public int setID(int id){
        this.id = new SimpleIntegerProperty((id));
        return id;
    }

    public String getType() {
        return type.getValue();
    }

    public String setType(String type) {
        this.type = new SimpleStringProperty(type);
        return type;
    }

    public String getIssue() {
        return issue.getValue();
    }

    public String setIssue(String issue) {
        this.issue = new SimpleStringProperty(issue);
        return issue;
    }

    public int getEmployee_ID(){ return employee_id.getValue();}

    public int setEmployee_ID(int employee_id){
        this.employee_id = new SimpleIntegerProperty((employee_id));
        return employee_id;
    }

}
