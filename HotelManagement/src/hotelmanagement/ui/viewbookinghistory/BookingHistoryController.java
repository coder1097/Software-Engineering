
package hotelmanagement.ui.viewbookinghistory;

import hotelmanagement.DB.DBHandler;
import hotelmanagement.ui.main.Room;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Laptop
 */
public class BookingHistoryController implements Initializable {

    @FXML
    private TableView<Bill> tableView;
    @FXML
    private TableColumn<Bill, Integer> idCol;
    @FXML
    private TableColumn<Bill, String> customerIdCol;
    @FXML
    private TableColumn<Bill, Integer> roomIdCol;
    @FXML
    private TableColumn<Bill, String> roomTypeCol;
    @FXML
    private TableColumn<Bill, String> checkInCol;
    @FXML
    private TableColumn<Bill, String> checkOutCol;
    @FXML
    private TableColumn<Bill, Integer> feeCol; 
    
    private ObservableList<Bill> billList;
    private DBHandler dbHandler;
    private Alert alert;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbHandler = DBHandler.getInstance();
        billList = FXCollections.observableArrayList();
        initCols();
        loadBills();
    }

    private void initCols() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        roomIdCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        feeCol.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    private void loadBills() {
        String sql = "SELECT * FROM BILL";
        ResultSet rs = dbHandler.executeQuery(sql);
        
        try {
            while(rs.next()){
                int id = rs.getInt("id");
                String customerID = rs.getString("customerID");
                int roomID = rs.getInt("roomID");
                String roomType = rs.getString("roomType");
                String checkIn = rs.getString("checkIn");
                String checkOut = rs.getString("checkOut");
                int fee = rs.getInt("fee");

                billList.add(new Bill(id,customerID,roomID,roomType,checkIn,checkOut,fee));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookingHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.setItems(billList);
    }

    @FXML
    private void deleteBill(ActionEvent event) {
        Bill billToDelete = tableView.getSelectionModel().getSelectedItem();
        if(billToDelete != null){
            Boolean status = dbHandler.executeBillDeletion(billToDelete);
            if(status){
                billList.remove(billToDelete);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("DONE");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Failed to delete");
                alert.showAndWait();
            }
        }
    }
    
}
