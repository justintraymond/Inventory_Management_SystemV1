package Controller;


import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** @author Justin Traymond Miles
 * Student ID - 006121780
 */
/** This class controls the modify part form. */
public class modifyPartFormController implements Initializable {

    //Declarations
    @FXML
    private Button modifyPartCancelBtn;
    @FXML
    private Label modifyPartDynLbl;
    @FXML
    private TextField modifyPartDynTxt;
    @FXML
    private ToggleGroup modifyPartRadioTG;
    @FXML
    private Label modifyPartIDLbl;
    @FXML
    private TextField modifyPartIdTxt;
    @FXML
    private RadioButton modifyPartInHouseRadio;
    @FXML
    private Label modifyPartInvLbl;
    @FXML
    private TextField modifyPartInvTxt;
    @FXML
    private Label modifyPartMaxLbl;
    @FXML
    private TextField modifyPartMaxTxt;
    @FXML
    private Label modifyPartMinLbl;
    @FXML
    private TextField modifyPartMinTxt;
    @FXML
    private Label modifyPartNameLbl;
    @FXML
    private TextField modifyPartNameTxt;
    @FXML
    private RadioButton modifyPartOutsourcedRadio;
    @FXML
    private Label modifyPartPriceLbl;
    @FXML
    private TextField modifyPartPriceTxt;
    @FXML
    private Button modifyPartSaveBtn;
    @FXML
    private Label modifyPartScreenLbl;
    //Selected Part Declaration
    private Part selectedPart;

//Action Events
/** This method performs the returnHome function. */
private void returnHome(ActionEvent event) throws IOException {

    Parent mpReturnHome = FXMLLoader.load(getClass().getResource("/View/Main Form.fxml"));
    Scene scene = new Scene(mpReturnHome);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}
    @FXML
    /**This method cancels the modify part action and returns Home */
    void handleModifyPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Cancel Alert");
        alert.setContentText("Cancel Part Modify and Return Home?");
        Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                returnHome(event);
        }
    }
    @FXML
    /** This method saves the modify part action and returns Home. */
    void handleModifyPartSave(ActionEvent event) throws IOException {
        try {
            int id = selectedPart.getId();
            String name = modifyPartNameTxt.getText();
            Double price = Double.parseDouble(modifyPartPriceTxt.getText());
            int stock = Integer.parseInt(modifyPartInvTxt.getText());
            int min = Integer.parseInt(modifyPartMinTxt.getText());
            int max = Integer.parseInt(modifyPartMaxTxt.getText());
            int machineId;
            String companyName;
            boolean partAddTrue = false;
                if (minTrue(min, max) && invTrue(min, max, stock)) {
                    if (modifyPartInHouseRadio.isSelected()) {
                        try {
                        machineId = Integer.parseInt(modifyPartDynTxt.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.addPart(newInHousePart);
                        partAddTrue = true;
                    } catch (Exception welp) {
                        whoaAlert(2);
                    }
                }
                if (modifyPartOutsourcedRadio.isSelected()) {
                    companyName = modifyPartDynTxt.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max,
                            companyName);
                    Inventory.addPart(newOutsourcedPart);
                    partAddTrue = true;
                }
                if (partAddTrue) {
                    Inventory.deletePart(selectedPart);
                    returnHome(event);
                }
            }
        } catch(Exception welp) {
            whoaAlert(1);
        }
    }
    @FXML
    void onModifyPartsInHouseRadio(ActionEvent event) {
        modifyPartDynLbl.setText("Machine ID");
    }
    @FXML
    void onModifyPartsOutsourcedRadio(ActionEvent event) {
        modifyPartDynLbl.setText("Company Name");
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
                error.setHeaderText("Error Modifying Part");
                error.setContentText("Complete All Form Fields.");
                error.showAndWait();
                break;
                case 2:
                error.setTitle("Warning, Error");
                error.setHeaderText("Invalid value for Machine ID");
                error.setContentText("Machine ID may only contain numbers.");
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
                error.setContentText("Inventory Must Be Between 0 and Max.");
                error.showAndWait();
                break;
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    /** This method initializes the modify part screen. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = mainFormController.getPartModifier();

        if (selectedPart instanceof InHouse) {
            //Example of correcting a build error because of a simple typo. Prevented ".setSelected" from being executed
            modifyPartInHouseRadio.setSelected(true);
            modifyPartDynLbl.setText("Machine ID");
            modifyPartDynTxt.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        if (selectedPart instanceof Outsourced){
            //Example of correcting a build error because of a simple typo. Prevented ".setSelected" from being executed
            modifyPartOutsourcedRadio.setSelected(true);
            modifyPartDynLbl.setText("Company Name");
            modifyPartDynTxt.setText(((Outsourced) selectedPart).getCompanyName());
        }
        modifyPartIdTxt.setText(String.valueOf(selectedPart.getId()));
        modifyPartNameTxt.setText(selectedPart.getName());
        modifyPartInvTxt.setText(String.valueOf(selectedPart.getStock()));
        modifyPartPriceTxt.setText(String.valueOf(selectedPart.getPrice()));
        modifyPartMaxTxt.setText(String.valueOf(selectedPart.getMax()));
        modifyPartMinTxt.setText(String.valueOf(selectedPart.getMin()));
    }
}
