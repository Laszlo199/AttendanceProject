package be;

public class Student extends User {

    private int semester;
    private int courseID;
    private int totalPresence;
    private WeekDay mostAbsWeekday;

    public Student(int id, String name, String email, String photoPath, int semester, int courseID, int totalPresence, WeekDay mostAbsWeekday) {
        super(id, name, email, photoPath);
        this.semester = semester;
        this.courseID = courseID;
        this.totalPresence = totalPresence;
        this.mostAbsWeekday = mostAbsWeekday;
    }

    public Student(int id, String name, String email, String photoPath, int semester, int courseID) {
        super(id, name, email, photoPath);
        this.semester = semester;
        this.courseID = courseID;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getTotalPresence() {
        return totalPresence;
    }

    public void setTotalPresence(int totalPresence) {
        this.totalPresence = totalPresence;
    }

    public WeekDay getMostAbsWeekday() {
        return mostAbsWeekday;
    }

    public void setMostAbsWeekday(WeekDay mostAbsWeekday) {
        this.mostAbsWeekday = mostAbsWeekday;
    }

    @Override
    public String toString() {
        return "Student{" +
                "semester=" + semester +
                ", courseID=" + courseID +
                '}';
    }
}
