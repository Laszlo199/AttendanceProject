package gui.strategy;

import be.Months;
import be.ScheduleEntity;
import gui.model.TeacherDashboardModel;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/**
 * Interface for all strategies
 * @author Kuba
 * @date 4/14/2021 11:39 AM
 */
public interface ICreateDataStrategy {
    TeacherDashboardModel model = TeacherDashboardModel.getInstance();
    ObservableList<PieChart.Data> createData(Object timeframe, ScheduleEntity currentLesson);
}
