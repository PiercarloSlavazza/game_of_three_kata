package kata.game_of_three;

import java.util.Objects;
import java.util.UUID;

public class GameResult {

    public enum GAME_OUTCOME_REASON {
        INVALID_MOVE, GOT_ONE, UNKNOWN_PLAYER
    }

    private final UUID gameUuid;
    private final GameOutcome gameOutcome;
    private final GAME_OUTCOME_REASON gameOutcomeReason;

    public GameResult(UUID gameUuid, GameOutcome gameOutcome, GAME_OUTCOME_REASON gameOutcomeReason) {
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

    public GAME_OUTCOME_REASON getGameOutcomeReason() {
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
