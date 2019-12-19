package kata.game_of_three.impl.autonomous_player;

import kata.game_of_three.GameTable;
import kata.game_of_three.Move;
import kata.game_of_three.Player;
import kata.game_of_three.impl.GameOfThreeMockUtils;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

public class AutonomousPlayerTest {

    @Test
    public void shouldReplyWithZero() {
	GameTable gameTable = Mockito.mock(GameTable.class);

	Player opponentPlayer = GameOfThreeMockUtils.mockPlayer("player1");
	AutonomousPlayer autonomousPlayer = new AutonomousPlayer(GameOfThreeMockUtils.mockPlayerIdentifier("player2"), gameTable);

	UUID gameUuid = UUID.randomUUID();

	Move player1Move = new Move(gameUuid, opponentPlayer.getIdentifier(), autonomousPlayer.getIdentifier(), Move.REPLY.ZERO, 15);
	autonomousPlayer.playTurn(player1Move);

	Move expectedMove = new Move(gameUuid, autonomousPlayer.getIdentifier(), opponentPlayer.getIdentifier(), Move.REPLY.ZERO, 5);
	Mockito.verify(gameTable, Mockito.times(1)).acceptMove(expectedMove);
    }

    @Test
    public void shouldReplyWithOne() {
	GameTable gameTable = Mockito.mock(GameTable.class);

	Player opponentPlayer = GameOfThreeMockUtils.mockPlayer("player1");
	AutonomousPlayer autonomousPlayer = new AutonomousPlayer(GameOfThreeMockUtils.mockPlayerIdentifier("player2"), gameTable);

	UUID gameUuid = UUID.randomUUID();

	Move player1Move = new Move(gameUuid, opponentPlayer.getIdentifier(), autonomousPlayer.getIdentifier(), Move.REPLY.ZERO, 14);
	autonomousPlayer.playTurn(player1Move);

	Move expectedMove = new Move(gameUuid, autonomousPlayer.getIdentifier(), opponentPlayer.getIdentifier(), Move.REPLY.ONE, 5);
	Mockito.verify(gameTable, Mockito.times(1)).acceptMove(expectedMove);
    }

    @Test
    public void shouldReplyWithMinusOne() {
	GameTable gameTable = Mockito.mock(GameTable.class);

	Player opponentPlayer = GameOfThreeMockUtils.mockPlayer("player1");
	AutonomousPlayer autonomousPlayer = new AutonomousPlayer(GameOfThreeMockUtils.mockPlayerIdentifier("player2"), gameTable);

	UUID gameUuid = UUID.randomUUID();

	Move player1Move = new Move(gameUuid, opponentPlayer.getIdentifier(), autonomousPlayer.getIdentifier(), Move.REPLY.ZERO, 16);
	autonomousPlayer.playTurn(player1Move);

	Move expectedMove = new Move(gameUuid, autonomousPlayer.getIdentifier(), opponentPlayer.getIdentifier(), Move.REPLY.MINUS_ONE, 5);
	Mockito.verify(gameTable, Mockito.times(1)).acceptMove(expectedMove);
    }

}
