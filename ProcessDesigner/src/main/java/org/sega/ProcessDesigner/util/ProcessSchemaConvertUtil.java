package org.sega.ProcessDesigner.util;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jta.flow.JTAFlow;
import org.jta.flow.ObjectFactory;

public class ProcessSchemaConvertUtil {
	
	private static ObjectFactory jtaFactory =new ObjectFactory();
	
	public static String JSONtoXML(String entityJSON,String processJSON,String bindJSON){
//		参数依次为enitity的json字符串，process的json字符串，binding的json字符串以及保存xml文件的目标文件夹，返回值为File类型的xml文件

		
		//调用工厂类的方法生成名为jtaFlow的xml对象，具体实现写在 org，jta，flow 包中
		JTAFlow jtaFlow = jtaFactory.createJTAFlow(entityJSON, processJSON, bindJSON);
		java.io.StringWriter sw = new StringWriter();
		//在指定位置保存生成的xml文件
		try {  

            JAXBContext jaxbContext = JAXBContext.newInstance(JTAFlow.class);  
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();  
            // output pretty printed  
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  
            jaxbMarshaller.marshal(jtaFlow, sw);
            
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }  
		
		return sw.toString();
	}
}
