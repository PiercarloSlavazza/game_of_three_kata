package kata.game_of_three.impl.autonomous_player;

import kata.game_of_three.GameResult;
import kata.game_of_three.Move;
import kata.game_of_three.PlayerIdentifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Turn {
    private final Move playerMove;
    private final Move opponentMove;

    Turn(Move playerMove, Move opponentMove) {
	this.playerMove = playerMove;
	this.opponentMove = opponentMove;
    }

    @SuppressWarnings("unused") public Move getPlayerMove() {
	return playerMove;
    }

    @SuppressWarnings("unused") public Move getOpponentMove() {
	return opponentMove;
    }

    @Override public String toString() {
	return "Turn{" +
			"playerMove=" + playerMove +
			", opponentMove=" + opponentMove +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof Turn))
	    return false;
	Turn turn = (Turn) o;
	return Objects.equals(playerMove, turn.playerMove) &&
			Objects.equals(opponentMove, turn.opponentMove);
    }

    @Override public int hashCode() {
	return Objects.hash(playerMove, opponentMove);
    }
}

class PlayerEndGame {
    private final PlayerIdentifier playerIdentifier;
    private final GameResult gameResult;

    PlayerEndGame(PlayerIdentifier playerIdentifier, GameResult gameResult) {
	this.playerIdentifier = playerIdentifier;
	this.gameResult = gameResult;
    }

    @SuppressWarnings("unused") public PlayerIdentifier getPlayerIdentifier() {
	return playerIdentifier;
    }

    @SuppressWarnings("unused") public GameResult getGameResult() {
	return gameResult;
    }

    @Override public String toString() {
	return "PlayerEndGame{" +
			"playerIdentifier=" + playerIdentifier +
			", gameResult=" + gameResult +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof PlayerEndGame))
	    return false;
	PlayerEndGame that = (PlayerEndGame) o;
	return Objects.equals(playerIdentifier, that.playerIdentifier) &&
			Objects.equals(gameResult, that.gameResult);
    }

    @Override public int hashCode() {
	return Objects.hash(playerIdentifier, gameResult);
    }
}

public class AutonomousPlayerEventsCollector implements AutonomousPlayerEventsListener {

    private final List<Turn> recordedTurns = new ArrayList<>();
    private final List<PlayerEndGame> recordedPlayerEndGames = new ArrayList<>();

    List<Turn> getRecordedTurns() {
	return new ArrayList<>(recordedTurns);
    }

    List<PlayerEndGame> getRecordedPlayerEndGames() {
	return new ArrayList<>(recordedPlayerEndGames);
    }

    @Override public void onPlayTurn(Move playerMove, Move opponentMove) {
	recordedTurns.add(new Turn(playerMove, opponentMove));
    }

    @Override public void onEndGame(PlayerIdentifier playerIdentifier, GameResult gameResult) {
        recordedPlayerEndGames.add(new PlayerEndGame(playerIdentifier, gameResult));
    }
}
