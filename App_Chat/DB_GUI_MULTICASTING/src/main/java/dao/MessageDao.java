package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JTextArea;

import model.tinnhan;

public class MessageDao {
    public static void Insert(String nd, String matk) {
    	try {
            //Kết nối dể lưu tin nhắn
            Connection conn = Mypublic.getConnection();
            String sql = "insert into TINNHAN(noidung,matk) values (N'"+nd+"','"+matk+"')";
            // Tạo statement
            PreparedStatement stmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            // Thêm tin nhắn vào csdl
            stmt.executeUpdate();
            
            ResultSet res = stmt.getGeneratedKeys();
            while (res.next())
            System.out.println("Generated key: " + res.getInt(1));
            
            
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void SelectTN(String userName, JTextArea txt) { // lấy dữ liệu từ server và hiển thị lại lịch sử chat
        
    	String sql = "select noidung, MaTK from tinnhan limit 100";
		PreparedStatement pst;
		try {
			// kết nối tới csdl "app_chat"
			pst = Mypublic.getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				if(rs.getString(2).trim().equals(userName))
				
            		txt.append("Bạn: "+rs.getString(1)+"\n");
				
            	else {
            		txt.append(rs.getString(2)+": "+rs.getString(1)+"\n");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}