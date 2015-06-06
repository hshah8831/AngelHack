package edu.neu.angelhack;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class DummyClass {
	private String apikey = "e92d3e29-a835-4b3c-a6fe-97be1c6dcee0";
	private String url_ocrdocument = "https://api.idolondemand.com/1/api/sync/ocrdocument/v1";
	private String filesrc="D:\\hw3\\hw3.pdf";

	public void post1(){
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {
		    HttpPost httppost = new HttpPost(url_ocrdocument);
		
		    File f = new File(filesrc);
		    FileBody fileBody = new FileBody(f);
		    StringBody apikeyStringBody = new StringBody(apikey, ContentType.TEXT_PLAIN);
		
		    HttpEntity reqEntity = MultipartEntityBuilder.create()
			    .addPart("file", fileBody)
			    .addPart("apikey", apikeyStringBody)
			    .build();
		
		    httppost.setEntity(reqEntity);
		
		    CloseableHttpResponse response = null;
		    
		    try {
			response = httpclient.execute(httppost);
			
			System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			if (entity != null) {
			    entity.writeTo(System.out);
			}
			
		    }catch(ClientProtocolException cpe){
			cpe.printStackTrace();
		    }catch(IOException ioe){
			ioe.printStackTrace();
		    } finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    }
		} finally {
		    try {
			httpclient.close();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
	}
	
	public static void main(String[] args){
		
		DummyClass dc = new DummyClass();
		
		dc.post1();
	}
}
