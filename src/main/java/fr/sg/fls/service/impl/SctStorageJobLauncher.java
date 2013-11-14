package fr.sg.fls.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author jntakpe
 */
@Component
public class SctStorageJobLauncher {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("sctStorageJob")
    private Job job;

    public void launch(String test) {
        logger.info("START THE FUCKING JOB");
        try {
            JobExecution jobExec = jobLauncher.run(job, new JobParametersBuilder().addString("msg", test).toJobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
