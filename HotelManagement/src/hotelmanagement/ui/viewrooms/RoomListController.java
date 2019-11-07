/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagement.ui.viewrooms;

import hotelmanagement.DB.DBHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Laptop
 */
public class RoomListController implements Initializable {

    @FXML
    private TableView<Room> tableView;
    @FXML
    private TableColumn<Room, String> idCol;
    @FXML
    private TableColumn<Room, String> typeCol;
    @FXML
    private TableColumn<Room, Integer> bedroomCol;
    @FXML
    private TableColumn<Room, Boolean> availabilityCol;
    
    private ObservableList<Room> roomList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        roomList = FXCollections.observableArrayList();
        initCols();
        loadData();
    }    

    private void initCols() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        bedroomCol.setCellValueFactory(new PropertyValueFactory<>("bedrooms"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadData() {
        DBHandler dbHandler = new DBHandler();
        String sql = "SELECT * FROM ROOM";
        ResultSet rs = dbHandler.executeQuery(sql);
        
        try {
            while(rs.next()){
                String id = rs.getString("id");
                String type = rs.getString("type");
                int bedrooms = rs.getInt("bedrooms");
                boolean isAvailable = rs.getBoolean("isAvailable");
                
                roomList.add(new Room(id,type,bedrooms,isAvailable));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.getItems().setAll(roomList);
    }
    
}
