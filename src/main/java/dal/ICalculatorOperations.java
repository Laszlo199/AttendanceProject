package dal;

import be.Student;
import be.WeekDay;
import bll.Calculations;

import java.util.Collections;
import java.util.List;

/**
 * here are the methods i need for my calculator
 * @author Kuba
 * @date 3/22/2021 9:01 PM
 */
public interface ICalculatorOperations {
    int getNumberOfPresentDays(Student student, Calculations.Timeframe timeframe);
    int getNumberOfAbsentDays(Student student, Calculations.Timeframe timeframe);
    List<Student> getAllStudents();

}
