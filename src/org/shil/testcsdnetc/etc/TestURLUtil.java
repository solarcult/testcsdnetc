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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TestURLUtil {

	public static String url = "https://www.douban.com/accounts/login";
	public static String login = "https://accounts.douban.com";
	
	public static String fetchUrlBody(String cardurl){
		String responseBody = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
        	
        	HttpPost httppost = new HttpPost(url);
//        	httppost.addHeader("Cookie","ue=soulmusicfly@163.com; __utmc=30149280; bid=pAgvtU3Muac; __utma=30149280.1695910391.1452181151.1494164880.1494520806.165; __utmz=30149280.1494164880.164.60.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmv=30149280.101; gr_user_id=c5e63dec-a481-4ca3-aaab-72e876398167; push_noty_num=0; push_doumail_num=0; ap=1; _vwo_uuid_v2=0575B48B5895EDA91151951D76154290|a823046643034f57a31420e1bdd3af70; _ga=GA1.2.1695910391.1452181151; ll=118282; __utmb=30149280.23.7.1494521001292; ps=y; _gid=GA1.2.1519591480.1494524949; ue=soulmusicfly@163.com; _gat_UA-7019765-1=1; __utmt=1; _pk_id.100001.8cb4=470b043f752b762a.1452181149.155.1494524969.1494164878.; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1494520998%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DFL67KHdTOFMEshaTm2B_pvTyoTM__tTw-VwWUBWekqhVKqwBQB8GHKXTDCgK4Or5r2EaXKUPskf9PzAwRq-gaa%26wd%3D%26eqid%3Dcf7c53150003970500000004590f23f8%22%5D; __yadk_uid=dX2MoAdQyqGvPOSr2o3uYTAsqHQHWNSX; _pk_ses.100001.8cb4=*");
//        	httppost.addHeader("Referer","https://www.douban.com/");
//        	httppost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
//        	params.add(new BasicNameValuePair("form_email", "soulmusicfly@163.com"));
//        	params.add(new BasicNameValuePair("form_password", "sl134120"));
        	params.add(new BasicNameValuePair("form_email", "goodgirl@163.com"));
        	params.add(new BasicNameValuePair("form_password", "iloveapple"));
//        	params.add(new BasicNameValuePair("login","登录"));
//        	params.add(new BasicNameValuePair("redir", "https://www.douban.com"));
        	params.add(new BasicNameValuePair("source", "index_nav"));
        	
        	System.out.println("Executing request " + httppost.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
//                	Header[] hs = response.getHeaders("Set-Cookie");
//                	for(Header h : hs){
//                		System.out.println(h.getName()+" vs " + h.getValue());
//                	}
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        
                        return entity != null ? EntityUtils.toString(entity) : null;
                    }else if(status == 302){
                    	String locationUrl=response.getLastHeader("Location").getValue();
                    	System.out.println(locationUrl);
                    	return get(locationUrl,response.getAllHeaders());//跳转到重定向的url  
                    }else {
                        throw new ClientProtocolException("Unexpected response stat0us: " + status);
                    }
					
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
        
        System.out.println("----------------------------------------");
//        System.out.println(responseBody);
        
        return responseBody;
	}
	
	public static String get(String cardurl,Header[] hs){
		String responseBody = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
        	
        	HttpGet httpget = new HttpGet(cardurl);
        	if(hs!=null){
	        	for(Header h : hs){
	        		httpget.addHeader(new BasicHeader(h.getName(),h.getValue()));
	        	}
        	}
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response stat0us: " + status);
                    }
                }
            };
            
            responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            
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
        
        return responseBody;
	}
	
	public static void main(String[] args){
		fetchUrlBody("");
	}
}
