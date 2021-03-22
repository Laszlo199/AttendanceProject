package be;

public class Student extends User {

    private int semester;
    private int courseID;

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
}
