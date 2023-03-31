/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.pojo.BranchMarket;
import com.tester.service.BranchMarketService;
import com.tester.service.impl.BranchMarketServiceImpl;
import com.tester.utils.MessageBox;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author LENOVO
 */
public class ManageBranchController extends AbstractManageController {

    @FXML
    private TableView tbvBranch;
    @FXML private TextField txtBranchId;
    @FXML private TextField txtBranchName;
    @FXML private TextField txtBranchLocation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        loadTableColumn();
        loadContentToTableView(null);
    }

    private void loadTableColumn() {
        TableColumn idCol = new TableColumn("Mã chi nhánh");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        idCol.setPrefWidth(300);

        TableColumn nameCol = new TableColumn("Tên chi nhánh");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        nameCol.setPrefWidth(300);

        TableColumn locationCol = new TableColumn("Địa chỉ chi nhánh");
        locationCol.setCellValueFactory(new PropertyValueFactory("location"));
        locationCol.setPrefWidth(300);

        TableColumn updateCol = new TableColumn();
        updateCol.setCellFactory(clbck -> {
            Button btn = new Button("Update");
            btn.getStyleClass().add("update");
            btn.setOnAction(event -> {
                Alert alert = MessageBox.AlertBox("Update", "Chỉnh sửa ?", Alert.AlertType.CONFIRMATION);
                alert.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {

                    }
                });
            });
            TableCell tbc = new TableCell();
            tbc.setGraphic(btn);
            return tbc;
        });
        this.tbvBranch.getColumns().addAll(idCol, nameCol, locationCol, updateCol);
    }

    private void loadContentToTableView(String kw) {
        if (kw != null && !kw.isBlank()) {

        }
        BranchMarketService service = new BranchMarketServiceImpl();
        List<BranchMarket> branches = service.getBranchMarkets();
        this.tbvBranch.getItems().clear();
        this.tbvBranch.setItems(FXCollections.observableList(branches));
    }
}
