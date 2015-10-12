package org.sega.viewer.services;

import org.json.JSONObject;
import org.sega.viewer.models.Process;
import org.springframework.stereotype.Service;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Service
public class TaskService {

    public JSONObject getTask(Process process){
        JSONObject task = null;
        JSONObject json = new JSONObject(process.getBindingJson());

        return task;
    }
}
