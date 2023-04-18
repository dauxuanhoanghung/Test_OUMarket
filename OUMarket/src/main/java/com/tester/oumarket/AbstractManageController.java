/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.constant.UIConstant;
import com.tester.utils.ExcelIgnore;
import com.tester.utils.MessageBox;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author LENOVO
 */
public abstract class AbstractManageController implements Initializable {

    @FXML
    protected Label lblNameAdmin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.lblNameAdmin.setText("Welcome " + App.getCurrentEmployee().getName());
    }

    public void returnMenuService() throws IOException {
        App.setRoot("ManageServicePage");
    }

    public void logout(ActionEvent event) {
        Alert alert = MessageBox.AlertBox("LOGOUT", "Are you sure to exit this session?", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                App.setCurrentEmployee(null);
                try {
                    App.setSceneSize(UIConstant.LOGIN_WIDTH, UIConstant.LOGIN_HEIGHT);
                    App.setRoot("Login");
                } catch (IOException ex) {
                    Logger.getLogger(AbstractManageController.class.getName()).log(Level.SEVERE,
                            "There's something error in ManageServiceController", ex);
                }
            }
        });
    }

    static List<Button> getTableViewButtons(TableView tbv, String... excludeText) {
        List<Button> buttons = new ArrayList<>();
        tbv.lookupAll("Button").forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                boolean shouldExclude = false;
                for (String text : excludeText) {
                    if (button.getText().equals(text)) {
                        shouldExclude = true;
                        break;
                    }
                }
                if (!shouldExclude) {
                    buttons.add(button);
                }
            }
        });
        return buttons;
    }

    static List<Button> getTableViewButtons(TableView tbv) {
        return getTableViewButtons(tbv, "");
    }

    public void exportToExcel(TableView table, String filePath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = (XSSFSheet) workbook.createSheet("Sheet 1");

            ObservableList<TableColumn> columns = table.getColumns();
            int rowIndex = 0;
            Row row = sheet.createRow(rowIndex++);

            // Write column headers
            for (int i = 0; i < columns.size(); i++) {
                TableColumn column = columns.get(i);
                Cell cell = row.createCell(i);
                cell.setCellValue(column.getText());
            }
            // Write data rows
            ObservableList<ObservableList> rows = table.getItems();
            for (int i = 0; i < rows.size(); i++) {
                ObservableList rowValues = rows.get(i);
                row = sheet.createRow(rowIndex++);
                for (int j = 0; j < rowValues.size(); j++) {
                    Object value = rowValues.get(j);
                    Cell cell = row.createCell(j);
                    if (value instanceof String) {
                        cell.setCellValue((String) value);
                    } else if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                    }
                }
            }

            // Resize columns
            for (int i = 0; i < columns.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Save the workbook to a file
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
        }
    }

    public void exportToExcel(List<?> list, String filePath) throws IOException, IllegalAccessException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Sheet1");
            // Create header row
            Row headerRow = sheet.createRow(0);
            List<Field> fields = new ArrayList<>(Arrays.asList(list.get(0).getClass().getDeclaredFields()));
            for (int i = 0; i < fields.size(); ++i) {
                if (fields.get(i).isAnnotationPresent(ExcelIgnore.class)) {
                    fields.remove(i);
                }
            }
            int columnIndex = 0;
            for (Field field : fields) {

                field.setAccessible(true);
                Cell cell = headerRow.createCell(columnIndex++);
                cell.setCellValue(field.getName());
            }   // Write data rows
            int rowIndex = 1;
            for (Object obj : list) {
                Row dataRow = sheet.createRow(rowIndex++);
                columnIndex = 0;
                for (Field field : fields) {
                    field.setAccessible(true);
                    Cell cell = dataRow.createCell(columnIndex++);
                    Object value = field.get(obj);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }   // Write to file
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            outputStream.close();
        }
    }
}
