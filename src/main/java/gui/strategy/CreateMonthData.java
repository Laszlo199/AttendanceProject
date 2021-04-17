package gui.strategy;

import be.Months;
import be.ScheduleEntity;
import be.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/**
 * @author Kuba
 * @date 4/14/2021 11:57 AM
 */
public class CreateMonthData implements ICreateDataStrategy{

    @Override
    public ObservableList<PieChart.Data> createData(ScheduleEntity currentLesson,
                                                    Months month, Teacher teacher) {
        int present = model.getClassPresentDays(teacher, month);
        int absent = model.getClassAbsentDays(teacher, month);
        int sum = present + absent;
        if(sum==0)
            return FXCollections.observableArrayList(
                    new PieChart.Data("No data", 100));

        return FXCollections.observableArrayList(
                new PieChart.Data("Present", (present * 100) / sum),
                new PieChart.Data("Absent", (absent * 100) / sum)

        );
    }
}
