/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.pojo.Category;
import com.tester.pojo.Product;
import com.tester.pojo.Unit;
import com.tester.service.CategoryService;
import com.tester.service.ProductService;
import com.tester.service.UnitService;
import com.tester.service.impl.CategoryServiceImpl;
import com.tester.service.impl.ProductServiceImpl;
import com.tester.service.impl.UnitServiceImpl;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author LENOVO
 */
public class ManageProductController extends AbstractManageController {
    @FXML
    private TableView tbvProduct;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        loadTableColumn();
        loadContentToTableView(null);
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
        unitCol.setCellValueFactory(cellData -> {
            Integer unitId = cellData.getValue().getUnitId();
            UnitService us = new UnitServiceImpl();
            Unit unit = us.getUnitById(unitId);
            if (unit == null) {
                return new SimpleStringProperty("");
            } else {
                return new SimpleStringProperty(unit.getName());
            }
        });
        this.tbvProduct.getColumns().addAll(idCol, nameCol, descriptionCol, priceCol, originCol, unitCol, categoryCol);
    }
    
    private void loadContentToTableView(String kw) {
        if (kw != null && !kw.isBlank()) {

        }
        ProductService ps = new ProductServiceImpl();
        List<Product> products = ps.getProducts(kw);
        this.tbvProduct.getItems().clear();
        this.tbvProduct.setItems(FXCollections.observableList(products));
    }
}
