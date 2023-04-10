/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import static com.tester.oumarket.AbstractManageController.getTableViewButtons;
import com.tester.pojo.BranchMarket;
import com.tester.pojo.Category;
import com.tester.pojo.Employee;
import com.tester.pojo.Product;
import com.tester.pojo.Unit;
import com.tester.service.CategoryService;
import com.tester.service.EmployeeService;
import com.tester.service.ProductService;
import com.tester.service.UnitService;
import com.tester.service.impl.CategoryServiceImpl;
import com.tester.service.impl.EmployeeServiceImpl;
import com.tester.service.impl.ProductServiceImpl;
import com.tester.service.impl.UnitServiceImpl;
import com.tester.utils.ChangeStatus;
import com.tester.utils.CheckUtils;
import com.tester.utils.MessageBox;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author LENOVO
 */
public class ManageProductController extends AbstractManageController {

    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox cbbCategory;
    @FXML
    private ComboBox cbbUnit;
    @FXML
    private TextField txtOrigin;
    @FXML
    private TextArea txtProductDescription;
    @FXML
    private TextField txtProductID;
    @FXML
    private TextField txtProductName;
    @FXML
    private TextField txtProductPrice;
    @FXML
    private TableView tbvProduct;

    private CategoryService cs;
    private UnitService us;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        loadTableColumn();
        loadContentToTableView(null);
        addButton.setOnAction(this::handlerAddNewEmployee);
        tbvProduct.setOnMouseClicked(this::handlerClickOnTableView);

        cs = new CategoryServiceImpl();
        List<Category> cates = cs.getCategories();
        cbbCategory.getItems().addAll(cates);
        us = new UnitServiceImpl();
        List<Unit> units = us.getUnits();
        cbbUnit.getItems().addAll(units);

