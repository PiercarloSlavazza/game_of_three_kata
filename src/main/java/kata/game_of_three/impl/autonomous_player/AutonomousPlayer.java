package kata.game_of_three.impl.autonomous_player;

import kata.game_of_three.*;

import java.util.Arrays;
import java.util.List;

public class AutonomousPlayer implements Player {

    private final PlayerIdentifier playerIdentifier;
    private final GameTable gameTable;
    private final List<AutonomousPlayerEventsListener> playerEventsListeners;

    public AutonomousPlayer(PlayerIdentifier playerIdentifier, GameTable gameTable,
		     AutonomousPlayerEventsListener... playerEventsListeners) {
	this.playerIdentifier = playerIdentifier;
	this.gameTable = gameTable;
	this.playerEventsListeners = Arrays.asList(playerEventsListeners);
    }

    @Override public PlayerIdentifier getIdentifier() {
	return playerIdentifier;
    }

    private int computeReply(int opponentNumber) {
	int opponentNumberModuloThree = opponentNumber % 3;
	return opponentNumberModuloThree == 0 ? 0 : opponentNumberModuloThree == 1 ? -1 : 1;
    }

    private Move.REPLY normalizeReply(int reply) {
	if (reply == 0) return Move.REPLY.ZERO;
	if (reply == -1) return  Move.REPLY.MINUS_ONE;
	if (reply == 1) return Move.REPLY.ONE;
	throw new IllegalStateException("reply must be in [-1, 1] but is" + reply);
    }

    @Override public void playTurn(Move opponentMove) {
	int opponentNumber = opponentMove.getResultingNumber();
	int reply = computeReply(opponentNumber);
	assert reply >=-1 && reply <=1 : "reply must be in [-1, 1] but is" + reply;

	int resultingNumber = (opponentNumber + reply) / 3;
	Move.REPLY normalizedReply = normalizeReply(reply);

	Move replyMove = new Move(opponentMove.getGameUuid(), playerIdentifier, opponentMove.getPlayer(), normalizedReply, resultingNumber);
	playerEventsListeners.forEach(listener -> listener.onPlayTurn(replyMove, opponentMove));
	gameTable.acceptMove(replyMove);
    }

    @Override public void endGame(GameResult gameResult) {
        playerEventsListeners.forEach(listener -> listener.onEndGame(playerIdentifier, gameResult));
    }
}
