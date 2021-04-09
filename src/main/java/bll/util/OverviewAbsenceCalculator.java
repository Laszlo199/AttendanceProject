package bll.util;

import be.Months;
import be.Student;
import be.Subject;
import be.WeekDay;
import bll.ICalculationsOverview;
import bll.exception.BLLexception;
import dal.AbsenceData;
import dal.FacadeDAL;
import dal.IAbsenceData;
import dal.IFacadeDAL;
import dal.exception.DALexception;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kuba
 * @date 3/22/2021 5:32 PM
 */
public class OverviewAbsenceCalculator implements ICalculationsOverview {

    //private IAbsenceData dal = new AbsenceData(); // concrete class created by kamila that
    //implements that interface
    private IFacadeDAL dal  = FacadeDAL.getInstance();

    /**
     * Names are keys and Overview entities are values
     * method returns data that will fill in tableview with overview
     * Hashmap is used because teacher will have ability to search
     * the data for specific user and hashmap will be faster
     *
     * @return HashMap<String, OverviewEntity>
     */
    @Override
    public HashMap<String, OverviewEntity> getOverviewClassAttendance(Months month) throws BLLexception {
        HashMap<String, OverviewEntity> mapStudents = new HashMap<>();

        try {
            for (Student s: dal.getAllStudents() ) {
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

    /**
     * kamila will get it from DAL
     * present days and unpresent days
     * @return
     */
    @Override
    public int getPresence(Student student, Months month) throws BLLexception {
        int presentDays = 0;
        try {
            presentDays = dal.getNumberOfPresentDays(student, month);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get number of present days");
        }
        int absentDats  = 0;
        try {
            absentDats = dal.getNumberOfAbsentDays(student, month);
        } catch (DALexception daLexception) {
            daLexception.printStackTrace();
            throw new BLLexception("Couldn't get number of present days");
        }
        return presentDays/(presentDays+absentDats);
    }

    //overall we need to get present and unpresent days
    // - on the specific course
    // - on the semester
    // - that month

    /**
     * Im not sure in which time frame
     * it can be month sem or year
     * maybe all?
     * @return
     */
    @Override
    public WeekDay getMostAbsWeekday(Months month, Student student) throws BLLexception{
        //key is weekday value is the present days in percents
        Map<WeekDay, Integer> weekDayIntegerMap = new HashMap<>();

        //for (WeekDay day: WeekDay.values()) {

        try {
            for (WeekDay day : WeekDay.values()) {
                int presentDays = dal.getNumberOfPresentDays(student, month); //exception is inside this method
                int absDays = dal.getNumberOfAbsentDays(student, month);
               // int avg = presentDays / (presentDays + absDays);
                int avg = 0;
                if(presentDays + absDays!=0)
                    avg = presentDays / (presentDays + absDays);
                weekDayIntegerMap.put(day, avg);
            }
        } catch (DALexception ex){
            ex.printStackTrace();
            throw new BLLexception("Couldn't get number of pres/abs days");
        }

        //get the smallest value
        Map.Entry<WeekDay, Integer> min = null;
        for (Map.Entry<WeekDay, Integer> entry : weekDayIntegerMap.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }

        return min.getKey();
    }

    public DayOfWeek getAbsORPresentForDay() throws BLLexception {

       HashMap<DayOfWeek, Integer> hasmap= new HashMap<>();
       try {
           for (DayOfWeek day : DayOfWeek.values()) {
               int present = dal.getPresentForDay(day);
               int abs = dal.getAbsForDay(day);
               int pr = present / (present + abs);
               hasmap.put(day, pr);
           }
       } catch (DALexception ex) {
           ex.printStackTrace();
           throw new BLLexception("Couldn't get number of present days");
       }

        HashMap.Entry<DayOfWeek, Integer> min = null;
        for (HashMap.Entry<DayOfWeek, Integer> entry : hasmap.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }

        return min.getKey();
    }

    /**
     * class used by overview method.
     * Data will be wrapped up and encapsulated
     */
    public class OverviewEntity{
        private int presenceThisMonth;
        private int presenceThisSem;
        private WeekDay mostAbs;


        public int getPresenceThisMonth() {
            return presenceThisMonth;
        }

        public void setPresenceThisMonth(int presenceThisMonth) {
            this.presenceThisMonth = presenceThisMonth;
        }

        public int getPresenceThisSem() {
            return presenceThisSem;
        }

        public void setPresenceThisSem(int presenceThisSem) {
            this.presenceThisSem = presenceThisSem;
        }

        public WeekDay getMostAbs() {
            return mostAbs;
        }

        public void setMostAbs(WeekDay mostAbs) {
            this.mostAbs = mostAbs;
        }
    }

}
