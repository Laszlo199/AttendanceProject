package bll;

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
        getOverviewClassAttendance(OverviewAbsenceCalculator.Timeframe timeframe);
        int getPresence(Student student, OverviewAbsenceCalculator.Timeframe timeframe);
        WeekDay getMostAbsWeekday(OverviewAbsenceCalculator.Timeframe timeframe, Student student);
}
