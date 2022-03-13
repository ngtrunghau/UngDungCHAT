package Client;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Server.Server;
import dao.MessageDao;
import dao.Mypublic;

public class Client extends JFrame implements ActionListener{
	private JButton send,clear,exit,login,logout;
    private JPanel p_login,p_chat;
    private JTextField nick,nick1,message;
    private JTextArea msg,online;
    private JPasswordField mk;

    private Socket client;
    private DataStream dataStream;
    private DataOutputStream dos;
	private DataInputStream dis;
	public Client(){
		super("App Chat : Client");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				exit();
			}
		});
		setSize(800, 400);
		addItem();
		
		setVisible(true);
		
	}
//Tạo GUI
	private void addItem() {
		setLayout(new BorderLayout());

		exit = new JButton("Thoát");
		exit.addActionListener(this);
		send = new JButton("Gửi");
		send.addActionListener(this);
		clear = new JButton("Xóa");
		clear.addActionListener(this);
		login= new JButton("Đăng nhập");
		login.addActionListener(this);
		logout= new JButton("Thoát");
		logout.addActionListener(this);

		p_chat = new JPanel();
		p_chat.setLayout(new BorderLayout());

		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		nick = new JTextField(20);
		p1.add(new JLabel("Tên tài khoản : "));
		p1.add(nick);
		p1.add(exit);

		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());

		JPanel p22 = new JPanel();
		p22.setLayout(new FlowLayout(FlowLayout.CENTER));
		p22.add(new JLabel("Danh sách hoạt động"));
		p2.add(p22,BorderLayout.NORTH);

		online = new JTextArea(10,10);
		online.setEditable(false);
		p2.add(new JScrollPane(online),BorderLayout.CENTER);
		p2.add(new JLabel("     "),BorderLayout.SOUTH);
		p2.add(new JLabel("     "),BorderLayout.EAST);
		p2.add(new JLabel("     "),BorderLayout.WEST);

		msg = new JTextArea(10,20);
		msg.setEditable(false);

		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.add(new JLabel("Tin nhắn"));
		message = new JTextField(30);
		p3.add(message);
		p3.add(send);
		p3.add(clear);

		p_chat.add(new JScrollPane(msg),BorderLayout.CENTER);
		p_chat.add(p1,BorderLayout.NORTH);
		p_chat.add(p2,BorderLayout.EAST);
		p_chat.add(p3,BorderLayout.SOUTH);
		p_chat.add(new JLabel("     "),BorderLayout.WEST);

		p_chat.setVisible(false);
		add(p_chat,BorderLayout.CENTER);
		//-------------------------
		p_login = new JPanel();
		p_login.setLayout(new FlowLayout(FlowLayout.CENTER));
		p_login.add(new JLabel("Tên tài khoản : "));
		nick1=new JTextField(20);
		p_login.add(nick1);
		p_login.add(new JLabel("Mật khẩu : "));
		mk=new JPasswordField(20);
		p_login.add(mk);
		p_login.add(login);
		p_login.add(logout);

		add(p_login,BorderLayout.NORTH);
	}
//Tạo Socket (Kết nối với Server)
	protected void go() {
		try {
			client = new Socket("localhost",2207);
			dos=new DataOutputStream(client.getOutputStream());
			dis=new DataInputStream(client.getInputStream());

			//client.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,"Lỗi kết nối, xem lại dây mạng đi hoặc room chưa mở.","Message Dialog",JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
	}

//Lưu dữ liệu
	private void sendMSG(String data){
		try {
			dos.writeUTF(data);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getMSG(){
		String data=null;
		try {
			data=dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void getMSG(String msg1, String msg2){
		int stt = Integer.parseInt(msg1);
		switch (stt) {
		// tin nhắn của những người khác
		case 3:
			this.msg.append(msg2);
			break;
		// update danh sách online
		case 4:
			this.online.setText(msg2);
			break;
		// server đóng cửa
		case 5:
			dataStream.stopThread();
			exit();
			break;
		default:
			break;
		}
	}
//Kiểm tra tin nhắn
	private void checkSend(String msg){
		if(msg.compareTo("\n")!=0){
			this.msg.append("Tôi : "+msg);
			//Server.
			
			sendMSG("1");
			sendMSG(msg);
		}
	}
//Kiểm tra đăng nhập
	public boolean checkUser(String username, String password) {
		String sql = "select count(*) from taikhoan where MaTK = '"+username+"' and matkhau = '"+password+"'";
		PreparedStatement pst;
		try {
			pst = Mypublic.getConnection().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				if(rs.getInt(1) == 1) {
					sendMSG(username);
					int sst = Integer.parseInt(getMSG());
					if(sst==0)
						 return false;
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
//Phương thức thoát
	private void exit(){
		try {
			sendMSG("0");
			dos.close();
			dis.close();
			client.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.exit(0);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==exit){
			dataStream.stopThread();
			exit();
		}
		else if(e.getSource()==clear){
			message.setText("");
		}
		else if(e.getSource()==send){
			checkSend(message.getText()+"\n");
			message.setText("");
		}
		else if(e.getSource()==login){
			String pwd= new String(mk.getPassword());
			if(checkUser(nick1.getText(),pwd)){
				p_chat.setVisible(true);
				p_login.setVisible(false);
				nick.setText(nick1.getText());
				nick.setEditable(false);
				this.setTitle(nick1.getText());
				// Hiển thị các tin nhắn đã chat
				MessageDao.SelectTN(nick1.getText(), msg);
				msg.append("Đã đăng nhập thành công\n");
				dataStream = new DataStream(this, this.dis);
			}
			else{
				JOptionPane.showMessageDialog(this,"Sai tài khoản hay mật khẩu hoặc hệ thống đã tồn tại tài khoản này, bạn vui lòng nhập lại.","Message Dialog",JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(e.getSource()==logout){
			exit();
		}
	}


}
