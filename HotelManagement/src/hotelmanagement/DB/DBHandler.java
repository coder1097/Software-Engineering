package hotelmanagement.DB;

import hotelmanagement.ui.viewrooms.Room;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHandler {
    
    static{
        List<Room> rooms = new ArrayList<Room>();
        //Floor 1
        rooms.add(new Room("101","VIP",4));
        rooms.add(new Room("102","NORMAL",2));
        rooms.add(new Room("103","NORMAL",2));
        rooms.add(new Room("104","NORMAL",2));
        
        //Floor 2
        rooms.add(new Room("201","VIP",4));
        rooms.add(new Room("202","NORMAL",2));
        rooms.add(new Room("203","NORMAL",2));
        rooms.add(new Room("204","NORMAL",2));
        
        //Floor 3
        rooms.add(new Room("301","VIP",4));
        rooms.add(new Room("302","NORMAL",2));
        rooms.add(new Room("303","NORMAL",2));
        rooms.add(new Room("304","NORMAL",2));
        
        //Floor 4
        rooms.add(new Room("401","VIP",4));
        rooms.add(new Room("402","NORMAL",2));
        rooms.add(new Room("403","NORMAL",2));
        rooms.add(new Room("404","NORMAL",2));
        
        //Floor 5
        rooms.add(new Room("501","VIP",4));
        rooms.add(new Room("502","NORMAL",2));
        rooms.add(new Room("503","NORMAL",2));
        rooms.add(new Room("504","NORMAL",2));
    }
    
    private static DBHandler handler;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=HotelManagement;user=sa;password=sa";
    private static Connection conn = null;
    private static PreparedStatement pst = null;
    private static DatabaseMetaData dbm = null;
    private static ResultSet rs = null;
    private static List<Room> rooms;
    
    static{
        rooms = new ArrayList<>();
        //Floor 1
        rooms.add(new Room("101","VIP",4));
        rooms.add(new Room("102","NORMAL",2));
        rooms.add(new Room("103","NORMAL",2));
        rooms.add(new Room("104","NORMAL",2));
        
        //Floor 2
        rooms.add(new Room("201","VIP",4));
        rooms.add(new Room("202","NORMAL",2));
        rooms.add(new Room("203","NORMAL",2));
        rooms.add(new Room("204","NORMAL",2));
        
        //Floor 3
        rooms.add(new Room("301","VIP",4));
        rooms.add(new Room("302","NORMAL",2));
        rooms.add(new Room("303","NORMAL",2));
        rooms.add(new Room("304","NORMAL",2));
        
        //Floor 4
        rooms.add(new Room("401","VIP",4));
        rooms.add(new Room("402","NORMAL",2));
        rooms.add(new Room("403","NORMAL",2));
        rooms.add(new Room("404","NORMAL",2));
        
        //Floor 5
        rooms.add(new Room("501","VIP",4));
        rooms.add(new Room("502","NORMAL",2));
        rooms.add(new Room("503","NORMAL",2));
        rooms.add(new Room("504","NORMAL",2));
    }
    
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
                     "name nvarchar(256) NOT NULL,\n"+
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

    private void insertRooms() {
        String sql = "INSERT INTO ROOM VALUES(?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            
            for(Room r:rooms){
                pst.setString(1, r.getId());
                pst.setString(2, r.getType());
                pst.setInt(3, r.getBedrooms());
                pst.setBoolean(4, true);
                        
                pst.addBatch();
            }
            
            pst.executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
    }
}
