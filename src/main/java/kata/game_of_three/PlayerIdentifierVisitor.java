package kata.game_of_three;

import kata.game_of_three.impl.PlayerIdentifierImpl;
import kata.game_of_three.impl.RemotePlayerIdentifier;

public interface PlayerIdentifierVisitor<T> {

    T visit(RemotePlayerIdentifier playerIdentifier);
    T visit(PlayerIdentifierImpl playerIdentifier);

}
