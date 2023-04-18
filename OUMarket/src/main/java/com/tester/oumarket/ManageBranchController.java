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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

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
    private Label lblLocationMessage;
    @FXML
    private Label lblPhoneMessage;
    @FXML
    private Label lblNameMesssage;
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
        setHandling();
        ChangeStatus.invisible(lblLocationMessage, lblNameMesssage, lblPhoneMessage);
        ChangeStatus.disable(cancelButton, txtBranchName, txtBranchLocation, txtBranchPhone);
    }

    public void setHandling() {
        this.addButton.setOnAction(this::handleAddButton);
        this.cancelButton.setOnAction(this::handlerCancelButton);
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
            TableCell tbc = new TableCell() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
            return tbc;
        });

        TableColumn manageProductCol = new TableColumn();
        manageProductCol.setCellFactory(clbck -> {
            Button btn = new Button();
            ChangeStatus.adjustButton(btn, "Quản lý", "confirm");
            btn.setOnAction(this::handlerChangeToManageProduct);
            TableCell tbc = new TableCell() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
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
    public void handleAddButton(ActionEvent event) {
        if (addButton.getText().equals("Confirm")) { //Nút xác nhận
            BranchMarket bm = mapInputToBranchMarket(new BranchMarket());
            if (CheckUtils.isNotNullAndBlankText(bm.getLocation(), bm.getName())
                    && CheckUtils.isValidPhoneNumber(bm.getPhone()) == 1) {
                BranchMarketService bms = new BranchMarketServiceImpl();
                if (bms.addBranchMarket(bm) > 0) {
                    MessageBox.AlertBox("Add successful", "Add successful", Alert.AlertType.INFORMATION).show();
                    loadContentToTableView(null);
                    ChangeStatus.adjustButton(addButton, "Thêm", ".update");
                    ChangeStatus.disable();
                    ChangeStatus.enable();
                    ChangeStatus.visible(lblEmplCount);
                    ChangeStatus.invisible(lblLocationMessage, lblPhoneMessage, lblNameMesssage);
                    this.tbvBranch.setOnMouseClicked(this::handleMouseClickOnRow);
                    ChangeStatus.clearText(txtBranchName, txtBranchLocation, txtBranchId, txtBranchPhone);
                } else {
                    MessageBox.AlertBox("Error", "Something is error", Alert.AlertType.ERROR).show();
                }
            } else {
                handleError(bm);
                MessageBox.AlertBox("Error", "Something is error", Alert.AlertType.ERROR).show();
            }
        } else { //Nút thêm
            ChangeStatus.enable(cancelButton, txtBranchName, txtBranchLocation, txtBranchPhone);
            ChangeStatus.disable(getTableViewButtons(tbvBranch, "Quản lý"));
            ChangeStatus.adjustButton(addButton, "Confirm", ".update");
            ChangeStatus.invisible(lblEmplCount);
            this.tbvBranch.setOnMouseClicked(evt -> {
            });
            ChangeStatus.clearText(txtBranchName, txtBranchLocation, txtBranchId, txtBranchPhone);
        }

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
                ChangeStatus.visible(lblEmplCount);
                ChangeStatus.invisible(lblLocationMessage, lblNameMesssage, lblPhoneMessage);
                this.tbvBranch.setOnMouseClicked(this::handleMouseClickOnRow);
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
        if (branch == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageProductBranch.fxml"));
            Parent root = loader.load();
            ManageBranchProductController mbpController = loader.getController();
            mbpController.setBranch(branch);
            mbpController.setPrevious("ManageBranchMarket");

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            App.setScene(scene);
            mbpController.updateUI();
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
        Button b = (Button) event.getSource();
        TableCell cell = (TableCell) b.getParent();
        BranchMarket branch = (BranchMarket) cell.getTableRow().getItem();
        if (branch == null) {
            return;
        }
        Alert alert = MessageBox.AlertBox("Update", "Chỉnh sửa ?", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                //Xác nhận update
                if (b.getText().equals("Confirm")) {
                    mapInputToBranchMarket(branch);
                    if (CheckUtils.isNotNullAndBlankText(branch.getLocation(), branch.getName())
                            && CheckUtils.isValidPhoneNumber(branch.getPhone()) == 1) {
                        //Update here
                        BranchMarketService bms = new BranchMarketServiceImpl();
                        if (bms.updateBranchMarket(branch) > 0) { //update thành công
                            //Xử lý tắt các button tron tbv, tắt nút thêm, bật nút hủy
                            ChangeStatus.adjustButton(b, "Update", "update");
                            ChangeStatus.toggleEnabledButton(cancelButton, addButton);
                            ChangeStatus.enable(getTableViewButtons(tbvBranch, ""));
                            ChangeStatus.disable(cancelButton, txtBranchName, txtBranchLocation, txtBranchPhone);
                            loadContentToTableView(null);
                            this.tbvBranch.setOnMouseClicked(this::handleMouseClickOnRow);
                        } else {
                            MessageBox.AlertBox("Error", "Something is error!!!", Alert.AlertType.ERROR).show();
                        }
                    } else {
                        handleError(branch);
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
                    this.tbvBranch.setOnMouseClicked(evt -> {
                    });
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

    private void handleError(BranchMarket bm) {
        boolean locationCheck = CheckUtils.isNotNullAndBlankText(bm.getLocation());
        boolean nameCheck = CheckUtils.isNotNullAndBlankText(bm.getName());
        int phoneCheck = CheckUtils.isValidPhoneNumber(bm.getPhone());

        switch (phoneCheck) {
            case 0:
                lblPhoneMessage.setText("Số điện thoại đang không được nhập");
                ChangeStatus.visible(lblPhoneMessage);
                break;
            case -1:
                lblPhoneMessage.setText("Số điện thoại phải đủ 10 ký tự");
                ChangeStatus.visible(lblPhoneMessage);
                break;
            case -2:
                lblPhoneMessage.setText("Số điện thoại chỉ có thể là số");
                ChangeStatus.visible(lblPhoneMessage);
                break;
            case -3:
                lblPhoneMessage.setText("Số điện thoại phải bắt đầu bằng số 0");
                ChangeStatus.visible(lblPhoneMessage);
                break;
        }

        if (!nameCheck) {
            lblNameMesssage.setText("Địa chỉ đang không được nhập");
            ChangeStatus.visible(lblNameMesssage);
        }
        if (!locationCheck) {
            lblLocationMessage.setText("Địa chỉ đang không được nhập");
            ChangeStatus.visible(lblLocationMessage);
        }
    }
}
