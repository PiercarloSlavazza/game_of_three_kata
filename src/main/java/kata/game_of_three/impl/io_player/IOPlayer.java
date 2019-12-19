package kata.game_of_three.impl.io_player;

import kata.game_of_three.*;

public class IOPlayer implements Player {

    private final PlayerIdentifier playerIdentifier;
    private final GameTable gameTable;

    public IOPlayer(PlayerIdentifier playerIdentifier, GameTable gameTable) {
	this.playerIdentifier = playerIdentifier;
	this.gameTable = gameTable;
    }

    @Override public PlayerIdentifier getIdentifier() {
	return playerIdentifier;
    }

    @Override public void playTurn(Move opponentMove) {

    }

    @Override public void endGame(GameResult gameResult) {

    }
}
