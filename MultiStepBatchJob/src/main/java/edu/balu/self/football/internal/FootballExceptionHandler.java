

package edu.balu.self.football.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;

public class FootballExceptionHandler implements ExceptionHandler {

	private static final Log logger = LogFactory
			.getLog(FootballExceptionHandler.class);

	@Override
	public void handleException(RepeatContext context, Throwable throwable)
			throws Throwable {

		if (!(throwable instanceof NumberFormatException)) {
			throw throwable;
		} else {
			logger.error("Number Format Exception!", throwable);
		}

	}

}
