package org.sega.viewer.services.support;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
public class TaskType {
    private String id;
    private boolean autoGenerate;
    private boolean syncPoint;

    private List<String> reads = new ArrayList<>();
    private List<String> writes = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAutoGenerate() {
        return autoGenerate;
    }

    public void setAutoGenerate(boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }

    public boolean isSyncPoint() {
        return syncPoint;
    }

    public void setSyncPoint(boolean syncPoint) {
        this.syncPoint = syncPoint;
    }

    public List<String> getReads() {
        return reads;
    }

    public void setReads(List<String> reads) {
        this.reads = reads;
    }

    public List<String> getWrites() {
        return writes;
    }

    public void setWrites(List<String> writes) {
        this.writes = writes;
    }
}
