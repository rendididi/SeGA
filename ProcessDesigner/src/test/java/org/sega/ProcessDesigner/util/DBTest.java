package org.sega.ProcessDesigner.util;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

import com.mysql.fabric.xmlrpc.base.Data;


public class DBTest {

	public static void main(String[] args) {

		String random_name = (new Date()).toLocaleString()+RandomStringUtils.randomAlphanumeric(32);
		System.out.println(random_name);
	}

}
