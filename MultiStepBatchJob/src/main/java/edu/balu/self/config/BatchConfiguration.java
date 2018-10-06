package edu.balu.self.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import edu.balu.self.football.Game;
import edu.balu.self.football.Player;
import edu.balu.self.football.PlayerSummary;
import edu.balu.self.football.internal.GameFieldSetMapper;
import edu.balu.self.football.internal.JdbcGameDao;
import edu.balu.self.football.internal.JdbcPlayerDao;
import edu.balu.self.football.internal.JdbcPlayerSummaryDao;
import edu.balu.self.football.internal.PlayerFieldSetMapper;
import edu.balu.self.football.internal.PlayerItemWriter;
import edu.balu.self.football.internal.PlayerSummaryRowMapper;
import edu.balu.self.listener.CustomChunkListener;
import edu.balu.self.listener.CustomStepExecutionListener;
import edu.balu.self.listener.JobCompletionNotificationListener;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	private static final Integer CHUNK_SIZE = 100;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;

	@Autowired
	JobCompletionNotificationListener listener;

	@Autowired
	CustomChunkListener chunkListener;
	
	@Autowired
	private CustomStepExecutionListener stepExecutionListener;
	
	@Bean
	public Job footballJob() {
		return this.jobBuilderFactory.get("footballJob")
				.incrementer(new RunIdIncrementer())
				.start(playerLoad())
				.next(gameLoad())
				.next(summarizationStep())
				.listener(listener)
				// .stop()
				.build();
	}
	
	@Bean
	public FlatFileItemReader<Player> playerReader() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] { "ID", "lastName", "firstName", "position", "debutYear", "birthYear" });
		// lineTokenizer.setNames("ID","lastName","firstName","position","birthYear","debutYear");
		DefaultLineMapper<Player> lineMapper = new DefaultLineMapper<>();
		lineMapper.setFieldSetMapper(new PlayerFieldSetMapper());
		lineMapper.setLineTokenizer(lineTokenizer);

		return new FlatFileItemReaderBuilder<Player>().name("playerFileItemReader")
				.resource(new ClassPathResource("input/player.csv")).lineMapper(lineMapper).lineTokenizer(lineTokenizer)
				// .delimited()
				// .names(new String[]
				// {"ID","lastName","firstName","position","debutYear","birthYear"})
				// .fieldSetMapper()
				.build();

	}

	@Bean
	public PlayerItemWriter playerWriter(DataSource dataSource) {
		PlayerItemWriter writer = new PlayerItemWriter();
		JdbcPlayerDao playerDao = new JdbcPlayerDao();
		playerDao.setDataSource(dataSource);
		writer.setPlayerDao(playerDao);
		return writer;
	}

	@Bean
	public FlatFileItemReader<Game> gameReader() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] { "id", "year", "team", "week", "opponent", "completes", "attempts",
				"passingYards", "passingTd", "interceptions", "rushes", "rushYards", "receptions", "receptionYards",
				"totalTd" });
		final DefaultLineMapper<Game> lineMapper = new DefaultLineMapper<>();
		lineMapper.setFieldSetMapper(new GameFieldSetMapper());
		lineMapper.setLineTokenizer(lineTokenizer);
		// lineTokenizer.setNames("ID","lastName","firstName","position","birthYear","debutYear");

		return new FlatFileItemReaderBuilder<Game>().name("gameFileItemReader")
				.resource(new ClassPathResource("input/games.csv")).lineMapper(lineMapper).lineTokenizer(lineTokenizer)
				// .delimited()
				// .names(new String[]
				// {"id","year","team","week","opponent","completes","attempts","passingYards","passingTd","interceptions","rushes","rushYards","receptions","receptionYards","totalTd"})
				// .fieldSetMapper(new GameFieldSetMapper())
				.build();

	}

	@Bean
	public JdbcGameDao gameWriter(DataSource dataSource) {
		JdbcGameDao gameDao = new JdbcGameDao();
		gameDao.setDataSource(dataSource);
		return gameDao;
	}

	@Bean
	public JdbcPlayerSummaryDao summaryWriter(DataSource dataSource) {
		JdbcPlayerSummaryDao gameDao = new JdbcPlayerSummaryDao();
		gameDao.setDataSource(dataSource);
		return gameDao;
	}

	@Bean
	public Step playerLoad() {
		return stepBuilderFactory.get("playerLoad")
				.listener(stepExecutionListener)
				.<Player, Player>chunk(CHUNK_SIZE)
				.reader(playerReader())
				.writer(playerWriter(dataSource))
				//.listener(chunkListener)
				.build();
	}

	@Bean
	protected Step gameLoad() {
		return stepBuilderFactory.get("gameLoad")
				.listener(stepExecutionListener)
				.<Game, Game>chunk(CHUNK_SIZE)
				.reader(gameReader())
				.writer(gameWriter(dataSource))
				//.listener(chunkListener)
				.build();
	}

	@Bean
	protected Step summarizationStep() {
		return stepBuilderFactory.get("summarizationStep")
				.listener(stepExecutionListener)
				.<PlayerSummary, PlayerSummary>chunk(CHUNK_SIZE)
				.reader(playerSummarizationSource())
				.writer(summaryWriter(dataSource))
				//.listener(chunkListener)
				.build();
	}

	/*
	 * @Bean protected Step playerSummarization(Tasklet tasklet) { return
	 * stepBuilderFactory.get("summarizationStep") . .build(); }
	 */

	private static final String QUERY_PLAYER_SUMMARY = "SELECT GAMES.player_id, GAMES.year_no, SUM(COMPLETES),"
			+ "SUM(ATTEMPTS), SUM(PASSING_YARDS), SUM(PASSING_TD),"
			+ "SUM(INTERCEPTIONS), SUM(RUSHES), SUM(RUSH_YARDS),"
			+ "SUM(RECEPTIONS), SUM(RECEPTIONS_YARDS), SUM(TOTAL_TD)" + "from GAMES, PLAYERS where PLAYERS.player_id ="
			+ "GAMES.player_id group by GAMES.player_id, GAMES.year_no";

	@Bean(destroyMethod = "")
	public ItemReader<PlayerSummary> playerSummarizationSource() {

		JdbcCursorItemReader<PlayerSummary> databaseReader = new JdbcCursorItemReader<>();

		databaseReader.setDataSource(dataSource);
		databaseReader.setSql(QUERY_PLAYER_SUMMARY);
		databaseReader.setRowMapper(new PlayerSummaryRowMapper());
		databaseReader.setVerifyCursorPosition(true);

		return databaseReader;
	}
}
