package org.sega.viewer.common;

import java.util.*;

import org.sega.viewer.utils.HashMapBuilder;

/**
 * @author: Raysmond
 * <p/>
 * A constant class
 */
public final class Constant {

    public final static Map<String, String> STEPS = new HashMapBuilder<String, String>()
            .put("step-process-select", "Select Process Template")
            .put("step-custom-entity", "Customize Entity")
            .put("step-dbtemplate-edb-mapping", "DB Template-EDB Mapping")
            .put("step-entity-edb-mapping", "Entity-EDB Mapping")
            .put("step-custom-process", "Customize Process")
            .put("step-bind-process", "Service Binding")
            .put("step-publish", "Publish")
            .build();

    public final static String KEY_USER_TYPE = "user_type";

    public enum UserType {
        EXPERT, DEVELOPER, OPERATOR
    }
}
