/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagement.ui.main;

import hotelmanagement.DB.DBHandler;
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
public class MainController implements Initializable {

    @FXML
    private TableView<Room> tableView;
    @FXML
    private TableColumn<Room, String> idCol;
    @FXML
    private TableColumn<Room, String> typeCol;
    @FXML
    private TableColumn<Room, Integer> bedroomCol;
    @FXML
    private TableColumn<Room, String> statusCol;
    
    private ObservableList<Room> roomList;
    private DBHandler dbHandler;
    private Alert alert;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbHandler = DBHandler.getInstance();
        initCols();
    }

    private void initCols() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        bedroomCol.setCellValueFactory(new PropertyValueFactory<>("bedrooms"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("sStatus"));
    }

    @FXML
    private void loadRoomData(ActionEvent event) {
        //Clear previous content
        roomList = FXCollections.observableArrayList();
        tableView.getItems().clear();
        
        String sql = "SELECT * FROM ROOM";
        ResultSet rs = dbHandler.executeQuery(sql);
        
        try {
            while(rs.next()){
                int id = rs.getInt("id");
                String type = rs.getString("type");
                int bedrooms = rs.getInt("bedrooms");
                boolean isAvailable = rs.getBoolean("isAvailable");
                
                roomList.add(new Room(id,type,bedrooms,isAvailable));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.setItems(roomList);
    }

    @FXML
    private void handleCheckIn(ActionEvent event) {
        load("/hotelmanagement/ui/docheckin/check_in.fxml","Check-In");
    }

    @FXML
    private void handleCheckOut(ActionEvent event) {
        load("/hotelmanagement/ui/docheckout/check_out.fxml","Check-Out");
    }

    @FXML
    private void addCustomer(ActionEvent event) {
        load("/hotelmanagement/ui/addcustomer/add_customer.fxml","Add Customer");
    }

    @FXML
    private void showCustomerList(ActionEvent event) {
        load("/hotelmanagement/ui/viewcustomers/view_customers.fxml","Customer List");
    }

    @FXML
    private void showBookingHistory(ActionEvent event) {
        load("/hotelmanagement/ui/viewbookinghistory/booking_history.fxml","Booking History");
    }
    
    private void load(String loc, String title){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteRoom(ActionEvent event) {
        Room roomToDelete = tableView.getSelectionModel().getSelectedItem();
        if(roomToDelete != null){
            Boolean status = dbHandler.executeRoomDeletion(roomToDelete);
            if(status){
                roomList.remove(roomToDelete);
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
