/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagement.ui.viewbookinghistory;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Laptop
 */
public class Bill {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty customerID;
    private final SimpleIntegerProperty roomID;
    private final SimpleStringProperty roomType;
    private final SimpleStringProperty checkIn;
    private final SimpleStringProperty checkOut;
    private final SimpleIntegerProperty fee;
    
    public Bill(int id, String customerID, int roomID, String roomType, String checkIn, String checkOut, int fee){
        this.id = new SimpleIntegerProperty(id);
        this.customerID = new SimpleStringProperty(customerID);
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomType = new SimpleStringProperty(roomType);
        this.checkIn = new SimpleStringProperty(checkIn);
        this.checkOut = new SimpleStringProperty(checkOut);
        this.fee = new SimpleIntegerProperty(fee);
    }
    
    public int getId(){
        return id.get();
    }
   
    
    public String getCustomerID(){
        return customerID.get();
    }
    
    
    public int getRoomID(){
        return roomID.get();
    }
    
    
    public String getRoomType(){
        return roomType.get();
    }
    
    
    public String getCheckIn(){
        return checkIn.get();
    }
    
    
    public String getCheckOut(){
        return checkOut.get();
    }
    
    public int getFee(){
        return fee.get();
    }
}
