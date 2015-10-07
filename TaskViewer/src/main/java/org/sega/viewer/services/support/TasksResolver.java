package org.sega.viewer.services.support;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sega.viewer.utils.Base64Util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
public class TasksResolver {
    private JSONArray tasksJson;
    private List<TaskType> tasks = null;

    public TasksResolver(String bindingResult) throws UnsupportedEncodingException {
        this.setTasks(bindingResult);
    }

    public void setTasks(String json) throws UnsupportedEncodingException {
        this.tasksJson = new JSONArray(Base64Util.decode(json));
    }

    public List<TaskType> getTasks(){
        this.tasks = new ArrayList<>();

        for (int i=0;i<this.tasksJson.length();++i){
            JSONObject taskJson = this.tasksJson.getJSONObject(i);
            TaskType task = new TaskType();
            task.setId(taskJson.getString("task"));
            task.setAutoGenerate(taskJson.getBoolean("autoGenerate"));
            task.setSyncPoint(taskJson.getBoolean("syncPoint"));

            List<String> reads = new ArrayList<>();
            if(taskJson.has("read")) {
                JSONArray readsJson = taskJson.getJSONArray("read");
                for (int j=0;j<readsJson.length();++j){
                    reads.add(readsJson.getString(j));
                }
            }
            task.setReads(reads);

            List<String> writes = new ArrayList<>();
            if(taskJson.has("write")) {
                JSONArray writesJson = taskJson.getJSONArray("write");
                for (int j = 0; j < writesJson.length(); ++j) {
                    writes.add(writesJson.getString(j));
                }
            }
            task.setWrites(writes);
            tasks.add(task);
        }

        return tasks;
    }

    public TaskType getTask(String taskId){
        if (tasks == null){
            this.getTasks();
        }

        for (TaskType task : tasks){
            if (task.getId().equals(taskId))
                return task;
        }

        return null;
    }
}
