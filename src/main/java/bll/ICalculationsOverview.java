package bll;

import be.Months;
import be.Student;
import be.WeekDay;

import java.util.HashMap;

/**
 * @author Kuba
 * @date 3/22/2021 5:32 PM
 */
public interface ICalculationsOverview {
        // Name, encapsulated data.
        HashMap<String,  OverviewAbsenceCalculator.OverviewEntity>
        getOverviewClassAttendance(Months month);
        int getPresence(Student student, Months month);
        WeekDay getMostAbsWeekday(Months month, Student student);
}
