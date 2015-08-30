package org.sega.ProcessDesigner.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Util {
	public static String decode(String b64) throws UnsupportedEncodingException{
		return new String(Base64.getDecoder().decode(b64), "UTF-8");
	}
}
