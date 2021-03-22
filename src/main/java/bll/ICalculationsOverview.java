package bll;

import be.Student;
import be.WeekDay;

import java.util.HashMap;
import java.util.List;

/**
 * @author Kuba
 * @date 3/22/2021 5:32 PM
 */
public interface ICalculationsOverview {
        // Name, encapsulated data.
        HashMap<String,  Calculations.OverviewEntity>
        getOverviewClassAttendance(Calculations.Timeframe timeframe);
        int getPresence(Student student, Calculations.Timeframe timeframe);
        WeekDay getMostAbsWeekday(Calculations.Timeframe timeframe, Student student);
}
