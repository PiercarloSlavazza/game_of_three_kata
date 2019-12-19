package kata.game_of_three;

import java.util.Objects;

public class GameOutcome {

    public static final GameOutcome YOU_WIN = new GameOutcome(true);
    public static final GameOutcome YOU_LOSE = new GameOutcome(false);

    private Boolean youWin;

    @SuppressWarnings("unused") private GameOutcome() {}

    private GameOutcome(Boolean youWin) {
	this.youWin = youWin;
    }

    @SuppressWarnings("unused") public Boolean getYouWin() {
	return youWin;
    }

    @Override public String toString() {
	return "GameOutcome{" +
			"youWin=" + youWin +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof GameOutcome))
	    return false;
	GameOutcome that = (GameOutcome) o;
	return Objects.equals(youWin, that.youWin);
    }

    @Override public int hashCode() {
	return Objects.hash(youWin);
    }
}
