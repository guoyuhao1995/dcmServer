/* ***** BEGIN LICENSE BLOCK *****
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 Martín Lamas
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * ***** END LICENSE BLOCK ***** */

package com.dryork.vision.base.util.net.service.storescp;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomOutputStream;
import org.dcm4che3.net.Association;
import org.dcm4che3.net.PDVInputStream;
import org.dcm4che3.net.Status;
import org.dcm4che3.net.pdu.PresentationContext;
import org.dcm4che3.net.service.BasicCStoreSCP;
import org.dcm4che3.net.service.DicomServiceException;
import org.dcm4che3.util.SafeClose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.dryork.vision.base.util.ConstantsPond;
import com.dryork.vision.base.util.JSONUtils;
import com.dryork.vision.base.util.dcm.ConvertImage;
import com.dryork.vision.base.util.dcm.DicomServerApplicationEntity;
import com.dryork.vision.base.util.dcm.DisplayTag;
import com.dryork.vision.base.util.dcm.Tags;
import com.dryork.vision.manage.po.PatientReport;

public class StoreSCP extends BasicCStoreSCP {
	private static Logger logger = LoggerFactory.getLogger(StoreSCP.class);
	
	private static final String FILE_EXT = ".dcm";
	private static final String PART_EXT = ".part";
	
	private List<StoreSCPListener> storeSCPListeners;
	
	private void deleteFile(Association as, File file) {
		if(file.delete())
			logger.info("{}: M-DELETE {}", as, file);
		else
			logger.warn("{}: M-DELETE {} failed!", as, file);
	}
	
	private void renameTo(Association as, File from, File destination)
			throws IOException {
		logger.info("{}: M-RENAME {}", new Object [] {as, from, destination});
		
		destination.getParentFile().mkdirs();
		if(!from.renameTo(destination))
			throw new IOException("Failed to rename " + from + " to " + destination);
    }
	
	private void storeTo(Association as, Attributes fmi, PDVInputStream data,
			File file) throws IOException  {
		logger.info("{}: M-WRITE {}", as, file);
		
		file.getParentFile().mkdirs();
		DicomOutputStream out = new DicomOutputStream(file);
		try {
			out.writeFileMetaInformation(fmi);
			data.copyTo(out);
		} finally {
			SafeClose.close(out);
		}
	}
	
	public StoreSCP() {
		storeSCPListeners = new ArrayList<StoreSCPListener>();
	}
	
	public void addStoreSCPListener(StoreSCPListener storeSCPListener) {
		storeSCPListeners.add(storeSCPListener);
	}
	
	@Override
	public void onClose(Association as) {
		for(StoreSCPListener storeSCPListener: storeSCPListeners)
			storeSCPListener.onCloseAssociation(as);
	}
	
