package kata.game_of_three.impl;

import kata.game_of_three.Move;
import kata.game_of_three.Player;
import kata.game_of_three.PlayerIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class GameTableImplTest {

    private GameTableImpl gameTable;

    @Before
    public void setUp() {
	gameTable = new GameTableImpl();
    }

    @Test
    public void shouldAcceptValidFirstMove() {

	PlayerIdentifier playerId = mock(PlayerIdentifier.class);
	when(playerId.getIdentifier()).thenReturn("player");

	PlayerIdentifier opponentId = mock(PlayerIdentifier.class);
	when(opponentId.getIdentifier()).thenReturn("player");

	Player opponent = mock(Player.class);
	Move move = new Move(UUID.randomUUID(), UUID.randomUUID(), playerId, opponentId, Move.REPLY.ZERO, 6);

	gameTable.acceptMove(move);

	verify(opponent, times(1)).playTurn(move);
    }

}
