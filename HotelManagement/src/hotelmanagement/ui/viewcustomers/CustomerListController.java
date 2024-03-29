package hotelmanagement.ui.viewcustomers;

import com.jfoenix.controls.JFXTextField;
import hotelmanagement.DB.DBHandler;
import hotelmanagement.ui.addcustomer.CustomerAddController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Laptop
 */
public class CustomerListController implements Initializable {

    @FXML
    private TableView<Customer> tableView;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TableColumn<Customer, String> idCol;
    @FXML
    private TableColumn<Customer, String> hometownCol;
    @FXML
    private TableColumn<Customer, Integer> yobCol;
    @FXML
    private TableColumn<Customer, String> mobileCol;
    @FXML
    private TableColumn<Customer, String> emailCol;
    @FXML
    private JFXTextField custSearch;
    
    private ObservableList<Customer> customerList;
    private DBHandler dbHandler;
    private Alert alert;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbHandler = DBHandler.getInstance();
        customerList = FXCollections.observableArrayList();
        initCols();
        loadData();
    }    

    private void initCols() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        hometownCol.setCellValueFactory(new PropertyValueFactory<>("hometown"));
        yobCol.setCellValueFactory(new PropertyValueFactory<>("yearOfBirth"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadData() {
        String sql = "SELECT * FROM CUSTOMER";
        ResultSet rs = dbHandler.executeQuery(sql);
        
        try {
            while(rs.next()){
                String name = rs.getString("name");
                String id = rs.getString("id");               
                String hometown = rs.getString("hometown");
                int yearOfBirth = rs.getInt("yearOfBirth");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                             
                customerList.add(new Customer(name,id,hometown,yearOfBirth,mobile,email));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.setItems(customerList);

    }

    @FXML
    private void searchCustomer(ActionEvent event) {
        //Clear previous content
        customerList = FXCollections.observableArrayList();
        tableView.getItems().clear();
        
        String customerID = custSearch.getText();
        String sql = "SELECT * FROM CUSTOMER WHERE id='"+customerID+"'";
        ResultSet rs = dbHandler.executeQuery(sql);
        
        try {
            while(rs.next()){
                String name = rs.getString("name");
                String id = rs.getString("id");               
                String hometown = rs.getString("hometown");
                int yearOfBirth = rs.getInt("yearOfBirth");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                             
                customerList.add(new Customer(name,id,hometown,yearOfBirth,mobile,email));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.setItems(customerList);

    }

    @FXML
    private void editCustomerInfo(ActionEvent event) {
        Customer customerToEdit = tableView.getSelectionModel().getSelectedItem();
        if(customerToEdit != null){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotelmanagement/ui/addcustomer/add_customer.fxml"));
                Parent parent = loader.load();
                CustomerAddController customerAddC = (CustomerAddController)loader.getController();
                customerAddC.inflateUI(customerToEdit);
                
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setTitle("Edit Customer Info");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(CustomerListController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
