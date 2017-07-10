package com.dryork.vision.base.util.image;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class uploadImage {
	private static final String BOUNDARY = "1";
	 public String sendHttpPostRequest(String serverUrl,  
	            ArrayList<FormFieldKeyValuePair> generalFormFields,  
	            ArrayList<UploadFileItem> filesToBeUploaded) throws Exception {  
	  
	        // 向服务器发送post请求  
	  
	        URL url = new URL(serverUrl/* "http://127.0.0.1:8080/test/upload" */);  
	  
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
	  
	        // 发送POST请求必须设置如下两行  
	  
	        connection.setDoOutput(true);  
	        connection.setDoInput(true);  
	        connection.setUseCaches(false);  
	        connection.setRequestMethod("POST");  
	        connection.setRequestProperty("Connection", "Keep-Alive");  
	        connection.setRequestProperty("Charset", "UTF-8");  
	        connection.setRequestProperty("Content-Type",  
	                "multipart/form-data; boundary=" + BOUNDARY);  
	  
	        // 头  
	  
	        String boundary = BOUNDARY;  
	  
	        // 传输内容  
	  
	        StringBuffer contentBody = new StringBuffer("--" + BOUNDARY);  
	  
	        // 尾  
	  
	        String endBoundary = "\r\n--" + boundary + "--\r\n";  
	  
	        OutputStream out = connection.getOutputStream();  
	  
	        // 1. 处理文字形式的POST请求  
	  
	        for (FormFieldKeyValuePair ffkvp : generalFormFields)  
	  
	        {  
	  
	            contentBody.append("\r\n")  
	  
	            .append("Content-Disposition: form-data; name=\"")  
	  
	            .append(ffkvp.getKey() + "\"")  
	  
	            .append("\r\n")  
	  
	            .append("\r\n")  
	  
	            .append(ffkvp.getValue())  
	  
	            .append("\r\n")  
	  
	            .append("--")  
	  
	            .append(boundary);  
	  
	        }  
	  
	        String boundaryMessage1 = contentBody.toString();  
	  
	        out.write(boundaryMessage1.getBytes("utf-8"));  
	  
	        // 2. 处理文件上传  
	  
	        for (UploadFileItem ufi : filesToBeUploaded)  
	  
	        {  
	  
	            contentBody = new StringBuffer();  
	  
	            contentBody.append("\r\n")  
	  
	            .append("Content-Disposition:form-data; name=\"")  
	  
	            .append(ufi.getFormFieldName() + "\"; ") // form中field的名称  
	  
	                    .append("filename=\"")  
	  
	                    .append(ufi.getFileName() + "\"") // 上传文件的文件名，包括目录  
	  
	                    .append("\r\n")  
	  
	                    .append("Content-Type:application/octet-stream")  
	  
	                    .append("\r\n\r\n");  
	  
	            String boundaryMessage2 = contentBody.toString();  
	  
	            out.write(boundaryMessage2.getBytes("utf-8"));  
	  
	            // 开始真正向服务器写文件  
	  
	            File file = new File(ufi.getFileName());  
	  
	            DataInputStream dis = new DataInputStream(new FileInputStream(file));  
	  
	            int bytes = 0;  
	  
	            byte[] bufferOut = new byte[(int) file.length()];  
	  
	            bytes = dis.read(bufferOut);  
	  
	            out.write(bufferOut, 0, bytes);  
	  
	            dis.close();  
	  
	            contentBody.append("------------HV2ymHFg03ehbqgZCaKO6jyH");  
	  
	            String boundaryMessage = contentBody.toString();  
	  
	            out.write(boundaryMessage.getBytes("utf-8"));  
	  
	            // System.out.println(boundaryMessage);  
	  
	        }  
	  
	        out.write("------------HV2ymHFg03ehbqgZCaKO6jyH--\r\n"  
	                .getBytes("UTF-8"));  
	  
	        // 3. 写结尾  
	  
	        out.write(endBoundary.getBytes("utf-8"));  
	  
	        out.flush();  
	  
	        out.close();  
	  
	        // 4. 从服务器获得回答的内容  
	  
	        String strLine = "";  
	  
	        String strResponse = "";  
	  
	        InputStream in = connection.getInputStream();  
	  
	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
	  
	        while ((strLine = reader.readLine()) != null)  
	  
	        {  
	  
	            strResponse += strLine + "\n";  
	  
	        }  
	  
	        // System.out.print(strResponse);  
	  
	        return strResponse;  
	  
	    }  
	 
	      
	 public static void main(String[] args) {
		 	StringBuffer buffer;
			try {
				MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,"----------ThIs_Is_tHe_bouNdaRY_$", Charset.defaultCharset());  
				multipartEntity.addPart("phone",new StringBody("136********", Charset.forName("UTF-8")));    
				multipartEntity.addPart("receiver",new StringBody("138***********",Charset.forName("UTF-8")));    
				multipartEntity.addPart("cardimg",new FileBody(new File("/opt/dcm/test.jpg"),"image/jpg"));    
				    
				HttpPost request = new HttpPost("http://123.57.149.211:8080/shikang/server/test.json");     
				request.setEntity(multipartEntity);  
				request.addHeader("Content-Type","multipart/form-data; boundary=----------ThIs_Is_tHe_bouNdaRY_$");  
				  
				DefaultHttpClient httpClient = new DefaultHttpClient();  
				HttpResponse response =httpClient.execute(request);  
				  
				InputStream is = response.getEntity().getContent();  
				BufferedReader in = new BufferedReader(new InputStreamReader(is));  
				buffer = new StringBuffer();  
				String line = "";  
				while ((line = in.readLine()) != null) {  
				    buffer.append(line);  
				}
				 System.out.println("发送消息收到的返回："+buffer.toString());  
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  
	          
	    }  
	
}
