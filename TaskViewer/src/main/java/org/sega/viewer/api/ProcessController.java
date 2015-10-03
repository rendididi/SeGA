package org.sega.viewer.api;

import org.sega.viewer.repositories.ProcessRepository;
import org.sega.viewer.models.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Raysmond<jiankunlei@gmail.com>
 */
@RestController
@RequestMapping("api/processes")
public class ProcessController {
    @Autowired
    private ProcessRepository processRepository;

    @RequestMapping(value = "")
    public List<Process> all() {
        return processRepository.findAll();
    }

    @RequestMapping("{processId:\\d+}")
    public Process getMessage(@PathVariable Long processId) {
        return processRepository.findOne(processId);
    }

}
