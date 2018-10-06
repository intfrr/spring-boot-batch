package edu.balu.self.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import edu.balu.self.football.PlayerSummary;
import edu.balu.self.football.internal.PlayerSummaryMapper;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	private final String PLAYER_SUMMARY_SELECT = " SELECT ID, YEAR_NO, COMPLETES, ATTEMPTS, PASSING_YARDS, PASSING_TD, " + 
			"INTERCEPTIONS, RUSHES, RUSH_YARDS, RECEPTIONS, RECEPTIONS_YARDS, TOTAL_TD FROM PLAYER_SUMMARY";
	
	
	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOG.info("!!! JOB FINISHED! Time to verify the results");
			
/*			
			//List<PlayerSummary> playerSummaryList= 
			jdbcTemplate.query(PLAYER_SUMMARY_SELECT, new PlayerSummaryMapper())
			.forEach(playerSummary -> LOG.info("Found <" + playerSummary + "> in the database."));
*/			
//			
//			(" SELECT ID, YEAR_NO, COMPLETES, ATTEMPTS, PASSING_YARDS, PASSING_TD, " + 
//					"INTERCEPTIONS, RUSHES, RUSH_YARDS, RECEPTIONS, RECEPTIONS_YARDS, TOTAL_TD FROM PLAYER_SUMMARY"),
//					(rs, row) -> new PlayerSummary(rs.getString(1), rs.getString(2)))
//			.forEach(person -> LOG.info("Found <" + person + "> in the database."));
			
		}

	}
}
