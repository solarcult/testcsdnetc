package org.shil.testcsdnetc.entity;

public class CsdnAccount {

	private long id;
	private String csdnname;
	private String password;
	private String email;
	private String site;
	private int status;
	
	public CsdnAccount(String csdnname,String password,String email,int status){
		this.csdnname = csdnname;
		this.password = password;
		this.email = email;
		if(this.email!=null && !this.email.trim().isEmpty()){
			int atindex = this.email.indexOf("@");
			this.site = this.email.substring(atindex+1).toLowerCase();
		}
		this.status = status;
	}

	public String getCsdnname() {
		return csdnname;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public long getId() {
		return id;
	}

	public String getSite() {
		return site;
	}

	public int getStatus() {
		return status;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CsdnAccount [id=" + id + ", csdnname=" + csdnname + ", password=" + password + ", email=" + email
				+ ", site=" + site + ", status=" + status + "]";
	}

}
