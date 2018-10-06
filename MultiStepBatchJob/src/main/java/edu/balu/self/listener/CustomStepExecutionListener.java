/**
 * 
 */
package edu.balu.self.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author rtalapaneni
 *
 */
@Component
public class CustomStepExecutionListener implements StepExecutionListener {
	private static final Logger LOG = LoggerFactory.getLogger(CustomStepExecutionListener.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#beforeStep(org.springframework.batch.core.StepExecution)
	 */
	@Override
	public void beforeStep(StepExecution stepExecution) {
		LOG.info("Starting step + " + stepExecution.getStepName());
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#afterStep(org.springframework.batch.core.StepExecution)
	 */
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		LOG.info("Completed step + " + stepExecution.getStepName());
		LOG.info("Summary of step - " + stepExecution.getStepName() + " " + stepExecution.getSummary());
		return stepExecution.getExitStatus();
	}

}
