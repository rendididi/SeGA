package org.sega.ProcessDesigner.util;

import org.sega.ProcessDesigner.models.ProcessTemplateHome;

public class DBTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProcessTemplateHome dao = new ProcessTemplateHome();
		System.out.println(dao.findAll().size());
		
	}

}
