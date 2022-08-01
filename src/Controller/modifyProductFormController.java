package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** @author Justin Traymond Miles
 * Student ID - 006121780
 */
/** This class controls the modify product form. */
public class modifyProductFormController implements Initializable {
    /** This method performs the returnHome function. */
    private void returnHome(ActionEvent event) throws IOException {
        Parent aprReturnHome = FXMLLoader.load(getClass().getResource("/View/Main Form.fxml"));
        Scene aprscene = new Scene(aprReturnHome);
        Stage aprstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        aprstage.setScene(aprscene);
        aprstage.show();
    }
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    Product selectedProduct;
    @FXML
    private Button addProductClearBtn;
    @FXML
    private TableView<Part> modifyProductAdd;
    @FXML
    private Button modifyProductAddBtn;
    @FXML
    private TableColumn<Part, Integer> modifyProductAddIDColumn;
    @FXML
    private TableColumn<Part, Integer> modifyProductAddInvColumn;
    @FXML
    private TableColumn<Part, String> modifyProductAddNameColumn;
    @FXML
    private TableColumn<Part, Double> modifyProductAddPriceColumn;
    @FXML
    private Button modifyProductCancelBtn;
    @FXML
    private TableView<Part> modifyProductDelete;
    @FXML
    private TableColumn<Part, Integer> modifyProductDeleteIDColumn;
    @FXML
    private TableColumn<Part, Integer> modifyProductDeleteInvColumn;
    @FXML
    private TableColumn<Part, String> modifyProductDeleteNameColumn;
    @FXML
    private TableColumn<Part, Double> modifyProductDeletePriceColumn;
    @FXML
    private Label modifyProductIDLbl;
    @FXML
    private TextField modifyProductIdTxt;
    @FXML
    private Label modifyProductInvLbl;
    @FXML
    private TextField modifyProductInvTxt;
    @FXML
    private Label modifyProductMaxLbl;
    @FXML
    private TextField modifyProductMaxTxt;
    @FXML
    private Label modifyProductMinLbl;
    @FXML
    private TextField modifyProductMinTxt;
    @FXML
    private Label modifyProductNameLbl;
    @FXML
    private TextField modifyProductNameTxt;
    @FXML
    private Label modifyProductPriceLbl;
    @FXML
    private TextField modifyProductPriceTxt;
    @FXML
    private Button modifyProductRemoveBtn;
    @FXML
    private Button modifyProductSaveBtn;
    @FXML
    private Label modifyProductScreenLbl;
    @FXML
    private Button modifyProductSearchBtn;
    @FXML
    private TextField modifyProductSearchTxt;
    private boolean minTrue(int min, int max) {
        boolean trueOrNah = true;
        if (min <= 0 || min >= max) {
            trueOrNah = false;
            whoaAlert(3);
        }
        return trueOrNah;
    }
    private boolean invTrue(int min, int max, int stock) {
        boolean trueOrNah = true;
        if (stock < min || stock > max) {
            trueOrNah = false;
            whoaAlert(4);
        }
        return trueOrNah;
    }
    public void updateModifyProductAdd() {
        modifyProductAdd.setItems(Inventory.getAllParts());
    }
    public void updateModifyProductDelete() {
        modifyProductDelete.setItems(associatedParts);
    }
//Action Events
    @FXML
    void handleAdd(ActionEvent event) {
        Part selectedPart = modifyProductAdd.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            whoaAlert(5);
        } else {
             associatedParts.add(selectedPart);
            modifyProductAdd.setItems(associatedParts);
        }
    }
    @FXML
    void handleClearSearch(ActionEvent event) {
        updateModifyProductAdd();
        modifyProductSearchTxt.setText("");
    }
    @FXML
    void handleDelete(ActionEvent event) {
        Part selectedPart = modifyProductAdd.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                whoaAlert(5);
            } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Do you want to remove the selected part?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    associatedParts.remove(selectedPart);
                    modifyProductAdd.setItems(associatedParts);
                }
            }
    }
    @FXML
    /**This method cancels the modify product action and returns Home */
    void handleModifyProductCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Cancel Alert");
        alert.setContentText("Cancel Product Add and Return Home?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnHome(event);
        }
    }
    @FXML
    /** This method saves the modify product action and returns Home. */
    void handleModifyProductSave(ActionEvent event) {
        try {
            int id = selectedProduct.getId();
            String name = modifyProductNameTxt.getText();
            Double price = Double.parseDouble(modifyProductPriceTxt.getText());
            int stock = Integer.parseInt(modifyProductInvTxt.getText());
            int min = Integer.parseInt(modifyProductMinTxt.getText());
            int max = Integer.parseInt(modifyProductMaxTxt.getText());
                if (name.isEmpty()) {
                    whoaAlert(6);
                } else {
                    if (minTrue(min, max) && invTrue(min, max, stock)) {
                        Product newProd = new Product(id, name, price, stock, min, max);
                        for (Part part : associatedParts) {
                            newProd.addAssociatedPart(part);
                        }
                        Inventory.addProduct(newProd);
                        Inventory.deleteProduct(selectedProduct);
                        returnHome(event);
                    }
                }
            } catch (Exception welp){
                whoaAlert(1);
        }
    }
    @FXML
    void handleSearch(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partFiesta = FXCollections.observableArrayList();
        String searchString = modifyProductSearchTxt.getText();
            for (Part part : allParts) {
                if (String.valueOf(part.getId()).contains(searchString) ||
                        part.getName().contains(searchString)) {
                    partFiesta.add(part);
                }
            }
            modifyProductDelete.setItems(partFiesta);
            if (partFiesta.size() == 0) {
                whoaAlert(1);
            }
    }
    @FXML
    void modifyProductSearchKeyPressed(KeyEvent event) {

        if (modifyProductSearchTxt.getText().isEmpty()) {
            modifyProductDelete.setItems(Inventory.getAllParts());
        }
    }
    //Errors
    private void whoaAlert(int alertType) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        Alert info = new Alert(Alert.AlertType.INFORMATION);

            switch (alertType) {
                case 1:
                    error.setTitle("Warning, Error");
                    error.setHeaderText("Error Modifying Product");
                    error.setContentText("Complete All Form Fields.");
                    error.showAndWait();
                    break;
                case 2:
                    info.setTitle("Information");
                    info.setHeaderText("Invalid Part");
                    info.showAndWait();
                    break;
                case 3:
                    error.setTitle("Warning, Error");
                    error.setHeaderText("Invalid value for Min");
                    error.setContentText("Min Must Be Between 0 and Max.");
                    error.showAndWait();
                    break;
                case 4:
                    error.setTitle("Warning, Error");
                    error.setHeaderText("Invalid value for Inventory");
                    error.setContentText("Inventory Must Be Between 0 and Max.");
                    error.showAndWait();
                    break;
                case 5:
                    error.setTitle("Warning, Error");
                    error.setHeaderText("Select a Part.");
                    error.setContentText("Select a Part to Perform This Action");
                    error.showAndWait();
                    break;
                case 6:
                    error.setTitle("Warning, Error");
                    error.setHeaderText("Name Empty");
                    error.setContentText("Enter a Name.");
                    error.showAndWait();
                    break;
        }
    }
    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    /** This method initializes the modify product screen. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = mainFormController.getProductModifier();
        associatedParts = selectedProduct.getAllAssociatedParts();
        modifyProductAddIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductAddNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductAddInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductAddPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductAdd.setItems(Inventory.getAllParts());
        modifyProductDeleteIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductDeleteNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductDeleteInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductDeletePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductDelete.setItems(associatedParts);
        modifyProductIdTxt.setText(String.valueOf(selectedProduct.getId()));
        modifyProductNameTxt.setText(selectedProduct.getName());
        modifyProductInvTxt.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPriceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMaxTxt.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMinTxt.setText(String.valueOf(selectedProduct.getMin()));
        updateModifyProductDelete();
        updateModifyProductAdd();
    }


}

