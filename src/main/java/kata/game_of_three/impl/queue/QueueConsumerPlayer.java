package kata.game_of_three.impl.queue;

import kata.game_of_three.GameResult;
import kata.game_of_three.Move;
import kata.game_of_three.Player;
import kata.game_of_three.PlayerIdentifier;

public class QueueConsumerPlayer implements Player {

    private final PlayerIdentifier playerIdentifier;
    private final Player player;

    public QueueConsumerPlayer(PlayerIdentifier playerIdentifier, Player player) {
	this.playerIdentifier = playerIdentifier;
	this.player = player;
    }

    @Override public PlayerIdentifier getIdentifier() {
	return playerIdentifier;
    }

    @Override public void playTurn(Move opponentMove) {

    }

    @Override public void endGame(GameResult gameResult) {

    }
}
