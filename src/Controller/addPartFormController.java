package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

 /** @author Justin Traymond Miles
 * Student ID - 006121780
 */
/** This class controls the add part form.*/
public class addPartFormController implements Initializable {
    @FXML
    private ToggleGroup addPartRadioTG;
    @FXML
    private Label addPartIdLbl;
    @FXML
    private Button addPartsCancelBtn;
    @FXML
    private Label addPartsDynLbl;
    @FXML
    private TextField addPartsDynTxt;
    @FXML
    private RadioButton addPartsInHouseRadio;
    @FXML
    private Label addPartsInvLbl;
    @FXML
    private TextField addPartsInvTxt;
    @FXML
    private Label addPartsMaxLbl;
    @FXML
    private TextField addPartsMaxTxt;
    @FXML
    private Label addPartsMinLbl;
    @FXML
    private TextField addPartsMinTxt;
    @FXML
    private Label addPartsNameLbl;
    @FXML
    private TextField addPartsNameTxt;
    @FXML
    private RadioButton addPartsOutsourceRadio;
    @FXML
    private Label addPartsPriceLbl;
    @FXML
    private TextField addPartsPriceTxt;
    @FXML
    private Button addPartsSaveBtn;
    @FXML
    private Label addPartsScreenLbl;
    @FXML
    private TextField idTxt;
//Action Events
    /**This method performs the returnHome function*/
    private void returnHome(ActionEvent event) throws IOException {
        Parent apReturnHome = FXMLLoader.load(getClass().getResource("/View/Main Form.fxml"));
        Scene apscene = new Scene(apReturnHome);
        Stage apstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        apstage.setScene(apscene);
        apstage.show();
    }
    @FXML
    void addPartsInHouseRadio(ActionEvent event) {
        addPartsDynLbl.setText("Machine ID");
        addPartsDynTxt.setPromptText("Machine ID");
    }
    @FXML
    void addPartsOutsourceRadio(ActionEvent event) {
        addPartsDynLbl.setText("Company Name");
        addPartsDynTxt.setPromptText("Company Name");
    }
    @FXML
    /** This method cancels the add part action and returns Home. */
    void handleAddPartsCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Cancel Alert");
        alert.setContentText("Cancel Part Add and Return Home?");
        Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
            returnHome(event);
        }
    }
    @FXML
    /** This method saves the add part action and returns Home. */
    void handleAddPartsSave(ActionEvent event) {
        try {
            int id = 0 ;
            String name = addPartsNameTxt.getText();
            Double price = Double.parseDouble(addPartsPriceTxt.getText());
            int stock = Integer.parseInt(addPartsInvTxt.getText());
            int min = Integer.parseInt(addPartsMinTxt.getText());
            int max = Integer.parseInt(addPartsMaxTxt.getText());
            int machineId;
            String companyName;
            boolean partAddTrue = false;
            if (name.isEmpty()) {
                whoaAlert(5);
            } else
            if (minTrue(min, max) && invTrue(min, max, stock)) {
                if (addPartsInHouseRadio.isSelected()) {
                    try {
                        machineId = Integer.parseInt(addPartsDynTxt.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                        newInHousePart.setId(Inventory.incPartIdTick());
                        Inventory.addPart(newInHousePart);
                        partAddTrue = true;
                    } catch (Exception welp) {
                        whoaAlert(2);
                    }
                }
                if (addPartsOutsourceRadio.isSelected()) {
                    companyName = addPartsDynTxt.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max,
                            companyName);
                    Inventory.addPart(newOutsourcedPart);
                    partAddTrue = true;
                }
                if (partAddTrue) {
                    returnHome(event);
                }
            }
        } catch(Exception welp) {
            whoaAlert(1);
        }
    }
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
    //Errors
    private void whoaAlert(int alertType) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        switch (alertType) {
            case 1:
                error.setTitle("Warning, Error");
                error.setHeaderText("Error Adding Part");
                error.setContentText("Blank Field or Invalid Value Entered.");
                error.showAndWait();
                break;
            case 2:
                error.setTitle("Warning, Error");
                error.setHeaderText("Invalid value for Machine ID");
                error.setContentText("Machine ID Must Be a Number.");
                error.showAndWait();
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
                error.setContentText("Inventory must be between 0 and Max.");
                error.showAndWait();
                break;
            case 5:
                error.setTitle("Warning, Error");
                error.setHeaderText("Name Empty");
                error.setContentText("Enter a Name");
                error.showAndWait();
                break;
        }
    }
    @Override
    /** This method initializes the add part screen. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartsInHouseRadio.setSelected(true);
    }
}
