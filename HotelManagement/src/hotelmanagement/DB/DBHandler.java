package hotelmanagement.DB;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHandler {
    private static DBHandler handler;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=HotelManagement;user=sa;password=sa";
    private static Connection conn = null;
    private static PreparedStatement pst = null;
    private static DatabaseMetaData dbm = null;
    private static ResultSet rs = null;

    public DBHandler(){
        createConnection();
        createCustomerTable();
        createRoomTable();
    }
    
 

    void createConnection(){
        try{
            conn = DriverManager.getConnection(DB_URL);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    void createCustomerTable(){
        String sql = "CREATE TABLE CUSTOMER(\n"+
                     "name nvarchar(256),\n"+
                     "id varchar(12),\n"+
                     "hometown nvarchar(256),\n"+
                     "yearOfBirth int,\n"+
                     "mobile varchar(20),\n"+
                     "email varchar(256),\n"+
                     "PRIMARY KEY(id))";
        try {
            dbm = conn.getMetaData();
            rs = dbm.getTables(null, null, "CUSTOMER", null);
            if(rs.next()){
                System.out.println("Table is already existed");
            }else{
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void createRoomTable(){
        String sql = "CREATE TABLE ROOM(\n"+
                     "id char(4),\n"+
                     "type int,\n"+
                     "no_of_bedrooms int,\n"+
                     "isAvailable bit,\n"+
                     "PRIMARY KEY(id))";
        try {
            dbm = conn.getMetaData();
            rs = dbm.getTables(null, null, "ROOM", null);
            
            if(rs.next()){
                System.out.println("Table is already existed");
            }else{
                pst = conn.prepareStatement(sql);
                pst.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
