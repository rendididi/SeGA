package org.sega.viewer;

import net.minidev.json.JSONValue;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.json.JSONObject;
import org.sega.viewer.models.*;
import org.sega.viewer.models.Process;
import org.sega.viewer.services.ProcessInstanceService;
import org.sega.viewer.utils.Base64Util;

import java.io.UnsupportedEncodingException;

/**
 * Created by glp on 2015/10/6.
 */
public class TestJSONObject {
    public static void main(String[] args) throws ParseException, UnsupportedEncodingException {

        JSONObject json = new JSONObject("{a:{b:1}}");
        JSONObject json2 = json.getJSONObject("a");
        json2.put("b",2);
        System.out.println(json.toString(2));

        JSONParser jp = new JSONParser(JSONParser.MODE_PERMISSIVE);
        net.minidev.json.JSONObject o1 = (net.minidev.json.JSONObject) jp.parse("{a:{b:1}}");
        net.minidev.json.JSONObject o2 = (net.minidev.json.JSONObject) jp.parse("{a:{b:3}}");

        o1.putAll(o2);
        System.out.println(o1.toJSONString());

        ProcessInstanceService service = new ProcessInstanceService();
        ProcessInstance instance = new ProcessInstance();
        Process process = new Process();

        service.writeEntity(new JSONObject("{a:'1'}"), instance, "");
        service.writeEntity(new JSONObject("{a:{b:[{c:'1'},{c:'2'}],d:3}}"), instance, "");
        System.out.println(instance.getEntity());

        process.setEntityJSON(Base64Util.encode("[{id:'a',type:'artifact',children:[{id:'b',type:'artifact_n',children:[{id:'c',type:'attribute'}]},{id:'d',type:'attribute'}]}]"));
        process.setBindingJson(Base64Util.encode("[{task:'t1',autoGenerate:'true',syncPoint:'true',read:['c']}]"));
        instance.setProcess(process);
        System.out.println(service.readEntity(instance, "t1").toString());

        process.setBindingJson(Base64Util.encode("[{task:'t1',autoGenerate:'true',syncPoint:'true',read:['d']}]"));
        System.out.println(service.readEntity(instance, "t1").toString());

    }
}
