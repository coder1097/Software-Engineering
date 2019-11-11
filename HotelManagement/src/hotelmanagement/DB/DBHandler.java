package hotelmanagement.DB;

import hotelmanagement.ui.main.Room;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DBHandler {
    
    private static DBHandler dbHandler = null;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=HotelManagement;user=sa;password=sa";
    private static Connection conn = null;
    private static PreparedStatement pst = null;
    private static DatabaseMetaData dbm = null;
    private static ResultSet rs = null;
    
    private DBHandler(){
        createConnection();
        createCustomerTable();
        createRoomTable();
        createBillTable();
    }
    
    public static DBHandler getInstance(){
        if(dbHandler == null){
            dbHandler = new DBHandler();
        }
        return dbHandler;
    }

    private void createConnection(){
        try{
            conn = DriverManager.getConnection(DB_URL);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void createCustomerTable(){
        String sql = "CREATE TABLE CUSTOMER(\n"+
                     "name nvarchar(256) NOT NULL,\n"+
                     "id varchar(20),\n"+
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
    
    private void createRoomTable(){
        String sql = "CREATE TABLE ROOM(\n"+
                     "id int,\n"+
                     "type varchar(10) DEFAULT('NORMAL') CHECK(type IN('NORMAL','VIP')),\n"+
                     "bedrooms int CHECK(bedrooms IN(2,4)),\n"+
                     "isAvailable bit DEFAULT(1),\n"+
                     "PRIMARY KEY(id))";
        try {
            dbm = conn.getMetaData();
            rs = dbm.getTables(null, null, "ROOM", null);
            
            if(rs.next()){
                System.out.println("Table is already existed");
            }else{
                pst = conn.prepareStatement(sql);
                pst.execute();
                insertRooms();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void insertRooms() {
        String sql = "INSERT INTO ROOM VALUES(?,?,?,?)";
        Room r;
        try {
            pst = conn.prepareStatement(sql);
            
            for(int i=1; i<=5; i++){
                for(int j=1; j<=4; j++){
                    int id = i*100+j;
                    
                    if(j == 1)
                        r = new Room(id,"VIP",4);
                    else
                        r = new Room(id,"NORMAL",2);
                    
                    pst.setInt(1, r.getId());
                    pst.setString(2, r.getType());
                    pst.setInt(3, r.getBedrooms());
                    pst.setBoolean(4, true);

                    pst.addBatch();
                }  
            }
            
            pst.executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void createBillTable(){
        String sql = "CREATE TABLE BILL(\n"+
                     "id int identity(1,1),\n"+
                     "customerID varchar(20),\n"+
                     "roomID int,\n"+
                     "roomType varchar(10) DEFAULT('NORMAL') CHECK(roomType IN('NORMAL','VIP')),\n"+
                     "checkIn varchar(25),\n"+
                     "checkOut varchar(25) default(null),\n"+
                     "fee int default(0),\n"+
                     "CONSTRAINT FK_BILL_CUSTOMERID_CUSTOMER FOREIGN KEY(customerID) REFERENCES CUSTOMER(id),\n"+
                     "CONSTRAINT FK_BILL_ROOMID_ROOM FOREIGN KEY(roomID) REFERENCES ROOM(id),\n"+
                     "PRIMARY KEY(id))";
        try {
            dbm = conn.getMetaData();
            rs = dbm.getTables(null, null, "BILL", null);
            
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
    
    public boolean execute(String sql){
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public ResultSet executeQuery(String sql){
        ResultSet rs;
        
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return rs;
    }

    
}
