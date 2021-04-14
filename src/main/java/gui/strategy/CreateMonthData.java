package gui.strategy;

import be.ScheduleEntity;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

/**
 * @author Kuba
 * @date 4/14/2021 11:57 AM
 */
public class CreateMonthData implements ICreateDataStrategy{
    @Override
    public ObservableList<PieChart.Data> createData(Object timeframe,
                                                    ScheduleEntity currentLesson) {
    return null;
    }
}
