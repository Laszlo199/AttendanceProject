package bll.util;

import be.ScheduleEntity;
import be.Student;
import bll.exception.BLLexception;
import dal.AbsenceData;
import dal.IAbsenceData;
import dal.exception.DALexception;

import java.util.List;

/**
 * @author kamila
 */
public class SingleDayAbsenceCalculator {

    private ScheduleEntity scheduleEntity;

    private IAbsenceData dal;

    public SingleDayAbsenceCalculator(ScheduleEntity scheduleEntity) {
        dal = new AbsenceData();
        this.scheduleEntity = scheduleEntity;
    }

    public List<Student> getAbsentToday() throws BLLexception {
        try {
            return dal.getAbsentToday(this.scheduleEntity);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get a list of absent students today");
        }
    }

    public List<Student> getPresentToday() throws BLLexception {
        try {
            return dal.getPresentToday(this.scheduleEntity);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get a list of present students today");
        }
    }

    public int getNumberOfAbsentStudents() throws BLLexception {
        try {
            return dal.getNumberOfAbsentToday(this.scheduleEntity);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get a number of absent students today");
        }
    }

    public int getNumberOfPresentStudents() throws BLLexception {
        try {
            return dal.getNumberOfPresentToday(this.scheduleEntity);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get a number of absent students today");
        }
    }
}
