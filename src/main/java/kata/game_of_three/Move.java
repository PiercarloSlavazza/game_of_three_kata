package kata.game_of_three;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType") public class Move {

    private final UUID moveUuid;
    private final UUID uuidOfPreviousMove;
    private final PlayerIdentifier player;
    private final PlayerIdentifier opponent;
    private final Integer added;
    private final Integer resultingNumber;

    public Move(UUID moveUuid, Optional<UUID> uuidOfPreviousMove, PlayerIdentifier player, PlayerIdentifier opponent, Integer added, Integer resultingNumber) {
	this.moveUuid = moveUuid;
	this.uuidOfPreviousMove = uuidOfPreviousMove.orElse(null);
	this.player = player;
	this.opponent = opponent;
	this.added = added;
	this.resultingNumber = resultingNumber;
    }

    public UUID getMoveUuid() {
	return moveUuid;
    }

    public Optional<UUID> getUuidOfPreviousMove() {
	return Optional.ofNullable(uuidOfPreviousMove);
    }

    public PlayerIdentifier getPlayer() {
	return player;
    }

    public PlayerIdentifier getOpponent() {
	return opponent;
    }

    public Integer getAdded() {
	return added;
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
			", added=" + added +
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
			Objects.equals(added, move.added) &&
			Objects.equals(resultingNumber, move.resultingNumber);
    }

    @Override public int hashCode() {
	return Objects.hash(moveUuid, uuidOfPreviousMove, player, opponent, added, resultingNumber);
    }
}
