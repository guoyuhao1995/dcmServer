package com.dryork.vision.base.util;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.JsonMappingException;

import com.alibaba.fastjson.JSONObject;
import com.dryork.vision.manage.po.PatientReport;

/**
 * 以post或get方式调用远程方法
 * 
 * @author leoliang
 *
 */
public class HttpClientLocalUtils {

	private static Logger logger = LogUtils.INFO;
	private static Logger errorLog = LogUtils.ERROR;

	public static int uploadPatientInfo(PatientReport record) { 
		int code = -1;
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpclient = httpClientBuilder.build();
       //CloseableHttpClient httpclient = HttpClients.createDefault();  
        String url = ConstantsPond.SERVER_PATH+"server/test.json";
        try {  
            HttpPost httppost = new HttpPost(url);  
            FileBody bin = new FileBody(new File(record.getImage()));  
            //StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);  
            String responseStr = "";
            //HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("cardimg", bin).addPart("comment", comment).addTextBody("patNo", record.getPatNo()).build();
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("image", bin).
            														addTextBody("patNo", record.getPatNo()).
            														addTextBody("name", record.getName()).
            														addTextBody("sex", record.getSex()).
            														addTextBody("age", record.getAge() != null ? record.getAge()+"":null).
            														addTextBody("isLeft", record.getIsLeft()).
            														addTextBody("mecId", record.getMecId()).
            														//addTextBody("iopLeft", record.getIopLeft()).
            														//addTextBody("iopRight", record.getIopRight()).
            														//addTextBody("visionLeft", record.getVisionLeft()).
            														//addTextBody("visionRight", record.getVisionRight()).
            														build();
            httppost.setEntity(reqEntity);  
            System.out.println("executing request " + httppost.getRequestLine());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity resEntity = response.getEntity(); 
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == 200) {
					responseStr = EntityUtils.toString(resEntity, "utf-8");// 返回请求实体
				} else {
					logger.info("httpClient响应状态" + statusLine);
				}
                EntityUtils.consume(resEntity);
                logger.info("save patient info result :"+responseStr);
                JSONObject jsonObject = JSONUtils.toJSONObject(responseStr);
                if(jsonObject != null && jsonObject.getIntValue("code") == 0){
                	code = 0;
                 }
            } finally {  
                response.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();
			errorLog.error(e.toString(), e);
			errorLog.error("post请求调用远程方法出现异常：" + url);
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
        return code;
    }  
	
	public static void main(String[] args) throws IOException, JsonMappingException, IOException {
		PatientReport record = new PatientReport(); 
		uploadPatientInfo(record);
		/*HttpGet httpGet = new HttpGet();
		String requestUrl = "http://101.200.180.80:8000/";
		requestUrl = requestUrl + "/api/gethostinfo";
		// requestUrl=requestUrl+"/com/putao/erp/setting/box/box/queryAllBox4VOD.do";
		// HttpPost httpPost = new HttpPost();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sn", "9fps33x");
		Map<String, Object> headMap = new HashMap<String, Object>();
		headMap.put("x-cookie", "ccc");
		String responseStr = getTelnetData(httpGet, requestUrl, map, null);
		JSONObject jobj = JSONObject.parseObject(responseStr);
		System.out.println(responseStr);
		System.out.println(jobj.get("sid"));
		System.out.println(jobj.get("status"));*/

	}
}
