package kata.game_of_three;

public interface Player {

    PlayerIdentifier getIdentifier();
    void playTurn(Move opponentMove);
    void endGame(GameResult gameResult);

}
