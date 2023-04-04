/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.pojo.BranchMarket;
import com.tester.pojo.Employee;
import com.tester.service.BranchMarketService;
import com.tester.service.EmployeeService;
import com.tester.service.impl.BranchMarketServiceImpl;
import com.tester.service.impl.EmployeeServiceImpl;
import com.tester.utils.ChangeStatus;
import com.tester.utils.CheckUtils;
import com.tester.utils.MessageBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
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
public class ManageEmployeeController extends AbstractManageController {

    @FXML
    private TableView tbvEmp;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtPhone;
    @FXML
    private ComboBox cbbRole;
    @FXML
    private ComboBox cbbBranch;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        //handle on role
        cbbRole.getItems().addAll(Employee.ADMIN, Employee.EMPLOYEE);
        this.cbbRole.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.equals(Employee.EMPLOYEE)) {
                this.cbbBranch.setPromptText("Lựa chọn chi nhánh");
                this.cbbBranch.getSelectionModel().clearSelection();
                this.cbbBranch.setDisable(true);
            } else {
                this.cbbBranch.setDisable(false);
            }
        });

        //handle on branch combobox
        BranchMarketService bms = new BranchMarketServiceImpl();
        List<BranchMarket> branches = bms.getBranchMarkets();
        this.cbbBranch.setItems(FXCollections.observableArrayList(branches));
        this.cbbBranch.setDisable(true);

        cancelButton.setDisable(true);

        loadTableColumn();
        loadContentToTableView();
        tbvEmp.setOnMouseClicked(this::handlerClickOnTableView);
    }

    /**
     * Load cấu trúc table
     */
    private void loadTableColumn() {
        TableColumn<Employee, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employee, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Employee, Date> joinDateCol = new TableColumn<>("Join Date");
        joinDateCol.setCellValueFactory(new PropertyValueFactory<>("joinDate"));

        TableColumn<Employee, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Employee, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<Employee, Integer> branchIdCol = new TableColumn<>("Branch ID");
        branchIdCol.setCellValueFactory(new PropertyValueFactory<>("branchId"));

        TableColumn updateCol = new TableColumn();
        updateCol.setCellFactory(clbck -> {
            Button btn = new Button("Update");
            btn.getStyleClass().add("update");
            btn.setOnAction(this::handlerUpdateButton);
            TableCell tbc = new TableCell();
            tbc.setGraphic(btn);
            return tbc;
        });
        this.tbvEmp.getColumns().addAll(idCol, nameCol, usernameCol, joinDateCol,
                phoneCol, roleCol, branchIdCol, updateCol);
    }

    /**
     * Load Nội dung
     */
    private void loadContentToTableView() {
        EmployeeService s = new EmployeeServiceImpl();
        List<Employee> employees = s.getEmployees(null);
        this.tbvEmp.getItems().clear();
        this.tbvEmp.setItems(FXCollections.observableList(employees));
    }

    /**
     * Xử lý Update
     *
     * @param event
     */
    public void handlerUpdateButton(ActionEvent event) {
        Alert alert = MessageBox.AlertBox("Update", "Chỉnh sửa ?", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                Button b = (Button) event.getSource();
                TableCell cell = (TableCell) b.getParent();
                Employee employee = (Employee) cell.getTableRow().getItem();
                if (b.getText().equals("Confirm")) {
                    mapInputToEmployee(employee);
                    System.out.println(employee);
                    if (CheckUtils.isValidName(employee.getName()) == 1
                            && CheckUtils.isValidPassword(employee.getPassword()) == 1
                            && CheckUtils.isValidPhoneNumber(employee.getPhone()) == 1) {
                        //Update here
                        EmployeeService es = new EmployeeServiceImpl();
                        if (es.updateEmployee(employee) > 0) { //update thành công
                            //Xử lý tắt các button tron tbv, tắt nút thêm, bật nút hủy
                            ChangeStatus.adjustButton(b, "Update", "");
                            ChangeStatus.toggleEnabledButton(cancelButton, addButton);
                            ChangeStatus.enableButton(getTableViewButtons());
                            loadContentToTableView();
                        } else {
                            MessageBox.AlertBox("Error", "Something is error!!!", Alert.AlertType.ERROR).show();
                        }
                    }
                } else {
                    //Bắt đầu input để update
                    ChangeStatus.adjustButton(b, "Confirm", "confirm");
                    ChangeStatus.toggleEnabledButton(cancelButton, addButton);
                    ChangeStatus.disabledButton(getTableViewButtons());
                    ChangeStatus.disabledButton(addButton);
                    ChangeStatus.enableTextField(txtName, txtPassword, txtPhone, txtUsername);
                    b.setDisable(false);
                    showEmployeeDetail(employee);
                }
            }
        });
    }

    /**
     * Xử lý khi click vào tableview
     *
     * @param event
     */
    public void handlerClickOnTableView(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Employee selectedEmployee = (Employee) tbvEmp.getSelectionModel().getSelectedItem();
            if (selectedEmployee == null) return;
            showEmployeeDetail(selectedEmployee);
            ChangeStatus.disableTextField(txtName, txtPassword, txtPhone, txtUsername);
            if (event.getClickCount() == 2) {
                TableCell cell = (TableCell) event.getPickResult().getIntersectedNode().getParent().getChildrenUnmodifiable().get(7);
                Button b = (Button) cell.getGraphic();
                ChangeStatus.adjustButton(b, "Confirm", "confirm");
                ChangeStatus.toggleEnabledButton(cancelButton, addButton);
                ChangeStatus.disabledButton(getTableViewButtons());
                ChangeStatus.disabledButton(addButton);
                ChangeStatus.enableTextField(txtName, txtPassword, txtPhone, txtUsername);
                b.setDisable(false);
            }

        }
    }

    /**
     * Xử lý Thêm
     *
     * @param event
     */
    public void handlerAddNewEmployee(ActionEvent event) {
        Employee emp = mapInputToEmployee(new Employee());
        if (CheckUtils.isValidName(emp.getName()) == 1 && CheckUtils.isValidPassword(emp.getPassword()) == 1
                && CheckUtils.isValidPhoneNumber(emp.getPhone()) == 1) {
            System.out.println(emp);
            EmployeeService es = new EmployeeServiceImpl();
            es.addEmployee(emp);
            MessageBox.AlertBox("Add successful", "Add successful", Alert.AlertType.INFORMATION).show();
        } else {
            MessageBox.AlertBox("Error", "Something is error", Alert.AlertType.ERROR).show();
        }
    }

    /**
     * Hàm load Employee được chọn -> textfield, combobox
     *
     * @param emp
     */
    private void showEmployeeDetail(Employee emp) {
        this.txtName.setText(emp.getName());
        this.txtUsername.setText(emp.getUsername());
        this.txtPassword.setText(emp.getPassword());
        this.txtPhone.setText(emp.getPhone());
        this.cbbRole.setValue(emp.getRole());
        if (!emp.getRole().equals(Employee.ADMIN)) {
            for (Object branch : cbbBranch.getItems()) {
                if (((BranchMarket) branch).getId() == emp.getBranchId()) {
                    cbbBranch.setValue(branch);
                    break;
                }
            }
        } else {
            this.cbbBranch.getSelectionModel().clearSelection();
            this.cbbBranch.setPromptText("Lựa chọn chi nhánh");
        }

    }

    /**
     * Hàm lấy dữ liệu từ textfield -> Object, truyền mới khi add
     *
     * @param employee
     */
    private Employee mapInputToEmployee(Employee employee) {
        employee.setName(this.txtName.getText());
        employee.setUsername(this.txtUsername.getText());
        employee.setPassword(this.txtPassword.getText());
        employee.setPhone(this.txtPhone.getText());
        employee.setRole((String) this.cbbRole.getValue());
        BranchMarket branch = (BranchMarket) this.cbbBranch.getSelectionModel().getSelectedItem();
        employee.setBranchId(branch != null ? branch.getId() : 0);
        return employee;
    }

    /**
     * Lấy cái button của bảng để disable hoặc enable
     *
     * @return
     */
    private List<Button> getTableViewButtons() {
        List<Button> buttons = new ArrayList<>();
        tbvEmp.lookupAll("Button").forEach(node -> {
            if (node instanceof Button) {
                buttons.add((Button) node);
            }
        });
        return buttons;
    }
}
