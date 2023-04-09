package com.tester.oumarket;

import com.tester.pojo.BranchMarket;
import com.tester.pojo.BranchProduct;
import com.tester.pojo.Product;
import com.tester.service.BranchMarketService;
import com.tester.service.ProductService;
import com.tester.service.impl.BranchMarketServiceImpl;
import com.tester.service.impl.ProductServiceImpl;
import com.tester.utils.ChangeStatus;
import com.tester.utils.MessageBox;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class ManageBranchProductController extends AbstractManageController {

    private BranchMarket branch;
    private String previous;

    @FXML
    private TableView tbvUnsetProduct;
    @FXML
    private TableView tbvMainProduct;
    @FXML
    private Button findButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        this.findButton.setOnAction(this::handleFindButton);
        this.addButton.setOnAction(this::handleAddButton);
        this.tbvUnsetProduct.setOnMouseClicked(this::handleMouseClickOnUnsetTableView);
        this.tbvMainProduct.setOnMouseClicked(this::handleMouseClickOnMainTableView);

        txtSearch.textProperty().addListener(o -> {
            String text = this.txtSearch.getText();
            this.loadContentToUnsetTableView(text);
        });

        loadMainTableColumn();
        loadUnsetTableColumn();

        ChangeStatus.disable(addButton);
    }

    /**
     * @param branch the branch to set
     */
    public void setBranch(BranchMarket branch) {
        this.branch = branch;
    }

    /**
     * @param previous the previous to set
     */
    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public void handleFindButton(ActionEvent event) {

    }

    public void handleAddButton(ActionEvent event) {
        Product selectedProduct = (Product) tbvUnsetProduct.getSelectionModel().getSelectedItem();
        tbvUnsetProduct.getItems().remove(selectedProduct);
        BranchMarketService bms = new BranchMarketServiceImpl();
        if (bms.addProductToBranch(branch, selectedProduct) > 0) {
            loadContentToMainTableView("");
        }
        else
            MessageBox.AlertBox("Error", "Something is error!!!", Alert.AlertType.ERROR).show();
    }

    public void handleMouseClickOnUnsetTableView(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            Product selectedProduct = (Product) tbvUnsetProduct.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                ChangeStatus.enable(addButton);
            }
            else 
                ChangeStatus.disable(addButton);
        }
    }

    public void handleMouseClickOnMainTableView(MouseEvent event) {

    }

    /**
     * Gọi sau khi chạy để load nội dung các bảng
     */
    public void updateUI() {
        loadContentToUnsetTableView("");
        loadContentToMainTableView("");
    }

    /**
     * Bảng chỉ load SP chưa có trong chi nhánh
     */
    private void loadUnsetTableColumn() {
        TableColumn idCol = new TableColumn("Mã SP");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        idCol.setPrefWidth(100);

        TableColumn nameCol = new TableColumn("Tên SP");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        nameCol.setPrefWidth(100);

        TableColumn priceCol = new TableColumn("Giá");
        priceCol.setCellValueFactory(new PropertyValueFactory("price"));
        priceCol.setPrefWidth(300);

        this.tbvUnsetProduct.getColumns().addAll(idCol, nameCol, priceCol);
    }

    /**
     * Bảng chỉ load SP có trong chi nhánh
     */
    private void loadMainTableColumn() {
        TableColumn idCol = new TableColumn("Mã");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        idCol.setPrefWidth(100);

        TableColumn nameCol = new TableColumn("Tên SP");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        nameCol.setPrefWidth(100);

        TableColumn priceCol = new TableColumn("Giá");
        priceCol.setCellValueFactory(new PropertyValueFactory("price"));
        priceCol.setPrefWidth(300);

        TableColumn activeCol = new TableColumn<>("Active");
//        activeCol.setCellFactory(column -> {
//            Button activeBtn = new Button();
//            ChangeStatus.adjustButton(activeBtn, "", "confirm");
//            TableCell<BranchProduct, Boolean> cell = new TableCell<>() {
//                @Override
//                protected void updateItem(Boolean active, boolean empty) {
//                    super.updateItem(active, empty);
//                    if (empty) {
//                        setGraphic(null);
//                    } else {
//                        activeBtn.setText(active ? "Stop" : "Continue");
//                        setGraphic(activeBtn);
//                        activeBtn.setOnAction(event -> {
//                            BranchProduct product = (BranchProduct) getTableView().getItems().get(getIndex());                            
//                            BranchMarketService bms = new BranchMarketServiceImpl();
////                            BranchProduct productBranch = bms.getBranchProduct(branch, product);
////                            productBranch.setActive(!productBranch.isActive());
////                            if (bms.updateProductInBranch(productBranch) == 1) {
////                                activeBtn.setText(productBranch.isActive() ? "Stop" : "Continue");
////                            } else {
////                                MessageBox.AlertBox("Error", "Something is error!!!", Alert.AlertType.ERROR).show();
////                            }
//                        });
//                    }
//                }
//            };
//            return cell;
//        });
        activeCol.setCellFactory(column -> {
            Button activeBtn = new Button();
            ChangeStatus.adjustButton(activeBtn, "", "confirm");
            TableCell<Product, Boolean> cell = new TableCell<>() {
                @Override
                protected void updateItem(Boolean active, boolean empty) {
                    super.updateItem(active, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        BranchMarketService bms = new BranchMarketServiceImpl();
                        Product product = (Product) getTableView().getItems().get(getIndex());
                        BranchProduct branchProduct = bms.getBranchProduct(branch, product);
                        activeBtn.setText(branchProduct.isActive() ? "Stop" : "Continue");
                        setGraphic(activeBtn);
                        activeBtn.setOnAction(event -> {
                            branchProduct.setActive(!branchProduct.isActive());                            
                            if (bms.updateProductInBranch(branchProduct) == 1) {
                                activeBtn.setText(branchProduct.isActive() ? "Stop" : "Continue");
                            } else {
                                MessageBox.AlertBox("Error", "Something is error!!!", Alert.AlertType.ERROR).show();
                            }
                        });
                    }
                }
            };
            return cell;
        });
        this.tbvMainProduct.getColumns().addAll(idCol, nameCol, priceCol, activeCol);
    }

    /**
     * Load sản phẩm chưa có trong danh sách
     */
    private void loadContentToUnsetTableView(String kw) {
        ProductService service = new ProductServiceImpl();
        List<Product> products = service.getUnsetProductsByBranch(branch, kw);
        this.tbvUnsetProduct.getItems().clear();
        this.tbvUnsetProduct.setItems(FXCollections.observableList(products));
    }

    /**
     * Load sản phẩm đã có trong danh sách
     */
    private void loadContentToMainTableView(String kw) {
        ProductService service = new ProductServiceImpl();
        List<Product> products = service.getProductsByBranch(branch);
        this.tbvMainProduct.getItems().clear();
        this.tbvMainProduct.setItems(FXCollections.observableList(products));
    }
}
