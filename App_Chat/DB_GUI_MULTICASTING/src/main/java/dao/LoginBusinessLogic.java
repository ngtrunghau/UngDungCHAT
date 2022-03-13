package dao;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import model.taikhoan;

public class LoginBusinessLogic {
	public LoginBusinessLogic() {}
	
//Kết nối cơ sở dữ liệu tài khoản
	public static boolean checkUser(String username, String password) {
		String sql = "select count(*) from taikhoan where MaTK = '"+username+"' and matkhau = '"+password+"'";
		PreparedStatement pst;
		try {
			pst = Mypublic.getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				if(rs.getInt(1) == 1) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
