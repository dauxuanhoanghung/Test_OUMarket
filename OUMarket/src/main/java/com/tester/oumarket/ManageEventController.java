/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.oumarket;

import com.tester.pojo.Event;
import com.tester.pojo.Product;
import com.tester.pojo.sub.SubProduct;
import com.tester.service.CategoryService;
import com.tester.service.EventService;
import com.tester.service.ProductService;
import com.tester.service.impl.CategoryServiceImpl;
import com.tester.service.impl.EventServiceImpl;
import com.tester.service.impl.ProductServiceImpl;
import com.tester.utils.ChangeStatus;
import com.tester.utils.CheckUtils;
import com.tester.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author LENOVO
 */
public class ManageEventController extends AbstractManageController {

    @FXML
    private Button saveBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button deleteDetail;
    @FXML
    private TextField descriptionField;
    @FXML
    private TableView tbvUnset;
    @FXML
    private TableView tbvProductSelected;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private DatePicker startDatePicker;
    private CategoryService cs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        cs = new CategoryServiceImpl();

        loadUnsetTableColumn();
        loadContentToUnsetTableView("");

        loadEventDetailTableColumn();

        setHandling();
        ChangeStatus.disable(saveBtn);

        tbvProductSelected.getItems().addListener(new ListChangeListener<SubProduct>() {
            @Override
            public void onChanged(Change<? extends SubProduct> change) {
                if (change.next()) {
                    int newSize = tbvProductSelected.getItems().size();
                    saveBtn.setDisable(newSize == 0);
                }
            }
        });
    }

    private void setHandling() {
        tbvUnset.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tbvProductSelected.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        saveBtn.setOnAction(this::handleSaveEvent);
        addBtn.setOnAction(this::handleAddButton);
        cancelBtn.setOnAction(this::handleCancelButton);
        deleteDetail.setOnAction(this::handleRemoveButton);
    }

    private void loadUnsetTableColumn() {
        TableColumn<Product, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Float> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, String> originCol = new TableColumn<>("Origin");
        originCol.setCellValueFactory(new PropertyValueFactory<>("origin"));

        TableColumn<Product, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(cellData -> {

            return new SimpleStringProperty(cellData.getValue().getCategory().getName());
        });

        this.tbvUnset.getColumns().addAll(idCol, nameCol, priceCol, originCol, categoryCol);
    }

    private void loadContentToUnsetTableView(String kw) {
        ProductService ps = new ProductServiceImpl();
        List<Product> products = ps.getProducts(kw);
        this.tbvUnset.getItems().clear();
        this.tbvUnset.setItems(FXCollections.observableList(products));
    }

    private void loadEventDetailTableColumn() {
        TableColumn<SubProduct, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(120);

        TableColumn<SubProduct, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(300);

        TableColumn<SubProduct, Float> priceCol = new TableColumn<>("Discount Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(100);

        this.tbvProductSelected.getColumns().addAll(idCol, nameCol, priceCol);
    }

    public void handleSaveEvent(ActionEvent event) {
        String description = descriptionField.getText();
        LocalDateTime endDate = endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        if (endDate == null) {
            MessageBox.AlertBox("Thiếu dữ liệu", "Hãy điền ngày kết thúc", Alert.AlertType.WARNING).show();
            return;
        }
        LocalDateTime startDate = startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime();
        if (startDate == null) {
            MessageBox.AlertBox("Thiếu dữ liệu", "Hãy điền ngày bắt đầu", Alert.AlertType.WARNING).show();
            return;
        }
        EventService es = new EventServiceImpl();
        if (CheckUtils.isNotPastDate(endDate) == 1
                && CheckUtils.isNotPastDate(startDate) == 1) {
            Event addedEvent = new Event(description, startDate, endDate.plusDays(1));
            es.addEvent(addedEvent, tbvProductSelected.getItems());
            MessageBox.AlertBox("Add Successful", "Add Successful", Alert.AlertType.INFORMATION).show();
            ChangeStatus.clearText(descriptionField);
            endDatePicker.setValue(null);
            startDatePicker.setValue(null);
            loadContentToUnsetTableView("");
            tbvProductSelected.getItems().clear();
        } else {
            System.out.println("Khong luu dc");
        }
    }

//    public void handleAddButton(ActionEvent event) {
//        ObservableList<Product> selectedProducts = tbvUnset.getSelectionModel().getSelectedItems();
//        if (selectedProducts.isEmpty()) {
//            MessageBox.AlertBox("Vui lòng chọn sản phẩm", "Chưa có sản phẩm nào đc chọn",
//                    Alert.AlertType.WARNING).show();
//            return;
//        }
//        Stage mainStage = (Stage) addBtn.getScene().getWindow();
//        for (Product p : selectedProducts) {
//            Stage subStage = new Stage();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("EventDetails.fxml"));
//            Parent root = null;
//            try {
//                root = loader.load();
//            } catch (IOException ex) {
//                Logger.getLogger(ManageEventController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            Scene scene = new Scene(root);
//            // Set the controller for the sub stage
//            EventDetailsController edController = loader.getController();
//            edController.setProduct(p);
//            edController.showProduct(p);
//            
//            subStage.setScene(scene);
//            subStage.initModality(Modality.APPLICATION_MODAL);
//            subStage.initOwner(mainStage);
//            subStage.showAndWait();
//            SubProduct subProduct = edController.getResult();
//            if (subProduct != null) {
//                tbvProductSelected.getItems().add(subProduct);
//                tbvUnset.getItems().removeIf(product -> {
//                    return ((Product) product).hasSameId(subProduct);
//                });
//            }
//            
//        }
//        MessageBox.AlertBox("OK", "OK", Alert.AlertType.INFORMATION).show();
//    }
    public void handleAddButton(ActionEvent event) {
        ObservableList<Product> selectedProducts = tbvUnset.getSelectionModel().getSelectedItems();
        if (selectedProducts.isEmpty()) {
            MessageBox.AlertBox("Vui lòng chọn sản phẩm", "Chưa có sản phẩm nào đc chọn",
                    Alert.AlertType.WARNING).show();
            return;
        }
        Stage mainStage = (Stage) addBtn.getScene().getWindow();
        for (Product p : selectedProducts) {
            Stage subStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EventDetails.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(ManageEventController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene scene = new Scene(root);
            // Set the controller for the sub stage
            EventDetailsController edController = loader.getController();
            edController.setProduct(p);
            edController.showProduct(p);

            subStage.setScene(scene);
            subStage.initModality(Modality.APPLICATION_MODAL);
            subStage.initOwner(mainStage);
            subStage.showAndWait();
            SubProduct subProduct = edController.getResult();
            if (subProduct != null) {
                tbvProductSelected.getItems().add(subProduct);
                for (Object product : tbvUnset.getItems()) {
                    if (((Product)product).hasSameId(subProduct)) {
                        tbvUnset.getItems().remove(product);
                        break;
                    }
                }
            }
        }
        MessageBox.AlertBox("OK", "OK", Alert.AlertType.WARNING).show();
    }

    public void handleCancelButton(ActionEvent event) {
        ChangeStatus.clearText(descriptionField);
        endDatePicker.setValue(null);
        startDatePicker.setValue(null);
        ObservableList<SubProduct> selectedSubProducts = tbvProductSelected.getItems();
        removeAllInSelected(selectedSubProducts);
    }

    public void handleRemoveButton(ActionEvent event) {
        ObservableList<SubProduct> selectedSubProducts = tbvProductSelected.getSelectionModel().getSelectedItems();
        if (selectedSubProducts.isEmpty()) {
            MessageBox.AlertBox("Vui lòng chọn sản phẩm", "Chưa có sản phẩm nào đc chọn",
                    Alert.AlertType.WARNING).show();
            return;
        }
        // Get the selected SubProduct
        removeAllInSelected(selectedSubProducts);
    }

    private void removeAllInSelected(ObservableList<SubProduct> selectedSubProducts) {
        Alert alert = MessageBox.AlertBox("Xóa",
                "Bạn có chắc loại bỏ sản phẩm này khỏi sự kiện ?",
                Alert.AlertType.CONFIRMATION);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                for (SubProduct selectedSubProduct : selectedSubProducts) {
                    if (selectedSubProduct != null) {
                        // Remove the SubProduct from the tbvProductSelected table view
                        tbvProductSelected.getItems().remove(selectedSubProduct);
                        // Add the corresponding Product to the tbvUnset table view
                        ProductService ps = new ProductServiceImpl();
                        Product product = ps.getProductById(selectedSubProduct.getId());
                        if (product != null) {
                            tbvUnset.getItems().add(product);
                        }
                    }
                }
            }
        });
    }
}
