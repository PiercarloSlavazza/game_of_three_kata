package kata.game_of_three;

import java.util.Objects;

public class PlayerInvitation {

    private final PlayerIdentifier from;
    private final PlayerIdentifier to;
    private final Integer gameInception;

    public PlayerInvitation(PlayerIdentifier from, PlayerIdentifier to, Integer gameInception) {
	this.from = from;
	this.to = to;
	this.gameInception = gameInception;
    }

    public PlayerIdentifier getFrom() {
	return from;
    }

    public PlayerIdentifier getTo() {
	return to;
    }

    public Integer getGameInception() {
	return gameInception;
    }

    @Override public String toString() {
	return "PlayerInvitation{" +
			"from=" + from +
			", to=" + to +
			", gameInception=" + gameInception +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof PlayerInvitation))
	    return false;
	PlayerInvitation that = (PlayerInvitation) o;
	return Objects.equals(from, that.from) &&
			Objects.equals(to, that.to) &&
			Objects.equals(gameInception, that.gameInception);
    }

    @Override public int hashCode() {
	return Objects.hash(from, to, gameInception);
    }
}
