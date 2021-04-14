package bll.util;

import be.Months;
import be.Student;
import be.Teacher;
import bll.exception.BLLexception;
import dal.exception.DALexception;

import java.util.HashMap;

/**
 * @author Kuba
 * @date 4/14/2021 1:11 PM
 */
public class OverviewAbsenceCalculatorForCourse extends OverviewAbsenceCalculator{

    private Teacher teacher;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    /**
     * before using this method we need to set a teacher
     * @param month
     * @return
     * @throws BLLexception
     */
    @Override
    public HashMap<String, OverviewEntity> getOverviewClassAttendance(Months month) throws BLLexception {
        HashMap<String, OverviewEntity> mapStudents = new HashMap<>();

        try {
            for (Student s: dal.getTaughtStudents(teacher) ) {
                OverviewEntity overviewEntity = new OverviewEntity();
                overviewEntity.setMostAbs(getMostAbsWeekday(month, s));
                overviewEntity.setPresenceThisMonth(getPresence(s, month));
                //overviewEntity.setPresenceThisSem(getPresence(s, Timeframe.SEMESTER ));
                mapStudents.put(s.getName(), overviewEntity);
            }
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get overview class attendance");
        }
        return mapStudents;
    }

    public int getClassPresentDays(Teacher teacher, Months months) throws BLLexception {
        int presNumber=0;
        try {
            for (Student s: dal.getTaughtStudents(teacher) ) {
                presNumber += dal.getNumberOfPresentDays(s, months);
            } return presNumber;
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't get present days of taught students");
        }
    }

    public int getClassAbsentDays(Teacher teacher, Months months) throws BLLexception {
        int absDays=0;
        try {
            for (Student s: dal.getTaughtStudents(teacher) ) {
                absDays += dal.getNumberOfAbsentDays(s, months);
            } return absDays;
        } catch (DALexception daLexception) {
            throw new BLLexception("Couldn't get absent days of taught students");
        }
    }


}
