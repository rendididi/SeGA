package org.sega.viewer.services.support;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sega.viewer.utils.Base64Util;

import java.io.UnsupportedEncodingException;

/**
 * @author Raysmond<i@raysmond.com>.
 */
public class ProcessJsonResolver {

    private JSONObject process;

    public ProcessJsonResolver(String processJson) throws UnsupportedEncodingException {
        this.setProcess(processJson);
    }

    public void setProcess(String process) throws UnsupportedEncodingException {
        process = Base64Util.decode(process);
        this.process = new JSONObject(process);
    }

    /**
     * Find the next task id
     * Return the first task id if it has multiple next tasks
     */
    public Node getNextTask(String task) {
        JSONArray nodes = process.getJSONArray("cells");

        Node next = null;

        // Find the start node if task is NULL
        if (task == null) {
            for (int i = 0; i < nodes.length(); ++i) {
                JSONObject node = nodes.getJSONObject(i);

                if (node.getString("type").equals("sega.Start")) {
                    task = node.getString("id");
                    break;
                }
            }
        }

        if (task == null)
            return null;

        // Find the first next task id
        for (int i = 0; i < nodes.length(); ++i) {
            JSONObject node = nodes.getJSONObject(i);

            if (node.getString("type").equals("sega.Link")) {
                if (node.getJSONObject("source").getString("id").equals(task)) {
                    next = findNode(node.getJSONObject("target").getString("id"));
                }
            }
        }

        return next;
    }

    public Node findNode(String id){
        Node result = new Node();
        JSONArray nodes = process.getJSONArray("cells");

        for (int i=0;i<nodes.length();i++){
            JSONObject node = nodes.getJSONObject(i);
            if (node.getString("id").equals(id)){
                result.setId(id);
                result.setType(node.getString("type"));
                if (node.getString("type").equals("sega.Task")||node.getString("type").equals("sega.Service")){
                    JSONObject data = node.getJSONObject("attrs").getJSONObject("data");
                    result.setName(data.getString("name"));
                    result.setDescription(data.getString("description"));
                    result.setSplitMode(data.getString("splitemode"));
                    result.setJoinMode(data.getString("jointmode"));
                }
            }
        }

        return result;
    }
}
