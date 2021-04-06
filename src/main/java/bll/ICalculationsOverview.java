package bll;

import be.Months;
import be.Student;
import be.WeekDay;
import bll.exception.BLLexception;

import java.util.HashMap;

/**
 * @author Kuba
 * @date 3/22/2021 5:32 PM
 */
public interface ICalculationsOverview {
        // Name, encapsulated data.
        HashMap<String,  OverviewAbsenceCalculator.OverviewEntity>
        getOverviewClassAttendance(Months month) throws BLLexception;
        int getPresence(Student student, Months month) throws BLLexception;
        WeekDay getMostAbsWeekday(Months month, Student student) throws BLLexception;
}
