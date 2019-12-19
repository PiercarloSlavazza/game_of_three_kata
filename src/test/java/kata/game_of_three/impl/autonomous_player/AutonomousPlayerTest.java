package kata.game_of_three.impl.autonomous_player;

import kata.game_of_three.*;
import kata.game_of_three.impl.GameOfThreeMockUtils;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AutonomousPlayerTest {

    @Test
    public void shouldReplyWithZero() {
	GameTable gameTable = mock(GameTable.class);
	AutonomousPlayerEventsListener playerEventsListener = mock(AutonomousPlayerEventsListener.class);

	Player opponentPlayer = GameOfThreeMockUtils.mockPlayer("player1");
	AutonomousPlayer autonomousPlayer = new AutonomousPlayer(GameOfThreeMockUtils.mockPlayerIdentifier("player2"), gameTable, playerEventsListener);

	UUID gameUuid = UUID.randomUUID();

	Move player1Move = new Move(gameUuid, opponentPlayer.getIdentifier(), autonomousPlayer.getIdentifier(), MoveReply.ZERO, 15);
	autonomousPlayer.playTurn(player1Move);

	Move expectedMove = new Move(gameUuid, autonomousPlayer.getIdentifier(), opponentPlayer.getIdentifier(), MoveReply.ZERO, 5);
	Mockito.verify(gameTable, times(1)).acceptMove(expectedMove);
	verify(playerEventsListener, times(1)).onPlayTurn(expectedMove, player1Move);
    }

    @Test
    public void shouldReplyWithOne() {
	GameTable gameTable = mock(GameTable.class);
	AutonomousPlayerEventsListener playerEventsListener = mock(AutonomousPlayerEventsListener.class);

	Player opponentPlayer = GameOfThreeMockUtils.mockPlayer("player1");
	AutonomousPlayer autonomousPlayer = new AutonomousPlayer(GameOfThreeMockUtils.mockPlayerIdentifier("player2"), gameTable, playerEventsListener);

	UUID gameUuid = UUID.randomUUID();

	Move player1Move = new Move(gameUuid, opponentPlayer.getIdentifier(), autonomousPlayer.getIdentifier(), MoveReply.ZERO, 14);
	autonomousPlayer.playTurn(player1Move);

	Move expectedMove = new Move(gameUuid, autonomousPlayer.getIdentifier(), opponentPlayer.getIdentifier(), MoveReply.ONE, 5);
	Mockito.verify(gameTable, times(1)).acceptMove(expectedMove);
	verify(playerEventsListener, times(1)).onPlayTurn(expectedMove, player1Move);
    }

    @Test
    public void shouldReplyWithMinusOne() {
	GameTable gameTable = mock(GameTable.class);
	AutonomousPlayerEventsListener playerEventsListener = mock(AutonomousPlayerEventsListener.class);

	Player opponentPlayer = GameOfThreeMockUtils.mockPlayer("player1");
	AutonomousPlayer autonomousPlayer = new AutonomousPlayer(GameOfThreeMockUtils.mockPlayerIdentifier("player2"), gameTable, playerEventsListener);

	UUID gameUuid = UUID.randomUUID();

	Move player1Move = new Move(gameUuid, opponentPlayer.getIdentifier(), autonomousPlayer.getIdentifier(), MoveReply.ZERO, 16);
	autonomousPlayer.playTurn(player1Move);

	Move expectedMove = new Move(gameUuid, autonomousPlayer.getIdentifier(), opponentPlayer.getIdentifier(), MoveReply.MINUS_ONE, 5);
	Mockito.verify(gameTable, times(1)).acceptMove(expectedMove);
	verify(playerEventsListener, times(1)).onPlayTurn(expectedMove, player1Move);
    }

    @Test
    public void shouldNotifyEndGameToListeners() {
	GameTable gameTable = mock(GameTable.class);

	AutonomousPlayerEventsListener playerEventsListener = mock(AutonomousPlayerEventsListener.class);
	AutonomousPlayer autonomousPlayer = new AutonomousPlayer(GameOfThreeMockUtils.mockPlayerIdentifier("player2"), gameTable, playerEventsListener);

	GameResult gameResult = new GameResult(UUID.randomUUID(), GameOutcome.YOU_LOSE, GameOutcomeReason.INVALID_MOVE);
	autonomousPlayer.endGame(gameResult);
	verify(playerEventsListener, times(1)).onEndGame(autonomousPlayer.getIdentifier(), gameResult);
    }

}
