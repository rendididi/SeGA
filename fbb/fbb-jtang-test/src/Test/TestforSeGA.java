package Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.SeGA.api.instance.ProcessInsAPI;
import org.SeGA.api.instance.WorkitemAPI;
import org.SeGA.api.schema.ProcessAPI;
import org.SeGA.model.EngineType;
import org.SeGA.model.Process;
import org.SeGA.model.ProcessInstance;
import org.SeGA.model.SchemaType;

import com.caucho.hessian.client.HessianProxyFactory;

public class TestforSeGA {
	public static void main(String[] args) throws Exception {
		testforapi();		
	}	
	public static void testforapi () throws Exception {
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
		URL location = TestforSeGA.class.getProtectionDomain().getCodeSource().getLocation();
		File xmlfile = new File(location.getPath()+"../xml/process.xml");

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
		
		ProcessInstance processInstance = procInsAPI.createInstance(engineType, process, instanceName);
//		ProcessInstance processInstance = processInsAPI.createInstance(schemaType, processSchema, processName, instanceName);
		
		Map <String, Object> map = new HashMap<String, Object>();
		map.put("datainput", 3);
//		processInstance = workitemAPI.commitWorkitemWithMap(engineType, process, processInstance, map, "14", processInstance.getWorkitems().get(0).getId());
		
		System.out.println(processInstance.getWorkitems().get(0).getName());
		System.out.println(processInstance.getWorkitems().size());
		
		processInstance = workitemAPI.commitWorkitem(engineType, process, processInstance, "14", processInstance.getWorkitems().get(0).getId());
		System.out.println(processInstance.getWorkitems().size());
		
		processInstance = workitemAPI.commitWorkitem(engineType, process, processInstance, "14", processInstance.getWorkitems().get(1).getId());
		System.out.println(processInstance.getWorkitems().size());
		
//		workitemAPI.commitWorkitem(schemaType, processSchema, processName, processInstance, "14", processInstance.getWorkitems().get(0).getId());
		processInstance = workitemAPI.commitWorkitem(engineType, process, processInstance, "14", processInstance.getWorkitems().get(2).getId());

//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		ObjectOutputStream oos = new ObjectOutputStream(baos);
//		oos.writeObject(process);
//		oos.close();
//		String b64 = Base64.getEncoder().encodeToString(baos.toByteArray());
//		System.out.println(b64);
	}
	
}
