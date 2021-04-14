package gui.strategy;

import be.ScheduleEntity;
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
    public ObservableList<PieChart.Data> createData(Object timeframe,
                                                    ScheduleEntity currentLesson) {
        int absent = model.getNumberOfAbsent(currentLesson);
        int present = model.getNumberOfPresent(currentLesson);
        int noAllStudents = model.getNumberOfAllStudents(currentLesson);
        int sumOfStudents = absent + present + noAllStudents;
        return FXCollections.observableArrayList(
                new PieChart.Data("Absent", (absent * 100) / sumOfStudents),
                new PieChart.Data("Present", (present * 100) / sumOfStudents),
                new PieChart.Data("No data",
                        ((noAllStudents - absent - present) * 100) / sumOfStudents)
        );
    }
}
