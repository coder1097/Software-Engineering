package hotelmanagement.ui.main;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Laptop
 */
public class Room {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty type;
    private final SimpleIntegerProperty bedrooms;
    private final SimpleStringProperty sStatus;
    private final boolean bStatus;
    
    public Room(int id, String type, int bedrooms){
        this(id,type,bedrooms,true,null);
    }
    
    public Room(int id, String type, int bedrooms, boolean bStatus){
        this(id,type,bedrooms,bStatus,null);
    }
    
    public Room(int id, String type, int bedrooms, boolean bStatus, String sStatus){
        this.id = new SimpleIntegerProperty(id);
        this.type = new SimpleStringProperty(type);
        this.bedrooms = new SimpleIntegerProperty(bedrooms);
        this.bStatus = bStatus;
        
        if(bStatus)
            this.sStatus = new SimpleStringProperty("Available");
        else
            this.sStatus = new SimpleStringProperty("Check-In");
    }
    
    public int getId(){
        return id.get();
    }
    
    public String getType(){
        return type.get();
    }
    
    public int getBedrooms(){
        return bedrooms.get();
    }
    
    public String getSStatus(){
        return sStatus.get();
    }
    
    
}
