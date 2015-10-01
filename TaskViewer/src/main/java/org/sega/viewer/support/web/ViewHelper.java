package org.sega.viewer.support.web;

import org.springframework.stereotype.Service;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Service
public class ViewHelper {
    private Long startTime;
    private String applicationEnv;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getApplicationEnv() {
        return applicationEnv;
    }

    public void setApplicationEnv(String applicationEnv) {
        this.applicationEnv = applicationEnv;
    }
}
