/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagement.ui.docheckout;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Laptop
 */
public class CheckOutController implements Initializable {

    @FXML
    private JFXTextField roomID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void returnRoom(ActionEvent event) {
        //Get checkIn
        String checkIn = getCheckIn();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime checkInDT = LocalDateTime.parse(checkIn,formatter);
        //checkOut
        LocalDateTime checkOutDT = getTime();
        //Find days and hours
        Duration dt_diff = Duration.between(checkInDT, checkOutDT);
        int days = (int)dt_diff.toDays();
        int hours = (int)dt_diff.minusDays(days).toHours();
        //Compute fee
        int fee = computeFee(days,hours);
        //Update checkOut and fee
        update(checkOutDT,fee);
        //Print bill
        printBill();
        //Set room status to 'Available'
        setRoomStatus("Available");
    }
    
    private LocalDateTime getTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String checkIn = now.format(formatter);
        return LocalDateTime.parse(checkIn,formatter);
    }

    private String getCheckIn() {
        return "";
    }

    private int computeFee(int days, int hours) {
        return 0;
    }

    private void update(LocalDateTime checkOutDT, int fee) {
        
    }

    private void printBill() {
        
    }

    private void setRoomStatus(String available) {
        
    }

    
}
