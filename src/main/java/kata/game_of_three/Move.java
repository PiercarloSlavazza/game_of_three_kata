package kata.game_of_three;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType") public class Move {

    public enum REPLY {
	ZERO, ONE, MINUS_ONE
    }

    private UUID gameUuid;
    private PlayerIdentifier player;
    private PlayerIdentifier opponent;
    private REPLY reply;
    private Integer resultingNumber;

    @SuppressWarnings("unused") private Move() {}

    public Move(UUID gameUuid, PlayerIdentifier player, PlayerIdentifier opponent, Integer resultingNumber) {
        this(gameUuid, player, opponent, Optional.empty(), resultingNumber);
    }

    public Move(UUID gameUuid, PlayerIdentifier player, PlayerIdentifier opponent, REPLY reply, Integer resultingNumber) {
        this(gameUuid, player, opponent, Optional.of(reply), resultingNumber);
    }

    private Move(UUID gameUuid, PlayerIdentifier player, PlayerIdentifier opponent, Optional<REPLY> reply, Integer resultingNumber) {
	this.gameUuid = gameUuid;
	this.player = player;
	this.opponent = opponent;
	this.reply = reply.orElse(null);
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

    public Optional<REPLY> getReply() {
	return Optional.ofNullable(reply);
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
