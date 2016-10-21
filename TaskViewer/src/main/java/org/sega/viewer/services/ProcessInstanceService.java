package org.sega.viewer.services;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sega.viewer.models.Process;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.services.support.ProcessJsonResolver;
import org.sega.viewer.services.support.TaskType;
import org.sega.viewer.services.support.TasksResolver;
import org.sega.viewer.utils.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Raysmond<i@raysmond.com>.
 */
@Service
public class ProcessInstanceService {
    @Autowired
    private ProcessInstanceRepository instanceRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProcessInstanceService.class);

    public List<ProcessInstance> getProcessInstances(Process process){
        return instanceRepository.findAllByProcess(process, new Sort(Sort.Direction.DESC, "createdAt"));
    }

    public JSONObject readEntity(ProcessInstance processInstance, String taskId) throws UnsupportedEncodingException {
        TasksResolver tasksResolver = getTaskResolver(processInstance.getProcess().getBindingJson(), processInstance.getProcess().getProcessJSON());
        if (tasksResolver == null)
            return null;
        TaskType taskType = tasksResolver.getTask(taskId);
        List<String> reads = taskType.getReads();
        List<String> writes = taskType.getWrites();

        Process process = processInstance.getProcess();
        JSONObject entitySchema = (new JSONArray(Base64Util.decode(process.getEntityJSON()))).getJSONObject(0);

        markUpdateStat(entitySchema, reads, "read");
        JSONObject values = new JSONObject(processInstance.getEntity());
        extractValues(values, entitySchema);

        return values;
    }

    public void writeEntity(JSONObject entity, ProcessInstance processInstance, String taskId){
        /*
        TasksResolver tasksResolver = getTaskResolver(processInstance.getJtangProcess().getBindingJson());
        if (tasksResolver == null)
            return;
        TaskType taskType = tasksResolver.getTask(taskId);
        List<String> writes = taskType.getWrites();
        Process process = processInstance.getJtangProcess();
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


    @Transactional
    public ProcessInstance updateInstance(ProcessInstance processInstance) throws UnsupportedEncodingException {
//        ProcessJsonResolver processJsonResolver = new ProcessJsonResolver(processInstance.getProcess().getProcessJSON());
//        processInstance.setNextTask(processJsonResolver.getNextTask(processInstance.getNextTask()).getId());

        return instanceRepository.save(processInstance);
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


    public ProcessInstance createProcessInstance(Process process) throws UnsupportedEncodingException {
        ProcessInstance instance = new ProcessInstance(process);
//        artifactService.createMainArtifact(instance);

        // TODO
        instance.setEntity(createEntity(process));

        ProcessJsonResolver processJsonResolver = new ProcessJsonResolver(process.getProcessJSON());
        instance.setNextTask(processJsonResolver.getNextTask(null).getId());

        instanceRepository.save(instance);

        return instance;
    }

    public String getNextTaskName(ProcessInstance instance) {
        JSONObject task=null;
        try {
            JSONObject processSchema = new JSONObject(Base64Util.decode(instance.getProcess().getProcessJSON()));
            JSONArray tasks = processSchema.getJSONArray("cells");
            for(int i=0, len=tasks.length(); i<len; i++){
                task= tasks.getJSONObject(i);
                if(task.getString("id").equals(instance.getNextTask())){
                	logger.debug("is compelted ======="+task.getString("type"));
                    if(task.getString("type").equals("sega.End"))
                        return "已办结";
                    else if(task.getString("type").equals("sega.Task"))
                        return task.getJSONObject("attrs").getJSONObject("data").getString("name");
                }
            }
        } catch (UnsupportedEncodingException e) {
            return instance.getNextTask();
        } catch (org.json.JSONException e) {
            System.out.println(task);
        }
        return instance.getNextTask();
    }

    private String createEntity(Process process) {

        return "{}";
    }

    private TasksResolver getTaskResolver(String bingResultJson, String processJSON){
        try {
            return new TasksResolver(bingResultJson, processJSON);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
