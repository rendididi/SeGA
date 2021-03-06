package org.sega.viewer.services.support;

import lombok.Data;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Data
public class Node {
    private String id;
    private String type;
    private String name;
    private String description;
    private String splitMode;
    private String joinMode;
}
