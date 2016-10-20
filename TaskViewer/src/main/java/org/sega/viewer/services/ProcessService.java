package org.sega.viewer.services;

import org.sega.viewer.models.ProcessEdit;
import org.sega.viewer.repositories.ProcessEditRepository;
import org.sega.viewer.repositories.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.sega.viewer.models.Process;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Raysmond<i@raysmond.com>.
 */
@Service
public class ProcessService {
    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ProcessEditRepository processEditRepository;

    public Process getProcess(Long processId) {
        return processRepository.findOne(processId);
    }

    public List<Process> getAllProcesses() {
        List<ProcessEdit> edit = processEditRepository.findByStep("published", new Sort(Sort.Direction.DESC, "datetime"));
        List<Process> processes = new ArrayList<Process>();
        for(int i=0;i<edit.size();i++){
            processes.add(edit.get(i).getProcess());
        }
        return processes;
    }
    
    public List<Process> getAllProcessesByCity(String city){
        List<ProcessEdit> edit = processEditRepository.findByStep("published", new Sort(Sort.Direction.DESC, "datetime"));
        List<Process> processes = new ArrayList<Process>();
        if(edit != null){
        	for(int i=0;i<edit.size();i++){
            	if(edit.get(i).getProcess().getCity().equals(city)){
            		processes.add(edit.get(i).getProcess());
            	}
                
            }
        }
        return processes;
    }
}
