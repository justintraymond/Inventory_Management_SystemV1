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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.input.KeyCode;

/**
 * @author Justin Traymond Miles
 * Student ID - 006121780
 */
/** This class controls the main screen of the Inventory Management System.*/
public class mainFormController implements Initializable {
    private static Part partModifier;
    private static Product productModifier;
//Declares Scene Objects
    @FXML
    /**an element of the GUI. */
    private Button addPartsBtn;
    @FXML
    /**an element of the GUI. */
    private Button addProductsBtn;
    @FXML
    /**an element of the GUI. */
    private Button clearSearchPartsBtn;
    @FXML
    /**an element of the GUI. */
    private Button clearSearchProductsBtn;
    @FXML
    /**an element of the GUI. */
    private Button deletePartsBtn;
    @FXML
    /**an element of the GUI. */
    private Button deleteProductsBtn;
    @FXML
    /**an element of the GUI. */
    private Button mainExitBtn;
    @FXML
    /**an element of the GUI. */
    private Label mainScreenLbl;
    @FXML
    /**an element of the GUI. */
    private Button modifyPartsBtn;
    @FXML
    /**an element of the GUI. */
    private Button modifyProductsBtn;
    @FXML
    /**an element of the GUI. */
    private TableView<Part> parts;
    @FXML
    /**an element of the GUI. */
    private TableColumn<Part, Integer> partsIDColumn;
    @FXML
    /**an element of the GUI. */
    private TableColumn<Part, Integer> partsInvColumn;
    @FXML
    /**an element of the GUI. */
    private Label partsLbl;
    @FXML
    /**an element of the GUI. */
    private TableColumn<Part, String> partsNameColumn;
    @FXML
    /**an element of the GUI. */
    private TableColumn<Part, Double> partsPriceColumn;
    public static Part getPartModifier(){
        return partModifier;
    }
    @FXML
    /**Table for products. */
    private TableView<Product> products;
    @FXML
    /**an element of the GUI. */
    private TableColumn<Product, Integer> productsIDColumn;
    @FXML
    /**an element of the GUI. */
    private TableColumn<Product, Integer> productsInvColumn;
    @FXML
    /**an element of the GUI. */
    private Label productsLbl;
    @FXML
    /**an element of the GUI. */
    private TableColumn<Product, String> productsNameColumn;
    @FXML
    /**an element of the GUI. */
    private TableColumn<Product, Double> productsPriceColumn;
    public static Product getProductModifier(){
        return productModifier;
    }
    @FXML
    /**an element of the GUI. */
    private Button searchPartsBtn;
    @FXML
    /**an element of the GUI. */
    private TextField searchPartsTxt;
    @FXML
    /**an element of the GUI. */
    private Button searchProductsBtn;
    @FXML
    /**an element of the GUI. */
    private TextField searchProductsTxt;
//Action Events
    @FXML
    //Part Screen Action
    /**This method launches the add part form. */
    void addPartsScreen(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("/View/AddPartForm.fxml"));
        Scene apScene = new Scene(addParts);
        Stage apStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        apStage.setScene(apScene);
        apStage.show();
        System.out.println("Add Part Button Clicked!");
    }
    //Product Screen Action
    @FXML
    /**This method launches the add product form. */
    void addProductsScreen(ActionEvent event) throws IOException {
            Parent addProds = FXMLLoader.load(getClass().getResource("/View/AddProductForm.fxml"));
            Scene aprScene = new Scene(addProds);
            Stage aprStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            aprStage.setScene(aprScene);
            aprStage.show();
            System.out.println("Add Product Button Clicked!");
        }
        //Clear Search Part
        @FXML
        /**This method launches the add part form. */
        void clearPartsSearch (ActionEvent event){
            updateParts();
            searchPartsTxt.setText("");
        }
    /**This method launches the add part form. */
    private void updateParts() {
        parts.setItems(Inventory.getAllParts());
    }
    /**This method launches the add part form. */
        //Clear Search Product
        @FXML
        void clearProductsSearch (ActionEvent event){
            updateProducts();
            searchProductsTxt.setText("");
        }
    private void updateProducts() {
        products.setItems(Inventory.getAllProducts());
    }

    //Program Exit
        @FXML
        void exitButton (ActionEvent event){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Exit Application");
            alert.setContentText("Do You Want To Exit The Application?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }

        System.out.println("Exit Button Clicked!");
    }
        //Part Delete
        @FXML
        void handlePartsDelete (ActionEvent event){
        Part selectedPart = parts.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                whoaAlert(3);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Do you want to delete the selected part?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deletePart(selectedPart);
                }
            }
        }
        //Part Search
        @FXML
         void handlePartsSearch (ActionEvent event){
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partFiesta = FXCollections.observableArrayList();
            String searchString = searchPartsTxt.getText();
            for (Part part : allParts) {
                if (String.valueOf(part.getId()).contains(searchString) ||
                        part.getName().contains(searchString)) {
                    partFiesta.add(part);
                }
            }
            parts.setItems(partFiesta);
            if (partFiesta.size() == 0) {
                whoaAlert(1);
            }
        }

    @FXML
    void partSearchTxtKey(KeyEvent event )  {
           if (searchPartsTxt.getText().isEmpty()) {
                parts.setItems(Inventory.getAllParts());
            }
        }

        //Product Delete
        @FXML
        void handleProductsDelete (ActionEvent event){
            Product selectedProduct = products.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                whoaAlert(4);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Delete selected product?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    ObservableList<Part> assocParts = selectedProduct.getAllAssociatedParts();
                    if (assocParts.size() >= 1) {
                        whoaAlert(5);
                    } else {
                        Inventory.deleteProduct(selectedProduct);
                    }
                }
            }
        }
        @FXML
        void handleProductsSearch (ActionEvent event){

            ObservableList<Product> allProducts = Inventory.getAllProducts();
            ObservableList<Product> productFiesta = FXCollections.observableArrayList();
            String searchString = searchProductsTxt.getText();
            for (Product product : allProducts) {
                if (String.valueOf(product.getId()).contains(searchString) ||
                        product.getName().contains(searchString)) {
                    productFiesta.add(product);
                }
            }
            products.setItems(productFiesta);
            if (productFiesta.size() == 0) {
                whoaAlert(2);
            }
        }
    @FXML
    void productSearchTxtKey(KeyEvent event) {
        if (searchProductsTxt.getText().isEmpty()) {
            products.setItems(Inventory.getAllProducts());
        }
    }
        @FXML
        //Modify Part Screen Action
        /**This method launches the modify part form. */
        void modifyPartsScreen (ActionEvent event) throws IOException {
            partModifier = parts.getSelectionModel().getSelectedItem();
            if (partModifier == null) {
                whoaAlert(3);
            } else {
                Parent modifyParts = FXMLLoader.load(getClass().getResource("/View/ModifyPartForm.fxml"));
                Scene mpScene = new Scene(modifyParts);
                Stage mpStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                mpStage.setScene(mpScene);
                mpStage.show();
                System.out.println("Modify Part Button Clicked!");
            }
        }
            @FXML
            //Modify Product Screen Action
            /**This method launches the modify product form. */
            void modifyProductsScreen (ActionEvent event) throws IOException {
                productModifier = products.getSelectionModel().getSelectedItem();
                if (productModifier == null) {
                    whoaAlert(4);
                } else {
                    Parent modifyProducts = FXMLLoader.load(getClass().getResource("/View/ModifyProductForm.fxml"));
                    Scene mprScene = new Scene(modifyProducts);
                    Stage mprStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    mprStage.setScene(mprScene);
                    mprStage.show();
                    System.out.println("Modify Product Button Clicked!");
                }
            }
            //Alert Creation
            private void whoaAlert(int alertType) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                Alert error = new Alert(Alert.AlertType.ERROR);

                switch (alertType) {
                    case 1:
                        alert.setTitle("Info");
                        alert.setHeaderText("Invalid Part");
                        alert.showAndWait();
                        break;
                    case 2:
                        alert.setTitle("Info");
                        alert.setHeaderText("Invalid Product");
                        alert.showAndWait();
                        break;
                    case 3:
                        error.setTitle("Warning, Error");
                        error.setHeaderText("Select a Part");
                        error.showAndWait();
                        break;
                    case 4:
                        error.setTitle("Warning, Error");
                        error.setHeaderText("Select a Product");
                        error.showAndWait();
                        break;
                    case 5:
                        error.setTitle("Warning, Error");
                        error.setHeaderText("Product has Associated Parts");
                        error.setContentText("Remove Associated Parts From Product.");
                        error.showAndWait();
                        break;
                }
            }
            @Override
            /** This method initializes the main screen. */
            public void initialize (URL url, ResourceBundle resourceBundle){
                //Part Populate
                parts.setItems(Inventory.getAllParts());
                partsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                partsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                partsInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                partsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                //Product Populate
                products.setItems(Inventory.getAllProducts());
                productsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                productsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                productsInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                productsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                updateParts();
                updateProducts();
                System.out.println("I am Groot!");
            }

   }


