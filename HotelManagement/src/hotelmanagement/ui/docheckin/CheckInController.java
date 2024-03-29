/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagement.ui.docheckin;

import com.jfoenix.controls.JFXTextField;
import hotelmanagement.DB.DBHandler;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Laptop
 */
public class CheckInController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField customerID;
    @FXML
    private JFXTextField roomID;
    
    private DBHandler dbHandler;
    private Alert alert;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbHandler = DBHandler.getInstance();
    }    

    @FXML
    private void bookRoom(ActionEvent event) {
        String custID = customerID.getText();
        int rID = Integer.parseInt(roomID.getText());
        String roomType = getRoomType(rID);
        String checkIn = getTime();
        
        String billTableInsertionSQL = "INSERT INTO BILL(customerID, roomID,roomType,checkIn) VALUES('"+custID+"',"+rID+",'"+roomType+"',"
                + "CAST('"+checkIn+"'AS DATETIME))";
        String roomUpdateStatusSQL = "UPDATE ROOM SET isAvailable = 0 WHERE id="+rID;
        
        if(dbHandler.execute(billTableInsertionSQL) && dbHandler.execute(roomUpdateStatusSQL)){
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
    
    private String getRoomType(int roomID){
        if(roomID % 10 == 1) return "VIP";
        else return "NORMAL";
    }
    
    private String getTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    @FXML
    private void cancelBooking(ActionEvent event) {
        Stage stage = (Stage)rootPane.getScene().getWindow();
        stage.close();
    }
    
}
