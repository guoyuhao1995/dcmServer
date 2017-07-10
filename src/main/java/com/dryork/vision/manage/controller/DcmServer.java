package com.dryork.vision.manage.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.dryork.vision.base.util.ConstantsPond;
import com.dryork.vision.base.util.dcm.DicomServerApplicationEntity;
import com.dryork.vision.base.util.net.dicomstorescpserver.DicomStoreSCPServer;
import com.dryork.vision.base.util.net.dicomstorescpserver.IDicomStoreSCPServer;

/**
 * spring容器加载完毕后启动监听104端口服务
 */
@Component("BeanDefineConfigue")
public class DcmServer implements ApplicationListener<ContextRefreshedEvent> {
	
	private static String DEFAULT_AE_TITLE="SERVER";
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
			System.out.println("开始执行监听"+ConstantsPond.DCM_SERVER_URL+":"+ConstantsPond.DCM_SERVER_PORT+"端口dcm服务");
			String aeTitle = DEFAULT_AE_TITLE;
			File storageDirectory = new File(ConstantsPond.DEFAULT_STORAGE_DIRECTORY);
			try {
				if(!storageDirectory.exists()){
					storageDirectory.mkdirs();
				}
				List<DicomServerApplicationEntity> applicationEntities = new ArrayList<DicomServerApplicationEntity>();
				DicomServerApplicationEntity applicationEntity =new DicomServerApplicationEntity(aeTitle, storageDirectory);
				applicationEntities.add(applicationEntity);
				IDicomStoreSCPServer server = new DicomStoreSCPServer(ConstantsPond.DCM_SERVER_PORT,applicationEntities);
				server.start();
				System.out.println("dcm服务启动完成");
			}catch(Exception e){
				System.out.println("dcm服务启动异常");
				e.printStackTrace();
			}
		}
	}
}
