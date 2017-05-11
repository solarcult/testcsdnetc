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
        	httppost.addHeader("Cookie","ue=soulmusicfly@163.com; __utmc=30149280; bid=pAgvtU3Muac; __utma=30149280.1695910391.1452181151.1494164880.1494520806.165; __utmz=30149280.1494164880.164.60.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmv=30149280.101; gr_user_id=c5e63dec-a481-4ca3-aaab-72e876398167; push_noty_num=0; push_doumail_num=0; ap=1; _vwo_uuid_v2=0575B48B5895EDA91151951D76154290|a823046643034f57a31420e1bdd3af70; _ga=GA1.2.1695910391.1452181151; ll=118282; __utmb=30149280.23.7.1494521001292; ps=y; _gid=GA1.2.1519591480.1494524949; ue=soulmusicfly@163.com; _gat_UA-7019765-1=1; __utmt=1; _pk_id.100001.8cb4=470b043f752b762a.1452181149.155.1494524969.1494164878.; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1494520998%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DFL67KHdTOFMEshaTm2B_pvTyoTM__tTw-VwWUBWekqhVKqwBQB8GHKXTDCgK4Or5r2EaXKUPskf9PzAwRq-gaa%26wd%3D%26eqid%3Dcf7c53150003970500000004590f23f8%22%5D; __yadk_uid=dX2MoAdQyqGvPOSr2o3uYTAsqHQHWNSX; _pk_ses.100001.8cb4=*");
        	httppost.addHeader("Referer","https://www.douban.com/");
        	httppost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");
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
                	Header[] hs = response.getHeaders("Set-Cookie");
                	for(Header h : hs){
                		System.out.println(h.getName()+" vs " + h.getValue());
                	}
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        
                        return entity != null ? EntityUtils.toString(entity) : null;
                    }else if(status == 302){
                    	String locationUrl=response.getLastHeader("Location").getValue();
                    	System.out.println(locationUrl);
                    	return get(locationUrl);//跳转到重定向的url  
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
	
	public static String get(String cardurl){
		String responseBody = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
        	
        	HttpGet httpget = new HttpGet(cardurl);
//        	httpget.addHeader(new BasicHeader("Cookie","incap_ses_574_839682=3hK+FoNdnEvtwL5ZLUL3B31zFFkAAAAA9I6TUjqUiXoFviYDBmlJLA==; CHSESSION=phrniie8m1e054hbco9e2csk33; visid_incap_839682=Y2evWq9iQ/CnqNS44KGhUwJph1gAAAAAQkIPAAAAAACADgp8AXbBFIqXKAfjUUs6sYsKhlDkdpeE; ajs_user_id=null; ajs_group_id=null; ajs_anonymous_id=%22c3a40103-d7a0-4019-b654-5d22756afa5f%22; _ga=GA1.2.1509432278.1485269260; intercom-id-jv6shdwn=920223f0-e140-4bfe-86a0-052aafec36e2; App[AnonymousId]=Q2FrZQ%3D%3D.pua3hG9eoNdgt6LUv3cW%2FiJsM3uDpdeVzhi8y9J%2B9CIkJE6unwnkHS3HKDnT7LDP55LX4q3qkHwzsRs92SFjqfajGnXW48jTosQWH6cOf7o2Vh5N7t1PSr00Lj%2F3CH5DXnE%3D; _gid=GA1.2.1724015756.1494465040"));
        	
//            System.out.println("Executing request " + httpget.getRequestLine());

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
