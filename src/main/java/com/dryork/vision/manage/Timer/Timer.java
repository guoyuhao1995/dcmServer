package com.dryork.vision.manage.Timer;

import java.io.File;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dryork.vision.base.util.ConstantsPond;
import com.dryork.vision.base.util.net.service.storescp.StoreSCP;

@Component
public class Timer {
	
	 @Scheduled(fixedDelay = 1000*60*5)
	 void doSomethingWithDelay(){
		System.out.println("进入DCMSERVER任务调度");
		 File file = new File(ConstantsPond.DEFAULT_STORAGE_DIRECTORY); 
		 File[] tempFile = file.listFiles();
		 System.out.println("一共有"+tempFile.length+"个文件");
		 for(int i=0;i<tempFile.length;i++){
			 try {
				if(tempFile[i].getName().endsWith(".dcm")){
					 System.out.println(tempFile[i].getName());
					 String[] names = tempFile[i].getName().split(".dcm");
					 String iuid = names[0];
					 StoreSCP.sendPatientInfo(iuid);
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }

	 }
}
