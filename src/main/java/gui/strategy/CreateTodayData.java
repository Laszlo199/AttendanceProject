package gui.strategy;

import be.Months;
import be.ScheduleEntity;
import be.Teacher;
import gui.model.TeacherDashboardModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/**
 * @author Kuba
 * @date 4/14/2021 11:43 AM
 */
public class CreateTodayData implements ICreateDataStrategy{

    @Override
    public ObservableList<PieChart.Data> createData(ScheduleEntity currentLesson,
                                                    Months month, Teacher teacher) {
        int absent = model.getNumberOfAbsent(currentLesson);
        int present = model.getNumberOfPresent(currentLesson);
        int noAllStudents = model.getNumberOfAllStudents(currentLesson);
        int sumOfStudents = absent + present + noAllStudents;

        if(sumOfStudents==0)
            return FXCollections.observableArrayList(
                    new PieChart.Data("No data", 100));

        return FXCollections.observableArrayList(
                new PieChart.Data("Absent", (absent * 100) / sumOfStudents),
                new PieChart.Data("Present", (present * 100) / sumOfStudents),
                new PieChart.Data("No data",
                        ((noAllStudents - absent - present) * 100) / sumOfStudents)
        );
    }
}
