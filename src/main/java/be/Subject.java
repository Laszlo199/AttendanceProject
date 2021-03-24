package be;

public class Subject {

    private int id;
    private String name;
    private int teacherId;
    private int courseId;

    public Subject(int id, String name, int teacherId, int courseId) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
