

package edu.balu.self.football.internal;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import edu.balu.self.football.Player;
import edu.balu.self.football.PlayerDao;

/**
 * @author Lucas Ward
 *
 */
public class JdbcPlayerDao implements PlayerDao  {

	public static final String INSERT_PLAYER =
			"INSERT into PLAYERS (player_id, last_name, first_name, pos, year_of_birth, year_drafted)" +
			" values (:id, :lastName, :firstName, :position, :birthYear, :debutYear)";

    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    @Override
	public void savePlayer(Player player) {
        namedParameterJdbcTemplate.update(INSERT_PLAYER, new BeanPropertySqlParameterSource(player));
	}

    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
