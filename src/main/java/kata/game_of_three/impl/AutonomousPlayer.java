package kata.game_of_three.impl;

import kata.game_of_three.*;

public class AutonomousPlayer implements Player {

    private final PlayerIdentifier playerIdentifier;
    private final GameTable gameTable;

    AutonomousPlayer(PlayerIdentifier playerIdentifier, GameTable gameTable) {
	this.playerIdentifier = playerIdentifier;
	this.gameTable = gameTable;
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

    @Override public void playTurn(Move move) {
        int opponentNumber = move.getResultingNumber();
	int reply = computeReply(opponentNumber);
	assert reply >=-1 && reply <=1 : "reply must be in [-1, 1] but is" + reply;

	int resultingNumber = (opponentNumber + reply) / 3;
	Move.REPLY normalizedReply = normalizeReply(reply);

	Move replyMove = new Move(move.getGameUuid(), playerIdentifier, move.getPlayer(), normalizedReply, resultingNumber);
	gameTable.acceptMove(replyMove);
    }

    @Override public void endGame(GameResult gameResult) {

    }
}
