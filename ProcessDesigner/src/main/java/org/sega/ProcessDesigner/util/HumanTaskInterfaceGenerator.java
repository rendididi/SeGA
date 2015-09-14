package org.sega.ProcessDesigner.util;

import java.awt.Button;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.Object;
import java.util.ArrayList;

import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.xml.transform.OutputKeys;

import org.jsoup.nodes.Node;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HumanTaskInterfaceGenerator {
	
	//对应js里的document对象
	private static Document doc =null;
	
	//main函数测试用
	public static void main(String args[]) throws IOException{
		
		String jsonstr = getEntity("entity.json");
		JSONArray ja = JSONArray.fromObject(jsonstr);
		JSONObject json = ja.getJSONObject(0);
		
		
		System.out.println(json);
		
		//载入网页模板
		File input = new File("htmlTemplate.html");
		
		//Jsoup解析网页模板
		try {
			doc = Jsoup.parse(input,"UTF-8");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//静态方法PageMaker.pageMaker(json)
		Document resultDoc = pageMaker(json);
		
		
		

		//
		try {
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("file2.html"),"UTF-8");
			String docStr = resultDoc.toString();
			//转换字符
			docStr=docStr.replace("&lt;","<");
			docStr=docStr.replace("&gt;",">");
			System.out.println(docStr);
			
			out.write(docStr);
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//生成文本域，name为entity的name，enable为设置表单是否可编辑
	public static Element textareaMaker(String name,Boolean enable){
		
		Element p = doc.createElement("div").addClass("form-group");
		Element label = createLabel(name);
		Element textarea = doc.createElement("textarea").addClass("form-control");
		Element div = doc.createElement("div").addClass("col-sm-6");
		if(!enable){
			textarea.attr("readonly","readonly");
		}
		div.appendChild(textarea);
		p.appendChild(label);
		p.appendChild(div);
		
		return p;
	}
	
	//重载生成文本域方法，默认可编辑
	public  static Element textareaMaker(String name){
		
		Element p = textareaMaker(name, true);
		
		return p;
	}
	public static Element textInputMaker(String name,boolean b){
		
		Element p = doc.createElement("div").addClass("form-group");
		Element label = createLabel(name);
		Element input = doc.createElement("input").addClass("form-control");
		Element div = doc.createElement("div").addClass("col-sm-6");
		input.attr("type", "text");
		div.appendChild(input);
		p.appendChild(label);
		p.appendChild(div);
		
		return p;
		
	}
	
	public static Element textInputMaker(String name){
		Element p = textInputMaker(name, true);
		
		return p;
	}
	
	public static Element selectMaker(String name,Boolean enable){
		Element p = doc.createElement("div").addClass("form-group");
		Element label = createLabel(name);
		Element div = doc.createElement("div").addClass("col-sm-6");
		Element select = doc.createElement("select").addClass("form-control");
		div.appendChild(select);
		p.appendChild(label);
		p.appendChild(div);
		
		return p;
	}
	
	public static Element setToGroup(Element p){
		org.jsoup.select.Elements es = p.getElementsByClass("col-sm-6");
		for(int i = 0; i<es.size();i++){
			es.get(i).removeClass("col-sm-6");
			es.get(i).addClass("col-sm-9");
		}
		return p;
	}
	
	public static Element selectMaker(String name){
		Element p = selectMaker(name,true);
		System.out.println(p);
		return p;
	}
	
	public void selectOpitonMaker(Element select,String name){
		Element option = doc.createElement("option").html(name);
		select.appendChild(option);
	}
	
	public Element checkboxMaker(String name,Boolean enable){
		Element div = doc.createElement("div").addClass("checkbox");
		Element ckbInput = doc.createElement("input").attr("type", "checkbox").attr("value", name);
		Element label = doc.createElement("label").appendChild(ckbInput).appendText(name);
		div.appendChild(label);
		if(!enable){
			div.addClass("disabled");
		}
		return div;
		
	}
	public Element checkboxMaker(String name){
		Element div = checkboxMaker(name, true);
		return div;
	}
	public void radioMaker(String name,String value,Boolean enable){
		Element radio = doc.createElement("input").attr("type","radio");
	}
	
	
	public static Element createLabel(String name){
		Element label = createLabel(name,true);
		
		return label;
	}
	
	public static Element createLabel(String name,boolean style){
		
		Element label = doc.createElement("label");
		if(style==true){
			label.addClass("col-sm-3 control-label");
		}
		label.appendText(name+":");
		
		return label;
	}
	
	public static void addSubmitBtn(){
		
		Element form = doc.getElementById("main-form");
		Element p = doc.createElement("p").attr("style", "text-align:center;widht:100%");
		p.appendChild(doc.createElement("button").appendText("提交").addClass("btn btn-primary"));
		p.appendChild(doc.createElement("button").appendText("重置").addClass("btn"));
		form.appendChild(p);
	}
	
	
	public static String getEntity(String filePath) throws FileNotFoundException{
		String str=new String();
		int a = 0;
		FileReader fr = new FileReader(filePath);
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
	
	private static Document pageMaker(JSONObject json,int cc,Element parentNode,String nodePath){
		
		//该方法用于递归生成表单页面
		//json：用于生成表单的JSONObject对象
		//cc：表单entity层级
		//parentNode：当前父节点
		//nodePath：从跟节点到该节点的路径
		
		Element parent = parentNode;
		String name = json.getString("name");
		String type = "attribute";
		String formType=new String();
		String path;
		path = nodePath+"\\"+name;//当前结点path
		
		JSONArray children = null;
		
		if(json.get("type")!=null){
			type = json.getString("type");
		}
		
		if(type.equals("artifact")||type.equals("artifact_n")){
			Element h1 = doc.createElement("h"+cc);//根据层级选择标题级别
			h1.appendText(name);
			Element head = doc.createElement("div").addClass("an_head");
			Element legend = doc.createElement("legend");
			legend.appendText(name);
			
			
			Element fieldset = doc.createElement("fieldset");
			/*fieldset.appendChild(legend);*/
			if(cc>1){
				parent.appendChild(fieldset);
				parent = fieldset;
			}
			cc++;
			head.appendChild(h1);
			parent.appendChild(head);
			
			if(type.equals("artifact_n")){
				Element subFieldset = doc.createElement("fieldset");
				Element btn = head.appendElement("button").appendText("+");
				btn.attr("type","button");
				btn.addClass("btn btn-primary addbtn");
				btn.attr("onclick","add"+name+"()");
				
				Element delBtn = doc.createElement("button").attr("type", "button").append("-").addClass("btn btn-danger delbtn").attr("onclick", "selfRemove()");
				subFieldset.appendChild(delBtn);
				parent.appendChild(subFieldset);
				parent=subFieldset;
				
				
			}
			
			
			
			children = json.getJSONArray("children");
			
			for(int i = 0; i<children.size();i++){
				JSONObject js = children.getJSONObject(i);
				pageMaker(js,cc,parent,path);
			}
			
			if(type.equals("artifact_n")){
				String func = "function add"+name+"(){\n"
						+ "\tvar insertText = \'"+parent+"\';\n"
						+ "\tvar div = document.createElement(\"div\");\n"
						+ "\tdiv.innerHTML=insertText;"
						+ "\n\tevent.target.parentNode.parentNode.appendChild(div.children[0]);\n"
						+ "}";
				Element sc = doc.getElementById("sc");
				sc.appendText(func);
				System.out.println(func);
			}
			
			
		}else if(type.equals("attribute")){
			
			if(json.get("formType")!=null){
				formType = json.getString("formType");
				
				switch (formType) {
				case "text":
					parent.appendChild(textInputMaker(name).attr("path", path));
					break;

				case "textarea":
					parent.appendChild(textareaMaker(name).attr("path", path));
					break;
				case "select":
					parent.appendChild(selectMaker(name).attr("path", path));
					break;
				default:
					break;
				}
			}
			
		}else if(type.equals("group")){
			children = json.getJSONArray("children");
			Element p = doc.createElement("div").addClass("form-group");
			Element label = createLabel(name);
			p.appendChild(label);
			Element formInline = doc.createElement("div").addClass("col-xs-6");
			p.appendChild(formInline);
			parent.appendChild(p);
			parent = formInline;
			
			for(int i = 0; i<children.size();i++){
				JSONObject js = children.getJSONObject(i);
				System.out.println(parent);
				pageMaker(js,cc,parent,path);
			}
			setToGroup(p);
			p.appendChild(parent);
		}
		
		return doc;
	}
	
	//向页面中添加控件自删除方法	
	private static void addSelfRemoveScript(){
		Element script = doc.createElement("script");
		String selfRemove="function selfRemove(){event.target.parentNode.parentNode.removeChild(event.target.parentNode)}";
		
		script.appendText(selfRemove);
		Element body = doc.getElementById("body");
		body.appendChild(script);
	}
	
	public static Document pageMaker(JSONObject json){
		String path="";
		doc =  pageMaker(json,1,doc.getElementById("main-form"),path);
		addSelfRemoveScript();
		return doc;
		
	}
}
