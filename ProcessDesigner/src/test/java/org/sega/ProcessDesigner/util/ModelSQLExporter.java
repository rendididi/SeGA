package org.sega.ProcessDesigner.util;

import java.util.Iterator;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class ModelSQLExporter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configuration cfg = new Configuration().configure();

		SchemaExport exporter =new SchemaExport(cfg);
		
		exporter.create(true, false);
	}

}
