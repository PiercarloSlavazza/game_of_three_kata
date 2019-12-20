package kata.game_of_three;

import java.util.Objects;

public class PlayerIdentifier {

    private final String id;

    public PlayerIdentifier(String id) {
	this.id = id;
    }

    public String getId() {
	return id;
    }

    @Override public String toString() {
	return "PlayerIdentifier{" +
			"id='" + id + '\'' +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof PlayerIdentifier))
	    return false;
	PlayerIdentifier that = (PlayerIdentifier) o;
	return Objects.equals(id, that.id);
    }

    @Override public int hashCode() {
	return Objects.hash(id);
    }
}
