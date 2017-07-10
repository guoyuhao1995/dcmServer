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

package com.dryork.vision.base.util.net.dicomstorescpserver;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.dcm4che3.net.ApplicationEntity;
import org.dcm4che3.net.Connection;
import org.dcm4che3.net.Device;
import org.dcm4che3.net.TransferCapability;
import org.dcm4che3.net.service.DicomServiceRegistry;

import com.dryork.vision.base.util.ConstantsPond;
import com.dryork.vision.base.util.dcm.DicomServerApplicationEntity;
import com.dryork.vision.base.util.net.service.storescp.StoreSCP;
import com.dryork.vision.base.util.net.service.storescp.StoreSCPListener;

public class DicomStoreSCPServer implements IDicomStoreSCPServer {
	private Connection connection;
	private Device device;
	
	private StoreSCP storeSCP;
	
	private void configureTransferCapability(ApplicationEntity applicationEntity) {
		// Accepts all SOP Classes and Transfer Syntaxes
		applicationEntity.addTransferCapability(new TransferCapability(null, "*",
				TransferCapability.Role.SCP, "*"));
	}
	
	public DicomStoreSCPServer(int port,List<DicomServerApplicationEntity> applicationEntities) {
		System.out.println("ip address is :"+ConstantsPond.DCM_SERVER_URL);
		connection = new Connection("dcmServer",ConstantsPond.DCM_SERVER_URL,ConstantsPond.DCM_SERVER_PORT);
		//connection.setPort(port);
		//connection.setHostname("http://127.0.0.1");
		device = new Device("proxy");
		
		storeSCP = new StoreSCP();
		
		DicomServiceRegistry serviceRegistry = new DicomServiceRegistry();
		serviceRegistry.addDicomService(storeSCP);
		
		device.addConnection(connection);
		device.setDimseRQHandler(serviceRegistry);
		
		for(DicomServerApplicationEntity applicationEntity: applicationEntities) {
			configureTransferCapability(applicationEntity);
			device.addApplicationEntity(applicationEntity);
			
			applicationEntity.addConnection(connection);
		}
	}
	
	public DicomStoreSCPServer(int port, File storageDirectory,
			List<DicomServerApplicationEntity> applicationEntities,
			List<StoreSCPListener> storeSCPListeners) {
		this(port, applicationEntities);
		
		for(StoreSCPListener storeSCPListener: storeSCPListeners)
			storeSCP.addStoreSCPListener(storeSCPListener);
	}
	
	public void addStoreSCPListener(StoreSCPListener storeSCPListener) {
		storeSCP.addStoreSCPListener(storeSCPListener);
	}
	
	public void start() throws GeneralSecurityException, IOException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		ScheduledExecutorService scheduledExecutorService =
				Executors.newSingleThreadScheduledExecutor();
		
		device.setScheduledExecutor(scheduledExecutorService);
		device.setExecutor(executorService);
		device.bindConnections();
	}
}
