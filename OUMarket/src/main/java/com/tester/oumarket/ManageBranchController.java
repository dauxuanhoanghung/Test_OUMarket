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
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private TextField txtBranchPhone;
    @FXML
    private Button addButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        this.lblEmplCount.setText("Chưa có chi nhánh được chọn");
        loadTableColumn();
        loadContentToTableView(null);

        this.addButton.setOnAction(this::handleClickOnAddButton);
        this.cancelButton.setOnAction(this::handlerCancelButton);
        ChangeStatus.disable(cancelButton, txtBranchName, txtBranchLocation, txtBranchPhone);
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

        TableColumn phoneCol = new TableColumn("Điện thoại liên hệ");
        phoneCol.setCellValueFactory(new PropertyValueFactory("phone"));
        phoneCol.setPrefWidth(100);

        TableColumn updateCol = new TableColumn();
        updateCol.setCellFactory(clbck -> {
            Button btn = new Button();
            ChangeStatus.adjustButton(btn, "Update", "update");
            btn.setOnAction(this::handlerUpdateButton);
            TableCell tbc = new TableCell();
            tbc.setGraphic(btn);
            return tbc;
        });

        TableColumn manageProductCol = new TableColumn();
        manageProductCol.setCellFactory(clbck -> {
            Button btn = new Button();
            ChangeStatus.adjustButton(btn, "Quản lý", "confirm");
            btn.setOnAction(this::handlerChangeToManageProduct);
            TableCell tbc = new TableCell();
            tbc.setGraphic(btn);
            return tbc;
        });
        this.tbvBranch.getColumns().addAll(idCol, nameCol, locationCol,
                phoneCol, updateCol, manageProductCol);
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
     *
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
            } else {
                MessageBox.AlertBox("Error", "Something is error", Alert.AlertType.ERROR).show();
            }
            ChangeStatus.adjustButton(addButton, "Thêm", ".update");
            ChangeStatus.disable();
            ChangeStatus.enable();
            ChangeStatus.visible(lblEmplCount);
        } else { //Nút thêm
            ChangeStatus.enable(cancelButton, txtBranchName, txtBranchLocation);
            ChangeStatus.disable(getTableViewButtons(tbvBranch, "Quản lý"));
            ChangeStatus.adjustButton(addButton, "Confirm", ".update");
            ChangeStatus.invisible(lblEmplCount);
        }
        ChangeStatus.clearText(txtBranchName, txtBranchLocation, txtBranchId);

    }

    /**
     * Table View click
     *
     * @param event
     */
    public void handleMouseClickOnRow(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            BranchMarket selectedBranch = (BranchMarket) tbvBranch.getSelectionModel().getSelectedItem();
            if (selectedBranch != null) {
                showBranchDetails(selectedBranch);
            } else {
                ChangeStatus.clearText(txtBranchId, txtBranchLocation, txtBranchName, txtBranchPhone);
            }
            if (event.getClickCount() == 2) {
                TableCell cell = (TableCell) event.getPickResult().getIntersectedNode().getParent().getChildrenUnmodifiable().get(4);
                Button button = (Button) cell.getGraphic();
                ChangeStatus.adjustButton(button, "Confirm", "confirm");
            }
        }
    }

    /**
     * Hàm xử lý hủy các action add
     *
     * @param event
     */
    public void handlerCancelButton(ActionEvent event) {
        Alert alert = MessageBox.AlertBox("Cancel", "Hủy mọi thay đổi?", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                ChangeStatus.adjustButton(addButton, "Thêm", "update");
                ChangeStatus.disable(txtBranchId, txtBranchLocation, txtBranchName,
                        txtBranchPhone, cancelButton);
                ChangeStatus.enable(addButton);
                List<Button> btns = getTableViewButtons(tbvBranch, "Quản lý");
                ChangeStatus.enable(btns);
                btns.forEach(b -> ChangeStatus.adjustButton(b, "Update", "update"));
                ChangeStatus.clearText(txtBranchId, txtBranchLocation, txtBranchName, txtBranchPhone);
            }
        });
    }

    /**
     * Chuyển qua quản lý sản phẩm
     *
     * @param event
     */
    public void handlerChangeToManageProduct(ActionEvent event) {
        Button b = (Button) event.getSource();
        TableCell cell = (TableCell) b.getParent();
        BranchMarket branch = (BranchMarket) cell.getTableRow().getItem();
        
        ManageBranchProductController mbpController = new ManageBranchProductController();
        mbpController.setBranch(branch); 
        mbpController.setPrevious("ManageBranchMarket");
        try {
            App.setRoot("ManageBranchProduct");
        } catch (IOException ex) {
            Logger.getLogger(ManageBranchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Update Branch
     *
     * @param event
     */
    public void handlerUpdateButton(ActionEvent event) {
        Alert alert = MessageBox.AlertBox("Update", "Chỉnh sửa ?", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                Button b = (Button) event.getSource();
                TableCell cell = (TableCell) b.getParent();
                BranchMarket branch = (BranchMarket) cell.getTableRow().getItem();
                //Xác nhận update
                if (b.getText().equals("Confirm")) {
                    mapInputToBranchMarket(branch);
                    System.out.println(branch);
                    if (CheckUtils.isValidName(branch.getName()) == 1) {
                        //Update here
                        BranchMarketService bms = new BranchMarketServiceImpl();
                        if (bms.updateBranchMarket(branch) > 0) { //update thành công
                            //Xử lý tắt các button tron tbv, tắt nút thêm, bật nút hủy
                            ChangeStatus.adjustButton(b, "Update", "update");
                            ChangeStatus.toggleEnabledButton(cancelButton, addButton);
                            ChangeStatus.enable(getTableViewButtons(tbvBranch, ""));
                            ChangeStatus.disable();
                            loadContentToTableView(null);
                        } else {
                            MessageBox.AlertBox("Error", "Something is error!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                } else {
                    //Bắt đầu input để update
                    ChangeStatus.adjustButton(b, "Confirm", "confirm");
                    ChangeStatus.toggleEnabledButton(cancelButton, addButton);
                    ChangeStatus.disable(getTableViewButtons(tbvBranch, "Quản lý"));
                    ChangeStatus.disable(addButton);
                    ChangeStatus.enable(cancelButton, txtBranchLocation, txtBranchName, txtBranchPhone);
                    b.setDisable(false);
                    showBranchDetails(branch);
                }
            }
        });
    }

    /**
     * đưa dữ liệu -> textfield
     *
     * @param branchMarket
     */
    private void showBranchDetails(BranchMarket branchMarket) {
        this.txtBranchId.setText(String.valueOf(branchMarket.getId()));
        this.txtBranchName.setText(branchMarket.getName());
        this.txtBranchLocation.setText(branchMarket.getLocation());
        this.txtBranchPhone.setText(branchMarket.getPhone());
        BranchMarketService bms = new BranchMarketServiceImpl();
        this.lblEmplCount.setText(bms.countEmployeesByBranchId(branchMarket) + "");
    }

    /**
     * map input -> object
     *
     * @param branchMarket
     */
    private BranchMarket mapInputToBranchMarket(BranchMarket branchMarket) {
        branchMarket.setName(txtBranchName.getText());
        branchMarket.setLocation(txtBranchLocation.getText());
        branchMarket.setPhone(txtBranchPhone.getText());
        return branchMarket;
    }

}
