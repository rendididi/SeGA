package org.sega.ProcessDesigner.util;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import com.mysql.fabric.xmlrpc.base.Data;


public class DBTest {

	public static void main(String[] args) {

		String random_name = (new Date()).toLocaleString()+RandomStringUtils.randomAlphanumeric(32);
		System.out.println(random_name);
		
		JSONObject jo1 = new JSONObject();
		jo1.put("abc", "123");
		
		JSONObject jo2 = new JSONObject(jo1, new String[]{"abc"});
		System.out.println(jo1);
		System.out.println(jo2);
		
	}

}
