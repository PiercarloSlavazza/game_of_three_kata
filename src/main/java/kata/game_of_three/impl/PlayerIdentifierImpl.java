package kata.game_of_three.impl;

import kata.game_of_three.PlayerIdentifier;
import kata.game_of_three.PlayerIdentifierVisitor;

public class PlayerIdentifierImpl implements PlayerIdentifier {

    @Override public String getId() {
	return null;
    }

    @Override public <T> T accept(PlayerIdentifierVisitor<T> playerIdentifierVisitor) {
	return playerIdentifierVisitor.visit(this);
    }
}
