package org.sega.viewer.services;

import org.sega.viewer.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LogService {
	@Autowired
    private LogRepository logRepository;

}
