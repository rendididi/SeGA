package org.sega.viewer.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.sega.viewer.models.Log;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.repositories.LogRepository;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.services.EdbService;
import org.sega.viewer.services.JtangEngineService;
import org.sega.viewer.services.ProcessInstanceService;
import org.sega.viewer.services.support.MysqlConnection;
import org.sega.viewer.services.support.TasksResolver;
import org.sega.viewer.services.support.UnSupportEdbException;
import org.sega.viewer.utils.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 * @author Raysmond<i@raysmond.com>
 */
@Controller
@RequestMapping("processes/instances")
public class InstanceController {
    @Autowired
    private ProcessInstanceService processInstanceService;

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @Autowired
    private JtangEngineService jtangEngineService;

    @Autowired
    private EdbService edbService;
    
    @Autowired
    private LogRepository logRepository;
    

    private static final String TASK_TEMPLATE = "templates/fragments/humantask/%s/%s.html";

    private static final Logger logger = LoggerFactory.getLogger(InstanceController.class);

    @RequestMapping(value = "{instanceId:\\d+}/task/{taskId}", method = RequestMethod.GET)
    public String showTask(@PathVariable Long instanceId, @PathVariable String taskId, Model model)
            throws UnsupportedEncodingException {
        ProcessInstance instance = processInstanceRepository.findOne(instanceId);
        String path = String.format(TASK_TEMPLATE, instance.getProcess().getId(), taskId);

        logger.debug("Resolved task template file: " + path);

        JSONObject entity = processInstanceService.readEntity(instance, taskId);

        model.addAttribute("entity", entity.toString());
        model.addAttribute("schema", Base64Util.decode(instance.getProcess().getEntityJSON()));
        model.addAttribute("instance", instance);
        model.addAttribute("templatePath", path);
        model.addAttribute("taskId", taskId);
        model.addAttribute("taskName",processInstanceService.getNextTaskName(instance));
        model.addAttribute("templateHtml", readTaskTemplate(path));

        return "instances/task";
    }

    // edb sync test
    @RequestMapping(value = "{instanceId:\\d+}/sync_edb", method = RequestMethod.GET)
    @ResponseBody
    public String syncTest(@PathVariable Long instanceId) throws ClassNotFoundException, SQLException, UnSupportEdbException, UnsupportedEncodingException {
        ProcessInstance instance = processInstanceRepository.findOne(instanceId);

        edbService.sync(instance);

        return "";
    }

    @RequestMapping(
            value = "{instanceId:\\d+}/task/{taskId}",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String commitTask(@PathVariable Long instanceId, @PathVariable String taskId, @RequestBody String entity)
            throws UnsupportedEncodingException, MalformedURLException {
        ProcessInstance instance = processInstanceRepository.findOne(instanceId);
        processInstanceService.writeEntity(new JSONObject(entity), instance, taskId);

        try { 
            TasksResolver tasksResolver = new TasksResolver(instance.getProcess().getBindingJson(), instance.getProcess().getProcessJSON());
            
            //Commit to JTang Server
            String nextTask = jtangEngineService.commitTask(instance);
            //String nextTask = tasksResolver.getNextTask(taskId);
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
            HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
            String username = (String) request.getSession().getAttribute("username");
            //persist instance
            Map map = new HashMap();
            String operate = "";
            if(instance.getOperatorandtime() != null){
            	operate = instance.getOperatorandtime();
            }
            String taskName = processInstanceService.getNextTaskName(instance);
            Date date=new Date(); 
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
            //process_instance 表中  operatorandtime字段的格式  当前环节名称/张三,2016-10-27
            String newStr = taskName +"/"+username+","+df.format(date)+";";
            
            //获取edb的name
            MysqlConnection mysql = new MysqlConnection(instance.getProcess().getDbconfig());
	        Connection connection = mysql.open();
	        Statement stm = connection.createStatement();
	        String sql = "select * from tpg_gzfsqspb t where t.YWSLID = "+instance.getBusinessId();
	        ResultSet rs = stm.executeQuery(sql);
	        
	        ResultSetMetaData md = rs.getMetaData();
	        int columnCount = md.getColumnCount();
	        String edb_name="";
	        while(rs.next()){ 
	        	edb_name = rs.getString("SQRXM");
	        }
            
            //记录日志操作
            Log log = new Log();
            log.setDate(df.format(date));
            log.setContent(edb_name+"业务的"+taskName+"环节,操作人是："+username+",操作时间是："+df.format(date));
            log.setClassName("InstanceController");
            log.setDescriptions("业务办理");
            log.setType("11");
            log.setOperationType("业务操作");
            logRepository.save(log);
            
            instance.setNextTask(nextTask);
            instance.setOperatorandtime(operate + newStr);
            instance.setEdb_name(edb_name);
            processInstanceService.updateInstance(instance);
            
            //Sync to EDB
            if (tasksResolver.getTask(taskId).isSyncPoint()) {
                //DEMO:edbService.sync(instance);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "{\"success\": false}";
        }

        return "{\"success\": true}";
    }

    /**
     * Read task template file
     *
     * @param file template file path
     * @return raw content of file
     */
    private String readTaskTemplate(String file) {
        String result = "";
        ClassLoader classLoader = getClass().getClassLoader();

        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(file), "UTF-8");
        } catch (IOException e) {
            logger.error("Failed to read task template file " + file + ", got exception: " + e);
        }
        return result;
    }


}
