package org.sega.viewer.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sega.viewer.models.Log;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.repositories.LogRepository;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.repositories.ProcessRepository;
import org.sega.viewer.services.ProcessInstanceService;
import org.sega.viewer.services.support.MysqlConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class HomeController {

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;
    @Autowired
    private ProcessRepository processRepository;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private ProcessInstanceService processInstanceService;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private static final int pageSize = 6;
    String city = null;
    private Connection connection;

    @RequestMapping(value = "", method = GET)
    public String index(Principal principal, @RequestParam(defaultValue = "0") int page, Model model) {
        page = page < 0 ? 0 : page;
        Page<ProcessInstance> instances = processInstanceRepository.findByNextTaskNot(
                ProcessInstance.STATE_COMPLETED, new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
        city = getCity();
        List<ProcessInstance> newInstances = new ArrayList<ProcessInstance>();
        if (instances != null && instances.getSize() != 0) {
            for (ProcessInstance instance : instances) {
                if (instance.getProcess().getCity().equals(city) && !processInstanceService.getNextTaskName(instance).equals(ProcessInstance.STATE_COMPLETED)) {
                    newInstances.add(instance);
                }
            }
        }
        Map<String, String> taskNameMap = new HashMap<String, String>();
        for (ProcessInstance instance : instances) {
            taskNameMap.put(instance.getNextTask(), processInstanceService.getNextTaskName(instance));
            if (processInstanceService.getNextTaskName(instance).equals(ProcessInstance.STATE_COMPLETED)) {
                try {
                    instance.setNextTask(ProcessInstance.STATE_COMPLETED);
                    processInstanceService.updateInstance(instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        model.addAttribute("processType", 0);
        model.addAttribute("instances", newInstances);
        model.addAttribute("taskNames", taskNameMap);
        model.addAttribute("totalInstances", newInstances.size());
        model.addAttribute("page", page + 1);
        model.addAttribute("totalPages", newInstances.size() / pageSize);
        return principal != null ? "home/index" : "users/signin";
    }

    @RequestMapping(value = "/completed", method = GET)
    public String indexCompleted(Principal principal, @RequestParam(defaultValue = "0") int page, Model model) {
        page = page < 0 ? 0 : page;
        Page<ProcessInstance> instances = processInstanceRepository.findByNextTask(
                ProcessInstance.STATE_COMPLETED, getCity(),new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
        Map<String, String> taskNameMap = new HashMap<String, String>();
        for (ProcessInstance instance : instances) {
            taskNameMap.put(instance.getNextTask(), processInstanceService.getNextTaskName(instance));
        }
        model.addAttribute("processType", 0);
        model.addAttribute("instances", instances);
        model.addAttribute("taskNames", taskNameMap);
        model.addAttribute("totalInstances", instances.getTotalElements());
        model.addAttribute("page", page + 1);
        model.addAttribute("totalPages", instances.getTotalPages());
        return principal != null ? "home/completed" : "users/signin";
    }

    //completed_detail
    @RequestMapping(value = "/completed_detail/{instanceId:\\d+}", method = RequestMethod.GET)
    public String showDetail(Principal principal, @PathVariable Long instanceId, Model model) {
        ProcessInstance instance = processInstanceRepository.findOne(instanceId);
        String operateContent = instance.getOperatorandtime();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < operateContent.split(";").length; i++) {
            list.add(operateContent.split(";")[i]);
        }
        model.addAttribute("instance", instance);
        model.addAttribute("operate", list);
        try {
            MysqlConnection mysql = new MysqlConnection(instance.getProcess().getDbconfig());
            connection = mysql.open();
            Statement stm = connection.createStatement();
            String sql = "select * from tpg_gzfsqspb t where t.YWSLID = " + instance.getBusinessId();
            ResultSet rs = stm.executeQuery(sql);

            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            HashMap map = new HashMap();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    if (rs.getObject(i) == null) {
                        map.put(md.getColumnName(i), " ");
                    } else {
                        map.put(md.getColumnName(i), rs.getObject(i) + " ");
                    }
                }
            }


            model.addAttribute("map", map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return principal != null ? "home/completed_detail" : "users/signin";
    }

    //selection by wxf
    @SuppressWarnings("null")
    @RequestMapping(value = "/type/{processType:\\d+}", method = RequestMethod.GET)
    public String selectTask(@PathVariable String processType, Principal principal, @RequestParam(defaultValue = "0") int page, Model model) {
        page = page < 0 ? 0 : page;
        city = getCity();
        Page<ProcessInstance> instances = processInstanceRepository.findByNextTaskNot(
                ProcessInstance.STATE_COMPLETED, new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
        List<ProcessInstance> newInstances = new ArrayList<ProcessInstance>();
        if (instances != null && instances.getSize() != 0) {
            for (ProcessInstance instance : instances) {
                if (instance.getProcess().getCity().equals(city) && instance.getProcess().getType().equals(processType)) {
                    newInstances.add(instance);
                }
            }
        }

        Map<String, String> taskNameMap = new HashMap<String, String>();

        for (ProcessInstance instance : newInstances) {
            taskNameMap.put(instance.getNextTask(), processInstanceService.getNextTaskName(instance));
        }
        model.addAttribute("processType", processType);
        model.addAttribute("instances", newInstances);
        model.addAttribute("taskNames", taskNameMap);
        model.addAttribute("totalInstances", newInstances.size());
        model.addAttribute("page", page + 1);
        model.addAttribute("totalPages", (newInstances.size()) / pageSize);
        return principal != null ? "home/index" : "users/signin";
    }


    //show console
    @RequestMapping(value = "/showConsole", method = RequestMethod.GET)
    public String showConsole(@RequestParam(defaultValue = "0") int page, Principal principal, Model model) {
        page = page < 0 ? 0 : page;
        Page<Log> logs1 = logRepository.findByType("11", new PageRequest(page, pageSize, Sort.Direction.DESC, "date"));
        Page<Log> logs2 = logRepository.findByType("22", new PageRequest(page, pageSize, Sort.Direction.DESC, "date"));
        Page<Log> logs3 = logRepository.findByType("33", new PageRequest(page, pageSize, Sort.Direction.DESC, "date"));
        model.addAttribute("logs1", logs1);
        model.addAttribute("logs2", logs2);
        model.addAttribute("logs3", logs3);
        model.addAttribute("page", page + 1);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages1", (logs1.getSize()) / pageSize);
        model.addAttribute("totalPages2", (logs2.getSize()) / pageSize);
        model.addAttribute("totalPages3", (logs3.getSize()) / pageSize);
        return principal != null ? "home/showConsole" : "users/signin";
    }

    public String getCity() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
        String city = (String) request.getSession().getAttribute("city");
        return city;
    }

}
