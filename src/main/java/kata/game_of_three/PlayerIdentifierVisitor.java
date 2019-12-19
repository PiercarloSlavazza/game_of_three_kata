package kata.game_of_three;

public interface PlayerIdentifierVisitor<T> {

    T visit(PlayerIdentifier playerIdentifier);

}
