/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplegui;
import java.sql.*;
/**
 *
 * @author DELL
 */
public class DatabaseConnection {
    Connection con;
    private static DatabaseConnection dbc;
    private DatabaseConnection(){
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
            
            con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/aptiv","root","root");
            System.out.println("Connection established");
        }catch(Exception e){
           System.out.println(e);
        }  
    }
    
    
    public static DatabaseConnection getDatabaseConnection(){
        if(dbc == null){
            dbc = new DatabaseConnection();
        }
        return dbc;
    }
    
    
    public Connection getConnection(){
        return con;
    }
    
    
}
