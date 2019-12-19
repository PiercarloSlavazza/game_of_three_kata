package kata.game_of_three.impl;

import kata.game_of_three.Game;
import kata.game_of_three.Games;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryGames implements Games {

    private final Map<UUID, Game> gamesByUuid = new HashMap<>();

    @Override public synchronized void startGame(Game game) {
	if (gamesByUuid.get(game.getGameUuid()) != null) throw new IllegalStateException("invoked start game on game already started|uuid|" + game.getGameUuid());
	gamesByUuid.put(game.getGameUuid(), game);
    }

    @Override public synchronized void endGame(UUID gameUuid) {
        gamesByUuid.remove(gameUuid);
    }

    @Override public synchronized Optional<Game> getGame(UUID gameUuid) {
	return Optional.ofNullable(gamesByUuid.get(gameUuid));
    }
}
