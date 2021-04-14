package dal;

import be.Months;
import be.Student;
import be.Teacher;
import dal.exception.DALexception;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Kuba
 * @date 4/9/2021 11:46 AM
 */
class AbsenceDataTest {

    @org.junit.jupiter.api.Test
    void isStudentPresent() {

    }

    @org.junit.jupiter.api.Test
    void isDataStudentPresent() {
        AbsenceData absenceData = new AbsenceData();
        boolean result = absenceData.isStudentPresent(1);
        boolean expectedResult = true;
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void getNumberOfPresentDays() {
        AbsenceData absenceData = new AbsenceData();
        Student student = new Student(1, "Dorelia McCawley", "dmccawley0@epa.gov",
                null,
                1, 1);
        int actual = 0;
        try {
            actual = absenceData.getNumberOfPresentDays(student, Months.MARCH);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
        }
        int expected = 1;
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void getNumberOfAbsentDays() {
        AbsenceData absenceData = new AbsenceData();
        Student student = new Student(1, "Dorelia McCawley", "dmccawley0@epa.gov",
                null,
                1, 1);
        int actual = 0;
        try {
            actual = absenceData.getNumberOfAbsentDays(student, Months.MARCH);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
        }
        int expected = 1;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getTaughtStudents() {
        AbsenceData absenceData = new AbsenceData();
       Teacher teacher = new Teacher(1, "Tom Tom",
               "tom@ee.dk", null, "IT");
        try {
            List<Student> studentList = absenceData.getTaughtStudents(teacher);
            for(Student s:studentList)
                System.out.println(s);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
        }
    }
}