package model;

import java.security.Timestamp;

public class tinnhan {
	private String matn;
	private String noidung;
	private String taikhoan;
	private Timestamp thoigian;
	
	public tinnhan()
	{
		taikhoan="";
		noidung="";
		matn="";
		thoigian=null;
	}
	
	public String getTaiKhoan()
	{
		return taikhoan;
	}
	public String getNoiDung()
	{
		return noidung;
	}
	
	public String getMatn()
	{
		return matn;
	}
	public Timestamp getthoigian()
	{
		return thoigian;
	}
	
	public void setTaiKhoan(String taikhoan)
	{
		this.taikhoan=taikhoan;
	}
	
	public void setNoiDung(String noidung)
	{
		this.noidung=noidung;
	}
	
	public void setMatn(String matn)
	{
		this.matn=matn;
	}
	public void setThoiGian(Timestamp thoigian)
	{
		this.thoigian=thoigian;
	}
}
