package bll.util;

import be.Months;
import be.Student;
import bll.exception.BLLexception;
import dal.FacadeDAL;
import dal.IFacadeDAL;
import dal.exception.DALexception;
import gui.controller.TeacherViewController;

/**
 * @author Kuba
 * @date 4/9/2021 9:56 AM
 */
public class PresenceCalculator {
    private IFacadeDAL dal = FacadeDAL.getInstance();
    OverviewAbsenceCalculator overviewAbsenceCalculator =
            new OverviewAbsenceCalculator();

    /**
     * method handles request from a tableview in the teacher's dashboard
     * it will return:
     * - TODAY -> PRESENT /ABSENT
     * - MONTH -> ex 43%
     * - TOTAL -> ex. 73%
     * @param student
     * @param timeframe
     * @return
     */
    public String getPresenceForStudent(Student student, TeacherViewController.Timeframe timeframe) throws DALexception {
        switch (timeframe){
            case TODAY: return   getPresenceToday(student);

            case TOTAL: return getPresenceTotal(student);
        }
        return getPresenceTotal(student);
    }

    private String getPresenceTotal(Student student) throws DALexception {
        int presentDays = dal.getTotalNumberOfPresentDays(student);
        int absentDays = dal.getTotalNumberOfAbsentDays(student);
       // System.out.println("we got there");
        if(presentDays + absentDays == 0)
            return "No data";

        return (presentDays/(presentDays+absentDays))*100 + "%";

    }

    private String getPresenceToday(Student student) throws DALexception {
        return null;
    }

    public String getMostAbsentDay(Student student, TeacherViewController.Timeframe timeframe) throws BLLexception {
        return overviewAbsenceCalculator.getMostAbsWeekday(Months.APRIL, student).name();

    }
}