	@Override
	protected void store(Association as, PresentationContext pc, Attributes rq,
			PDVInputStream data, Attributes rsp) throws IOException {
		String cuid = rq.getString(Tag.AffectedSOPClassUID);
		String iuid = rq.getString(Tag.AffectedSOPInstanceUID);
		System.out.println(rq.getString(Tag.PatientName));
		String tsuid = pc.getTransferSyntax();
		File storageDirectory = ((DicomServerApplicationEntity)
				as.getApplicationEntity()).getStorageDirectory();
        
		if(storageDirectory == null) {
			logger.error("Unable to store instance with SOP Instance UID " + iuid +
					": no storage directory configured");
			return;
		}
		if(!storageDirectory.exists()){
			storageDirectory.mkdirs();
		}
		
		File partFile = new File(storageDirectory, iuid + PART_EXT);
		File instanceFile = new File(storageDirectory, iuid + FILE_EXT);
		
		try {
			storeTo(as, as.createFileMetaInformation(iuid, cuid, tsuid),
					data, partFile);
			renameTo(as, partFile, instanceFile);
		} catch(Exception e) {
			deleteFile(as, partFile);
			throw new DicomServiceException(Status.ProcessingFailure, e);
		}
		
		for(StoreSCPListener storeSCPListener: storeSCPListeners){
			storeSCPListener.onInstanceReceived(as, iuid, instanceFile);
		}
		sendPatientInfo(iuid);
		 
	}
	
	
	public static void  sendPatientInfo(String iuid){
		 File dcmFile = new File(ConstantsPond.DEFAULT_STORAGE_DIRECTORY+iuid+".dcm");
		 try {
			Attributes attr = DisplayTag.loadDicomObject(dcmFile);
			PatientReport record = new PatientReport();
			 byte[] namebyte = attr.getBytes(Tag.PatientName);
			//record.setName(DisplayTag.getStringTag2(attr, Tags.name));
			 record.setName(new String(namebyte,"gb18030"));
			 record.setPatNo(DisplayTag.getStringTag2(attr, Tags.patNo));
			 String sex = "";
			 byte[] sexbyte = attr.getBytes(Tag.PatientSex);
			 if(sexbyte != null && sexbyte.length > 0){
				 sex = new String(sexbyte,"gb18030");
			 }
			 if("男".equals(sex)){
				 sex = "1";
			 }else if("女".equals(sex)){
				 sex = "0";
			 }
			 record.setSex(sex);
			 record.setAge(StringUtils.isNotBlank(DisplayTag.getStringTag2(attr, Tags.age)) ? Integer.parseInt(DisplayTag.getStringTag2(attr, Tags.age)):null);
			 //record.setVisionLeft(DisplayTag.getStringTag(rq, Tags.patNo));
			 //record.setVisionRight(DisplayTag.getStringTag(rq, Tags.patNo));
			 String isLeft = DisplayTag.getStringTag2(attr, Tags.isLeft);
			 record.setMecId(ConstantsPond.mecId);
			 record.setIsLeft("0");
			 if("L".equals(isLeft)){
				 record.setIsLeft("1");
			 }
			 File src = new File(ConstantsPond.DEFAULT_STORAGE_DIRECTORY+iuid+FILE_EXT);
			 File dest = new File(ConstantsPond.DEFAULT_STORAGE_DIRECTORY+iuid+".jpg");
			 if(!dest.exists()){
				 dest.createNewFile();
			 }
			 ConvertImage convertImage = new ConvertImage();
			 convertImage.mconvert(src, dest);
			 record.setImage(ConstantsPond.DEFAULT_STORAGE_DIRECTORY+iuid+".jpg");
			 int code = uploadPatientInfo(record);
			 if(code ==1){
				 System.out.println("patientName:"+record.getName());
				 System.out.println("新增患者接口成功，删除文件："+src.getName()+" "+src.delete());
				 System.out.println("新增患者接口成功，删除文件："+dest.getName()+" "+dest.delete());
				 File partFile = new File(ConstantsPond.DEFAULT_STORAGE_DIRECTORY+iuid+".part");
				 if(partFile.exists()){
					 partFile.delete();
				 }
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public static int uploadPatientInfo(PatientReport record) { 
		int code = 0;
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpclient = httpClientBuilder.build();
        String url = ConstantsPond.SERVER_PATH+"server/addPatient.json";
        try {  
            HttpPost httppost = new HttpPost(url);  
            FileBody bin = new FileBody(new File(record.getImage()));  
            String responseStr = "";
            String name = URLEncoder.encode(record.getName(), "utf-8");	
            System.out.println("send interface param: patNo:"+record.getPatNo()+",name:"+name+",sex:"+record.getSex()+",age:"+record.getAge()+",isLeft:"+record.getIsLeft()+",mecId:"+record.getMecId());
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("image", bin).
            														addTextBody("patNo", record.getPatNo()).
            														addTextBody("name", name).
            														addTextBody("sex", record.getSex() ).
            														addTextBody("age", record.getAge() != null ? record.getAge()+"":"").
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
					System.out.println("httpClient响应状态" + statusLine);
				}
                EntityUtils.consume(resEntity);
                System.out.println("save patient info result :"+responseStr);
                JSONObject jsonObject = JSONUtils.toJSONObject(responseStr);
                if(jsonObject != null && jsonObject.getIntValue("code") == 1){
                	code = 1;
                 }
            } finally {  
                response.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();
        } finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
        return code;
	}  
	
	
	
}
