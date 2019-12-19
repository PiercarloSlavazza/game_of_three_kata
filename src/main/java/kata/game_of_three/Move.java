package kata.game_of_three;

import java.util.Objects;
import java.util.UUID;

public class Move {

    private final UUID gameUuid;
    private final UUID moveUuid;
    private final UUID uuidOfPreviousMove;
    private final PlayerIdentifier player;
    private final PlayerIdentifier opponent;
    private final Integer added;
    private final Integer resultingNumber;

    public Move(UUID gameUuid, UUID moveUuid, UUID uuidOfPreviousMove, PlayerIdentifier player, PlayerIdentifier opponent, Integer added,
		Integer resultingNumber) {
	this.gameUuid = gameUuid;
	this.moveUuid = moveUuid;
	this.uuidOfPreviousMove = uuidOfPreviousMove;
	this.player = player;
	this.opponent = opponent;
	this.added = added;
	this.resultingNumber = resultingNumber;
    }

    public UUID getGameUuid() {
	return gameUuid;
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

    public Integer getAdded() {
	return added;
    }

    public Integer getResultingNumber() {
	return resultingNumber;
    }

    @Override public String toString() {
	return "Move{" +
			"gameUuid=" + gameUuid +
			", moveUuid=" + moveUuid +
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
	return Objects.equals(gameUuid, move.gameUuid) &&
			Objects.equals(moveUuid, move.moveUuid) &&
			Objects.equals(uuidOfPreviousMove, move.uuidOfPreviousMove) &&
			Objects.equals(player, move.player) &&
			Objects.equals(opponent, move.opponent) &&
			Objects.equals(added, move.added) &&
			Objects.equals(resultingNumber, move.resultingNumber);
    }

    @Override public int hashCode() {
	return Objects.hash(gameUuid, moveUuid, uuidOfPreviousMove, player, opponent, added, resultingNumber);
    }
}
