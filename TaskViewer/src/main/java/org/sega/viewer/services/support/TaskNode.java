package org.sega.viewer.services.support;

import lombok.Data;

/**
 * Created by glp on 2015/10/19.
 */
@Data
public class TaskNode extends Node {
    private String name;
    private String description;
    private String splitMode;
    private String joinMode;
}
