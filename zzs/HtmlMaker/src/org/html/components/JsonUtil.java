package org.html.components;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	private static List list = new ArrayList();
	private static void func1(JSONObject json){
		
		String name=json.getString("name");
		list.add(name);
		if(json.get("children")!=null){
			JSONArray children = (JSONArray) json.get("children");
			for(int i = 0 ; i<children.size();i++){
				func(children.getJSONObject(i));
			}
		}
		
	}
	
	
	public static List func(JSONObject json){
		func1(json);
		return list;
	}
}
