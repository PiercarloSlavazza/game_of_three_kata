package kata.game_of_three;

import java.util.Objects;
import java.util.UUID;

public class GameResult {

    private final UUID gameUuid;
    private final GameOutcome gameOutcome;
    private final GameOutcomeReason gameOutcomeReason;

    public GameResult(UUID gameUuid, GameOutcome gameOutcome, GameOutcomeReason gameOutcomeReason) {
	this.gameUuid = gameUuid;
	this.gameOutcome = gameOutcome;
	this.gameOutcomeReason = gameOutcomeReason;
    }

    public UUID getGameUuid() {
	return gameUuid;
    }

    public GameOutcome getGameOutcome() {
	return gameOutcome;
    }

    public GameOutcomeReason getGameOutcomeReason() {
	return gameOutcomeReason;
    }

    @Override public String toString() {
	return "GameResult{" +
			"gameUuid=" + gameUuid +
			", gameOutcome=" + gameOutcome +
			", gameOutcomeReason=" + gameOutcomeReason +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof GameResult))
	    return false;
	GameResult that = (GameResult) o;
	return Objects.equals(gameUuid, that.gameUuid) &&
			gameOutcome == that.gameOutcome &&
			gameOutcomeReason == that.gameOutcomeReason;
    }

    @Override public int hashCode() {
	return Objects.hash(gameUuid, gameOutcome, gameOutcomeReason);
    }
}
