package kata.game_of_three;

public interface Player {

    PlayerIdentifier getIdentifier();
    void playTurn(Move move);
    void endGame(GameResult gameResult);

}
