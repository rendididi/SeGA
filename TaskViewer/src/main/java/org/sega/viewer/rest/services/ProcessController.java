package org.sega.viewer.rest.services;

import org.sega.viewer.services.IModel;
import org.sega.viewer.models.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Raysmond on 9/5/15.
 */
@RestController
public class ProcessController {

    @Autowired
    private IModel model;

    @RequestMapping(value = "/processes")
    public List<Process> all() {
        return model.findAll(Process.class);
    }

    @RequestMapping("/processes/{id}")
    public Process getMessage(@PathVariable Long id) {
        return model.get(Process.class, id);
    }

}
