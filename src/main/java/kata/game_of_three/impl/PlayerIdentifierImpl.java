package kata.game_of_three.impl;

import kata.game_of_three.PlayerIdentifier;
import kata.game_of_three.PlayerIdentifierVisitor;

import java.util.Objects;

public class PlayerIdentifierImpl implements PlayerIdentifier {

    private final String id;

    public PlayerIdentifierImpl(String id) {
	this.id = id;
    }

    @Override public String getId() {
	return id;
    }

    @Override public <T> T accept(PlayerIdentifierVisitor<T> playerIdentifierVisitor) {
	return playerIdentifierVisitor.visit(this);
    }

    @Override public String toString() {
	return "PlayerIdentifierImpl{" +
			"id='" + id + '\'' +
			'}';
    }

    @Override public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (!(o instanceof PlayerIdentifierImpl))
	    return false;
	PlayerIdentifierImpl that = (PlayerIdentifierImpl) o;
	return Objects.equals(id, that.id);
    }

    @Override public int hashCode() {
	return Objects.hash(id);
    }
}
