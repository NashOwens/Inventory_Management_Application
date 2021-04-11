package sample;

import java.util.ArrayList;


public class RequestNew {
    private static int id;
    private static String type;
    private static String issue;
    private static int employee_id;
    private static String location;
    private static String status;

    //allows the data to be used across classes
    public static ArrayList<String> Request(int idNew, String typeNew, String issueNew, int employee_idNew, String locationNew, String statusNew) {
        ArrayList<String> requestNew = Request(idNew, typeNew, issueNew, employee_idNew, locationNew, statusNew);
        return requestNew;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        sample.RequestNew.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        sample.RequestNew.location = location;
    }

    public int getID(){
        return id;}

    public void setID(int id){
        sample.RequestNew.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        sample.RequestNew.type = type;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        sample.RequestNew.issue = issue;
    }

    public int getEmployee_ID(){
        return employee_id;}

    public void setEmployee_ID(int employee_id){
        sample.RequestNew.employee_id = employee_id;
    }

}