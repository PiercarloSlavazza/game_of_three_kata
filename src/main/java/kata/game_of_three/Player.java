package kata.game_of_three;

public interface Player {

    void playTurn(Move move);
    void endGame(GameResult gameResult);

}
