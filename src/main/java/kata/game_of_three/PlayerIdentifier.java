package kata.game_of_three;

public interface PlayerIdentifier {

    String getId();
    <T> T accept(PlayerIdentifierVisitor<T> playerIdentifierVisitor);

}
