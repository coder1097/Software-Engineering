package hotelmanagement.ui.addcustomer;

import com.jfoenix.controls.JFXTextField;
import hotelmanagement.DB.DBHandler;
import hotelmanagement.ui.viewcustomers.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Laptop
 */
public class CustomerAddController implements Initializable {

    @FXML
    private JFXTextField custName;
    @FXML
    private JFXTextField custID;
    @FXML
    private JFXTextField custHometown;
    @FXML
    private JFXTextField custYOB;
    @FXML
    private JFXTextField custMobile;
    @FXML
    private JFXTextField custEmail;
    @FXML
    private AnchorPane rootPane;
    
    private DBHandler dbHandler;
    private Alert alert;
    private boolean isInEditMode = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbHandler = DBHandler.getInstance();
    }    
    
    @FXML
    private void addCustomer(ActionEvent event) {
        String name = custName.getText();
        String id = custID.getText();
        String hometown = custHometown.getText();
        int yearOfBirth = Integer.parseInt(custYOB.getText());
        String mobile = custMobile.getText();
        String email = custEmail.getText();
        
        if(name.isEmpty() || id.isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please insert name and id");
            alert.showAndWait();
            return;
        }
        
        if(isInEditMode){
            Customer c = new Customer(name,id,hometown,yearOfBirth,mobile,email);
            if(dbHandler.updateCustomerInfo(c)){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Update customer info successfully");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Failed to update customer info");
                alert.showAndWait();
            }
            
            return;
        }
        
        String sql = "INSERT INTO CUSTOMER VALUES(N'"+name+"','"+id+"',N'"+hometown+"',"+yearOfBirth+",'"+mobile+"','"+email+"')";
        
        if(dbHandler.execute(sql)){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("SUCCESS");
            alert.showAndWait();
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("FAILED");
            alert.showAndWait();
        }
        
    }
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage)rootPane.getScene().getWindow();
        stage.close();
    }
    
    public void inflateUI(Customer c){
        custName.setText(c.getName());
        custID.setText(c.getId());
        custHometown.setText(c.getHometown());
        custYOB.setText(String.valueOf(c.getYearOfBirth()));
        custMobile.setText(c.getMobile());
        custEmail.setText(c.getEmail());
        
        //Disable ID field
        custID.setEditable(false);
        isInEditMode = true;
    }
    
}
