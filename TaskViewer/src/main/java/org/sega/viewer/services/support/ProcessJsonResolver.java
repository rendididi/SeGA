package org.sega.viewer.services.support;

import org.json.JSONObject;
import org.sega.viewer.utils.Base64Util;

import java.io.UnsupportedEncodingException;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
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
}
