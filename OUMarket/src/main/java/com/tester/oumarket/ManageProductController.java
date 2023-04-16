/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import static com.tester.oumarket.AbstractManageController.getTableViewButtons;
import com.tester.pojo.Category;
import com.tester.pojo.Product;
import com.tester.pojo.Unit;
import com.tester.service.CategoryService;
import com.tester.service.ProductService;
import com.tester.service.UnitService;
import com.tester.service.impl.CategoryServiceImpl;
import com.tester.service.impl.ProductServiceImpl;
import com.tester.service.impl.UnitServiceImpl;
import com.tester.utils.ChangeStatus;
import com.tester.utils.CheckUtils;
import com.tester.utils.MessageBox;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

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
    private Button exportButton;
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
    @FXML
    private Label lbProductNameFalse;
    @FXML
    private Label lbPriceFalse;
    private CategoryService cs;
    private UnitService us;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        loadTableColumn();
        loadContentToTableView(null);

        setHandling();

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

    private void setHandling() {
        addButton.setOnAction(this::handlerAddNewProduct);
        cancelButton.setOnAction(this::handlerCancelButton);
        exportButton.setOnAction(this::handlerExportBtn);
        tbvProduct.setOnMouseClicked(this::handlerClickOnTableView);
    }

    private void loadTableColumn() {
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
//            Integer categoryId = cellData.getValue().getCategoryId();
//            Category category = cs.getCategoryById(categoryId);
//            if (category == null) {
//                return new SimpleStringProperty("");
//            } else {
//                return new SimpleStringProperty(category.getName());
//            }
            return new SimpleStringProperty(cellData.getValue().getCategory().getName());
        });

        TableColumn<Product, String> unitCol = new TableColumn<>("Unit");
        unitCol.setCellValueFactory((TableColumn.CellDataFeatures<Product, String> cellData) -> {
//            Integer unitId = cellData.getValue().getUnitId();
//            Unit unit = us.getUnitById(unitId);
//            if (unit == null) {
//                return new SimpleStringProperty("");
//            } else {
//                return new SimpleStringProperty(unit.getName());
//            }
            return new SimpleStringProperty(cellData.getValue().getUnit().getName());
        });

        TableColumn updateCol = new TableColumn();
        updateCol.setCellFactory(clbck -> {
            Button btn = new Button();
            ChangeStatus.adjustButton(btn, "Update", "update");
            btn.setOnAction(this::handlerUpdateButton);
            TableCell tbc = new TableCell();
            tbc.setGraphic(btn);
            return tbc;
        });

        this.tbvProduct.getColumns().addAll(idCol, nameCol, descriptionCol,
                priceCol, originCol, unitCol, categoryCol, updateCol);
    }

    private void loadContentToTableView(String kw) {
        if (kw != null && !kw.isBlank()) {

        }
        ProductService ps = new ProductServiceImpl();
        List<Product> products = ps.getProducts(kw);
        this.tbvProduct.getItems().clear();
        this.tbvProduct.setItems(FXCollections.observableList(products));
    }

    public void handlerAddNewProduct(ActionEvent event) {
        if (addButton.getText().equals("Confirm")) { //Nút xác nhận
            Product product = mapInputToProduct(new Product());
            if (CheckUtils.isValidName(product.getName()) == 1
                    &&CheckUtils.isValidPrice(product.getPrice())==1) {
                ProductService ps = new ProductServiceImpl();
                ps.addProduct(product);
                MessageBox.AlertBox("Add successful", "Add successful", Alert.AlertType.INFORMATION).show();
                loadContentToTableView("");
            } else {
                    handlerProductCheck(product);
//                MessageBox.AlertBox("Error", "Something is error", Alert.AlertType.ERROR).show();
            }
            ChangeStatus.adjustButton(addButton, "Thêm", "update");
            ChangeStatus.disable(cancelButton, txtOrigin,txtProductDescription, 
                    txtProductName, txtProductPrice, cbbCategory, cbbUnit);
            ChangeStatus.enable(getTableViewButtons(tbvProduct));
            tbvProduct.setOnMouseClicked(this::handlerClickOnTableView);
            cbbCategory.setValue(null);
            cbbUnit.setValue(null);
        } else { //Nút thêm
            ChangeStatus.enable(cancelButton, txtOrigin,
                    txtProductDescription, txtProductName, txtProductPrice,
                    cbbCategory, cbbUnit);
            ChangeStatus.disable(getTableViewButtons(tbvProduct));
            ChangeStatus.adjustButton(addButton, "Confirm", "update");
            this.cbbCategory.getSelectionModel().clearSelection();
            this.cbbUnit.getSelectionModel().clearSelection();
            tbvProduct.setOnMouseClicked(evt -> {
            });
        }
        ChangeStatus.clearText(txtProductID, txtOrigin, txtProductDescription,
                txtProductName, txtProductPrice);
    }

    public void handlerProductCheck(Product product)
    {
        if(CheckUtils.isValidName(product.getName()) == 0)
        {
            lbProductNameFalse.setText("Vui lòng nhập vào tên sản phẩm");
            lbProductNameFalse.setVisible(true);

        }
//        if(CheckUtils.isValidPrice(product.getPrice())==-1)
//        {
//            lbPriceFalse.setText("Gíá tiền sản phẩm chưa được nhập");
//            lbPriceFalse.setVisible(true);
//        }
//        if(CheckUtils.isValidPrice(product.getPrice())==0)
//        {
//            lbPriceFalse.setText("Gía tiền chỉ được phép nhập bằng sô");
//            lbPriceFalse.setVisible(true);
//        }
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
                tbvProduct.setOnMouseClicked(this::handlerClickOnTableView);
                lbProductNameFalse.setVisible(false);
                lbPriceFalse.setVisible(false);
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

    public void handlerExportBtn(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        try {
            fileChooser.setTitle("Open File");

            // Set the initial directory to open
            fileChooser.setInitialDirectory(new File("D:\\"));

            // Add filters to the dialog to show only certain types of files
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Excel File", "*.xlsx")
            );
            File selectedFile = fileChooser.showSaveDialog(null);
            if (selectedFile != null) {
                // User selected a file, do something with it
                exportToExcel(tbvProduct.getItems(), selectedFile.getAbsolutePath());
                MessageBox.AlertBox("Success", "Success", Alert.AlertType.INFORMATION).show();
            } else {
                System.out.println("No file selected");
            }
        } catch (IOException e) {
            System.err.println("Lỗi");
            e.printStackTrace();
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ManageProductController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    /**
     * Update Product
     *
     * @param event
     */
    public void handlerUpdateButton(ActionEvent event) {
        Button b = (Button) event.getSource();
        TableCell cell = (TableCell) b.getParent();
        Product product = (Product) cell.getTableRow().getItem();
        if (product == null) {
            return;
        }
        Alert alert = MessageBox.AlertBox("Update", "Chỉnh sửa ?", Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                //Xác nhận update
                if (b.getText().equals("Confirm")) {
                    mapInputToProduct(product);
                    //Update here
                    ProductService ps = new ProductServiceImpl();
                    if (ps.updateProduct(product) > 0) { //update thành công
                        //Xử lý tắt các button tron tbv, tắt nút thêm, bật nút hủy
                        ChangeStatus.adjustButton(b, "Update", "update");
                        ChangeStatus.toggleEnabledButton(cancelButton, addButton);
                        ChangeStatus.enable(getTableViewButtons(tbvProduct, ""));
                        ChangeStatus.disable(txtProductID, cancelButton, txtOrigin,
                                txtProductDescription, txtProductName, txtProductPrice,
                                cbbCategory, cbbUnit);
                        ChangeStatus.enable(addButton);
                        loadContentToTableView(null);
                    } else {
                        MessageBox.AlertBox("Error", "Something is error!!!", Alert.AlertType.ERROR).show();
                    }
                    this.tbvProduct.setOnMouseClicked(this::handlerClickOnTableView);
                } else {
                    //Bắt đầu input để update
                    ChangeStatus.adjustButton(b, "Confirm", "confirm");
                    ChangeStatus.toggleEnabledButton(cancelButton, addButton);
                    ChangeStatus.disable(getTableViewButtons(tbvProduct, "Quản lý"));
                    ChangeStatus.disable(addButton);
                    ChangeStatus.enable(cancelButton, txtOrigin,
                            txtProductDescription, txtProductName, txtProductPrice,
                            cbbCategory, cbbUnit);
                    b.setDisable(false);
                    showProductDetail(product);
                    this.tbvProduct.setOnMouseClicked(evt -> {
                    });
                }
            }
        });
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
