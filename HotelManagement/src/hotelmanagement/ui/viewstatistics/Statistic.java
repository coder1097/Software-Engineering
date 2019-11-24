/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagement.ui.viewstatistics;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

/**
 *
 * @author Laptop
 */
public class Statistic {
    private final SimpleIntegerProperty roomId;
    private final SimpleIntegerProperty numCheckIns;
    private final SimpleLongProperty revenue;
    
    public Statistic(int roomId, int numCheckIns, long revenue){
        this.roomId = new SimpleIntegerProperty(roomId);
        this.numCheckIns = new SimpleIntegerProperty(numCheckIns);
        this.revenue = new SimpleLongProperty(revenue);
    }
    
    public int getRoomId(){
        return roomId.get();
    }
   
    public int getNumCheckIns(){
        return numCheckIns.get();
    }
    
    public long getRevenue(){
        return revenue.get();
    }
    
}
