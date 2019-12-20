package kata.game_of_three;

import java.util.Objects;

public class GameOutcomeReason {

    public static final GameOutcomeReason GOT_ONE = new GameOutcomeReason("GOT_ONE");
    public static final GameOutcomeReason UNKNOWN_PLAYER = new GameOutcomeReason("UNKNOWN_PLAYER");
    public static final GameOutcomeReason INVALID_MOVE = new GameOutcomeReason("INVALID_MOVE");

    private String reason;

    @SuppressWarnings("unused") private GameOutcomeReason() {}

    private GameOutcomeReason(String reason) {
	this.reason = reason;
    }

    public String getReason() {
	return reason;
    }

    @Override public String toString() {
	return "GameOutcomeReason{" +
			"reason='" + reason + '\'' +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof GameOutcomeReason))
	    return false;
	GameOutcomeReason that = (GameOutcomeReason) o;
	return Objects.equals(reason, that.reason);
    }

    @Override public int hashCode() {
	return Objects.hash(reason);
    }
}
