package be;

import java.time.LocalDateTime;

public class ScheduleEntity {

    private int id;
    private int subjectId;
    private WeekDay weekDay;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ScheduleEntity(int id, int subjectId, WeekDay weekDay, LocalDateTime startTime, LocalDateTime endTime) {
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

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
