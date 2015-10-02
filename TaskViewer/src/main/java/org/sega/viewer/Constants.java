package org.sega.viewer;

import java.util.*;

import org.sega.viewer.utils.HashMapBuilder;


public final class Constants {

    public static final String ENV_PRODUCTION = "production";
    public static final String ENV_DEVELOPMENT = "development";

    public final static Map<String, String> STEPS_MAP =
            new HashMapBuilder<String, String>()
                    .put("step-process-select", "Select Process Template")
                    .put("step-custom-entity", "Customize Entity")
                    .put("step-dbtemplate-edb-mapping", "DB Template-EDB Mapping")
                    .put("step-entity-edb-mapping", "Entity-EDB Mapping")
                    .put("step-custom-process", "Customize Process")
                    .put("step-bind-process", "Service Binding")
                    .put("step-publish", "Publish")
                    .build();
}
