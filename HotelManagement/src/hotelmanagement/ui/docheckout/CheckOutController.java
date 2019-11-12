
package hotelmanagement.ui.docheckout;

import com.jfoenix.controls.JFXTextField;
import hotelmanagement.DB.DBHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

public class CheckOutController implements Initializable {

    @FXML
    private JFXTextField roomID;
    private DBHandler dbHandler;
    private Alert alert;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbHandler = DBHandler.getInstance();
    }    

    @FXML
    private void returnRoom(ActionEvent event) {
        //Get checkIn
        int rID = Integer.parseInt(roomID.getText());
        
        String checkIn = getCheckIn(rID);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime checkInDT = LocalDateTime.parse(checkIn,formatter);
        //checkOut
        LocalDateTime checkOutDT = getTime();
        //Find days and hours
        Duration dt_diff = Duration.between(checkInDT, checkOutDT);
        int days = (int)dt_diff.toDays();
        int hours = (int)dt_diff.minusDays(days).toHours();
        int mins = (int)dt_diff.minusDays(days).minusHours(hours).toMinutes();
        //Compute fee
        boolean isVIPRoom = rID%10 == 1 ? true:false;
        int fee = computeFee(days,hours,mins, isVIPRoom);
        //Update checkOut and fee
        String checkOut = checkOutDT.toString();
        updateFeeAndCheckOutCol(rID,checkOut,fee);
        //Print bill
        printBill();
        //Set room status to 'Available'
        setRoomStatus(true);
    }
    
    private LocalDateTime getTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String checkIn = now.format(formatter);
        return LocalDateTime.parse(checkIn,formatter);
    }

    private String getCheckIn(int roomID) {
        String sql = "SELECT checkIn FROM BILL WHERE roomID = "+roomID+" AND checkOut = null";
        ResultSet rs = dbHandler.executeQuery(sql);
        String checkIn = null;
        try {
            if(rs.next()){
                checkIn = rs.getString("checkIn");
            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Failed to retrieve checkIn value");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkIn;
    }

    private int computeFee(int days, int hours, int mins, boolean isVIPRoom) {
        int fee=0;
        
        int fee_by_hours = computeFeeByHours(hours,mins,isVIPRoom);
        //Compute total fee
        if(isVIPRoom)
            fee = fee_by_hours + days*450;
        else
            fee = fee_by_hours + days*350;
        
        return fee;
    }
    
    private int computeFeeByHours(int hours, int mins, boolean isVIPRoom){
        int fee=0;
        
        if(hours==0 || (hours==1 && mins <= 15)){
            fee=60;
        }else if(hours==1 || (hours==2 && mins <= 15)){
            fee=120;
        }else if(hours < 6 || (hours==6 && mins <= 15)){
            if(mins > 15)
                fee = 120 + (hours-1)*20;
            else
                fee = 120 + (hours-2)*20;
        }else{
            if(hours < 12 || (hours==12 && mins <= 15)){
                if(isVIPRoom) fee=350;
                else fee=250;
            }else{
                if(isVIPRoom) fee=450;
                else fee=350;
            }
        }
        
        return fee;
    }

    private void updateFeeAndCheckOutCol(int rID, String checkOut, int fee) {
        String feeUpdateSQL = "UPDATE BILL SET fee="+fee+" WHERE roomID="+rID+" AND checkOut=null";
        String checkOutUpdateSQL = "UPDATE BILL SET checkOut='"+checkOut+"' WHERE roomID="+rID+" AND checkOut=null";
        
        if(dbHandler.execute(feeUpdateSQL) && dbHandler.execute(checkOutUpdateSQL)){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Update fee and checkOut successfully");
            alert.showAndWait();
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed to update fee and checkOut");
            alert.showAndWait();
        }
    }

    private void printBill() {
        
    }

    private void setRoomStatus(boolean available) {
        
    }

    

    
}
