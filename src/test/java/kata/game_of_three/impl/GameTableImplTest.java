package kata.game_of_three.impl;

import kata.game_of_three.*;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class GameTableImplTest {

    private Games games;
    private GameTableImpl gameTable;

    @Before
    public void setUp() {
	games = mock(Games.class);
	gameTable = new GameTableImpl(games);
    }

    private static Player mockPlayer(String playerId) {
	PlayerIdentifier playerIdentifier = mock(PlayerIdentifier.class);
	when(playerIdentifier.getIdentifier()).thenReturn("player");

	Player player = mock(Player.class);
	when(player.getIdentifier()).thenReturn(playerIdentifier);
	return player;
    }

    @SuppressWarnings("Duplicates") @Test
    public void shouldAcceptValidMove() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 8);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(game);

	Move move = new Move(gameUuid, player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.ONE, 3);
	gameTable.acceptMove(move);

	verify(player1, times(1)).playTurn(move);
    }

    @Test
    public void shouldNotdAcceptNumberNotDivisibleByThree() {

	Player player1 = mockPlayer("player1");
	Player player2 = mockPlayer("player2");

	UUID gameUuid = UUID.randomUUID();
	Move lastMove = new Move(gameUuid, player1.getIdentifier(), player2.getIdentifier(), Move.REPLY.ZERO, 8);

	Game game = new Game(gameUuid, player1, player2, lastMove);
	when(games.getGame(gameUuid)).thenReturn(game);

	Move move = new Move(gameUuid, player2.getIdentifier(), player1.getIdentifier(), Move.REPLY.ONE, 7);
	gameTable.acceptMove(move);

	verify(player2, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_LOSE, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
	verify(player1, times(1)).endGame(new GameResult(GameResult.GAME_OUTCOME.YOU_WIN, GameResult.GAME_OUTCOME_REASON.INVALID_MOVE));
    }
}

