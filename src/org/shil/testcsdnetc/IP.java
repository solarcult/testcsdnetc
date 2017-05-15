package org.shil.testcsdnetc;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.shil.testcsdnetc.etc.TestURLUtil;

public class IP {
	
	public static void testCommand(){
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("ipconfig").getInputStream()));
			String line = br.readLine();
			while(line!=null){
				System.out.println(line);
				line = br.readLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void testIpip138(){
		Header[] headers = new Header[3];
		headers[0] = new BasicHeader("Cookie","pgv_si=s7326821376; PHPSESSID=aq2o6ub4fcauvpnpe5gp5p4po4; pgv_pvi=9557374976; _qddaz=QD.sy09z0.afxa64.j2mw6t9u; ASPSESSIONIDSAQDTDTQ=CFINBDDCKNMHHPCPLLLCBPII");
		headers[1] = new BasicHeader("Host","1212.ip138.com");
		headers[2] = new BasicHeader("Referer","http://www.ip138.com/");
		
		TestURLUtil.get("http://1212.ip138.com/ic.asp", headers);
	}
	
	public static void testIpipcn(){
		Header[] headers = new Header[3];
		headers[0] = new BasicHeader("Cookie","UM_distinctid=15c00899e88186-009f6ebf24ae18-572f7b6e-1a25b6-15c00899e8a227; CNZZDATA123770=cnzz_eid%3D1734912979-1494653286-null%26ntime%3D1494653286");
		headers[1] = new BasicHeader("Host","www.ip.cn");
		headers[2] = new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");
		
		TestURLUtil.get("http://www.ip.cn/", headers);
	}
	

	public static void main(String[] args) {
		testIpipcn();
		testIpip138();
	}

}
