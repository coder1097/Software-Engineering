
package hotelmanagement.ui.viewstatistics;

import hotelmanagement.DB.DBHandler;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Laptop
 */
public class ViewStatisticsController implements Initializable {

    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private TableView<Statistic> tbView;
    @FXML
    private TableColumn<Statistic, Integer> roomIdCol;
    @FXML
    private TableColumn<Statistic, Integer> numCheckInCol;
    @FXML
    private TableColumn<Statistic, Long> revenueCol;
    
    private ObservableList<Statistic> statistics;
    private DBHandler dbHandler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbHandler = DBHandler.getInstance();
        statistics = FXCollections.observableArrayList();
        initCols();
    }

    private void initCols() {
        roomIdCol.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        numCheckInCol.setCellValueFactory(new PropertyValueFactory<>("numCheckIns"));
        revenueCol.setCellValueFactory(new PropertyValueFactory<>("revenue"));
    }

    @FXML
    private void loadStatistics(ActionEvent event) {
        String fromD = fromDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String toD = toDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sql =    "SELECT r.id as roomId, COUNT(b.roomID) as numCheckIns,\n"+
                        "CASE\n"+	
                        "        WHEN SUM(fee) IS NULL then 0\n"+
                        "        ELSE SUM(fee)\n"+
                        "END AS revenue\n"+
                        "FROM BILL b \n"+
                        "RIGHT OUTER JOIN ROOM r ON b.roomID=r.id\n"+
                        "WHERE CAST(checkIn AS DATE) BETWEEN '"+fromD+"' AND '"+toD+"' OR checkIn IS NULL\n"+ 
                        "GROUP BY r.id\n"+
                        "ORDER BY r.id";
        ResultSet rs = dbHandler.executeQuery(sql);
        
        try {
            while(rs.next()){
                int id = rs.getInt("roomId");
                int numCheckIns = rs.getInt("numCheckIns");
                long revenue = rs.getLong("revenue");

                statistics.add(new Statistic(id,numCheckIns,revenue));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewStatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tbView.setItems(statistics);
    }

    
    
}
