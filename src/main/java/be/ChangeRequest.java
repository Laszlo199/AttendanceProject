package be;

import java.util.Date;

public class ChangeRequest {

    private int recordId;
    private StatusType status;

    private String studentName;
    private String subjectName;
    private Date date;

    public ChangeRequest(int recordId, StatusType status) {
        this.recordId = recordId;
        this.status = status;
    }

    //to display values in the table view
    public ChangeRequest(int recordId, StatusType status, String studentName, String subjectName, Date date) {
        this.recordId = recordId;
        this.status = status;
        this.studentName = studentName;
        this.subjectName = subjectName;
        this.date = date;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
