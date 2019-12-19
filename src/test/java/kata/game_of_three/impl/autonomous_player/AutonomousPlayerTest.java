package kata.game_of_three.impl.autonomous_player;

import kata.game_of_three.GameResult;
import kata.game_of_three.GameTable;
import kata.game_of_three.Move;
import kata.game_of_three.Player;
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

	Move player1Move = new Move(gameUuid, opponentPlayer.getIdentifier(), autonomousPlayer.getIdentifier(), Move.REPLY.ZERO, 15);
	autonomousPlayer.playTurn(player1Move);

	Move expectedMove = new Move(gameUuid, autonomousPlayer.getIdentifier(), opponentPlayer.getIdentifier(), Move.REPLY.ZERO, 5);
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

	Move player1Move = new Move(gameUuid, opponentPlayer.getIdentifier(), autonomousPlayer.getIdentifier(), Move.REPLY.ZERO, 14);
	autonomousPlayer.playTurn(player1Move);

	Move expectedMove = new Move(gameUuid, autonomousPlayer.getIdentifier(), opponentPlayer.getIdentifier(), Move.REPLY.ONE, 5);
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

	Move player1Move = new Move(gameUuid, opponentPlayer.getIdentifier(), autonomousPlayer.getIdentifier(), Move.REPLY.ZERO, 16);
	autonomousPlayer.playTurn(player1Move);

	Move expectedMove = new Move(gameUuid, autonomousPlayer.getIdentifier(), opponentPlayer.getIdentifier(), Move.REPLY.MINUS_ONE, 5);
	Mockito.verify(gameTable, times(1)).acceptMove(expectedMove);
	verify(playerEventsListener, times(1)).onPlayTurn(expectedMove, player1Move);
    }

    @Test
    public void shouldNotifyEndGameToListeners() {
	GameTable gameTable = mock(GameTable.class);

	AutonomousPlayerEventsListener playerEventsListener = mock(AutonomousPlayerEventsListener.class);
	AutonomousPlayer autonomousPlayer = new AutonomousPlayer(GameOfThreeMockUtils.mockPlayerIdentifier("player2"), gameTable, playerEventsListener);

	GameResult gameResult = new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE);
	autonomousPlayer.endGame(gameResult);
	verify(playerEventsListener, times(1)).onEndGame(gameResult);
    }

}
