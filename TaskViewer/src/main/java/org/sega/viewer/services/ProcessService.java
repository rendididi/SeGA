package org.sega.viewer.services;

import org.sega.viewer.repositories.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.sega.viewer.models.Process;

import java.util.List;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Service
public class ProcessService {
    @Autowired
    private ProcessRepository processRepository;

    public Process getProcess(Long processId) {
        return processRepository.findOne(processId);
    }

    public List<Process> getAllProcesses() {
        return processRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }
}
