package org.sega.ProcessDesigner.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.jsoup.nodes.Document;
import org.sega.ProcessDesigner.models.Process;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HumanTaskGeneratorTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		Process p = (org.sega.ProcessDesigner.models.Process)session.get("org.sega.ProcessDesigner.models.Process", new Long(78));
		session.getTransaction().commit();
		
		
		String entity_json = Base64Util.decode(p.getEntityJSON());
		String bind_json = Base64Util.decode(p.getBindingJSON());
		
		Properties prop = new Properties();
		InputStream in = HumanTaskGeneratorTest.class.getResourceAsStream("../package.properties");
		prop.load(in);
		in.close();
		
		
		JSONObject entity = JSONArray.fromObject(entity_json).getJSONObject(0);
		JSONArray binding = JSONArray.fromObject(bind_json);
		List<String> read = (List<String>) JSONArray.toCollection(binding.getJSONObject(0).getJSONArray("read"));
		List<String> write = (List<String>) JSONArray.toCollection(binding.getJSONObject(0).getJSONArray("write"));
		
		HumanTaskInterfaceGenerator gen = new HumanTaskInterfaceGenerator();
		String html = gen.generate(entity, read, write);
		
		URL location = HumanTaskGeneratorTest.class.getProtectionDomain().getCodeSource().getLocation();
		File testfile = new File(FilenameUtils.concat(location.getPath(), "../../src/main/webapp/testht.html"));
		//FileUtils.writeStringToFile(testfile, html);
		System.out.println(binding.toString(2));

	}

}
