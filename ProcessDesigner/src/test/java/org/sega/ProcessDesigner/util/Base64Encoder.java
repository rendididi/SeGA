package org.sega.ProcessDesigner.util;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

public class Base64Encoder {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String str = FileUtils.readFileToString(new File("d:/temp/temp.json"));
		//System.out.println(str);
		byte[] base64 = Base64.getEncoder().encode(str.getBytes());
		
		FileUtils.writeByteArrayToFile(new File("d:/temp/base64.txt"), base64);
	}

}
