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
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 @author Justin Traymond Miles
 Student ID - 006121780
 */
/** This class controls the add product form. RUNTIME ERROR: Example of runtime error correction. Search button wasn't parsing items with matching criteria into table.
 fixed by adding this to addProductFormController search function
 " addProducts.setItems(partFiesta);
 if (partFiesta.size() == 0) {
 whoaAlert(1);". */
public class addProductFormController implements Initializable {
    //Declarations
    /**
     * The list of part associated with the user's product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    @FXML
    private TableView<Part> addProducts;
    @FXML
    private Button addProductsAddBtn;
    @FXML
    private TableColumn<Part, Integer> addProductsAddIDColumn;
    @FXML
    private TableColumn<Part, Integer> addProductsAddInvColumn;
    @FXML
    private TableColumn<Part, String> addProductsAddNameColumn;
    @FXML
    private TableColumn<Part, Double> addProductsAddPriceColumn;
    @FXML
    private Button addProductsCancelBtn;
    @FXML
    private Button addProductsClearBtn;
    @FXML
    private Label addProductsIdLbl;
    @FXML
    private TextField addProductsIdTxt;
    @FXML
    private Label addProductsInvLbl;
    @FXML
    private TextField addProductsInvTxt;
    @FXML
    private Label addProductsMaxLbl;
    @FXML
    private TextField addProductsMaxTxt;
    @FXML
    private Label addProductsMinLbl;
    @FXML
    private TextField addProductsMinTxt;
    @FXML
    private Label addProductsNameLbl;
    @FXML
    private TextField addProductsNameTxt;
    @FXML
    private Label addProductsPriceLbl;
    @FXML
    private TextField addProductsPriceTxt;
    @FXML
    private Button addProductsRemoveBtn;
    @FXML
    private Button addProductsSaveBtn;
    @FXML
    private Label addProductsScreenLbl;
    @FXML
    private Button addProductsSearchBtn;
    @FXML
    private TextField addProductsSearchTxt;
    @FXML
    private TableView<Part> removeProducts;
    @FXML
    private TableColumn<Part, Integer> removeProductsIDColumn;
    @FXML
    private TableColumn<Part, Integer> removeProductsInvColumn;
    @FXML
    private TableColumn<Part, String> removeProductsNameColumn;
    @FXML
    private TableColumn<Part, Double> removeProductsPriceColumn;
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
//ActionEvents
    /** This method performs the returnHome function. */
    private void returnHome(ActionEvent event) throws IOException {
        Parent aprReturnHome = FXMLLoader.load(getClass().getResource("/View/Main Form.fxml"));
        Scene aprscene = new Scene(aprReturnHome);
        Stage aprstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        aprstage.setScene(aprscene);
        aprstage.show();
    }
    public void updateAddProducts() {
        addProducts.setItems(Inventory.getAllParts());
    }
    @FXML
    void handleAdd(ActionEvent event) {
        Part selectedPart = addProducts.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                whoaAlert(5);
            } else {
                associatedParts.add(selectedPart);
                removeProducts.setItems(associatedParts);
        }
    }
    @FXML
/**This method cancels the add product action and returns Home */
    void handleAddProductsCancel(ActionEvent event) throws IOException {
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
    /** This method saves the add product action and returns Home. */
    void handleAddProductsSave(ActionEvent event) throws IOException {
        try {
            int id = 0;
            String name = addProductsNameTxt.getText();
            Double price = Double.parseDouble(addProductsPriceTxt.getText());
            int stock = Integer.parseInt(addProductsInvTxt.getText());
            int min = Integer.parseInt(addProductsMinTxt.getText());
            int max = Integer.parseInt(addProductsMaxTxt.getText());
                    if (name.isEmpty()) {
                        whoaAlert(6);
                    } else {
                        if (minTrue(min, max) && invTrue(min, max, stock)) {
                            Product newProduct = new Product(id, name, price, stock, min, max);
                            for (Part part : associatedParts) {
                                newProduct.addAssociatedPart(part);
                            }
                            newProduct.setId(Inventory.incProdIdTick());
                            Inventory.addProduct(newProduct);
                            returnHome(event);
                }
            }
        }catch (Exception welp){
            whoaAlert(1);
        }
    }
    @FXML
    void handleClearSearch(ActionEvent event) {
        updateAddProducts();
        addProductsSearchTxt.setText("");
    }
    @FXML
    void addProductsSearchKeyPressed(KeyEvent event) {

        if (addProductsSearchTxt.getText().isEmpty()) {
            addProducts.setItems(Inventory.getAllParts());
        }
    }
    @FXML
    void handleDelete(ActionEvent event) {
        Part selectedPart = removeProducts.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                whoaAlert(5);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Remove selected part?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    associatedParts.remove(selectedPart);
                    removeProducts.setItems(associatedParts);
            }
        }
    }
    @FXML
        void handleSearch(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partFiesta = FXCollections.observableArrayList();
        String searchString = addProductsSearchTxt.getText();
        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partFiesta.add(part);
            }
        }
        //Example of runtime error correction. Search button wasn't parsing items with matching criteria into table.
        addProducts.setItems(partFiesta);
            if (partFiesta.size() == 0) {
                whoaAlert(1);
            }
    }//Alerts & Errors
        private void whoaAlert(int alertType) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            switch (alertType) {
                case 1 -> {
                    error.setTitle("Warning, Error");
                    error.setHeaderText("Error Adding Product");
                    error.setContentText("Complete All Form Fields.");
                    error.showAndWait();
                }
                case 2 -> {
                    info.setTitle("Information");
                    info.setHeaderText("Invalid Part");
                    info.showAndWait();
                }
                case 3 -> {
                    error.setTitle("Warning, Error");
                    error.setHeaderText("Invalid value for Min");
                    error.setContentText("Min Must Be Between 0 and Max.");
                    error.showAndWait();
                }
                case 4 -> {
                    error.setTitle("Warning, Error");
                    error.setHeaderText("Invalid value for Inventory");
                    error.setContentText("Inventory Must Be Between 0 and Max.");
                    error.showAndWait();
                }
                case 5 -> {
                    error.setTitle("Warning, Error");
                    error.setHeaderText("Select a Part");
                    error.setContentText("Select a Part to Perform This Action.");
                    error.showAndWait();
                }
                case 6 -> {
                    error.setTitle("Warning, Error");
                    error.setHeaderText("Name Empty");
                    error.setContentText("Enter a Name.");
                    error.showAndWait();
                }
            }
        }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductsAddIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductsAddNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductsAddInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductsAddPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProducts.setItems(Inventory.getAllParts());

        removeProductsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        removeProductsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        removeProductsInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        removeProductsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
