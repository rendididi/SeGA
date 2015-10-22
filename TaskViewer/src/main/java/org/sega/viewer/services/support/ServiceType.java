package org.sega.viewer.services.support;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by glp on 2015/10/21.
 */

@Data
public class ServiceType {
    private String id;
    private boolean serviceUrl;
    private boolean syncPoint;

    private List<String> reads = new ArrayList<>();
    private List<String> writes = new ArrayList<>();
}
