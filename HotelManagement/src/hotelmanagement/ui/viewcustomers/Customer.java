package hotelmanagement.ui.viewcustomers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private final SimpleStringProperty name;
    private final SimpleStringProperty id;
    private final SimpleStringProperty hometown;
    private final SimpleIntegerProperty yearOfBirth;
    private final SimpleStringProperty mobile;
    private final SimpleStringProperty email;
    
    public Customer(String name, String id, String hometown, int yearOfBirth, String mobileNumber, String email){
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
        this.hometown = new SimpleStringProperty(hometown);
        this.yearOfBirth = new SimpleIntegerProperty(yearOfBirth);
        this.mobile = new SimpleStringProperty(mobileNumber);
        this.email = new SimpleStringProperty(email);
    }
    
    public String getName(){
        return name.get();
    }
   
    
    public String getId(){
        return id.get();
    }
    
    
    public String getHometown(){
        return hometown.get();
    }
    
    
    public int getYearOfBirth(){
        return yearOfBirth.get();
    }
    
    
    public String getMobile(){
        return mobile.get();
    }
    
    
    public String getEmail(){
        return email.get();
    }
    

    
}
