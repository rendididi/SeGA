package org.SeGA.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.SeGA.api.instance.ProcessInsAPI;
import org.SeGA.api.instance.WorkitemAPI;
import org.SeGA.api.schema.ProcessAPI;
import org.SeGA.model.EngineType;
import org.SeGA.model.JTangProcIns;
import org.SeGA.model.JTangProcess;
import org.SeGA.model.ProcessInstance;
import org.SeGA.model.Process;
import org.SeGA.model.SchemaType;


import com.caucho.hessian.client.HessianProxyFactory;


public class Test1 {
	
	public static void main(String[] args) throws MalformedURLException {
		testforapi();		
	}	
	public static void testforapi () throws MalformedURLException {
		String localUrl = "http://localhost:8080/SeGA/";
		String pubUrl = "http://120.24.49.253:8080/SeGA/";
		String procUrl = pubUrl + "ProcessAPI";
		String procInsUrl = pubUrl + "ProcessInsAPI";
		String workitemUrl = pubUrl + "WorkitemAPI";
		HessianProxyFactory factory = new HessianProxyFactory();
		ProcessInsAPI procInsAPI = (ProcessInsAPI) factory.create(ProcessInsAPI.class, procInsUrl);
		WorkitemAPI workitemAPI = (WorkitemAPI) factory.create(WorkitemAPI.class,workitemUrl);
		ProcessAPI processAPI = (ProcessAPI) factory.create(ProcessAPI.class,procUrl);
		String schemaType = SchemaType.SimpleJTang;
		String engineType = EngineType.JTang;
		String processSchema = "";
		String processName = "test";
		String instanceName = "testIns";
		
		URL location = Test1.class.getProtectionDomain().getCodeSource().getLocation();
		File xmlfile = new File(location.getPath()+"../xml/process2.xml");


//		File xmlfile = new File("C://Users/Administrator/Desktop/simplest.xml");
		String send="";
		InputStream input;
		try {
			input = new FileInputStream(xmlfile);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(input,
							"utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			send = buffer.toString();

			System.out.println(send);
		} catch (IOException e) {
			e.printStackTrace();
		}		
					
		processSchema = send;

		Process process = processAPI.publishProcess(schemaType, engineType, processSchema, processName);
		JTangProcess JtangP = (JTangProcess) process;
		
		ProcessInstance processInstance = procInsAPI.createInstance(engineType, process, instanceName);

		Map <String, Object> map = new HashMap<String, Object>();
		map.put("datainput", 3);
		
		JTangProcIns JtangPI = (JTangProcIns) processInstance;
		System.out.println(JtangP.getActDescByActId(JtangPI.getActWorkitems().get(0).getActID()));

		processInstance = workitemAPI.commitWorkitem(engineType, process, processInstance, "14", JtangPI.getActWorkitems().get(0).getId());
		System.out.println(JtangP.getActDescByActId(JtangPI.getActWorkitems().get(0).getActID()));
		
		JtangPI = (JTangProcIns) processInstance;
		processInstance = workitemAPI.commitWorkitem(engineType, process, processInstance, "14", JtangPI.getActWorkitems().get(0).getId());
		System.out.println(JtangP.getActDescByActId(JtangPI.getActWorkitems().get(0).getActID()));
		
	}
	
	
}
