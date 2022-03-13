package model;

public class taikhoan {
	private String tendn;
	private String matkhau;
	private String ten;
	
	public taikhoan()
	{
		tendn="";
		matkhau="";
		ten="";
	}
	
	public String getTenDN()
	{
		return tendn;
	}
	public String getMatKhau()
	{
		return matkhau;
	}
	public String getTen()
	{
		return ten;
	}
	public void setTenDN(String tendn)
	{
		this.tendn=tendn;
	}
	public void setMatKhau(String matkhau)
	{
		this.matkhau=matkhau;
	}
	public void setTen(String ten)
	{
		this.ten=ten;
	}
}
