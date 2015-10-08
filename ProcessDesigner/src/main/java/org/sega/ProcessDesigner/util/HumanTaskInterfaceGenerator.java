package org.sega.ProcessDesigner.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HumanTaskInterfaceGenerator {
	
	private Configuration fm_cfg;
	
	public HumanTaskInterfaceGenerator() throws IOException{
		fm_cfg = new Configuration(Configuration.VERSION_2_3_22);
		fm_cfg.setDirectoryForTemplateLoading(new File(HumanTaskInterfaceGenerator.class.getResource("../HumantaskTemplate").getFile()));
		fm_cfg.setDefaultEncoding("UTF-8");
		fm_cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}
	
	public String generate(final JSONObject root, List<String> read, List<String> write) throws Exception {
		String result = "";
		JSONObject e = JSONObject.fromObject(root);
		markUpdateStat(e, read, "read");
		markUpdateStat(e, write, "write");
		result = generateElement(e, new ArrayList<Pair<String,String>>());
		/*
		Map<String, Object> tpl_model = new HashMap<String, Object>();
		tpl_model.put("content", result);
		result = getProcessedHtml(tpl_model, "test.ftl");
		*/
		return result;
	}


	public String generateElement(JSONObject root, final List<Pair<String,String>> path) throws Exception {
		String result = "";
		boolean read = root.optBoolean("read"),
				write = root.optBoolean("write");
		Map<String, Object> tpl_model = new HashMap<String, Object>();

		if(!(read || write))
			return result;
		
		tpl_model.put("read", read);
		tpl_model.put("write", write);
		tpl_model.put("path", getPathString(path));
		tpl_model.put("id", root.getString("id"));
		tpl_model.put("text", root.getString("text"));

		if(	"artifact".equals(root.getString("type")) || 
			"artifact_n".equals(root.getString("type")) ||
			"group".equals(root.getString("type")) ){
			tpl_model.put("path", getPathString(path));
			tpl_model.put("pathForClick", getPathStringForClick(path));
			
			ArrayList<Pair<String,String>> currentPath = new ArrayList<Pair<String,String>>(path);
			currentPath.add(Pair.of(root.getString("id"),root.getString("type")));
			tpl_model.put("currentPath", getPathString(currentPath));
			
			StringBuilder sb = new StringBuilder();
			for(int i=0,len=root.getJSONArray("children").size(); i<len;i++){
				JSONObject child = root.getJSONArray("children").getJSONObject(i);
				sb.append(generateElement(child, currentPath));
			}
			tpl_model.put("children", sb.toString());
		}
		
		if("artifact".equals(root.getString("type"))){
			result = getProcessedHtml(tpl_model, "artifact.ftl");
		}else if("artifact_n".equals(root.getString("type"))){
			result = getProcessedHtml(tpl_model, "artifact_n.ftl");
		}else if("group".equals(root.getString("type"))){
			result = getProcessedHtml(tpl_model, "group.ftl");
		}else if("key".equals(root.getString("type"))){
			result = getProcessedHtml(tpl_model, "key.ftl");
		}else if("attribute".equals(root.getString("type"))){
			String value_type = root.getJSONObject("data").getString("value_type");
			result = getProcessedHtml(tpl_model, "attribute-"+value_type+".ftl");
		}


		
		return result;
	}
	
	private String getPathString(List<Pair<String,String>> path){
		StringBuilder sb = new StringBuilder();
		int numOfArtifactN = 0;
		for(int i=0;i<path.size();i++){
			if("artifact_n".equals(path.get(i).getRight())){
				numOfArtifactN++;
			}
		}
		for(int i=0;i<path.size();i++){
			if(i==0){
				sb.append(path.get(i).getLeft());
			}else{
				if("artifact_n".equals(path.get(i).getRight())){
					numOfArtifactN--;
					sb.append("."+path.get(i).getLeft()+"["+StringUtils.repeat("$parent.", numOfArtifactN)+"$index]");
				}else{
					sb.append("."+path.get(i).getLeft());
				}
			}
		}
		return sb.toString();
	}

	private String getPathStringForClick(List<Pair<String,String>> path){
		StringBuilder sb = new StringBuilder();
		int numOfArtifactN = 0;
		for(int i=0;i<path.size();i++){
			if("artifact_n".equals(path.get(i).getRight())){
				numOfArtifactN++;
			}
		}
		for(int i=0;i<path.size();i++){
			if(i==0){
				sb.append(path.get(i).getLeft());
			}else{
				if("artifact_n".equals(path.get(i).getRight())){
					numOfArtifactN--;
					sb.append("."+path.get(i).getLeft()+"['+"+StringUtils.repeat("$parent.", numOfArtifactN)+"$index+']");
				}else{
					sb.append("."+path.get(i).getLeft());
				}
			}
		}
		return sb.toString();
	}

	
	private String getProcessedHtml(Map<String, Object> model, String tpl_name) throws Exception{
		Template tpl = fm_cfg.getTemplate(tpl_name);
		Writer out = new StringWriter();
		tpl.process(model, out);
		return out.toString();
	}
	
	private boolean markUpdateStat(JSONObject e, List<String> list, String mode) {
		String type = e.getString("type");
		String id = e.getString("id");
		boolean result = false;
		
		if(list.contains(id)){
			result = true;
		}
		
		if(e.has("children")){
			JSONArray children = e.getJSONArray("children");
			for(int i=0;i<children.size();i++){
				JSONObject child = children.getJSONObject(i);
				if(markUpdateStat(child, list, mode)){
					result = true;
				}
			}
		}
		
		if(result){
			e.put(mode, result);
		}
		return result;
	}
	
	public static class UnexpectedEntityFormat extends Exception{
		
	}

}
