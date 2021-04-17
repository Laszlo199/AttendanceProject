package gui.util;

import gui.controller.StudentDashboardController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * @author Kuba
 * @date 4/17/2021 11:13 AM
 */
public class HoverChart {
    private static boolean threePies;

    public static boolean isThreePies() {
        return threePies;
    }

    public static void setThreePies(boolean threePies) {
        HoverChart.threePies = threePies;
    }

   private static void unhoveredPieChart(PieChart donutChart, Label caption) {
        donutChart.getData()
                .stream()
                .forEach(data -> {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                        caption.setVisible(false);
                    });
                });
    }

    private static void hoveredPieChart(PieChart donutChart, Label caption, ObservableList<PieChart.Data> pieChartData) {
        donutChart.getData()
                .stream()
                .forEach(data -> {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                        Point2D locationInScene = new Point2D(e.getSceneX(), e.getSceneY());
                        Point2D locationInParent = donutChart.sceneToLocal(locationInScene);
                        caption.relocate(locationInParent.getX(), locationInParent.getY());
                        // caption.setText(String.valueOf(data.getPieValue()));
                        if(isThreePies()){
                            int present  = (int) pieChartData.get(0).getPieValue();
                            int absent = (int) pieChartData.get(1).getPieValue();
                            int noData = (int) pieChartData.get(2).getPieValue();
                            if(data.getName().matches("Present"))
                                caption.setText("Presence: " + present + "%");
                            else if(data.getName().matches("Absent"))
                                caption.setText("Presence: " + absent + "%");
                            else if(data.getName().matches("No data"))
                                caption.setText("Presence: " + noData + "%");
                        }
                        else{
                            int avg = (int) ((pieChartData.get(0).getPieValue() / (pieChartData.get(0).getPieValue() +
                                    pieChartData.get(1).getPieValue())) * 100);
                            if (data.getName().matches("Present"))
                                caption.setText("Presence: " + avg + "%");
                            else if (data.getName().matches("Absent"))
                                caption.setText("Absence: " + (100 - avg) + "%");
                            caption.setVisible(true);
                        }
                    });
                });
    }

   public static void listenerPieChart(PieChart donutChart, Label caption, ObservableList<PieChart.Data> pieChartData) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                hoveredPieChart(donutChart, caption, pieChartData);
                unhoveredPieChart(donutChart, caption);
            }
        });
    }
}