        ChangeStatus.disable(txtProductID, cancelButton, txtOrigin,
                txtProductDescription, txtProductName, txtProductPrice,
                cbbCategory, cbbUnit);
        ChangeStatus.enable(addButton);
    }

    private void loadTableColumn() {
        UnitService us = new UnitServiceImpl();
        TableColumn<Product, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Product, Float> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, String> originCol = new TableColumn<>("Origin");
        originCol.setCellValueFactory(new PropertyValueFactory<>("origin"));

        TableColumn<Product, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory((TableColumn.CellDataFeatures<Product, String> cellData) -> {
            Integer categoryId = cellData.getValue().getCategoryId();
            CategoryService cs = new CategoryServiceImpl();
            Category category = cs.getCategoryById(categoryId);
            if (category == null) {
                return new SimpleStringProperty("");
            } else {
                return new SimpleStringProperty(category.getName());
            }
        });

        TableColumn<Product, String> unitCol = new TableColumn<>("Unit");
        unitCol.setCellValueFactory((TableColumn.CellDataFeatures<Product, String> cellData) -> {
            Integer unitId = cellData.getValue().getUnitId();
            Unit unit = us.getUnitById(unitId);
            if (unit == null) {
                return new SimpleStringProperty("");
            } else {
                return new SimpleStringProperty(unit.getName());
            }
        });
        this.tbvProduct.getColumns().addAll(idCol, nameCol, descriptionCol,
                priceCol, originCol, unitCol, categoryCol);
    }

    private void loadContentToTableView(String kw) {
        if (kw != null && !kw.isBlank()) {

        }
        ProductService ps = new ProductServiceImpl();
        List<Product> products = ps.getProducts(kw);
        this.tbvProduct.getItems().clear();
        this.tbvProduct.setItems(FXCollections.observableList(products));
    }

    public void handlerAddNewEmployee(ActionEvent event) {
        if (addButton.getText().equals("Confirm")) { //Nút xác nhận
            Product product = mapInputToProduct(new Product());
            if (CheckUtils.isValidName(product.getName()) == 1) {
                ProductService ps = new ProductServiceImpl();
                ps.addProduct(product);
                MessageBox.AlertBox("Add successful", "Add successful", Alert.AlertType.INFORMATION).show();
                loadContentToTableView("");
            } else {
                MessageBox.AlertBox("Error", "Something is error", Alert.AlertType.ERROR).show();
            }
            ChangeStatus.adjustButton(addButton, "Thêm", "update");
            ChangeStatus.disable(cancelButton, txtOrigin,
                    txtProductDescription, txtProductName, txtProductPrice);
            ChangeStatus.enable(getTableViewButtons(tbvProduct));
        } else { //Nút thêm
            ChangeStatus.enable(cancelButton, txtOrigin,
                    txtProductDescription, txtProductName, txtProductPrice,
                    cbbCategory, cbbUnit);
            ChangeStatus.disable(getTableViewButtons(tbvProduct));
            ChangeStatus.adjustButton(addButton, "Confirm", "update");
        }
        ChangeStatus.clearText(txtProductID, txtOrigin, txtProductDescription,
                txtProductName, txtProductPrice);
    }

    /**
     * Hàm xử lý hủy các action add - update
     *
     * @param event
     */
    public void handlerCancelButton(ActionEvent event) {
        Alert alert = MessageBox.AlertBox("Cancel", "Hủy mọi thay đổi?", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                ChangeStatus.disable(txtOrigin, txtProductName, txtProductPrice,
                        txtProductDescription, cbbCategory, cbbUnit, cancelButton);
                ChangeStatus.enable(addButton);
                List<Button> btns = getTableViewButtons(tbvProduct, "Delete", "Restore");
                btns.forEach(b -> ChangeStatus.adjustButton(b, "Update", "update"));
                ChangeStatus.enable(getTableViewButtons(tbvProduct));
                ChangeStatus.adjustButton(addButton, "Thêm", "update");
            }
        });
    }

    /**
     * Xử lý khi click vào tableview Double Click hiện đang còn lỗi
     *
     * @param event
     */
    public void handlerClickOnTableView(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Product selectedproduct = (Product) tbvProduct.getSelectionModel().getSelectedItem();
            if (selectedproduct == null) {
                return;
            }
            showProductDetail(selectedproduct);
            ChangeStatus.disable(txtProductID, cancelButton, txtOrigin,
                    txtProductDescription, txtProductName, txtProductPrice,
                    cbbCategory, cbbUnit);
//            if (event.getClickCount() == 2) {
//                TableCell cell = (TableCell) event.getPickResult().getIntersectedNode()
//                        .getParent().getChildrenUnmodifiable().get(tbvEmp.getColumns().size() - 1);
//                Button b = (Button) cell.getGraphic();
//                ChangeStatus.adjustButton(b, "Confirm", "confirm");
//                ChangeStatus.toggleEnabledButton(cancelButton, addButton);
//                ChangeStatus.disable(getTableViewButtons());
//                ChangeStatus.disable(addButton);
//                ChangeStatus.enable(txtName, txtPassword, txtPhone, txtUsername);
//                b.setDisable(false);
//            }
        }
    }

    private Product mapInputToProduct(Product product) {
        product.setName(txtProductName.getText());
        product.setOrigin(txtOrigin.getText());
        product.setDescription(txtProductDescription.getText());
        product.setPrice(Float.parseFloat(txtProductPrice.getText()));

        Category cate = (Category) cbbCategory.getSelectionModel().getSelectedItem();
        product.setCategoryId(cate.getId());
        Unit unit = (Unit) cbbUnit.getSelectionModel().getSelectedItem();
        product.setUnitId(unit.getId());
        return product;
    }

    /**
     * Hàm load Product được chọn -> textfield, combobox
     *
     * @param emp
     */
    private void showProductDetail(Product product) {
        this.txtProductID.setText(product.getId());
        this.txtProductName.setText(product.getName());
        this.txtProductDescription.setText(product.getDescription());
        this.txtOrigin.setText(product.getOrigin());
        this.txtProductPrice.setText(String.valueOf(product.getPrice()));
        for (Object cate : cbbCategory.getItems()) {
            if (((Category) cate).getId() == product.getCategoryId()) {
                cbbCategory.setValue(cate);
                break;
            }
        }
        for (Object unit : cbbUnit.getItems()) {
            if (((Unit) unit).getId() == product.getUnitId()) {
                cbbUnit.setValue(unit);
                break;
            }
        }
        
    }

}
