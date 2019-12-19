package kata.game_of_three.impl;

import kata.game_of_three.GameTable;
import kata.game_of_three.Move;
import kata.game_of_three.Player;
import org.junit.Test;

import java.util.UUID;

import static kata.game_of_three.impl.GameOfThreeMockUtils.mockPlayer;
import static kata.game_of_three.impl.GameOfThreeMockUtils.mockPlayerIdentifier;
import static org.mockito.Mockito.*;

public class AutonomousPlayerTest {

    @Test
    public void shouldReplyWithZero() {
	GameTable gameTable = mock(GameTable.class);

	Player opponentPlayer = mockPlayer("player1");
	AutonomousPlayer autonomousPlayer = new AutonomousPlayer(mockPlayerIdentifier("player2"), gameTable);

	UUID gameUuid = UUID.randomUUID();

	Move player1Move = new Move(gameUuid, opponentPlayer.getIdentifier(), autonomousPlayer.getIdentifier(), Move.REPLY.ZERO, 15);
	autonomousPlayer.playTurn(player1Move);

	Move expectedMove = new Move(gameUuid, autonomousPlayer.getIdentifier(), opponentPlayer.getIdentifier(), Move.REPLY.ZERO, 5);
	verify(gameTable, times(1)).acceptMove(expectedMove);
    }

    @Test
    public void shouldReplyWithOne() {
	GameTable gameTable = mock(GameTable.class);

	Player opponentPlayer = mockPlayer("player1");
	AutonomousPlayer autonomousPlayer = new AutonomousPlayer(mockPlayerIdentifier("player2"), gameTable);

	UUID gameUuid = UUID.randomUUID();

	Move player1Move = new Move(gameUuid, opponentPlayer.getIdentifier(), autonomousPlayer.getIdentifier(), Move.REPLY.ZERO, 14);
	autonomousPlayer.playTurn(player1Move);

	Move expectedMove = new Move(gameUuid, autonomousPlayer.getIdentifier(), opponentPlayer.getIdentifier(), Move.REPLY.ONE, 5);
	verify(gameTable, times(1)).acceptMove(expectedMove);
    }

    @Test
    public void shouldReplyWithMinusOne() {
	GameTable gameTable = mock(GameTable.class);

	Player opponentPlayer = mockPlayer("player1");
	AutonomousPlayer autonomousPlayer = new AutonomousPlayer(mockPlayerIdentifier("player2"), gameTable);

	UUID gameUuid = UUID.randomUUID();

	Move player1Move = new Move(gameUuid, opponentPlayer.getIdentifier(), autonomousPlayer.getIdentifier(), Move.REPLY.ZERO, 16);
	autonomousPlayer.playTurn(player1Move);

	Move expectedMove = new Move(gameUuid, autonomousPlayer.getIdentifier(), opponentPlayer.getIdentifier(), Move.REPLY.MINUS_ONE, 5);
	verify(gameTable, times(1)).acceptMove(expectedMove);
    }
}
