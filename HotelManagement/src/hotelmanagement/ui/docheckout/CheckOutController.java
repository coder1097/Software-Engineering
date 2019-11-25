
package hotelmanagement.ui.docheckout;

import com.jfoenix.controls.JFXTextField;
import hotelmanagement.DB.DBHandler;
import java.net.URL;
import java.sql.Connection;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
        String checkOut = checkOutDT.format(formatter);
        updateFeeAndCheckOutCol(rID,checkOut,fee);
        //Set room status to 'Available'
        setRoomStatus(rID,true);
        //Print bill
        printBill();
        
    }
    
    private LocalDateTime getTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String checkIn = now.format(formatter);
        return LocalDateTime.parse(checkIn,formatter);
    }

    private String getCheckIn(int roomID) {
        String sql = "SELECT CONVERT(varchar(20),checkIn,20) AS checkIn FROM BILL WHERE roomID="+roomID+" AND checkOut IS NULL";
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
    //Room Fee Algorithm
    private int computeFee(int days, int hours, int mins, boolean isVIPRoom) {
        int fee=0;
        
        int fee_by_hours = computeFeeByHours(hours,mins,isVIPRoom);
        //Compute total fee
        if(isVIPRoom)
            fee = fee_by_hours + days*450000;
        else
            fee = fee_by_hours + days*350000;
        
        return fee;
    }
    
    private int computeFeeByHours(int hours, int mins, boolean isVIPRoom){
        int fee=0;
        
        if(hours==0 || (hours==1 && mins <= 15)){
            fee=60000;
        }else if(hours==1 || (hours==2 && mins <= 15)){
            fee=120000;
        }else if(hours < 6 || (hours==6 && mins <= 15)){
            if(mins > 15)
                fee = 120000 + (hours-1)*20000;
            else
                fee = 120000 + (hours-2)*20000;
        }else{
            if(hours < 12 || (hours==12 && mins <= 15)){
                if(isVIPRoom) 
                    fee=350000;
                else 
                    fee=250000;
            }else{
                if(isVIPRoom) 
                    fee=450000;
                else 
                    fee=350000;
            }
        }
        
        return fee;
    }

    private void updateFeeAndCheckOutCol(int rID, String checkOut, int fee) {
        String feeUpdateSQL = "UPDATE BILL SET fee="+fee+" WHERE roomID="+rID+" AND checkOut IS NULL";
        String checkOutUpdateSQL = "UPDATE BILL SET checkOut=CAST('"+checkOut+"' AS DATETIME) WHERE roomID="+rID+" AND checkOut IS NULL";
        
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
        try {
            Connection conn = dbHandler.getConnection();
            
            JasperDesign billDesign = JRXmlLoader.load("D:\\SE\\HotelManagement\\src\\hotelmanagement\\ui\\docheckout\\bill.jrxml");
            String billPrintSQL = "SELECT TOP 1 * FROM BILL ORDER BY checkOut DESC";
            JRDesignQuery updateSQL = new JRDesignQuery();
            updateSQL.setText(billPrintSQL);
            billDesign.setQuery(updateSQL);
            JasperReport bill = JasperCompileManager.compileReport(billDesign);
            JasperPrint bill_print = JasperFillManager.fillReport(bill,null,conn);
            JasperViewer.viewReport(bill_print,false);
        } catch (JRException ex) {
            Logger.getLogger(CheckOutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void setRoomStatus(int rID, boolean isAvailable) {
        String roomStatusUpdateSQL = "UPDATE ROOM SET isAvailable=1 WHERE id="+rID;
        
        if(dbHandler.execute(roomStatusUpdateSQL)){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Update room status successfully");
            alert.showAndWait();
        }else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed to update room status");
            alert.showAndWait();
        }
    }

    

    
}
