package be;

public class Teacher extends User {

    private String department;

    public Teacher(int id, String name, String email, String photoPath, String department) {
        super(id, name, email, photoPath);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
