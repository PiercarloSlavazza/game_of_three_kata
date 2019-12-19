package kata.game_of_three;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType") public class Move {

    public enum REPLY {
	ZERO, ONE, MINUS_ONE
    }

    private final UUID gameUuid;
    private final PlayerIdentifier player;
    private final PlayerIdentifier opponent;
    private final REPLY reply;
    private final Integer resultingNumber;

    public Move(UUID gameUuid, PlayerIdentifier player, PlayerIdentifier opponent, REPLY reply, Integer resultingNumber) {
	this.gameUuid = gameUuid;
	this.player = player;
	this.opponent = opponent;
	this.reply = reply;
	this.resultingNumber = resultingNumber;
    }

    public UUID getGameUuid() {
	return gameUuid;
    }

    public PlayerIdentifier getPlayer() {
	return player;
    }

    public PlayerIdentifier getOpponent() {
	return opponent;
    }

    public REPLY getReply() {
	return reply;
    }

    public Integer getResultingNumber() {
	return resultingNumber;
    }

    @Override public String toString() {
	return "Move{" +
			"gameUuid=" + gameUuid +
			", player=" + player +
			", opponent=" + opponent +
			", reply=" + reply +
			", resultingNumber=" + resultingNumber +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof Move))
	    return false;
	Move move = (Move) o;
	return Objects.equals(gameUuid, move.gameUuid) &&
			Objects.equals(player, move.player) &&
			Objects.equals(opponent, move.opponent) &&
			reply == move.reply &&
			Objects.equals(resultingNumber, move.resultingNumber);
    }

    @Override public int hashCode() {
	return Objects.hash(gameUuid, player, opponent, reply, resultingNumber);
    }
}
