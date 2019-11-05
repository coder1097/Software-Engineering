package hotelmanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import hotelmanagement.DB.DBHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Laptop
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private JFXTextField custName;
    @FXML
    private JFXTextField custID;
    @FXML
    private JFXTextField custHometown;
    @FXML
    private JFXTextField custYOB;
    @FXML
    private JFXTextField cusMobile;
    @FXML
    private JFXTextField cusEmail;
    @FXML
    private JFXButton saveBnt;
    @FXML
    private JFXButton cancelBnt;
    
    DBHandler dbHandler;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbHandler = new DBHandler();
    }    

    @FXML
    private void addCustomer(ActionEvent event) {
    }

    @FXML
    private void cancel(ActionEvent event) {
    }
    
}
