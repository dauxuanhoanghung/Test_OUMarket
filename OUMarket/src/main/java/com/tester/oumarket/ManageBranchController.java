/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.pojo.BranchMarket;
import com.tester.service.BranchMarketService;
import com.tester.service.impl.BranchMarketServiceImpl;
import com.tester.utils.ChangeStatus;
import com.tester.utils.CheckUtils;
import com.tester.utils.MessageBox;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author LENOVO
 */
public class ManageBranchController extends AbstractManageController {
    @FXML
    private Button cancelButton;
    @FXML
    private Label lblEmplCount;
    @FXML
    private TableView tbvBranch;
    @FXML
    private TextField txtBranchId;
    @FXML
    private TextField txtBranchName;
    @FXML
    private TextField txtBranchLocation;
    @FXML
    private Button addButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        this.lblEmplCount.setText("Chưa có chi nhánh được chọn");
        loadTableColumn();
        loadContentToTableView(null);
        this.addButton.setOnAction(this::handleClickOnAddButton);
        
        ChangeStatus.disable(cancelButton, txtBranchName, txtBranchLocation);
    }

    private void loadTableColumn() {
        TableColumn idCol = new TableColumn("Mã chi nhánh");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        idCol.setPrefWidth(100);

        TableColumn nameCol = new TableColumn("Tên chi nhánh");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        nameCol.setPrefWidth(100);

        TableColumn locationCol = new TableColumn("Địa chỉ chi nhánh");
        locationCol.setCellValueFactory(new PropertyValueFactory("location"));
        locationCol.setPrefWidth(300);

        TableColumn updateCol = new TableColumn();
        updateCol.setCellFactory(clbck -> {
            Button btn = new Button();
            ChangeStatus.adjustButton(btn, "Update", "update");
            btn.setOnAction(event -> {
                String msg = "Bạn có chắc sẽ chỉnh sửa ?";
                Alert alert = MessageBox.AlertBox("Update", msg, Alert.AlertType.CONFIRMATION);
                alert.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button) event.getSource();
                        if (b.getText().equals("Confirm")) {
                            //Update here
                            ChangeStatus.adjustButton(b, "Update", "");
                            
                        } else {
                            //Bắt đầu input để update
                            ChangeStatus.adjustButton(b, "Confirm", "confirm");
                            TableCell cell = (TableCell) b.getParent();
                            BranchMarket branchMarket = (BranchMarket) cell.getTableRow().getItem();
                            showBranchDetails(branchMarket);
                            this.txtBranchName.requestFocus();
                        }
                    }
                });
            });
            TableCell tbc = new TableCell();
            tbc.setGraphic(btn);
            return tbc;
        });
        TableColumn manageProductCol = new TableColumn();
        manageProductCol.setCellFactory(clbck -> {
            Button btn = new Button();
            ChangeStatus.adjustButton(btn, "Quản lý", "confirm");
            TableCell tbc = new TableCell();
            tbc.setGraphic(btn);
            return tbc;
        });
        this.tbvBranch.getColumns().addAll(idCol, nameCol, locationCol, 
                updateCol, manageProductCol);
    }

    private void loadContentToTableView(String kw) {
        if (kw != null && !kw.isBlank()) {

        }
        BranchMarketService service = new BranchMarketServiceImpl();
        List<BranchMarket> branches = service.getBranchMarkets();
        this.tbvBranch.getItems().clear();
        this.tbvBranch.setItems(FXCollections.observableList(branches));
    }
    
    /**
     * Bấm nút thêm
     * @param event 
     */
    public void handleClickOnAddButton(ActionEvent event) {
        if (addButton.getText().equals("Confirm")) { //Nút xác nhận
            BranchMarket bm = mapInputToBranchMarket(new BranchMarket());
            if (CheckUtils.isValidName(bm.getName()) == 1) {
                System.out.println(bm);
                BranchMarketService bms = new BranchMarketServiceImpl();
                bms.addBranchMarket(bm);
                MessageBox.AlertBox("Add successful", "Add successful", Alert.AlertType.INFORMATION).show();
                loadContentToTableView(null);
            } else 
                MessageBox.AlertBox("Error", "Something is error", Alert.AlertType.ERROR).show();
            ChangeStatus.adjustButton(addButton, "Thêm", ".update");
            ChangeStatus.disable();
            ChangeStatus.enable();
        }
        else { //Nút thêm
            ChangeStatus.enable(cancelButton, txtBranchName, txtBranchLocation);
            ChangeStatus.disable();
            ChangeStatus.adjustButton(addButton, "Confirm", ".update");
        }
        ChangeStatus.clearText(txtBranchName, txtBranchLocation); 
    }
    
    /**
     * Table View click
     * @param event 
     */
    public void handleMouseClickOnRow(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            BranchMarket selectedBranch = (BranchMarket) tbvBranch.getSelectionModel().getSelectedItem();
            if (selectedBranch != null) {
                showBranchDetails(selectedBranch);
            }
            if (event.getClickCount() == 2) {
                TableCell cell = (TableCell) event.getPickResult().getIntersectedNode().getParent().getChildrenUnmodifiable().get(3);
                Button button = (Button) cell.getGraphic();
                ChangeStatus.adjustButton(button, "Confirm", "confirm");
            }
        }
    }
    
    /**
     * Hàm xử lý hủy các action add
     * @param event 
     */
    public void handlerCancelButton(ActionEvent event) {
        Alert alert = MessageBox.AlertBox("Cancel", "Hủy mọi thay đổi?", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                ChangeStatus.disable(cancelButton);
            }
        });
    }
    
    public void handlerChangeToManageProduct(ActionEvent event) {
        Button b = (Button) event.getSource();
        
    } 
    
    private void showBranchDetails(BranchMarket branchMarket) {
        this.txtBranchId.setText(String.valueOf(branchMarket.getId()));
        this.txtBranchName.setText(branchMarket.getName());
        this.txtBranchLocation.setText(branchMarket.getLocation());
        BranchMarketService bms = new BranchMarketServiceImpl();
        this.lblEmplCount.setText(bms.countEmployeesByBranchId(branchMarket) + "");
    }
    
    private BranchMarket mapInputToBranchMarket(BranchMarket branchMarket) {
        branchMarket.setName(txtBranchName.getText());
        branchMarket.setLocation(txtBranchLocation.getText());
        return branchMarket;
    }
}
