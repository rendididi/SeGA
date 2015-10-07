package SegaConvert;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jta.flow.JTAFlow;
import org.jta.flow.ObjectFactory;

public class SegaConvert {
	private String entityJson;
	private String processJson;
	private String bindJson;
	
	private static ObjectFactory jtaFactory =new ObjectFactory();
	
	public static File JSONtoXML(String entityJSON,String processJSON,String bindJSON,String savePath){
//		参数依次为enitity的json字符串，process的json字符串，binding的json字符串以及保存xml文件的目标文件夹，返回值为File类型的xml文件
		
		File file = null;
		
		//调用工厂类的方法生成名为jtaFlow的xml对象，具体实现写在 org，jta，flow 包中
		JTAFlow jtaFlow = jtaFactory.createJTAFlow(entityJSON, processJSON, bindJSON);
		
		//在指定位置保存生成的xml文件
		try {  
            file = new File(savePath+(jtaFlow.getID()));  

            JAXBContext jaxbContext = JAXBContext.newInstance(JTAFlow.class);  
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();  
            // output pretty printed  
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            jaxbMarshaller.marshal(jtaFlow, file);  
            jaxbMarshaller.marshal(jtaFlow, System.out);
            
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }  
		
		return file;
	}
}
