package hotelmanagement.ui.viewrooms;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Laptop
 */
public class Room {
    private final SimpleStringProperty id;
    private final SimpleStringProperty type;
    private final SimpleIntegerProperty bedrooms;
    private final SimpleBooleanProperty status;
    
    public Room(String id, String type, int bedrooms){
        this(id,type,bedrooms,true);
    }
    
    public Room(String id, String type, int bedrooms, boolean status){
        this.id = new SimpleStringProperty(id);
        this.type = new SimpleStringProperty(type);
        this.bedrooms = new SimpleIntegerProperty(bedrooms);
        this.status = new SimpleBooleanProperty(status);
    }
    
    public String getId(){
        return id.get();
    }
    
    public String getType(){
        return type.get();
    }
    
    public int getBedrooms(){
        return bedrooms.get();
    }
    
    public boolean getStatus(){
        return status.get();
    }
}
