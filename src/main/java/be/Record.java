package be;

import java.util.Date;

public class Record {

    private int id;
    private int studentId;
    private Date date;
    private int scheduleEntityId;
    private boolean isPresent;

    public Record(int id, int studentId, Date date, int scheduleEntityId, boolean isPresent) {
        this.id = id;
        this.studentId = studentId;
        this.date = date;
        this.scheduleEntityId = scheduleEntityId;
        this.isPresent = isPresent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScheduleEntityId() {
        return scheduleEntityId;
    }

    public void setScheduleEntityId(int scheduleEntityId) {
        this.scheduleEntityId = scheduleEntityId;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
