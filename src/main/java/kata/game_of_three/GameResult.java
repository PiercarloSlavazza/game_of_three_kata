package kata.game_of_three;

import java.util.Objects;

public class GameResult {

    public enum GAME_OUTCOME {
        YOU_WIN, YOU_LOSE
    }

    public enum GAME_OUTCOME_REASON {
        INVALID_MOVE, GOT_ONE
    }

    private final GAME_OUTCOME gameOutcome;
    private final GAME_OUTCOME_REASON gameOutcomeReason;

    public GameResult(GAME_OUTCOME gameOutcome, GAME_OUTCOME_REASON gameOutcomeReason) {
	this.gameOutcome = gameOutcome;
	this.gameOutcomeReason = gameOutcomeReason;
    }

    public GAME_OUTCOME getGameOutcome() {
	return gameOutcome;
    }

    public GAME_OUTCOME_REASON getGameOutcomeReason() {
	return gameOutcomeReason;
    }

    @Override public String toString() {
	return "GameResult{" +
			"gameOutcome=" + gameOutcome +
			", gameOutcomeReason=" + gameOutcomeReason +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof GameResult))
	    return false;
	GameResult that = (GameResult) o;
	return gameOutcome == that.gameOutcome &&
			gameOutcomeReason == that.gameOutcomeReason;
    }

    @Override public int hashCode() {
	return Objects.hash(gameOutcome, gameOutcomeReason);
    }
}
