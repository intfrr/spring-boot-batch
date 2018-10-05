

package edu.balu.self.football.internal;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import edu.balu.self.football.Player;
import edu.balu.self.football.PlayerDao;

public class PlayerItemWriter implements ItemWriter<Player> {

	private PlayerDao playerDao;

	@Override
	public void write(List<? extends Player> players) throws Exception {
		for (Player player : players) {
			playerDao.savePlayer(player);
		}
	}

	public void setPlayerDao(PlayerDao playerDao) {
		this.playerDao = playerDao;
	}

}
