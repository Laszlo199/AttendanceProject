package be;

import java.sql.Time;
import java.time.LocalDateTime;

public class ScheduleEntity {

    private int id;
    private int subjectId;
    private WeekDay weekDay;
    private Time startTime;
    private Time endTime;

    public ScheduleEntity(int id, int subjectId, WeekDay weekDay, Time startTime, Time endTime) {
        this.id = id;
        this.subjectId = subjectId;
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    @Override
    public String toString() {
        return "ScheduleEntity{" +
                "id=" + id +
                ", subjectId=" + subjectId +
                ", weekDay=" + weekDay +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
