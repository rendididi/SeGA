package org.sega.viewer.rest.services;

import org.sega.viewer.repositories.ProcessRepository;
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
    private ProcessRepository processRepository;

    @RequestMapping(value = "/processes")
    public List<Process> all() {
        return processRepository.findAll();
    }

    @RequestMapping("/processes/{processId:\\d+}")
    public Process getMessage(@PathVariable Long processId) {
        return processRepository.findOne(processId);
    }

}
