package kata.game_of_three.impl;

import kata.game_of_three.Player;
import kata.game_of_three.PlayerFactory;
import kata.game_of_three.PlayerIdentifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CachedPlayerFactory implements PlayerFactory {

    private final Map<PlayerIdentifier, Player> playersByIdentifier = new HashMap<>();

    public synchronized void addPlayer(Player player) {
        playersByIdentifier.put(player.getIdentifier(), player);
    }

    @Override public synchronized Optional<Player> buildPlayer(PlayerIdentifier playerIdentifier) {
	return Optional.ofNullable(playersByIdentifier.get(playerIdentifier));
    }
}
