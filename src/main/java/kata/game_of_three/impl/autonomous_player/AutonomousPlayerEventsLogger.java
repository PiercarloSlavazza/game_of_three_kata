package kata.game_of_three.impl.autonomous_player;

import kata.game_of_three.GameResult;
import kata.game_of_three.Move;
import kata.game_of_three.PlayerIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutonomousPlayerEventsLogger implements AutonomousPlayerEventsListener {

    private static final Logger log = LoggerFactory.getLogger(AutonomousPlayerEventsLogger.class);

    @Override public void onPlayTurn(Move playerMove, Move opponentMove) {
	log.info(String.format("Game: %s Opponent %s move: %d Player %s answer: %d",
			       playerMove.getGameUuid(),
			       opponentMove.getPlayer().getId(),
			       opponentMove.getResultingNumber(),
			       playerMove.getPlayer().getId(),
			       playerMove.getResultingNumber()));
    }

    @Override public void onEndGame(PlayerIdentifier playerIdentifier, GameResult gameResult) {
        log.info(String.format("Game: %s Player %s Outcome: %s Reason: %s",
			       gameResult.getGameUuid(),
			       playerIdentifier.getId(),
			       gameResult.getGameOutcome(),
			       gameResult.getGameOutcomeReason()));
    }
}
