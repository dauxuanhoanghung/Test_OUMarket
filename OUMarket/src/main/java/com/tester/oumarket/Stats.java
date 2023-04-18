/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.service.OrderService;
import com.tester.service.impl.OrderServiceImpl;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author LENOVO
 */
public class Stats extends AbstractManageController {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        OrderService os = new OrderServiceImpl();
        Map dataP = os.countOrderByBranchInLastMonth();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Object branch : dataP.keySet()) {
            Float count = (float) dataP.get(branch);
            PieChart.Data data = new PieChart.Data(branch + ": " + count, count);
            data.nameProperty().addListener((obs, oldVal, newVal) -> data.setName(newVal + " (" + count + ")"));
            pieChartData.add(data);
        }
        pieChart.setData(pieChartData);
        pieChart.setLegendVisible(false);

        for (PieChart.Data data : pieChart.getData()) {
            Node slice = data.getNode();
            Text text = new Text(data.getPieValue() + "");
            slice.parentProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    Pane parent = (Pane) newVal;
                    parent.getChildren().add(text);
                }
            });
            slice.boundsInParentProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    double centerX = newVal.getMinX() + newVal.getWidth() / 2;
                    double centerY = newVal.getMinY() + newVal.getHeight() / 2;
                    text.relocate(centerX - text.getLayoutBounds().getWidth() / 2, centerY - text.getLayoutBounds().getHeight() / 2);
                }
            });
        }
        pieChart.setData(pieChartData);

        Map dataMap = os.countOrdersInOneMonth();
        XYChart.Series dataSeries = new XYChart.Series<>();

        dataMap.forEach((month, count) -> {
            XYChart.Data data = new XYChart.Data<>(month.toString(), count);
            dataSeries.getData().add(data);
        });

        barChart.getData().add(dataSeries);
    }
    @FXML
    private BarChart barChart;
    @FXML
    private PieChart pieChart;
}
