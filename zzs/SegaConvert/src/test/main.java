package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import SegaConvert.SegaConvert;

public class main {
	public static void main(String args[]) throws FileNotFoundException{
		String entityJSON = getStr("entitySample01.json");
		String processJSON = getStr("process.json");
		String bindJSON = getStr("binding.json");
		
		
		//测试方法，xml生成目录是根目录
		SegaConvert.JSONtoXML(entityJSON, processJSON, bindJSON, "process.xml");
	}
	
	public static String getStr(String file) throws FileNotFoundException{
		String str=new String();
		int a = 0;
		FileReader fr = new FileReader(file);
		try {
			while((a=fr.read())!=-1){
				str = str+(char)a;
			}
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str;
	}
}
