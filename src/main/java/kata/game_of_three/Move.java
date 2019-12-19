package kata.game_of_three;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType") public class Move {

    private UUID gameUuid;
    private PlayerIdentifier player;
    private PlayerIdentifier opponent;
    private MoveReply moveReply;
    private Integer resultingNumber;

    @SuppressWarnings("unused") private Move() {}

    public Move(UUID gameUuid, PlayerIdentifier player, PlayerIdentifier opponent, Integer resultingNumber) {
        this(gameUuid, player, opponent, Optional.empty(), resultingNumber);
    }

    public Move(UUID gameUuid, PlayerIdentifier player, PlayerIdentifier opponent, MoveReply moveReply, Integer resultingNumber) {
        this(gameUuid, player, opponent, Optional.of(moveReply), resultingNumber);
    }

    private Move(UUID gameUuid, PlayerIdentifier player, PlayerIdentifier opponent, Optional<MoveReply> reply, Integer resultingNumber) {
	this.gameUuid = gameUuid;
	this.player = player;
	this.opponent = opponent;
	this.moveReply = reply.orElse(null);
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

    public Optional<MoveReply> getMoveReply() {
	return Optional.ofNullable(moveReply);
    }

    public Integer getResultingNumber() {
	return resultingNumber;
    }

    @Override public String toString() {
	return "Move{" +
			"gameUuid=" + gameUuid +
			", player=" + player +
			", opponent=" + opponent +
			", moveReply=" + moveReply +
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
			moveReply == move.moveReply &&
			Objects.equals(resultingNumber, move.resultingNumber);
    }

    @Override public int hashCode() {
	return Objects.hash(gameUuid, player, opponent, moveReply, resultingNumber);
    }
}
