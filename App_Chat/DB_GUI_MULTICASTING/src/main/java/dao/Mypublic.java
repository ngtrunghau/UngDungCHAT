package dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Mypublic {
    private static String DB_URL = "jdbc:mysql://localhost:3306/app_chat";
    private static String DB_USER_NAME = "root";
    private static String DB_PASSWORD = "";
    private static Connection conn=null;
    
    //Kết nối csdl
    private static NavigableMap<String,String> listUser = new TreeMap<String, String>();
    
    public static String server_address;

    public static Connection getConnection() {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL,DB_USER_NAME,DB_PASSWORD);
            //conn.setAutoCommit(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }
    
}
