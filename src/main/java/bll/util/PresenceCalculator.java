package bll.util;

import be.Months;
import be.Student;
import bll.exception.BLLexception;
import dal.FacadeDAL;
import dal.IFacadeDAL;
import dal.exception.DALexception;
import gui.controller.TeacherViewController;

import java.util.concurrent.*;

/**
 * @author Kuba
 * @date 4/9/2021 9:56 AM
 */
public class PresenceCalculator {
    private IFacadeDAL dal = FacadeDAL.getInstance();
    OverviewAbsenceCalculator overviewAbsenceCalculator =
            new OverviewAbsenceCalculator();
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * method handles request from a tableview in the teacher's dashboard
     * it will return:
     * - TODAY -> PRESENT /ABSENT
     * - MONTH -> ex 43%
     * - TOTAL -> ex. 73%
     *
     * it shold work in another thread
     * @param student
     * @param timeframe
     * @return
     */
    public String getPresenceForStudent(Student student, TeacherViewController.Timeframe timeframe) throws BLLexception {
        switch (timeframe){
            case TODAY: return   getPresenceToday(student);

            case TOTAL: return getPresenceTotal(student);
        }
        return getPresenceTotal(student);
    }

    /**
     * make it run in another thread
     * its an experiment
     * @param student
     * @return
     * @throws DALexception
     */
    private String getPresenceTotal(Student student) throws BLLexception {
        Callable<String> callable = () ->{
            int presentDays = dal.getTotalNumberOfPresentDays(student);
            int absentDays = dal.getTotalNumberOfAbsentDays(student);
            // System.out.println("we got there");
            if(presentDays + absentDays == 0)
                return "No data";

            return (presentDays/(presentDays+absentDays))*100 + "%";
        };
        Future<String> future  =executorService.submit(callable);
        try {
            return future.get();
        } catch (InterruptedException e) {
            throw new BLLexception("Couldnt get presence in total", e);
        } catch (ExecutionException e) {
            throw new BLLexception("Couldnt get presence in total", e);
        }
    }

    private String getPresenceToday(Student student) {
        return null;
    }

    public String getMostAbsentDay(Student student, TeacherViewController.Timeframe timeframe) throws BLLexception {
        return overviewAbsenceCalculator.getMostAbsWeekday(Months.APRIL, student).name();

    }
}
