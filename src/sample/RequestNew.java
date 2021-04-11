package sample;


public class RequestNew {
    private static int id;
    private String type;
    private String issue;
    private int employee_id;
    private String location;
    private String status;
    public static Request requestNew;


    //allows the data to be used across classes
    public static Request setRequestNew(int idNew, String typeNew, String issueNew, int employee_idNew, String locationNew, String statusNew) {
        requestNew = new Request(idNew, typeNew, issueNew, employee_idNew, locationNew, statusNew);
        return requestNew;
    }
    public Request getRequestNew() { return  requestNew;}

    public String getStatus() { return status; }

    public String setStatus(String status) {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public String setLocation(String location) {
        return location;
    }

    public static int getID(){
        return id;}

    public int setID(int id){
        return id;
    }

    public String getType() {
        return type;
    }

    public String setType(String type) {
        return type;
    }

    public String getIssue() {
        return issue;
    }

    public String setIssue(String issue) {
        return issue;
    }

    public int getEmployee_ID(){
        return employee_id;}

    public int setEmployee_ID(int employee_id){
        return employee_id;
    }

}