package kata.game_of_three.impl.autonomous_player;

import kata.game_of_three.GameResult;
import kata.game_of_three.Move;

public interface AutonomousPlayerEventsListener {

    void onPlayTurn(Move playerMove, Move opponentMove);
    void onEndGame(GameResult gameResult);

}
