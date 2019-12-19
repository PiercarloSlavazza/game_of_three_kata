package kata.game_of_three;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType") public class Move {

    public enum REPLY {
	ZERO, ONE, MINUS_ONE
    }

    private final UUID moveUuid;
    private final UUID uuidOfPreviousMove;
    private final PlayerIdentifier player;
    private final PlayerIdentifier opponent;
    private final REPLY reply;
    private final Integer resultingNumber;

    public Move(UUID moveUuid, UUID uuidOfPreviousMove, PlayerIdentifier player, PlayerIdentifier opponent, REPLY reply, Integer resultingNumber) {
	this.moveUuid = moveUuid;
	this.uuidOfPreviousMove = uuidOfPreviousMove;
	this.player = player;
	this.opponent = opponent;
	this.reply = reply;
	this.resultingNumber = resultingNumber;
    }

    public UUID getMoveUuid() {
	return moveUuid;
    }

    public UUID getUuidOfPreviousMove() {
	return uuidOfPreviousMove;
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
			"moveUuid=" + moveUuid +
			", uuidOfPreviousMove=" + uuidOfPreviousMove +
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
	return Objects.equals(moveUuid, move.moveUuid) &&
			Objects.equals(uuidOfPreviousMove, move.uuidOfPreviousMove) &&
			Objects.equals(player, move.player) &&
			Objects.equals(opponent, move.opponent) &&
			Objects.equals(reply, move.reply) &&
			Objects.equals(resultingNumber, move.resultingNumber);
    }

    @Override public int hashCode() {
	return Objects.hash(moveUuid, uuidOfPreviousMove, player, opponent, reply, resultingNumber);
    }
}
