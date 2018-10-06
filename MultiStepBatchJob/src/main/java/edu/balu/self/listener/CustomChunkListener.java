package edu.balu.self.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class CustomChunkListener implements ChunkListener {
	private static final Logger LOG = LoggerFactory.getLogger(CustomChunkListener.class);
	
	/**
	 * 
	 */
	@Override
	public void beforeChunk(ChunkContext context) {
		LOG.info("Starting chunk of " + context.getStepContext().getStepExecution().getReadCount() + " for step - " + context.getStepContext().getStepName());
	}
	
	/**
	 * 
	 */
	@Override
	public void afterChunk(ChunkContext context) {
		LOG.info("Completed chunk of " + context.getStepContext().getStepExecution().getReadCount() + " for step - " + context.getStepContext().getStepName());
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.ChunkListener#afterChunkError(org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public void afterChunkError(ChunkContext context) {
		LOG.info("Chunk error for step - " + context.getStepContext().getStepName());
		
	}

	
}
