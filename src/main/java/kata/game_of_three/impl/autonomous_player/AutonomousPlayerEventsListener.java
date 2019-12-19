package kata.game_of_three.impl.autonomous_player;

import kata.game_of_three.GameResult;
import kata.game_of_three.Move;
import kata.game_of_three.PlayerIdentifier;

public interface AutonomousPlayerEventsListener {

    void onPlayTurn(Move playerMove, Move opponentMove);
    void onEndGame(PlayerIdentifier playerIdentifier, GameResult gameResult);

}
