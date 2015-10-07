package org.sega.viewer.services;

import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.models.Process;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.services.support.TaskType;
import org.sega.viewer.services.support.TasksResolver;
import org.sega.viewer.utils.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Service
public class ProcessInstanceService {
    @Autowired
    private ProcessInstanceRepository instanceRepository;

    @Autowired
    private ArtifactService artifactService;

    // todo
    public JSONObject readEntity(ProcessInstance processInstance, String taskId) throws UnsupportedEncodingException {
        JSONObject values = new JSONObject();
        TasksResolver tasksResolver = getTaskResolver(processInstance.getProcess().getBindingJson());
        if (tasksResolver == null)
            return null;
        TaskType taskType = tasksResolver.getTask(taskId);
        List<String> reads = taskType.getReads();
        Process process = processInstance.getProcess();
        JSONObject entitySchema = (new JSONArray(Base64Util.decode(process.getEntityJSON()))).getJSONObject(0);

        markUpdateStat(entitySchema, reads, "read");
        values = new JSONObject(processInstance.getEntity());
        extractValues(values, entitySchema);

        return values;
    }



    // todo
    public void writeEntity(JSONObject entity, ProcessInstance processInstance, String taskId){
        /*
        TasksResolver tasksResolver = getTaskResolver(processInstance.getProcess().getBindingJson());
        if (tasksResolver == null)
            return;
        TaskType taskType = tasksResolver.getTask(taskId);
        List<String> writes = taskType.getWrites();
        Process process = processInstance.getProcess();
        JSONObject entitySchema = (new JSONArray(process.getEntityJSON())).getJSONObject(0);

        markUpdateStat(entitySchema, writes, "write");
        */
        // just merge here, do not validate
        try {
            JSONParser jp = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
            net.minidev.json.JSONObject o1 = (net.minidev.json.JSONObject) jp.parse(processInstance.getEntity()!=null?processInstance.getEntity().toString():"{}");
            net.minidev.json.JSONObject o2 = (net.minidev.json.JSONObject) jp.parse(entity.toString());
            o1.putAll(o2);
            processInstance.setEntity(o1.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    /*  Mark 'read'/'write' flag on schema json
    *
    *   j1_1--+--j2_1
    *         |
    *         +--j2_2-----j3_1(read)
    *
    *   into
    *
    *   j1_1(read)--+--j2_1
    *               |
    *               +--j2_2(read)-----j3_1(read)
    *
    * */
    private boolean markUpdateStat(JSONObject schema, List<String> list, String mode) {
        String type = schema.getString("type");
        String id = schema.getString("id");
        boolean result = false;

        if(list.contains(id)){
            result = true;
        }

        if(schema.has("children")){
            JSONArray children = schema.getJSONArray("children");
            for(int i=0;i<children.length();i++){
                JSONObject child = children.getJSONObject(i);
                if(markUpdateStat(child, list, mode)){
                    result = true;
                }
            }
        }

        if(result){
            schema.put(mode, result);
        }
        return result;
    }

    /*  Given marked schema, extract values for read from entity
    *   by removing unmarked attributes
    */
    private void extractValues(JSONObject values, JSONObject schema) {
        String id = schema.getString("id");
        String type = schema.getString("type");
        boolean read = schema.optBoolean("read");
        if(read) {
            if("artifact".equals(type) || "group".equals(type)){
                if(values.has(id)){
                    JSONArray schema_children = schema.getJSONArray("children");
                    for(int i=0; i<schema_children.length(); i++) {
                        extractValues(values.getJSONObject(id), schema_children.getJSONObject(i));
                    }
                }
            }else if("artifact_n".equals(type)){
                if(values.has(id)) {
                    JSONArray value_children = values.getJSONArray(id);
                    JSONArray schema_children = schema.getJSONArray("children");
                    for(int j=0;j<value_children.length();j++){
                        for(int i=0; i<schema_children.length(); i++) {
                            extractValues(value_children.getJSONObject(j), schema_children.getJSONObject(i));
                        }
                    }
                }
            }

        }else {
            if(values.has(id))
                values.remove(id);
        }

    }

    // designed changed, so, for now, not used
    private boolean getAttributePath(JSONObject schema, String item, List<String> path) {

        if(schema.getString("id").equals(item)){
            path.add(item);
            return true;
        }
        if(Arrays.asList("artifact","artifact_n","group").contains(schema.getString("type"))){
            JSONArray children = schema.getJSONArray("children");
            for(int i=0,len=children.length();i<len;i++) {
                if(getAttributePath(children.getJSONObject(i), item, path)){
                    path.add(schema.getString("id"));
                    return true;
                }
            }
        }

        return false;
    }


    public ProcessInstance createProcessInstance(Process process){
        ProcessInstance instance = new ProcessInstance(process);
//        artifactService.createMainArtifact(instance);

        // TODO
        instance.setEntity(createEntity(process));
        instanceRepository.save(instance);
        return instance;
    }

    private String createEntity(Process process) {

        return "{}";
    }

    private TasksResolver getTaskResolver(String bingResultJson){
        try {
            return new TasksResolver(bingResultJson);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

}