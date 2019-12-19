package kata.game_of_three.impl;

import kata.game_of_three.GameResult;
import kata.game_of_three.Move;
import kata.game_of_three.Player;
import kata.game_of_three.PlayerIdentifier;

public class RabbitMQPlayerProducer implements Player {

    private final PlayerIdentifier playerIdentifier;

    public RabbitMQPlayerProducer(PlayerIdentifier playerIdentifier) {
	this.playerIdentifier = playerIdentifier;
    }

    @Override public PlayerIdentifier getIdentifier() {
	return playerIdentifier;
    }

    @Override public void playTurn(Move opponentMove) {

    }

    @Override public void endGame(GameResult gameResult) {

    }
}
